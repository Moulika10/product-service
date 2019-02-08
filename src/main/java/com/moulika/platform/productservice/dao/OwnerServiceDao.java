package com.mapp.platform.productservice.dao;

import com.mapp.platform.productservice.bean.dto.OwnerServiceDto;

import java.util.List;

public interface OwnerServiceDao {

    OwnerServiceDto findOne(Long id);
    List<OwnerServiceDto> findByOwnerId(String ownerId);

}
