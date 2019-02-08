package com.moulika.platform.productservice.controller;

import com.moulika.platform.productservice.bean.ProductClass;
import com.moulika.platform.productservice.bean.ProductFamily;
import com.moulika.platform.productservice.exception.ErrorResponse;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.ProductFamilyService;
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
@RequestMapping("v1/productFamily")
@Api(value = "ProductFamily", description = "These API will allow you to manage ProductFamily")
public class ProductFamilyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductController.class);
    private ProductFamilyService productFamilyService;

    public ProductFamilyController(ProductFamilyService productFamilyService) {
        this.productFamilyService = productFamilyService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all families")
    List<ProductFamily> getAllFamilies() throws ProductServiceException {
        List<ProductFamily> productFamily;
        productFamily = productFamilyService.findFamilies();
        if (productFamily == null) {
            throw new ProductServiceException("No Product Families available");
        }
        return productFamily;
    }

    @RequestMapping(
            path = "/{familyCode}/class",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find classes by family code")
    Set<ProductClass> getClassesByFamilyCode
            (@PathVariable(value = "familyCode") long familyCode) throws ProductServiceException {
        ProductFamily family = productFamilyService.findFamilyByCode(familyCode);
        if (family == null) {
            LOGGER.error("Error Family Code is Null");
            throw new ProductServiceException("Not a Valid Family Code! Enter valid Family Code");
        }
        Set<ProductClass> productClassSet;
        productClassSet = new HashSet<>(family.getClasses());
        return productClassSet;
    }

    @RequestMapping(
            path = "/{familyCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find Family by Family Code")
    ProductFamily getFamilyByFamilyCode
            (@PathVariable(value = "familyCode") Long familyCode) throws ProductServiceException {
        ProductFamily family = productFamilyService.findFamilyByCode(familyCode);
        if (family == null) {
            LOGGER.error("Error Family Code is Null");
            throw new ProductServiceException("Not a Valid Family Code! Enter valid Family Code");
        }
        return family;
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
