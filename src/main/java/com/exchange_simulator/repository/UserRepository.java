package com.exchange_simulator.repository;

import com.exchange_simulator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByName(String name);

    @Transactional
    @Modifying
    @Query("update User u set u.name = :name where u.id = :id")
    int updateNameById(Long id, String name);

    @Transactional
    @Modifying
    @Query("update User u set u.email = :email where u.id = :id")
    int updateEmailById (Long id, String email);
}