package com.moulika.platform.productservice.controller;

import com.moulika.platform.productservice.bean.CampaignOwnerProduct;
import com.moulika.platform.productservice.exception.ErrorResponse;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.CampaignOwnerProductService;
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
@RequestMapping("v1/campaignOwnerProduct")
@Api(value = "CampaignProduct", description = "These API will allow you to manage Campaign product data")
public class CampaignOwnerProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignOwnerProductController.class);
    private CampaignOwnerProductService campaignOwnerProductService;

    public CampaignOwnerProductController(CampaignOwnerProductService campaignOwnerProductService) {
        this.campaignOwnerProductService = campaignOwnerProductService;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves Campaign Products by Campaign Id and Owner ProductId")
    List<CampaignOwnerProduct> getProductsByCampaignId(@RequestParam(value = "campaignId", required = false) Integer campaignId, @RequestParam(value = "ownerProductId", required = false) Long ownerProductId) throws ProductServiceException {
        List<CampaignOwnerProduct> campaignOwnerProducts;
        if (campaignId != null) {
            campaignOwnerProducts = campaignOwnerProductService.findCampaignProductsByCampaignId(campaignId);
            if (campaignOwnerProducts.isEmpty()) {
                throw new ProductServiceException("No Campaign Owner Products available");
            }
        } else {
            campaignOwnerProducts = campaignOwnerProductService.findCampaignProductsByOwnerProductId(ownerProductId);
            if (campaignOwnerProducts.isEmpty()) {
                throw new ProductServiceException("No Campaign Owner Products available");
            }
        }
        return campaignOwnerProducts;
    }

    @RequestMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves Campaign Owner Product by Id")
    CampaignOwnerProduct getProductById(@PathVariable(value = "id") long id) throws ProductServiceException {
        CampaignOwnerProduct campaignOwnerProduct = campaignOwnerProductService.findCampaignOwnerProductById(id);
        if (campaignOwnerProduct == null) {
            LOGGER.error("Error: is Null ");
            throw new ProductServiceException("Enter valid Id!");
        }
        return campaignOwnerProduct;
    }

    @RequestMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ApiOperation(value = "Posts validated Campaign Owner Product")
    @ResponseBody
    Map<String, String> createCampaignOwnerProduct(
            @Valid @RequestBody CampaignOwnerProduct campaignOwnerProduct) throws ProductServiceException {
        CampaignOwnerProduct product;
        try {
            product = campaignOwnerProductService.create(campaignOwnerProduct);
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
    @ApiOperation(value = "Deletes Campaign Owner Product based on Id")
    ResponseEntity deleteCampaignOwnerProduct(@PathVariable(value = "id") long id) throws ProductServiceException {
        LOGGER.debug("deleteCampaignOwnerProduct");
        try {
            CampaignOwnerProduct campaignOwnerProduct = campaignOwnerProductService.findCampaignOwnerProductById(id);
            campaignOwnerProductService.delete(campaignOwnerProduct.getId());
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
