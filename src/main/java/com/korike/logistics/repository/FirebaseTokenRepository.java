package com.korike.logistics.repository;

import com.korike.logistics.entity.FirebaseTokenDatabase;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FirebaseTokenRepository extends JpaRepository<FirebaseTokenDatabase, String> {
    @Query(value = "select * from firebase_token_database where user_id = :user_id and user_device_token = :user_device_token", nativeQuery = true)
    FirebaseTokenDatabase getTokenByUserAndDeviceId(@Param("user_id") String user_id, @Param("user_device_token") String deviceId);

}
