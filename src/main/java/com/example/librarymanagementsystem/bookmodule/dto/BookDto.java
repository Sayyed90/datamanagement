package com.example.librarymanagementsystem.bookmodule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {
    private String isbnNumber;
    private String title;
    private String author;
}
