package com.korike.logistics.controller.user;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.korike.logistics.entity.User;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.UserInfoDto;
import com.korike.logistics.model.UserResponseDto;
import com.korike.logistics.repository.UserRepository;
import com.korike.logistics.service.UserService;
import com.korike.logistics.util.CommonUtils;

@RestController
@RequestMapping("/api/consumer")
public class UserController {

	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	UserService userDaoService;
	
	@PostMapping("/userprofile")
	public ResponseEntity<?> updateUser(@RequestBody UserInfoDto userProfile) throws JsonProcessingException{
		  Optional<User> fetchedUser = userRepo.findByUserName(CommonUtils.getCurrentUserName());
			if(!fetchedUser.isPresent()) {
				logger.error("Invalid userName " +CommonUtils.getCurrentUserName());
				throw new ResourceNotFoundException("Invalid userName " +CommonUtils.getCurrentUserName());
			}
			
			userDaoService.updateUser(userProfile, fetchedUser.get());
			UserResponseDto userRes = new UserResponseDto();
			userRes.setUserId(fetchedUser.get().getUserId());
			Map<String, Object> model = new HashMap<>();
			model.put("details", userRes);
			model.put("success","true");
			model.put("statusDetail", "User Updated Successfully");
			return ok(model);
	}
	
	@GetMapping("/userprofile")
	public ResponseEntity<?> getUserInfo() throws JsonProcessingException{
		
		Optional<User> fetchedUser = userRepo.findByUserName(CommonUtils.getCurrentUserName());
		if(!fetchedUser.isPresent()) {
			logger.error("Invalid userName " +CommonUtils.getCurrentUserName());
			throw new ResourceNotFoundException("Invalid userName " +CommonUtils.getCurrentUserName());
		}
		
		UserInfoDto fetchedUserInfo = userDaoService.getUserInfo(fetchedUser.get());
		
		Map<String, Object> model = new HashMap<>();
		model.put("details", fetchedUserInfo);
		model.put("success","true");
		model.put("statusDetail", "User fetched Successfully");
		return ok(model);
	}
	
}
