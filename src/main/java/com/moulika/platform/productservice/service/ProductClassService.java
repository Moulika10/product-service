package com.mapp.platform.productservice.service;

import com.mapp.platform.productservice.bean.ProductClass;
import com.mapp.platform.productservice.repository.ProductClassRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductClassService {
    private final ProductClassRepository productClassRepository;

    public ProductClassService(ProductClassRepository productClassRepository) {
        this.productClassRepository = productClassRepository;
    }

    /**
     * @return list of ProductClasses.
     */
    public List<ProductClass> findClasses() {
        List<ProductClass> productClasses;
        productClasses = (List<ProductClass>) productClassRepository.findAll();
        return productClasses;
    }

    /**
     * @param classCode which is given in the request body.
     * @return ProductBrick based on the classCode.
     */
    public ProductClass findClassByCode(Long classCode) {
        return productClassRepository.findOne(classCode);
    }
}

