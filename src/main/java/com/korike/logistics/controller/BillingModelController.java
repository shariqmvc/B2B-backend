package com.korike.logistics.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.security.InvalidParameterException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.model.WholeSaleLimits;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.korike.logistics.entity.BillingTemplate;
import com.korike.logistics.entity.Commission;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.Promo;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.BillingModelDto;
import com.korike.logistics.repository.BillingTemplateRepository;
import com.korike.logistics.repository.CommissionRepository;
import com.korike.logistics.repository.PartnerRepository;
import com.korike.logistics.repository.PromoRepository;
import com.korike.logistics.service.BillingModelService;

@RestController
@RequestMapping("/api/common")
public class BillingModelController {

	 private static Logger logger = Logger.getLogger(BillingModelController.class);
	 @Autowired
	 BillingModelService billModelService;
	 @Autowired
	 BillingTemplateRepository billTemplateRepository;
	 @Autowired
	 PromoRepository promoRepository;
	 @Autowired
	 CommissionRepository commRepository;
	 @Autowired
	 PartnerRepository partnerRepository;
	 public Partner fetchedPartner = null;
	 
	 
	 @RequestMapping(value={"/billingModel/{bill_id}","/billingModel"}, method  = RequestMethod.POST)
		public ResponseEntity<?> createUpdateBillModel(@RequestParam( required = false, name="action") String action,@RequestBody BillingModelDto billReq) throws JsonProcessingException{
			logger.info("Started createUpdateBillModel() method ");
			Map<String, Object> model = new HashMap<>();
			
			if("add".equals(action)) {
				if(billReq.getCommissionId() == null || billReq.getCommissionId() == 0) {
					throw new InvalidParameterException("commission id is missing");
				}
				if(billReq.getPromoId() == null || billReq.getPromoId() == 0) {
					throw new InvalidParameterException("promo id is missing");
				}
				
				Optional<Commission> fetchedComm = commRepository.findById(billReq.getCommissionId());
				if(!fetchedComm.isPresent()) {
					logger.error("Exception occured in BillingModelController() method.");
					throw new ResourceNotFoundException("Invalid partnerId "+billReq.getCommissionId());
				}
				
                if(billReq.getPartnerId() != null && !billReq.getPartnerId().isEmpty()) {
                	fetchedPartner = partnerRepository.findByPartnerId(billReq.getPartnerId());
				}
				
				Optional<Promo> fetchedPromo = promoRepository.findById(billReq.getPromoId());
				if(!fetchedPromo.isPresent()) {
					logger.error("Exception occured in BillingModelController() method.");
					throw new ResourceNotFoundException("Invalid promoId "+billReq.getPromoId());
				}
				
				 billReq = billModelService.createBillingModel(billReq, fetchedPartner, fetchedPromo.get(), fetchedComm.get());
				 model.put("billing_id", billReq.getBillingId());
				 model.put("job_id","default");
				 model.put("statusDetail", "Billing Model created successfully");
			}else if("edit".equals(action)) {
				
				
				if(billReq.getCommissionId() == null || billReq.getCommissionId() == 0) {
					throw new InvalidParameterException("commission id is missing");
				}
				
				if(billReq.getPromoId() == null || billReq.getPromoId() == 0) {
					throw new InvalidParameterException("promo id is missing");
				}
				
				Optional<BillingTemplate> fetchedBillTemplate = billTemplateRepository.findById(billReq.getBillingId());
				if(!fetchedBillTemplate.isPresent()) {
					logger.error("Exception occured in BillingModelController() method.");
					throw new ResourceNotFoundException("Invalid billId "+billReq.getBillingId());
				}
				
				
				Optional<Commission> fetchedComm = commRepository.findById(billReq.getCommissionId());
				if(!fetchedComm.isPresent()) {
					logger.error("Exception occured in BillingModelController() method.");
					throw new ResourceNotFoundException("Invalid partnerId "+billReq.getCommissionId());
				}
				
                if(billReq.getPartnerId() != null && !billReq.getPartnerId().isEmpty()) {
                	fetchedPartner = partnerRepository.findByPartnerId(billReq.getPartnerId());
				}
				
				Optional<Promo> fetchedPromo = promoRepository.findById(billReq.getPromoId());
				if(!fetchedPromo.isPresent()) {
					logger.error("Exception occured in BillingModelController() method.");
					throw new ResourceNotFoundException("Invalid promoId "+billReq.getPromoId());
				}
				
				 billReq = billModelService.updateBillingModel(billReq, fetchedPartner, fetchedPromo.get(), fetchedComm.get(),fetchedBillTemplate.get());
				 model.put("billing_id", billReq.getBillingId());
				 model.put("job_id","default");
				 model.put("statusDetail", "Billing Model updated successfully");
			}else if("query".equals(action)) {
				if(billReq.getBillingId()==null) {
					List<BillingTemplate> billingTemplateList = billTemplateRepository.getBillingTemplatesListByFilter(billReq.getFilters().getPartner_id(), billReq.getFilters().getIsActive());
					List<BillingModelDto> billingModelDtoList = new ArrayList<>();
					for(int i=0; i<billingTemplateList.size(); i++){
						ObjectMapper objectMapper = new ObjectMapper();
						billReq = new BillingModelDto();
						billReq.setActive(billingTemplateList.get(i).getIsActive());
						billReq.setBillingId(billingTemplateList.get(i).getBillingId());
						billReq.setCommissionId(billingTemplateList.get(i).getCommission().getCommissionId());
						billReq.setPartnerId(billingTemplateList.get(i).getPartner().getPartnerId());
						billReq.setPromoId(billingTemplateList.get(i).getPromo().getPromoId());
						billReq.setType(billingTemplateList.get(i).getType());
						billReq.setUnit(billingTemplateList.get(i).getUnit());
						billReq.setWholeSaleLimits(objectMapper.readValue(billingTemplateList.get(i).getWholeSaleLimits(),WholeSaleLimits.class));
						billingModelDtoList.add(billReq);
					}
					model.put("billingTemplateList", billingModelDtoList);
					model.put("statusDetail", "Found " + billingModelDtoList.size() + " billing templates");
					//Need to clarify the params on which basis the billing model would be fetched
				}else{
					Optional<BillingTemplate> billingTemplateList = billTemplateRepository.findById(billReq.getBillingId());
					if(billingTemplateList.isPresent()) {
						ObjectMapper objectMapper = new ObjectMapper();
						billReq = new BillingModelDto();
						billReq.setActive(billingTemplateList.get().getIsActive());
						billReq.setBillingId(billingTemplateList.get().getBillingId());
						billReq.setCommissionId(billingTemplateList.get().getCommission().getCommissionId());
						billReq.setPartnerId(billingTemplateList.get().getPartner().getPartnerId());
						billReq.setPromoId(billingTemplateList.get().getPromo().getPromoId());
						billReq.setType(billingTemplateList.get().getType());
						billReq.setUnit(billingTemplateList.get().getUnit());
						billReq.setWholeSaleLimits(objectMapper.readValue(billingTemplateList.get().getWholeSaleLimits(), WholeSaleLimits.class));
						model.put("billingTemplate", billReq);
					}
					else{
						model.put("statusDetail", "Billing templates not found");
					}
				}
			}
			
			return ok(model);
	 }
}
