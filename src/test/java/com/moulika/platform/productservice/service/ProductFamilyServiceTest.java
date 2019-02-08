package com.moulika.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.moulika.platform.productservice.bean.ProductFamily;
import com.moulika.platform.productservice.repository.ProductFamilyRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductFamilyServiceTest {

    @Mock
    private ProductFamilyRepository productFamilyRepository;

    @InjectMocks
    private ProductFamilyService productFamilyService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findFamilies() {
        List<ProductFamily> productFamilies = new ArrayList<>();
        productFamilies.add(new ProductFamily(50160000, "Confectionery/Sugar Sweetening Products", null));
        productFamilies.add(new ProductFamily(94020000, "Crops for Food Production", null));
        productFamilies.add(new ProductFamily(50340000, "Nuts/Seeds - Unprepared/Unprocessed (Shelf Stable)", null));
        when(productFamilyRepository.findAll()).thenReturn(productFamilies);
        when(productFamilyService.findFamilies()).thenReturn(productFamilies);
        assertEquals(3, productFamilies.size());
    }

    @Test
    public void findFamilyByCode() {
        ProductFamily productFamily = new ProductFamily(50160000, "Confectionery/Sugar Sweetening Products", null);
        when(productFamilyRepository.findOne((long) 50160000)).thenReturn(productFamily);
        when(productFamilyService.findFamilyByCode((long) 50160000)).thenReturn(productFamily);
        assertEquals(50160000, productFamily.getFamilyCode());
        assertEquals("Confectionery/Sugar Sweetening Products", productFamily.getFamilyDescription());
    }

}
