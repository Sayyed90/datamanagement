package com.example.librarymanagementsystem.borrowmodule.repository;

import com.example.librarymanagementsystem.borrowmodule.DAO.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow,Long> {
    @Query("SELECT b FROM Borrow b WHERE b.book_Id = :bookId")
    Borrow getByBookId(Long bookId);
}
