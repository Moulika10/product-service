package com.moulika.platform.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moulika.platform.productservice.bean.ServiceSubSector;
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
public class ServiceSubSectorRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ServiceSubSectorRepository serviceSubSectorRepository;

  private ServiceSubSector firstTestServiceSubSector;

  @Before
  public void initialize() {
    firstTestServiceSubSector = new ServiceSubSector("456", "Tools/Equipment - Power", null);
    entityManager.persist(firstTestServiceSubSector);
  }

  @Test
  public void findSubSectorById() {
    ServiceSubSector results = serviceSubSectorRepository.findBySubSectorCode("456");
    assertThat(results.getSubSectorCode()).isEqualTo(firstTestServiceSubSector.getSubSectorCode());
    assertThat(results.getSubSectorName()).isEqualTo(firstTestServiceSubSector.getSubSectorName());
  }

}
