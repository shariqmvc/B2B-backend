package com.korike.logistics.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.entity.Consumer;
import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.entity.User;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.ConsumerAddresses;
import com.korike.logistics.model.ConsumerDetails;
import com.korike.logistics.model.orders.OrderReqDto;
import com.korike.logistics.repository.ConsumerRepository;
import com.korike.logistics.service.ConsumerService;
import com.korike.logistics.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    private static Logger logger = Logger.getLogger(ConsumerServiceImpl.class);

    @Autowired
    ConsumerRepository consumerRepo;
    @Override
    public Consumer createConsumer(User user) {

        Consumer consumer = new Consumer();
        try {
            consumer.setUser(user);
            consumer.setConsumerId(CommonUtils.generateOrdNum());
            consumer.setIsActive(true);
            consumer.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            consumer.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
            consumer.setCreatedBy(user.getUserId());
            consumer = consumerRepo.save(consumer);
            return consumer;
        }
        catch(Exception exc){
            logger.error("Exception occured in createConsumer() method." + CommonUtils.printException(exc));
            throw new KorikeException("Exception occured in createConsumer() method while creating "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Consumer updateConsumer(CustomerOrderDetails customerOrderDetails, Consumer consumer, User user, String consumerStatus) {
        try {
            consumer.setOrderDetails(customerOrderDetails);
            consumer.setConsumerStatus(consumerStatus);
            consumer.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
            consumer.setCreatedBy(user.getUserId());
            consumer = consumerRepo.save(consumer);
            return consumer;
        }
        catch(Exception exc){
            logger.error("Exception occured in createConsumer() method." + CommonUtils.printException(exc));
            throw new KorikeException("Exception occured in createConsumer() method while creating "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Consumer createConsumerProfile(ConsumerDetails consumerDetails, User user) throws JsonProcessingException {

        try {
            Consumer fetchedConsumer = consumerRepo.findByUser(user);
            for(ConsumerAddresses consumerAddress: consumerDetails.getConsumerAddresses()){
                consumerAddress.setConsumerId(fetchedConsumer.getConsumerId());
            }
            ObjectMapper mapper = new ObjectMapper();
            String fetchedConsumerDetails = mapper.writeValueAsString(consumerDetails);
            fetchedConsumer.setConsumerDetails(fetchedConsumerDetails);
            fetchedConsumer = consumerRepo.save(fetchedConsumer);
            return fetchedConsumer;
        }
        catch(Exception exc){
            logger.error("Exception occured in createConsumer() method." + CommonUtils.printException(exc));
            throw new KorikeException("Exception occured in createConsumer() method while creating "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ConsumerDetails queryConsumerProfile(User user) {
        try {
            Consumer consumer = consumerRepo.findByUser(user);
            if(null!=consumer.getConsumerDetails()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(consumer.getConsumerDetails(), ConsumerDetails.class);
            }
            return null;
        }
        catch(Exception exc){
            logger.error("Exception occured in createConsumer() method." + CommonUtils.printException(exc));
            throw new KorikeException("Exception occured in createConsumer() method while creating "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
