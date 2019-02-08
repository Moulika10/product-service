package com.mapp.platform.productservice.repository;

import com.mapp.platform.productservice.bean.ServiceSubSector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceSubSectorRepository extends CrudRepository<ServiceSubSector, Long> {

    ServiceSubSector findBySubSectorCode(String subSectorCode);
}
