package com.moulika.platform.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moulika.platform.productservice.bean.OwnerProduct;
import com.moulika.platform.productservice.bean.ProductBrick;
import com.moulika.platform.productservice.bean.ProductClass;
import com.moulika.platform.productservice.bean.ProductFamily;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OwnerProductRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private OwnerProductRepository ownerProductRepository;

  private ProductFamily firstTestProductFamily;
  private ProductClass firstTestProductClass;
  private ProductBrick firstTestProductBrick;
  private OwnerProduct firstOwnerProductTest;
  private OwnerProduct secondOwnerProductTest;

  @Before
  public void initialize() {

    firstTestProductFamily = new ProductFamily(82000123, "Tools - Power", null);
    firstTestProductClass = new ProductClass(firstTestProductFamily, 71000000, "Computing", null);
    firstTestProductBrick = new ProductBrick(firstTestProductClass, 71000023, "Computing-tools");
    firstOwnerProductTest = new OwnerProduct(firstTestProductBrick,
        "397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals", 71000023L, false);
    secondOwnerProductTest = new OwnerProduct(firstTestProductBrick, "abc-um5999", "Baby Products",
        71000023L, true);
    entityManager.persist(firstTestProductFamily);
    entityManager.persist(firstTestProductClass);
    entityManager.persist(firstTestProductBrick);
    entityManager.persist(firstOwnerProductTest);
    entityManager.persist(secondOwnerProductTest);
  }

  @Test
  public void findAllOwnerProducts() {
    List<OwnerProduct> results = ownerProductRepository.findAll();
    assertThat(results.size()).isEqualTo(1);
    assertThat(results.get(0).getOwnerId()).isEqualTo(firstOwnerProductTest.getOwnerId());
    assertThat(results.get(0).getProductName()).isEqualTo(firstOwnerProductTest.getProductName());
    assertThat(results.get(0).getBrickCode()).isEqualTo(firstOwnerProductTest.getBrickCode());
  }

  @Test
  public void findAll() {
    List<OwnerProduct> results = ownerProductRepository.findProducts();
    assertThat(results.size()).isEqualTo(2);
    assertThat(results.get(0).getOwnerId()).isEqualTo(firstOwnerProductTest.getOwnerId());
    assertThat(results.get(0).getProductName()).isEqualTo(firstOwnerProductTest.getProductName());
    assertThat(results.get(0).getBrickCode()).isEqualTo(firstOwnerProductTest.getBrickCode());
  }

  @Test
  public void findOne() {
    OwnerProduct result = ownerProductRepository.findOne(firstOwnerProductTest.getId());
    assertThat(result.getOwnerId()).isEqualTo(firstOwnerProductTest.getOwnerId());
  }

  @Test
  public void undoDeleteTest() {
    int result = ownerProductRepository.undoDelete(71000023L, "abc-um5999", "Baby Products");
    assertThat(result).isEqualTo(1);
  }

  @Test
  public void findByOwnerIdAndBrickCode() {
    OwnerProduct result = ownerProductRepository
        .findByOwnerIdAndBrickCode(71000023L, "397d242a-4c59-478e-87c1-f81a50c8528e",
            "Vitamins/Minerals");
    assertThat(result.getOwnerId()).isEqualTo(firstOwnerProductTest.getOwnerId());
    assertThat(result.getBrickCode()).isEqualTo(firstOwnerProductTest.getBrickCode());
  }
}
