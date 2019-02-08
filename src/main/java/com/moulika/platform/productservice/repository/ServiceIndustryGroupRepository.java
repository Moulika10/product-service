package com.moulika.platform.productservice.repository;

import com.moulika.platform.productservice.bean.ServiceIndustryGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceIndustryGroupRepository extends CrudRepository<ServiceIndustryGroup, Long> {

    ServiceIndustryGroup findByIndustryGroupCode(String industryGroupCode);
}
