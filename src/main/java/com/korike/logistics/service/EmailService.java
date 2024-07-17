package com.korike.logistics.service;

public interface EmailService {

    public void addEmailtoAllowList(String emailAddress);
    public void sendOtpToEmail(String email, String otp);
}
