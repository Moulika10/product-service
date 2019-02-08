package com.mapp.platform.productservice.controller;

import com.mapp.platform.productservice.bean.dto.OwnerProductDto;
import com.mapp.platform.productservice.exception.ErrorResponse;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.service.OwnerProductService;
import com.mapp.platform.productservice.bean.OwnerProduct;
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

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
@RestController
@RequestMapping("v1/ownerProduct")
@Api(value = "OwnerProduct", description = "These API will allow you to manage Owner product data")
public class OwnerProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductController.class);
    private OwnerProductService ownerProductService;

    public OwnerProductController(OwnerProductService ownerProductService) {
        this.ownerProductService = ownerProductService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all Owner Products by Owner Id")
    List<OwnerProductDto> getProductsByOwnerId(@RequestParam("ownerId") String ownerId) throws ProductServiceException {
        List<OwnerProductDto> ownerProducts;
        ownerProducts = ownerProductService.findOwnerProductsByOwnerId(ownerId);
        if (ownerProducts.isEmpty()) {
            throw new ProductServiceException("No Owner Products available");
        }
        return ownerProducts;
    }

    @RequestMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves Owner Product by Id")
    public OwnerProductDto getProductById(@PathVariable(value = "id") long id) throws ProductServiceException {

        OwnerProductDto ownerProductDtoList = ownerProductService.findByID(id);
        if (ownerProductDtoList== null) {
            throw new ProductServiceException("Enter valid Id!");
        }
        return ownerProductDtoList;
    }

    @RequestMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ApiOperation(value = "Posts validated Owner Product")
    Map<String, String> createOwnerProduct(
            @Valid @RequestBody OwnerProduct productDto) throws ProductServiceException {
        OwnerProduct product;
        try {
            product = ownerProductService.create(productDto);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error: ", e);
            throw new ProductServiceException("Data already exists");
        }
        return Collections.singletonMap("id", Long.toString(product.getId()));
    }

    @RequestMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes Owner Product based on Id")
    ResponseEntity deleteProductOwner(@PathVariable(value = "id") long id) throws ProductServiceException {
        LOGGER.debug("deleteProductOwner");
        try {
            OwnerProduct ownerProduct = ownerProductService.findOwnerProductById(id);
            ownerProductService.delete(ownerProduct.getId());
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
