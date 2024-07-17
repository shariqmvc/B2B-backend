package com.korike.logistics.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.common.OrderStatus;
import com.korike.logistics.common.OrderStatusText;
import com.korike.logistics.common.PartnerStatus;
import com.korike.logistics.entity.*;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.InvoiceResDto;
import com.korike.logistics.model.PartnerLocation;
import com.korike.logistics.model.orders.OrderDetails;
import com.korike.logistics.model.orders.OrderReqDto;
import com.korike.logistics.model.orders.OrderResponseDto;
import com.korike.logistics.model.partner.*;
import com.korike.logistics.model.services.Details;
import com.korike.logistics.model.services.ServiceDetails;
import com.korike.logistics.repository.*;
import com.korike.logistics.service.*;
import com.korike.logistics.util.CommonUtils;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

//import javax.persistence.criteria.Order;
//import javax.rmi.CORBA.Util;
//import javax.swing.text.html.parser.Parser;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ServicePartnerController {
    private static Logger logger = Logger.getLogger(ServicePartnerController.class);
    @Autowired
    PartnerRepository servicePartnerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceDefinitionRepository serviceDefRepo;
    @Autowired
    PartnerService partnerService;
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    CustomerOrderService customerOrdService;

    @Autowired
    ServiceDefintitionService serviceDefintitionService;
    @Autowired
    CustomerOrderRepository customerOrderRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    PartnerDeclinedOrdersRepo partnerDeclinedOrdersRepo;
    @Autowired
    PartnerDeclinedOrdersService partnerDeclinedOrdersService;


    @RequestMapping(value = {"/api/partner/{partner_id}", "/api/partner"}, method = RequestMethod.POST)
    public ResponseEntity<?> createUpdatePartner(@PathVariable(required = false) String partner_id, @RequestParam(required = false, name = "action") String action, @RequestBody ServicePartnerReqDto servicePartnerReqDto) throws JsonProcessingException {

        ServiceDefinition fetchedServiceDefinition = null;
        if ("new".equals(action) || "edit".equals(action)) {
            if (servicePartnerReqDto.getPartner().getPartnerSid() == null && servicePartnerReqDto.getPartner().getPartnerSid().isEmpty()) {
                logger.error("sid missing");
                throw new InvalidParameterException("sid missing");
            }
            String sid = servicePartnerReqDto.getPartner().getPartnerSid();
            fetchedServiceDefinition = serviceDefRepo.getByServiceId(sid);
            if (fetchedServiceDefinition == null) {
                logger.error("Invalid sid " + sid);
                throw new ResourceNotFoundException("Invalid sid " + sid);
            }
        }
        Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        if (!fetchedUser.isPresent()) {
            logger.error("Invalid userName " + CommonUtils.getCurrentUserName());
            throw new ResourceNotFoundException("Invalid userName " + CommonUtils.getCurrentUserName());
        }
        ServicePartnerResponseDto partnerServiceCrudResponse = null;
        if ("new".equals(action)) {
            partnerServiceCrudResponse = partnerService.createPartner(servicePartnerReqDto, fetchedUser.get(), fetchedServiceDefinition);
        } else if ("edit".equals(action)) {
            if (!servicePartnerReqDto.getPartner().getPartnerId().isEmpty() && servicePartnerReqDto.getPartner().getPartnerId() != null) {
                Optional<Partner> fetchedServicePartner = servicePartnerRepository.findById(servicePartnerReqDto.getPartner().getPartnerId());
                partnerServiceCrudResponse = partnerService.updatePartner(fetchedServicePartner.get(), servicePartnerReqDto, fetchedUser.get(), fetchedServiceDefinition);
            } else {
                Optional<Partner> fetchedServicePartner = servicePartnerRepository.findById(partner_id);
                partnerServiceCrudResponse = partnerService.updatePartner(fetchedServicePartner.get(), servicePartnerReqDto, fetchedUser.get(), fetchedServiceDefinition);
            }
        }
        Map<String, Object> model = new HashMap<>();
        model.put("details", partnerServiceCrudResponse);
        model.put("success", "true");

        if ("new".equals(action)) {
            model.put("statusDetail", "Created Partner " + partnerServiceCrudResponse.getPartnerId());
        } else {
            model.put("statusDetail", "Updated Partner " + partnerServiceCrudResponse.getPartnerId());
        }

        return ok(model);
    }

    @RequestMapping(value = {"/api/partner/{partner_id}/receiveOrders"}, method = RequestMethod.POST)
    public ResponseEntity<Object> receiveOrders(@PathVariable(required = false) String partner_id, @RequestBody ServicePartnerReqDto servicePartnerReqDto) throws JsonProcessingException, JSONException {

        ServiceDefinition fetchedServiceDefinition = null;
        fetchedServiceDefinition = serviceDefRepo.getByServiceId(servicePartnerReqDto.getPartner().getPartnerSid());
        Optional<Partner> fetchedServicePartner = servicePartnerRepository.findById(partner_id);
        if (!fetchedServicePartner.isPresent()) {
            logger.error("Invalid partner id ");
            throw new ResourceNotFoundException("Invalid partnerId ");
        }

        OrderResponseDto orderRes = null;
        ObjectMapper mapper = new ObjectMapper();
        ServicePartnerResponseDto partnerServiceCrudResponse = null;
        Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        if (!fetchedUser.isPresent()) {
            logger.error("Invalid userName " + CommonUtils.getCurrentUserName());
            throw new ResourceNotFoundException("Invalid userName " + CommonUtils.getCurrentUserName());
        }
        partnerService.updatePartnerLocation(fetchedServicePartner.get(), servicePartnerReqDto, fetchedUser.get(), fetchedServiceDefinition);
        List<CustomerOrderDetails> orderList = customerOrdService.fetchOpenOrderBySidForPartner(servicePartnerReqDto.getPartner().getPartnerSid(), partner_id);
        Map<String, Object> model = new HashMap<>();
        if (orderList.size() == 0) {
            model.put("orderSize", orderList.size());
            model.put("message", "No orders in the system at the moment, please stay online for receiving orders");
        } else {
            OrderReqDto orderDetails = mapper.readValue(orderList.get(0).getOrderDetails(), OrderReqDto.class);
            model.put("orderSize", orderList.size());
            model.put("orderId", orderList.get(0).getOrderId());
            model.put("orderDetails", orderDetails);
            model.put("orderStatus", orderList.get(0).getOrderStatus());
        }
        model.put("success", "true");
        return ok(model);
    }

    @RequestMapping(value = {"/api/partner/{partner_id}/order/{orderId}"}, method = RequestMethod.POST)
    public ResponseEntity<?> updateOrder(@PathVariable(required = true) String partner_id, @PathVariable(required = true) String orderId, @RequestParam(required = false, name = "action") String action, @RequestBody OrderReqDto orderReqDto) throws JsonProcessingException, JSONException {
        InvoiceResDto invoiceResDto = null;
        Map<String, Object> model = new HashMap<>();
        Optional<Partner> fetchedServicePartner = servicePartnerRepository.findById(partner_id);
        if (!fetchedServicePartner.isPresent()) {
            logger.error("Invalid partner id ");
            throw new ResourceNotFoundException("Invalid partnerId ");
        }

        Optional<CustomerOrderDetails> fetchedCustomerOrderDetails = customerOrderRepository.findById(orderId);
        if (!fetchedCustomerOrderDetails.isPresent()) {
            logger.error("Invalid order id");
            throw new ResourceNotFoundException("Invalid order id");
        }
        CustomerOrderDetails savedCustOrdDetails = new CustomerOrderDetails();

        if ("updateOrder".equals(action)) {

            switch (orderReqDto.getoDetails().getOrderStatus()) {
                case "ACCEPTED":
                    if ("CREATED".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.ACCEPTED.toString());
                        fetchedCustomerOrderDetails.get().setPartner(fetchedServicePartner.get());
                        ObjectMapper order_details_mapper = new ObjectMapper();
                        OrderReqDto orderDetails = order_details_mapper.readValue(fetchedCustomerOrderDetails.get().getOrderDetails(), OrderReqDto.class);
                        orderDetails.getoDetails().setSourceLocation(orderReqDto.getoDetails().getPartnerLocation().getPartnerLatLong());
                        fetchedCustomerOrderDetails.get().setOrderDetails(order_details_mapper.writeValueAsString(orderDetails));
                    }
                    break;
                case "DECLINED":
                    List<PartnerDeclinedOrders> declinedOrders = partnerDeclinedOrdersRepo.getDeclinedOrdersByOrderAndPartner(partner_id, orderId);
                    if (declinedOrders.size() > 0) {
                        model.put("isDeclined", true);
                    } else {
                        if ("CREATED".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                            ObjectMapper partnerLocationMapper = new ObjectMapper();
                            partnerDeclinedOrdersService.entryPartnerDeclinedOrder(orderId, fetchedServicePartner.get().getPartnerId(), partnerLocationMapper.writeValueAsString(orderReqDto.getoDetails().getPartnerLocation()));
                        }
                    }
                    break;
                case "PREPARING":
                    if ("ACCEPTED".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.PREPARING.toString());
                        fetchedCustomerOrderDetails.get().setLocation(fetchedServicePartner.get().getPartnerLocation());
                    }
                    break;

                case "PREPARED":
                    if ("PREPARING".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.PREPARED.toString());
                        fetchedCustomerOrderDetails.get().setLocation(fetchedServicePartner.get().getPartnerLocation());
                    }
                    break;
                case "GENERATING_BILL":
                    if ("PREPARED".equals(fetchedCustomerOrderDetails.get().getOrderStatus()) || "GENERATED_BILL".equals(fetchedCustomerOrderDetails.get().getOrderStatus()) || "AWAITING_PAYMENT".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        logger.info("In " + fetchedCustomerOrderDetails.get().getOrderStatus() + "condition");
                        fetchedCustomerOrderDetails.get().setLocation(fetchedServicePartner.get().getPartnerLocation());
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        savedCustOrdDetails = customerOrderRepository.save(fetchedCustomerOrderDetails.get());
                        logger.info("In " + fetchedCustomerOrderDetails.get().getOrderStatus() + "PREPARED condition: savedCustOrderDetails" + savedCustOrdDetails.toString());
                        //Generate Invoice
                        ObjectMapper mapper = new ObjectMapper();
                        OrderReqDto orderDetails = mapper.readValue(savedCustOrdDetails.getOrderDetails(), OrderReqDto.class);
                        logger.info("In " + fetchedCustomerOrderDetails.get().getOrderStatus() + "condition: orderDetails" + orderDetails.toString());
                        //Save to Invoice table
                        Invoice invoice = new Invoice();
                        invoice.setInvoiceId(UUID.randomUUID().toString());
                        invoice.setCustomerOrdDetails(savedCustOrdDetails);
                        invoice.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                        invoice.setCreatedBy(CommonUtils.getCurrentUserName());
                        invoice.setService(savedCustOrdDetails.getServiceDefintion());
                        ObjectMapper invoiceRespDtoMapper = new ObjectMapper();
                        invoiceResDto = invoiceService.generateInvoice(mapOrderDetailsToOrderResponseDto(orderDetails, fetchedServicePartner.get().getPartnerId(), orderId, savedCustOrdDetails.getConusmer().getConsumerId()));
                        String reqJson = invoiceRespDtoMapper.writeValueAsString(invoiceResDto);
                        logger.info("In " + fetchedCustomerOrderDetails.get().getOrderStatus() + " condition: invoiseEqJson" + reqJson);
                        invoice.setInvoiceDetails(reqJson);
                        invoiceRepository.save(invoice);

                        //save Invoice to OrderDetails
                        ObjectMapper mapperAfterForGeneratingBill = new ObjectMapper();
                        OrderReqDto orderDetailsForGeneratingBill = mapperAfterForGeneratingBill.readValue(savedCustOrdDetails.getOrderDetails(), OrderReqDto.class);
                        OrderDetails orderDetailsObject = orderDetailsForGeneratingBill.getoDetails();
                        logger.info("In PREPARING condition: Mapping Invoice orderDetails" + orderDetailsObject.toString());
                        orderDetailsObject.setInvoiceResDto(invoiceResDto);
                        orderDetailsForGeneratingBill.setoDetails(orderDetailsObject);
                        String reqJsonForGeneratingBill = mapper.writeValueAsString(orderDetailsForGeneratingBill);
                        fetchedCustomerOrderDetails.get().setOrderDetails(reqJsonForGeneratingBill);
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.GENERATING_BILL.toString());
                        customerOrderRepository.save(fetchedCustomerOrderDetails.get());
                    }
                    break;
                case "GENERATED_BILL":
                    if ("GENERATING_BILL".equals(fetchedCustomerOrderDetails.get().getOrderStatus()) || "AWAITING_PAYMENT".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.GENERATED_BILL.toString());
                    }
                    break;
                case "AWAITING_PAYMENT":
                    if ("GENERATED_BILL".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.AWAITING_PAYMENT.toString());

                    }
                    break;
                case "PAYMENT_COMPLETED":
                    if ("AWAITING_PAYMENT".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.PAYMENT_COMPLETED.toString());
                    }
                    break;
                case "PREPARING_DELIVERY":
                    if ("PAYMENT_COMPLETED".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.PREPARING_DELIVERY.toString());
                    }
                    break;
                case "DELIVERY_INPROGRESS":
                    if ("PREPARING_DELIVERY".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.DELIVERY_INPROGRESS.toString());
                    }
                    break;
                case "DELIVERY_ACKNOWLEDGING":
                    if ("DELIVERY_INPROGRESS".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.DELIVERY_ACKNOWLEDGING.toString());
                    }
                    break;
                case "COMPLETED":
                    if ("DELIVERY_ACKNOWLEDGING".equals(fetchedCustomerOrderDetails.get().getOrderStatus())) {
                        fetchedCustomerOrderDetails.get().setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.COMPLETED.toString());

                    }
                    break;
//                case "CANCELLED":
//                    fetchedCustomerOrderDetails.get().setOrderStatus(OrderStatus.CANCELLED.toString());
//                    break;
                default:
                    JSONObject acceptOrderResponse = new JSONObject();
                    acceptOrderResponse.put("status", "failed");
                    acceptOrderResponse.put("reason", "Wrong status or orderId/partnerId combination");
                    return ok(acceptOrderResponse);
            }
        }

        savedCustOrdDetails = customerOrderRepository.save(fetchedCustomerOrderDetails.get());
        ObjectMapper mapper = new ObjectMapper();
        OrderReqDto orderDetails = mapper.readValue(savedCustOrdDetails.getOrderDetails(), OrderReqDto.class);
        Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        model.put("customerOrderDetails", orderDetails);
        model.put("orderId", orderId);
        PartnerDetails partnerDetails = new PartnerDetails();
        Partner partner = servicePartnerRepository.findByPartnerId(partner_id);
//        JSONObject partnerDetails = new JSONObject();
        partnerDetails.setPartnerName(partner.getPartnerName());
        partnerDetails.setPartnerId(partner_id);
        ObjectMapper partnerLocationMapper = new ObjectMapper();
        partnerDetails.setPartnerLocation(partnerLocationMapper.readValue(partner.getPartnerLocation(), PartnerLocation.class));
        partnerDetails.setPartnerUsername(fetchedUser.get().getUsername());
        model.put("partnerDetails", partnerDetails);
        model.put("order_status", savedCustOrdDetails.getOrderStatus());
        model.put("order_status_text", OrderStatusText.getOrderStatusText(savedCustOrdDetails.getOrderStatus()));

        return ok(model);
    }

    private OrderResponseDto mapOrderDetailsToOrderResponseDto(OrderReqDto orderDetails, String partnerId, String orderId, String consumerId) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderDetails(orderDetails.getoDetails());
        orderResponseDto.setPartnerId(partnerId);
        orderResponseDto.setOrderStatus(orderDetails.getoDetails().getOrderStatus());
        orderResponseDto.setOrderId(orderId);
        orderResponseDto.setConsumerId(consumerId);
        return orderResponseDto;
    }

    @GetMapping("/api/partner/{partner_id}/order/{orderId}")
    public ResponseEntity<?> getOrderByOrderId(@PathVariable(required = false) String orderId) throws JsonProcessingException, JSONException {

        logger.info("Started GET Order by Order ID " + orderId);
        Optional<CustomerOrderDetails> fetchedCustomerOrderDetails = customerOrderRepository.findById(orderId);
        if (!fetchedCustomerOrderDetails.isPresent()) {
            logger.error("Invalid order id");
            throw new ResourceNotFoundException("Invalid order id");
        }
        CustomerOrderDetails savedCustOrdDetails = new CustomerOrderDetails();
        ObjectMapper mapper = new ObjectMapper();
        OrderReqDto orderDetails = mapper.readValue(savedCustOrdDetails.getOrderDetails(), OrderReqDto.class);

        Map<String, Object> model = new HashMap<>();
        model.put("customerOrderDetails", orderDetails);
        PartnerDetails partnerDetails = new PartnerDetails();
//        JSONObject partnerDetails = new JSONObject();
        partnerDetails.setPartnerName(savedCustOrdDetails.getPartner().getPartnerName());
        partnerDetails.setPartnerId(savedCustOrdDetails.getPartner().getPartnerId());
        ObjectMapper partnerLocationMapper = new ObjectMapper();
        partnerDetails.setPartnerLocation(partnerLocationMapper.readValue(savedCustOrdDetails.getPartner().getPartnerLocation(), PartnerLocation.class));
        model.put("partnerDetails", partnerDetails);
        model.put("customerOrderStatus", savedCustOrdDetails.getOrderStatus());
        model.put("order_status_text", OrderStatusText.getOrderStatusText(savedCustOrdDetails.getOrderStatus()));
        return ok(model);
    }

    @GetMapping("/api/partner/{partner_id}/orders")
    public ResponseEntity<?> getOrdersByPartner(@PathVariable(required = false) String partner_id) throws JsonProcessingException {

        logger.info("Started GET Orders by Partner");
        List<OrderResponseDto> orderList = customerOrdService.getOrdersByPartner(partner_id);
        System.out.println("Hit");
        Map<String, Object> model = new HashMap<>();
        model.put("details", orderList);
        model.put("success", "true");
        model.put("statusDetail", "Found " + orderList.size() + " orders");
        return ok(model);
    }


    @GetMapping("/api/partner/partnerDetails")
    public ResponseEntity<?> getPartnerDetails() throws JsonProcessingException {

        logger.info("Started GET Partner Details request");
        Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        PartnerDetails fetchedPartner = partnerService.queryPartner(fetchedUser.get());
        if (fetchedPartner != null) {
            Map<String, Object> model = new HashMap<>();
            model.put("partnerDetails", fetchedPartner);
            model.put("success", "true");
            model.put("statusDetail", "Found partner");
            return ok(model);
        } else {
            Map<String, Object> model = new HashMap<>();
            model.put("success", "false");
            model.put("statusDetail", "Partner not found");
            return ok(model);
        }
    }

    @GetMapping("/api/partner/partnerDetails/service/{s_id}")
    public ResponseEntity<?> getPartnerDetails(@PathVariable(required = true) String s_id) throws JsonProcessingException {

        logger.info("Started GET Partner Details request");
        Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        PartnerDetails fetchedPartner = partnerService.queryPartnerByService(fetchedUser.get(), s_id);
        Map<String, Object> model = new HashMap<>();
        model.put("partnerDetails", fetchedPartner);
        model.put("success", "true");
        model.put("statusDetail", "Found partner");
        return ok(model);
    }

    @GetMapping("/api/partners")
    public ResponseEntity<?> getAllPartners() throws JsonProcessingException {

        logger.info("Started GET all partners ");
        List<PartnerDetails> fetchedPartners = partnerService.getAllPartners();
        Map<String, Object> model = new HashMap<>();
        model.put("partnerDetails", fetchedPartners);
        model.put("success", "true");
        model.put("statusDetail", "Found " + fetchedPartners.size() + "partner(s)");
        return ok(model);
    }

    @GetMapping("/api/partners/service/{serviceId}/report")
    public ResponseEntity<?> getReportSummary(@PathVariable(required = true) String serviceId) throws JsonProcessingException {
        Map<String, Object> model = new HashMap<>();
        Optional<User> fetcheduser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        if (!fetcheduser.isPresent()) {
            logger.error("Invalid userName " + CommonUtils.getCurrentUserName());
            throw new ResourceNotFoundException("Invalid userName " + CommonUtils.getCurrentUserName());
        }
        Set<Role> roles = fetcheduser.get().getRoles();
        Iterator<Role> roleIterator = roles.iterator();
        while (roleIterator.hasNext()) {
            String roleName = roleIterator.next().getName();
            if (!roleName.equalsIgnoreCase("superuser") && !roleName.equalsIgnoreCase("service") && !roleName.equalsIgnoreCase("delivery")) {
                model.put("success", "false");
                model.put("statusDetail", "Not a service partner");
                return ok(model);
            }
        }

        List<Partner> fetchedPartner = servicePartnerRepository.getPartnerByUserId(fetcheduser.get().getUserId());
        if (fetchedPartner.isEmpty()) {
            model.put("success", "false");
            model.put("statusDetail", "Found 0 partners");
            return ok(model);
        }

        List<CustomerOrderDetails> fetchedOrders = customerOrderRepository.getOrdersByPartner(fetchedPartner.get(0).getPartnerId());
        if (fetchedOrders.isEmpty()) {
            model.put("success", "false");
            model.put("statusDetail", "Found 0 orders");
            return ok(model);
        }
        Map<Invoice, String> invoiceMap = new HashMap<>();
        for (CustomerOrderDetails fetchedOrder : fetchedOrders) {
            Invoice fetchedInvoice = invoiceRepository.getInvoiceByOrderId(fetchedOrder.getOrderId());
            if (fetchedInvoice == null) {
                logger.info("No invoices yet");
                break;
            }
            invoiceMap.put(fetchedInvoice, fetchedOrder.getOrderId());
        }
        long maxOrderTimeTaken = 0;
        long minOrderTimeTaken = 999999999;
        long avgOrderTimeTaken = 0;
        double maxInvoiceAmount = 0;
        double minInvoiceAmount = 99999999;
        double avgInvoiceAmount = 0;
        int totalOrderCount = fetchedOrders.size();
        int totalCancelledOrderCount = 0;
        int totalCompletedOrderCount = 0;
        int totalInProgressOrderCount = 0;
        List<PartnerOrderObject> partnerOrderObjectList = new ArrayList<>();
        try {
            for (CustomerOrderDetails order : fetchedOrders) {
                if (order.getOrderStatus().equalsIgnoreCase("cancelled")) {
                    totalCancelledOrderCount = totalCancelledOrderCount + 1;
                }
                if (order.getOrderStatus().equalsIgnoreCase("completed")) {
                    totalCompletedOrderCount = totalCompletedOrderCount + 1;
                }
                if (!order.getOrderStatus().equalsIgnoreCase("cancelled") &&
                        !order.getOrderStatus().equalsIgnoreCase("created")) {
                    totalInProgressOrderCount = totalInProgressOrderCount + 1;
                }
                PartnerOrderObject partnerOrderObject = new PartnerOrderObject();
                partnerOrderObject.setOrder_id(order.getOrderId());
                partnerOrderObject.setOrder_location(order.getLocation());
                ObjectMapper objectMapper = new ObjectMapper();
                OrderReqDto orderDetails = objectMapper.readValue(order.getOrderDetails(), OrderReqDto.class);
                partnerOrderObject.setOrder_payment_method(orderDetails.getoDetails().getPaymentMode());
                if (orderDetails.getoDetails().getInvoiceResDto() != null) {
                    partnerOrderObject.setOrder_invoice_amount(orderDetails.getoDetails().getInvoiceResDto().getTotalPayable());
                    if (orderDetails.getoDetails().getInvoiceResDto().getTotalPayable() > maxInvoiceAmount) {
                        maxInvoiceAmount = orderDetails.getoDetails().getInvoiceResDto().getTotalPayable();
                    }
                    if (orderDetails.getoDetails().getInvoiceResDto().getTotalPayable() < minInvoiceAmount) {
                        minInvoiceAmount = orderDetails.getoDetails().getInvoiceResDto().getTotalPayable();
                    }
                    avgInvoiceAmount = avgOrderTimeTaken + orderDetails.getoDetails().getInvoiceResDto().getTotalPayable();
                }
                partnerOrderObject.setOrder_status(order.getOrderStatus());
                if ((order.getCreatedAt().getTime() - order.getLastUpdatedAt().getTime()) > maxOrderTimeTaken) {
                    maxOrderTimeTaken = order.getCreatedAt().getTime() - order.getLastUpdatedAt().getTime();
                }
                if ((order.getCreatedAt().getTime() - order.getLastUpdatedAt().getTime()) < minOrderTimeTaken) {
                    minOrderTimeTaken = order.getCreatedAt().getTime() - order.getLastUpdatedAt().getTime();
                }
                avgOrderTimeTaken = avgOrderTimeTaken + (order.getCreatedAt().getTime() - order.getLastUpdatedAt().getTime());
                partnerOrderObject.setOrder_time_taken(String.valueOf((order.getCreatedAt().getTime() - order.getLastUpdatedAt().getTime())));
                partnerOrderObjectList.add(partnerOrderObject);
            }
            avgOrderTimeTaken = avgOrderTimeTaken / fetchedOrders.size();
            if (avgInvoiceAmount > 0) {
                avgInvoiceAmount = avgInvoiceAmount / fetchedOrders.size();
            }
            PartnerReport partnerReport = new PartnerReport();
            partnerReport.setOrder_list(partnerOrderObjectList);
            partnerReport.setAverage_order_time(String.valueOf(new Timestamp(avgOrderTimeTaken)));
            partnerReport.setMax_time(String.valueOf(new Timestamp(maxOrderTimeTaken)));
            partnerReport.setMin_time(String.valueOf(new Timestamp(minOrderTimeTaken)));
            partnerReport.setAverage_order_amount(avgInvoiceAmount);
            partnerReport.setMin_order_amount(minInvoiceAmount);
            partnerReport.setMax_order_amount(maxInvoiceAmount);
            partnerReport.setInprogress_order_count(totalInProgressOrderCount);
            partnerReport.setCancelled_order_count(totalCancelledOrderCount);
            partnerReport.setCompleted_order_count(totalCompletedOrderCount);
            partnerReport.setTotal_order_count(totalOrderCount);
            model.put("partnerReport", partnerReport);
            model.put("success", "true");
            model.put("statusDetail", "Created partner report");
            return ok(model);
        } catch (KorikeException e) {
            model.put("success", "false");
            model.put("statusDetail", "Unable to create partner report");
            return ok(model);
        }
    }

    @GetMapping("/api/partners/service/{serviceId}")
    public ResponseEntity<?> getAllPartnersByService(@PathVariable(required = true) String serviceId) throws JsonProcessingException {

        logger.info("Started GET all partners by Service");
        List<PartnerDetails> fetchedPartners = partnerService.getAllPartnersByService(serviceId);
        Map<String, Object> model = new HashMap<>();
        model.put("partnerDetails", fetchedPartners);
        model.put("success", "true");
        model.put("statusDetail", "Found partner");
        return ok(model);
    }


    @GetMapping("/api/partners/services")
    public ResponseEntity<?> getAllServicesByPartner() throws JsonProcessingException {

        logger.info("Started GET all services by Partner");
        Optional<User> fetcheduser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        if (!fetcheduser.isPresent()) {
            logger.error("Invalid userName " + CommonUtils.getCurrentUserName());
            throw new ResourceNotFoundException("Invalid userName " + CommonUtils.getCurrentUserName());
        }
        List<ServiceDefinition> fetchedServices = serviceDefintitionService.getRegisteredServicesByUser(fetcheduser.get().getUserId());
        if(!fetchedServices.isEmpty()) {
            Map<String, Object> model = new HashMap<>();
            model.put("serviceDetails", fetchedServices);
            model.put("success", "true");
            model.put("statusDetail", "Found services");
            return ok(model);
        }else{
            Map<String, Object> model = new HashMap<>();
            model.put("success", "false");
            model.put("statusDetail", "Found no services registered for user.");
            return ok(model);
            }
        }

}
