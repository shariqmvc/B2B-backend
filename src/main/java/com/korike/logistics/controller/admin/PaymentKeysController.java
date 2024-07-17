package com.korike.logistics.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.common.Constants;
import com.korike.logistics.entity.*;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.CategoryDto;
import com.korike.logistics.model.ConsumerDetails;
import com.korike.logistics.model.UserInfoDto;
import com.korike.logistics.model.orders.OrderDetails;
import com.korike.logistics.model.orders.OrderItemsListDto;
import com.korike.logistics.model.orders.OrderReqDto;
import com.korike.logistics.model.payments.*;
import com.korike.logistics.repository.CustomerOrderRepository;
import com.korike.logistics.repository.PaymentInformationRepository;
import com.korike.logistics.repository.PaymentKeysRepository;
import com.korike.logistics.repository.UserRepository;
import com.korike.logistics.service.ConsumerService;
import com.korike.logistics.service.CustomerOrderService;
import com.korike.logistics.service.PaymentKeysService;
import com.korike.logistics.service.PaymentServices;
import com.korike.logistics.util.CommonUtils;
import com.korike.logistics.util.PaymentUtil;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.InvalidParameterException;
import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class PaymentKeysController {

    private static Logger logger = Logger.getLogger(PaymentKeysController.class);

    @Autowired
    PaymentKeysService paymentKeysService;
    @Autowired
    PaymentKeysRepository paymentKeysRepository;
    @Autowired
    CustomerOrderRepository customerOrderRepository;
    @Autowired
    CustomerOrderService customerOrderService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConsumerService consumerService;
    @Autowired
    PaymentInformationRepository paymentInformationRepository;
    @Autowired
    PaymentServices paymentServices;

    @RequestMapping(value={"/api/admin/paymentkey"}, method  = RequestMethod.POST)
    public ResponseEntity<?> createUpdatePaymentKey(@RequestParam( required = false, name="action") String action, @RequestBody PaymentKeysRequestResponseDto paymentKeysRequestResponseDto) throws JsonProcessingException {
        logger.info("Started create or update payment key method");
        Map<String, Object> model = new HashMap<>();
        if("add".equals(action)) {
            if(paymentKeysRequestResponseDto.getMerchantKey() == null || paymentKeysRequestResponseDto.getMerchantKey().isEmpty()) {
                throw new InvalidParameterException("Merchant Key is missing");
            }
            if(paymentKeysRequestResponseDto.getMerchantSalt() == null || paymentKeysRequestResponseDto.getMerchantSalt().isEmpty()) {
                throw new InvalidParameterException("Merchant Salt is missing");
            }
            if(paymentKeysRequestResponseDto.getPayment_gateway() == null || paymentKeysRequestResponseDto.getPayment_gateway().isEmpty()) {
                throw new InvalidParameterException("Payment Gateway is missing");
            }

            paymentKeysRequestResponseDto = paymentKeysService.createPaymentKeyEntry(paymentKeysRequestResponseDto);
            model.put("payment_keyId", paymentKeysRequestResponseDto.getId());
            model.put("job_id","default");
            model.put("statusDetail", "Payment key entry created successfully");
        }else if("edit".equals(action)) {
            if(paymentKeysRequestResponseDto.getId() == null) {
                throw new InvalidParameterException("Payment key ID is missing");
            }
            if(paymentKeysRequestResponseDto.getPayment_gateway() == null || paymentKeysRequestResponseDto.getPayment_gateway().isEmpty()) {
                throw new InvalidParameterException("Payment gateway is missing");
            }
            if(paymentKeysRequestResponseDto.getMerchantKey() == null || paymentKeysRequestResponseDto.getMerchantKey().isEmpty()) {
                throw new InvalidParameterException("Merchant Key is missing");
            }
            if(paymentKeysRequestResponseDto.getMerchantSalt() == null || paymentKeysRequestResponseDto.getMerchantSalt().isEmpty()) {
                throw new InvalidParameterException("Merchant Salt is missing");
            }
            Optional<PaymentKeys> fetchedPaymentKeyEntry = paymentKeysRepository.findById(String.valueOf(paymentKeysRequestResponseDto.getId()));
            if(!fetchedPaymentKeyEntry.isPresent()) {
                logger.error("Exception occured in Payment Key controller method.");
                throw new ResourceNotFoundException("Invalid payment key entry "+paymentKeysRequestResponseDto.getId());
            }
            paymentKeysRequestResponseDto = paymentKeysService.updatePaymentKeyEntry(paymentKeysRequestResponseDto, fetchedPaymentKeyEntry.get());

            model.put("payment_keyId", paymentKeysRequestResponseDto.getId());
            model.put("job_id","default");
            model.put("statusDetail", "Payment key entry updated successfully");
        }else if("query".equals(action)) {
            List<PaymentKeys> paymentKeysList  = paymentKeysRepository.findAll();
            List<PaymentKeysRequestResponseDto> paymentKeysRequestResponseDtoList = new ArrayList<>();
            for(PaymentKeys paymentKeys : paymentKeysList){
                PaymentKeysRequestResponseDto paymentKeysRequestResponseDto1 = new PaymentKeysRequestResponseDto();
                paymentKeysRequestResponseDto1.setId(paymentKeys.getId());
                paymentKeysRequestResponseDto1.setMerchantKey(paymentKeys.getKey());
                paymentKeysRequestResponseDto1.setMerchantSalt(paymentKeys.getSalt());
                paymentKeysRequestResponseDto1.setPayment_gateway(paymentKeys.getPayment_gateway());
                paymentKeysRequestResponseDtoList.add(paymentKeysRequestResponseDto1);
            }
            model.put("Payment Keys List", paymentKeysRequestResponseDtoList);
            model.put("job_id","default");
            model.put("statusDetail", "Found "+paymentKeysRequestResponseDtoList.size()+" payment key entries");
        }
        return ok(model);
    }

    @RequestMapping(value={"/api/consumer/proceedPayment"}, method  = RequestMethod.POST)
    public ResponseEntity<?> proceedPayment(@RequestBody PaymentHashDto paymentHashDto) throws JsonProcessingException, JSONException {
        Map<String, Object> model = new HashMap<>();
        Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        if (!fetchedUser.isPresent()) {
            logger.error("Invalid userName " + CommonUtils.getCurrentUserName());
            throw new ResourceNotFoundException("Invalid userName " + CommonUtils.getCurrentUserName());
        }

        Optional<CustomerOrderDetails> fetchedCustomerOrderDetails = customerOrderRepository.getOrderById(paymentHashDto.getOrder_id());
        if (!fetchedCustomerOrderDetails.isPresent()) {
            logger.error("Invalid userName " + CommonUtils.getCurrentUserName());
            throw new ResourceNotFoundException("Invalid userName " + CommonUtils.getCurrentUserName());
        }

        ObjectMapper orderDetailsMapper = new ObjectMapper();
        OrderReqDto orderDetails = orderDetailsMapper.readValue(fetchedCustomerOrderDetails.get().getOrderDetails(), OrderReqDto.class);
        double totalPayable = 0;
        ObjectMapper userDetailsMapper = new ObjectMapper();
        UserInfoDto userDetails = userDetailsMapper.readValue(fetchedUser.get().getUserDetails(), UserInfoDto.class);
        if(userDetails.getEmailId().isEmpty() || userDetails.getEmailId()==null){
            model.put("error","INCOMPLETE_PROFILE");
            model.put("statusDetail", "failure");
            return ok(model);
        }
        if (orderDetails.getoDetails().getInvoiceResDto() != null) {
            totalPayable = orderDetails.getoDetails().getInvoiceResDto().getTotalPayable();
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setAmount(String.valueOf(totalPayable));
            paymentModel.setEmail(userDetails.getEmailId());
            paymentModel.setfUrl(Constants.PAYMENT_FURL);
            paymentModel.setfUrl(Constants.PAYMENT_SURL);
            paymentModel.setName(userDetails.getFullName());
            ObjectMapper itemsMapper = new ObjectMapper();
            String itemsListDto = itemsMapper.writeValueAsString(orderDetails.getoDetails().getItems());
            paymentModel.setProductInfo(itemsListDto);
            paymentModel.setPhone(userDetails.getPhone());
            paymentModel.setTxnId(paymentHashDto.getTxnId());
            paymentModel.setKey(paymentKeysRepository.getPaymentKeyByGateway(paymentHashDto.getPayment_gateway()).get(0).getKey());
            paymentModel.setSalt(paymentKeysRepository.getPaymentKeyByGateway(paymentHashDto.getPayment_gateway()).get(0).getSalt());
            paymentModel = PaymentUtil.populatePaymentDetail(paymentModel);
            PaymentInformation paymentInformation = paymentInformationRepository.getPaymentInformationByTxId(paymentHashDto.getTxnId());
            paymentInformation = paymentServices.createUpdatePaymentInformationEntry(paymentModel, paymentInformation);
            PaymentInformationResponseDto paymentInformationResponseDto = new PaymentInformationResponseDto();
            paymentInformationResponseDto.setEmail(paymentInformation.getEmail());
            paymentInformationResponseDto.setName(paymentInformation.getName());
            paymentInformationResponseDto.setPhone(paymentInformation.getPhone());
            paymentInformationResponseDto.setAmount(paymentInformation.getAmount());
            paymentInformationResponseDto.setProduct_info(orderDetails.getoDetails().getItems());
            paymentInformationResponseDto.setMihpay_id(paymentInformation.getMihpayId());
            paymentInformationResponseDto.setHash(paymentModel.getHash());
            paymentInformationResponseDto.setPayment_status(paymentInformation.getPaymentStatus().name());
            paymentInformationResponseDto.setTxn_id(paymentInformation.getTxnId());
            paymentInformationResponseDto.setKey(paymentModel.getKey());
            //paymentInformationResponseDto.setMode(paymentInformation.getMode().name()==null ? "" : paymentInformation.getMode().name());
            model.put("paymentInformation", paymentInformationResponseDto);
            model.put("job_id", "default");
            model.put("statusDetail", "success");
            return ok(model);
            //return paymentDetail;
        } else {
            model.put("message", "Invalid invoice section");
            model.put("statusDetail", "failure");
            return ok(model);
        }
    }

    @RequestMapping(path = "/api/consumer/payment-response", method = RequestMethod.POST)
    public @ResponseBody String payuCallback(@RequestParam String mihpayid, @RequestParam String status, @RequestParam PaymentMode mode, @RequestParam String txnid, @RequestParam String hash){
        PaymentCallback paymentCallback = new PaymentCallback();
        paymentCallback.setMihpayid(mihpayid);
        paymentCallback.setTxnid(txnid);
        paymentCallback.setMode(mode);
        paymentCallback.setHash(hash);
        paymentCallback.setStatus(status);
        return paymentServices.payuCallback(paymentCallback);
    }



}
