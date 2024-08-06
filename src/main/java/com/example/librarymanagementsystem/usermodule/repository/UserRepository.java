package com.example.librarymanagementsystem.usermodule.repository;

import com.example.librarymanagementsystem.usermodule.DAO.User;
import com.example.librarymanagementsystem.usermodule.exception.UserRegistrationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.mockito.Mockito.when;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    @Test
    default void testUserRegistrationWhenEmailExisted(UserRegistrationServiceTest userRegistrationServiceTest) {
        Optional<User> userOpt=Optional.of(new User());
        Mockito.when(findByEmail("mockEmail@gmail.com")).thenReturn(userOpt);
        Assertions.assertThrows(UserRegistrationException.class,()-> userRegistrationServiceTest.userRegistrationService.saveUser(UserRegistrationServiceTest.userRegistration));authenticationManager3aqwq;
    }
}
