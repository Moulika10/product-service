package com.moulika.platform.productservice.controller;

import com.moulika.platform.productservice.exception.ErrorResponse;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.ServiceIndustryGroupService;
import com.moulika.platform.productservice.bean.ServiceIndustryGroup;
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
@RequestMapping("v1/serviceIndustryGroup")
@Api(value = "IndustryGroupEntity", description = "These API will allow you to manage industryGroups")
public class ServiceIndustryGroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductController.class);
    private ServiceIndustryGroupService serviceIndustryGroupService;

    public ServiceIndustryGroupController(ServiceIndustryGroupService serviceIndustryGroupService) {
        this.serviceIndustryGroupService = serviceIndustryGroupService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all IndustryGroups")
    List<ServiceIndustryGroup> getAllIndustryGroups() throws ProductServiceException {
        List<ServiceIndustryGroup> serviceIndustryGroups;
        serviceIndustryGroups = serviceIndustryGroupService.findIndustryGroups();
        if (serviceIndustryGroups == null) {
            throw new ProductServiceException("No IndustryGroups available");
        }
        return serviceIndustryGroups;
    }

    @RequestMapping(
            path = "/{industryGroupCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Find IndustryGroup by industryGroupCode")
    ServiceIndustryGroup getIndustryGroupByIndustryGroupCode
            (@PathVariable(value = "industryGroupCode") String industryGroupCode) throws ProductServiceException {
        ServiceIndustryGroup serviceIndustryGroup = serviceIndustryGroupService.findIndustryGroupByCode(industryGroupCode);
        if (serviceIndustryGroup == null) {
            LOGGER.error("Error IndustryGroup Code is Null");
            throw new ProductServiceException("Not a Valid IndustryGroupCode! Enter a Valid IndustryGroupCode");
        }
        return serviceIndustryGroup;
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
