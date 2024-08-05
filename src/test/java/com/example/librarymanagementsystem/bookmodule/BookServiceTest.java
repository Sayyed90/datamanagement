package com.example.librarymanagementsystem.bookmodule;

import com.example.librarymanagementsystem.bookmodule.DAO.Book;
import com.example.librarymanagementsystem.bookmodule.dto.BookDto;
import com.example.librarymanagementsystem.bookmodule.repository.BookRepository;
import com.example.librarymanagementsystem.bookmodule.service.BookService;
import com.example.librarymanagementsystem.bookmodule.service.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookService;

    static Book book;

    static BookDto bookDto;

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
        book_dto_list=new ArrayList<>(List.of(bookDto));
        book_list=new ArrayList<>(List.of(book));

    }

    @Test
    public void testBookRegister(){
        when(bookRepository.save(book)).thenReturn(book);
        bookService.save(bookDto);
    }

    @Test
    public void getIsbnNumbers(){
        when(bookRepository.uniqueIsbnNumbers()).thenReturn(book_list);
        List<BookDto> listOfBook= bookService.getIsbnNumbers();
        Assertions.assertEquals(1,listOfBook.size());
    }

    @Test
    public void getAllBooks(){
        when(bookRepository.findAll()).thenReturn(book_list);
        List<BookDto> listOfBook= bookService.getAllBooks();
        Assertions.assertEquals(1,listOfBook.size());
    }
    @Test
    public void getBookById(){
        Optional<Book> optBook=Optional.of(book);
        when(bookRepository.findById(book.getId())).thenReturn(optBook);
        BookDto books= bookService.getById(book.getId());
        Assertions.assertEquals(book.getAuthor(),books.getAuthor());
    }
}
