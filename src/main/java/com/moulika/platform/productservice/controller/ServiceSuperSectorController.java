package com.mapp.platform.productservice.controller;

import com.mapp.platform.productservice.bean.ServiceSuperSector;
import com.mapp.platform.productservice.exception.ErrorResponse;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.bean.ServiceSubSector;
import com.mapp.platform.productservice.service.ServiceSuperSectorService;
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
@RequestMapping("v1/serviceSuperSector")
@Api(value = "SuperSectorEntity", description = "These API will allow you to manage SuperSector")
public class ServiceSuperSectorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductController.class);
    private ServiceSuperSectorService serviceSuperSectorService;

    public ServiceSuperSectorController(ServiceSuperSectorService serviceSuperSectorService) {
        this.serviceSuperSectorService = serviceSuperSectorService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all SuperSectors")
    List<ServiceSuperSector> getAllSuperSectors() throws ProductServiceException {
        List<ServiceSuperSector> serviceSuperSectors;
        serviceSuperSectors = serviceSuperSectorService.findSuperSectors();
        if (serviceSuperSectors == null) {
            throw new ProductServiceException("No SuperSectors available");
        }
        return serviceSuperSectors;
    }

    @RequestMapping(
            path = "/{superSectorCode}/subSector",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find SubSectors by SuperSectorCode")
    Set<ServiceSubSector> getSubSectorsBySuperSectorCode
            (@PathVariable(value = "superSectorCode") String superSectorCode) throws ProductServiceException {
        ServiceSuperSector serviceSupersector = serviceSuperSectorService.findSuperSectorByCode(superSectorCode);
        if (serviceSupersector == null) {
            LOGGER.error("Error SuperSector Code is Null");
            throw new ProductServiceException("Not a valid SuperSectorCode! Enter a valid Super Sector Code");
        }
        Set<ServiceSubSector> serviceSubSectorSet;
        serviceSubSectorSet = new HashSet<>(serviceSupersector.getSubSectors());
        return serviceSubSectorSet;
    }

    @RequestMapping(
            path = "/{superSectorCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find SuperSector by SuperSectorCode")
    ServiceSuperSector getSuperSectorBySuperSectorCode
            (@PathVariable(value = "superSectorCode") String superSectorCode) throws ProductServiceException {
        ServiceSuperSector serviceSupersector = serviceSuperSectorService.findSuperSectorByCode(superSectorCode);
        if (serviceSupersector == null) {
            LOGGER.error("Error SuperSector Code is Null");
            throw new ProductServiceException("Not a valid SuperSectorCode! Enter a valid Super Sector Code");
        }
        return serviceSupersector;
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
