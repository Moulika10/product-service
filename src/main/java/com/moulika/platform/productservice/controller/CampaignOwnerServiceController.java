package com.moulika.platform.productservice.controller;

import com.moulika.platform.productservice.bean.CampaignOwnerService;
import com.moulika.platform.productservice.exception.ErrorResponse;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.CampaignOwnerServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
@RestController
@RequestMapping("v1/campaignOwnerService")
@Api(value = "CampaignService", description = "These API will allow you to manage Campaign service data")
public class CampaignOwnerServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignOwnerServiceController.class);
    private CampaignOwnerServiceService campaignOwnerServiceService;

    public CampaignOwnerServiceController(CampaignOwnerServiceService campaignOwnerServiceService) {
        this.campaignOwnerServiceService = campaignOwnerServiceService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves Campaign Services by Campaign Id and Owner ServiceId")
    List<CampaignOwnerService> getServicesByCampaignId(@RequestParam(value = "campaignId", required = false) Integer campaignId, @RequestParam(value = "ownerServiceId", required = false) Long ownerServiceId) throws ProductServiceException {
        List<CampaignOwnerService> campaignOwnerServices;
        if (campaignId != null) {
            campaignOwnerServices = campaignOwnerServiceService.findCampaignOwnerServicesByCampaignId(campaignId);
            if (campaignOwnerServices.isEmpty()) {
                throw new ProductServiceException("No Campaign Owner Services available");
            }
        } else {
            campaignOwnerServices = campaignOwnerServiceService.findCampaignOwnerServicesByOwnerServiceId(ownerServiceId);
            if (campaignOwnerServices.isEmpty()) {
                throw new ProductServiceException("No Campaign Owner Services available");
            }
        }
        return campaignOwnerServices;
    }

    @RequestMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves Campaign Service by Id")
    CampaignOwnerService getServiceById(@PathVariable(value = "id") long id) throws ProductServiceException {
        CampaignOwnerService campaignOwnerService = campaignOwnerServiceService.findCampaignOwnerServiceById(id);
        if (campaignOwnerService == null) {
            LOGGER.error("Error: is Null ");
            throw new ProductServiceException("Enter valid Id!");
        }
        return campaignOwnerService;
    }

    @RequestMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ApiOperation(value = "Posts validated Campaign Owner Service")
    @ResponseBody
    Map<String, String> createCampaignOwnerService(
            @Valid @RequestBody CampaignOwnerService campaignOwnerService) throws ProductServiceException {
        CampaignOwnerService service;
        try {
            service = campaignOwnerServiceService.create(campaignOwnerService);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error: ", e);
            throw new ProductServiceException("Data already exists");
        }
        return Collections.singletonMap("id", Long.toString(service.getId()));
    }

    @RequestMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes Campaign Owner Service based on Id")
    ResponseEntity deleteCampaignOwnerService(@PathVariable(value = "id") long id) throws ProductServiceException {
        LOGGER.debug("deleteCampaignOwnerService");
        try {
            CampaignOwnerService campaignOwnerService = campaignOwnerServiceService.findCampaignOwnerServiceById(id);
            campaignOwnerServiceService.delete(campaignOwnerService.getId());
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
