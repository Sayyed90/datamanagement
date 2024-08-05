package com.example.librarymanagementsystem.borrowmodule.service;

import com.example.librarymanagementsystem.borrowmodule.dto.BorrowDto;

public sealed interface BorrowService permits BorrowServiceImpl {
    BorrowDto getDetailsByBookId(Long bookId);
    void saveBorrowDetail(BorrowDto borrowDto);

    void deleteBookById(Long id);

    void updateBorrowDetail(Long id);
}
