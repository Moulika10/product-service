package com.moulika.platform.productservice.bean.dto;

import io.swagger.annotations.ApiModelProperty;

public class OwnerServiceDto {

    private long id;
    private String ownerId;
    private String serviceName;
    private String industryGroupCode;
    private String industryGroupName;
    private String subSectorCode;
    private String subSectorName;

    public OwnerServiceDto(){ super(); }

    public OwnerServiceDto(long id, String ownerId, String serviceName, String industryGroupCode, String industryGroupName, String subSectorCode, String subSectorName) {
        this.id = id;
        this.ownerId = ownerId;
        this.serviceName = serviceName;
        this.industryGroupCode = industryGroupCode;
        this.industryGroupName = industryGroupName;
        this.subSectorCode = subSectorCode;
        this.subSectorName = subSectorName;
    }

    @ApiModelProperty(position = 1, name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ApiModelProperty(position = 2, name = "ownerId")
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @ApiModelProperty(position = 3, name = "serviceName")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @ApiModelProperty(position = 4, name = "industryGroupCode")
    public String getIndustryGroupCode() {
        return industryGroupCode;
    }

    public void setIndustryGroupCode(String industryGroupCode) {
        this.industryGroupCode = industryGroupCode;
    }

    @ApiModelProperty(position = 5, name = "industryGroupName")
    public String getIndustryGroupName() {
        return industryGroupName;
    }

    public void setIndustryGroupName(String industryGroupName) {
        this.industryGroupName = industryGroupName;
    }

    @ApiModelProperty(position = 6, name = "subSectorCode")
    public String getSubSectorCode() {
        return subSectorCode;
    }

    public void setSubSectorCode(String subSectorCode) {
        this.subSectorCode = subSectorCode;
    }

    @ApiModelProperty(position = 7, name = "subSectorName")
    public String getSubSectorName() {
        return subSectorName;
    }

    public void setSubSectorName(String subSectorName) {
        this.subSectorName = subSectorName;
    }
}
