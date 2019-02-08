package com.mapp.platform.productservice.repository;

import com.mapp.platform.productservice.bean.OwnerProduct;

import javax.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerProductRepository extends CrudRepository<OwnerProduct, Long> {

    @Override
    @Transactional
    @Query("SELECT e FROM OwnerProduct e WHERE e.deleted = FALSE ")
    List<OwnerProduct> findAll();

    @Query("SELECT e FROM OwnerProduct e ")
    @Transactional
    List<OwnerProduct> findProducts();

    @Transactional
    @Query("SELECT DISTINCT e FROM OwnerProduct e WHERE e.id = ?1 and e.deleted = FALSE ")
    OwnerProduct findOne(Long id);

    @Transactional
    @Query("UPDATE OwnerProduct e SET e.deleted = TRUE where e.id = ?1 ")
    @Modifying
    void delete(Long id);

    @Transactional
    @Modifying
    @Query(value = ""
            + "UPDATE OwnerProduct e SET e.deleted = FALSE, e.productName =?3 " +
            "WHERE e.brickCode = ?1 AND e.ownerId =?2 AND e.deleted = TRUE ")
    int undoDelete(Long brickCode, String ownerId, String productName);

    @Transactional
    @Query("SELECT e FROM OwnerProduct e WHERE e.brickCode =?1 AND e.ownerId =?2 " +
            "AND e.productName = ?3 AND e.deleted = FALSE ")
    OwnerProduct findByOwnerIdAndBrickCode(Long brickCode, String ownerId, String productName);

    @Transactional
    @Query("Select e FROM OwnerProduct e WHERE e.ownerId = ?1 AND e.deleted = FALSE ")
    List<OwnerProduct> findOwnerProducts(String ownerId);
}
