package com.mapp.platform.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mapp.platform.productservice.bean.ServiceIndustryGroup;
import com.mapp.platform.productservice.bean.ServiceSubSector;
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
public class ServiceIndustryGroupRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ServiceIndustryGroupRepository serviceIndustryGroupRepository;

  private ServiceSubSector firstTestServiceSubSector;
  private ServiceIndustryGroup firstTestServiceIndustryGroup;

  @Before
  public void initialize() {
    firstTestServiceSubSector = new ServiceSubSector("567", "Tools/Equipment - Power", null);
    firstTestServiceIndustryGroup = new ServiceIndustryGroup(firstTestServiceSubSector, "7777",
        "Tools/Equipment - Power");
    entityManager.persist(firstTestServiceSubSector);
    entityManager.persist(firstTestServiceIndustryGroup);
  }

  @Test
  public void findIndustryGroupById() {
    ServiceIndustryGroup results = serviceIndustryGroupRepository.findByIndustryGroupCode("7777");
    assertThat(results.getIndustryGroupCode())
        .isEqualTo(firstTestServiceIndustryGroup.getIndustryGroupCode());
    assertThat(results.getIndustryGroupName())
        .isEqualTo(firstTestServiceIndustryGroup.getIndustryGroupName());
  }
}
