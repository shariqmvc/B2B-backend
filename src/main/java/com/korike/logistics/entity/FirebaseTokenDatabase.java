package com.korike.logistics.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="firebase_token_database")
public class FirebaseTokenDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private String user_id;

    @Column(name="user_device_token")
    private String user_device_token;

    @Column(name="user_firebase_token")
    private String user_firebase_token;

    @Column(name="firebase_token_timestamp")
    private String firebase_token_timestamp;

    @Column(name="firebase_token_status", nullable = true)
    private String firebase_token_status;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
    private Timestamp firebase_token_createtime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
    private Timestamp firebase_token_updatetime;

}
