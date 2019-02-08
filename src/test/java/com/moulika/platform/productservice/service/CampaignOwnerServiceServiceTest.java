package com.moulika.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.moulika.platform.productservice.bean.CampaignOwnerService;
import com.moulika.platform.productservice.bean.OwnerService;
import com.moulika.platform.productservice.exception.ProductServiceException;
import com.moulika.platform.productservice.repository.CampaignOwnerServiceRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

public class CampaignOwnerServiceServiceTest {

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Mock
  private CampaignOwnerServiceRepository campaignOwnerServiceRepository;

  @Mock
  private OwnerServiceService ownerServiceService;

  @InjectMocks
  private CampaignOwnerServiceService campaignOwnerServiceService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findCampaignOwnerServiceById() {
    CampaignOwnerService campaignOwnerService = new CampaignOwnerService(123, 1L, false);
    when(campaignOwnerServiceRepository.findOne((long) 1)).thenReturn(campaignOwnerService);
    when(campaignOwnerServiceService.findCampaignOwnerServiceById(1))
        .thenReturn(campaignOwnerService);
    assertEquals(123, campaignOwnerService.getCampaignId());
    assertEquals((long) 1, campaignOwnerService.getOwnerServiceId());
    assertFalse(campaignOwnerService.getDeleted());
  }

  @Test
  public void findForDeletedServices() {
    List<CampaignOwnerService> campaignOwnerServices = new ArrayList<>();
    campaignOwnerServices.add(new CampaignOwnerService(123, 1L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 2L, false));
    when(campaignOwnerServiceRepository.findServices()).thenReturn(campaignOwnerServices);
    when(campaignOwnerServiceService.findForDeletedServices()).thenReturn(campaignOwnerServices);
    assertEquals(2, campaignOwnerServices.size());
  }

  @Test
  public void insert() {
    CampaignOwnerService campaignOwnerService = new CampaignOwnerService(123, 1L, false);
    when(campaignOwnerServiceRepository.save(campaignOwnerService))
        .thenReturn(campaignOwnerService);
    when(campaignOwnerServiceService.insert(campaignOwnerService)).thenReturn(campaignOwnerService);
    assertEquals(123, campaignOwnerService.getCampaignId());
    assertEquals(1L, campaignOwnerService.getOwnerServiceId());
    assertFalse(campaignOwnerService.getDeleted());
  }

  @Test
  public void delete() {
    doNothing().when(campaignOwnerServiceRepository).delete((long) 1);
    campaignOwnerServiceService.delete((long) 1);
  }

  @Test
  public void findAndUpdateServiceDeleted() {
    List<CampaignOwnerService> campaignOwnerServices = new ArrayList<>();
    campaignOwnerServices.add(new CampaignOwnerService(123, 1L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 2L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 3L, false));
    CampaignOwnerService campaignOwnerService = new CampaignOwnerService(123, 1L, false);
    when(campaignOwnerServiceRepository.undoDelete(1L, 123)).thenReturn(1);
    when(campaignOwnerServiceRepository.findByCampaignIdAndOwnerServiceId(1L, 123))
        .thenReturn(campaignOwnerService);
    when(campaignOwnerServiceService.findAndUpdateServiceDeleted(campaignOwnerServices, 1L, 123))
        .thenReturn(campaignOwnerService);
    assertEquals(123, campaignOwnerService.getCampaignId());
    assertEquals(1L, campaignOwnerService.getOwnerServiceId());
    assertFalse(campaignOwnerService.getDeleted());
  }

  @Test
  public void containsOwnerServiceId() {
    List<CampaignOwnerService> campaignOwnerServices = new ArrayList<>();
    campaignOwnerServices.add(new CampaignOwnerService(123, 1L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 2L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 3L, false));
    assertTrue(campaignOwnerServiceService.containsOwnerServiceId(campaignOwnerServices, 1L));
  }

  @Test
  public void containsServiceCampaignId() {
    List<CampaignOwnerService> campaignOwnerServices = new ArrayList<>();
    campaignOwnerServices.add(new CampaignOwnerService(123, 1L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 2L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 3L, false));
    assertTrue(campaignOwnerServiceService.containsServiceCampaignId(campaignOwnerServices, 123));
  }

  @Test
  public void create() throws ProductServiceException {
    List<CampaignOwnerService> campaignOwnerServices = new ArrayList<>();
    campaignOwnerServices.add(new CampaignOwnerService(123, 1L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 2L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 3L, false));
    OwnerService ownerService = new OwnerService("abc", "Vitamins", "1111", false);
    CampaignOwnerService campaignOwnerService = new CampaignOwnerService(123, 1L, false);
    when(campaignOwnerServiceRepository.findServices()).thenReturn(campaignOwnerServices);
    when(campaignOwnerServiceService.findForDeletedServices()).thenReturn(campaignOwnerServices);
    when(ownerServiceService.findOwnerServiceById(1L)).thenReturn(ownerService);
    when(campaignOwnerServiceRepository.save(campaignOwnerService))
        .thenReturn(campaignOwnerService);
    when(campaignOwnerServiceService.insert(campaignOwnerService)).thenReturn(campaignOwnerService);
    when(campaignOwnerServiceService.create(campaignOwnerService)).thenReturn(campaignOwnerService);
    assertEquals(123, campaignOwnerService.getCampaignId());
    assertEquals(1L, campaignOwnerService.getOwnerServiceId());
    assertFalse(campaignOwnerService.getDeleted());
  }

  @Test
  public void createWhenOwnerServiceIsNull() throws ProductServiceException {
    expectedEx.expectMessage("Owner Service does not exists");
    CampaignOwnerService campaignOwnerService = new CampaignOwnerService(123, 1L, false);
    when(campaignOwnerServiceService.create(campaignOwnerService)).thenReturn(null);
  }

  @Test
  public void createWhenSoftDelete() throws Exception {
    List<CampaignOwnerService> campaignOwnerServices = new ArrayList<>();
    campaignOwnerServices.add(new CampaignOwnerService(123, 1L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 2L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 3L, false));
    OwnerService ownerService = new OwnerService("abc", "Vitamins", "1111", false);
    CampaignOwnerService campaignOwnerService = new CampaignOwnerService(123, 1L, false);
    when(campaignOwnerServiceRepository.undoDelete(1L, 123)).thenReturn(1);
    when(campaignOwnerServiceRepository.findByCampaignIdAndOwnerServiceId(1L, 123))
        .thenReturn(campaignOwnerService);
    when(campaignOwnerServiceRepository.findServices()).thenReturn(campaignOwnerServices);
    when(campaignOwnerServiceService.findForDeletedServices()).thenReturn(campaignOwnerServices);
    when(ownerServiceService.findOwnerServiceById(1L)).thenReturn(ownerService);
    when(campaignOwnerServiceService.findAndUpdateServiceDeleted(campaignOwnerServices, 1L, 123))
        .thenReturn(campaignOwnerService);
    when(campaignOwnerServiceService.create(campaignOwnerService)).thenReturn(campaignOwnerService);
    assertEquals(123, campaignOwnerService.getCampaignId());
    assertEquals(1L, campaignOwnerService.getOwnerServiceId());
    assertFalse(campaignOwnerService.getDeleted());
  }

  @Test
  public void testFindCampaignOwnerServicesByCampaignId() {
    List<CampaignOwnerService> campaignOwnerServices = new ArrayList<>();
    campaignOwnerServices.add(new CampaignOwnerService(123, 1L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 2L, false));
    campaignOwnerServices.add(new CampaignOwnerService(123, 3L, false));
    when(campaignOwnerServiceRepository.findCampaignServicesByCampaignId(123))
        .thenReturn(campaignOwnerServices);
    when(campaignOwnerServiceService.findCampaignOwnerServicesByCampaignId(123))
        .thenReturn(campaignOwnerServices);
  }

  @Test
  public void testFindCampaignOwnerServicesByOwnerServiceId() {
    List<CampaignOwnerService> campaignOwnerServices = new ArrayList<>();
    campaignOwnerServices.add(new CampaignOwnerService(123, 1L, false));
    campaignOwnerServices.add(new CampaignOwnerService(124, 1L, false));
    campaignOwnerServices.add(new CampaignOwnerService(125, 1L, false));
    when(campaignOwnerServiceRepository.findCampaignServicesByOwnerServiceId(1L))
        .thenReturn(campaignOwnerServices);
    when(campaignOwnerServiceService.findCampaignOwnerServicesByOwnerServiceId(1L))
        .thenReturn(campaignOwnerServices);
  }
}
