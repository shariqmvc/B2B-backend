package com.korike.logistics.service.impl;

import com.korike.logistics.entity.PartnerDeclinedOrders;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.orders.OrderResponseDto;
import com.korike.logistics.repository.PartnerDeclinedOrdersRepo;
import com.korike.logistics.service.PartnerDeclinedOrdersService;
import com.korike.logistics.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;



@Service
public class PartnerDeclinedOrdersServiceImpl implements PartnerDeclinedOrdersService {
    private static Logger logger = Logger.getLogger(PartnerDeclinedOrdersServiceImpl.class);
        @Autowired
        PartnerDeclinedOrdersRepo partnerDeclinedOrdersRepo;

        @Override
        public PartnerDeclinedOrders entryPartnerDeclinedOrder(String orderId, String partnerId, String partnerLocation) {
            PartnerDeclinedOrders partnerDeclinedOrders = new PartnerDeclinedOrders();
            partnerDeclinedOrders.setDeclineId(UUID.randomUUID().toString());
            partnerDeclinedOrders.setOrderId(orderId);
            partnerDeclinedOrders.setPartnerId(partnerId);
            partnerDeclinedOrders.setPartnerLocation(partnerLocation);

            try {
                partnerDeclinedOrdersRepo.save(partnerDeclinedOrders);
                return partnerDeclinedOrders;
            }catch(Exception exc) {
                logger.error("Exception occured in createOrder() method." + CommonUtils.printException(exc));
                throw new KorikeException("Exception occured in entryPartnerDeclinedOrder() method while creating "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
            }
        }

        @Override
        public List<PartnerDeclinedOrders> getPartnerDeclinedOrderByOrder(String orderId) {
            List<PartnerDeclinedOrders> partnerDeclinedOrdersList = partnerDeclinedOrdersRepo.getDeclinedOrdersByOrder(orderId);
            return partnerDeclinedOrdersList;
        }
        public List<PartnerDeclinedOrders> getPartnerDeclinedOrderByPartner(String partnerId) {
            List<PartnerDeclinedOrders> partnerDeclinedOrdersList = partnerDeclinedOrdersRepo.getDeclinedOrdersByPartner(partnerId);
            return partnerDeclinedOrdersList;
        }
        public List<PartnerDeclinedOrders> getPartnerDeclinedOrderByOrderAndPartner(String orderId, String partnerId) {
            List<PartnerDeclinedOrders> partnerDeclinedOrdersList = partnerDeclinedOrdersRepo.getDeclinedOrdersByOrderAndPartner(orderId, partnerId);
            return partnerDeclinedOrdersList;
        }

    }
