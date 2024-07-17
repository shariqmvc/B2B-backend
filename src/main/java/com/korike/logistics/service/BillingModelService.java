package com.korike.logistics.service;

import com.korike.logistics.model.promo.PromoAddReqDto;
import org.springframework.stereotype.Service;

import com.korike.logistics.entity.BillingTemplate;
import com.korike.logistics.entity.Commission;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.Promo;
import com.korike.logistics.model.BillingModelDto;

import java.util.List;

@Service
public interface BillingModelService {

	public BillingModelDto createBillingModel(BillingModelDto billingModel, Partner fetchedPartner, Promo fetchedPromo, Commission fetchedComm);
	public BillingModelDto updateBillingModel(BillingModelDto billingModel, Partner fetchedPartner, Promo fetchedPromo, Commission fetchedComm, BillingTemplate fetchedBillTemplate);

}
