package com.mapp.platform.productservice.repository;

import com.mapp.platform.productservice.bean.ProductClass;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductClassRepository extends CrudRepository<ProductClass, Long> {

    @Transactional
    @Query("SELECT DISTINCT e FROM ProductClass e WHERE e.classCode = ?1 ")
    ProductClass findOne(Long classCode);
}
