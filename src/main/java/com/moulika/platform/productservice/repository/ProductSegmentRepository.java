package com.mapp.platform.productservice.repository;

import com.mapp.platform.productservice.bean.ProductSegment;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSegmentRepository extends CrudRepository<ProductSegment, Long> {

    @Transactional
    @Query("SELECT DISTINCT e FROM ProductSegment e WHERE e.segmentCode = ?1 ")
    ProductSegment findOne(Long segmentCode);
}
