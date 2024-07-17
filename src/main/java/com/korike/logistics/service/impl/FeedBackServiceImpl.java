package com.korike.logistics.service.impl;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.entity.Feedback;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.FeedBackReqDto;
import com.korike.logistics.repository.FeedBackRespository;
import com.korike.logistics.service.FeedBackService;
import com.korike.logistics.util.CommonUtils;

@Service
public class FeedBackServiceImpl implements FeedBackService{

	private static Logger logger = Logger.getLogger(FeedBackServiceImpl.class);
	
	@Autowired
	FeedBackRespository feedBackRepo;
	
	@Override
	public FeedBackReqDto createFeedBack(FeedBackReqDto feedBackReq, CustomerOrderDetails orderDetails) {
		logger.info("Started createFeedBack() method ");
		
		Feedback newFeedBack = new Feedback();
		newFeedBack.setPoints(Long.valueOf(feedBackReq.getStar()));
		newFeedBack.setMessage(feedBackReq.getFeedBackText());
		newFeedBack.setOrderDetail(orderDetails);
		newFeedBack.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newFeedBack.setCreatedBy(CommonUtils.getCurrentUserName());
		
		try {
			newFeedBack = feedBackRepo.save(newFeedBack);
			
		}catch(Exception exc) {
			logger.error("Exception occured in FeedBackService " +CommonUtils.printException(exc));
			throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while creating");
		}
		
		
		return feedBackReq;
	}

}
