package com.example.librarymanagementsystem.borrowmodule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BorrowDto  {
    @JsonIgnore
    private Long id;
    @Nonnull
    private Long book_Id;
    @Nonnull
    private Long user_Id;
    @JsonIgnore
    private String status;
    @JsonIgnore
    private Date borrowedDate;
    @JsonIgnore
    private Date returnedDate;

    private String response;
}
