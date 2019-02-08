package com.mapp.platform.productservice.controller;

import com.mapp.platform.productservice.bean.ProductBrick;
import com.mapp.platform.productservice.exception.ErrorResponse;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.service.ProductBrickService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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
@RequestMapping("v1/productBrick")
@Api(value = "ProductBrick", description = "These API will allow you to manage ProductBrick")
public class ProductBrickController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductController.class);
    private ProductBrickService productBrickService;

    public ProductBrickController(ProductBrickService productBrickService) {
        this.productBrickService = productBrickService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all Bricks")
    List<ProductBrick> getAllBricks() throws ProductServiceException {
        List<ProductBrick> productBrick;
        productBrick = productBrickService.findBricks();
        if (productBrick == null) {
            throw new ProductServiceException("No Product Bricks available");
        }
        return productBrick;
    }

    @RequestMapping(
            path = "/{brickCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find Brick by Brick code")
    ProductBrick getBrickByBrickCode
            (@PathVariable(value = "brickCode") Long brickCode) throws ProductServiceException {
        ProductBrick productBrick = productBrickService.findBrickByCode(brickCode);
        if (productBrick == null) {
            LOGGER.error("Error BrickCode is Null:");
            throw new ProductServiceException("Not a Valid Brick Code! Enter valid Brick Code");
        }
        return productBrick;
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
