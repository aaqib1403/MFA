package com.SpringSecurity.MultifactorAuthentication.AuthenticationProviders;

import com.SpringSecurity.MultifactorAuthentication.Authentications.OtpAuthentication;
import com.SpringSecurity.MultifactorAuthentication.Entities.OtpEntity;
import com.SpringSecurity.MultifactorAuthentication.Repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import java.util.Optional;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    OtpRepository otpRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String otp = (String) authentication.getCredentials();
        Optional<OtpEntity> otpEntity = otpRepository.findByUserName(userName);
        if (otpEntity.isPresent()) {
            return new OtpAuthentication(userName, otp, new ArrayList<>());
        }
        throw new BadCredentialsException("Bad Credentials");
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(OtpAuthentication.class);
    }
}
