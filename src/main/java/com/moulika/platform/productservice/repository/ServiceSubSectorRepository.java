package com.moulika.platform.productservice.repository;

import com.moulika.platform.productservice.bean.ServiceSubSector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceSubSectorRepository extends CrudRepository<ServiceSubSector, Long> {

    ServiceSubSector findBySubSectorCode(String subSectorCode);
}
