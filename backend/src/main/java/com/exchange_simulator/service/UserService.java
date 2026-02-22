package com.exchange_simulator.service;

import com.exchange_simulator.dto.user.UserRequestDto;
import com.exchange_simulator.entity.User;
import com.exchange_simulator.exceptionHandler.exceptions.database.UserAlreadyExistsException;
import com.exchange_simulator.exceptionHandler.exceptions.database.UserNotFoundException;
import com.exchange_simulator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRequestDto userData) {
        if (userRepository.existsByUsernameAndIsActiveTrue(userData.username())) {
            throw new UserAlreadyExistsException("User with username '" + userData.username() + "' already exists");
        }
        if (userRepository.existsByEmailAndIsActiveTrue(userData.email())) {
            throw new UserAlreadyExistsException("User with email '" + userData.email() + "' already exists");
        }
        String password = passwordEncoder.encode(userData.password());
        return userRepository.save(new User(userData.username(), userData.email(), password,
                "ROLE_USER", BigDecimal.valueOf(1000.0), Instant.now(), true));
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findByIdAndIsActiveTrue(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User findUserByIdWithLock(Long userId) {
        return userRepository.findByIdWithLock(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User findUserById(Long userId) {
        return userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public void updateFunds(User user, BigDecimal amount) {
        if (user.getFunds().add(amount).compareTo(BigDecimal.ZERO) < 0) {
            user.setFunds(BigDecimal.ZERO);
        } else {
            user.setFunds(user.getFunds().add(amount));
        }
        userRepository.save(user);
    }

    public User deleteUser(User user) {
        user.setIsActive(false);
        return userRepository.save(user);
    }
}
