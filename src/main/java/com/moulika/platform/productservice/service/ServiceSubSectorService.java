package com.moulika.platform.productservice.service;

import com.moulika.platform.productservice.bean.ServiceSubSector;
import com.moulika.platform.productservice.repository.ServiceSubSectorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServiceSubSectorService {
    private final ServiceSubSectorRepository serviceSubSectorRepository;

    public ServiceSubSectorService(ServiceSubSectorRepository serviceSubSectorRepository) {
        this.serviceSubSectorRepository = serviceSubSectorRepository;
    }

    /**
     * @return list of ServiceSubSectors.
     */
    public List<ServiceSubSector> findSubSectors() {
        List<ServiceSubSector> serviceSubSectors;
        serviceSubSectors = (List<ServiceSubSector>) serviceSubSectorRepository.findAll();
        return serviceSubSectors;
    }

    /**
     * @param subSectorCode which is given in the request body.
     * @return ServiceSubSector based on the subSectorCode.
     */
    public ServiceSubSector findSubSectorByCode(String subSectorCode) {
        return serviceSubSectorRepository.findBySubSectorCode(subSectorCode);
    }
}
