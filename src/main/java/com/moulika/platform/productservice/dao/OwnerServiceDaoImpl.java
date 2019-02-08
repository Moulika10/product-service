package com.moulika.platform.productservice.dao;

import com.moulika.platform.productservice.bean.dto.OwnerServiceDto;
import com.moulika.platform.productservice.dao.extractor.OwnerServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerServiceDaoImpl implements OwnerServiceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerProductDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    public OwnerServiceDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OwnerServiceDto findOne(Long id) {
        String query = "select os.id, os.owner_id, os.service_name, os.industry_group_code, ig.industry_group_name, sc.sub_sector_code, sc.sub_sector_name \n" +
                "FROM product_service.service_industry_group ig \n" +
                "INNER JOIN product_service.owner_service os  ON os.industry_group_code = ig.industry_group_code \n" +
                "INNER JOIN product_service.service_sub_sector sc ON ig.sub_sector_code = sc.sub_sector_code \n" +
                "Where os.deleted = FALSE AND os.id = ? ";
        OwnerServiceDto serviceDTO = null;
        try {
            serviceDTO = jdbcTemplate.queryForObject(query, new Object[]{id}, new OwnerServiceMapper());
        } catch (Exception e) {
            LOGGER.error("Error: ", e);
        }
        return serviceDTO;
    }

    @Override
    public List<OwnerServiceDto> findByOwnerId(String ownerId) {
        String query = "select os.id, os.owner_id, os.service_name, os.industry_group_code, ig.industry_group_name, sc.sub_sector_code, sc.sub_sector_name \n" +
                "FROM product_service.service_industry_group ig \n" +
                "INNER JOIN product_service.owner_service os  ON os.industry_group_code = ig.industry_group_code \n" +
                "INNER JOIN product_service.service_sub_sector sc ON ig.sub_sector_code = sc.sub_sector_code \n" +
                "Where os .deleted = FALSE AND os.owner_id = '" + ownerId + "' ";
        return jdbcTemplate.query(query, new OwnerServiceMapper());
    }
}
