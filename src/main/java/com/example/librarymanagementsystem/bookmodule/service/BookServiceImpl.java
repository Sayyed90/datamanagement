package com.example.librarymanagementsystem.bookmodule.service;

import com.example.librarymanagementsystem.bookmodule.DAO.Book;
import com.example.librarymanagementsystem.bookmodule.dto.BookDto;
import com.example.librarymanagementsystem.bookmodule.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class BookServiceImpl implements BookService {

    private static final Logger logger= LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    BookRepository bookRepository;
    @Override
    public void save(BookDto bookDto) {
        Book book=mapBooks(bookDto);
        logger.debug("get mapped book from DTO:{}",book);
        bookRepository.save(book);
    }

    @Override
    public List<BookDto> getIsbnNumbers() {
        List<Book> getListOfAllBooksByIsbn= bookRepository.uniqueIsbnNumbers();
        logger.debug("get list of unique isBN number:{}",getListOfAllBooksByIsbn);
        return getListOfAllBooksByIsbn.stream().map(this::mapBookToDto).toList();
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> listOfBook=bookRepository.findAll();
        logger.debug("get list of all book: {}",listOfBook);
        return listOfBook.stream().map(this::mapBookToDto).toList();
    }

    @Override
    public BookDto getById(Long id) {
        BookDto getBookDto=null;
        Optional<Book> book=bookRepository.findById(id);

        if(book.isPresent()){
            Book getBook=book.get();
            logger.debug("get book: {}",getBook);
            getBookDto=mapBookToDto(getBook);
        }
        return getBookDto;
    }

    private BookDto mapBookToDto(Book book) {
        BookDto bookDto=new BookDto();
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTitle(book.getTitle());
        bookDto.setIsbnNumber(book.getIsbnNumber());
        return bookDto;
    }

    private Book mapBooks(BookDto bookDto) {
        Book book=new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setIsbnNumber(bookDto.getIsbnNumber());
        return book;
    }
}
