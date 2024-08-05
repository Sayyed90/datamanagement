package com.example.librarymanagementsystem.borrowmodule.service;

import com.example.librarymanagementsystem.borrowmodule.DAO.Borrow;
import com.example.librarymanagementsystem.borrowmodule.dto.BorrowDto;
import com.example.librarymanagementsystem.borrowmodule.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public final class BorrowServiceImpl implements BorrowService {
    @Autowired
    BorrowRepository borrowRepository;
    @Override
    public BorrowDto getDetailsByBookId(Long bookId) {
        Borrow borrowDetail=borrowRepository.getByBookId(bookId);
        BorrowDto borrowDto=null;
        if(null!=borrowDetail){
            borrowDto= mapBorrowToDto(borrowDetail);
        }
        return borrowDto;
    }

    @Override
    public void saveBorrowDetail(BorrowDto borrowDto) {
        Borrow borrowDetail=mapDtoToBorrow(borrowDto);
        borrowRepository.save(borrowDetail);
    }

    @Override
    public void deleteBookById(Long id) {
        borrowRepository.deleteById(id);
    }

    @Override
    public void updateBorrowDetail(Long id) {
       Optional<Borrow> borroDetail= borrowRepository.findById(id);
       if(borroDetail.isPresent()){
           Borrow borrows=borroDetail.get();
           borrows.setReturnedDate(new Date());
           borrowRepository.saveAndFlush(borrows);
       }
    }

    private BorrowDto mapBorrowToDto(Borrow borrowDetail) {
        BorrowDto borrowDto=new BorrowDto();
        borrowDto.setId(borrowDetail.getId());
        borrowDto.setStatus(borrowDetail.getStatus());
        borrowDto.setBorrowedDate(borrowDetail.getBorrowedDate());
        borrowDto.setReturnedDate(borrowDetail.getReturnedDate());
        borrowDto.setUser_Id(borrowDetail.getUser_Id());
        borrowDto.setBook_Id(borrowDetail.getBook_Id());
        return borrowDto;
    }

    private Borrow mapDtoToBorrow(BorrowDto borrowDto) {
        Borrow borrow=new Borrow();
        borrow.setStatus(borrowDto.getStatus());
        borrow.setBorrowedDate(new Date());
        borrow.setReturnedDate(null);
        borrow.setUser_Id(borrowDto.getUser_Id());
        borrow.setBook_Id(borrowDto.getBook_Id());
        return borrow;
    }
}
