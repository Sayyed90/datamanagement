package com.example.datamanagementsystem.usermodule;

import com.example.datamanagementsystem.configuration.JwtTokenUtil;
import com.example.datamanagementsystem.usermodule.DAO.User;
import com.example.datamanagementsystem.usermodule.dto.UserRegistration;
import com.example.datamanagementsystem.usermodule.exception.UserRegistrationException;
import com.example.datamanagementsystem.usermodule.repository.UserRepository;
import com.example.datamanagementsystem.usermodule.service.UserRegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserRegistrationServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserDetailsService userDetailsService;
    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserDetails userDetails;

    @Mock
    Authentication authetication;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @Mock
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    @InjectMocks
    UserRegistrationServiceImpl userRegistrationService;

    static UserRegistration userRegistration;
    static User user;
    @BeforeAll
    public static void setup(){
        userRegistration=new UserRegistration();
        userRegistration.setAge(10);
        userRegistration.setName("mockName");
        userRegistration.setEmail("mockEmail@gmail.com");
        userRegistration.setPassword("mockPassword");

        user=new User();
        user.setEmail(userRegistration.getEmail());
        user.setName(userRegistration.getName());
        user.setAge(userRegistration.getAge());
        user.setId(1L);
        user.setPassword(userRegistration.getPassword());
    }

    @Test
    public void testUserRegistration() throws UserRegistrationException {
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        userRegistrationService.saveUser(userRegistration);
    }

    @Test
    public void testUserRegistrationAutheticate() throws Exception {
        String expectedEmail="mockEmail@gmail.com";
        String expectedPassword="mockPassword";
        String mockToken="abcdkwefkwjkwf";
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(expectedEmail,expectedPassword)
        )).thenReturn(authetication);
        when(userDetailsService.loadUserByUsername(expectedEmail)).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(mockToken);
        CompletableFuture<String> completedToken=userRegistrationService.autheticateUser(expectedEmail,expectedPassword);
        String token=completedToken.get();
        Assertions.assertEquals(mockToken,token);
      }

    @Test
    public void testUserRegistrationThrowDisabledException() throws Exception {
        String expectedEmail="mockEmail@gmail.com";
        String expectedPassword="mockPassword";
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(expectedEmail,expectedPassword)
        )).thenThrow(DisabledException.class);

        Assertions.assertThrows(Exception.class,()->userRegistrationService.autheticateUser(expectedEmail,expectedPassword));
    }

    @Test
    public void testUserRegistrationThrowInvalidCredException() throws Exception {
        String expectedEmail="mockEmail@gmail.com";
        String expectedPassword="mockPassword";
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(expectedEmail,expectedPassword)
        )).thenThrow(BadCredentialsException.class);

        Assertions.assertThrows(Exception.class,()->userRegistrationService.autheticateUser(expectedEmail,expectedPassword));
    }
}
