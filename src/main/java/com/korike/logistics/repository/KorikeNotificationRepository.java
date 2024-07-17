package com.korike.logistics.repository;

import com.korike.logistics.entity.KorikeNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KorikeNotificationRepository extends JpaRepository<KorikeNotification,String> {

    @Query(value = "select * from korike_notification where (user_id = :user_id or user_id = null) and is_ga = true and is_valid = true", nativeQuery = true)
    List<KorikeNotification> getAllValidMessagesForUser(@Param("user_id") String user_id);

    @Query(value = "select * from korike_notification where id = :id", nativeQuery = true)
    KorikeNotification getNotificationById(@Param("id") Long id);

}
