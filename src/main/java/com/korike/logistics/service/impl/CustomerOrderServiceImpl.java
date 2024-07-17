package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.korike.logistics.entity.PartnerDeclinedOrders;
import com.korike.logistics.model.services.ServiceDetails;
import com.korike.logistics.repository.ServiceDefinitionRepository;
import com.korike.logistics.service.PartnerDeclinedOrdersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.korike.logistics.common.OrderStatus;
import com.korike.logistics.entity.Consumer;
import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.orders.OrderReqDto;
import com.korike.logistics.model.orders.OrderResponseDto;
import com.korike.logistics.model.orders.OrdersListReqDto;
import com.korike.logistics.repository.ConsumerRepository;
import com.korike.logistics.repository.CustomerOrderRepository;
import com.korike.logistics.service.CustomerOrderService;
import com.korike.logistics.util.CommonUtils;
import com.korike.logistics.util.CoreUtil;

import javax.swing.text.html.Option;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService{
	private static Logger logger = Logger.getLogger(CustomerOrderServiceImpl.class);

	@Autowired
	CustomerOrderRepository custOrdRepo;
	@Autowired
	ConsumerRepository consumerRepo;
	@Autowired
	ServiceDefinitionRepository serviceDefinitionRepository;
	@Autowired
	PartnerDeclinedOrdersService partnerDeclinedOrdersService;

	@Override
	public OrderResponseDto createOrder(String detailJson, OrderReqDto orderReq, Consumer fetchedConsumer, ServiceDefinition selService) {

		/*Just for testing without the token*/
		//	fetchedConsumer = consumerRepo.findAll().get(0);

		CustomerOrderDetails newCustOrd = new CustomerOrderDetails();
		newCustOrd.setOrderId(CommonUtils.generateOrdNum());
		newCustOrd.setConusmer(fetchedConsumer);
		newCustOrd.setOrderDetails(detailJson);
		newCustOrd.setLocation(orderReq.getoDetails().getSourceLocation());
		newCustOrd.setOrderStatus(OrderStatus.CREATED.name());
		newCustOrd.setServiceDefintion(selService);
		newCustOrd.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newCustOrd.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
		newCustOrd.setCreatedBy(fetchedConsumer.getConsumerId());
		//newCustOrd.setCreatedBy("dummy user");

		try {
			newCustOrd = custOrdRepo.save(newCustOrd);
			OrderResponseDto orderResponse = new OrderResponseDto();
			orderResponse.setOrderId(newCustOrd.getOrderId());
			return orderResponse;
		}catch(Exception exc) {
			logger.error("Exception occured in createOrder() method." + CommonUtils.printException(exc));
			throw new KorikeException("Exception occured in createOperationOrder() method while creating "+ApiErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<OrderResponseDto> getOrdersList(OrdersListReqDto orderReq) throws JsonMappingException, JsonProcessingException {

		List<CustomerOrderDetails> getAllOrders = new ArrayList();
		String stDate = "";
		String endDate = "";
		String status = "";
		if(orderReq.getFilter() != null && ((orderReq.getStartTimeStamp() != null && !orderReq.getStartTimeStamp().isEmpty()) && (orderReq.getEndTimeStamp() != null && !orderReq.getEndTimeStamp().isEmpty()))) {
			status = orderReq.getFilter().getStatus();
			stDate = orderReq.getStartTimeStamp();
			endDate = orderReq.getEndTimeStamp();

			//getAllOrders =  custOrdRepo.getOrdersByDateAndStatus(stDate, endDate, status);
			getAllOrders = custOrdRepo.findByCreatedAtBetweenAndOrderStatus(CommonUtils.convertStringToTimestamp(stDate), CommonUtils.convertStringToTimestamp(endDate),status);

		}else if(((orderReq.getStartTimeStamp() != null && !orderReq.getStartTimeStamp().isEmpty()) && (orderReq.getEndTimeStamp() != null && !orderReq.getEndTimeStamp().isEmpty())) && orderReq.getFilter() == null) {
			stDate = orderReq.getStartTimeStamp();
			endDate = orderReq.getEndTimeStamp();

			//getAllOrders = custOrdRepo.getOrdersByDate(stDate, endDate);
			getAllOrders = custOrdRepo.findByCreatedAtBetween(CommonUtils.convertStringToTimestamp(stDate), CommonUtils.convertStringToTimestamp(endDate));
		}else if(orderReq.getFilter() != null && ((orderReq.getStartTimeStamp() == null || orderReq.getStartTimeStamp().isEmpty()) && (orderReq.getEndTimeStamp() == null || orderReq.getEndTimeStamp().isEmpty()))) {
			status = orderReq.getFilter().getStatus();

			getAllOrders = custOrdRepo.getOrdersByStatus(status);
		}else if(orderReq.getFilter() == null && ((orderReq.getStartTimeStamp() == null || orderReq.getStartTimeStamp().isEmpty()) && (orderReq.getEndTimeStamp() == null || orderReq.getEndTimeStamp().isEmpty() ))) {
			getAllOrders = custOrdRepo.getOrders();
		}

		return getAllOrders.stream().map(
				o -> {

					return new OrderResponseDto(
							o.getOrderId(),
							"",
							CoreUtil.getOrderDetails(o.getOrderDetails()),
							o.getOrderStatus()
					);

				}
		).collect(Collectors.toList());

	}

	public List<OrderResponseDto> getOrdersByPartner(String partner_id){
		List<CustomerOrderDetails> getAllOrders = new ArrayList();
		String stDate = "";
		String endDate = "";
		String status = "";

		getAllOrders = custOrdRepo.getOrdersByPartner(partner_id);
		return getAllOrders.stream().map(
				o -> {

					return new OrderResponseDto(
							o.getOrderId(),
							"",
							CoreUtil.getOrderDetails(o.getOrderDetails()),
							o.getOrderStatus()
					);

				}
		).collect(Collectors.toList());
	}

	@Override
	public OrderResponseDto updateOrder(String reqJson, OrderReqDto orderReq, Consumer fetchedConsumer,
										ServiceDefinition selService, CustomerOrderDetails fetchedOrder, String action) {

		if("edit".equals(action)) {
			fetchedOrder.setLocation(orderReq.getoDetails().getSourceLocation());
			fetchedOrder.setOrderDetails(reqJson);
			fetchedOrder.setLocation(orderReq.getoDetails().getSourceLocation());
			fetchedOrder.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
			fetchedOrder.setLastUpdatedBy(fetchedConsumer.getConsumerId());
		}else {
			fetchedOrder.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
			fetchedOrder.setOrderStatus(OrderStatus.CANCELLED.name());
		}

		try {
			fetchedOrder = custOrdRepo.save(fetchedOrder);
			OrderResponseDto orderResponse = new OrderResponseDto();
			orderResponse.setOrderId(fetchedOrder.getOrderId());
			return orderResponse;
		}catch(Exception exc) {
			logger.error("Exception occured in updateOrder() method." + CommonUtils.printException(exc));
			throw new KorikeException("Exception occured in updateOrder() method while updating "+ApiErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public CustomerOrderDetails getOrderByOid(String oid) {

		Optional<CustomerOrderDetails> fetchedOrder = custOrdRepo.findById(oid);
		if(!fetchedOrder.isPresent()) {
			logger.error("Invalid o_id " +oid);
			throw new ResourceNotFoundException("Invalid o_id " +oid);
		}
		return fetchedOrder.get();
	}

	@Override
	public List<CustomerOrderDetails> fetchOpenOrderBySid(String sid) {

		Optional<ServiceDefinition> serviceDefinition =  serviceDefinitionRepository.findById(sid);
		if(serviceDefinition.get().getServiceId().isEmpty()) {
			logger.error("Invalid s_id " +sid);
			throw new ResourceNotFoundException("Invalid s_id " +sid);
		}
		List<CustomerOrderDetails> fetchedOrders = custOrdRepo.getOpenOrdersBySid(sid);

		return fetchedOrders;
	}

	@Override
		public List<CustomerOrderDetails> fetchOpenOrderBySidForPartner(String sid, String partner_id) {

		Optional<ServiceDefinition> serviceDefinition =  serviceDefinitionRepository.findById(sid);
		if(serviceDefinition.get().getServiceId().isEmpty()) {
			logger.error("Invalid s_id " +sid);
			throw new ResourceNotFoundException("Invalid s_id " +sid);
		}
		List<CustomerOrderDetails> fetchedOrders = custOrdRepo.getOpenOrdersBySid(sid);
		List<PartnerDeclinedOrders> partnerDeclinedOrdersList = partnerDeclinedOrdersService.getPartnerDeclinedOrderByPartner(partner_id);
		List<CustomerOrderDetails> ordersToRemove = new ArrayList<>();

		//If PartnerDeclinedOrders is not empty compare with fetchedOrders and remove from list if present.
		for (int i = 0; i < fetchedOrders.size(); i++) {
			for (PartnerDeclinedOrders partnerDeclinedOrders : partnerDeclinedOrdersList) {
				try {
					if (partnerDeclinedOrders.getOrderId().equalsIgnoreCase(fetchedOrders.get(i).getOrderId())) {
						ordersToRemove.add(fetchedOrders.get(i));
					}
				}catch(Exception e){
					logger.info(e);
				}
			}
		}
		for(CustomerOrderDetails order:ordersToRemove){
			fetchedOrders.remove(order);
		}
		return fetchedOrders;
	}
}
