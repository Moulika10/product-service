package com.moulika.platform.productservice.dao;

import com.moulika.platform.productservice.bean.dto.OwnerServiceDto;

import java.util.List;

public interface OwnerServiceDao {

    OwnerServiceDto findOne(Long id);
    List<OwnerServiceDto> findByOwnerId(String ownerId);

}
