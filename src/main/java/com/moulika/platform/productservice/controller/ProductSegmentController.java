package com.mapp.platform.productservice.controller;

import com.mapp.platform.productservice.bean.ProductSegment;
import com.mapp.platform.productservice.exception.ErrorResponse;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.service.ProductSegmentService;
import com.mapp.platform.productservice.bean.ProductFamily;
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
@RequestMapping("v1/productSegment")
@Api(value = "SegmentEntity", description = "These API will allow you to manage segments")
public class ProductSegmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductController.class);
    private ProductSegmentService productSegmentService;

    public ProductSegmentController(ProductSegmentService productSegmentService) {
        this.productSegmentService = productSegmentService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all segments")
    List<ProductSegment> getAllSegments() throws ProductServiceException {
        List<ProductSegment> productSegment;
        productSegment = productSegmentService.findSegments();
        if (productSegment == null) {
            throw new ProductServiceException("No Product Segments available");
        }
        return productSegment;
    }

    @RequestMapping(
            path = "/{segmentCode}/family",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find families by segment code")
    Set<ProductFamily> getFamiliesBySegmentCode
            (@PathVariable(value = "segmentCode") Long segmentCode) throws ProductServiceException {
        ProductSegment segment = productSegmentService.findSegmentByCode(segmentCode);
        if (segment == null) {
            LOGGER.error("Error Segment Code is Null");
            throw new ProductServiceException("Not a Valid Segment Code! Enter valid Segment Code");
        }
        Set<ProductFamily> productFamilySet;
        productFamilySet = new HashSet<>(segment.getFamilies());
        return productFamilySet;
    }

    @RequestMapping(
            path = "/{segmentCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find Segment by segment code")
    ProductSegment getSegmentBySegmentCode
            (@PathVariable(value = "segmentCode") Long segmentCode) throws ProductServiceException {
        ProductSegment segment = productSegmentService.findSegmentByCode(segmentCode);
        if (segment == null) {
            LOGGER.error("Error Segment Code is Null");
            throw new ProductServiceException("Not a Valid Segment Code! Enter valid Segment Code");
        }
        return segment;
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}