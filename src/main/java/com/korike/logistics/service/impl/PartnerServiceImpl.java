package com.korike.logistics.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.common.PartnerStatus;
import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.entity.User;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.PartnerLocation;
import com.korike.logistics.model.orders.OrderResponseDto;
import com.korike.logistics.model.orders.OrdersListReqDto;
import com.korike.logistics.model.partner.PartnerContact;
import com.korike.logistics.model.partner.PartnerDetails;
import com.korike.logistics.model.partner.ServicePartnerReqDto;
import com.korike.logistics.model.partner.ServicePartnerResponseDto;
import com.korike.logistics.model.services.ServiceDetails;
import com.korike.logistics.repository.ConsumerRepository;
import com.korike.logistics.repository.CustomerOrderRepository;
import com.korike.logistics.repository.ServicePartnerRepository;
import com.korike.logistics.service.PartnerService;
import com.korike.logistics.util.CommonUtils;
import com.korike.logistics.util.CoreUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartnerServiceImpl implements PartnerService {
    private static Logger logger = Logger.getLogger(PartnerServiceImpl.class);

    @Autowired
    ServicePartnerRepository servicePartnerRepository;

    @Override
    public ServicePartnerResponseDto createPartner(ServicePartnerReqDto servicePartnerReqDto, User user, ServiceDefinition serviceDefinition) throws JsonProcessingException {

        Partner newPartner = new Partner();
        newPartner.setUser(user);
        newPartner.setServiceDefintion(serviceDefinition);
        newPartner.setPartnerId(CommonUtils.generateOrdNum());
        newPartner.setPartnerStatus(PartnerStatus.OFFLINE.toString());
        newPartner.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        newPartner.setCreatedBy(user.getUserName());
        newPartner.setPartnerReg(servicePartnerReqDto.getPartner().getPartnerReg());
        newPartner.setIsActive(false);
        newPartner.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        newPartner.setLastUpdatedBy(user.getUserName());
        newPartner.setPartnerType(servicePartnerReqDto.getPartner().getPartnerType());
        newPartner.setPartnerName(servicePartnerReqDto.getPartner().getPartnerName());
        newPartner.setPartnerFiles(servicePartnerReqDto.getPartner().getPartnerFiles());
        ObjectMapper partnerContactToString = new ObjectMapper();
        newPartner.setPartnerContact(partnerContactToString.writeValueAsString(servicePartnerReqDto.getPartner().getPartnerContact()));
        ObjectMapper partnerLocationToString = new ObjectMapper();
        newPartner.setPartnerLocation(partnerLocationToString.writeValueAsString(servicePartnerReqDto.getPartner().getPartnerLocation()));
        newPartner.setPartnerDescription(servicePartnerReqDto.getPartner().getPartnerDescription());
        newPartner.setPartnerPan(servicePartnerReqDto.getPartner().getPartnerPan());
        newPartner.setPartnerGst(servicePartnerReqDto.getPartner().getPartnerGst());

        try {
            newPartner = servicePartnerRepository.save(newPartner);
            ServicePartnerResponseDto partnerResponse = new ServicePartnerResponseDto();
            partnerResponse.setPartnerId(newPartner.getPartnerId());
            return partnerResponse;
        }catch(Exception exc) {
            logger.error("Exception occured in createPartner() method." + CommonUtils.printException(exc));
            throw new KorikeException("Exception occured in createPartner() method while creating "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public ServicePartnerResponseDto updatePartnerLocation(Partner fetchedServicePartner, ServicePartnerReqDto servicePartnerReqDto, User user, ServiceDefinition serviceDefinition) throws JsonProcessingException {
        Partner editPartner = fetchedServicePartner;
        ObjectMapper partnerLocationToString = new ObjectMapper();
        if(servicePartnerReqDto.getPartner().getPartnerLocation()!=null) {
            editPartner.setPartnerLocation(partnerLocationToString.writeValueAsString(servicePartnerReqDto.getPartner().getPartnerLocation()));
        }
        try {
            editPartner = servicePartnerRepository.save(editPartner);
            ServicePartnerResponseDto partnerResponse = new ServicePartnerResponseDto();
            partnerResponse.setPartnerId(editPartner.getPartnerId());
            return partnerResponse;
        }catch(Exception exc) {
            logger.error("Exception occured in updatePartner() method." + CommonUtils.printException(exc));
            throw new KorikeException("Exception occured in updatePartner() method while updating "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


        @Override
    public ServicePartnerResponseDto updatePartner(Partner fetchedServicePartner, ServicePartnerReqDto servicePartnerReqDto, User user, ServiceDefinition serviceDefinition) throws JsonProcessingException {

        Partner editPartner = fetchedServicePartner;
        editPartner.setServiceDefintion(serviceDefinition);
        editPartner.setPartnerStatus((servicePartnerReqDto.getPartner().getPartnerStatus() == null || servicePartnerReqDto.getPartner().getPartnerStatus().isEmpty()) ? fetchedServicePartner.getPartnerStatus() : servicePartnerReqDto.getPartner().getPartnerStatus());
        editPartner.setIsActive(servicePartnerReqDto.getPartner().getActive() == null ? fetchedServicePartner.getIsActive(): servicePartnerReqDto.getPartner().getActive());
        editPartner.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        editPartner.setLastUpdatedBy(user.getUserName());
        editPartner.setPartnerType((servicePartnerReqDto.getPartner().getPartnerType() == null || servicePartnerReqDto.getPartner().getPartnerType().isEmpty()) ? fetchedServicePartner.getPartnerType() : servicePartnerReqDto.getPartner().getPartnerType());
        editPartner.setPartnerReg((servicePartnerReqDto.getPartner().getPartnerReg() == null || servicePartnerReqDto.getPartner().getPartnerReg().isEmpty()) ? fetchedServicePartner.getPartnerReg() : servicePartnerReqDto.getPartner().getPartnerReg());
        editPartner.setPartnerName((servicePartnerReqDto.getPartner().getPartnerName()==null || servicePartnerReqDto.getPartner().getPartnerName().isEmpty()) ? fetchedServicePartner.getPartnerName() : servicePartnerReqDto.getPartner().getPartnerName());
        if(servicePartnerReqDto.getPartner().getPartnerFiles() !=null) {
            editPartner.setPartnerFiles(servicePartnerReqDto.getPartner().getPartnerFiles());
        }
        if(servicePartnerReqDto.getPartner().getPartnerContact() != null) {
            ObjectMapper partnerContactToString = new ObjectMapper();
            editPartner.setPartnerContact(partnerContactToString.writeValueAsString(servicePartnerReqDto.getPartner().getPartnerContact()));
        }
        if(servicePartnerReqDto.getPartner().getPartnerLocation() != null ) {
                ObjectMapper partnerLocationToString = new ObjectMapper();
                editPartner.setPartnerLocation(partnerLocationToString.writeValueAsString(servicePartnerReqDto.getPartner().getPartnerLocation()));
        }
        editPartner.setPartnerDescription((servicePartnerReqDto.getPartner().getPartnerDescription() == null || servicePartnerReqDto.getPartner().getPartnerDescription().isEmpty()) ? fetchedServicePartner.getPartnerDescription() : servicePartnerReqDto.getPartner().getPartnerDescription());
        editPartner.setPartnerPan((servicePartnerReqDto.getPartner().getPartnerPan() == null || servicePartnerReqDto.getPartner().getPartnerPan().isEmpty()) ? fetchedServicePartner.getPartnerPan() : servicePartnerReqDto.getPartner().getPartnerPan());
        editPartner.setPartnerGst((servicePartnerReqDto.getPartner().getPartnerGst() == null || servicePartnerReqDto.getPartner().getPartnerGst().isEmpty()) ? fetchedServicePartner.getPartnerGst() : servicePartnerReqDto.getPartner().getPartnerGst());
        try {
            editPartner = servicePartnerRepository.save(editPartner);
            ServicePartnerResponseDto partnerResponse = new ServicePartnerResponseDto();
            partnerResponse.setPartnerId(editPartner.getPartnerId());
            return partnerResponse;
        }catch(Exception exc) {
            logger.error("Exception occured in updatePartner() method." + CommonUtils.printException(exc));
            throw new KorikeException("Exception occured in updatePartner() method while updating "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PartnerDetails queryPartner(User user) throws JsonProcessingException {

        List<Partner> partner = servicePartnerRepository.getPartnerByUserId(user.getUserId());
        List<String> partner_files = servicePartnerRepository.getPartnerFiles(partner.get(0).getPartnerId());
        if(partner.size()!=0) {
            PartnerDetails partnerDetails = new PartnerDetails();
            partnerDetails.setPartnerId(partner.get(0).getPartnerId());
            partnerDetails.setActive(partner.get(0).getActive());
            partnerDetails.setPartnerSid(partner.get(0).getServiceDefintion().getServiceId());
            partnerDetails.setPartnerDescription(partner.get(0).getPartnerDescription());
            partnerDetails.setPartnerFiles(partner_files);
            partnerDetails.setPartnerGst(partner.get(0).getPartnerGst());
            partnerDetails.setPartnerReg(partner.get(0).getPartnerReg());
            ObjectMapper partnerLocationRead = new ObjectMapper();
            partnerDetails.setPartnerLocation(partnerLocationRead.readValue(partner.get(0).getPartnerLocation(), PartnerLocation.class));
            partnerDetails.setPartnerName(partner.get(0).getPartnerName());
            partnerDetails.setPartnerPan(partner.get(0).getPartnerPan());
            partnerDetails.setPartnerStatus(partner.get(0).getPartnerStatus());
            partnerDetails.setPartnerType(partner.get(0).getPartnerType());
            return partnerDetails;
        }
        else{
            return null;
        }
    }

    @Override
    public PartnerDetails queryPartnerByService(User user, String s_id) throws JsonProcessingException {

        List<Partner> partner = servicePartnerRepository.getPartnersByUserByService(s_id, user.getUserId());
        List<String> partner_files = servicePartnerRepository.getPartnerFiles(partner.get(0).getPartnerId());
        PartnerDetails partnerDetails = new PartnerDetails();
        partnerDetails.setPartnerId(partner.get(0).getPartnerId());
        partnerDetails.setActive(partner.get(0).getActive());
        partnerDetails.setPartnerSid(partner.get(0).getServiceDefintion().getServiceId());
        partnerDetails.setPartnerDescription(partner.get(0).getPartnerDescription());
        partnerDetails.setPartnerFiles(partner_files);
        partnerDetails.setPartnerGst(partner.get(0).getPartnerGst());
        ObjectMapper partnerContactRead = new ObjectMapper();
        partnerDetails.setPartnerContact(partnerContactRead.readValue(partner.get(0).getPartnerContact(),PartnerContact.class));
        ObjectMapper partnerLocationRead = new ObjectMapper();
        partnerDetails.setPartnerLocation(partnerLocationRead.readValue(partner.get(0).getPartnerLocation(), PartnerLocation.class));
        partnerDetails.setPartnerName(partner.get(0).getPartnerName());
        partnerDetails.setPartnerReg(partner.get(0).getPartnerReg());
        partnerDetails.setPartnerPan(partner.get(0).getPartnerPan());
        partnerDetails.setPartnerStatus(partner.get(0).getPartnerStatus());
        partnerDetails.setPartnerType(partner.get(0).getPartnerType());
        return partnerDetails;
    }

    @Override
    public List<PartnerDetails> getAllPartners() throws JsonProcessingException {
        List<Partner> partnerList = servicePartnerRepository.findAll();
        List<PartnerDetails> partnerDetailsList = new ArrayList<>();
        for(Partner partner : partnerList){
            PartnerDetails partnerDetails = new PartnerDetails();
            partnerDetails.setPartnerId(partner.getPartnerId());
            partnerDetails.setActive(partner.getActive());
            partnerDetails.setPartnerSid(partner.getServiceDefintion().getServiceId());
            partnerDetails.setPartnerDescription(partner.getPartnerDescription());
            List<String> partner_files = servicePartnerRepository.getPartnerFiles(partner.getPartnerId());
            partnerDetails.setPartnerFiles(partner_files);
            partnerDetails.setPartnerGst(partner.getPartnerGst());
            partnerDetails.setPartnerReg(partner.getPartnerReg());
            ObjectMapper partnerLocationRead = new ObjectMapper();
            partnerDetails.setPartnerLocation(partnerLocationRead.readValue(partner.getPartnerLocation(), PartnerLocation.class));
            ObjectMapper partnerContactRead = new ObjectMapper();
            partnerDetails.setPartnerContact(partnerContactRead.readValue(partner.getPartnerContact(), PartnerContact.class));
            partnerDetails.setPartnerName(partner.getPartnerName());
            partnerDetails.setPartnerPan(partner.getPartnerPan());
            partnerDetails.setPartnerStatus(partner.getPartnerStatus());
            partnerDetails.setPartnerType(partner.getPartnerType());
            partnerDetailsList.add(partnerDetails);
        }
        return partnerDetailsList;

    }

    @Override
    public List<PartnerDetails> getAllPartnersByService(String serviceId) throws JsonProcessingException {

        List<Partner> partnerList = servicePartnerRepository.getPartnersByService(serviceId);
        List<PartnerDetails> partnerDetailsList = new ArrayList<>();
        for(Partner partner : partnerList){
            PartnerDetails partnerDetails = new PartnerDetails();
            partnerDetails.setPartnerId(partner.getPartnerId());
            partnerDetails.setActive(partner.getActive());
            partnerDetails.setPartnerSid(partner.getServiceDefintion().getServiceId());
            partnerDetails.setPartnerDescription(partner.getPartnerDescription());
            List<String> partner_files = servicePartnerRepository.getPartnerFiles(partner.getPartnerId());
            partnerDetails.setPartnerFiles(partner_files);
            partnerDetails.setPartnerGst(partner.getPartnerGst());
            partnerDetails.setPartnerReg(partner.getPartnerReg());
            ObjectMapper partnerLocationRead = new ObjectMapper();
            partnerDetails.setPartnerLocation(partnerLocationRead.readValue(partner.getPartnerLocation(), PartnerLocation.class));
            partnerDetails.setPartnerName(partner.getPartnerName());
            partnerDetails.setPartnerPan(partner.getPartnerPan());
            partnerDetails.setPartnerStatus(partner.getPartnerStatus());
            partnerDetails.setPartnerType(partner.getPartnerType());
            partnerDetailsList.add(partnerDetails);
        }
        return partnerDetailsList;

    }

    @Override
    public List<OrderResponseDto> getOrdersListByPartner(String partnerId) throws JsonMappingException, JsonProcessingException
    {
        List<CustomerOrderDetails> getAllOrders = null;

        getAllOrders = servicePartnerRepository.getOrdersByPartner(partnerId);

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
}
