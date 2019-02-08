package com.mapp.platform.productservice.repository;

import com.mapp.platform.productservice.bean.CampaignOwnerService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CampaignOwnerServiceRepository extends CrudRepository<CampaignOwnerService, Long> {

    @Transactional
    @Query("SELECT c FROM CampaignOwnerService c INNER JOIN c.ownerService os WHERE " +
            "c.id = ?1 AND c.deleted = FALSE AND os.deleted = FALSE ")
    CampaignOwnerService findOne(long id);

    @Transactional
    @Query("Select c FROM CampaignOwnerService c INNER JOIN c.ownerService os WHERE " +
            "c.campaignId = ?1 AND c.deleted = FALSE AND os.deleted = FALSE ")
    List<CampaignOwnerService> findCampaignServicesByCampaignId(Integer campaignId);

    @Transactional
    @Query("Select c FROM CampaignOwnerService c INNER JOIN c.ownerService os WHERE " +
            "c.ownerServiceId = ?1 AND c.deleted = FALSE AND os.deleted = FALSE ")
    List<CampaignOwnerService> findCampaignServicesByOwnerServiceId(Long ownerServiceId);

    @Transactional
    @Query("UPDATE CampaignOwnerService e SET e.deleted = TRUE where e.id = ?1 ")
    @Modifying
    void delete(long id);

    @Query("SELECT e FROM CampaignOwnerService e INNER JOIN e.ownerService os WHERE " +
            "os.deleted = FALSE")
    @Transactional
    List<CampaignOwnerService> findServices();

    @Transactional
    @Modifying
    @Query(value = ""
            + "UPDATE CampaignOwnerService e SET e.deleted = FALSE " +
            "WHERE e.ownerServiceId = ?1 AND e.campaignId =?2 AND e.deleted = TRUE ")
    int undoDelete(Long ownerServiceId, Integer campaignId);

    @Transactional
    @Query("SELECT e FROM CampaignOwnerService e WHERE e.ownerServiceId =?1 AND e.campaignId =?2 " +
            "AND e.deleted = FALSE ")
    CampaignOwnerService findByCampaignIdAndOwnerServiceId(Long ownerServiceId, Integer campaignId);
}
