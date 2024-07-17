package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.entity.BillingTemplate;
import com.korike.logistics.entity.Commission;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.Promo;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.BillingModelDto;
import com.korike.logistics.repository.BillingTemplateRepository;
import com.korike.logistics.service.BillingModelService;
import com.korike.logistics.util.CommonUtils;
@Service
public class BillingModelServiceImpl implements BillingModelService{
	
	private static Logger logger = Logger.getLogger(BillingModelServiceImpl.class);
	
	@Autowired
	BillingTemplateRepository billTempRepository;

	@Override
	public BillingModelDto createBillingModel(BillingModelDto billingModel, Partner fetchedPartner, Promo fetchedPromo, Commission fetchedComm) {
		logger.info("Started createBillingModel() method ");
		ObjectMapper objectMapper = new ObjectMapper();
		
		BillingTemplate newBillTemplate = new BillingTemplate();
		newBillTemplate.setCommission(fetchedComm);
		newBillTemplate.setPromo(fetchedPromo);
		if(fetchedPartner!=null) {
			newBillTemplate.setPartner(fetchedPartner);
		}
		
		newBillTemplate.setType(billingModel.getType());
		newBillTemplate.setUnit(billingModel.getUnit());
		newBillTemplate.setIsActive(true);
		newBillTemplate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newBillTemplate.setCreatedBy(CommonUtils.getCurrentUserName());
		try {
			newBillTemplate.setWholeSaleLimits(objectMapper.writeValueAsString(billingModel.getWholeSaleLimits()));
		} catch (JsonProcessingException e) {
			
			logger.error("Exception occured in createBillingModel() while processing json. "+CommonUtils.printException(e));
		}
		
		try {
			newBillTemplate  =billTempRepository.save(newBillTemplate);
			billingModel.setBillingId(newBillTemplate.getBillingId());
		}catch(Exception exc) {
			logger.error("Exception occured in createBillingModel() method. "+CommonUtils.printException(exc));
			throw new KorikeException("Internal Server Error");
		}
		
		return billingModel;
	}

	@Override
	public BillingModelDto updateBillingModel(BillingModelDto billingModel, Partner fetchedPartner, Promo fetchedPromo,
			Commission fetchedComm, BillingTemplate fetchedBillTemplate) {
		logger.info("Started updateBillingModel() method ");
        ObjectMapper objectMapper = new ObjectMapper();
		
		fetchedBillTemplate.setBillingId(billingModel.getBillingId());
        fetchedBillTemplate.setCommission(fetchedComm);
        fetchedBillTemplate.setPromo(fetchedPromo);
		if(fetchedPartner!=null) {
			fetchedBillTemplate.setPartner(fetchedPartner);
		}
		
		fetchedBillTemplate.setType(billingModel.getType());
		fetchedBillTemplate.setUnit(billingModel.getUnit());
		fetchedBillTemplate.setIsActive(true);
		fetchedBillTemplate.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
		fetchedBillTemplate.setLastUpdatedBy(CommonUtils.getCurrentUserName());
		try {
			fetchedBillTemplate.setWholeSaleLimits(objectMapper.writeValueAsString(billingModel.getWholeSaleLimits()));
		} catch (JsonProcessingException e) {
			
			logger.error("Exception occured in createBillingModel() while processing json. "+CommonUtils.printException(e));
		}
		
		try {
			fetchedBillTemplate  =billTempRepository.save(fetchedBillTemplate);
			billingModel.setBillingId(fetchedBillTemplate.getBillingId());
		}catch(Exception exc) {
			logger.error("Exception occured in updateBillingModel() method. "+CommonUtils.printException(exc));
			throw new KorikeException("Internal Server Error");
		}
		
		return billingModel;
	}
}
