package com.korike.logistics.service;

import org.springframework.stereotype.Service;

import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.model.FeedBackReqDto;

@Service
public interface FeedBackService {

	public FeedBackReqDto createFeedBack(FeedBackReqDto feedBackReq, CustomerOrderDetails orderDetails);
		
	
}
