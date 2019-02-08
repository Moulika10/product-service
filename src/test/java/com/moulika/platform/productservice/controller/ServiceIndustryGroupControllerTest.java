package com.mapp.platform.productservice.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapp.platform.productservice.bean.ServiceIndustryGroup;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.service.ServiceIndustryGroupService;

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
public class ServiceIndustryGroupControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private ServiceIndustryGroupService serviceIndustryGroupService;

    @InjectMocks
    private ServiceIndustryGroupController serviceIndustryGroupController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(serviceIndustryGroupController)
                .build();
    }

    @Test
    public void getAllIndustryGroups() throws Exception {
        List<ServiceIndustryGroup> serviceIndustryGroups = new ArrayList<>();
        serviceIndustryGroups.add(new ServiceIndustryGroup(null, "1111", "Oilseed and Grain Farming"));
        serviceIndustryGroups.add(new ServiceIndustryGroup(null, "1112", "Vegetable and Melon Farming"));
        serviceIndustryGroups.add(new ServiceIndustryGroup(null, "1113", "Fruit and Tree Nut Farming"));

        when(serviceIndustryGroupService.findIndustryGroups()).thenReturn(serviceIndustryGroups);
        when(serviceIndustryGroupController.getAllIndustryGroups()).thenReturn(serviceIndustryGroups);
        mockMvc.perform(get("/v1/serviceIndustryGroup").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].industryGroupCode", is("1111")))
                .andExpect(jsonPath("$[0].industryGroupName", is("Oilseed and Grain Farming")));
    }

    @Test
    public void getIndustryGroupByIndustryGroupCode() throws Exception {
        ServiceIndustryGroup serviceIndustryGroup = new ServiceIndustryGroup(null,"146", "Personal Accessories");
        when(serviceIndustryGroupService.findIndustryGroupByCode("146")).thenReturn(serviceIndustryGroup);
        when(serviceIndustryGroupController.getIndustryGroupByIndustryGroupCode("146")).thenReturn(serviceIndustryGroup);
        mockMvc.perform(get("/v1/serviceIndustryGroup/146").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.industryGroupCode", is("146")))
                .andExpect(jsonPath("$.industryGroupName", is("Personal Accessories")));
    }

    @Test
    public void invalidIndustryGroupCode() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Not a Valid IndustryGroupCode! Enter a Valid IndustryGroupCode");
        when(serviceIndustryGroupController.getIndustryGroupByIndustryGroupCode("1456")).thenReturn(null);
    }

    @Test
    public void getAllIndustryGroupsWhenNull() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No IndustryGroups available");
        when(serviceIndustryGroupService.findIndustryGroups()).thenReturn(null);
        when(serviceIndustryGroupController.getAllIndustryGroups()).thenReturn(null);
    }
}
