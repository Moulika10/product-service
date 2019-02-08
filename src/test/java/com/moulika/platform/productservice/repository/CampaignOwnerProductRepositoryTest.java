package com.mapp.platform.productservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mapp.platform.productservice.bean.CampaignOwnerProduct;
import com.mapp.platform.productservice.bean.OwnerProduct;
import com.mapp.platform.productservice.bean.ProductBrick;
import com.mapp.platform.productservice.bean.ProductClass;
import com.mapp.platform.productservice.bean.ProductFamily;
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
public class CampaignOwnerProductRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private OwnerProductRepository ownerProductRepository;

  @Autowired
  private CampaignOwnerProductRepository campaignOwnerProductRepository;

  private ProductFamily firstTestProductFamily;
  private ProductClass firstTestProductClass;
  private ProductBrick firstTestProductBrick;
  private OwnerProduct firstOwnerProductTest;
  private CampaignOwnerProduct firstCampaignOwnerProductTest;

  @Before
  public void initialize() {
    firstTestProductFamily = new ProductFamily(82000123, "Tools - Power", null);
    testEntityManager.persist(firstTestProductFamily);
    firstTestProductClass = new ProductClass(firstTestProductFamily, 71000000, "Computing", null);
    testEntityManager.persist(firstTestProductClass);
    firstTestProductBrick = new ProductBrick(firstTestProductClass, 71000023, "Computing-tools");
    testEntityManager.persist(firstTestProductBrick);
    firstOwnerProductTest = new OwnerProduct(firstTestProductBrick,
        "397d242a-4c59-478e-87c1-f81a50c8528e", "Vitamins/Minerals", 71000023L, false);
    testEntityManager.persist(firstOwnerProductTest);
    firstCampaignOwnerProductTest = new CampaignOwnerProduct(firstOwnerProductTest, 123,
        firstOwnerProductTest.getId(), false);
    testEntityManager.persist(firstCampaignOwnerProductTest);
  }

  @Test
  public void findOne() {
    CampaignOwnerProduct result = campaignOwnerProductRepository
        .findOne(firstCampaignOwnerProductTest.getId());
    assertThat(result.getOwnerProductId())
        .isEqualTo(firstCampaignOwnerProductTest.getOwnerProductId());
  }

  @Test
  public void findCampaignProductsByCampaignId() {
    List<CampaignOwnerProduct> results = campaignOwnerProductRepository
        .findCampaignProductsByCampaignId(123);
    assertThat(results.size()).isEqualTo(1);
    assertThat(results.get(0).getCampaignId())
        .isEqualTo(firstCampaignOwnerProductTest.getCampaignId());
  }

  @Test
  public void findCampaignProductsByOwnerProductId() {
    List<CampaignOwnerProduct> results = campaignOwnerProductRepository
        .findCampaignProductsByOwnerProductId(firstOwnerProductTest.getId());
    assertThat(results.size()).isEqualTo(1);
    assertThat(results.get(0).getOwnerProductId())
        .isEqualTo(firstCampaignOwnerProductTest.getOwnerProductId());
    assertThat(results.get(0).getCampaignId())
        .isEqualTo(firstCampaignOwnerProductTest.getCampaignId());
  }

  @Test
  public void findProducts() {
    List<CampaignOwnerProduct> results = campaignOwnerProductRepository
        .findProducts();
    assertThat(results.size()).isEqualTo(1);
    assertThat(results.get(0).getOwnerProductId())
        .isEqualTo(firstCampaignOwnerProductTest.getOwnerProductId());
    assertThat(results.get(0).getCampaignId())
        .isEqualTo(firstCampaignOwnerProductTest.getCampaignId());
  }

  @Test
  public void undoDeleteTest() {
    int result = campaignOwnerProductRepository.undoDelete(firstOwnerProductTest.getId(), 123);
    assertThat(result).isEqualTo(0);
  }

  @Test
  public void findByCampaignIdAndOwnerProductId() {
    CampaignOwnerProduct result = campaignOwnerProductRepository
        .findByCampaignIdAndOwnerProductId(firstOwnerProductTest.getId(), 123);
    assertThat(result.getCampaignId()).isEqualTo(firstCampaignOwnerProductTest.getCampaignId());
  }
}
