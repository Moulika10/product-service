package com.mapp.platform.productservice.service;

import com.mapp.platform.productservice.dao.OwnerProductDaoImpl;
import com.mapp.platform.productservice.bean.OwnerProduct;
import com.mapp.platform.productservice.bean.dto.OwnerProductDto;
import com.mapp.platform.productservice.bean.ProductBrick;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.repository.OwnerProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OwnerProductService {

    private OwnerProductRepository ownerProductRepository;
    private ProductBrickService productBrickService;
    private OwnerProductDaoImpl ownerProductDao;

    public OwnerProductService(OwnerProductRepository ownerProductRepository, ProductBrickService productBrickService, OwnerProductDaoImpl ownerProductDao) {
        this.ownerProductRepository = ownerProductRepository;
        this.productBrickService = productBrickService;
        this.ownerProductDao = ownerProductDao;
    }

    /**
     * @param list      of OwnerProduct values.
     * @param brickCode which is given in the request body.
     * @return true if brickCode is present in the list of values.
     */
    boolean containsBrickCode(final List<OwnerProduct> list, final long brickCode) {
        return list.stream().anyMatch(o -> o.getBrickCode().equals(brickCode));
    }

    /**
     * @param list    of Owner Product values.
     * @param ownerId which is given in the request body.
     * @return true if ownerId is present in the list of values.
     */
    boolean containsProductOwnerId(final List<OwnerProduct> list, final String ownerId) {
        return list.stream().anyMatch(o -> o.getOwnerId().equals(ownerId));
    }

    /**
     * @param ownerProducts list of Owner Product objects
     * @param brickCode     which is given in the request body.
     * @param ownerId       which is given in the request body.
     * @param productName   which is given in the request body.
     * @return OwnerProduct after finding and undoing the deleted product in the product_Dto list.
     */
    OwnerProduct findAndUpdateProductDeleted(List<OwnerProduct> ownerProducts, Long brickCode, String ownerId, String productName) {
        int id;
        OwnerProduct product = null;
        if (containsBrickCode(ownerProducts, brickCode) &&
                containsProductOwnerId(ownerProducts, ownerId)) {
            id = ownerProductRepository.undoDelete(brickCode, ownerId, productName);
            if (id == 1) {
                product = ownerProductRepository.findByOwnerIdAndBrickCode(brickCode, ownerId, productName);
            }
        }
        return product;
    }

    /**
     * @return list of OwnerProduct where deleted = FALSE
     */
    List<OwnerProduct> findAllProducts() {
        return ownerProductRepository.findAll();
    }

    /**
     * @param id is the primary key.
     * @return OwnerProduct after finding the record based on the id.
     */
    public OwnerProduct findOwnerProductById(long id) {
        OwnerProduct ownerProduct;
        ownerProduct = ownerProductRepository.findOne(id);
        return ownerProduct;
    }

    /**
     * @return list of OwnerService where deleted = TRUE and deleted = FALSE.
     */
    List<OwnerProduct> findForDeletedProducts() {
        return ownerProductRepository.findProducts();
    }

    /**
     * @param productDto is OwnerProduct data from the request body.
     * @return OwnerProduct after saving the productDto in the database.
     */
    public OwnerProduct insert(OwnerProduct productDto) {
        return ownerProductRepository.save(productDto);
    }

    /**
     * @param id is the primary key.
     */
    public void delete(long id) {
        ownerProductRepository.delete(id);
    }

    /**
     * @param ownerId which is given in the request body
     * @return list of owner products where deleted = FALSE
     */
    public List<OwnerProductDto> findOwnerProductsByOwnerId(String ownerId) {
        List<OwnerProductDto> ownerProducts;
        ownerProducts = ownerProductDao.findByOwnerId(ownerId);
        return ownerProducts;
    }

    /**
     * @param productDto which is given in the request body.
     * @return product after inserting productDto into database.
     * @throws ProductServiceException throws exception when Brick Code does not exists in ProductBrick Table
     */
    public OwnerProduct create(OwnerProduct productDto) throws ProductServiceException {
        OwnerProduct product;
        List<OwnerProduct> ownerProducts = findForDeletedProducts();
        Long brickCode = productDto.getBrickCode();
        String ownerId = productDto.getOwnerId();
        String productName = productDto.getProductName();
        ProductBrick productBrick = productBrickService.findBrickByCode(brickCode);
        if (productBrick != null) {
            product = findAndUpdateProductDeleted(ownerProducts, brickCode, ownerId, productName);
            if (product == null) {
                product = insert(productDto);
            }
        } else {
            throw new ProductServiceException("Brick Code does not exists");
        }
        return product;
    }

    public OwnerProductDto findByID(long id) {
        return ownerProductDao.findOne(id);
    }

}
