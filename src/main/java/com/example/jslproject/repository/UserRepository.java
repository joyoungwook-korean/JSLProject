package com.example.jslproject.repository;

import com.example.jslproject.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndProviderAndUsername(String email, String provider, String username);
    User findByEmailAndProvider(String email, String provider);
    User findByUsernameAndProvider(String username, String provider);
}
