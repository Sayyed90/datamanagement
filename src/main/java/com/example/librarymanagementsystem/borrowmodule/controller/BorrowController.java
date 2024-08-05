package com.example.librarymanagementsystem.borrowmodule.controller;

import com.example.librarymanagementsystem.borrowmodule.dto.BorrowDto;
import com.example.librarymanagementsystem.borrowmodule.exception.BorrowException;
import com.example.librarymanagementsystem.borrowmodule.service.BorrowService;
import com.example.librarymanagementsystem.utils.CommonConstants;
import com.example.librarymanagementsystem.utils.InputValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
@Tag(name = "Borrow", description = "Borrow management APIs")
@RestController
@RequestMapping(CommonConstants.BORROW_PAGE)
public class BorrowController {
    @Autowired
    BorrowService borrowService;

    @Operation(
            summary = "Borrow details saving",
            description = "save borrower details on book borrowed and validate if the same book is borrowed by more than one member, the system will prompt error",
            tags = { "Borrow", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = BorrowDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(CommonConstants.BORROW_SAVE)
    public ResponseEntity<String> borrow(@Valid @RequestBody BorrowDto borrow) throws BorrowException {
        InputValidator.validateInput(borrow);
        BorrowDto borrowedDetail= borrowService.getDetailsByBookId(borrow.getBook_Id());

        Date getReturnedDate=null;

        if(null!=borrowedDetail){
            getReturnedDate=borrowedDetail.getReturnedDate();
            if(InputValidator.isSameInput(borrowedDetail.getBook_Id().toString(),borrow.getBook_Id().toString()) &&
                    InputValidator.isSameInput(borrowedDetail.getUser_Id().toString(),borrow.getUser_Id().toString())){
                throw new BorrowException("Can not borrow the same book!!");
            }
        }

        if(ObjectUtils.isEmpty(getReturnedDate) || InputValidator.isBookReturned(getReturnedDate)){
            borrow.setStatus(CommonConstants.BORROWED);
            borrowService.saveBorrowDetail(borrow);
            return new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        }else{
            throw new BorrowException("Can not borrow this book!!");
        }
    }
    @Operation(
            summary = "Borrow details deletion",
            description = "deleting the borrower details by book_id",
            tags = { "Borrow", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping(CommonConstants.BORROW_DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable String book_id) throws BorrowException {
        InputValidator.validateInput(book_id);
        try{
            BorrowDto borrowedDetail= borrowService.getDetailsByBookId(Long.valueOf(book_id));
            if(null!=borrowedDetail){
                borrowService.deleteBookById(borrowedDetail.getId());
                return new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
            }
        }catch(Exception ex){
           throw new BorrowException("cannot delete a borrow details");
        }
        return new ResponseEntity<>("error", HttpStatusCode.valueOf(400));
    }

    @Operation(
            summary = "Updating DB when user returning the book",
            description = "updating the borrower details by book_id",
            tags = { "Borrow", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping(CommonConstants.BORROW_RETURN)
    public ResponseEntity<String> returnBook(@PathVariable String book_id) throws BorrowException {
        InputValidator.validateInput(book_id);
        try{
            BorrowDto borrowedDetail= borrowService.getDetailsByBookId(Long.valueOf(book_id));
            if(null!=borrowedDetail){
                borrowService.updateBorrowDetail(borrowedDetail.getId());
                return new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
            }
        }catch(Exception ex){
            throw new BorrowException("cannot update a borrow details");
        }
        return new ResponseEntity<>("error", HttpStatusCode.valueOf(400));
    }

}
