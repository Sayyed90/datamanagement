package com.example.datamanagementsystem.datamodule.controller;


import com.example.datamanagementsystem.datamodule.DAO.Datasource;
import com.example.datamanagementsystem.datamodule.dto.DataSourceDto;
import com.example.datamanagementsystem.datamodule.exception.DataSourceException;
import com.example.datamanagementsystem.datamodule.service.DataSourceService;
import com.example.datamanagementsystem.utils.CommonConstants;
import com.example.datamanagementsystem.utils.InputValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "${angularURL}")
@Tag(name = "Data controller", description = "Data Management APIS")
@RestController
@RequestMapping(CommonConstants.DATA)
public class DataController {
    
    private static final Logger logger= LoggerFactory.getLogger(DataController.class);
    @Autowired
    DataSourceService dataSourceService;


    @Operation(
            summary = "saving new data",
            description = "adding data details into DB",
            tags = { "Data", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(CommonConstants.SAVE_DATA)
    public ResponseEntity<String> saveData(@RequestBody DataSourceDto dataSourceDto) throws DataSourceException {

        InputValidator.validateInput(dataSourceDto);

        dataSourceService.save(dataSourceDto);

        return new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
    }

    @Operation(
            summary = "retrieving all data details",
            description = "getting data details from DB",
            tags = { "Data", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(CommonConstants.RETRIEVE_ALL_DATA)
    public ResponseEntity<Page<Datasource>> retrieveAllData(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize){
        Page<Datasource> getAllData=dataSourceService.getAllData( pageNo, pageSize);
        logger.debug("get list of all data:{}",getAllData);
        return new ResponseEntity<>(getAllData, HttpStatusCode.valueOf(200));
    }

    @Operation(
            summary = "retrieving data by customer id details",
            description = "getting data details from DB",
            tags = { "Data", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(CommonConstants.RETRIEVE_BY_CUSTOMER_ID)
    public ResponseEntity<Page<Datasource>> retrieveByCustomerID(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,@PathVariable String customerId){
        Page<Datasource> getAllData=dataSourceService.getById( pageNo, pageSize,customerId);
        logger.debug("get list of data by customer id:{}",getAllData);
        return new ResponseEntity<>(getAllData, HttpStatusCode.valueOf(200));
    }

    @Operation(
            summary = "data by account number",
            description = "getting data details from DB",
            tags = { "Data", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(CommonConstants.RETRIEVE_BY_ACC_NUM)
    public ResponseEntity<Page<Datasource>> retrieveByAccountNumber(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,@PathVariable String accountNumber){
        Page<Datasource> getAllData=dataSourceService.getByAccountNumber( pageNo, pageSize,accountNumber);
        logger.debug("get list of data by account number:{}",getAllData);
        return new ResponseEntity<>(getAllData, HttpStatusCode.valueOf(200));
    }

    @Operation(
            summary = "data by description detail",
            description = "getting data details from DB",
            tags = { "Data", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(CommonConstants.RETRIEVE_BY_DESCRIPTION)
    public ResponseEntity<Page<Datasource>> retrieveByDescription(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,@PathVariable String description){
        Page<Datasource> getAllData=dataSourceService.getByDescription( pageNo, pageSize,description);
        logger.debug("get list of data by description:{}",getAllData);
        return new ResponseEntity<>(getAllData, HttpStatusCode.valueOf(200));
    }

    @Operation(
            summary = "updating description detail",
            description = "update data details from DB",
            tags = { "Data", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping(CommonConstants.UPDATE)
    public ResponseEntity<Boolean> updateByDescription(@PathVariable String id,@RequestBody DataSourceDto dataSourceDto){
        dataSourceService.updateDescription( id,dataSourceDto);
        logger.debug("updating to DB:{}");
        return new ResponseEntity<>(true, HttpStatusCode.valueOf(200));
    }

    @Operation(
            summary = "delete by id",
            description = "delete data details from DB",
            tags = { "Data", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping(CommonConstants.DELETE)
    public ResponseEntity<Boolean> updateByDescription(@PathVariable String id){
        dataSourceService.deleteData( id);
        logger.debug("updating to DB:{}");
        return new ResponseEntity<>(true, HttpStatusCode.valueOf(200));
    }


}
