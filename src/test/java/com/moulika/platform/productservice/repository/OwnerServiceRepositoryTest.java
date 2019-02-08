package com.moulika.platform.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moulika.platform.productservice.bean.OwnerService;
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
public class OwnerServiceRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private OwnerServiceRepository ownerServiceRepository;

  private OwnerService firstOwnerServiceTest;
  private OwnerService secondOwnerServiceTest;

  @Before
  public void initialize() {

    firstOwnerServiceTest = new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e",
        "Vitamins/Minerals", "1111", false);
    secondOwnerServiceTest = new OwnerService("abc-um5999", "Baby Products", "1111", true);
    entityManager.persist(firstOwnerServiceTest);
    entityManager.persist(secondOwnerServiceTest);
  }

  @Test
  public void findAllOwnerServices() {
    List<OwnerService> results = ownerServiceRepository.findAll();
    assertThat(results.size()).isEqualTo(1);
    assertThat(results.get(0).getOwnerId()).isEqualTo(firstOwnerServiceTest.getOwnerId());
    assertThat(results.get(0).getServiceName()).isEqualTo(firstOwnerServiceTest.getServiceName());
    assertThat(results.get(0).getIndustryGroupCode())
        .isEqualTo(firstOwnerServiceTest.getIndustryGroupCode());
  }

  @Test
  public void findAll() {
    List<OwnerService> results = ownerServiceRepository.findServices();
    assertThat(results.size()).isEqualTo(2);
    assertThat(results.get(0).getOwnerId()).isEqualTo(firstOwnerServiceTest.getOwnerId());
    assertThat(results.get(0).getServiceName()).isEqualTo(firstOwnerServiceTest.getServiceName());
    assertThat(results.get(0).getIndustryGroupCode())
        .isEqualTo(firstOwnerServiceTest.getIndustryGroupCode());
  }

  @Test
  public void findOne() {
    OwnerService result = ownerServiceRepository.findOne(firstOwnerServiceTest.getId());
    assertThat(result.getServiceName()).isEqualTo(firstOwnerServiceTest.getServiceName());
  }

  @Test
  public void undoDeleteTest() {
    int result = ownerServiceRepository.undoDelete("1111", "abc-um5999", "Baby Products");
    assertThat(result).isEqualTo(1);
  }

  @Test
  public void findByOwnerIdAndIndustryGroupCode() {
    OwnerService result = ownerServiceRepository
        .findByOwnerIdAndIndustryGroupCode("1111", "397d242a-4c59-478e-87c1-f81a50c8528e",
            "Vitamins/Minerals");
    assertThat(result.getOwnerId()).isEqualTo(firstOwnerServiceTest.getOwnerId());
    assertThat(result.getIndustryGroupCode())
        .isEqualTo(firstOwnerServiceTest.getIndustryGroupCode());
  }
}
