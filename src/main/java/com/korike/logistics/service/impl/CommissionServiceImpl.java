package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korike.logistics.entity.Commission;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.CommissionDto;
import com.korike.logistics.repository.CommissionRepository;
import com.korike.logistics.service.CommissionService;
import com.korike.logistics.util.CommonUtils;
@Service
public class CommissionServiceImpl implements CommissionService{
	
	private static Logger logger = Logger.getLogger(CommissionServiceImpl.class);
	
	@Autowired
	CommissionRepository commRepository;

	@Override
	public CommissionDto createCommission(CommissionDto commDto, Partner fetchedPartner) {
		logger.info("Started createCommission() method ");
		
		Commission newComm = new Commission();
		newComm.setCommissionPercent(commDto.getCommissionPercent());
		if(fetchedPartner!=null) {
			newComm.setPartner(fetchedPartner);
		}
		newComm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newComm.setCreatedBy(CommonUtils.getCurrentUserName());
		newComm.setIsActive(true);
		
		try {
			newComm = commRepository.save(newComm);
			commDto.setCommId(newComm.getCommissionId());
		}catch(Exception exc) {
			logger.error("Exception occured in createCommission() method.");
			throw new KorikeException("Internal Server Error");
		}
		
		return commDto;
	}

	@Override
	public CommissionDto updateCommission(CommissionDto commDto, Commission fetchedComm, Partner fetchedPartner) {
		
        logger.info("Started updateCommission() method ");
		
		
        fetchedComm.setCommissionPercent(commDto.getCommissionPercent());
		if(fetchedPartner!=null) {
			fetchedComm.setPartner(fetchedPartner);
		}
		fetchedComm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		fetchedComm.setCreatedBy(CommonUtils.getCurrentUserName());
		fetchedComm.setIsActive(true);
		
		try {
			fetchedComm = commRepository.save(fetchedComm);
			commDto.setCommId(fetchedComm.getCommissionId());
		}catch(Exception exc) {
			logger.error("Exception occured in createCommission() method.");
			throw new KorikeException("Internal Server Error");
		}
		
		return commDto;
	}

	@Override
	public List<CommissionDto> commissionList(CommissionDto commDto) {
		 logger.info("Started commissionList() method ");
		 List<Commission> commList = new ArrayList();
		 List<CommissionDto> commDtoList = new ArrayList();
		 if(commDto.getCommFilter() == null || commDto.getCommFilter().getCommId() == null || commDto.getCommFilter().getCommId() == 0) {
			 commList = commRepository.findAll();
			 return commList.stream().map(comm -> new CommissionDto(comm.getCommissionId(),comm.getCommissionPercent(),comm.getPartner().getPartnerId()))
					 .collect(Collectors.toList());
		 }else if(commDto.getCommFilter() != null) {
			 Optional<Commission> fetchedComm = commRepository.findById(commDto.getCommFilter().getCommId());
			 if(!fetchedComm.isPresent()) {
				 logger.error("Exception occured in commissionList() method.");
			     throw new KorikeException("Invalid commId");
			 }
			 commDtoList.add(new CommissionDto(fetchedComm.get().getCommissionId(), fetchedComm.get().getCommissionPercent(), fetchedComm.get().getPartner().getPartnerId()));
			 return  commDtoList;
		 }else {
				logger.error("Exception occured in commissionList() method.");
				throw new KorikeException("Internal Server Error");
			}
		 
		
	}

}
