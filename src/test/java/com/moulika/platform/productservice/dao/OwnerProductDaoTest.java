package com.mapp.platform.productservice.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.mapp.platform.productservice.bean.dto.OwnerProductDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class OwnerProductDaoTest {

    @Mock
    private OwnerProductDaoImpl ownerProductDaoImpl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findOne(){
        OwnerProductDto ownerProductDto = new OwnerProductDto(1, "397d242a-4c59-478e-87c1-f81a50c8528e", "Cortaderia Selloana - Live Plants", 10006600, "Cortaderia Selloana - Live Plants", 93033900,"Cortaderia - Live Plants");
        when(ownerProductDaoImpl.findOne((long) 1)).thenReturn(ownerProductDto);
        assertNotNull(ownerProductDto);
    }

    @Test
    public void findByOwnerId() {
        List<OwnerProductDto> ownerProducts = new ArrayList<>();
        ownerProducts.add(new OwnerProductDto((long) 1, "397d242a-4c59-478e-87c1-f81a50c8528e", "Autoles", 10002135, "Water/Beverage Equipment Other",73040200,"Water/Beverage Equipment"));
        ownerProducts.add(new OwnerProductDto((long) 2, "397d242a-4c59-478e-87c1-f81a50c8528e", "Laminate Trimmers", (long) 10003626, "Laminate Trimmers",82010400,"Power Tools - Hand-held Portable"));
        when(ownerProductDaoImpl.findByOwnerId("397d242a-4c59-478e-87c1-f81a50c8528e")).thenReturn(ownerProducts);
        assertTrue(ownerProducts.size() > 0);
    }
}
