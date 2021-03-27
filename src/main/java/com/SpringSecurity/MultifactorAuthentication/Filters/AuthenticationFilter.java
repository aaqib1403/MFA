package com.SpringSecurity.MultifactorAuthentication.Filters;

import com.SpringSecurity.MultifactorAuthentication.Authentications.OtpAuthentication;
import com.SpringSecurity.MultifactorAuthentication.Authentications.UsernamePasswordAuthentication;
import com.SpringSecurity.MultifactorAuthentication.Entities.OtpEntity;
import com.SpringSecurity.MultifactorAuthentication.Repositories.OtpRepository;
import com.SpringSecurity.MultifactorAuthentication.Services.MyUserDetailsService;
import com.SpringSecurity.MultifactorAuthentication.Utility.JwtUtil;
import com.SpringSecurity.MultifactorAuthentication.Utility.OtpGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    OtpGenerator otpGenerator;
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean avoid  =  true;
        if(request.getServletPath().contains("/login")){
            avoid = false;
        }
        return avoid;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String userName = httpServletRequest.getHeader("userName");
        String jwt = null;
        String password = httpServletRequest.getHeader("password");
        String otp = httpServletRequest.getHeader("otp");
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }
        // Two steps for Authentication
        // Step1: After Successful login OTP is Generated
        // Step2: With the OTP Token is generated
        if (otp == null) {
            // Step1
            Authentication a = new UsernamePasswordAuthentication(userName, password);
            authenticationManager.authenticate(a);  // have to give out authentication object to Authentication Manager
            // This Authentication Manager decides the right Authentication Provider
            String code = String.valueOf(otpGenerator.getOtp());
            OtpEntity otpEntity = new OtpEntity();
            otpEntity.setUserName(userName);
            otpEntity.setOtp(code);
            otpRepository.save(otpEntity);

        } else if (otp != null && jwt == null) {
            //Step2
            Authentication a = new OtpAuthentication(userName, otp);
            authenticationManager.authenticate(a);

            // Generate the JWT Here and Attach it to the Response Header
            // We can try to move this code in Otp Authentication Provider

            UserDetails user = myUserDetailsService.loadUserByUsername(userName);
             jwt = jwtUtil.generateToken(user);
             httpServletResponse.setHeader("JWT",jwt);

        }
        // this part needs to Verify again

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
