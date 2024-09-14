package com.example.datamanagementsystem.datamodule.service;

import com.example.datamanagementsystem.datamodule.DAO.Datasource;
import com.example.datamanagementsystem.datamodule.dto.DataSourceDto;
import org.springframework.data.domain.Page;

public interface DataSourceService{
    void save(DataSourceDto bookDto);

    Page<Datasource> getAllData(int pageNo, int pageSize);

    Page<Datasource> getById(int pageNo, int pageSize, String customerId);

    Page<Datasource> getByAccountNumber(int pageNo, int pageSize, String accountNumber);

    Page<Datasource> getByDescription(int pageNo, int pageSize, String description);

    void updateDescription(String id, DataSourceDto dataSourceDto);

    void deleteData(String id);
}
