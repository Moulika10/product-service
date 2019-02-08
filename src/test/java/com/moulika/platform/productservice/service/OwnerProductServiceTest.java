package com.mapp.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.mapp.platform.productservice.bean.OwnerProduct;
import com.mapp.platform.productservice.bean.ProductBrick;
import com.mapp.platform.productservice.bean.dto.OwnerProductDto;
import com.mapp.platform.productservice.dao.OwnerProductDaoImpl;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.repository.OwnerProductRepository;
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
public class OwnerProductServiceTest {

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Mock
  private OwnerProductRepository ownerProductRepository;

  @Mock
  private ProductBrickService productBrickService;

  @InjectMocks
  private OwnerProductService ownerProductService;

  @Mock
  private OwnerProductDaoImpl ownerProductDao;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findAllProducts() {
    List<OwnerProduct> ownerProducts = new ArrayList<>();
    ownerProducts.add(new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants",
        (long) 10006193, false));
    ownerProducts.add(
        new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "First Aid - Accessories",
            (long) 10000449, false));
    ownerProducts.add(
        new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals",
            (long) 10000467, false));
    when(ownerProductRepository.findAll()).thenReturn(ownerProducts);
    when(ownerProductService.findAllProducts()).thenReturn(ownerProducts);
    assertEquals(3, ownerProducts.size());
  }

  @Test
  public void findOwnerProductById() {
    OwnerProduct ownerProduct = new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e",
        "Red Currants", 10006193, false);
    when(ownerProductRepository.findOne(ownerProduct.getId())).thenReturn(ownerProduct);
    when(ownerProductService.findOwnerProductById(1)).thenReturn(ownerProduct);
    assertEquals("397d242a-4c59-478e-87c1-f81a50c8528e", ownerProduct.getOwnerId());
    assertEquals("Red Currants", ownerProduct.getProductName());
    assertEquals((long) 10006193, (long) ownerProduct.getBrickCode());
    assertFalse(ownerProduct.getDeleted());
  }

  @Test
  public void findForDeletedProducts() {
    List<OwnerProduct> ownerProducts = new ArrayList<>();
    ownerProducts.add(new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants",
        (long) 10006193, true));
    ownerProducts.add(
        new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "First Aid - Accessories",
            (long) 10000449, false));
    when(ownerProductRepository.findProducts()).thenReturn(ownerProducts);
    when(ownerProductService.findForDeletedProducts()).thenReturn(ownerProducts);
    assertEquals(2, ownerProducts.size());
  }

  @Test
  public void insert() {
    OwnerProduct ownerProduct = new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e",
        "Red Currants", 10006193, false);
    when(ownerProductRepository.save(ownerProduct)).thenReturn(ownerProduct);
    when(ownerProductService.insert(ownerProduct)).thenReturn(ownerProduct);
    assertEquals("397d242a-4c59-478e-87c1-f81a50c8528e", ownerProduct.getOwnerId());
    assertEquals("Red Currants", ownerProduct.getProductName());
    assertEquals((long) 10006193, (long) ownerProduct.getBrickCode());
    assertFalse(ownerProduct.getDeleted());
  }

  @Test
  public void delete() {
    doNothing().when(ownerProductRepository).delete((long) 1);
    ownerProductService.delete((long) 1);
  }

  @Test
  public void findAndUpdateProductDeleted() {
    List<OwnerProduct> ownerProducts = new ArrayList<>();
    ownerProducts.add(new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants",
        (long) 10006193, false));
    ownerProducts.add(
        new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "First Aid - Accessories",
            (long) 10000449, false));
    ownerProducts.add(
        new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals",
            (long) 10000467, false));
    OwnerProduct ownerProduct = new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e",
        "Red Currants", (long) 10006193, false);
    when(ownerProductRepository
        .undoDelete((long) 10006193, "397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants"))
        .thenReturn(1);
    when(ownerProductRepository
        .findByOwnerIdAndBrickCode((long) 10006193, "397d242a-4c59-478e-87c1-f81a50c8528e",
            "Red Currants")).thenReturn(ownerProduct);
    when(ownerProductService.findAndUpdateProductDeleted(ownerProducts, (long) 10006193,
        "397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants")).thenReturn(ownerProduct);
    assertEquals("397d242a-4c59-478e-87c1-f81a50c8528e", ownerProduct.getOwnerId());
    assertEquals("Red Currants", ownerProduct.getProductName());
    assertEquals((long) 10006193, (long) ownerProduct.getBrickCode());
    assertFalse(ownerProduct.getDeleted());
  }

  @Test
  public void containsBrickCode() {
    List<OwnerProduct> ownerProducts = new ArrayList<>();
    ownerProducts.add(new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants",
        (long) 10006193, false));
    ownerProducts.add(
        new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "First Aid - Accessories",
            (long) 10000449, false));
    ownerProducts.add(
        new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals",
            (long) 10000467, false));
    assertTrue(ownerProductService.containsBrickCode(ownerProducts, (long) 10006193));
  }

  @Test
  public void containsProductOwnerId() {
    List<OwnerProduct> ownerProducts = new ArrayList<>();
    ownerProducts.add(new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50c8528e", "Red Currants",
        (long) 10006193, false));
    ownerProducts.add(
        new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50a3528e", "First Aid - Accessories",
            (long) 10000449, false));
    ownerProducts.add(
        new OwnerProduct(null, "397d242a-4c59-478e-87c1-f81a50bf528e", "Vitamins/Minerals",
            (long) 10000467, false));
    assertTrue(ownerProductService
        .containsProductOwnerId(ownerProducts, "397d242a-4c59-478e-87c1-f81a50c8528e"));
  }

  @Test
  public void create() throws ProductServiceException {
    List<OwnerProduct> ownerProducts = new ArrayList<>();
    ownerProducts.add(new OwnerProduct(null, "123", "Red Currants", (long) 10006193, false));
    ownerProducts
        .add(new OwnerProduct(null, "125", "First Aid - Accessories", (long) 10000449, false));
    ownerProducts.add(new OwnerProduct(null, "1456", "Vitamins/Minerals", (long) 10000467, false));
    ProductBrick productBrick = new ProductBrick(null, 10000467, "Vitamins/Minerals");
    OwnerProduct ownerProduct = new OwnerProduct(null, "1456", "Vitamins/Minerals", (long) 10000467,
        false);
    when(ownerProductService.findForDeletedProducts()).thenReturn(ownerProducts);
    when(productBrickService.findBrickByCode((long) 10000467)).thenReturn(productBrick);
    when(ownerProductService.insert(ownerProduct)).thenReturn(ownerProduct);
    when(ownerProductService.create(ownerProduct)).thenReturn(ownerProduct);
    assertEquals("1456", ownerProduct.getOwnerId());
    assertEquals("Vitamins/Minerals", ownerProduct.getProductName());
    assertEquals((long) 10000467, (long) ownerProduct.getBrickCode());
  }

  @Test
  public void createWhenNoBrickCode() throws ProductServiceException {
    expectedEx.expectMessage("Brick Code does not exists");
    OwnerProduct ownerProduct = new OwnerProduct(null, "1456", "Vitamins/Minerals", (long) 10000467,
        false);
    when(productBrickService.findBrickByCode((long) 10000467)).thenReturn(null);
    when(ownerProductService.create(ownerProduct)).thenReturn(null);
  }

  @Test
  public void createWhenSoftDelete() throws Exception {
    List<OwnerProduct> ownerProducts = new ArrayList<>();
    ownerProducts.add(new OwnerProduct(null, "123", "Red Currants", (long) 10006193, false));
    ownerProducts
        .add(new OwnerProduct(null, "123", "First Aid - Accessories", (long) 10000449, false));
    ownerProducts.add(new OwnerProduct(null, "123", "Vitamins/Minerals", (long) 10000467, false));
    OwnerProduct ownerProduct = new OwnerProduct(null, "123", "Red Currants", (long) 10006193,
        false);
    ProductBrick productBrick = new ProductBrick(null, 10000467, "Red Currants");
    when(ownerProductRepository.undoDelete((long) 10006193, "123", "Red Currants")).thenReturn(1);
    when(ownerProductRepository.findByOwnerIdAndBrickCode((long) 10006193, "123", "Red Currants"))
        .thenReturn(ownerProduct);
    when(ownerProductService.findForDeletedProducts()).thenReturn(ownerProducts);
    when(productBrickService.findBrickByCode((long) 10006193)).thenReturn(productBrick);
    when(ownerProductService
        .findAndUpdateProductDeleted(ownerProducts, (long) 10006193, "123", "Red Currants"))
        .thenReturn(ownerProduct);
    when(ownerProductService.create(ownerProduct)).thenReturn(ownerProduct);
    assertEquals("123", ownerProduct.getOwnerId());
    assertEquals("Red Currants", ownerProduct.getProductName());
    assertEquals((long) 10006193, (long) ownerProduct.getBrickCode());
  }

  @Test
  public void findByID() {
    OwnerProductDto ownerProductDto = new OwnerProductDto(1, "397d2-f8528e",
        "Cortaderia Selloana - Live Plants", 10006600, "Cortaderia Selloana - Live Plants",
        93033900, "Cortaderia - Live Plants");
    when(ownerProductDao.findOne(1L)).thenReturn(ownerProductDto);
    when(ownerProductService.findByID(1L)).thenReturn(ownerProductDto);
    assertEquals(1, ownerProductDto.getId());
    assertEquals("397d2-f8528e", ownerProductDto.getOwnerId());
    assertEquals("Cortaderia Selloana - Live Plants", ownerProductDto.getProductName());
    assertEquals(10006600, ownerProductDto.getBrickCode());
    assertEquals("Cortaderia Selloana - Live Plants", ownerProductDto.getBrickDescription());
    assertEquals(93033900, ownerProductDto.getClassCode());
    assertEquals("Cortaderia - Live Plants", ownerProductDto.getClassDescription());
  }

  @Test
  public void findOwnerProductsByOwnerId() {
    List<OwnerProductDto> ownerProducts = new ArrayList<>();
    ownerProducts.add(
        new OwnerProductDto(1, "397d2-f8528e", "Cortaderia Selloana - Live Plants", 10006600,
            "Selloana - Live Plants", 93033900, "Cortaderia - Live Plants"));
    ownerProducts.add(
        new OwnerProductDto(2, "397d2-f8528e", "Cortaderia Selloana - Live Plants", 10006600,
            "Algae - Live Plants", 93033900, "Cortaderia - Live Plants"));
    ownerProducts.add(
        new OwnerProductDto(3, "397d2-f8528e", "Cortaderia Selloana - Live Plants", 10006600,
            "Cars - Live Plants", 93033900, "Cortaderia - Live Plants"));
    when(ownerProductService.findOwnerProductsByOwnerId("397d2-f8528e")).thenReturn(ownerProducts);
  }
}



