package com.korike.logistics.controller.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.korike.logistics.entity.KorikeNotification;
import com.korike.logistics.model.notification.NotificationData;
import com.korike.logistics.service.impl.FirebaseMessagingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class FirebaseSendNotificationController {
    private static Logger logger = Logger.getLogger(FirebaseTokenController.class);
    @Autowired
    FirebaseMessagingService firebaseMessagingService;
    public FirebaseSendNotificationController(FirebaseMessagingService firebaseMessagingService) {
        this.firebaseMessagingService = firebaseMessagingService;
    }

    @RequestMapping("/send-notification")
    @ResponseBody
    public String sendNotification(@RequestBody KorikeNotification note,
                                   @RequestParam String topic) throws FirebaseMessagingException, JsonProcessingException {
        return firebaseMessagingService.sendNotification(note, topic);
    }

}
