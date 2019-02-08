package com.mapp.platform.productservice.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
@Entity
@Table(name = "service_industry_group", schema = "product_service")
public class ServiceIndustryGroup {
    private String industryGroupCode;
    private String industryGroupName;
    private ServiceSubSector subSector;
    private Collection<OwnerService> ownerServices;

    public ServiceIndustryGroup() {
        super();
    }

    public ServiceIndustryGroup(ServiceSubSector subSector, String industryGroupCode, String industryGroupName) {
        super();
        this.subSector = subSector;
        this.industryGroupCode = industryGroupCode;
        this.industryGroupName = industryGroupName;
    }

    @Id
    @Column(name = "industry_group_code")
    public String getIndustryGroupCode() {
        return industryGroupCode;
    }

    public void setIndustryGroupCode(String industryGroupCode) {
        this.industryGroupCode = industryGroupCode;
    }

    @Basic
    @Column(name = "industry_group_name")
    public String getIndustryGroupName() {
        return industryGroupName.trim();
    }

    public void setIndustryGroupName(String industryGroupName) {
        this.industryGroupName = industryGroupName.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceIndustryGroup that = (ServiceIndustryGroup) o;
        return Objects.equals(industryGroupCode, that.industryGroupCode) &&
                Objects.equals(industryGroupName, that.industryGroupName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(industryGroupCode, industryGroupName);
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sub_sector_code", referencedColumnName = "sub_sector_code", nullable = false)
    @ApiModelProperty(hidden = true)
    public ServiceSubSector getSubSector() {
        return subSector;
    }

    public void setSubSector(ServiceSubSector subSector) {
        this.subSector = subSector;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "serviceIndustryGroup")
    @ApiModelProperty(hidden = true)
    public Collection<OwnerService> getOwnerServices() {
        return ownerServices;
    }

    public void setOwnerServices(Collection<OwnerService> ownerServices) {
        this.ownerServices = ownerServices;
    }
}
