package com.example.librarymanagementsystem.borrowmodule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BorrowControllerAdvice {

    @ExceptionHandler(BorrowException.class)
    public ResponseEntity<String> handleException(BorrowException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + ex.getMessage());
    }
}
