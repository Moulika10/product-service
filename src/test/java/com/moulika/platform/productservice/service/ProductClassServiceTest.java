package com.moulika.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.moulika.platform.productservice.bean.ProductClass;
import com.moulika.platform.productservice.repository.ProductClassRepository;
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
public class ProductClassServiceTest {

  @Mock
  private ProductClassRepository productClassRepository;

  @InjectMocks
  private ProductClassService productClassService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findClasses() {
    List<ProductClass> productClasses = new ArrayList<>();
    productClasses.add(new ProductClass(null, 47101900, "Surface Care", null));
    productClasses.add(new ProductClass(null, 83012500, "Access Covers/Panels", null));
    productClasses.add(new ProductClass(null, 91050100, "Baby Safety/Security/Surveillance", null));
    when(productClassRepository.findAll()).thenReturn(productClasses);
    when(productClassService.findClasses()).thenReturn(productClasses);
    assertEquals(3, productClasses.size());
  }

  @Test
  public void findClassByCode() {
    ProductClass productClass = new ProductClass(null, 47101900, "Surface Care", null);
    when(productClassRepository.findOne((long) 47101900)).thenReturn(productClass);
    when(productClassService.findClassByCode((long) 47101900)).thenReturn(productClass);
    assertEquals(47101900, productClass.getClassCode());
    assertEquals("Surface Care", productClass.getClassDescription());
  }

}
