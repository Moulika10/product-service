package com.moulika.platform.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moulika.platform.productservice.bean.ServiceSuperSector;
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
public class ServiceSuperSectorRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ServiceSuperSectorRepository serviceSuperSectorRepository;

  private ServiceSuperSector firstTestServiceSuperSector;

  @Before
  public void initialize() {
    firstTestServiceSuperSector = new ServiceSuperSector("11", "Tools/Equipment - Power", null);
    entityManager.persist(firstTestServiceSuperSector);
  }

  @Test
  public void findSuperSectorById() {
    ServiceSuperSector results = serviceSuperSectorRepository.findBySuperSectorCode("11");
    assertThat(results.getSuperSectorCode())
        .isEqualTo(firstTestServiceSuperSector.getSuperSectorCode());
    assertThat(results.getSuperSectorName())
        .isEqualTo(firstTestServiceSuperSector.getSuperSectorName());
  }
}
