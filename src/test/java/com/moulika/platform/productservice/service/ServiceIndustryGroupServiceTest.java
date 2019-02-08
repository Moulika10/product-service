package com.mapp.platform.productservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.mapp.platform.productservice.bean.ServiceIndustryGroup;
import com.mapp.platform.productservice.repository.ServiceIndustryGroupRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceIndustryGroupServiceTest {

  @Mock
  private ServiceIndustryGroupRepository serviceIndustryGroupRepository;

  @InjectMocks
  private ServiceIndustryGroupService serviceIndustryGroupService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findIndustryGroups() {
    List<ServiceIndustryGroup> serviceIndustryGroups = new ArrayList<>();
    serviceIndustryGroups.add(new ServiceIndustryGroup(null, "1111", "Oilseed and Grain Farming"));
    serviceIndustryGroups
        .add(new ServiceIndustryGroup(null, "1112", "Vegetable and Melon Farming"));
    serviceIndustryGroups.add(new ServiceIndustryGroup(null, "1113", "Fruit and Tree Nut Farming"));
    when(serviceIndustryGroupRepository.findAll()).thenReturn(serviceIndustryGroups);
    when(serviceIndustryGroupService.findIndustryGroups()).thenReturn(serviceIndustryGroups);
    assertEquals(3, serviceIndustryGroups.size());
  }

  @Test
  public void findIndustryGroupByCode() {
    ServiceIndustryGroup serviceIndustryGroups = new ServiceIndustryGroup(null, "1111",
        "Oilseed and Grain Farming");
    when(serviceIndustryGroupRepository.findByIndustryGroupCode("1111"))
        .thenReturn(serviceIndustryGroups);
    when(serviceIndustryGroupService.findIndustryGroupByCode("1111"))
        .thenReturn(serviceIndustryGroups);
    assertEquals("1111", serviceIndustryGroups.getIndustryGroupCode());
    assertEquals("Oilseed and Grain Farming", serviceIndustryGroups.getIndustryGroupName());
  }

}
