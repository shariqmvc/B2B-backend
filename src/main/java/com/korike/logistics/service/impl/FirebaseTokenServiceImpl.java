package com.korike.logistics.service.impl;

import com.korike.logistics.entity.FirebaseTokenDatabase;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.repository.FirebaseTokenRepository;
import com.korike.logistics.service.FirebaseTokenService;
import com.korike.logistics.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class FirebaseTokenServiceImpl implements FirebaseTokenService {
    private static Logger logger = Logger.getLogger(FirebaseTokenServiceImpl.class);
    @Autowired
    FirebaseTokenRepository firebaseTokenDatabaseRepo;

    @Override
    public FirebaseTokenDatabase createTokenEntry(FirebaseTokenDatabase firebaseTokenDatabase) {
        logger.info("Start creating firebase token entry.");
        firebaseTokenDatabase.setFirebase_token_createtime(new Timestamp(System.currentTimeMillis()));
        firebaseTokenDatabase.setFirebase_token_updatetime(new Timestamp(System.currentTimeMillis()));
        try {
            firebaseTokenDatabase = firebaseTokenDatabaseRepo.save(firebaseTokenDatabase);
        }catch(Exception exc) {
            logger.error("Exception occured in creating firebase token entry " + CommonUtils.printException(exc));
            throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while creating");
        }
        return firebaseTokenDatabase;
    }

    @Override
    public FirebaseTokenDatabase updateTokenEntry(FirebaseTokenDatabase firebaseTokenDatabase) {
        logger.info("Start updating firebase token entry.");
        FirebaseTokenDatabase firebaseTokenDatabase1 = firebaseTokenDatabaseRepo.getTokenByUserAndDeviceId(firebaseTokenDatabase.getUser_id(), firebaseTokenDatabase.getUser_device_token());
        if(firebaseTokenDatabase1!=null) {
            firebaseTokenDatabase1.setFirebase_token_updatetime(new Timestamp(System.currentTimeMillis()));
        }
        try {
            firebaseTokenDatabase1 = firebaseTokenDatabaseRepo.save(firebaseTokenDatabase1);
        }catch(Exception exc) {
            logger.error("Exception occured in updating firebase token entry " + CommonUtils.printException(exc));
            throw new KorikeException(ApiErrorCode.INTERNAL_SERVER_ERROR, "Error occured while updating");
        }
        return firebaseTokenDatabase1;
    }
}
