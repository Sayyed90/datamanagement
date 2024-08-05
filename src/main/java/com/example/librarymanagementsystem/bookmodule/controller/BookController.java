package com.example.librarymanagementsystem.bookmodule.controller;

import com.example.librarymanagementsystem.bookmodule.dto.BookDto;
import com.example.librarymanagementsystem.bookmodule.exception.BookException;
import com.example.librarymanagementsystem.bookmodule.service.BookService;
import com.example.librarymanagementsystem.borrowmodule.exception.BorrowException;
import com.example.librarymanagementsystem.utils.CommonConstants;
import com.example.librarymanagementsystem.utils.InputValidator;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Tag(name = "Book controller", description = "Book Management APIS")
@RestController
@RequestMapping(CommonConstants.BOOK_PAGE)
public class BookController {
    
    private static final Logger logger= LoggerFactory.getLogger(BookController.class);
    @Autowired
    BookService bookService;

    /**
     * This API is to register a book
     * We first retrieve if there any any exisiting books by ISBNNumber
     * then we validate if there are any discrepancy between the new book details to be stored into DB and the
     * Details from already available book information in the DB. Since ISBNNumber must have same author aand title
     * we check this first before saving a new book.
     * Just for the purpose of project i put sesnsitive information in loggers debugging mode, however in
     * real-time scenario it is confidential to put sensitive information in loggers.
     * @param bookDto
     * @return
     * @throws BookException
     */
    @Operation(
            summary = "Register new book details",
            description = "adding book details into DB",
            tags = { "Book", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(CommonConstants.REGISTER_BOOK)
    public ResponseEntity<String> registerBook(@RequestBody BookDto bookDto) throws BookException, BorrowException {
        InputValidator.validateInput(bookDto);
        List<BookDto> getListOfUniqueIsbnNumbers=bookService.getIsbnNumbers();
        logger.debug("get list of unique is number:{}",getListOfUniqueIsbnNumbers);
        Optional<BookDto> listOfAllMatchedIsBnNumber= getListOfUniqueIsbnNumbers.stream().filter(x->x.getIsbnNumber().equalsIgnoreCase(bookDto.getIsbnNumber())).findFirst();

        if(listOfAllMatchedIsBnNumber.isPresent()){
            logger.debug("list of all matched ISBN number is present");
          validateAndSave(listOfAllMatchedIsBnNumber.get(),bookDto);
        }

        bookService.save(bookDto);
        logger.debug("book saved successfully");
        return new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
    }

    /**
     * Retrieving all books
     * @return
     */
    @Operation(
            summary = "retrieving all book details",
            description = "getting book details from DB",
            tags = { "Book", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(CommonConstants.RETRIEVE_ALL_BOOK)
    public ResponseEntity<List<BookDto>> retrieveAllBook(){
        List<BookDto> getAllBooks=bookService.getAllBooks();
        logger.debug("get list of all books:{}",getAllBooks);
        return new ResponseEntity<>(getAllBooks, HttpStatusCode.valueOf(200));
    }

    @Operation(
            summary = "retrieving book details by id",
            description = "getting book details from DB by using book id",
            tags = { "Book", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(CommonConstants.RETRIEVE_BY_ID)
    public ResponseEntity<BookDto> retrieveById(@PathVariable Long id) throws BorrowException {
        InputValidator.validateInput(id);
        BookDto getBook=bookService.getById(id);
        logger.debug("get a single book by ID:{}",getBook);
        return new ResponseEntity<>(getBook, HttpStatusCode.valueOf(200));
    }

    private boolean ValidateBook(BookDto bookDto, BookDto dto) {
        return InputValidator.isSameInput(bookDto.getAuthor(),dto.getAuthor())
                &&
                InputValidator.isSameInput(bookDto.getTitle(),dto.getTitle());
    }

    private void validateAndSave(BookDto bookDtoFromDb, BookDto dtoFromUser) throws BookException {
        if(!ValidateBook(bookDtoFromDb,dtoFromUser)){
            logger.error("throwing custom error since book can not be validated!");
            throw new BookException("Author name and title must be the same for given ISBN Number");
        }
    }


}
