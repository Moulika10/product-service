package com.moulika.platform.productservice.service;

import com.moulika.platform.productservice.bean.ServiceSuperSector;
import com.moulika.platform.productservice.repository.ServiceSuperSectorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServiceSuperSectorService {
    private final ServiceSuperSectorRepository serviceSuperSectorRepository;

    public ServiceSuperSectorService(ServiceSuperSectorRepository serviceSuperSectorRepository) {
        this.serviceSuperSectorRepository = serviceSuperSectorRepository;
    }

    /**
     * @return list of ServiceSuperSectors.
     */
    public List<ServiceSuperSector> findSuperSectors() {
        List<ServiceSuperSector> serviceSuperSectors;
        serviceSuperSectors = (List<ServiceSuperSector>) serviceSuperSectorRepository.findAll();
        return serviceSuperSectors;
    }

    /**
     * @param superSectorCode which is given in the request body.
     * @return ServiceSuperSector based on the superSectorCode.
     */
    public ServiceSuperSector findSuperSectorByCode(String superSectorCode) {
        return serviceSuperSectorRepository.findBySuperSectorCode(superSectorCode);
    }
}
