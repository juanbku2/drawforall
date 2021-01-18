package com.drawsforall.oauthserver.persistance;


import com.drawsforall.oauthserver.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String user);
}
