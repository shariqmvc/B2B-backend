package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.model.partner.PartnerDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.Promo;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.promo.PromoAddReqDto;
import com.korike.logistics.repository.PromoRepository;
import com.korike.logistics.service.PromoService;

@Service
public class PromoServiceImpl implements PromoService{
	
	private static Logger logger = Logger.getLogger(PromoServiceImpl.class);
	
	@Autowired
	PromoRepository promoRepo;
	

	@Override
	public PromoAddReqDto createPromo(PromoAddReqDto promoAddReq, Partner fetchedPartner, ServiceDefinition serviceDefinition) {
		
		Promo newPromo = new Promo();
		newPromo.setPromoPercent(promoAddReq.getPromoPercent());
		newPromo.setPromoCode(promoAddReq.getPromoCode());
		newPromo.setMinBill(promoAddReq.getMinBill());
		newPromo.setMaxDiscount(promoAddReq.getMaxDiscount());
		newPromo.setMaxCashback(promoAddReq.getMaxCashback());
		newPromo.setAddCashOnWalletCreate(promoAddReq.getAddCashOnWalletCreate());
		newPromo.setOnWalletCreate(promoAddReq.getOnWalletCreate());
		newPromo.setExpiry(promoAddReq.getExpiry());
		newPromo.setIsActive(promoAddReq.getIsActive());
		newPromo.setServiceDefinition(serviceDefinition);
		if(fetchedPartner!=null) {
			newPromo.setPartner(fetchedPartner);
		}
		newPromo.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newPromo.setCreatedBy("Admin");
		
		try {
			promoRepo.save(newPromo);
			promoAddReq.setPromoId(newPromo.getPromoId());
		}catch(Exception exc) {
			logger.error("Exception occured in createPromo() method.");
			throw new KorikeException("Internal Server Error");
		}
		
		return promoAddReq;
	}


	@Override
	public PromoAddReqDto updatePromo(PromoAddReqDto promoAddReq, Partner fetchedPartner, ServiceDefinition serviceDefinition, Promo fetchedPromo) {
		

		fetchedPromo.setPromoPercent(promoAddReq.getPromoPercent());
		fetchedPromo.setPromoCode(promoAddReq.getPromoCode());
		fetchedPromo.setMinBill(promoAddReq.getMinBill());
		fetchedPromo.setMaxDiscount(promoAddReq.getMaxDiscount());
		fetchedPromo.setMaxCashback(promoAddReq.getMaxCashback());
		fetchedPromo.setAddCashOnWalletCreate(promoAddReq.getAddCashOnWalletCreate());
		fetchedPromo.setOnWalletCreate(promoAddReq.getOnWalletCreate());
		fetchedPromo.setExpiry(promoAddReq.getExpiry());
		fetchedPromo.setIsActive(promoAddReq.getIsActive());
		fetchedPromo.setServiceDefinition(serviceDefinition);
	//	if(fetchedPartner!=null) {
			fetchedPromo.setPartner(fetchedPartner);
//		}
		fetchedPromo.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
		fetchedPromo.setLastUpdatedBy("Admin");
		
		try {
			promoRepo.save(fetchedPromo);
			promoAddReq.setPromoId(fetchedPromo.getPromoId());
		}catch(Exception exc) {
			logger.error("Exception occured in updatePromo() method.");
			throw new KorikeException("Internal Server Error");
		}
		
		
		return promoAddReq;
	}


	@Override
	public List<PromoAddReqDto> getPromoListByFilter(String serviceId, Boolean isActive) {
		
		List<Promo> promoList = promoRepo.getPromoByFilter(serviceId, isActive);
		List<PromoAddReqDto> promoDtoList = new ArrayList();
		
		PromoAddReqDto newPromoDto;
		for(Promo promo : promoList) {
			newPromoDto = new PromoAddReqDto();
			newPromoDto.setPromoId(promo.getPromoId());
			newPromoDto.setPromoCode(promo.getPromoCode());
			newPromoDto.setPromoPercent(promo.getPromoPercent());
			newPromoDto.setOnWalletCreate(promo.getOnWalletCreate());
			newPromoDto.setMinBill(promo.getMinBill());
			newPromoDto.setMaxDiscount(promo.getMaxDiscount());
			newPromoDto.setMaxCashback(promo.getMaxCashback());
			newPromoDto.setIsActive(promo.getIsActive());
			newPromoDto.setExpiry(promo.getExpiry());
			newPromoDto.setAddCashOnWalletCreate(promo.getAddCashOnWalletCreate());
			promoDtoList.add(newPromoDto);
		}
		
		
		return promoDtoList;
	}


	@Override
	public List<Promo> getPromoList() {
		
		return promoRepo.getPromosWithOutPartner();
	}

}
