package com.example.datamanagementsystem.usermodule.service;

import com.example.datamanagementsystem.usermodule.dto.UserRegistration;
import com.example.datamanagementsystem.usermodule.exception.UserRegistrationException;

import java.util.concurrent.CompletableFuture;


public interface UserRegistrationService{
    void saveUser(UserRegistration registration) throws UserRegistrationException;

    CompletableFuture<String> autheticateUser(String email, String password) throws Exception;
}
