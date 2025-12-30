package com.exchange_simulator.service;

import com.exchange_simulator.entity.User;
import com.exchange_simulator.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    UserRepository repository;

    public UserService(UserRepository userRepository){
        repository = userRepository;
    }

    public void createSampleUser(){
        var user = new User("Joshua", "email@mail.email");
        repository.save(user);
    }

    public List<User> getUsers(){
        return repository.findAll();
    }
}
