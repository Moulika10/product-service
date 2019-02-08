package com.mapp.platform.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
public class ProductClassRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProductClassRepository productClassRepository;

  private ProductFamily firstTestProductFamily;
  private ProductClass firstTestProductClass;

  @Before
  public void initialize() {
    firstTestProductFamily = new ProductFamily(82000123, "Tools - Power", null);
    firstTestProductClass = new ProductClass(firstTestProductFamily, 71000000, "Computing", null);
    entityManager.persist(firstTestProductFamily);
    entityManager.persist(firstTestProductClass);
  }

  @Test
  public void findClassById() {
    ProductClass results = productClassRepository.findOne(71000000L);
    assertThat(results.getClassCode()).isEqualTo(firstTestProductClass.getClassCode());
    assertThat(results.getClassDescription())
        .isEqualTo(firstTestProductClass.getClassDescription());
    entityManager.flush();
  }
}
