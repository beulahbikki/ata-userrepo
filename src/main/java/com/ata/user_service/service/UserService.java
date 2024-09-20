package com.ata.user_service.service;

import java.util.List;

import com.ata.user_service.entity.UserEntity;

public interface UserService {
    UserEntity saveUser(UserEntity user);
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Long id);
    void deleteUserById(Long id);
}

