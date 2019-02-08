package com.mapp.platform.productservice.service;

import com.mapp.platform.productservice.bean.ProductFamily;
import com.mapp.platform.productservice.repository.ProductFamilyRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductFamilyService {

    private final ProductFamilyRepository productFamilyRepository;

    public ProductFamilyService(ProductFamilyRepository productFamilyRepository) {
        this.productFamilyRepository = productFamilyRepository;
    }

    /**
     * @return list of ProductFamilies.
     */
    public List<ProductFamily> findFamilies() {
        List<ProductFamily> productFamilies;
        productFamilies = (List<ProductFamily>) productFamilyRepository.findAll();
        return productFamilies;
    }

    /**
     * @param familyCode which is given in the request body.
     * @return ProductFamily based on the familyCode.
     */
    public ProductFamily findFamilyByCode(Long familyCode) {
        return productFamilyRepository.findOne(familyCode);
    }
}
