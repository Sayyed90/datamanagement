package com.example.datamanagementsystem.datamodule.batch;

import com.example.datamanagementsystem.datamodule.DAO.Datasource;
import com.example.datamanagementsystem.datamodule.repository.DataSourceRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataWritter implements ItemWriter<List<Datasource>> {
    @Autowired
    DataSourceRepository dataSourceRepository;


    @Override
    public void write(Chunk<? extends List<Datasource>> chunk) throws Exception {
        Set<Datasource> flatSet = new HashSet<>();

        for (List<Datasource> list : chunk.getItems()) {
            flatSet.addAll(list);
        }
        List<Datasource> convertBackAfterDuplicateRemoval= new ArrayList<>(flatSet.stream().toList());
       List<Datasource> getDataFromExistingTable= dataSourceRepository.findAll();

        convertBackAfterDuplicateRemoval.removeAll(getDataFromExistingTable);
        dataSourceRepository.saveAll(convertBackAfterDuplicateRemoval);
    }

}
