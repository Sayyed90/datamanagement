package com.example.datamanagementsystem.datamodule.batch;

import com.example.datamanagementsystem.datamodule.DAO.Datasource;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Configuration
public class BatchConfig {

    @Autowired
    public JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @Value("${inputFile}")
    String resourcee;

    @Bean
    public ExecutionContext context(){
        return new ExecutionContext();
    }

    @Bean
    public Step step1() throws Exception {
        return new StepBuilder("step1", jobRepository)
                .<List<Datasource>, List<Datasource>>chunk(100, transactionManager)
                .reader(DataItemReader())
                .writer(DataItemWriter())
                .build();
    }

    @Bean
    @Qualifier("runJob")
    public Job runJob() throws Exception {
        return new JobBuilder("datasource", jobRepository)
                .start(step1())
                .build();
    }

    @Bean
    public ItemWriter<List<Datasource>> DataItemWriter() {
        return new DataWritter();
    }
    @Bean
    public ItemReader<List<Datasource>> DataItemReader() throws URISyntaxException {
        List<Datasource> returnData=listOfData();
        return () -> returnData;
    }
    @Bean
    public List<Datasource> listOfData() throws URISyntaxException {
        List<Datasource> dataSources=new ArrayList<>();
        URL resource = BatchConfig.class.getResource(resourcee);
        assert resource != null;
        try (Stream<String> stream = Files.lines(Paths.get(resource.toURI()))) {
            stream.skip(1).forEach(x->{
                Datasource dtSource=new Datasource();
                String[] split=x.split("\\|");
                dtSource.setAccountNumber(split[0]);
                dtSource.setTrxAmmount(Double.parseDouble(split[1]));
                dtSource.setDescription(split[2]);
                dtSource.setTrxDate(split[3]);
                dtSource.setTime(split[4]);
                dtSource.setCustomerId(split[5]);
                dataSources.add(dtSource);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataSources;

    }


}