package com.korike.logistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.korike.logistics.entity.Consumer;
import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.entity.User;
import com.korike.logistics.model.ConsumerDetails;
import com.korike.logistics.model.orders.OrderReqDto;
import org.springframework.stereotype.Service;

@Service
public interface ConsumerService {
    public Consumer createConsumer(User user);
    public Consumer updateConsumer(CustomerOrderDetails customerOrderDetails, Consumer consumer, User user, String consumerStatus);
    Consumer createConsumerProfile(ConsumerDetails consumerDetails,User user) throws JsonProcessingException;
    ConsumerDetails queryConsumerProfile(User user);
}
