package com.SpringSecurity.MultifactorAuthentication.Repositories;

import com.SpringSecurity.MultifactorAuthentication.Entities.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity,Long> {

    Optional<OtpEntity> findByUserName(String userName);
}
