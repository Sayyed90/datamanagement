package com.example.datamanagementsystem.datamodule.service;

import com.example.datamanagementsystem.datamodule.DAO.Datasource;
import com.example.datamanagementsystem.datamodule.dto.DataSourceDto;
import com.example.datamanagementsystem.datamodule.repository.DataSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    private static final Logger logger= LoggerFactory.getLogger(DataSourceServiceImpl.class);
    @Autowired
    DataSourceRepository dataSourceRepository;
    @Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(DataSourceDto dataSourceDto) {
        Datasource getMappedSource=mapToDataSource("1",dataSourceDto);
        dataSourceRepository.save(getMappedSource);
    }

    @Override
    public Page<Datasource> getAllData(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return dataSourceRepository.findAll(pageable);
    }



    @Override
    public Page<Datasource> getById(int pageNo, int pageSize, String customerId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return dataSourceRepository.getByCustomerId(customerId,pageable);
    }

    @Override
    public Page<Datasource> getByAccountNumber(int pageNo, int pageSize, String accountNumber) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return dataSourceRepository.getByAccountNumber(accountNumber,pageable);
    }

    @Override
    public Page<Datasource> getByDescription(int pageNo, int pageSize, String description) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return dataSourceRepository.getByDescription(description,pageable);
    }

    /**
     * Making the isolation as Serializable so that when a concurrent update happens
     * only one will be execyted at a time as even if parallelly run but it guarantees
     * the data to be no data overlapping
     * @param id
     * @param dataSourceDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void updateDescription(String id, DataSourceDto dataSourceDto) {
        Datasource getMappedSource=mapToDataSource(id,dataSourceDto);
        dataSourceRepository.save(getMappedSource);
    }

    @Override
    public void deleteData(String id) {
        dataSourceRepository.deleteById(Long.valueOf(id));
    }

    private Datasource mapToDataSource(String id, DataSourceDto dataSourceDto) {
        return new Datasource(Long.valueOf(id), dataSourceDto.getAccountNumber(), dataSourceDto.getTrxAmmount(), dataSourceDto.getDescription(),dataSourceDto.getTrxDate(),dataSourceDto.getTime(),dataSourceDto.getCustomerId());
    }

}
