package com.korike.logistics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.korike.logistics.entity.Commission;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.model.CommissionDto;

@Service
public interface CommissionService {

	public CommissionDto createCommission(CommissionDto commDto, Partner fetchedPartner);
	public CommissionDto updateCommission(CommissionDto commDto, Commission fetchedComm, Partner fetchedPartner);
	public List<CommissionDto> commissionList(CommissionDto commDto);
}
