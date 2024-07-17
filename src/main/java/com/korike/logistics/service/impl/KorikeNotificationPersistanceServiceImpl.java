package com.korike.logistics.service.impl;

import com.korike.logistics.entity.KorikeNotification;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.repository.KorikeNotificationRepository;
import com.korike.logistics.service.KorikeNotificationPersistanceService;
import com.korike.logistics.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class KorikeNotificationPersistanceServiceImpl implements KorikeNotificationPersistanceService {
    private static Logger logger = Logger.getLogger(KorikeNotificationPersistanceServiceImpl.class);
    @Autowired
    KorikeNotificationRepository korikeNotificationRepository;

    @Override
    public KorikeNotification createUpdateNotificationEntry(KorikeNotification korikeNotification) {
        logger.info("Started creating notification entry");

        korikeNotification.setNotification_create_time(new Timestamp(System.currentTimeMillis()));
        try {
            korikeNotification = korikeNotificationRepository.save(korikeNotification);
        } catch (Exception exc) {
            logger.error("Exception occured in creating notification entry " + CommonUtils.printException(exc));
            throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while creating");
        }
        return korikeNotification;
    }

    @Override
    public KorikeNotification updateNotificationEntry(KorikeNotification korikeNotification) {
        return null;
    }

    @Override
    public KorikeNotification deleteNotificationEntry(KorikeNotification korikeNotification) {
        return null;
    }
}
