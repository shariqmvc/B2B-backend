package com.korike.logistics.service;

import java.util.List;
import java.util.Optional;

import com.korike.logistics.model.orders.OrderDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.korike.logistics.entity.Consumer;
import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.model.orders.OrderReqDto;
import com.korike.logistics.model.orders.OrderResponseDto;
import com.korike.logistics.model.orders.OrdersListReqDto;

@Service
public interface CustomerOrderService {

	public OrderResponseDto createOrder(String reqJson, OrderReqDto orderReq,Consumer fetchedConsumer, ServiceDefinition selService);
	public List<OrderResponseDto> getOrdersList(OrdersListReqDto orderReq) throws JsonMappingException, JsonProcessingException;
	public OrderResponseDto updateOrder(String reqJson, OrderReqDto orderReq,Consumer fetchedConsumer, ServiceDefinition selService,  CustomerOrderDetails fetchedOrder,String action);
	public CustomerOrderDetails getOrderByOid(String oid);
	public List<OrderResponseDto> getOrdersByPartner(String partner_id);
	public List<CustomerOrderDetails> fetchOpenOrderBySid(String sid);
	public List<CustomerOrderDetails> fetchOpenOrderBySidForPartner(String sid, String partner_id);
}
