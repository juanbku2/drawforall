package com.drawsforall.usermanagement.persistance;

import com.drawsforall.usermanagement.persistance.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<Page<User>> findUserByFirstNameContaining(String firstName, Pageable of);
    Optional<Page<User>> findUserByLastNameContaining(String lastName, Pageable of);
    Optional<User> findByUsername(String user);
}
