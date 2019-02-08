package com.mapp.platform.productservice.service;

import com.mapp.platform.productservice.bean.CampaignOwnerService;
import com.mapp.platform.productservice.bean.OwnerService;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.repository.CampaignOwnerServiceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("CanBeFinal")
@Service
public class CampaignOwnerServiceService {

    private CampaignOwnerServiceRepository campaignOwnerServiceRepository;
    private OwnerServiceService ownerServiceService;

    public CampaignOwnerServiceService(CampaignOwnerServiceRepository campaignOwnerServiceRepository, OwnerServiceService ownerServiceService) {
        this.campaignOwnerServiceRepository = campaignOwnerServiceRepository;
        this.ownerServiceService = ownerServiceService;
    }

    /**
     * @param campaignId used for finding list of CampaignOwnerServices.
     * @return list of Campaign Owner Service.
     */
    public List<CampaignOwnerService> findCampaignOwnerServicesByCampaignId(Integer campaignId) {
        List<CampaignOwnerService> campaignOwnerServices;
        campaignOwnerServices = campaignOwnerServiceRepository.findCampaignServicesByCampaignId(campaignId);
        return campaignOwnerServices;
    }

    /**
     * @param ownerServiceId used for finding list of CampaignOwnerServices.
     * @return list of CampaignOwnerServices.
     */
    public List<CampaignOwnerService> findCampaignOwnerServicesByOwnerServiceId(Long ownerServiceId) {
        List<CampaignOwnerService> campaignOwnerServices;
        campaignOwnerServices = campaignOwnerServiceRepository.findCampaignServicesByOwnerServiceId(ownerServiceId);
        return campaignOwnerServices;
    }

    /**
     * @param id is the primary key used for finding campaign owner service.
     * @return CampaignOwnerService
     */
    public CampaignOwnerService findCampaignOwnerServiceById(long id) {
        CampaignOwnerService campaignOwnerService;
        campaignOwnerService = campaignOwnerServiceRepository.findOne(id);
        return campaignOwnerService;
    }

    /**
     * @param campaignOwnerService which is given in the request body.
     * @return service after inserting campaignOwnerService into database.
     * @throws ProductServiceException throws exception when Owner Service does not exists in OwnerService Table.
     */
    public CampaignOwnerService create(CampaignOwnerService campaignOwnerService) throws ProductServiceException {
        CampaignOwnerService service;
        List<CampaignOwnerService> campaignOwnerServices = findForDeletedServices();
        Integer campaignId = campaignOwnerService.getCampaignId();
        Long ownerServiceId = campaignOwnerService.getOwnerServiceId();
        OwnerService ownerService = ownerServiceService.findOwnerServiceById(campaignOwnerService.getOwnerServiceId());
        if (ownerService != null && !ownerService.getDeleted()) {
            service = findAndUpdateServiceDeleted(campaignOwnerServices, ownerServiceId, campaignId);
            if (service == null) {
                service = insert(campaignOwnerService);
            }
        } else {
            throw new ProductServiceException("Owner Service does not exists");
        }
        return service;
    }

    /**
     * @param campaignOwnerService is CampaignOwnerService data from the request body.
     * @return CampaignOwnerService after saving the campaignOwnerService in the database.
     */
    public CampaignOwnerService insert(CampaignOwnerService campaignOwnerService) {
        return campaignOwnerServiceRepository.save(campaignOwnerService);
    }

    /**
     * @return list of CampaignOwnerService where deleted = TRUE and deleted = FALSE.
     */
    List<CampaignOwnerService> findForDeletedServices() {
        List<CampaignOwnerService> campaignOwnerServices;
        campaignOwnerServices = campaignOwnerServiceRepository.findServices();
        return campaignOwnerServices;
    }

    /**
     * @param campaignOwnerServices list of CampaignOwnerService objects.
     * @param ownerServiceId        which is given in the request body.
     * @param campaignId            which is given in the request body.
     * @return CampaignOwnerService after finding and undoing the deleted service in the campaignOwnerServices list.
     */
    CampaignOwnerService findAndUpdateServiceDeleted(List<CampaignOwnerService> campaignOwnerServices, Long ownerServiceId, Integer campaignId) {
        int flag;
        CampaignOwnerService campaignOwnerService = null;
        if (containsOwnerServiceId(campaignOwnerServices, ownerServiceId) &&
                containsServiceCampaignId(campaignOwnerServices, campaignId)) {
            flag = campaignOwnerServiceRepository.undoDelete(ownerServiceId, campaignId);
            if (flag == 1) {
                campaignOwnerService = campaignOwnerServiceRepository.findByCampaignIdAndOwnerServiceId(ownerServiceId, campaignId);
            }
        }
        return campaignOwnerService;
    }

    /**
     * @param list           of CampaignOwnerService values.
     * @param ownerServiceId which is given in the request body.
     * @return true if ownerServiceId is present in the list of values.
     */
    boolean containsOwnerServiceId(final List<CampaignOwnerService> list, final long ownerServiceId) {
        return list.stream().anyMatch(o -> Objects.equals(o.getOwnerServiceId(), ownerServiceId));
    }

    /**
     * @param list       of CampaignOwnerService values.
     * @param campaignId which is given in the request body.
     * @return true if campaignId is present in the list of values.
     */
    boolean containsServiceCampaignId(final List<CampaignOwnerService> list, final Integer campaignId) {
        return list.stream().anyMatch(o -> Objects.equals(o.getCampaignId(), campaignId));
    }

    /**
     * @param id is the primary key.
     */
    public void delete(long id) {
        campaignOwnerServiceRepository.delete(id);
    }
}
