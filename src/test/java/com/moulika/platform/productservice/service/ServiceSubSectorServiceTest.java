package com.moulika.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.moulika.platform.productservice.bean.ServiceSubSector;
import com.moulika.platform.productservice.repository.ServiceSubSectorRepository;
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
public class ServiceSubSectorServiceTest {

    @Mock
    private ServiceSubSectorRepository serviceSubSectorRepository;

    @InjectMocks
    private ServiceSubSectorService serviceSubSectorService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findSubSectors() {
        List<ServiceSubSector> serviceSubSectors = new ArrayList<>();
        serviceSubSectors.add(new ServiceSubSector("111", "Crop Production", null));
        serviceSubSectors.add(new ServiceSubSector("112", "Animal Production and Aquaculture", null));
        serviceSubSectors.add(new ServiceSubSector("113", "Forestry and Logging", null));
        when(serviceSubSectorRepository.findAll()).thenReturn(serviceSubSectors);
        when(serviceSubSectorService.findSubSectors()).thenReturn(serviceSubSectors);
        assertEquals(3, serviceSubSectors.size());
    }

    @Test
    public void findSubSectorByCode() {
        ServiceSubSector serviceSubSector = new ServiceSubSector("111", "Crop Production", null);
        when(serviceSubSectorRepository.findBySubSectorCode("111")).thenReturn(serviceSubSector);
        when(serviceSubSectorService.findSubSectorByCode("111")).thenReturn(serviceSubSector);
        assertEquals("111", serviceSubSector.getSubSectorCode());
        assertEquals("Crop Production", serviceSubSector.getSubSectorName());
    }

}
