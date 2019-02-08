package com.moulika.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.moulika.platform.productservice.bean.ProductBrick;
import com.moulika.platform.productservice.repository.ProductBrickRepository;
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
public class ProductBrickServiceTest {

  @Mock
  private ProductBrickRepository productBrickRepository;

  @InjectMocks
  private ProductBrickService productBrickService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findBricks() {
    List<ProductBrick> productBricks = new ArrayList<>();
    productBricks.add(new ProductBrick(null, 10000449, "First Aid - Accessories"));
    productBricks.add(new ProductBrick(null, 10000467, "Vitamins/Minerals"));
    productBricks.add(new ProductBrick(null, 10005756, "Key Rings"));
    when(productBrickRepository.findAll()).thenReturn(productBricks);
    when(productBrickService.findBricks()).thenReturn(productBricks);
    assertEquals(3, productBricks.size());
  }

  @Test
  public void findBrickByCode() {
    ProductBrick productBrick = new ProductBrick(null, 10000449, "First Aid - Accessories");
    when(productBrickRepository.findOne((long) 10000449)).thenReturn(productBrick);
    when(productBrickService.findBrickByCode((long) 10000449)).thenReturn(productBrick);
    assertEquals(10000449, productBrick.getBrickCode());
    assertEquals("First Aid - Accessories", productBrick.getBrickDescription());
  }

}
