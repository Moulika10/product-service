package com.moulika.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.moulika.platform.productservice.bean.CampaignOwnerProduct;
import com.moulika.platform.productservice.bean.OwnerProduct;
import com.moulika.platform.productservice.exception.ProductServiceException;

import com.moulika.platform.productservice.repository.CampaignOwnerProductRepository;
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
public class CampaignOwnerProductServiceTest {

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Mock
  private CampaignOwnerProductRepository campaignOwnerProductRepository;

  @Mock
  private OwnerProductService ownerProductService;

  @InjectMocks
  private CampaignOwnerProductService campaignOwnerProductService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findCampaignOwnerProductById() {
    CampaignOwnerProduct campaignOwnerProduct = new CampaignOwnerProduct(null, 123, 1L, false);
    when(campaignOwnerProductRepository.findOne((long) 1)).thenReturn(campaignOwnerProduct);
    when(campaignOwnerProductService.findCampaignOwnerProductById(1))
        .thenReturn(campaignOwnerProduct);
    assertEquals(123, campaignOwnerProduct.getCampaignId());
    assertEquals((long) 1, campaignOwnerProduct.getOwnerProductId());
    assertFalse(campaignOwnerProduct.getDeleted());
  }

  @Test
  public void findForDeletedProducts() {
    List<CampaignOwnerProduct> campaignOwnerProducts = new ArrayList<>();
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 1L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 2L, false));
    when(campaignOwnerProductRepository.findProducts()).thenReturn(campaignOwnerProducts);
    when(campaignOwnerProductService.findForDeletedProducts()).thenReturn(campaignOwnerProducts);
    assertEquals(2, campaignOwnerProducts.size());
  }

  @Test
  public void insert() {
    CampaignOwnerProduct campaignOwnerProduct = new CampaignOwnerProduct(null, 123, 1L, false);
    when(campaignOwnerProductRepository.save(campaignOwnerProduct))
        .thenReturn(campaignOwnerProduct);
    when(campaignOwnerProductService.insert(campaignOwnerProduct)).thenReturn(campaignOwnerProduct);
    assertEquals(123, campaignOwnerProduct.getCampaignId());
    assertEquals(1L, campaignOwnerProduct.getOwnerProductId());
    assertFalse(campaignOwnerProduct.getDeleted());
  }

  @Test
  public void delete() {
    doNothing().when(campaignOwnerProductRepository).delete((long) 1);
    campaignOwnerProductService.delete((long) 1);
  }

  @Test
  public void findAndUpdateProductDeleted() {
    List<CampaignOwnerProduct> campaignOwnerProducts = new ArrayList<>();
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 1L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 2L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 3L, false));
    CampaignOwnerProduct campaignOwnerProduct = new CampaignOwnerProduct(null, 123, 1L, false);
    when(campaignOwnerProductRepository.undoDelete(1L, 123)).thenReturn(1);
    when(campaignOwnerProductRepository.findByCampaignIdAndOwnerProductId(1L, 123))
        .thenReturn(campaignOwnerProduct);
    when(campaignOwnerProductService.findAndUpdateProductDeleted(campaignOwnerProducts, 1L, 123))
        .thenReturn(campaignOwnerProduct);
    assertEquals(123, campaignOwnerProduct.getCampaignId());
    assertEquals(1L, campaignOwnerProduct.getOwnerProductId());
    assertFalse(campaignOwnerProduct.getDeleted());
  }

  @Test
  public void containsOwnerProductId() {
    List<CampaignOwnerProduct> campaignOwnerProducts = new ArrayList<>();
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 1L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 2L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 3L, false));
    assertTrue(campaignOwnerProductService.containsOwnerProductId(campaignOwnerProducts, 1L));
  }

  @Test
  public void containsProductCampaignId() {
    List<CampaignOwnerProduct> campaignOwnerProducts = new ArrayList<>();
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 1L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 2L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 3L, false));
    assertTrue(campaignOwnerProductService.containsProductCampaignId(campaignOwnerProducts, 123));
  }

  @Test
  public void create() throws ProductServiceException {
    List<CampaignOwnerProduct> campaignOwnerProducts = new ArrayList<>();
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 1L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 2L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 3L, false));
    OwnerProduct ownerProduct = new OwnerProduct(null, "abc", "Vitamins", (long) 10000467, false);
    CampaignOwnerProduct campaignOwnerProduct = new CampaignOwnerProduct(null, 123, 1L, false);
    when(campaignOwnerProductRepository.findProducts()).thenReturn(campaignOwnerProducts);
    when(campaignOwnerProductService.findForDeletedProducts()).thenReturn(campaignOwnerProducts);
    when(ownerProductService.findOwnerProductById(1L)).thenReturn(ownerProduct);
    when(campaignOwnerProductRepository.save(campaignOwnerProduct))
        .thenReturn(campaignOwnerProduct);
    when(campaignOwnerProductService.insert(campaignOwnerProduct)).thenReturn(campaignOwnerProduct);
    when(campaignOwnerProductService.create(campaignOwnerProduct)).thenReturn(campaignOwnerProduct);
    assertEquals(123, campaignOwnerProduct.getCampaignId());
    assertEquals(1L, campaignOwnerProduct.getOwnerProductId());
    assertFalse(campaignOwnerProduct.getDeleted());
  }

  @Test
  public void createWhenOwnerProductIsNull() throws ProductServiceException {
    expectedEx.expectMessage("Owner Product does not exists");
    CampaignOwnerProduct campaignOwnerProduct = new CampaignOwnerProduct(null, 123, 1L, false);
    when(ownerProductService.findOwnerProductById(1L)).thenReturn(null);
    when(campaignOwnerProductService.create(campaignOwnerProduct)).thenReturn(null);
  }

  @Test
  public void createWhenSoftDelete() throws Exception {
    List<CampaignOwnerProduct> campaignOwnerProducts = new ArrayList<>();
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 1L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 2L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 3L, false));
    OwnerProduct ownerProduct = new OwnerProduct(null, "abc", "Vitamins", (long) 10000467, false);
    CampaignOwnerProduct campaignOwnerProduct = new CampaignOwnerProduct(null, 123, 1L, false);
    when(campaignOwnerProductRepository.undoDelete(1L, 123)).thenReturn(1);
    when(campaignOwnerProductRepository.findByCampaignIdAndOwnerProductId(1L, 123))
        .thenReturn(campaignOwnerProduct);
    when(campaignOwnerProductRepository.findProducts()).thenReturn(campaignOwnerProducts);
    when(campaignOwnerProductService.findForDeletedProducts()).thenReturn(campaignOwnerProducts);
    when(ownerProductService.findOwnerProductById(1L)).thenReturn(ownerProduct);
    when(campaignOwnerProductService.findAndUpdateProductDeleted(campaignOwnerProducts, 1L, 123))
        .thenReturn(campaignOwnerProduct);
    when(campaignOwnerProductService.create(campaignOwnerProduct)).thenReturn(campaignOwnerProduct);
    assertEquals(123, campaignOwnerProduct.getCampaignId());
    assertEquals(1L, campaignOwnerProduct.getOwnerProductId());
    assertFalse(campaignOwnerProduct.getDeleted());
  }

  @Test
  public void findCampaignProductsByCampaignId() {
    List<CampaignOwnerProduct> campaignOwnerProducts = new ArrayList<>();
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 1L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 2L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 3L, false));
    when(campaignOwnerProductRepository.findCampaignProductsByCampaignId(123))
        .thenReturn(campaignOwnerProducts);
    when(campaignOwnerProductService.findCampaignProductsByCampaignId(1))
        .thenReturn(campaignOwnerProducts);
  }

  @Test
  public void findCampaignProductsByOwnerProductId() {
    List<CampaignOwnerProduct> campaignOwnerProducts = new ArrayList<>();
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 123, 1L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 124, 1L, false));
    campaignOwnerProducts.add(new CampaignOwnerProduct(null, 125, 1L, false));
    when(campaignOwnerProductRepository.findCampaignProductsByOwnerProductId(1L))
        .thenReturn(campaignOwnerProducts);
    when(campaignOwnerProductService.findCampaignProductsByOwnerProductId(1L))
        .thenReturn(campaignOwnerProducts);
  }
}

