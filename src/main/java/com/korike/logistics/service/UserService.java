package com.korike.logistics.service;

import com.korike.logistics.entity.KycEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.korike.logistics.entity.User;
import com.korike.logistics.model.AuthRequestDto;
import com.korike.logistics.model.UserInfoDto;

import java.util.List;

@Service
public interface UserService {

	public User getUserByUserName(String userName);
	public User getUserByUserNameAndRole(String userName, Integer authType);
	public User getUserByOtp(String oTp);
	public UserInfoDto createUser(AuthRequestDto userDto) throws JsonProcessingException;
	public void updateUser(UserInfoDto userProfile,User fetchedUser) throws JsonProcessingException;
	public void blocklistUserToken(User fetchedUser) throws JsonProcessingException;
	public User pureUpdate(User user);
	public UserInfoDto getUserInfo(User user) throws JsonMappingException, JsonProcessingException;
	public List<KycEntity> getKycList();
	
}
