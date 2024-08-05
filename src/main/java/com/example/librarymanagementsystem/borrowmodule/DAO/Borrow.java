package com.example.librarymanagementsystem.borrowmodule.DAO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long book_Id;

    private Long user_Id;

    private String status;

    private Date borrowedDate;

    private Date returnedDate;
}
