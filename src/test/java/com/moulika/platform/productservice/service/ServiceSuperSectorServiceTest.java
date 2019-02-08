package com.mapp.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.mapp.platform.productservice.bean.ServiceSuperSector;
import com.mapp.platform.productservice.repository.ServiceSuperSectorRepository;
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
public class ServiceSuperSectorServiceTest {

    @Mock
    private ServiceSuperSectorRepository serviceSuperSectorRepository;

    @InjectMocks
    private ServiceSuperSectorService serviceSuperSectorService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findSuperSectors() {
        List<ServiceSuperSector> serviceSuperSectors = new ArrayList<>();
        serviceSuperSectors.add(new ServiceSuperSector("11", "Agriculture, Forestry, Fishing and Hunting", null));
        serviceSuperSectors.add(new ServiceSuperSector("21", "Mining, Quarrying, and Oil and Gas Extraction", null));
        serviceSuperSectors.add(new ServiceSuperSector("22", "Utilities", null));
        when(serviceSuperSectorRepository.findAll()).thenReturn(serviceSuperSectors);
        when(serviceSuperSectorService.findSuperSectors()).thenReturn(serviceSuperSectors);
        assertEquals(3, serviceSuperSectors.size());

    }

    @Test
    public void findSuperSectorByCode() {
        ServiceSuperSector serviceSuperSector = new ServiceSuperSector("11", "Agriculture, Forestry, Fishing and Hunting", null);
        when(serviceSuperSectorRepository.findBySuperSectorCode("11")).thenReturn(serviceSuperSector);
        when(serviceSuperSectorService.findSuperSectorByCode("11")).thenReturn(serviceSuperSector);
        assertEquals("11", serviceSuperSector.getSuperSectorCode());
        assertEquals("Agriculture, Forestry, Fishing and Hunting", serviceSuperSector.getSuperSectorName());
    }

}
