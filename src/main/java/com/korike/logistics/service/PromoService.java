package com.korike.logistics.service;


import java.util.List;

import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.model.partner.PartnerDetails;
import org.springframework.stereotype.Service;

import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.Promo;
import com.korike.logistics.model.promo.PromoAddReqDto;

@Service
public interface PromoService {

	public PromoAddReqDto createPromo(PromoAddReqDto promoAddReq, Partner fetchedPartner, ServiceDefinition serviceDefinition);
	public PromoAddReqDto updatePromo(PromoAddReqDto promoAddReq, Partner fetchedPartner, ServiceDefinition serviceDefinition, Promo fetchedPromo);
	public List<PromoAddReqDto> getPromoListByFilter(String service_id, Boolean isActive);
	public List<Promo> getPromoList();
}
