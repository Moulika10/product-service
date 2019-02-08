package com.mapp.platform.productservice.repository;

import com.mapp.platform.productservice.bean.ServiceSuperSector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceSuperSectorRepository extends CrudRepository<ServiceSuperSector, Long> {

    ServiceSuperSector findBySuperSectorCode(String superSectorCode);
}
