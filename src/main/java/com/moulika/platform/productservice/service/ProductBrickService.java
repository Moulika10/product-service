package com.moulika.platform.productservice.service;

import com.moulika.platform.productservice.bean.ProductBrick;
import com.moulika.platform.productservice.repository.ProductBrickRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductBrickService {

    private final ProductBrickRepository productBrickRepository;

    public ProductBrickService(ProductBrickRepository productBrickRepository) {
        this.productBrickRepository = productBrickRepository;
    }

    /**
     * @return list of ProductBricks.
     */
    public List<ProductBrick> findBricks() {
        List<ProductBrick> productBricks;
        productBricks = (List<ProductBrick>) productBrickRepository.findAll();
        return productBricks;
    }

    /**
     * @param brickCode which is given in the request body.
     * @return ProductBrick based on the brickCode.
     */
    public ProductBrick findBrickByCode(Long brickCode) {
        return productBrickRepository.findOne(brickCode);
    }
}
