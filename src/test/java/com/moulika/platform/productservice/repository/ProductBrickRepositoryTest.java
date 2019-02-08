package com.mapp.platform.productservice.repository;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.mapp.platform.productservice.bean.ProductBrick;
import com.mapp.platform.productservice.bean.ProductClass;
import com.mapp.platform.productservice.bean.ProductFamily;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class ProductBrickRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProductBrickRepository productBrickRepository;

  private ProductFamily firstTestProductFamily;
  private ProductClass firstTestProductClass;
  private ProductBrick firstTestProductBrick;

  @Before
  public void initialize() {
    firstTestProductFamily = new ProductFamily(82000123, "Tools - Power", null);
    firstTestProductClass = new ProductClass(firstTestProductFamily, 71000000, "Computing", null);
    firstTestProductBrick = new ProductBrick(firstTestProductClass, 71000023, "Computing-tools");
    entityManager.persist(firstTestProductFamily);
    entityManager.persist(firstTestProductClass);
    entityManager.persist(firstTestProductBrick);
  }

  @Test
  public void findBrickById() {
    ProductBrick results = productBrickRepository.findOne(71000023L);
    assertThat(results.getBrickCode()).isEqualTo(firstTestProductBrick.getBrickCode());
    assertThat(results.getBrickDescription())
        .isEqualTo(firstTestProductBrick.getBrickDescription());
    entityManager.flush();
  }

}
