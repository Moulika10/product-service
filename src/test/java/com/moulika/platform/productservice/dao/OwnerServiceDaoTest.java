package com.moulika.platform.productservice.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.moulika.platform.productservice.bean.dto.OwnerServiceDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class OwnerServiceDaoTest {

    @Mock
    private OwnerServiceDaoImpl ownerServiceDaoImpl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findOne(){
        OwnerServiceDto ownerServiceDto = new OwnerServiceDto(1, "397d242a-4c59-478e-87c1-f81a50c8528e", "Cortaderia Selloana - Live Plants", "1111", "Cortaderia Selloana - Live Plants", "111","Cortaderia - Live Plants");
        when(ownerServiceDaoImpl.findOne((long) 1)).thenReturn(ownerServiceDto);
        assertNotNull(ownerServiceDto);
    }

    @Test
    public void findByOwnerId() {
        List<OwnerServiceDto> ownerServices = new ArrayList<>();
        ownerServices.add(new OwnerServiceDto((long) 1, "397d242a-4c59-478e-87c1-f81a50c8528e", "Autoles", "1111", "Water/Beverage Equipment Other","111","Water/Beverage Equipment"));
        ownerServices.add(new OwnerServiceDto((long) 2, "397d242a-4c59-478e-87c1-f81a50c8528e", "Laminate Trimmers",  "1112", "Laminate Trimmers","112","Power Tools - Hand-held Portable"));
        when(ownerServiceDaoImpl.findByOwnerId("397d2-f8528e")).thenReturn(ownerServices);
        assertTrue(ownerServices.size() > 0);
    }
}
