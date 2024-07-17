package com.korike.logistics.controller.admin;

import static org.springframework.http.ResponseEntity.ok;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.repository.ServiceDefinitionRepository;
import com.korike.logistics.repository.ServicePartnerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.Promo;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.promo.PromoAddReqDto;
import com.korike.logistics.repository.PromoRepository;
import com.korike.logistics.service.PromoService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "https://korikelogistics.com:8443")
public class PromoController {

	private static Logger logger = Logger.getLogger(PromoController.class);
	
	@Autowired
	PromoService promoDaoService;
	@Autowired
	ServicePartnerRepository partnerRepo;
	@Autowired
	PromoRepository promoRepo;

	@Autowired
	ServiceDefinitionRepository serviceDefinitionRepository;
	
	@RequestMapping(value={"/promo/{promo_id}","/promo"}, method  = RequestMethod.POST)
	public ResponseEntity<?> createUpdatePromo(@PathVariable(required=false) Long promo_id, @RequestParam( required = false, name="action") String action,@RequestBody PromoAddReqDto promoAddReq){
		List<PromoAddReqDto> promoListByFilter = null;
		logger.info("Started createUpdatePromo() method ");
		Map<String, Object> model = new HashMap<>();

		
		Optional<Partner> fetchedPartner = null;
		if(promoAddReq.getPartnerId() != null && !promoAddReq.getPartnerId().isEmpty()) {
			fetchedPartner = partnerRepo.findById(promoAddReq.getPartnerId());
			if(!fetchedPartner.isPresent()) {
	        	logger.error("Exception occured in PromoController() method.");
				throw new ResourceNotFoundException("Invalid partner Id "+promoAddReq.getPartnerId());
	        }
		}

		Optional<ServiceDefinition> fetchedServiceDefinition = null;
		if(promoAddReq.getServiceId() != null && !promoAddReq.getServiceId().isEmpty()) {
			fetchedServiceDefinition = serviceDefinitionRepository.findById(promoAddReq.getServiceId());
			if(!fetchedServiceDefinition.isPresent()) {
				logger.error("Exception occured in PromoController() method.");
				throw new ResourceNotFoundException("Invalid service Id "+promoAddReq.getServiceId());
			}
		}
		if("add".equals(action)) {
			if(promoAddReq.getPromoCode() == null || promoAddReq.getPromoCode().isEmpty()) {
				throw new InvalidParameterException("Promo code is missing");
			}
	        if((promoAddReq.getPromoPercent() == null || promoAddReq.getPromoPercent().equals(String.valueOf(Double.valueOf("0.0")))) && (promoAddReq.getMaxCashback() == null || promoAddReq.getMaxCashback().equals(String.valueOf(Double.valueOf("0.0"))))) {
	        	throw new InvalidParameterException("promo percent and max cashback are missing or not scaled");
			}
	        if(promoAddReq.getExpiry() == null ||promoAddReq.getExpiry() == 0) {
				throw new InvalidParameterException("Promo expiry is missing or not scaled");
			}
	        
	        promoAddReq = promoDaoService.createPromo(promoAddReq, fetchedPartner == null?null:fetchedPartner.get(), fetchedServiceDefinition == null?null:fetchedServiceDefinition.get());
	        
	       
			model.put("promo_id", promoAddReq.getPromoId());
			model.put("promo_code",promoAddReq.getPromoCode());
			model.put("statusDetail", "Promo created successfully");
			
			
		} else if("edit".equals(action)) {
			Optional<Promo> fetchedPromo = promoRepo.findById(promo_id);
			if(!fetchedPromo.isPresent()) {
				logger.error("Exception occured in PromoController() method.");
				throw new ResourceNotFoundException("Invalid promo Id "+promo_id);
			}
			
			if(promoAddReq.getPromoCode() == null || promoAddReq.getPromoCode().isEmpty()) {
				throw new InvalidParameterException("Promo code is missing");
			}
	        if(promoAddReq.getPromoPercent() == null || promoAddReq.getPromoPercent().equals(String.valueOf(Double.valueOf("0.0")))) {
	        	throw new InvalidParameterException("promo percent is missing or not scaled");
			}
	        if(promoAddReq.getExpiry() == null ||promoAddReq.getExpiry() == 0) {
				throw new InvalidParameterException("Promo expiry is missing or not scaled");
			}
	        
	        promoAddReq = promoDaoService.updatePromo(promoAddReq, fetchedPartner == null?null:fetchedPartner.get(), fetchedServiceDefinition == null?null:fetchedServiceDefinition.get(), fetchedPromo.get());
	        
	        model.put("promo_id", promoAddReq.getPromoId());
			model.put("promo_code",promoAddReq.getPromoCode());
			model.put("statusDetail", "Promo updated successfully");
			
		}else if("query".equals(action)) {

//			String promo_code = promoAddReq.getFilter().getPromoCode();
			String service_id = promoAddReq.getFilter().getServiceId();
			promoListByFilter = promoDaoService.getPromoListByFilter(service_id, promoAddReq.getFilter().getIsActive());
			model.put("promoList", promoListByFilter);
			model.put("statusDetail", "Found "+promoListByFilter.size()+" promos");
		}
		return ok(model);
	}

}
