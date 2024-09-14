package com.example.datamanagementsystem.datamodule.repository;

import com.example.datamanagementsystem.datamodule.DAO.Datasource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceRepository extends JpaRepository<Datasource,Long> {
    @Query("SELECT d FROM Datasource d WHERE d.customerId = :customerId")
    Page<Datasource> getByCustomerId(String customerId,Pageable pageable);
    @Query("SELECT d FROM Datasource d WHERE d.accountNumber = :accountNumber")
    Page<Datasource> getByAccountNumber(String accountNumber, Pageable pageable);
    @Query("SELECT d FROM Datasource d WHERE d.description = :description")
    Page<Datasource> getByDescription(String description, Pageable pageable);
}
