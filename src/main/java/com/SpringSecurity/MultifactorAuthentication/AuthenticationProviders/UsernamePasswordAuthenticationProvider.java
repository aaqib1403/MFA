package com.SpringSecurity.MultifactorAuthentication.AuthenticationProviders;

import com.SpringSecurity.MultifactorAuthentication.Authentications.UsernamePasswordAuthentication;
import com.SpringSecurity.MultifactorAuthentication.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails user = myUserDetailsService.loadUserByUsername(userName);
        if (bCryptPasswordEncoder.matches(password,user.getPassword())) {
            return new UsernamePasswordAuthentication(userName, password, user.getAuthorities());// have to call this 3 arg constructor because it has a super method that sets setAuthenticated to true
            // but the 2 arg constructor will not set it to true .. This has to do to get the Authenticated Object
        }
        throw new BadCredentialsException("Bad Credentials");
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthentication.class);
    }
}
