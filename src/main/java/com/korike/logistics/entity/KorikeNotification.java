package com.korike.logistics.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Map;

@Data
@Entity
@Table(name="korike_notification")
public class KorikeNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="notification_type", length=100)
    private String notification_type;

    @Column(name="notification_subject", nullable = true, columnDefinition="TEXT")
    private String notification_subject;

    @Column(name="notification_content", nullable = true, columnDefinition="TEXT")
    private String notification_content;

    @Column(name="notification_image", nullable = true, columnDefinition="TEXT")
    private String notification_image;

    @Column(name="notification_data", nullable = true, columnDefinition="TEXT")
    private String notification_data;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
    private Timestamp notification_create_time;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
    private Timestamp notification_expiry_time;

    @Column(name="notification_target_device", nullable = true, columnDefinition="TEXT")
    private String notification_target_device;

    @Column(name="notification_target_user", nullable = true, columnDefinition="TEXT")
    private String notification_target_user;

    @Column(name="is_ga", length=100)
    private Boolean is_ga;

    @Column(name="is_valid", length=100)
    private Boolean is_valid;

}
