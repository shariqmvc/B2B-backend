package com.korike.logistics.service;

import com.korike.logistics.entity.KorikeNotification;
import org.springframework.stereotype.Service;

@Service
public interface KorikeNotificationPersistanceService {
    public KorikeNotification createUpdateNotificationEntry(KorikeNotification korikeNotification);
    public KorikeNotification updateNotificationEntry(KorikeNotification korikeNotification);
    public KorikeNotification deleteNotificationEntry(KorikeNotification korikeNotification);

}
