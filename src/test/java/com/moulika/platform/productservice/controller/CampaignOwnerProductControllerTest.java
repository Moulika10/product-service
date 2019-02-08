package com.mapp.platform.productservice.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.google.gson.Gson;
import com.mapp.platform.productservice.bean.CampaignOwnerProduct;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.service.CampaignOwnerProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class CampaignOwnerProductControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private CampaignOwnerProductService campaignOwnerProductService;

    @InjectMocks
    private CampaignOwnerProductController campaignOwnerProductController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(campaignOwnerProductController)
                .build();
    }

    @Test
    public void getProductsByCampaignId() throws Exception {
        List<CampaignOwnerProduct> campaignOwnerProducts = new ArrayList<>();
        campaignOwnerProducts.add(new CampaignOwnerProduct(null,  123, (long) 1, false));
        campaignOwnerProducts.add(new CampaignOwnerProduct(null,  123, (long) 2, false));
        campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, (long) 3, false));

        when(campaignOwnerProductService.findCampaignProductsByCampaignId(123)).thenReturn(campaignOwnerProducts);
        when(campaignOwnerProductController.getProductsByCampaignId(123, (long) 1)).thenReturn(campaignOwnerProducts);
        mockMvc.perform(get("/v1/campaignOwnerProduct?campaignId=123").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].campaignId", is(123)))
                .andExpect(jsonPath("$[0].ownerProductId", is(1)));
    }

    @Test
    public void getProductsByOwnerProductId() throws Exception {
        List<CampaignOwnerProduct> campaignOwnerProducts = new ArrayList<>();
        campaignOwnerProducts.add(new CampaignOwnerProduct(null,  123, 2, false));
        when(campaignOwnerProductService.findCampaignProductsByOwnerProductId(2L)).thenReturn(campaignOwnerProducts);
        when(campaignOwnerProductController.getProductsByCampaignId(null, 2L)).thenReturn(campaignOwnerProducts);
        mockMvc.perform(get("/v1/campaignOwnerProduct?ownerProductId=2").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].campaignId", is(123)))
                .andExpect(jsonPath("$[0].ownerProductId", is(2)));
    }

    @Test
    public void getProductsByCampaignIdWhenNoCampaignId() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No Campaign Owner Products available");
        when(campaignOwnerProductController.getProductsByCampaignId(123, null)).thenReturn(null);
    }

    @Test
    public void getProductsByCampaignIdWhenNoOwnerProductId() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No Campaign Owner Products available");
        when(campaignOwnerProductController.getProductsByCampaignId(null, (long) 1)).thenReturn(null);
    }

    @Test
    public void getProductById() throws Exception {
        CampaignOwnerProduct campaignOwnerProduct = new CampaignOwnerProduct(null,  123, 1, false);
        when(campaignOwnerProductService.findCampaignOwnerProductById((long) 1)).thenReturn(campaignOwnerProduct);
        when(campaignOwnerProductController.getProductById(1L)).thenReturn(campaignOwnerProduct);
        mockMvc.perform(get("/v1/campaignOwnerProduct/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.campaignId", is(123)))
                .andExpect(jsonPath("$.ownerProductId", is(1)));
    }

    @Test
    public void getInvalidProductId() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Enter valid Id!");
        when(campaignOwnerProductController.getProductById((long) 2)).thenReturn(null);
    }

    @Test
    public void createCampaignOwnerProduct() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "0");
        CampaignOwnerProduct campaignOwnerProduct = new CampaignOwnerProduct(null, 123, 1, false);
        when(campaignOwnerProductService.create(campaignOwnerProduct)).thenReturn(campaignOwnerProduct);
        Gson gson = new Gson();
        String json = gson.toJson(campaignOwnerProduct);
        assertEquals(map, campaignOwnerProductController.createCampaignOwnerProduct(campaignOwnerProduct));
        mockMvc.perform(post("/v1/campaignOwnerProduct").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is("0")));
    }

    @Test
    public void deleteCampaignProduct() throws Exception {
        CampaignOwnerProduct campaignOwnerProduct = new CampaignOwnerProduct(null, 123, 1, false);
        when(campaignOwnerProductService.findCampaignOwnerProductById((long) 1)).thenReturn(campaignOwnerProduct);
        doNothing().when(campaignOwnerProductService).delete(campaignOwnerProduct.getId());
        ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(r, campaignOwnerProductController.deleteCampaignOwnerProduct((long) 1));
    }

    @Test
    public void noCampaignProductToDelete() throws ProductServiceException {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("The record you want to delete does not exists with id 5");
        when(campaignOwnerProductController.deleteCampaignOwnerProduct((long) 5)).thenReturn(null);
    }
}
