package com.mapp.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.mapp.platform.productservice.bean.OwnerService;
import com.mapp.platform.productservice.bean.ServiceIndustryGroup;
import com.mapp.platform.productservice.bean.dto.OwnerServiceDto;
import com.mapp.platform.productservice.dao.OwnerServiceDaoImpl;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.repository.OwnerServiceRepository;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class OwnerServiceServiceTest {

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Mock
  private OwnerServiceRepository ownerServiceRepository;

  @Mock
  private OwnerServiceDaoImpl ownerServiceDao;

  @Mock
  private ServiceIndustryGroupService serviceIndustryGroupService;

  @InjectMocks
  private OwnerServiceService ownerServiceService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findAllServices() {
    List<OwnerService> ownerServices = new ArrayList<>();
    ownerServices
        .add(new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "Television", "4237", false));
    ownerServices
        .add(new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "Motors", "4238", false));
    when(ownerServiceRepository.findAll()).thenReturn(ownerServices);
    when(ownerServiceService.findAllServices()).thenReturn(ownerServices);
    assertEquals(2, ownerServices.size());
  }

  @Test
  public void findOwnerServiceById() {
    OwnerService ownerService = new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e",
        "Television", "4237", false);
    when(ownerServiceRepository.findOne(ownerService.getId())).thenReturn(ownerService);
    when(ownerServiceService.findOwnerServiceById(1)).thenReturn(ownerService);
    assertEquals("397d242a-4c59-478e-87c1-f81a50c8528e", ownerService.getOwnerId());
    assertEquals("Television", ownerService.getServiceName());
    assertEquals("4237", ownerService.getIndustryGroupCode());
    assertFalse(ownerService.getDeleted());
  }

  @Test
  public void findForDeletedServices() {
    List<OwnerService> ownerServices = new ArrayList<>();
    ownerServices
        .add(new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "Television", "4237", false));
    ownerServices
        .add(new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "Motors", "4238", false));
    when(ownerServiceRepository.findServices()).thenReturn(ownerServices);
    when(ownerServiceService.findForDeletedServices()).thenReturn(ownerServices);
    assertEquals(2, ownerServices.size());
  }

  @Test
  public void insert() {
    OwnerService ownerService = new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e",
        "Television", "4237", false);
    when(ownerServiceRepository.save(ownerService)).thenReturn(ownerService);
    when(ownerServiceService.insert(ownerService)).thenReturn(ownerService);
    assertEquals("397d242a-4c59-478e-87c1-f81a50c8528e", ownerService.getOwnerId());
    assertEquals("Television", ownerService.getServiceName());
    assertEquals("4237", ownerService.getIndustryGroupCode());
    assertFalse(ownerService.getDeleted());
  }

  @Test
  public void delete() {
    doNothing().when(ownerServiceRepository).delete((long) 1);
    ownerServiceService.delete((long) 1);
  }

  @Test
  public void findAndUpdateServiceDeleted() {
    List<OwnerService> ownerServices = new ArrayList<>();
    ownerServices.add(
        new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants", "10006193",
            false));
    ownerServices.add(
        new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "First Aid - Accessories",
            "10000449", false));
    ownerServices.add(
        new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals", "10000467",
            false));
    OwnerService ownerService = new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e",
        "Red Currants", "10006193", false);
    when(ownerServiceRepository
        .undoDelete("10006193", "397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants"))
        .thenReturn(1);
    when(ownerServiceRepository
        .findByOwnerIdAndIndustryGroupCode("10006193", "397d242a-4c59-478e-87c1-f81a50c8528e",
            "Red Currants")).thenReturn(ownerService);
    when(ownerServiceService.findAndUpdateServiceDeleted(ownerServices, "10006193",
        "397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants")).thenReturn(ownerService);
    assertEquals("397d242a-4c59-478e-87c1-f81a50c8528e", ownerService.getOwnerId());
    assertEquals("Red Currants", ownerService.getServiceName());
    assertEquals("10006193", ownerService.getIndustryGroupCode());
    assertFalse(ownerService.getDeleted());
  }

  @Test
  public void containsIndustryGroupCode() {
    List<OwnerService> ownerServices = new ArrayList<>();
    ownerServices.add(
        new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants", "10006193",
            false));
    ownerServices.add(
        new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "First Aid - Accessories",
            "10000449", false));
    ownerServices.add(
        new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals", "10000467",
            false));
    assertTrue(ownerServiceService.containsIndustryGroupCode(ownerServices, "10006193"));
  }

  @Test
  public void containsServiceOwnerId() {
    List<OwnerService> ownerServices = new ArrayList<>();
    ownerServices.add(
        new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants", "10006193",
            false));
    ownerServices.add(
        new OwnerService("397d242a-4c59-478e-87c1-f81a50a3528e", "First Aid - Accessories",
            "10000449", false));
    ownerServices.add(
        new OwnerService("397d242a-4c59-478e-87c1-f81a50bf528e", "Vitamins/Minerals", "10000467",
            false));
    assertTrue(ownerServiceService
        .containsServiceOwnerId(ownerServices, "397d242a-4c59-478e-87c1-f81a50c8528e"));
  }

  @Test
  public void create() throws ProductServiceException {
    List<OwnerService> ownerServices = new ArrayList<>();
    ownerServices.add(new OwnerService("123", "Red Currants", "1111", false));
    ownerServices.add(new OwnerService("125", "First Aid - Accessories", "1112", false));
    ownerServices.add(new OwnerService("1456", "Vitamins/Minerals", "1113", false));
    ServiceIndustryGroup serviceIndustryGroup = new ServiceIndustryGroup(null, "1113",
        "Vitamins/Minerals");
    OwnerService ownerService = new OwnerService("1456", "Vitamins/Minerals", "1113", false);
    when(ownerServiceService.findForDeletedServices()).thenReturn(ownerServices);
    when(serviceIndustryGroupService.findIndustryGroupByCode("1113"))
        .thenReturn(serviceIndustryGroup);
    when(ownerServiceService.insert(ownerService)).thenReturn(ownerService);
    when(ownerServiceService.create(ownerService)).thenReturn(ownerService);
    assertEquals("1456", ownerService.getOwnerId());
    assertEquals("Vitamins/Minerals", ownerService.getServiceName());
    assertEquals("1113", ownerService.getIndustryGroupCode());
  }

  @Test
  public void createWhenNoIndustryGroup() throws ProductServiceException {
    expectedEx.expectMessage("Industry Group Code does not exists");
    OwnerService ownerService = new OwnerService("1456", "Vitamins/Minerals", "1113", false);
    when(serviceIndustryGroupService.findIndustryGroupByCode("1113")).thenReturn(null);
    when(ownerServiceService.create(ownerService)).thenReturn(null);
  }

  @Test
  public void createWhenSoftDelete() throws Exception {
    List<OwnerService> campaignServices = new ArrayList<>();
    campaignServices.add(new OwnerService("123", "Red Currants", "1111", false));
    campaignServices.add(new OwnerService("123", "First Aid - Accessories", "1112", false));
    campaignServices.add(new OwnerService("123", "Vitamins/Minerals", "1113", false));
    OwnerService ownerService = new OwnerService("123", "Red Currants", "1111", false);
    ServiceIndustryGroup serviceIndustryGroup = new ServiceIndustryGroup(null, "1111",
        "Red Currants");
    when(ownerServiceRepository.undoDelete("1111", "123", "Red Currants")).thenReturn(1);
    when(ownerServiceRepository.findByOwnerIdAndIndustryGroupCode("1111", "123", "Red Currants"))
        .thenReturn(ownerService);
    when(ownerServiceService.findForDeletedServices()).thenReturn(campaignServices);
    when(serviceIndustryGroupService.findIndustryGroupByCode("1111"))
        .thenReturn(serviceIndustryGroup);
    when(ownerServiceService
        .findAndUpdateServiceDeleted(campaignServices, "1111", "123", "Red Currants"))
        .thenReturn(ownerService);
    when(ownerServiceService.create(ownerService)).thenReturn(ownerService);
    assertEquals("123", ownerService.getOwnerId());
    assertEquals("Red Currants", ownerService.getServiceName());
    assertEquals("1111", ownerService.getIndustryGroupCode());
  }

  @Test
  public void findByID() {
    OwnerServiceDto ownerServiceDto = new OwnerServiceDto(1, "397d2-f8528e",
        "Cortaderia Selloana - Live Plants", "1111", "Cortaderia Selloana - Live Plants", "111",
        "Cortaderia - Live Plants");
    when(ownerServiceDao.findOne(1L)).thenReturn(ownerServiceDto);
    when(ownerServiceService.findByID(1L)).thenReturn(ownerServiceDto);
    assertEquals(1, ownerServiceDto.getId());
    assertEquals("397d2-f8528e", ownerServiceDto.getOwnerId());
    assertEquals("Cortaderia Selloana - Live Plants", ownerServiceDto.getServiceName());
    assertEquals("1111", ownerServiceDto.getIndustryGroupCode());
    assertEquals("Cortaderia Selloana - Live Plants", ownerServiceDto.getIndustryGroupName());
    assertEquals("111", ownerServiceDto.getSubSectorCode());
    assertEquals("Cortaderia - Live Plants", ownerServiceDto.getSubSectorName());
  }

  @Test
  public void findOwnerServicesByOwnerId() {
    List<OwnerServiceDto> ownerServices = new ArrayList<>();
    ownerServices.add(
        new OwnerServiceDto(1, "397d2-f8528e", "Cortaderia Selloana - Live Plants", "1111",
            "Selloana - Live Plants", "111", "Cortaderia - Live Plants"));
    ownerServices.add(
        new OwnerServiceDto(2, "397d2-f8528e", "Cortaderia Selloana - Live Plants", "1112",
            "Algae - Live Plants", "111", "Cortaderia - Live Plants"));
    ownerServices.add(
        new OwnerServiceDto(3, "397d2-f8528e", "Cortaderia Selloana - Live Plants", "1113",
            "Cars - Live Plants", "111", "Cortaderia - Live Plants"));
    when(ownerServiceService.findOwnerServicesByOwnerId("397d2-f8528e")).thenReturn(ownerServices);
  }
}
