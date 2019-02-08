package com.mapp.platform.productservice.service;

import com.mapp.platform.productservice.bean.ProductSegment;
import com.mapp.platform.productservice.repository.ProductSegmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductSegmentService {

    private final ProductSegmentRepository productSegmentRepository;

    public ProductSegmentService(ProductSegmentRepository productSegmentRepository) {
        this.productSegmentRepository = productSegmentRepository;
    }

    /**
     * @return list of ProductSegments.
     */
    public List<ProductSegment> findSegments() {
        List<ProductSegment> productSegments;
        productSegments = (List<ProductSegment>) productSegmentRepository.findAll();
        return productSegments;
    }

    /**
     * @param segmentCode which is given in the request body.
     * @return ProductSegment based on the segmentCode.
     */
    public ProductSegment findSegmentByCode(Long segmentCode) {
        return productSegmentRepository.findOne(segmentCode);
    }
}
