package com.mapp.platform.productservice.repository;

import com.mapp.platform.productservice.bean.CampaignOwnerProduct;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CampaignOwnerProductRepository extends CrudRepository<CampaignOwnerProduct, Long> {

    @Transactional
    @Query("SELECT c FROM CampaignOwnerProduct c INNER JOIN c.ownerProduct op WHERE c.id = ?1 " +
            "AND c.deleted = FALSE AND op.deleted = FALSE ")
    CampaignOwnerProduct findOne(long id);

    @Transactional
    @Query("Select c FROM CampaignOwnerProduct c INNER JOIN c.ownerProduct op WHERE c.campaignId = ?1 " +
            "AND c.deleted = FALSE AND op.deleted = FALSE ")
    List<CampaignOwnerProduct> findCampaignProductsByCampaignId(Integer campaignId);

    @Transactional
    @Query("Select c FROM CampaignOwnerProduct c INNER JOIN c.ownerProduct op WHERE " +
            "c.ownerProductId = ?1 AND c.deleted = FALSE AND op.deleted = FALSE ")
    List<CampaignOwnerProduct> findCampaignProductsByOwnerProductId(Long ownerProductId);

    @Query("SELECT e FROM CampaignOwnerProduct e INNER JOIN e.ownerProduct op" +
            " WHERE op.deleted = FALSE ")
    @Transactional
    List<CampaignOwnerProduct> findProducts();

    @Transactional
    @Modifying
    @Query(value = ""
            + "UPDATE CampaignOwnerProduct e SET e.deleted = FALSE " +
            "WHERE e.ownerProductId = ?1 AND e.campaignId =?2 AND e.deleted = TRUE ")
    int undoDelete(Long ownerProductId, Integer campaignId);

    @Transactional
    @Query("SELECT e FROM CampaignOwnerProduct e WHERE e.ownerProductId =?1 AND e.campaignId =?2 " +
            "AND e.deleted = FALSE ")
    CampaignOwnerProduct findByCampaignIdAndOwnerProductId(Long ownerProductId, Integer campaignId);

    @Transactional
    @Query("UPDATE CampaignOwnerProduct e SET e.deleted = TRUE where e.id = ?1")
    @Modifying
    void delete(long id);
}
