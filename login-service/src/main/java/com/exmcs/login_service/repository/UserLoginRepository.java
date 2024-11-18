package com.exmcs.login_service.repository;

import com.exmcs.login_service.model.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    Optional<UserLogin> findByUsername(String username);
}
