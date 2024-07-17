package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.korike.logistics.entity.KycEntity;
import com.korike.logistics.model.Kyc;
import com.korike.logistics.repository.KycRepository;
import com.korike.logistics.util.CoreUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.entity.Promo;
import com.korike.logistics.entity.Role;
import com.korike.logistics.entity.User;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.AuthRequestDto;
import com.korike.logistics.model.Roles;
import com.korike.logistics.model.UserInfoDto;
import com.korike.logistics.model.wallet.WalletAddDto;
import com.korike.logistics.repository.RoleRepository;
import com.korike.logistics.repository.UserRepository;
import com.korike.logistics.service.PromoService;
import com.korike.logistics.service.UserService;
import com.korike.logistics.service.WalletService;
import com.korike.logistics.util.CommonUtils;

import javax.swing.text.html.Option;

import static com.korike.logistics.controller.AuthenticationController.authMode;

@Service
public class UserServiceImpl implements UserService{
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	WalletService walletService;
	@Autowired
	PromoService promoSerivce;
	@Autowired
	KycRepository kycRepository;

	@Override
	public List<KycEntity> getKycList(){
		logger.info("Getting Kyc constants");
		List<KycEntity> kyc;
		try{
			kyc = kycRepository.findAll();
			return kyc;
		}
		catch(Exception exc) {
			logger.error("Exception occured in fetching KycRepo " +CommonUtils.printException(exc));
			throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while fetching Kyc Repo");
		}

	}

	@Override
	public User getUserByUserName(String userName) {
		logger.info("Started getUserByUserName() method ");
		
		Optional<User> user;
		try {
		   user = 	userRepo.findByUserName(userName);
		}catch(Exception exc) {
			logger.error("Exception occured in UserService " +CommonUtils.printException(exc));
			throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while fetching user");
		}
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	public User getUserByUserNameAndRole(String userName, Integer authType) {
		logger.info("Started getUserByUserName() method ");

		Optional<User> user;
		try {
			user = 	userRepo.getUserByNameAndRole(userName, authType);
		}catch(Exception exc) {
			logger.error("Exception occured in UserService " +CommonUtils.printException(exc));
			throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while fetching user");
		}
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	public UserInfoDto createUser(AuthRequestDto userDto) throws JsonProcessingException {
		logger.info("Started createUser() method ");

		UserInfoDto userInfoDto = new UserInfoDto();
		List<Kyc> kycList = new ArrayList<>();
		List<KycEntity> kycEntities = new ArrayList<>();
		kycEntities = getKycList();
		for(KycEntity kycEnt: kycEntities){
			com.korike.logistics.model.Kyc kycModel = new com.korike.logistics.model.Kyc();
			kycModel.setKycId(kycEnt.getKycId());
			kycModel.setKycName(kycEnt.getKycName());
			kycModel.setKycDescription(kycEnt.getKycDescription());
			kycList.add(kycModel);
		}
		userInfoDto.setKyc(kycList);

		ObjectMapper mapper = new ObjectMapper();
		String userDetailsJson = mapper.writeValueAsString(userInfoDto);
		User newUser = null;
if(CoreUtil.getAuthModeByNum(authMode).equals("EP")
		|| CoreUtil.getAuthModeByNum(authMode).equals("EO")){
	//String userId, String userDetails, String userName, String email, String password, String mobile, String oneTimePass, Timestamp otpCreatedAt, String authToken, Date authTokenExpiry, Timestamp createdAt, Timestamp lastUpdatedAt, String createdBy, String lastUpdatedBy, Set<Role> roles, Boolean isActive
	newUser = new User(UUID.randomUUID().toString(),"",userDto.getUserName(),userDto.getUserName(),
			null,userDto.getMobile(),null,null,null,null,new Timestamp(System.currentTimeMillis()),null,userDto.getUserName(),null);
/*	newUser = new User(UUID.randomUUID().toString(),"","",userDto.getUserName(),userDto.getOneTimePass(),
			new Timestamp(System.currentTimeMillis()),null,userDto.getUserName(),null,
			null,false,"",userDto.getPassword()); */
}else if(CoreUtil.getAuthModeByNum(authMode).equals("MP")
		|| CoreUtil.getAuthModeByNum(authMode).equals("MO")){
	newUser = new User(UUID.randomUUID().toString(),"",userDto.getUserName(),"",
			null,userDto.getUserName(),null,null,null,null,new Timestamp(System.currentTimeMillis()),null,userDto.getUserName(),null);
/*	newUser = new User(UUID.randomUUID().toString(),"","",userDto.getOneTimePass(),
			new Timestamp(System.currentTimeMillis()),null,userDto.getUserName(),null,
			null,false,userDto.getMobile(),userDto.getPassword()); */
}

		
		Set<Role> role = new HashSet<Role>();
        role.add(roleRepository.findById(Long.valueOf(userDto.getAuthType())).get());
		System.out.println("Role getting assigned "+role);
        newUser.setRoles(role);
        newUser.setUserDetails(userDetailsJson);
		newUser.setIsActive(false);
		if(CoreUtil.getAuthModeByNum(userDto.getAuthMode()).equalsIgnoreCase("MO")
	|| CoreUtil.getAuthModeByNum(userDto.getAuthMode()).equalsIgnoreCase("EO")){
			newUser.setOneTimePass(userDto.getOneTimePass());
			newUser.setOtpCreatedAt(new Timestamp(System.currentTimeMillis()));
		}


		try {
			newUser = userRepo.save(newUser);
		}catch(Exception exc) {
			logger.error("Exception occured in UserService " +CommonUtils.printException(exc));
			throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while creating user");
		}
		
		if(!userDto.getAuthType().equals("4")) {
			WalletAddDto newWalletDto = new WalletAddDto();
			List<Promo> promos = promoSerivce.getPromoList();
			if (!promos.isEmpty()) {
				if (!promos.isEmpty() && promos.get(0).getAddCashOnWalletCreate() && promos.get(0).getOnWalletCreate()) {
					newWalletDto.setWalletBalance(String.valueOf(promos.get(0).getPromoCash()));
				} else {
					newWalletDto.setWalletBalance("0.0");
				}
			}
			else {
				newWalletDto.setWalletBalance("0.0");
			}

			walletService.createUserWallet(newWalletDto, newUser);
		}
		
		UserInfoDto createdUser = new UserInfoDto(newUser.getUserId(),newUser.getUsername(),"",
				newUser.getUsername()+"_"+userDto.getAuthType(),newUser.getRoles().stream().collect(Collectors.toList()).get(0).getName(),"","",null);
		
		return createdUser;
	}

	@Override
	public User getUserByOtp(String oTp) {
		logger.info("Started getUserByOtp() method ");
		
		Optional<User> user;
		try {
		   user = 	userRepo.findByOneTimePass(oTp);
		}catch(Exception exc) {
			logger.error("Exception occured in UserService " +CommonUtils.printException(exc));
			throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while fetching user");
		}
		if(!user.isPresent()) {
			logger.error("Exception occured in UserService Invalid otp " +oTp);
			throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while fetching user");
		}
		return user.get();
	}

	@Override
	public User pureUpdate(User user) {
		logger.info("Started pureUpdate() method " +user.toString());
		
		try {
			return userRepo.save(user);
		} catch (Exception exc) {
			logger.error("Exception occured in UserService " +CommonUtils.printException(exc));
			throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while updating user");
		}
		
	}

	@Override
	public void updateUser(UserInfoDto userProfile, User fetchedUser) throws JsonProcessingException {
		logger.info("Started updateUser() method " +userProfile.toString());
		ObjectMapper mapper = new ObjectMapper(); 
      
		String userDetailsJson = mapper.writeValueAsString(userProfile);
		fetchedUser.setUserDetails(userDetailsJson);
		fetchedUser.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
		fetchedUser.setLastUpdatedBy(fetchedUser.getUsername());
		
		try {
			userRepo.save(fetchedUser);
		}catch(Exception exc) {
			logger.error("Exception occured in UserService " +CommonUtils.printException(exc));
			throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while updating user");
		}
		
	}

	@Override
	public void blocklistUserToken(User fetchedUser) throws JsonProcessingException {

	}

	@Override
	public UserInfoDto getUserInfo(User user) throws JsonMappingException, JsonProcessingException {
		logger.info("Started getUserInfo() method ");
		ObjectMapper mapper = new ObjectMapper();
		UserInfoDto userInfo = new UserInfoDto();
		if(user.getUserDetails() != null && !user.getUserDetails().isEmpty()) {
			userInfo = mapper.readValue(user.getUserDetails(), UserInfoDto.class);
			userInfo.setPhone(user.getUsername());
		}
		return userInfo;
	}
}
