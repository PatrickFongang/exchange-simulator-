package com.exchange_simulator.repository;

import com.exchange_simulator.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.id = :id and u.isActive = true")
    Optional<User> findByIdWithLock(Long id);
    Optional<User> findByIdAndIsActiveTrue(Long id);
    boolean existsByUsernameAndIsActiveTrue(String username);
    boolean existsByEmailAndIsActiveTrue(String email);
    @Query("select u from User u where u.username = :username and u.isActive = true")
    Optional<User> findByUsername(String username);

}