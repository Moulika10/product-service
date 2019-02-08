package com.moulika.platform.productservice.controller;

import com.moulika.platform.productservice.bean.ProductBrick;
import com.moulika.platform.productservice.exception.ErrorResponse;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.ProductClassService;
import com.moulika.platform.productservice.bean.ProductClass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
@RequestMapping("v1/productClass")
@Api(value = "ProductClass", description = "These API will allow you to manage ProductClass")
public class ProductClassController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductController.class);
    private ProductClassService productClassService;

    public ProductClassController(ProductClassService productClassService) {
        this.productClassService = productClassService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all Classes")
    List<ProductClass> getAllClasses() throws ProductServiceException {
        List<ProductClass> productClass;
        productClass = productClassService.findClasses();
        if (productClass == null) {
            throw new ProductServiceException("No Product Classes available");
        }
        return productClass;
    }

    @RequestMapping(
            path = "/{classCode}/brick",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find bricks by class code")
    Set<ProductBrick> getBricksByClassCode
            (@PathVariable(value = "classCode") long classCode) throws ProductServiceException {
        ProductClass productClass = productClassService.findClassByCode(classCode);
        if (productClass == null) {
            LOGGER.error("Error ClassCode is Null:");
            throw new ProductServiceException("Not a Valid Class Code! Enter valid Class Code");
        }
        Set<ProductBrick> productBrickSet;
        productBrickSet = new HashSet<>(productClass.getBricks());
        return productBrickSet;
    }

    @RequestMapping(
            path = "/{classCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find Class by Class code")
    ProductClass getClassByClassCode
            (@PathVariable(value = "classCode") Long classCode) throws ProductServiceException {
        ProductClass productClass = productClassService.findClassByCode(classCode);
        if (productClass == null) {
            LOGGER.error("Error ClassCode is Null:");
            throw new ProductServiceException("Not a Valid Class Code! Enter valid Class Code");
        }
        return productClass;
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
