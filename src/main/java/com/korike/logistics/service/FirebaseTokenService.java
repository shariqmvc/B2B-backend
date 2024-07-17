package com.korike.logistics.service;

import com.korike.logistics.entity.FirebaseTokenDatabase;
import org.springframework.stereotype.Service;

@Service
public interface FirebaseTokenService {
    public FirebaseTokenDatabase createTokenEntry(FirebaseTokenDatabase firebaseTokenDatabase);
    public FirebaseTokenDatabase updateTokenEntry(FirebaseTokenDatabase firebaseTokenDatabase);

}
