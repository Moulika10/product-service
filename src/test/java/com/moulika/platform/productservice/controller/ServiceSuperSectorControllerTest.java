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

import com.moulika.platform.productservice.bean.ServiceSubSector;
import com.moulika.platform.productservice.bean.ServiceSuperSector;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.ServiceSuperSectorService;

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
public class ServiceSuperSectorControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private ServiceSuperSectorService serviceSuperSectorService;

    @InjectMocks
    private ServiceSuperSectorController serviceSuperSectorController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(serviceSuperSectorController)
                .build();
    }

    @Test
    public void getAllSuperSectors() throws Exception {
        List<ServiceSuperSector> serviceSuperSectors = new ArrayList<>();
        serviceSuperSectors.add(new ServiceSuperSector("11", "Agriculture, Forestry, Fishing and Hunting", null));
        serviceSuperSectors.add(new ServiceSuperSector("21", "Mining, Quarrying, and Oil and Gas Extraction", null));
        serviceSuperSectors.add(new ServiceSuperSector("22", "Utilities", null));

        when(serviceSuperSectorService.findSuperSectors()).thenReturn(serviceSuperSectors);
        when(serviceSuperSectorController.getAllSuperSectors()).thenReturn(serviceSuperSectors);
        mockMvc.perform(get("/v1/serviceSuperSector").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].superSectorCode", is("11")))
                .andExpect(jsonPath("$[0].superSectorName", is("Agriculture, Forestry, Fishing and Hunting")));
    }

    @Test
    public void getSubSectorsBySuperSectorCode() throws Exception {
        Set<ServiceSubSector> serviceSubSectors = new HashSet<>();
        serviceSubSectors.add(new ServiceSubSector("50160000", "Confectionery/Sugar Sweetening Products", null));
        serviceSubSectors.add(new ServiceSubSector("94020000", "Crops for Food Production", null));
        serviceSubSectors.add(new ServiceSubSector("50340000", "Nuts/Seeds - Unprepared/Unprocessed (Shelf Stable)", null));
        ServiceSuperSector serviceSuperSector = new ServiceSuperSector("64000000", "Personal Accessories", serviceSubSectors);
        when(serviceSuperSectorService.findSuperSectorByCode("74000000")).thenReturn(serviceSuperSector);
        assertEquals(serviceSubSectors, serviceSuperSectorController.getSubSectorsBySuperSectorCode("74000000"));
        mockMvc.perform(get("/v1/serviceSuperSector/74000000/subSector").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].subSectorCode", is("50340000")))
                .andExpect(jsonPath("$[0].subSectorName", is("Nuts/Seeds - Unprepared/Unprocessed (Shelf Stable)")));
    }

    @Test
    public void getSuperSectorBySuperSectorCode() throws Exception {
        ServiceSuperSector serviceSuperSector = new ServiceSuperSector("145", "Personal Accessories", null);
        when(serviceSuperSectorService.findSuperSectorByCode("145")).thenReturn(serviceSuperSector);
        when(serviceSuperSectorController.getSuperSectorBySuperSectorCode("145")).thenReturn(serviceSuperSector);
        mockMvc.perform(get("/v1/serviceSuperSector/145").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.superSectorCode", is("145")))
                .andExpect(jsonPath("$.superSectorName", is("Personal Accessories")));
    }

    @Test
    public void invalidSuperSectorCode() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a valid SuperSectorCode! Enter a valid Super Sector Code");
        when(serviceSuperSectorController.getSuperSectorBySuperSectorCode("1456")).thenReturn(null);
    }

    @Test
    public void invalidSuperSectorCodeForSubSector() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a valid SuperSectorCode! Enter a valid Super Sector Code");
        when(serviceSuperSectorController.getSubSectorsBySuperSectorCode("1456")).thenReturn(null);
    }

    @Test
    public void getAllSuperSectorsWhenNull() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No SuperSectors available");
        when(serviceSuperSectorService.findSuperSectors()).thenReturn(null);
        when(serviceSuperSectorController.getAllSuperSectors()).thenReturn(null);
    }

}
