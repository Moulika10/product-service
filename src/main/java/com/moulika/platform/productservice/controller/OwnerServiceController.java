package com.mapp.platform.productservice.controller;

import com.mapp.platform.productservice.bean.OwnerService;
import com.mapp.platform.productservice.bean.dto.OwnerServiceDto;
import com.mapp.platform.productservice.exception.ErrorResponse;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.service.OwnerServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

@SuppressWarnings("ConstantConditions")
@RestController
@RequestMapping("v1/ownerService")
@Api(value = "OwnerService", description = "These API will allow you to manage Owner service data")
public class OwnerServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductController.class);
    private OwnerServiceService ownerServiceService;


    public OwnerServiceController(OwnerServiceService ownerServiceService) {
        this.ownerServiceService = ownerServiceService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all Owner Service data by OwnerId")
    List<OwnerServiceDto> getServicesByOwnerId(@RequestParam("ownerId") String ownerId) throws ProductServiceException {
        List<OwnerServiceDto> ownerServices;
        ownerServices = ownerServiceService.findOwnerServicesByOwnerId(ownerId);
        if (ownerServices.isEmpty()) {
            throw new ProductServiceException("No Owner Services available");
        }
        return ownerServices;
    }

    @RequestMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves Owner Service by Id")
    OwnerServiceDto getServiceById(@PathVariable(value = "id") long id) throws ProductServiceException {
        OwnerServiceDto ownerService = ownerServiceService.findByID(id);
        if (ownerService == null) {
            LOGGER.error("Error: is Null ");
            throw new ProductServiceException("Enter valid Id!");
        }
        return ownerService;
    }

    @RequestMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ApiOperation(value = "Posts validated Owner Service")
    @ResponseBody
    Map<String, String> createOwnerService(
            @Valid @RequestBody OwnerService serviceDto) throws ProductServiceException {
        OwnerService service;
        try {
            service = ownerServiceService.create(serviceDto);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error: ", e);
            throw new ProductServiceException("Data already exists ");
        }
        return Collections.singletonMap("id", Long.toString(service.getId()));
    }

    @RequestMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes Owner Service based on Id")
    ResponseEntity deleteOwnerService(@PathVariable(value = "id") long id) throws ProductServiceException {
        try {
            OwnerService ownerService = ownerServiceService.findOwnerServiceById(id);
            ownerServiceService.delete(ownerService.getId());
        } catch (NullPointerException e) {
            LOGGER.error("Error: ", e);
            throw new ProductServiceException("The record you want to delete does not exists with id " + id);
        }
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
