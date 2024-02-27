package dev.jam.accountservice.dao.repositories;

import dev.jam.accountservice.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Optional<User> findByName(String name);

    Optional<User> findByName(String username);
}