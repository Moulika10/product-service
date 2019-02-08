package com.mapp.platform.productservice.repository;

import com.mapp.platform.productservice.bean.OwnerService;
import com.mapp.platform.productservice.bean.dto.OwnerServiceDto;

import javax.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerServiceRepository extends JpaRepository<OwnerService, Long> {

    @Override
    @Transactional
    @Query("SELECT e FROM OwnerService e WHERE e.deleted = FALSE ")
    List<OwnerService> findAll();

    @Query("SELECT e FROM OwnerService e ")
    @Transactional
    List<OwnerService> findServices();

    @Transactional
    @Query("SELECT DISTINCT e FROM OwnerService e WHERE e.id = ?1 AND e.deleted = FALSE ")
    OwnerService findOne(Long id);

    @Transactional
    @Query("UPDATE OwnerService e SET e.deleted=TRUE WHERE e.id = ?1 ")
    @Modifying
    void delete(Long id);

    @Transactional
    @Modifying
    @Query(value = ""
            + "UPDATE OwnerService e SET e.deleted = FALSE, e.serviceName = ?3 " +
            "WHERE e.industryGroupCode = ?1 AND e.ownerId = ?2 AND e.deleted = TRUE ")
    int undoDelete(String industryGroupCode, String ownerId, String serviceName);

    @Transactional
    @Query("SELECT e FROM OwnerService e WHERE e.industryGroupCode =?1 AND e.ownerId = ?2 " +
            "AND e.serviceName = ?3 AND e.deleted = FALSE")
    OwnerService findByOwnerIdAndIndustryGroupCode(String industryGroupCode, String ownerId, String serviceName);

    @Transactional
    @Query("Select e FROM OwnerService e WHERE e.ownerId = ?1 AND e.deleted = FALSE ")
    List<OwnerService> findOwnerServices(OwnerServiceDto ownerServiceDto, String ownerId);
}
