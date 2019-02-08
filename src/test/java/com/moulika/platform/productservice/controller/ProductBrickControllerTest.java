package com.moulika.platform.productservice.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.moulika.platform.productservice.bean.ProductBrick;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.ProductBrickService;

import java.util.ArrayList;
import java.util.List;
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
public class ProductBrickControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private ProductBrickService productBrickService;

    @InjectMocks
    private ProductBrickController productBrickController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(productBrickController)
                .build();
    }

    @Test
    public void getAllBricks() throws Exception {
        List<ProductBrick> productBricks = new ArrayList<>();
        productBricks.add(new ProductBrick(null,10000449, "First Aid - Accessories"));
        productBricks.add(new ProductBrick(null,10000467, "Vitamins/Minerals"));
        productBricks.add(new ProductBrick(null,10005756, "Key Rings"));

        when(productBrickService.findBricks()).thenReturn(productBricks);
        when(productBrickController.getAllBricks()).thenReturn(productBricks);
        mockMvc.perform(get("/v1/productBrick").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].brickCode", is(10000449)))
                .andExpect(jsonPath("$[0].brickDescription", is("First Aid - Accessories")));
    }

    @Test
    public void getBrickByBrickCode() throws Exception {
        ProductBrick productBrick = new ProductBrick(null,1L, "Personal Accessories");
        when(productBrickService.findBrickByCode((long) 1)).thenReturn(productBrick);
        when(productBrickController.getBrickByBrickCode((long) 1)).thenReturn(productBrick);
        mockMvc.perform(get("/v1/productBrick/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.brickCode", is(1)))
                .andExpect(jsonPath("$.brickDescription", is("Personal Accessories")));
    }

    @Test
    public void invalidBrickCode() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a Valid Brick Code! Enter valid Brick Code");
        when(productBrickController.getBrickByBrickCode((long) 2)).thenReturn(null);
    }

    @Test
    public void getAllBricksWhenNull() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No Product Bricks available");
        when(productBrickService.findBricks()).thenReturn(null);
        when(productBrickController.getAllBricks()).thenReturn(null);
    }
}
