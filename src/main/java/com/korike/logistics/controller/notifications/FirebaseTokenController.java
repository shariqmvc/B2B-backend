package com.korike.logistics.controller.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.korike.logistics.controller.BillingModelController;
import com.korike.logistics.entity.FirebaseTokenDatabase;
import com.korike.logistics.entity.User;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.BillingModelDto;
import com.korike.logistics.model.SubCategoryDto;
import com.korike.logistics.repository.FirebaseTokenRepository;
import com.korike.logistics.repository.UserRepository;
import com.korike.logistics.service.FirebaseTokenService;
import com.korike.logistics.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/notification")
public class FirebaseTokenController {
    private static Logger logger = Logger.getLogger(FirebaseTokenController.class);
    @Autowired
    FirebaseTokenRepository firebaseTokenRepository;
    @Autowired
    FirebaseTokenService firebaseTokenService;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value={"/token"}, method  = RequestMethod.GET)
    public ResponseEntity<?> getToken(@RequestParam( required = false, name="deviceId") String deviceId) throws JsonProcessingException {
        Map<String, Object> model = new HashMap<>();
        Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        if (fetchedUser.isPresent()) {
            logger.info("Starting to fetch valid token for user: " + fetchedUser.get().getUserName());
            try {
                FirebaseTokenDatabase firebaseTokenDatabase = firebaseTokenRepository.getTokenByUserAndDeviceId(fetchedUser.get().getUserId(), deviceId);
                if (null != firebaseTokenDatabase) {
                    model.put("status", "success");
                    model.put("result", firebaseTokenDatabase);
                } else {
                    logger.error("No records found.");
                    model.put("status", "success");
                    model.put("message", "No records found for requested user/device");
                }
            } catch (Exception exc) {
                logger.error("Exception occured in fetching firebase token " + CommonUtils.printException(exc));
                model.put("status", "failure");
                model.put("message", "Failed fetching token for requested user/device");
                return ok(model);
            }
        }
        return ok(model);
    }

    @RequestMapping(value={"/token"}, method  = RequestMethod.POST)
    public ResponseEntity<?> createUpdateFirebaseToken(@RequestBody FirebaseTokenDatabase firebaseTokenDatabase) throws JsonProcessingException {
        Map<String, Object> model = new HashMap<>();
        logger.info("Creating or Updating firebase token");

        Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        if (!fetchedUser.isPresent()) {
            logger.error("Invalid userName " + CommonUtils.getCurrentUserName());
            throw new ResourceNotFoundException("Invalid userName " + CommonUtils.getCurrentUserName());
        }
        firebaseTokenDatabase.setUser_id(fetchedUser.get().getUserId());
        FirebaseTokenDatabase fetchedFirebaseTokenDatabase = firebaseTokenRepository.getTokenByUserAndDeviceId(fetchedUser.get().getUserId(), firebaseTokenDatabase.getUser_device_token());
        if (fetchedFirebaseTokenDatabase != null) {
            fetchedFirebaseTokenDatabase = firebaseTokenService.updateTokenEntry(firebaseTokenDatabase);
            model.put("status", "success");
            model.put("message", "Updated existing token entry");
        } else {
            fetchedFirebaseTokenDatabase = firebaseTokenService.createTokenEntry(firebaseTokenDatabase);
            model.put("status", "success");
            model.put("message", "Created new token entry");
        }
        model.put("result", fetchedFirebaseTokenDatabase);
        return ok(model);
    }

}
