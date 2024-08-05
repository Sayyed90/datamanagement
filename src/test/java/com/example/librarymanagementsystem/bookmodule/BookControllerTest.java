package com.example.librarymanagementsystem.bookmodule;

import com.example.librarymanagementsystem.bookmodule.DAO.Book;
import com.example.librarymanagementsystem.bookmodule.controller.BookController;
import com.example.librarymanagementsystem.bookmodule.dto.BookDto;
import com.example.librarymanagementsystem.bookmodule.exception.BookException;
import com.example.librarymanagementsystem.bookmodule.repository.BookRepository;
import com.example.librarymanagementsystem.bookmodule.service.BookService;
import com.example.librarymanagementsystem.bookmodule.service.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookControllerTest {

    @InjectMocks
    BookController controller;

    @MockBean
    BookRepository bookRepository;

    static Book book;

    static BookDto bookDto;

    static BookService bookService;
    static List<BookDto> book_dto_list=null;
    static List<Book> book_list=null;


    @BeforeAll
    public static void setup(){
        book=new Book();
        bookDto=new BookDto();
        book.setIsbnNumber("ABC1233");
        book.setTitle("myTitle");
        book.setAuthor("myAuthor");
        book.setId(1L);

        bookDto.setIsbnNumber("ABC1233");
        bookDto.setTitle("myTitle");
        bookDto.setAuthor("myAuthor");
        bookService=new BookServiceImpl();
        mock(BookServiceImpl.class);

        book_dto_list=new ArrayList<>(List.of(bookDto));
        book_list=new ArrayList<>(List.of(book));

    }

    @Test
    public void registerBookTestSealedInterface() throws BookException {

       Assertions.assertThrows(NullPointerException.class,()->controller.registerBook(bookDto));
    }

}
