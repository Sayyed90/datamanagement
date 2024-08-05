package com.example.librarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MySQL is used because the data is not that big and MySql is more than enough for a lightweight application
 * unless if handling with big data then we may go for mongodb or PL/SQL if we have specific functions to write or procedures
 */
@SpringBootApplication
@EnableAutoConfiguration
public class LibrarymanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarymanagementsystemApplication.class, args);
	}

}

