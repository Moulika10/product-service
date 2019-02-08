package com.moulika.platform.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moulika.platform.productservice.bean.ProductSegment;
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
public class ProductSegmentRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProductSegmentRepository productSegmentRepository;

  private ProductSegment firstTestProductSegment;


  @Before
  public void initialize() {
    firstTestProductSegment = new ProductSegment(82000000, "Tools/Equipment - Power", null);
    entityManager.persist(firstTestProductSegment);
  }

  @Test
  public void findSegmentById() {
    ProductSegment results = productSegmentRepository.findOne(82000000L);
    assertThat(results.getSegmentCode()).isEqualTo(firstTestProductSegment.getSegmentCode());
    assertThat(results.getSegmentDescription())
        .isEqualTo(firstTestProductSegment.getSegmentDescription());
  }

}
