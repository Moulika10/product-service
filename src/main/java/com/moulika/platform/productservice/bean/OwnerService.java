package com.mapp.platform.productservice.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@SuppressWarnings("unused")
@Entity
@Table(name = "owner_service", schema = "product_service")
public class OwnerService {
    private long id;
    private String ownerId;
    private String serviceName;
    private String industryGroupCode;
    private boolean deleted;
    private Collection<CampaignOwnerService> campaignOwnerServices;
    private ServiceIndustryGroup serviceIndustryGroup;

    public OwnerService() {
        super();
    }

    public OwnerService( String ownerId, String serviceName, String industryGroupCode, boolean deleted) {
        super();
        this.ownerId = ownerId;
        this.serviceName = serviceName;
        this.industryGroupCode = industryGroupCode;
        this.deleted = deleted;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    @ApiModelProperty(hidden = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "owner_id")
    @ApiModelProperty(position = 1, required = true)
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    @Column(name = "service_name")
    @NotNull(message = "Service Name Cannot be null")
    @Size(max = 500)
    @ApiModelProperty(position = 2, required = true)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Basic
    @Column(name = "industry_group_code")
    @NotNull(message = "IndustryGroupCode cannot be Null")
    @Pattern(regexp = "^[0-9]*$", message = "{invalid.industryGroupCode}")
    @ApiModelProperty(position = 3, required = true)
    public String getIndustryGroupCode() {
        return industryGroupCode;
    }

    public void setIndustryGroupCode(String industryGroupCode) {
        this.industryGroupCode = industryGroupCode;
    }

    @Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
    @JsonIgnore
    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerService that = (OwnerService) o;
        return id == (that.id) &&
                Objects.equals(ownerId, that.ownerId) &&
                Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(industryGroupCode, that.industryGroupCode) &&
                Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ownerId, serviceName, industryGroupCode, deleted);
    }

    @Override
    public String toString() {
        return "OwnerService{" +
                "id=" + id +
                ", ownerId='" + ownerId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", industryGroupCode='" + industryGroupCode + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    @JsonBackReference
    @OneToMany(mappedBy = "ownerService", fetch = FetchType.EAGER)
    @ApiModelProperty(hidden = true)
    public Collection<CampaignOwnerService> getCampaignOwnerServices() {
        return campaignOwnerServices;
    }

    public void setCampaignOwnerServices(Collection<CampaignOwnerService> campaignOwnerServices) {
        this.campaignOwnerServices = campaignOwnerServices;
    }


    @JsonIgnore
    @ManyToOne(optional=false)
    @JoinColumn(name = "industry_group_code", referencedColumnName = "industry_group_code",insertable=false, updatable=false)
    public ServiceIndustryGroup getServiceIndustryGroup() {
        return serviceIndustryGroup;
    }

    public void setServiceIndustryGroup(ServiceIndustryGroup serviceIndustryGroup) {
        this.serviceIndustryGroup = serviceIndustryGroup;
    }
}
