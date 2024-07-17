package com.korike.logistics.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.korike.logistics.service.EmailService;
import com.korike.logistics.util.CoreUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.korike.logistics.common.Constants;
import com.korike.logistics.common.HttpClientWorkflow;
import com.korike.logistics.entity.Consumer;
import com.korike.logistics.entity.User;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.jwt.JwtTokenProvider;
import com.korike.logistics.model.AuthRequestDto;
import com.korike.logistics.model.RefreshTokenDto;
import com.korike.logistics.repository.ConsumerRepository;
import com.korike.logistics.repository.UserRepository;
import com.korike.logistics.service.ConsumerService;
import com.korike.logistics.service.UserService;
import com.korike.logistics.util.CommonUtils;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@CrossOrigin(origins = "https://korikelogistics.com:8443")
public class AuthenticationController {
    public static boolean isOtpFlag = false;
    public static int authMode = 0;
    private static Logger logger = Logger.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    ConsumerService consumerService;
    @Autowired
    UserService userDaoService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ConsumerRepository consumerRepository;
    @Autowired
    EmailService emailService;
    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds; // 1h 3600000
    @Value("${security.refresh.token.interval}")
    private long refreshTokenInterval; // 5 min



    @PostMapping("/authUser")
    public ResponseEntity<Map<String, Object>> getUserByUserName(@Valid @RequestBody AuthRequestDto authDto) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        //Enforce password,confirm password and OTP for the new user while login process.
        //This flag would be switched true or flase based on the input from the user
        logger.info(authMode);
        authMode = authDto.getAuthMode();
   /*     if (authDto.getAuthMode() == 1) {
            isOtpFlag = true;
        } */


        //isOtpFlag = authDto.isOtpLogin();
        logger.info("Started getUserByUserName() method ");

        String isNew = "False";
        String message = "";
        if (authDto.getUserName() == null || authDto.getUserName().isEmpty()) {
            throw new InvalidParameterException("Username or Mobile number is missing.");
        }

        if (null == authDto.getAuthType()) {
            throw new InvalidParameterException("User type is missing.");
        }

        //1. API to checkUserExists
        //2. API to generate and send otp to respective authModes Email or Mobile
        //2 or 3.API to generate token based on respective authModes(Password or otp)
        //Password would be saved at this stage and validated if user exists so need one isNew flag and authMode
        //in every call


        //check if authmode is null or empty
        //1. login with otp: New user
        /*
         * User enters the phone num and otp is generated as usual. User enters otp and success signup and signin then
         * taken to profile page to enter other details including the passowrd and email and then taken to homepage
         *
         * Existing user:two scenarios mobile num was already registered while first login with otp or email or password.
         *  User enters the phone num and otp is generated as usual. User enters otp and success  signin then
         * taken to honepage
         * */
        //2. login with email and password: New user
		    /*
		     User enters email and password and success sign up and sign then taken to profile page to enter other details such as name, phone number etc.
		     User exists: two scenarios email/password was already registered while first login with otp or email or password.
		    * */
        /*new implementation 16-01-2024*/
     //   User fetchedUser = userDaoService.getUserByUserName(authDto.getUserName());
        User fetchedUser = userDaoService.getUserByUserNameAndRole(authDto.getUserName(), authDto.getAuthType());
        if(CoreUtil.getAuthModeByNum(authMode).equals("MO") || CoreUtil.getAuthModeByNum(authMode).equals("EO")){
            //authDto.setUserName(CommonUtils.appendUsernameWithAuthtype(authDto.getUserName(), authDto.getAuthType()));
            int newOtp = CommonUtils.generateOtp();
            logger.info("Generated otp " + newOtp);

           // User fetchedUser = userDaoService.getUserByUserName(authDto.getUserName());
            //User fetchedUser = userDaoService.getUserByUserNameAndRole(authDto.getUserName(), authDto.getAuthType());

            if (fetchedUser == null || fetchedUser.isAccountNew() == true) {
                //create user
                authDto.setOneTimePass(passwordEncoder.encode(String.valueOf(newOtp)));
                if(fetchedUser == null){
                    userDaoService.createUser(authDto);
                }

                isNew = "True";
                message = "user created successfully";
                //send otp
                if(CoreUtil.getAuthModeByNum(authMode).equals("MO")){
                    String url = Constants.SMS_GATEWAY_BASE_URL_WITH_KEY + CommonUtils.stripUsername(authDto.getUserName()) + "/" + newOtp + "/" + Constants.SMS_GATEWAY_SUFFIX_WITH_TEMPLATE;
                    HttpClientWorkflow.executeGetRequests(url, null, Constants.SMS_GATEWAY_HEADER_CONTENT_TYPE, null);
                }
                if(CoreUtil.getAuthModeByNum(authMode).equals("EO")){
                    emailService.addEmailtoAllowList(authDto.getUserName());
                    emailService.sendOtpToEmail(authDto.getUserName(), String.valueOf(newOtp));

                }

            } else {
                //update user with new otp and time
                fetchedUser.setOneTimePass(passwordEncoder.encode(String.valueOf(newOtp)));
                fetchedUser.setOtpCreatedAt(new Timestamp(System.currentTimeMillis()));
                fetchedUser.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                fetchedUser.setLastUpdatedBy(authDto.getUserName());
                message = "user found";
                isNew = "False";
                userDaoService.pureUpdate(fetchedUser);
                if(CoreUtil.getAuthModeByNum(authMode).equals("MO")){
                    String url = Constants.SMS_GATEWAY_BASE_URL_WITH_KEY + CommonUtils.stripUsername(authDto.getUserName()) + "/" + newOtp + "/" + Constants.SMS_GATEWAY_SUFFIX_WITH_TEMPLATE;
                    HttpClientWorkflow.executeGetRequests(url, null, Constants.SMS_GATEWAY_HEADER_CONTENT_TYPE, null);
                }
                if(CoreUtil.getAuthModeByNum(authMode).equals("EO")){
                    emailService.sendOtpToEmail(authDto.getUserName(), String.valueOf(newOtp));

                }

            }
        }else if(CoreUtil.getAuthModeByNum(authMode).equals("EO")){

        }else if (CoreUtil.getAuthModeByNum(authMode).equals("EP")) {
            //User fetchedUser = userDaoService.getUserByUserName(authDto.getUserName());
            if (fetchedUser == null) {
                userDaoService.createUser(authDto);
                isNew = "True";
                message = "user created successfully";

               // emailService.addEmailtoAllowList(authDto.getUserName());

            }else {
                message = "user already existing";
            }
        }else if (CoreUtil.getAuthModeByNum(authMode).equals("MP")) {
           // User fetchedUser = userDaoService.getUserByUserName(authDto.getUserName());
            if (fetchedUser == null) {
                userDaoService.createUser(authDto);
                isNew = "True";
                message = "user created successfully";
            }else {
                message = "user already existing";
            }

        }



  /*      if (isOtpFlag) {
            authDto.setUserName(CommonUtils.appendUsernameWithAuthtype(authDto.getUserName(), authDto.getAuthType()));
            int newOtp = CommonUtils.generateOtp();
            logger.info("Generated otp " + newOtp);

            User fetchedUser = userDaoService.getUserByUserName(authDto.getUserName());
            //User fetchedUser = userDaoService.getUserByUserNameAndRole(authDto.getUserName(), authDto.getAuthType());

            if (fetchedUser == null) {
                //create user
                authDto.setOneTimePass(passwordEncoder.encode(String.valueOf(newOtp)));
                userDaoService.createUser(authDto);
                isNew = "True";
                message = "user created successfully";
                //send otp
                String url = Constants.SMS_GATEWAY_BASE_URL_WITH_KEY + CommonUtils.stripUsername(authDto.getUserName()) + "/" + newOtp + "/" + Constants.SMS_GATEWAY_SUFFIX_WITH_TEMPLATE;
                HttpClientWorkflow.executeGetRequests(url, null, Constants.SMS_GATEWAY_HEADER_CONTENT_TYPE, null);
            } else {
                //update user with new otp and time
                fetchedUser.setOneTimePass(passwordEncoder.encode(String.valueOf(newOtp)));
                fetchedUser.setOtpCreatedAt(new Timestamp(System.currentTimeMillis()));
                fetchedUser.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                fetchedUser.setLastUpdatedBy(authDto.getUserName());
                message = "user found";
                userDaoService.pureUpdate(fetchedUser);
                String url = Constants.SMS_GATEWAY_BASE_URL_WITH_KEY + CommonUtils.stripUsername(authDto.getUserName()) + "/" + newOtp + "/" + Constants.SMS_GATEWAY_SUFFIX_WITH_TEMPLATE;
                HttpClientWorkflow.executeGetRequests(url, null, Constants.SMS_GATEWAY_HEADER_CONTENT_TYPE, null);
            }
        } else {
            User fetchedUser = userDaoService.getUserByUserName(authDto.getUserName());
            logger.info(fetchedUser);
            logger.info("Login in  through email and password");

        } */


        Map<String, Object> model = new HashMap<>();
        model.put("userName", authDto.getUserName());
        model.put("success", "true");
        model.put("isNew",isNew);
        model.put("statusDetails", authDto.getUserName() + " " + message);
        return ok(model);
    }

    @PostMapping("/authUser/generateToken")
    public ResponseEntity<Map<Object, Object>> generateToken(@Valid @RequestBody AuthRequestDto authDto) throws JsonProcessingException {
        logger.info("Started generateToken() method ");
        authMode = authDto.getAuthMode();
        String restClientName = null;
        String password = null;
        //User exisitingUser = userDaoService.getUserByUserName(authDto.getUserName());
        User exisitingUser = userDaoService.getUserByUserNameAndRole(authDto.getUserName(), authDto.getAuthType());
        boolean isActive = exisitingUser.getIsActive();
        if (authDto.getUserName() == null || authDto.getUserName().isEmpty()) {
            throw new InvalidParameterException("Username or Mobile number is missing.");
        }

        if(CoreUtil.getAuthModeByNum(authMode).equals("EO")
                || CoreUtil.getAuthModeByNum(authMode).equals("MO")){
            if (authDto.getOneTimePass() == null || authDto.getOneTimePass().isEmpty()) {
                throw new InvalidParameterException("OTP is missing.");
            }

            /*Implementation of profile*/

            if(authDto.getIsNew().equalsIgnoreCase("True")){
                if (authDto.getPassword() == null || authDto.getPassword().isEmpty()) {
                    throw new InvalidParameterException("Password is missing.");
                }
                if(CoreUtil.getAuthModeByNum(authMode).equals("MO")){
                    exisitingUser.setEmail(authDto.getEmail());
                    exisitingUser.setPassword(passwordEncoder.encode(authDto.getPassword()));
                    exisitingUser.setMobileVerified(true);
                    //userDaoService.createUser(authDto);
                    //userDaoService.pureUpdate(fetchedUser);
                }else if(CoreUtil.getAuthModeByNum(authMode).equals("EO")){

                    exisitingUser.setMobile(authDto.getMobile());
                    exisitingUser.setPassword(passwordEncoder.encode(authDto.getPassword()));
                    exisitingUser.setEmailVerified(true);
                }
               // userDaoService.pureUpdate(fetchedUser);
                /* activates the user if its not activated yet*/
                restClientName = exisitingUser.getUsername();
                password = authDto.getOneTimePass();
                if (CommonUtils.isOtpExpired(exisitingUser.getOtpCreatedAt())) {
                    logger.error("Invalid username or password: " + authDto.toString());
                    throw new BadCredentialsException("OTP expired");
                }
                if (passwordEncoder.matches(authDto.getOneTimePass(), exisitingUser.getOneTimePass())) {
                    exisitingUser.setIsActive(true);
                    exisitingUser.setAccountNew(false);
                    userDaoService.pureUpdate(exisitingUser);
                }
            }else{
                //User exisitingUser = userDaoService.getUserByUserName(authDto.getUserName());
                //User exisitingUser = userDaoService.getUserByUserNameAndRole(authDto.getUserName(), authDto.getAuthType());

                restClientName = exisitingUser.getUsername();

                //also check if otp is expired then revert back if error and allow to re send


            /*    if(CoreUtil.getAuthModeByNum(authMode).equals("EO")
                        || CoreUtil.getAuthModeByNum(authMode).equals("MO")){ */
                    password = authDto.getOneTimePass();
                    if (CommonUtils.isOtpExpired(exisitingUser.getOtpCreatedAt())) {
                        logger.error("Invalid username or password: " + authDto.toString());
                        throw new BadCredentialsException("OTP expired");
                    }
            //    }
            }

            ////REST below will change


            if(CoreUtil.getAuthModeByNum(authMode).equals("EO")){
//check if the user new
                //then populate the Mobile and password for the user with the exisiting email populated in auth step.
                //Then verify the Mobile  after success login from profile
            }

            if(CoreUtil.getAuthModeByNum(authMode).equals("MO")){
//check if the user new
                //then populate the Email and password for the user with the exisiting Mobile populated in auth step.
                //Then verify the email after success login from profile
            }

        }else if(CoreUtil.getAuthModeByNum(authMode).equals("EP")
                || CoreUtil.getAuthModeByNum(authMode).equals("MP")){
//check if user is new or not
            restClientName = exisitingUser.getUsername();
            password = authDto.getPassword();
        }


  /*      if(CoreUtil.getAuthModeByNum(authMode).equals("EP")
        || CoreUtil.getAuthModeByNum(authMode).equals("MP")){
            if (authDto.getPassword() == null || authDto.getPassword().isEmpty()) {
                throw new InvalidParameterException("Password is missing.");
            }
            fetchedUser = userDaoService.getUserByUserName(authDto.getUserName());
            if (fetchedUser == null) {

                logger.error("Invalid username or password: " + authDto.toString());
                throw new BadCredentialsException("Invalid Username or password");

            }else{
                //create user\
                if(fetchedUser.getPassword() == null){
                    fetchedUser.setPassword(passwordEncoder.encode(authDto.getPassword()));
                    //userDaoService.createUser(authDto);
                    userDaoService.pureUpdate(fetchedUser);
                }


            }
        } */
        //authDto.setUserName(CommonUtils.appendUsernameWithAuthtype(authDto.getUserName(), authDto.getAuthType()));





        /* activates the user if its not activated yet*/
 /*       boolean isActive = exisitingUser.getIsActive();
        if (!isActive) {
            if(CoreUtil.getAuthModeByNum(authMode).equals("EO")
                    || CoreUtil.getAuthModeByNum(authMode).equals("MO")){
                if (passwordEncoder.matches(password, exisitingUser.getOneTimePass())) {
                    exisitingUser.setIsActive(true);
                    userDaoService.pureUpdate(exisitingUser);
                }
            }else if(CoreUtil.getAuthModeByNum(authMode).equals("EP")
                    || CoreUtil.getAuthModeByNum(authMode).equals("MP")){
                if (passwordEncoder.matches(password, exisitingUser.getPassword())) {
                    exisitingUser.setIsActive(true);
                    userDaoService.pureUpdate(exisitingUser);
                }
            }

        } */
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(restClientName, password));

            List<String> roleList = new ArrayList<>();
            roleList.add(exisitingUser.getRoles().stream().collect(Collectors.toList()).get(0).getName());
            String token = jwtTokenProvider.createToken(restClientName, roleList);
            Optional<User> existUser = userRepository.findByUserName(restClientName);
            try {
                existUser.get().setAuthToken(CommonUtils.toHexString(CommonUtils.getSHA(token)));
            } catch (NoSuchAlgorithmException e) {
                logger.error("Problem while generating hash");
                throw new KorikeException("Invalid Algorithm");
            }
            existUser.get().setAuthTokenExpiry(new Date(System.currentTimeMillis() + validityInMilliseconds));

            userRepository.save(existUser.get());

            if ((exisitingUser.getRoles().stream().collect(Collectors.toList()).get(0).getName().equals("Consumer") || (exisitingUser.getRoles().stream().collect(Collectors.toList()).get(0).getName().equals("Superuser"))) && !isActive) {
                try {
                    Consumer consumerResponse = consumerService.createConsumer(exisitingUser);
                    logger.info("Consumer created successfully " + consumerResponse);
                } catch (Exception exc) {
                    logger.error("Consumer creation failed.");
                    Map<Object, Object> model = new HashMap<>();
                    model.put("client", restClientName);
                    model.put("success", false);
                    return ok(model);
                }
            }
            Map<Object, Object> model = new HashMap<>();
            model.put("client", restClientName);
            model.put("token", token);
            model.put("success", true);
            return ok(model);
        } catch (AuthenticationException e) {
            logger.error("Invalid username or password: " + authDto.toString());
            throw new BadCredentialsException("Invalid OTP");
        }


    }

    @PostMapping("/authUser/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        Map<Object, Object> model = new HashMap<>();
        try {

            User exisitingUser = userRepository.findByAuthToken(CommonUtils.toHexString(CommonUtils.getSHA(refreshTokenDto.getRefreshToken())));
            if (exisitingUser == null) {
                logger.error("Invalid Token: " + refreshTokenDto.getRefreshToken());
                throw new BadCredentialsException("Invalid token");
            }
            Long duration = CommonUtils.getDiffereceInMin(exisitingUser.getAuthTokenExpiry(), new Date());
            if (duration > 0) {
                String restClientName = exisitingUser.getUsername();
                List<String> roleList = new ArrayList<>();
                roleList.add(exisitingUser.getRoles().stream().collect(Collectors.toList()).get(0).getName());
                String genRefreshToken = jwtTokenProvider.createRefreshToken(refreshTokenDto.getRefreshToken(), restClientName, roleList);
                try {
                    exisitingUser.setAuthToken(CommonUtils.toHexString(CommonUtils.getSHA(genRefreshToken)));
                } catch (NoSuchAlgorithmException e) {
                    logger.error("Problem while generating hash");
                    throw new KorikeException("Invalid Algorithm");
                }
                exisitingUser.setAuthTokenExpiry(new Date(System.currentTimeMillis() + validityInMilliseconds));
                userRepository.save(exisitingUser);


                model.put("client", restClientName);
                model.put("token", genRefreshToken);
                model.put("success", true);

            } else {
                //model.put("client", restClientName);
                model.put("message", "Token still valid, logout and login if you want a new token");
                model.put("success", false);
            }
            return ok(model);
        } catch (AuthenticationException | NoSuchAlgorithmException e) {
            logger.error("Invalid Token: " + refreshTokenDto.getRefreshToken());
            throw new BadCredentialsException("Invalid token");
        }

    }

    @PostMapping("/authUser/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshTokenDto refreshTokenDto) throws NoSuchAlgorithmException {
        Map<Object, Object> model = new HashMap<>();
        try {
            User fetchedUser = userRepository.findByAuthToken(CommonUtils.toHexString(CommonUtils.getSHA(refreshTokenDto.getRefreshToken())));
            if (fetchedUser == null) {
                model.put("message", "User not found");
                model.put("success", false);
            } else {
                jwtTokenProvider.invalidateToken(refreshTokenDto.getRefreshToken());
                fetchedUser.setAuthTokenExpiry(null);
                fetchedUser.setAuthToken(null);
                fetchedUser.setOneTimePass(null);
                fetchedUser.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
              //  fetchedUser.setLastUpdatedBy(fetchedUser.getUserName());
                fetchedUser.setLastUpdatedBy("-1");
                userDaoService.pureUpdate(fetchedUser);
                model.put("userName", "-1");
                model.put("success", "true");
                model.put("statusDetails", "-1" + " " + " logged out successfully.");
            }
            return ok(model);
        } catch (KorikeException e) {
            logger.error(e + " : Logout failed");
            model.put("message", "Lohour failed");
            model.put("success", false);
            return ok(model);
        }
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }
}
