package com.SpringSecurity.MultifactorAuthentication.Utility;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpGenerator {
    Random rand = new Random();

    public int getOtp() {
        int otp = rand.nextInt(89999) + 1000;
        return otp;
    }
}
