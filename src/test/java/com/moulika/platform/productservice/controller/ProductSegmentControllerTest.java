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

import com.mapp.platform.productservice.bean.ProductFamily;
import com.mapp.platform.productservice.bean.ProductSegment;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.service.ProductSegmentService;

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
public class ProductSegmentControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private ProductSegmentService productSegmentService;

    @InjectMocks
    private ProductSegmentController productSegmentController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(productSegmentController)
                .build();
    }

    @Test
    public void getAllSegments() throws Exception {
        List<ProductSegment> productSegments = new ArrayList<>();
        productSegments.add(new ProductSegment(74000000, "Camping", null));
        productSegments.add(new ProductSegment(93000000, "Horticulture Plants", null));
        productSegments.add(new ProductSegment(87000000, "Fuels/Gases", null));
        when(productSegmentService.findSegments()).thenReturn(productSegments);
        when(productSegmentController.getAllSegments()).thenReturn(productSegments);
        mockMvc.perform(get("/v1/productSegment").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].segmentCode", is(74000000)))
                .andExpect(jsonPath("$[0].segmentDescription", is("Camping")));
    }

    @Test
    public void getFamiliesBySegmentCode() throws Exception {
        Set<ProductFamily> productFamilies = new HashSet<>();
        productFamilies.add(new ProductFamily(94020000, "Crops for Food Production", null));
        productFamilies.add(new ProductFamily(50340000, "Nuts/Seeds - Unprepared/Unprocessed (Shelf Stable)", null));
        ProductSegment productSegment = new ProductSegment(64000000, "Personal Accessories", productFamilies);
        when(productSegmentService.findSegmentByCode((long) 64000000)).thenReturn(productSegment);
        assertEquals(productFamilies, productSegmentController.getFamiliesBySegmentCode((long) 64000000));
        mockMvc.perform(get("/v1/productSegment/64000000/family").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].familyCode", is(94020000)))
                .andExpect(jsonPath("$[1].familyDescription", is("Crops for Food Production")));
    }

    @Test
    public void getSegmentBySegmentCode() throws Exception {
        ProductSegment productSegment = new ProductSegment(1L, "Personal Accessories", null);
        when(productSegmentService.findSegmentByCode((long) 1)).thenReturn(productSegment);
        when(productSegmentController.getSegmentBySegmentCode((long) 1)).thenReturn(productSegment);
        mockMvc.perform(get("/v1/productSegment/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.segmentCode", is(1)))
                .andExpect(jsonPath("$.segmentDescription", is("Personal Accessories")));
    }

    @Test
    public void invalidSegmentCode() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a Valid Segment Code! Enter valid Segment Code");
        when(productSegmentController.getSegmentBySegmentCode((long) 2)).thenReturn(null);
    }

    @Test
    public void invalidSegmentCodeForFamily() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a Valid Segment Code! Enter valid Segment Code");
        when(productSegmentController.getFamiliesBySegmentCode((long) 1)).thenReturn(null);
    }

    @Test
    public void getAllSegmentsWhenNull() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No Product Segments available");
        when(productSegmentService.findSegments()).thenReturn(null);
        when(productSegmentController.getAllSegments()).thenReturn(null);
    }
}
