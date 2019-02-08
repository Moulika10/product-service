package com.mapp.platform.productservice.service;

import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.bean.CampaignOwnerProduct;
import com.mapp.platform.productservice.bean.OwnerProduct;
import com.mapp.platform.productservice.repository.CampaignOwnerProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("CanBeFinal")
@Service
public class CampaignOwnerProductService {

    private CampaignOwnerProductRepository campaignOwnerProductRepository;
    private OwnerProductService ownerProductService;

    public CampaignOwnerProductService(CampaignOwnerProductRepository campaignOwnerProductRepository, OwnerProductService ownerProductService) {
        this.campaignOwnerProductRepository = campaignOwnerProductRepository;
        this.ownerProductService = ownerProductService;
    }

    /**
     * @param id is the primary key used for finding campaign owner product.
     * @return CampaignOwnerProduct
     */
    public CampaignOwnerProduct findCampaignOwnerProductById(long id) {
        CampaignOwnerProduct campaignOwnerProduct;
        campaignOwnerProduct = campaignOwnerProductRepository.findOne(id);
        return campaignOwnerProduct;
    }

    /**
     * @param campaignId used for finding list of Campaign Products
     * @return list of Campaign Owner Products
     */
    public List<CampaignOwnerProduct> findCampaignProductsByCampaignId(Integer campaignId) {
        List<CampaignOwnerProduct> campaignOwnerProducts;
        campaignOwnerProducts = campaignOwnerProductRepository.findCampaignProductsByCampaignId(campaignId);
        return campaignOwnerProducts;
    }

    /**
     * @param ownerProductId used for finding list of Campaign Owner Products.
     * @return list of Campaign Owner Products
     */
    public List<CampaignOwnerProduct> findCampaignProductsByOwnerProductId(Long ownerProductId) {
        List<CampaignOwnerProduct> campaignOwnerProducts;
        campaignOwnerProducts = campaignOwnerProductRepository.findCampaignProductsByOwnerProductId(ownerProductId);
        return campaignOwnerProducts;
    }

    /**
     * @param campaignOwnerProduct which is given in the request body
     * @return product after inserting campaignOwnerProduct into database.
     * @throws ProductServiceException throws exception when Owner Product does not exists in OwnerProduct Table
     */
    public CampaignOwnerProduct create(CampaignOwnerProduct campaignOwnerProduct) throws ProductServiceException {
        CampaignOwnerProduct product;
        List<CampaignOwnerProduct> campaignOwnerProducts = findForDeletedProducts();
        Integer campaignId = campaignOwnerProduct.getCampaignId();
        Long ownerProductId = campaignOwnerProduct.getOwnerProductId();
        OwnerProduct ownerProduct = ownerProductService.findOwnerProductById(campaignOwnerProduct.getOwnerProductId());
        if (ownerProduct != null && !ownerProduct.getDeleted()) {
            product = findAndUpdateProductDeleted(campaignOwnerProducts, ownerProductId, campaignId);
            if (product == null) {
                product = insert(campaignOwnerProduct);
            }
        } else {
            throw new ProductServiceException("Owner Product does not exists");
        }
        return product;
    }

    /**
     * @param campaignOwnerProduct is CampaignOwnerProduct data from the request body.
     * @return CampaignOwnerProduct after saving the campaignOwnerProduct in the database.
     */
    public CampaignOwnerProduct insert(CampaignOwnerProduct campaignOwnerProduct) {
        return campaignOwnerProductRepository.save(campaignOwnerProduct);
    }

    /**
     * @return list of CampaignOwnerProduct where deleted = TRUE and deleted = FALSE.
     */
    List<CampaignOwnerProduct> findForDeletedProducts() {
        List<CampaignOwnerProduct> campaignOwnerProducts;
        campaignOwnerProducts = campaignOwnerProductRepository.findProducts();
        return campaignOwnerProducts;
    }

    /**
     * @param campaignOwnerProducts list of CampaignOwnerProduct objects
     * @param ownerProductId        which is given in the request body.
     * @param campaignId            which is given in the request body.
     * @return CampaignOwnerProduct after finding and undoing the deleted product in the campaignOwnerProducts list.
     */
    CampaignOwnerProduct findAndUpdateProductDeleted(List<CampaignOwnerProduct> campaignOwnerProducts, Long ownerProductId, Integer campaignId) {
        int flag;
        CampaignOwnerProduct campaignOwnerProduct = null;
        if (containsOwnerProductId(campaignOwnerProducts, ownerProductId) &&
                containsProductCampaignId(campaignOwnerProducts, campaignId)) {
            flag = campaignOwnerProductRepository.undoDelete(ownerProductId, campaignId);
            if (flag == 1) {
                campaignOwnerProduct = campaignOwnerProductRepository.findByCampaignIdAndOwnerProductId(ownerProductId, campaignId);
            }
        }
        return campaignOwnerProduct;
    }

    /**
     * @param list           of CampaignOwnerProduct values.
     * @param ownerProductId which is given in the request body.
     * @return true if ownerProductId is present in the list of values.
     */
    boolean containsOwnerProductId(final List<CampaignOwnerProduct> list, final long ownerProductId) {
        return list.stream().anyMatch(o -> Objects.equals(o.getOwnerProductId(), ownerProductId));
    }

    /**
     * @param list       of CampaignOwnerProduct values.
     * @param campaignId which is given in the request body.
     * @return true if campaignId is present in the list of values.
     */
    boolean containsProductCampaignId(final List<CampaignOwnerProduct> list, final Integer campaignId) {
        return list.stream().anyMatch(o -> Objects.equals(o.getCampaignId(), campaignId));
    }

    /**
     * @param id is the primary key.
     */
    public void delete(long id) {
        campaignOwnerProductRepository.delete(id);
    }
}
