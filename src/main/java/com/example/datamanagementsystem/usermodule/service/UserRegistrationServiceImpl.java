package com.example.datamanagementsystem.usermodule.service;

import com.example.datamanagementsystem.configuration.JwtTokenUtil;
import com.example.datamanagementsystem.usermodule.DAO.User;
import com.example.datamanagementsystem.usermodule.dto.UserRegistration;
import com.example.datamanagementsystem.usermodule.exception.UserRegistrationException;
import com.example.datamanagementsystem.usermodule.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private static final Logger logger= LoggerFactory.getLogger(UserRegistrationServiceImpl.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * This Api we used to store user into DB. If the user email exist then we will throw error
     * @param registration
     * @throws UserRegistrationException
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveUser(UserRegistration registration) throws UserRegistrationException {
        User user=mapEntityFromDto(registration);
        logger.debug("user detail:{}", user);
        if(!isUserExist(user.getEmail())){
            logger.debug("user does not exist in DB so can proceed to register!!");
            userRepository.saveAndFlush(user);
        }else{
            logger.error("user email already exist in records!");
            //throw new UserRegistrationException("Email already existed!");
        }


    }

    private boolean isUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * This method is used to autheticate user with db and token generated to be shared with user
     * The method is asynchronous is because assuming that multiple requests are coming therefore
     * we handle each request asynchronously
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    @Async
    public CompletableFuture<String> autheticateUser(String email, String password) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            password
                    )
            );
            logger.debug("user is authenticated successfully");
        } catch (DisabledException e) {
            logger.error("user is authenticated");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        } // the above two catch block can be combined into one by catching generic exception
          // however i prefer this way to cover Junit test

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        final String token = jwtTokenUtil.generateToken(userDetails);
        logger.debug("user token ;{}",token);// this is confidential and should never be printed in logs.
        return CompletableFuture.completedFuture(token);
    }

    private User mapEntityFromDto(UserRegistration registration) {
        User user=new User();
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setName(registration.getName());
        user.setAge(registration.getAge());
        user.setEmail(registration.getEmail());
        return user;
    }
}
