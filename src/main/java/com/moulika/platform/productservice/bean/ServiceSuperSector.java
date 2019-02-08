package com.mapp.platform.productservice.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "service_super_sector", schema = "product_service")
public class ServiceSuperSector {
    private String superSectorCode;
    private String superSectorName;
    private Set<ServiceSubSector> subSectors;

    public ServiceSuperSector() {
        super();
    }

    public ServiceSuperSector(String superSectorCode, String superSectorName, Set<ServiceSubSector> subSectors) {
        super();
        this.superSectorCode = superSectorCode;
        this.superSectorName = superSectorName;
        this.subSectors = subSectors;
    }

    @Id
    @Column(name = "super_sector_code")
    public String getSuperSectorCode() {
        return superSectorCode;
    }

    public void setSuperSectorCode(String superSectorCode) {
        this.superSectorCode = superSectorCode;
    }

    @Basic
    @Column(name = "super_sector_name")
    public String getSuperSectorName() {
        return superSectorName.trim();
    }

    public void setSuperSectorName(String superSectorName) {
        this.superSectorName = superSectorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceSuperSector that = (ServiceSuperSector) o;
        return Objects.equals(superSectorCode, that.superSectorCode) &&
                Objects.equals(superSectorName, that.superSectorName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(superSectorCode, superSectorName);
    }

    @JsonBackReference
    @OneToMany(mappedBy = "superSector", fetch = FetchType.EAGER)
    @ApiModelProperty(hidden = true)
    public Set<ServiceSubSector> getSubSectors() {
        return subSectors;
    }

    public void setSubSectors(Set<ServiceSubSector> subSectors) {
        this.subSectors = subSectors;
    }
}
