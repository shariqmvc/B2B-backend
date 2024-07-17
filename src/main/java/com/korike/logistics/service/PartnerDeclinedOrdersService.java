package com.korike.logistics.service;

import com.korike.logistics.entity.PartnerDeclinedOrders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PartnerDeclinedOrdersService {

    public PartnerDeclinedOrders entryPartnerDeclinedOrder(String orderId, String partnerId, String partnerLocation);
    public List<PartnerDeclinedOrders> getPartnerDeclinedOrderByOrder(String orderId);
    public List<PartnerDeclinedOrders> getPartnerDeclinedOrderByPartner(String partnerId);
    public List<PartnerDeclinedOrders> getPartnerDeclinedOrderByOrderAndPartner(String orderId, String partnerId);
    
}
