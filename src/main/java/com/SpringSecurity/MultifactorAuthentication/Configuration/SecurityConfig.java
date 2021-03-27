package com.SpringSecurity.MultifactorAuthentication.Configuration;

import com.SpringSecurity.MultifactorAuthentication.AuthenticationProviders.OtpAuthenticationProvider;
import com.SpringSecurity.MultifactorAuthentication.AuthenticationProviders.UsernamePasswordAuthenticationProvider;
import com.SpringSecurity.MultifactorAuthentication.Authentications.UsernamePasswordAuthentication;
import com.SpringSecurity.MultifactorAuthentication.Filters.AuthenticationFilter;
import com.SpringSecurity.MultifactorAuthentication.Filters.JWTFilter;
import com.SpringSecurity.MultifactorAuthentication.Services.MyUserDetailsService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    @Autowired
    OtpAuthenticationProvider otpAuthenticationProvider;
    @Autowired
    AuthenticationFilter authenticationFilter;
    @Autowired
    JWTFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider).authenticationProvider(otpAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers(new String[]{"/MFA/register","/MFA/login"}).permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAt(authenticationFilter, BasicAuthenticationFilter.class).addFilterAfter(jwtFilter,AuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
