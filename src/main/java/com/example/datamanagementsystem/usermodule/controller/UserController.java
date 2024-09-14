package com.example.datamanagementsystem.usermodule.controller;

import ch.qos.logback.core.util.StringUtil;
import com.example.datamanagementsystem.datamodule.exception.DataSourceException;
import com.example.datamanagementsystem.usermodule.dto.LoginResponsePayload;
import com.example.datamanagementsystem.usermodule.dto.UserRegistration;
import com.example.datamanagementsystem.usermodule.exception.EmailFormatException;
import com.example.datamanagementsystem.usermodule.exception.UserRegistrationException;
import com.example.datamanagementsystem.usermodule.service.UserRegistrationService;
import com.example.datamanagementsystem.utils.CommonConstants;
import com.example.datamanagementsystem.utils.InputValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
@Tag(name = "UserController", description = "User management APIS")
@RestController
@RequestMapping(CommonConstants.USER_MAINPAGE)
@Validated
public class UserController {

    private static final Logger logger= LoggerFactory.getLogger(UserController.class);


    @Autowired
    UserRegistrationService userRegistrationService;

    @Operation(
            summary = "register new user into db",
            description = "enrolling user into library",
            tags = { "User", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(CommonConstants.USER_REGISTER)
    public ResponseEntity<String> registerUser(@RequestBody UserRegistration registration) throws EmailFormatException, UserRegistrationException, DataSourceException {
        InputValidator.validateInput(registration);
        if(!InputValidator.validateEmailPattern(registration.getEmail())){
            logger.error("error occured while validating email pattern");
            throw new EmailFormatException("Email format error!!");
        }
        userRegistrationService.saveUser(registration);
        logger.debug("successfully saved user!!");

        return new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
    }

    /**
     * This API handles the JWT token and aalong authentication and authorization for user
     * token will be generated and shared back in response
     */
    @Operation(
            summary = "login user",
            description = "creating token for successful login and the token will be used alongside the request",
            tags = { "User", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(CommonConstants.USER_LOGIN)
    public ResponseEntity<LoginResponsePayload> login(@RequestParam String email, @RequestParam String password) throws Exception {
        InputValidator.validateInput(email);
        InputValidator.validateInput(password);
     CompletableFuture<String> isAuthenticated=userRegistrationService.autheticateUser(email,password);
     if(StringUtil.notNullNorEmpty(isAuthenticated.get())){
         logger.debug("user is authenticated");

         return new ResponseEntity<>(new LoginResponsePayload(isAuthenticated.get(),"success"), HttpStatusCode.valueOf(200));
     }
        logger.error("user authetication failed!!");

        return new ResponseEntity<>(new LoginResponsePayload(isAuthenticated.get(),"Bad Request"),HttpStatusCode.valueOf(400));
    }
}
