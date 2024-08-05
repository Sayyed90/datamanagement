package com.example.librarymanagementsystem.borrowmodule;

import com.example.librarymanagementsystem.borrowmodule.DAO.Borrow;
import com.example.librarymanagementsystem.borrowmodule.dto.BorrowDto;
import com.example.librarymanagementsystem.borrowmodule.repository.BorrowRepository;
import com.example.librarymanagementsystem.borrowmodule.service.BorrowServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class BorrowServiceTest {


    @Mock
    BorrowRepository borrowRepository;

    @InjectMocks
    BorrowServiceImpl borrowService;

    static Borrow borrow;
    static BorrowDto borrowDto;

    @BeforeAll
    public static void setup(){
        borrow=new Borrow();
        borrow.setId(1L);
        borrow.setUser_Id(3L);
        borrow.setBook_Id(2L);
        borrow.setStatus("borrowed");
        borrow.setBorrowedDate(new Date());
        borrow.setReturnedDate(null);
        borrowDto=new BorrowDto();

    }

    @Test
    public void getDetailsByBookId(){
        when(borrowRepository.getByBookId(borrow.getId())).thenReturn(borrow);
        BorrowDto borrowDetail=borrowService.getDetailsByBookId(borrow.getId());
        Assertions.assertEquals("borrowed",borrowDetail.getStatus());
    }

    @Test
    public void saveBorrowDetail(){
        when(borrowRepository.save(borrow)).thenReturn(borrow);
        borrowService.saveBorrowDetail(borrowDto);
    }

    @Test
    public void deleteBookById(){
            Mockito.doNothing().when(borrowRepository).deleteById(ArgumentMatchers.isA(Long.class));
            borrowRepository.deleteById(borrow.getId());
            verify(borrowRepository,times(1)).deleteById(borrow.getId());
            borrowRepository.deleteById(borrow.getId());
    }

    @Test
    public void updateBorrowDetail(){
        Optional<Borrow> optBorrow=Optional.of(borrow);
        when(borrowRepository.findById(borrow.getId())).thenReturn(optBorrow);
        when(borrowRepository.saveAndFlush(borrow)).thenReturn(borrow);
        borrowService.updateBorrowDetail(borrowDto.getId());
    }
}
