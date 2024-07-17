package com.korike.logistics.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.korike.logistics.entity.KorikeNotification;
import com.korike.logistics.model.notification.NotificationData;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }


    public String sendNotification(KorikeNotification note, String token) throws FirebaseMessagingException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        NotificationData notificationDataMap = objectMapper.readValue(note.getNotification_data(), NotificationData.class);
        Notification notification = Notification
                .builder()
                .setTitle(note.getNotification_subject())
                .setBody(note.getNotification_content())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(notificationDataMap.getData())
                .build();

        return firebaseMessaging.send(message);
    }

}