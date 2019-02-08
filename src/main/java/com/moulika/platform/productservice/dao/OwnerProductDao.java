package com.mapp.platform.productservice.dao;

import com.mapp.platform.productservice.bean.dto.OwnerProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OwnerProductDao {

    OwnerProductDto findOne(Long id);
    List<OwnerProductDto> findByOwnerId(String ownerId);
}
