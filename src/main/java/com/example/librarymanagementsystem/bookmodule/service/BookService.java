package com.example.librarymanagementsystem.bookmodule.service;

import com.example.librarymanagementsystem.bookmodule.dto.BookDto;

import java.util.List;

public sealed interface BookService permits BookServiceImpl{
    void save(BookDto bookDto);

    List<BookDto> getIsbnNumbers();

    List<BookDto> getAllBooks();

    BookDto getById(Long id);
}
