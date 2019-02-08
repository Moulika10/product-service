package com.moulika.platform.productservice.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
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
import com.moulika.platform.productservice.bean.OwnerService;
import com.moulika.platform.productservice.bean.dto.OwnerServiceDto;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.service.OwnerServiceService;

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
public class OwnerServiceControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private OwnerServiceService ownerServiceService;

    @InjectMocks
    private OwnerServiceController ownerServiceController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerServiceController)
                .build();
    }

    @Test
    public void getServicesByOwnerId() throws Exception {
        List<OwnerServiceDto> ownerServices = new ArrayList<>();
        ownerServices.add(new OwnerServiceDto((long) 1, "397d2-f8528e", "Autoles", "1111", "Water/Beverage Equipment Other","111","Water/Beverage Equipment"));
        ownerServices.add(new OwnerServiceDto((long) 2, "397d2-f8528e", "Laminate Trimmers", "1112", "Laminate Trimmers","112","Power Tools - Hand-held Portable"));

        when(ownerServiceService.findOwnerServicesByOwnerId("397d2-f8528e")).thenReturn(ownerServices);
        when(ownerServiceController.getServicesByOwnerId("397d2-f8528e")).thenReturn(ownerServices);
        mockMvc.perform(get("/v1/ownerService?ownerId=397d2-f8528e").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].ownerId", is("397d2-f8528e")))
                .andExpect(jsonPath("$[0].serviceName", is("Autoles")))
                .andExpect(jsonPath("$[0].industryGroupCode", is("1111")))
                .andExpect(jsonPath("$[0].industryGroupName", is("Water/Beverage Equipment Other")))
                .andExpect(jsonPath("$[0].subSectorCode", is("111")))
                .andExpect(jsonPath("$[0].subSectorName", is("Water/Beverage Equipment")));
    }

    @Test
    public void serviceById() throws Exception {
        OwnerServiceDto ownerServiceDto = new OwnerServiceDto((long) 1, "397d2-f8528e", "Cortaderia Selloana - Live Plants", "1111", "Cortaderia Selloana - Live Plants", "111","Cortaderia - Live Plants");
        when(ownerServiceService.findByID((long) 1)).thenReturn(ownerServiceDto);
        when(ownerServiceController.getServiceById((long) 1)).thenReturn(ownerServiceDto);
        mockMvc.perform(get("/v1/ownerService/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andExpect(jsonPath("$.ownerId", is("397d2-f8528e")))
                .andExpect(jsonPath("$.serviceName", is("Cortaderia Selloana - Live Plants")))
                .andExpect(jsonPath("$.industryGroupCode", is("1111")))
                .andExpect(jsonPath("$.industryGroupName", is("Cortaderia Selloana - Live Plants")))
                .andExpect(jsonPath("$.subSectorCode", is("111")))
                .andExpect(jsonPath("$.subSectorName", is("Cortaderia - Live Plants")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getInvalidServiceOwnerId() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("Enter valid Id!");
        when(ownerServiceController.getServiceById((long) 2)).thenReturn(null);
    }

    @Test
    public void getInvalidOwnerId() throws Exception {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("No Owner Services available");
        when(ownerServiceController.getServicesByOwnerId("123")).thenReturn(null);
    }

    @Test
    public void createOwnerService() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "0");
        OwnerService ownerService = new OwnerService( "397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals", "10000467", false);

        when(ownerServiceService.create(ownerService)).thenReturn(ownerService);
        Gson gson = new Gson();
        String json = gson.toJson(ownerService);
        assertEquals(map, ownerServiceController.createOwnerService(ownerService));
        mockMvc.perform(post("/v1/ownerService").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is("0")));
    }

    @Test
    public void deleteOwnerService() throws Exception {
        OwnerService ownerService = new OwnerService( "397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals", "10000467", false);
        when(ownerServiceService.findOwnerServiceById((long) 1)).thenReturn(ownerService);
        doNothing().when(ownerServiceService).delete(ownerService.getId());
        ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(r, ownerServiceController.deleteOwnerService((long) 1));
    }

    @Test
    public void noOwnerServiceToDelete() throws ProductServiceException {
        expectedEx.expect(ProductServiceException.class);
        expectedEx.expectMessage("The record you want to delete does not exists with id 2");
        when(ownerServiceController.deleteOwnerService((long) 2)).thenReturn(null);
    }
}
