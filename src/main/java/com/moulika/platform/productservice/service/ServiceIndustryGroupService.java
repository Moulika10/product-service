package com.mapp.platform.productservice.service;

import com.mapp.platform.productservice.bean.ServiceIndustryGroup;
import com.mapp.platform.productservice.repository.ServiceIndustryGroupRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServiceIndustryGroupService {
    private final ServiceIndustryGroupRepository serviceIndustryGroupRepository;

    public ServiceIndustryGroupService(ServiceIndustryGroupRepository serviceIndustryGroupRepository) {
        this.serviceIndustryGroupRepository = serviceIndustryGroupRepository;
    }

    /**
     * @return list of ServiceIndustryGroups.
     */
    public List<ServiceIndustryGroup> findIndustryGroups() {
        List<ServiceIndustryGroup> serviceIndustryGroups;
        serviceIndustryGroups = (List<ServiceIndustryGroup>) serviceIndustryGroupRepository.findAll();
        return serviceIndustryGroups;
    }

    /**
     * @param industryGroupCode which is given in the request body.
     * @return ServiceIndustryGroup based on the industryGroupCode.
     */
    public ServiceIndustryGroup findIndustryGroupByCode(String industryGroupCode) {
        return serviceIndustryGroupRepository.findByIndustryGroupCode(industryGroupCode);
    }
}
