package com.wisedevlife.whytalkauth.repository;

import com.wisedevlife.whytalkauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}
