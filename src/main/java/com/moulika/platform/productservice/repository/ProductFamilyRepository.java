package com.moulika.platform.productservice.repository;

import com.moulika.platform.productservice.bean.ProductFamily;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFamilyRepository extends CrudRepository<ProductFamily, Long> {

    @Transactional
    @Query("SELECT DISTINCT e FROM ProductFamily e WHERE e.familyCode = ?1 ")
    ProductFamily findOne(Long familyCode);
}
