package com.store.repositories;

import com.store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User save(User u);
    Optional<User> findByEmail(String email);
    boolean  existsByEmail(String email);
}
