package com.SpringSecurity.MultifactorAuthentication.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Otp_Master")
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String userName;
    String otp;
}
