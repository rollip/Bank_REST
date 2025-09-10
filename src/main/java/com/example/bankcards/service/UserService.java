package com.example.bankcards.service;

import com.example.bankcards.entity.UserEntity;

public interface UserService {
    UserEntity findById(Long id);
    UserEntity findByUsername(String username);
    UserEntity create(String username, String password);
    void delete(Long id);

}
