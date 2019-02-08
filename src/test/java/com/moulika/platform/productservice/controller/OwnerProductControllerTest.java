package com.moulika.platform.productservice.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.google.gson.Gson;
import com.moulika.platform.productservice.bean.OwnerProduct;
import com.moulika.platform.productservice.bean.dto.OwnerProductDto;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.OwnerProductService;

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
public class OwnerProductControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private OwnerProductService ownerProductService;

    @InjectMocks
    private OwnerProductController ownerProductController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerProductController)
                .build();
    }

    @Test
    public void getProductsByOwnerId() throws Exception {
        List<OwnerProductDto> ownerProducts = new ArrayList<>();
        ownerProducts.add(new OwnerProductDto((long) 1, "397d2-f8528e", "Autoles", 10002135, "Water/Beverage Equipment Other",73040200,"Water/Beverage Equipment"));
        ownerProducts.add(new OwnerProductDto((long) 2, "397d2-f8528e", "Laminate Trimmers", (long) 10003626, "Laminate Trimmers",82010400,"Power Tools - Hand-held Portable"));
        when(ownerProductService.findOwnerProductsByOwnerId("397d2-f8528e")).thenReturn(ownerProducts);
        when(ownerProductController.getProductsByOwnerId("397d2-f8528e")).thenReturn(ownerProducts);
        mockMvc.perform(get("/v1/ownerProduct?ownerId=397d2-f8528e").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].ownerId", is("397d2-f8528e")))
                .andExpect(jsonPath("$[0].productName", is("Autoles")))
                .andExpect(jsonPath("$[0].brickCode", is(10002135)))
                .andExpect(jsonPath("$[0].brickDescription", is("Water/Beverage Equipment Other")))
                .andExpect(jsonPath("$[0].classCode", is(73040200)))
                .andExpect(jsonPath("$[0].classDescription", is("Water/Beverage Equipment")));
    }

    @Test
    public void getProductById() throws Exception {
        OwnerProductDto ownerProductDto = new OwnerProductDto(1, "397d2-f8528e", "Cortaderia Selloana - Live Plants", 10006600, "Cortaderia Selloana - Live Plants", 93033900,"Cortaderia - Live Plants");
        when(ownerProductService.findByID(1)).thenReturn(ownerProductDto);
        when(ownerProductController.getProductById( 1)).thenReturn(ownerProductDto);
        mockMvc.perform(get("/v1/ownerProduct/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andExpect(jsonPath("$.ownerId", is("397d2-f8528e")))
                .andExpect(jsonPath("$.productName", is("Cortaderia Selloana - Live Plants")))
                .andExpect(jsonPath("$.brickCode", is(10006600)))
                .andExpect(jsonPath("$.brickDescription", is("Cortaderia Selloana - Live Plants")))
                .andExpect(jsonPath("$.classCode", is(93033900)))
                .andExpect(jsonPath("$.classDescription", is("Cortaderia - Live Plants")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getInvalidProductId() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Enter valid Id!");
        when(ownerProductController.getProductById((long) 2)).thenReturn(null);
    }

    @Test
    public void getInvalidProductOwnerId() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No Owner Products available");
        when(ownerProductController.getProductsByOwnerId("123")).thenReturn(null);
    }

    @Test
    public void createOwnerProduct() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "0");
        OwnerProduct ownerProduct = new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals", 10000467, false);
        when(ownerProductService.create(ownerProduct)).thenReturn(ownerProduct);
        Gson gson = new Gson();
        String json = gson.toJson(ownerProduct);
        assertEquals(map, ownerProductController.createOwnerProduct(ownerProduct));
        mockMvc.perform(post("/v1/ownerProduct").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is("0")));
    }

    @Test
    public void deleteOwnerProduct() throws Exception {
        OwnerProduct ownerProduct = new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals", 10000467, false);
        when(ownerProductService.findOwnerProductById((long) 1)).thenReturn(ownerProduct);
        doNothing().when(ownerProductService).delete(ownerProduct.getId());
        ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(r, ownerProductController.deleteProductOwner((long) 1));
    }

    @Test
    public void noOwnerProductToDelete() throws ProductServiceException {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("The record you want to delete does not exists with id 5");
        when(ownerProductController.deleteProductOwner((long) 5)).thenReturn(null);
    }

}

