package com.moulika.platform.productservice.controller;

import com.moulika.platform.productservice.exception.ErrorResponse;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.ServiceSubSectorService;
import com.moulika.platform.productservice.bean.ServiceIndustryGroup;
import com.moulika.platform.productservice.bean.ServiceSubSector;
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
@RequestMapping("v1/serviceSubSector")
@Api(value = "SubSectorEntity", description = "These API will allow you to manage SubSector")
public class ServiceSubSectorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductController.class);
    private ServiceSubSectorService serviceSubSectorService;

    public ServiceSubSectorController(ServiceSubSectorService serviceSubSectorService) {
        this.serviceSubSectorService = serviceSubSectorService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all SubSectors")
    List<ServiceSubSector> getAllSubSectors() throws ProductServiceException {
        List<ServiceSubSector> serviceSubSectors;
        serviceSubSectors = serviceSubSectorService.findSubSectors();
        if (serviceSubSectors == null) {
            throw new ProductServiceException("No SubSectors available");
        }
        return serviceSubSectors;
    }

    @RequestMapping(
            path = "/{subSectorCode}/industryGroup",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find industry groups by SubSectorCode")
    Set<ServiceIndustryGroup> getIndustryGroupsBySubSectorCode
            (@PathVariable(value = "subSectorCode") String subSectorCode) throws ProductServiceException {
        ServiceSubSector serviceSubsector = serviceSubSectorService.findSubSectorByCode(subSectorCode);
        if (serviceSubsector == null) {
            LOGGER.error("Error SubSector Code is Null");
            throw new ProductServiceException("Not a Valid SubSectorCode! Enter a Valid SubSectorCode");
        }
        Set<ServiceIndustryGroup> serviceIndustryGroupSet;
        serviceIndustryGroupSet = new HashSet<>(serviceSubsector.getIndustryGroups());
        return serviceIndustryGroupSet;
    }

    @RequestMapping(
            path = "/{subSectorCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find SubSector by subSectorCode")
    ServiceSubSector getSubSectorBySubSectorCode
            (@PathVariable(value = "subSectorCode") String subSectorCode) throws ProductServiceException {
        ServiceSubSector serviceSubsector = serviceSubSectorService.findSubSectorByCode(subSectorCode);
        if (serviceSubsector == null) {
            LOGGER.error("Error SubSector Code is Null");
            throw new ProductServiceException("Not a Valid SubSectorCode! Enter a Valid SubSectorCode");
        }
        return serviceSubsector;
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
