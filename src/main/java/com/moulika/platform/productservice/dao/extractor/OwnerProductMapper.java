package com.mapp.platform.productservice.dao.extractor;

import com.mapp.platform.productservice.bean.dto.OwnerProductDto;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerProductMapper implements RowMapper<OwnerProductDto> {

    @Override
    public OwnerProductDto mapRow(ResultSet rs, int arg1) throws SQLException {
        OwnerProductDto productDto = new OwnerProductDto();
        productDto.setId(rs.getLong("id"));
        productDto.setOwnerId(rs.getString("owner_id"));
        productDto.setProductName(rs.getString("product_name"));
        productDto.setBrickCode(rs.getLong("brick_code"));
        productDto.setBrickDescription(rs.getString("brick_description"));
        productDto.setClassCode(rs.getLong("class_code"));
        productDto.setClassDescription(rs.getString("class_description"));
        return productDto;
    }
}
