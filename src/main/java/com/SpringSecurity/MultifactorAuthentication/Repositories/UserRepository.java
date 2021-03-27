package com.SpringSecurity.MultifactorAuthentication.Repositories;

import com.SpringSecurity.MultifactorAuthentication.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

     UserEntity findByUserName(String username);
}
