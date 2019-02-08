package com.moulika.platform.productservice.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.google.gson.Gson;
import com.moulika.platform.productservice.bean.CampaignOwnerService;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.CampaignOwnerServiceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class CampaignOwnerServiceControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private CampaignOwnerServiceService campaignOwnerServiceService;

    @InjectMocks
    private CampaignOwnerServiceController campaignOwnerServiceController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(campaignOwnerServiceController)
                .build();
    }

    @Test
    public void getServicesByCampaignId() throws Exception {
        List<CampaignOwnerService> campaignOwnerServices = new ArrayList<>();
        campaignOwnerServices.add(new CampaignOwnerService( 123, (long) 1, false));
        campaignOwnerServices.add(new CampaignOwnerService( 123, (long) 2, false));
        campaignOwnerServices.add(new CampaignOwnerService( 123, (long) 3, false));

        when(campaignOwnerServiceService.findCampaignOwnerServicesByCampaignId(123)).thenReturn(campaignOwnerServices);
        when(campaignOwnerServiceController.getServicesByCampaignId(123,1L)).thenReturn(campaignOwnerServices);
        mockMvc.perform(get("/v1/campaignOwnerService?campaignId=123").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].campaignId", is(123)))
                .andExpect(jsonPath("$[0].ownerServiceId", is(1)));
    }

    @Test
    public void getServicesByOwnerProductId() throws Exception {
        List<CampaignOwnerService> campaignOwnerServices = new ArrayList<>();
        campaignOwnerServices.add(new CampaignOwnerService( 123, (long) 1, false));
        when(campaignOwnerServiceService.findCampaignOwnerServicesByOwnerServiceId(1L)).thenReturn(campaignOwnerServices);
        when(campaignOwnerServiceController.getServicesByCampaignId(null, 1L)).thenReturn(campaignOwnerServices);
        mockMvc.perform(get("/v1/campaignOwnerService?ownerServiceId=1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].campaignId", is(123)))
                .andExpect(jsonPath("$[0].ownerServiceId", is(1)));
    }

    @Test
    public void getServicesByCampaignIdWhenNoCampaignId() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No Campaign Owner Services available");
        when(campaignOwnerServiceController.getServicesByCampaignId(123, null)).thenReturn(null);
    }

    @Test
    public void getServicesByCampaignIdWhenNoOwnerProductId() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No Campaign Owner Services available");
        when(campaignOwnerServiceController.getServicesByCampaignId(null,  1L)).thenReturn(null);
    }

    @Test
    public void serviceById() throws Exception {
        CampaignOwnerService campaignOwnerService = new CampaignOwnerService( 123, 1, false);
        when(campaignOwnerServiceService.findCampaignOwnerServiceById((long) 1)).thenReturn(campaignOwnerService);
        when(campaignOwnerServiceController.getServiceById((long) 1)).thenReturn(campaignOwnerService);
        mockMvc.perform(get("/v1/campaignOwnerService/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.campaignId", is(123)))
                .andExpect(jsonPath("$.ownerServiceId", is(1)));
    }

    @Test
    public void getInvalidServiceCampaignId() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Enter valid Id!");
        when(campaignOwnerServiceController.getServiceById((long) 2)).thenReturn(null);
    }

    @Test
    public void createCampaignOwnerService() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "0");
        CampaignOwnerService campaignOwnerService = new CampaignOwnerService( 123, 1, false);

        when(campaignOwnerServiceService.create(campaignOwnerService)).thenReturn(campaignOwnerService);
        Gson gson = new Gson();
        String json = gson.toJson(campaignOwnerService);
        assertEquals(map, campaignOwnerServiceController.createCampaignOwnerService(campaignOwnerService));
        mockMvc.perform(post("/v1/campaignOwnerService").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is("0")));
    }

    @Test
    public void deleteCampaignOwnerService() throws Exception {
        CampaignOwnerService campaignOwnerService = new CampaignOwnerService(123, 1, false);
        when(campaignOwnerServiceService.findCampaignOwnerServiceById((long) 1)).thenReturn(campaignOwnerService);
        doNothing().when(campaignOwnerServiceService).delete(campaignOwnerService.getId());
        ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(r, campaignOwnerServiceController.deleteCampaignOwnerService((long) 1));
    }

    @Test
    public void noCampaignOwnerServiceToDelete() throws ProductServiceException {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("The record you want to delete does not exists with id 2");
        when(campaignOwnerServiceController.deleteCampaignOwnerService((long) 2)).thenReturn(null);
    }

}
