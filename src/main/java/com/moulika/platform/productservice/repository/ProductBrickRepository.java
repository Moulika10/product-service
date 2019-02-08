package com.mapp.platform.productservice.repository;

import com.mapp.platform.productservice.bean.ProductBrick;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBrickRepository extends CrudRepository<ProductBrick, Long> {

    @Transactional
    @Query("SELECT DISTINCT e FROM ProductBrick e WHERE e.brickCode = ?1 ")
    ProductBrick findOne(Long brickCode);
}
