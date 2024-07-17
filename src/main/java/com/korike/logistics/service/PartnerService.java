package com.korike.logistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.entity.User;
import com.korike.logistics.model.orders.OrderResponseDto;
import com.korike.logistics.model.partner.PartnerDetails;
import com.korike.logistics.model.partner.ServicePartnerReqDto;
import com.korike.logistics.model.partner.ServicePartnerResponseDto;
import com.korike.logistics.model.services.ServiceDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PartnerService {

    public PartnerDetails queryPartnerByService(User user, String s_id) throws JsonProcessingException;
    public ServicePartnerResponseDto createPartner(ServicePartnerReqDto servicePartnerReqDto, User user, ServiceDefinition serviceDefinition) throws JsonProcessingException;
    public ServicePartnerResponseDto updatePartner(Partner fetchedServicePartner, ServicePartnerReqDto servicePartnerReqDto, User user, ServiceDefinition serviceDefinition) throws JsonProcessingException;
    public PartnerDetails queryPartner(User user) throws JsonProcessingException;
    public ServicePartnerResponseDto updatePartnerLocation(Partner fetchedServicePartner, ServicePartnerReqDto servicePartnerReqDto, User user, ServiceDefinition serviceDefinition) throws JsonProcessingException;
    public List<PartnerDetails> getAllPartners() throws JsonProcessingException;
    public List<PartnerDetails> getAllPartnersByService(String serviceId) throws JsonProcessingException;
    List<OrderResponseDto> getOrdersListByPartner(String partnerId) throws JsonMappingException, JsonProcessingException;
}
