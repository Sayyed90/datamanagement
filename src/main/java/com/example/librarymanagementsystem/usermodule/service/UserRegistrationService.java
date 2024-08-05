package com.example.librarymanagementsystem.usermodule.service;

import com.example.librarymanagementsystem.usermodule.dto.UserRegistration;
import com.example.librarymanagementsystem.usermodule.exception.UserRegistrationException;

import java.util.concurrent.CompletableFuture;


public sealed interface UserRegistrationService permits UserRegistrationServiceImpl{
    void saveUser(UserRegistration registration) throws UserRegistrationException;

    CompletableFuture<String> autheticateUser(String email, String password) throws Exception;
}
