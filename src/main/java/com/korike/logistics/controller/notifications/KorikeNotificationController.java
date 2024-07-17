package com.korike.logistics.controller.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.korike.logistics.controller.BillingModelController;
import com.korike.logistics.entity.KorikeNotification;
import com.korike.logistics.entity.User;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.repository.KorikeNotificationRepository;
import com.korike.logistics.repository.UserRepository;
import com.korike.logistics.service.KorikeNotificationPersistanceService;
import com.korike.logistics.util.CommonUtils;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/notification")
public class KorikeNotificationController {
    private static Logger logger = Logger.getLogger(KorikeNotificationController.class);
    @Autowired
    KorikeNotificationRepository korikeNotificationRepository;
    @Autowired
    KorikeNotificationPersistanceService korikeNotificationPersistanceService;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value={"/messages"}, method  = RequestMethod.GET)
    public ResponseEntity<?> getMessages(@RequestParam( required = false, name="deviceId") String deviceId) throws JsonProcessingException {
        Map<String, Object> model = new HashMap<>();
        Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        if (!fetchedUser.isPresent()) {
            logger.error("Invalid userName " + CommonUtils.getCurrentUserName());
            throw new ResourceNotFoundException("Invalid userName " + CommonUtils.getCurrentUserName());
        }
        try {
            List<KorikeNotification> korikeNotifications = korikeNotificationRepository.getAllValidMessagesForUser(fetchedUser.get().getUserId());
            model.put("status", "success");
            model.put("result", korikeNotifications);
        } catch (Exception exc) {
            logger.error("Unable to fetch notification list");
            model.put("status", "failure");
            model.put("message", "Unable to fetch notification list");
        }
        return ok(model);
    }

    @RequestMapping(value={"/message","/message/{id}"}, method  = RequestMethod.POST)
    public ResponseEntity<?> createUpdateMessage(@PathVariable(required=false) Long id, @RequestBody KorikeNotification korikeNotification) throws JsonProcessingException {
        Map<String, Object> model = new HashMap<>();
        Optional<User> fetchedUser = userRepository.findByUserName(CommonUtils.getCurrentUserName());
        if (!fetchedUser.isPresent()) {
            logger.error("Invalid userName " + CommonUtils.getCurrentUserName());
            throw new ResourceNotFoundException("Invalid userName " + CommonUtils.getCurrentUserName());
        }
            if (id != null) {
                try {
                    KorikeNotification fetchedKorikeNotification = korikeNotificationRepository.getNotificationById(id);
                    if (fetchedKorikeNotification != null) {
                        fetchedKorikeNotification = korikeNotificationPersistanceService.createUpdateNotificationEntry(korikeNotification);
                        if (fetchedKorikeNotification != null) {
                            model.put("status", "success");
                            model.put("result", fetchedKorikeNotification);
                        } else {
                            model.put("status", "false");
                            model.put("message", "Updating notificatiom failed");
                        }
                    } else {
                        model.put("status", "false");
                        model.put("message", "Fetching notificatiom failed");
                    }
                } catch (Exception exc) {
                    logger.error("Unable to fetch Notification");
                    throw new ResourceNotFoundException("Unable to fetch Notification");
                }

            } else {
                try {
                    KorikeNotification fetchedKorikeNotification = korikeNotificationRepository.save(korikeNotification);
                    model.put("status", "success");
                    model.put("result", fetchedKorikeNotification);

                } catch (Exception exc) {
                    logger.error("Unable to create notification");
                    throw new KorikeException("Unable to create notification");
                }
            }

        return ok(model);
    }
}
