package com.mapp.platform.productservice.dao.extractor;

import com.mapp.platform.productservice.bean.dto.OwnerServiceDto;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerServiceMapper implements RowMapper<OwnerServiceDto> {

    @Override
    public OwnerServiceDto mapRow(ResultSet rs, int arg1) throws SQLException {
        OwnerServiceDto serviceDTO = new OwnerServiceDto();
        serviceDTO.setId(rs.getLong("id"));
        serviceDTO.setOwnerId(rs.getString("owner_id"));
        serviceDTO.setServiceName(rs.getString("service_name"));
        serviceDTO.setIndustryGroupCode(rs.getString("industry_group_code"));
        serviceDTO.setIndustryGroupName(rs.getString("industry_group_name"));
        serviceDTO.setSubSectorCode(rs.getString("sub_sector_code"));
        serviceDTO.setSubSectorName(rs.getString("sub_sector_name"));
        return serviceDTO;
    }
}
