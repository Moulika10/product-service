package com.mapp.platform.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mapp.platform.productservice.bean.CampaignOwnerService;
import com.mapp.platform.productservice.bean.OwnerService;
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
public class CampaignOwnerServiceRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private OwnerServiceRepository ownerServiceRepository;

  @Autowired
  private CampaignOwnerServiceRepository campaignOwnerServiceRepository;

  private OwnerService firstOwnerServiceTest;
  private CampaignOwnerService firstCampaignOwnerServiceTest;
  private CampaignOwnerService secondCampaignOwnerServiceTest;
  private CampaignOwnerService thirdCampaignOwnerServiceTest;

  @Before
  public void initialize() {
    firstOwnerServiceTest = new OwnerService("397d242a-4c59-478e-87c1-f81a50c8528e",
        "Vitamins/Minerals", "1111", false);
    testEntityManager.persist(firstOwnerServiceTest);
    firstCampaignOwnerServiceTest = new CampaignOwnerService(123, firstOwnerServiceTest.getId(),
        false);
    secondCampaignOwnerServiceTest = new CampaignOwnerService(124, firstOwnerServiceTest.getId(),
        false);
    thirdCampaignOwnerServiceTest = new CampaignOwnerService(125, firstOwnerServiceTest.getId(),
        true);
    testEntityManager.persist(firstCampaignOwnerServiceTest);
    testEntityManager.persist(secondCampaignOwnerServiceTest);
    testEntityManager.persist(thirdCampaignOwnerServiceTest);
  }

  @Test
  public void findOne() {
    CampaignOwnerService result = campaignOwnerServiceRepository
        .findOne(firstCampaignOwnerServiceTest.getId());
    assertThat(result.getCampaignId())
        .isEqualTo(firstCampaignOwnerServiceTest.getCampaignId());
  }

  @Test
  public void findCampaignServicesByCampaignId() {
    List<CampaignOwnerService> results = campaignOwnerServiceRepository
        .findCampaignServicesByCampaignId(123);
    assertThat(results.size()).isEqualTo(1);
    assertThat(results.get(0).getOwnerServiceId())
        .isEqualTo(firstCampaignOwnerServiceTest.getOwnerServiceId());
    assertThat(results.get(0).getCampaignId())
        .isEqualTo(firstCampaignOwnerServiceTest.getCampaignId());
  }

  @Test
  public void findCampaignServicesByOwnerServiceId() {
    List<CampaignOwnerService> results = campaignOwnerServiceRepository
        .findCampaignServicesByOwnerServiceId(firstCampaignOwnerServiceTest.getOwnerServiceId());
    assertThat(results.get(0).getOwnerServiceId())
        .isEqualTo(firstCampaignOwnerServiceTest.getOwnerServiceId());
    assertThat(results.get(0).getCampaignId())
        .isEqualTo(firstCampaignOwnerServiceTest.getCampaignId());
  }

  @Test
  public void findServices() {
    List<CampaignOwnerService> results = campaignOwnerServiceRepository.findServices();
    assertThat(results.get(0).getOwnerServiceId())
        .isEqualTo(firstCampaignOwnerServiceTest.getOwnerServiceId());
    assertThat(results.get(0).getCampaignId())
        .isEqualTo(firstCampaignOwnerServiceTest.getCampaignId());
  }
}
