package com.mapp.platform.productservice.dao;

import com.mapp.platform.productservice.bean.dto.OwnerProductDto;
import com.mapp.platform.productservice.dao.extractor.OwnerProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerProductDaoImpl implements OwnerProductDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    public OwnerProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OwnerProductDto findOne(Long id) {
        String query = "select op.id, op.owner_id, op.product_name, op.brick_code, pb.brick_description, pb.class_code, pc.class_description \n" +
                "FROM product_service.product_brick pb \n" +
                "INNER JOIN product_service.owner_product op  ON op.brick_code = pb.brick_code \n" +
                "INNER JOIN product_service.product_class pc ON pc.class_code = pb.class_code \n" +
                "Where op .deleted = FALSE AND op.id = ? ";

        OwnerProductDto productDto = null;
        try {
            productDto = jdbcTemplate.queryForObject(query, new Object[]{id}, new OwnerProductMapper());
        } catch (Exception e) {
            LOGGER.error("Error: ", e);
        }
        return productDto;
    }

    @Override
    public List<OwnerProductDto> findByOwnerId(String ownerId) {
        String query = "select op.id, op.owner_id, op.product_name, op.brick_code, pb.brick_description, pb.class_code, pc.class_description \n" +
                "FROM product_service.product_brick pb \n" +
                "INNER JOIN product_service.owner_product op  ON op.brick_code = pb.brick_code \n" +
                "INNER JOIN product_service.product_class pc ON pc.class_code = pb.class_code \n" +
                "Where op .deleted = FALSE AND op.owner_id = '" + ownerId + "' ";
        return jdbcTemplate.query(query, new OwnerProductMapper());
    }
}




