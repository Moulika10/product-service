package com.mapp.platform.productservice.service;

import com.mapp.platform.productservice.bean.dto.OwnerServiceDto;
import com.mapp.platform.productservice.exception.ProductServiceException;
import com.mapp.platform.productservice.dao.OwnerServiceDaoImpl;
import com.mapp.platform.productservice.bean.OwnerService;
import com.mapp.platform.productservice.bean.ServiceIndustryGroup;
import com.mapp.platform.productservice.repository.OwnerServiceRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * In OwnerServiceService class the first "Service" means the service that NAICS company sells, and the second "Service" means a type of java class that represents the service layer of a software application.
 */
@SuppressWarnings("CanBeFinal")
@Service
public class OwnerServiceService {

    private OwnerServiceRepository ownerServiceRepository;
    private ServiceIndustryGroupService industryGroupService;
    private OwnerServiceDaoImpl ownerServiceDao;

    public OwnerServiceService(OwnerServiceRepository ownerServiceRepository, ServiceIndustryGroupService industryGroupService, OwnerServiceDaoImpl ownerServiceDao) {
        this.ownerServiceRepository = ownerServiceRepository;
        this.industryGroupService = industryGroupService;
        this.ownerServiceDao = ownerServiceDao;
    }

    /**
     * @param list              of Owner Service values.
     * @param industryGroupCode which is given in the request body.
     * @return true if industryGroupCode is present in the list of values.
     */
    boolean containsIndustryGroupCode(final List<OwnerService> list, final String industryGroupCode) {
        return list.stream().anyMatch(o -> o.getIndustryGroupCode().equals(industryGroupCode));
    }

    /**
     * @param list    of Owner Service values.
     * @param ownerId which is given in the request body.
     * @return true if ownerId is present in the list of values.
     */
    boolean containsServiceOwnerId(final List<OwnerService> list, final String ownerId) {
        return list.stream().anyMatch(o -> o.getOwnerId().equals(ownerId));
    }

    /**
     * @param ownerServices     list of Owner Service objects
     * @param industryGroupCode which is given in the request body.
     * @param ownerId           which is given in the request body.
     * @param serviceName       which is given in the request body.
     * @return OwnerService after finding and undoing the deleted service in the service_Dto list.
     */
    OwnerService findAndUpdateServiceDeleted(List<OwnerService> ownerServices, String industryGroupCode, String ownerId, String serviceName) {
        int id;
        OwnerService service = null;
        if (containsIndustryGroupCode(ownerServices, industryGroupCode) &&
                containsServiceOwnerId(ownerServices, ownerId)) {
            id = ownerServiceRepository.undoDelete(industryGroupCode, ownerId, serviceName);

            if (id == 1) {
                service = ownerServiceRepository.findByOwnerIdAndIndustryGroupCode(industryGroupCode, ownerId, serviceName);
            }
        }
        return service;
    }

    /**
     * @return list of services where deleted = FALSE.
     */
    List<OwnerService> findAllServices() {
        return ownerServiceRepository.findAll();
    }

    /**
     * @param id is the primary key.
     * @return service after finding the record based on the id.
     */
    public OwnerService findOwnerServiceById(long id) {
        return ownerServiceRepository.findOne(id);
    }

    /**
     * @return list of OwnerService where deleted = TRUE and deleted = FALSE.
     */
    List<OwnerService> findForDeletedServices() {
        return ownerServiceRepository.findServices();
    }

    /**
     * @param serviceDto is the OwnerService data from the request body.
     * @return OwnerService after saving the serviceDto in the database.
     */
    public OwnerService insert(OwnerService serviceDto) {
        return ownerServiceRepository.save(serviceDto);
    }

    /**
     * @param id is the primary key.
     */
    public void delete(Long id) {
        ownerServiceRepository.delete(id);
    }

    /**
     * @param ownerId which is given in the request body
     * @return list of owner products where deleted = FALSE
     */
    public List<OwnerServiceDto> findOwnerServicesByOwnerId(String ownerId) {
        return ownerServiceDao.findByOwnerId(ownerId);
    }

    /**
     * @param serviceDto which is given in the request body.
     * @return service after inserting serviceDto into database.
     * @throws ProductServiceException throws exception when Industry Group Code does not exists in ServiceIndustryGroup Table.
     */
    public OwnerService create(OwnerService serviceDto) throws ProductServiceException {
        OwnerService service;
        List<OwnerService> ownerServices = findForDeletedServices();
        String industryGroupCode = serviceDto.getIndustryGroupCode();
        String ownerId = serviceDto.getOwnerId();
        String serviceName = serviceDto.getServiceName();
        ServiceIndustryGroup serviceIndustryGroup = industryGroupService.findIndustryGroupByCode(industryGroupCode);
        if (serviceIndustryGroup != null) {
            service = findAndUpdateServiceDeleted(ownerServices, industryGroupCode, ownerId, serviceName);
            if (service == null) {
                service = insert(serviceDto);
            }
        } else {
            throw new ProductServiceException("Industry Group Code does not exists");
        }
        return service;
    }

    public OwnerServiceDto findByID(long id) {
        return ownerServiceDao.findOne(id);
    }
}
