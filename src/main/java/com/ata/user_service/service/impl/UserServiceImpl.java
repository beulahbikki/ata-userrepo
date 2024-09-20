package com.ata.user_service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ata.user_service.entity.UserEntity;
import com.ata.user_service.exception.UserNotFoundException;
import com.ata.user_service.repository.UserRepository;
import com.ata.user_service.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder encoder;
   
    public UserEntity createUser(UserEntity user) {
        if (user.getUsername() == null) {
            throw new IllegalArgumentException("User name cannot be null");
        }
        return userRepository.save(user);
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        logger.info("Saving user: {}", user);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

   @Override
    public List<UserEntity> getAllUsers() {
        logger.info("Retrieving all users");
        return userRepository.findAll();
    }
    @Override
    public UserEntity getUserById(Long id) {
        logger.info("Fetching user by id: {}", id);
        return userRepository.findById(id).orElseThrow(() -> {
            logger.error("User not found with id: {}", id);
            return new UserNotFoundException("User not found with id: " + id);
        });
    }

    @Override
    public void deleteUserById(Long id) {
        logger.info("Deleting user by id: {}", id);
        if (!userRepository.existsById(id)) {
            logger.error("User not found with id: {}", id);
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
