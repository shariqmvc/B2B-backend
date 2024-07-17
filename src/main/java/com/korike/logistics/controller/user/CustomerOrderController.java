package com.korike.logistics.controller.user;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.*;

import com.fasterxml.jackson.databind.MapperFeature;
import com.itextpdf.text.pdf.AcroFields;
import com.korike.logistics.common.OrderStatusText;
import com.korike.logistics.entity.*;
import com.korike.logistics.model.ConsumerDetails;
import com.korike.logistics.model.PartnerLocation;
import com.korike.logistics.model.orders.*;
import com.korike.logistics.model.partner.PartnerContact;
import com.korike.logistics.model.partner.PartnerDetails;
import com.korike.logistics.model.partner.ServicePartnerDetailsDto;
import com.korike.logistics.model.payments.PaymentTransactionDetailsDto;
import com.korike.logistics.repository.*;
import com.korike.logistics.service.ConsumerService;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.common.OrderStatus;
import com.korike.logistics.exception.InvalidRequestParameterException;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.FeedBackReqDto;
import com.korike.logistics.model.payments.PaymentDetails;
import com.korike.logistics.model.payments.PaymentRequestDto;
import com.korike.logistics.model.payments.Tax;
import com.korike.logistics.model.services.ServiceDetails;
import com.korike.logistics.service.CustomerOrderService;
import com.korike.logistics.util.CommonUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/consumer")
public class CustomerOrderController {
	
	private static Logger logger = Logger.getLogger(CustomerOrderController.class);

	@Autowired
	UserRepository userRepository;
	@Autowired
	CustomerOrderService customerOrdService;
	@Autowired
	CustomerOrderRepository orderRepo;
	@Autowired
	ServicePartnerRepository servicePartnerRepository;
	@Autowired
	ServiceDefinitionRepository serviceDefRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ConsumerRepository consumerRepo;
	@Autowired
	ConsumerService consumerService;
	@Autowired
	PaymentTransactionDetailRepository paymentTransactionDetailRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	
	
	@RequestMapping(value={"/order/{o_id}","/order"}, method  = RequestMethod.POST)
	public ResponseEntity<?> createUpdateOrder(@PathVariable(required=false) String o_id, @RequestParam( required = false, name="action") String action, @RequestBody OrderReqDto orderReq, HttpServletRequest request) throws JsonProcessingException{


		ObjectMapper mapper = new ObjectMapper();
		List<String> itemIds = new ArrayList<>();
		List<Items> items = new ArrayList<>();
		if(null!=orderReq.getoDetails().getItems() && !orderReq.getoDetails().getItems().isEmpty() && orderReq.getoDetails().getItems().size()>0) {
			for (OrderItemsListDto orderItemsListDto : orderReq.getoDetails().getItems()) {
				itemIds.add(orderItemsListDto.getItemId());
			}
			try {
				items = itemRepository.getItemsByItemIds(itemIds);
			}catch(Exception e){
				System.out.println(Arrays.toString(e.getStackTrace()));
			}
		}

		if(null!=items && !items.isEmpty() && items.size()>0) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getItem_id().equalsIgnoreCase(orderReq.getoDetails().getItems().get(i).getItemId())) {
					orderReq.getoDetails().getItems().get(i).setBaseCost(items.get(i).getItemBaseCost());
					if(null!=items.get(i).getMrp()) {
						orderReq.getoDetails().getItems().get(i).setMrpCost(items.get(i).getMrp().doubleValue());
					}
				}
			}
		}

        Consumer fetchedConsumer = new Consumer(); // Adjusted for testing without token
        ServiceDefinition fetchedServiceDefinition = null;
		String sid = null;
        if("new".equals(action) || "edit".equals(action)) {
        	if(orderReq.getoDetails().getSid() == null && orderReq.getoDetails().getSid().isEmpty()) {
    			if(null==o_id || o_id.isEmpty()) {
					logger.error("sid missing");
					throw new InvalidParameterException("sid missing");
				}
    			else{
					Optional<CustomerOrderDetails> fetchedOrderForSid = orderRepo.findById(o_id);
					sid = fetchedOrderForSid.get().getServiceDefintion().getServiceId();
				}
    		}
        	else{
				sid = orderReq.getoDetails().getSid();
			}

    	    fetchedServiceDefinition = serviceDefRepo.getByServiceId(sid);
    		if(fetchedServiceDefinition == null) {
    			logger.error("Invalid sid "+sid);
    			throw new ResourceNotFoundException("Invalid sid "+sid);
    		}
        }
		//commented for testing without token
       Optional<User> fetchedUser = userRepo.findByUserName(CommonUtils.getCurrentUserName());
		if(!fetchedUser.isPresent()) {
			logger.error("Invalid userName " +CommonUtils.getCurrentUserName());
			throw new ResourceNotFoundException("Invalid userName " +CommonUtils.getCurrentUserName());
		}
		
		
		fetchedConsumer = consumerRepo.findByUser(fetchedUser.get());
		if(fetchedConsumer == null) {
			logger.error("Error fetching consumer for user "+fetchedUser.get());
			throw new ResourceNotFoundException("Error fetching consumer for user " + CommonUtils.getCurrentUserName());
		}
		
		OrderResponseDto orderRes = new OrderResponseDto();
		if("new".equals(action)) {
			String reqJson = mapper.writeValueAsString(orderReq);
			orderRes = customerOrdService.createOrder(reqJson, orderReq, fetchedConsumer, fetchedServiceDefinition);
			Optional<CustomerOrderDetails> fetchedOrderForNew = orderRepo.findById(orderRes.getOrderId());
			fetchedConsumer = consumerService.updateConsumer(fetchedOrderForNew.get(),fetchedConsumer,fetchedUser.get(), "ORDER_CREATED");
			logger.info("Created Order "+orderRes.getOrderId()+" for consumer "+fetchedConsumer);
		}else if("edit".equals(action)) {
			//Update functionality
			Optional<CustomerOrderDetails> fetchedOrderForEdit = orderRepo.findById(o_id);
			ObjectMapper odetailsMapper = new ObjectMapper();
			if(!fetchedOrderForEdit.isPresent()) {
				logger.error("Invalid o_id " +o_id);
				throw new ResourceNotFoundException("Invalid o_id " +o_id);
			}
			OrderReqDto oDetails = odetailsMapper.readValue(fetchedOrderForEdit.get().getOrderDetails(), OrderReqDto.class);
			oDetails.getoDetails().setOrderStatus(orderReq.getoDetails().getOrderStatus());
			orderReq.setoDetails(oDetails.getoDetails());
			String reqJson = mapper.writeValueAsString(orderReq);
			if(fetchedOrderForEdit.get().getOrderStatus().equals("CREATED") || fetchedOrderForEdit.get().getOrderStatus().equals("ACCEPTED") || orderReq.getoDetails().getOrderStatus().equalsIgnoreCase("PAYMENT_COMPLETED")) {
				boolean matchedTransactionList = false;
				List<PaymentTransactionDetails> fetchedPaymentTransactionDetails = paymentTransactionDetailRepository.fetchTransactionDetailsByOrderId(fetchedOrderForEdit.get().getOrderId());
				if (null != fetchedPaymentTransactionDetails && !fetchedPaymentTransactionDetails.isEmpty()) {
					for (PaymentTransactionDetails paymentTransactionDetails : fetchedPaymentTransactionDetails) {
						if (null != orderReq.getTransactionDetails() && orderReq.getTransactionDetails().getPayTransId().equals(paymentTransactionDetails.getPayTransId())) {
							paymentTransactionDetails.setPaymentType(orderReq.getTransactionDetails().getPaymentType());
							paymentTransactionDetails.setPayTransId(orderReq.getTransactionDetails().getPayTransId());
							paymentTransactionDetails.setStatus(orderReq.getTransactionDetails().getStatus());
							paymentTransactionDetails.setLastUpdatedBy(fetchedConsumer.getConsumerId());
							paymentTransactionDetails.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
							paymentTransactionDetails.setCreatedBy(fetchedConsumer.getConsumerId());
							paymentTransactionDetails.setCreatedAt(new Timestamp(System.currentTimeMillis()));
							paymentTransactionDetails.setPaymentDetails(orderReq.getTransactionDetails().getPaymentDetails());
							paymentTransactionDetails.setCustomerOrderDetails(fetchedOrderForEdit.get());
							paymentTransactionDetailRepository.save(paymentTransactionDetails);
							matchedTransactionList = true;
						}
					}
					if (!matchedTransactionList) {
						PaymentTransactionDetails paymentTransactionDetails = new PaymentTransactionDetails();
						paymentTransactionDetails.setPaymentType(orderReq.getTransactionDetails().getPaymentType());
						paymentTransactionDetails.setPayTransId(orderReq.getTransactionDetails().getPayTransId());
						paymentTransactionDetails.setStatus(orderReq.getTransactionDetails().getStatus());
						paymentTransactionDetails.setLastUpdatedBy(fetchedConsumer.getConsumerId());
						paymentTransactionDetails.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
						paymentTransactionDetails.setCreatedBy(fetchedConsumer.getConsumerId());
						paymentTransactionDetails.setCreatedAt(new Timestamp(System.currentTimeMillis()));
						paymentTransactionDetails.setPaymentDetails(orderReq.getTransactionDetails().getPaymentDetails());
						paymentTransactionDetails.setCustomerOrderDetails(fetchedOrderForEdit.get());
						paymentTransactionDetailRepository.save(paymentTransactionDetails);
					}
				} else if (null != orderReq.getTransactionDetails()) {
					PaymentTransactionDetails paymentTransactionDetails = new PaymentTransactionDetails();
					paymentTransactionDetails.setPaymentType(orderReq.getTransactionDetails().getPaymentType());
					paymentTransactionDetails.setPayTransId(orderReq.getTransactionDetails().getPayTransId());
					paymentTransactionDetails.setStatus(orderReq.getTransactionDetails().getStatus());
					paymentTransactionDetails.setLastUpdatedBy(fetchedConsumer.getConsumerId());
					paymentTransactionDetails.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
					paymentTransactionDetails.setCreatedBy(fetchedConsumer.getConsumerId());
					paymentTransactionDetails.setCreatedAt(new Timestamp(System.currentTimeMillis()));
					paymentTransactionDetails.setPaymentDetails(orderReq.getTransactionDetails().getPaymentDetails());
					paymentTransactionDetails.setCustomerOrderDetails(fetchedOrderForEdit.get());
					paymentTransactionDetailRepository.save(paymentTransactionDetails);
				}
				if(null!=orderReq.getoDetails().getOrderStatus() && !orderReq.getoDetails().getOrderStatus().isEmpty() && orderReq.getoDetails().getOrderStatus().equalsIgnoreCase("PAYMENT_COMPLETED")) {
					fetchedOrderForEdit.get().setOrderStatus(OrderStatus.PAYMENT_COMPLETED.toString());
					orderRes = customerOrdService.updateOrder(reqJson, orderReq, fetchedConsumer, fetchedServiceDefinition, fetchedOrderForEdit.get(), "edit");
				}
				else if(null!=orderReq.getoDetails().getOrderStatus() && !orderReq.getoDetails().getOrderStatus().isEmpty() && orderReq.getoDetails().getOrderStatus().equalsIgnoreCase("PAYMENT_FAILED")) {
					orderRes = customerOrdService.updateOrder(reqJson, orderReq, fetchedConsumer, fetchedServiceDefinition, fetchedOrderForEdit.get(), "edit");
					fetchedOrderForEdit.get().setOrderStatus(OrderStatus.PAYMENT_FAILED.toString());
				}
				else {
					orderRes = customerOrdService.updateOrder(reqJson, orderReq, fetchedConsumer, fetchedServiceDefinition, fetchedOrderForEdit.get(), "edit");
				}
			}
			else{
				Map<String, Object> model = new HashMap<>();
				ObjectMapper mapperForResponseOrder = new ObjectMapper();
				OrderReqDto orderDetails = mapperForResponseOrder.readValue(fetchedOrderForEdit.get().getOrderDetails(), OrderReqDto.class);
				model.put("details", orderDetails.getoDetails());
				model.put("message","Incorrect order status to update Order details");
				model.put("success","false");
				return ok(model);
			}
		}else if("cancel".equals(action)){
			String message = orderReq.getoDetails().getMessage();
			Optional<CustomerOrderDetails> fetchedOrderCancel;
			fetchedOrderCancel = orderRepo.findById(o_id);
			if(message!=null && !message.isEmpty()) {
				if(!fetchedOrderCancel.isPresent()) {
					logger.error("Invalid o_id " +o_id);
					throw new ResourceNotFoundException("Invalid o_id " +o_id);
				}
				OrderReqDto orderDetails = mapper.readValue(fetchedOrderCancel.get().getOrderDetails(), OrderReqDto.class);
			OrderDetails fetchedOrdDetails = orderDetails.getoDetails();
			fetchedOrdDetails.setMessage(message);
			orderRes = customerOrdService.updateOrder(mapper.writeValueAsString(orderDetails), orderDetails, fetchedConsumer, fetchedServiceDefinition, fetchedOrderCancel.get(),"cancel");
		}
	}
		
		Map<String, Object> model = new HashMap<>();
		Optional<CustomerOrderDetails> fetchedOrderForResponse = orderRepo.findById(orderRes.getOrderId());
		ObjectMapper mapperForResponseOrder = new ObjectMapper();
		if(!fetchedOrderForResponse.isPresent()) {
			logger.error("Invalid o_id " +o_id);
			throw new ResourceNotFoundException("Invalid o_id " +o_id);
		}
		OrderReqDto orderDetailsForResponse = mapperForResponseOrder.readValue(fetchedOrderForResponse.get().getOrderDetails(), OrderReqDto.class);
		model.put("order_id", fetchedOrderForResponse.get().getOrderId());
		model.put("details", orderDetailsForResponse.getoDetails());
		model.put("order_status", fetchedOrderForResponse.get().getOrderStatus());
		model.put("order_status_text", OrderStatusText.getOrderStatusText(fetchedOrderForResponse.get().getOrderStatus()));
		if(null!=orderReq.getoDetails().getOrderStatus() && orderReq.getoDetails().getOrderStatus().equalsIgnoreCase("PAYMENT_COMPLETED")){
			Optional<PaymentTransactionDetails> paymentTransactionDetailsForResponse = paymentTransactionDetailRepository.findById(orderReq.getTransactionDetails().getPayTransId());
//			ObjectMapper mapperForResponseTransaction = new ObjectMapper();
//			PaymentTransactionDetailsDto paymentTransactionDetailsDtoForResponse = mapperForResponseTransaction.readValue(paymentTransactionDetailsForResponse.get().toString(), PaymentTransactionDetailsDto.class);
			model.put("transactionDetails", paymentTransactionDetailsForResponse.toString());
		}
		model.put("success","true");
		if("new".equals(action)) {
			model.put("statusDetail", "Created Order "+fetchedOrderForResponse.get().getOrderId());
		}else {
			model.put("statusDetail", "Updated Order "+fetchedOrderForResponse.get().getOrderId());
		}
		
		return ok(model);
	}

	@PostMapping("/orders")
	public ResponseEntity<?> getOrderList(@RequestBody OrdersListReqDto orderLstReq, HttpServletRequest request) throws JsonProcessingException{

		String token = request.getHeader("Authorization").substring(1);
		System.out.println("Token extracted from Order API"+ token);
		
		List<OrderResponseDto> orderList = customerOrdService.getOrdersList(orderLstReq);
		
		
		System.out.println("Hit");
		Map<String, Object> model = new HashMap<>();
		model.put("details", orderList);
		model.put("success","true");
		model.put("statusDetail", "Found "+orderList.size()+" orders");
		return ok(model);
	}
	
	@GetMapping("/order/{o_id}")
	public ResponseEntity<?> getOrderByOid(@PathVariable(required=false) String o_id) throws JsonProcessingException, JSONException {
		
		CustomerOrderDetails order = customerOrdService.getOrderByOid(o_id);
		ObjectMapper mapper = new ObjectMapper();
		OrderReqDto orderDetails = mapper.readValue(order.getOrderDetails(), OrderReqDto.class);
		if(order.getOrderStatus().equalsIgnoreCase("created")) {
			Map<String, Object> model = new HashMap<>();
			model.put("order_details", orderDetails);
			model.put("order_status", order.getOrderStatus());
			model.put("order_status_text", OrderStatusText.getOrderStatusText(order.getOrderStatus()));
			model.put("success", "true");
			model.put("statusDetail", "Found order " + order.getOrderId());
			return ok(model);
		}
		else{
			ObjectMapper servicePartnerDtoMapper = new ObjectMapper();
			PartnerDetails partnerDetails = new PartnerDetails();
			ServicePartnerDetailsDto servicePartnerDetailsDto = new ServicePartnerDetailsDto();
			partnerDetails.setPartnerId(order.getPartner().getPartnerId());
			partnerDetails.setPartnerName(order.getPartner().getPartnerName());
			partnerDetails.setPartnerLocation(servicePartnerDtoMapper.readValue(order.getPartner().getPartnerLocation(), PartnerLocation.class));
			ObjectMapper partnerContactMapper = new ObjectMapper();
			partnerDetails.setPartnerContact(partnerContactMapper.readValue(order.getPartner().getPartnerContact(), PartnerContact.class));
			servicePartnerDetailsDto.setPartnerDetails(partnerDetails);
			Map<String, Object> model = new HashMap<>();
			model.put("order_details", orderDetails);
			model.put("partner_details",servicePartnerDetailsDto.getPartnerDetails());
			model.put("order_status", order.getOrderStatus());
			model.put("order_status_text", OrderStatusText.getOrderStatusText(order.getOrderStatus()));
			model.put("success", "true");
			model.put("statusDetail", "Found order " + order.getOrderId());
			return ok(model);
		}
	}
	@RequestMapping(value={"/order/payment/{o_id}"}, method  = RequestMethod.POST)
	public ResponseEntity<?> getPaymentDetails(@PathVariable(required=false) String o_id, @RequestBody PaymentRequestDto payReq) throws JsonProcessingException{
		
		if(payReq.getStatus() == null || payReq.getStatus().isEmpty()) {
			logger.error("Invalid o_id " +o_id);
			throw new InvalidRequestParameterException("Status is missing");
		}
		
		
		Map<String, Object> model = new HashMap<>();
		
		if(OrderStatus.COMPLETED.name().equals(payReq.getStatus())) {
			Optional<CustomerOrderDetails> fetchedOrder = orderRepo.findById(o_id);
			if(!fetchedOrder.isPresent()) {
				logger.error("Invalid o_id " +o_id);
				throw new ResourceNotFoundException("Invalid o_id " +o_id);
			}
			
			/*Just for testing in prod we need payment object from payment table*/
			PaymentDetails newPayDetails = new PaymentDetails();
			Tax newTax = new Tax();
			newTax.setGst1(Double.valueOf("10"));
			newTax.setGst2(Double.valueOf("8"));
			
			newPayDetails.setAmount(Double.valueOf("125"));
			newPayDetails.setDiscount(Double.valueOf("0"));
			newPayDetails.setTaxes(newTax);
			newPayDetails.setTotal(Double.valueOf("143"));
			newPayDetails.setWalletBalance(Double.valueOf("1000"));
			
			payReq.setOrderId(o_id);
			payReq.setPayment(newPayDetails);
			model.put("details", payReq);
			model.put("success","true");
			model.put("statusDetail", "Payment Details");
		}else {
			model.put("details", null);
			model.put("success","false");
			model.put("statusDetail", "Payment Details");
		}
		
		return ok(model);
		
	}

	@GetMapping("/customerDetails")
	public ResponseEntity<?> getPartnerDetails(){

		logger.info("Started GET Customer Details request");
		Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
		ConsumerDetails fetchedConsumer = consumerService.queryConsumerProfile(fetchedUser.get());
		if(null!=fetchedConsumer) {
			Map<String, Object> model = new HashMap<>();
			model.put("customerDetails", fetchedConsumer);
			model.put("success", "true");
			model.put("statusDetail", "Found consumer");
			return ok(model);
		}
		else{
			Map<String, Object> model = new HashMap<>();
			model.put("success", "false");
			model.put("statusDetail", "Consumer details not updated, use /api/consumer/profile to update.");
			return ok(model);
		}
	}

	@RequestMapping(value={"/profile"}, method  = RequestMethod.POST)
	public ResponseEntity<?> updateConsumerProfile(@RequestBody ConsumerDetails consumerDetails) throws JsonProcessingException {

		Consumer fetchedConsumer;
		ConsumerDetails fetchedConsumerDetails;
		Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
		consumerDetails.setUserId(fetchedUser.get().getUserId());
		fetchedConsumer = consumerService.createConsumerProfile(consumerDetails, fetchedUser.get());
		ObjectMapper mapper2 = new ObjectMapper();
		fetchedConsumerDetails = mapper2.readValue(fetchedConsumer.getConsumerDetails(), ConsumerDetails.class);
		Map<String, Object> model = new HashMap<>();
		model.put("customerDetails", fetchedConsumerDetails);
		model.put("success","true");
		model.put("statusDetail", "Created/Updated consumer profile");
		return ok(model);
	}
	
/*	@RequestMapping(value={"/order/{o_id}/getinvoice"}, method  = RequestMethod.GET)
	public ResponseEntity<?> getInvoice(@PathVariable() String o_id) throws JsonProcessingException{
		logger.info("Started getInvoice() method ");
		
		Optional<CustomerOrderDetails> fetchedOrder = customerOrderRepo.findById(o_id);
		if(!fetchedOrder.isPresent()) {
			logger.error("Invalid oid " +CommonUtils.getCurrentUserName());
			throw new ResourceNotFoundException("Invalid oid " +CommonUtils.getCurrentUserName());
		}
		
	} */
		
}

