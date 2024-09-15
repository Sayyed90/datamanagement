package com.example.datamanagementsystem.datamodule;

import com.example.datamanagementsystem.datamodule.DAO.Datasource;
import com.example.datamanagementsystem.datamodule.controller.DataController;
import com.example.datamanagementsystem.datamodule.dto.DataSourceDto;
import com.example.datamanagementsystem.datamodule.exception.DataSourceException;
import com.example.datamanagementsystem.datamodule.service.DataSourceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;


@SpringBootTest
public class DataControllerTest {

    @InjectMocks
    DataController controller;

    @Mock
    DataSourceService dataSourceService;

    static DataSourceDto dataSourceDto;
    static Datasource datasource;
    static Page<Datasource> pages;
    @BeforeAll
    public static void setup(){
        dataSourceDto=new DataSourceDto();
        datasource=new Datasource();
        dataSourceDto.setCustomerId("123");
        dataSourceDto.setTrxAmmount(10.00);
        dataSourceDto.setDescription("testDescription");
        dataSourceDto.setTrxDate("2024-09-09");
        dataSourceDto.setTime("12:00:00");
        dataSourceDto.setAccountNumber("1111");

        datasource.setCustomerId("123");
        datasource.setTrxAmmount(10.00);
        datasource.setDescription("testDescription");
        datasource.setTrxDate("2024-09-09");
        datasource.setTime("12:00:00");
        datasource.setAccountNumber("1111");
        datasource.setId(0L);
        pages=new PageImpl<>(List.of(datasource));
    }

    @Test
    public void testSaveMethod() throws DataSourceException {
        doNothing().when(dataSourceService).save(dataSourceDto);
        controller.saveData(dataSourceDto);
        verify(dataSourceService,times(1)).save(dataSourceDto);
    }

    @Test
    public void retrieveAllData() throws DataSourceException {
        int pageNo=1;
        int pageSize=1;
        when(dataSourceService.getAllData( pageNo, pageSize)).thenReturn(pages);
        ResponseEntity<Page<Datasource>> resEntity= controller.retrieveAllData(pageNo,pageSize);
        Assertions.assertEquals(HttpStatusCode.valueOf(200),resEntity.getStatusCode());
        Assertions.assertEquals(1, Objects.requireNonNull(resEntity.getBody()).stream().toList().size());
        Assertions.assertEquals("123", Objects.requireNonNull(resEntity.getBody()).stream().toList().get(0).getCustomerId());

    }

    @Test
    public void retrieveDataByCustId() throws DataSourceException {
        int pageNo=1;
        int pageSize=1;
        String customerId="123";
        when(dataSourceService.getById( pageNo, pageSize,customerId)).thenReturn(pages);
        ResponseEntity<Page<Datasource>> resEntity= controller.retrieveByCustomerID(pageNo,pageSize,customerId);
        Assertions.assertEquals(HttpStatusCode.valueOf(200),resEntity.getStatusCode());
        Assertions.assertEquals(1, Objects.requireNonNull(resEntity.getBody()).stream().toList().size());
        Assertions.assertEquals(customerId, Objects.requireNonNull(resEntity.getBody()).stream().toList().get(0).getCustomerId());

    }

    @Test
    public void retrieveDataByAccNum() throws DataSourceException {
        int pageNo=1;
        int pageSize=1;
        String accNumber="1111";
        when(dataSourceService.getByAccountNumber( pageNo, pageSize,accNumber)).thenReturn(pages);
        ResponseEntity<Page<Datasource>> resEntity= controller.retrieveByAccountNumber(pageNo,pageSize,accNumber);
        Assertions.assertEquals(HttpStatusCode.valueOf(200),resEntity.getStatusCode());
        Assertions.assertEquals(1, Objects.requireNonNull(resEntity.getBody()).stream().toList().size());
        Assertions.assertEquals(accNumber, Objects.requireNonNull(resEntity.getBody()).stream().toList().get(0).getAccountNumber());

    }

    @Test
    public void retrieveDataByDesc() throws DataSourceException {
        int pageNo=1;
        int pageSize=1;
        String description="testDescription";
        when(dataSourceService.getByDescription( pageNo, pageSize,description)).thenReturn(pages);
        ResponseEntity<Page<Datasource>> resEntity= controller.retrieveByDescription(pageNo,pageSize,description);
        Assertions.assertEquals(HttpStatusCode.valueOf(200),resEntity.getStatusCode());
        Assertions.assertEquals(1, Objects.requireNonNull(resEntity.getBody()).stream().toList().size());
        Assertions.assertEquals(description, Objects.requireNonNull(resEntity.getBody()).stream().toList().get(0).getDescription());
    }

    @Test
    public void updateDetail() throws DataSourceException {
        int id=1;
        String description="testDescription";
        doNothing().when(dataSourceService).updateDescription(String.valueOf(id),dataSourceDto);
        controller.updateByDescription(String.valueOf(id),dataSourceDto);
        verify(dataSourceService,times(1)).updateDescription(String.valueOf(1),dataSourceDto); }

}
