package com.mapp.platform.productservice.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapp.platform.productservice.bean.ProductBrick;
import com.mapp.platform.productservice.bean.ProductClass;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.service.ProductClassService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductClassControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private ProductClassService productClassService;

    @InjectMocks
    private ProductClassController productClassController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(productClassController)
                .build();
    }

    @Test
    public void getAllClasses() throws Exception {
        List<ProductClass> productClasses = new ArrayList<>();
        productClasses.add(new ProductClass(null,47101900, "Surface Care", null));
        productClasses.add(new ProductClass(null, 83012500, "Access Covers/Panels", null));
        productClasses.add(new ProductClass(null, 91050100, "Baby Safety/Security/Surveillance", null));
        when(productClassService.findClasses()).thenReturn(productClasses);
        when(productClassController.getAllClasses()).thenReturn(productClasses);
        mockMvc.perform(get("/v1/productClass").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].classCode", is(47101900)))
                .andExpect(jsonPath("$[0].classDescription", is("Surface Care")));
    }

    @Test
    public void getClassByClassCode() throws Exception {
        ProductClass productClass = new ProductClass(null, 1L, "Personal Accessories", null);
        when(productClassService.findClassByCode((long) 1)).thenReturn(productClass);
        when(productClassController.getClassByClassCode((long) 1)).thenReturn(productClass);
        mockMvc.perform(get("/v1/productClass/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.classCode", is(1)))
                .andExpect(jsonPath("$.classDescription", is("Personal Accessories")));
    }

    @Test
    public void getBricksByClassCode() throws Exception {
        Set<ProductBrick> productBricks = new HashSet<>();
        productBricks.add(new ProductBrick(null, 94020000, "Crops for Food Production"));
        productBricks.add(new ProductBrick(null, 50340000, "Nuts/Seeds - Unprepared/Unprocessed (Shelf Stable)"));
        ProductClass productClass = new ProductClass(null, 64000000, "Personal Accessories", productBricks);
        when(productClassService.findClassByCode((long) 74000000)).thenReturn(productClass);
        assertEquals(productBricks, productClassController.getBricksByClassCode((long) 74000000));
        mockMvc.perform(get("/v1/productClass/74000000/brick").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].brickCode", is(94020000)))
                .andExpect(jsonPath("$[1].brickDescription", is("Crops for Food Production")));
    }

    @Test
    public void invalidClassCode() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a Valid Class Code! Enter valid Class Code");
        when(productClassController.getClassByClassCode((long) 2)).thenReturn(null);
    }

    @Test
    public void invalidClassCodeForFamily() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a Valid Class Code! Enter valid Class Code");
        when(productClassController.getBricksByClassCode((long) 1)).thenReturn(null);
    }

    @Test
    public void getAllClassesWhenNull() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No Product Classes available");
        when(productClassService.findClasses()).thenReturn(null);
        when(productClassController.getAllClasses()).thenReturn(null);
    }
}
