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

import com.moulika.platform.productservice.bean.ServiceIndustryGroup;
import com.moulika.platform.productservice.bean.ServiceSubSector;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.ServiceSubSectorService;

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
public class ServiceSubSectorControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private ServiceSubSectorService serviceSubSectorService;

    @InjectMocks
    private ServiceSubSectorController serviceSubSectorController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(serviceSubSectorController)
                .build();
    }

    @Test
    public void getAllSubSectors() throws Exception {
        List<ServiceSubSector> serviceSubSectors = new ArrayList<>();
        serviceSubSectors.add(new ServiceSubSector("111", "Crop Production", null));
        serviceSubSectors.add(new ServiceSubSector("112", "Animal Production and Aquaculture", null));
        serviceSubSectors.add(new ServiceSubSector("113", "Forestry and Logging", null));

        when(serviceSubSectorService.findSubSectors()).thenReturn(serviceSubSectors);
        when(serviceSubSectorController.getAllSubSectors()).thenReturn(serviceSubSectors);
        mockMvc.perform(get("/v1/serviceSubSector").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].subSectorCode", is("111")))
                .andExpect(jsonPath("$[0].subSectorName", is("Crop Production")));
    }

    @Test
    public void getIndustryGroupsBySubSectorCode() throws Exception {
        Set<ServiceIndustryGroup> serviceIndustryGroups = new HashSet<>();
        serviceIndustryGroups.add(new ServiceIndustryGroup(null, "94020000", "Crops for Food Production"));
        serviceIndustryGroups.add(new ServiceIndustryGroup(null, "50340000", "Nuts/Seeds - Unprepared/Unprocessed (Shelf Stable)"));
        ServiceSubSector serviceSubSector = new ServiceSubSector("64000000", "Personal Accessories", serviceIndustryGroups);
        when(serviceSubSectorService.findSubSectorByCode("74000000")).thenReturn(serviceSubSector);
        assertEquals(serviceIndustryGroups, serviceSubSectorController.getIndustryGroupsBySubSectorCode("74000000"));
        mockMvc.perform(get("/v1/serviceSubSector/74000000/industryGroup").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].industryGroupCode", is("94020000")))
                .andExpect(jsonPath("$[1].industryGroupName", is("Crops for Food Production")));
    }

    @Test
    public void getSubSectorBySubSectorCode() throws Exception {
        ServiceSubSector serviceSubSector = new ServiceSubSector("146", "Personal Accessories", null);
        when(serviceSubSectorService.findSubSectorByCode("146")).thenReturn(serviceSubSector);
        when(serviceSubSectorController.getSubSectorBySubSectorCode("146")).thenReturn(serviceSubSector);
        mockMvc.perform(get("/v1/serviceSubSector/146").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.subSectorCode", is("146")))
                .andExpect(jsonPath("$.subSectorName", is("Personal Accessories")));
    }

    @Test
    public void invalidSubSectorCode() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a Valid SubSectorCode! Enter a Valid SubSectorCode");
        when(serviceSubSectorController.getSubSectorBySubSectorCode("1456")).thenReturn(null);
    }

    @Test
    public void invalidSubSectorCodeForIndustryGroup() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a Valid SubSectorCode! Enter a Valid SubSectorCode");
        when(serviceSubSectorController.getIndustryGroupsBySubSectorCode("1456")).thenReturn(null);
    }

    @Test
    public void getAllSubSectorsWhenNull() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No SubSectors available");
        when(serviceSubSectorService.findSubSectors()).thenReturn(null);
        when(serviceSubSectorController.getAllSubSectors()).thenReturn(null);
    }
}
