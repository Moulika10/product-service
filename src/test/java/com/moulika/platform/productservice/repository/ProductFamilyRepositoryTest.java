package com.mapp.platform.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
public class ProductFamilyRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProductFamilyRepository productFamilyRepository;

  private ProductFamily firstTestProductFamily;

  @Before
  public void initialize() {
    firstTestProductFamily = new ProductFamily(82000123, "Tools - Power", null);
    entityManager.persist(firstTestProductFamily);
  }

  @Test
  public void findFamilyById() {
    ProductFamily results = productFamilyRepository.findOne(82000123L);
    assertThat(results.getFamilyCode()).isEqualTo(firstTestProductFamily.getFamilyCode());
    assertThat(results.getFamilyDescription())
        .isEqualTo(firstTestProductFamily.getFamilyDescription());
    entityManager.flush();
  }
}
