package com.SpringSecurity.MultifactorAuthentication.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "UserDetails_Master")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String userName;
    String password;

}
