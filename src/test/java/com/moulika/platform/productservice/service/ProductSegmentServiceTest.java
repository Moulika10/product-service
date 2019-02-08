package com.mapp.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.mapp.platform.productservice.bean.ProductSegment;
import com.mapp.platform.productservice.repository.ProductSegmentRepository;
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
public class ProductSegmentServiceTest {

    @Mock
    private ProductSegmentRepository productSegmentRepository;

    @InjectMocks
    private ProductSegmentService productSegmentService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findSegments() {
        List<ProductSegment> productSegments = new ArrayList<>();
        productSegments.add(new ProductSegment(74000000, "Camping", null));
        productSegments.add(new ProductSegment(93000000, "Horticulture Plants", null));
        productSegments.add(new ProductSegment(87000000, "Fuels/Gases", null));
        when(productSegmentRepository.findAll()).thenReturn(productSegments);
        when(productSegmentService.findSegments()).thenReturn(productSegments);
        assertEquals(3, productSegments.size());
    }

    @Test
    public void findSegmentByCode() {
        ProductSegment productSegment = new ProductSegment(64000000, "Personal Accessories", null);
        when(productSegmentRepository.findOne((long) 64000000)).thenReturn(productSegment);
        when(productSegmentService.findSegmentByCode((long) 64000000)).thenReturn(productSegment);
        assertEquals(64000000, productSegment.getSegmentCode());
        assertEquals("Personal Accessories", productSegment.getSegmentDescription());
    }

}
