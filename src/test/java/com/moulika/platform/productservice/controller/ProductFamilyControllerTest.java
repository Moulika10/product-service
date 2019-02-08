package com.moulika.platform.productservice.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.moulika.platform.productservice.bean.ProductClass;
import com.moulika.platform.productservice.bean.ProductFamily;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.ProductFamilyService;

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
public class ProductFamilyControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private ProductFamilyService productFamilyService;

    @InjectMocks
    private ProductFamilyController productFamilyController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(productFamilyController)
                .build();
    }

    @Test
    public void getAllFamilies() throws Exception {
        List<ProductFamily> productFamilies = new ArrayList<>();
        productFamilies.add(new ProductFamily(50160000, "Confectionery/Sugar Sweetening Products", null));
        productFamilies.add(new ProductFamily(94020000, "Crops for Food Production", null));
        productFamilies.add(new ProductFamily(50340000, "Nuts/Seeds - Unprepared/Unprocessed (Shelf Stable)", null));
        when(productFamilyService.findFamilies()).thenReturn(productFamilies);
        when(productFamilyController.getAllFamilies()).thenReturn(productFamilies);
        mockMvc.perform(get("/v1/productFamily").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].familyCode", is(50160000)))
                .andExpect(jsonPath("$[0].familyDescription", is("Confectionery/Sugar Sweetening Products")));
    }

    @Test
    public void getFamilyByFamilyCode() throws Exception {
        ProductFamily productFamily = new ProductFamily(1L, "Personal Accessories", null);
        when(productFamilyService.findFamilyByCode((long) 1)).thenReturn(productFamily);
        when(productFamilyController.getFamilyByFamilyCode((long) 1)).thenReturn(productFamily);
        mockMvc.perform(get("/v1/productFamily/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.familyCode", is(1)))
                .andExpect(jsonPath("$.familyDescription", is("Personal Accessories")));
    }

    @Test
    public void getClassesByFamilyCode() throws Exception {
        Set<ProductClass> productClasses = new HashSet<>();
        productClasses.add(new ProductClass(null, 94020000, "Crops for Food Production", null));
        productClasses.add(new ProductClass(null, 50340000, "Nuts/Seeds - Unprepared/Unprocessed (Shelf Stable)", null));
        ProductFamily productFamily = new ProductFamily(74000000, "Personal Accessories", productClasses);
        when(productFamilyService.findFamilyByCode((long) 74000000)).thenReturn(productFamily);
        assertEquals(productClasses, productFamilyController.getClassesByFamilyCode((long) 74000000));
        mockMvc.perform(get("/v1/productFamily/74000000/class").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].classCode", is(94020000)))
                .andExpect(jsonPath("$[1].classDescription", is("Crops for Food Production")));
    }

    @Test
    public void invalidFamilyCode() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a Valid Family Code! Enter valid Family Code");
        when(productFamilyController.getFamilyByFamilyCode((long) 2)).thenReturn(null);
    }

    @Test
    public void invalidFamilyCodeForClass() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a Valid Family Code! Enter valid Family Code");
        when(productFamilyController.getClassesByFamilyCode((long) 1)).thenReturn(null);
    }

    @Test
    public void getAllFamiliesWhenNull() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No Product Families available");
        when(productFamilyService.findFamilies()).thenReturn(null);
        when(productFamilyController.getAllFamilies()).thenReturn(null);
    }

}
