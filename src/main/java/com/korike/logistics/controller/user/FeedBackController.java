package com.korike.logistics.controller.user;

import static org.springframework.http.ResponseEntity.ok;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.FeedBackReqDto;
import com.korike.logistics.model.FeedBackResDto;
import com.korike.logistics.repository.CustomerOrderRepository;
import com.korike.logistics.service.CustomerOrderService;
import com.korike.logistics.service.FeedBackService;
import com.korike.logistics.util.CommonUtils;

@RestController
@RequestMapping("/api/consumer")
public class FeedBackController {

	private static Logger logger = Logger.getLogger(FeedBackController.class);
	
    @Autowired
	FeedBackService feedBackDaoService;
    @Autowired
    CustomerOrderService customerOrderService;
    @Autowired
    CustomerOrderRepository customerOrderRepo;
	
	@RequestMapping(value={"/order/{o_id}/feedback"}, method  = RequestMethod.POST)
	public ResponseEntity<?> createFeedBack(@PathVariable() String o_id, @RequestBody FeedBackReqDto feedBackReq) throws JsonProcessingException{
		logger.info("Started createFeedBack() method ");
		
		if(feedBackReq.getFeedBackText() == null || feedBackReq.getFeedBackText().isEmpty()) {
			throw new InvalidParameterException("FeedBack text is missing");
		}
		
		if(feedBackReq.getStar() == null || feedBackReq.getStar() == 0) {
			throw new InvalidParameterException("Points 0 or  missing");
		}
		
		Optional<CustomerOrderDetails> fetchedOrder = customerOrderRepo.findById(o_id);
		if(!fetchedOrder.isPresent()) {
			logger.error("Invalid oid " +CommonUtils.getCurrentUserName());
			throw new ResourceNotFoundException("Invalid oid " +CommonUtils.getCurrentUserName());
		}
		
		FeedBackReqDto createdFeedBack = feedBackDaoService.createFeedBack(feedBackReq, fetchedOrder.get());
		FeedBackResDto FeedBackResDto = new FeedBackResDto();
		FeedBackResDto.setJobId("Dummy");
		
		Map<String, Object> model = new HashMap<>();
		model.put("details", FeedBackResDto);
		model.put("success","true");
		model.put("statusDetail", "Feedback created successfully");
		return ok(model);
		
		
	}
}
