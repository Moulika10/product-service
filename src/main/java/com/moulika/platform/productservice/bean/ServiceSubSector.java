package com.moulika.platform.productservice.bean;

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
import javax.persistence.FetchType;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "service_sub_sector", schema = "product_service")
public class ServiceSubSector {
    private String subSectorCode;
    private String subSectorName;
    private Set<ServiceIndustryGroup> industryGroups;
    private ServiceSuperSector superSector;

    public ServiceSubSector() {
        super();
    }

    public ServiceSubSector(String subSectorCode, String subSectorName, Set<ServiceIndustryGroup> industryGroups) {
        super();
        this.subSectorCode = subSectorCode;
        this.subSectorName = subSectorName;
        this.industryGroups = industryGroups;
    }

    @Id
    @Column(name = "sub_sector_code")
    public String getSubSectorCode() {
        return subSectorCode;
    }

    public void setSubSectorCode(String subSectorCode) {
        this.subSectorCode = subSectorCode;
    }

    @Basic
    @Column(name = "sub_sector_name")
    public String getSubSectorName() {
        return subSectorName.trim();
    }

    public void setSubSectorName(String subSectorName) {
        this.subSectorName = subSectorName.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceSubSector that = (ServiceSubSector) o;
        return Objects.equals(subSectorCode, that.subSectorCode) &&
                Objects.equals(subSectorName, that.subSectorName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(subSectorCode, subSectorName);
    }

    @JsonBackReference
    @OneToMany(mappedBy = "subSector",fetch = FetchType.EAGER)
    @ApiModelProperty(hidden = true)
    public Set<ServiceIndustryGroup> getIndustryGroups() {
        return industryGroups;
    }

    public void setIndustryGroups(Set<ServiceIndustryGroup> industryGroups) {
        this.industryGroups = industryGroups;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "super_sector_code", referencedColumnName = "super_sector_code")
    @ApiModelProperty(hidden = true)
    public ServiceSuperSector getSuperSector() {
        return superSector;
    }

    public void setSuperSector(ServiceSuperSector superSector) {
        this.superSector = superSector;
    }
}
