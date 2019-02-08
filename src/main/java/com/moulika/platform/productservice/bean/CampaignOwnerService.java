package com.mapp.platform.productservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@SuppressWarnings("unused")
@Entity
@Table(name = "campaign_owner_service", schema = "product_service")
public class CampaignOwnerService {
    private long id;
    private int campaignId;
    private long ownerServiceId;
    private boolean deleted;
    private OwnerService ownerService;

    public CampaignOwnerService() { super(); }

    public CampaignOwnerService( int campaignId, long ownerServiceId, boolean deleted) {
        this.campaignId = campaignId;
        this.ownerServiceId = ownerServiceId;
        this.deleted = deleted;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(position = 1, required = true, notes = "used to display generated Id for CampaignOwnerService")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "campaign_id")
    @NotNull(message = "CampaignId of CampaignOwnerService cannot be Null")
    @ApiModelProperty(position = 2, required = true, notes = "used to display generated CampaignId for CampaignOwnerService")
    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    @Basic
    @Column(name = "owner_service_id")
    @NotNull(message = "ServiceId cannot be Null")
    @ApiModelProperty(position = 3, required = true, notes = "used to display OwnerServiceId")
    public long getOwnerServiceId() {
        return ownerServiceId;
    }

    public void setOwnerServiceId(long ownerServiceId) {
        this.ownerServiceId = ownerServiceId;
    }

    @Basic
    @Column(name = "deleted")
    @JsonIgnore
    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @ManyToOne(optional=false)
    @JoinColumn(name="owner_service_id",referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnoreProperties("id")
    @ApiModelProperty(position = 4, name = "ownerService", allowableValues = "brickCode, ownerId, productName")
    public OwnerService getOwnerService() {
        return ownerService;
    }

    public void setOwnerService(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public String toString() {
        return "CampaignOwnerService{" +
                "id=" + id +
                ", campaignId=" + campaignId +
                ", ownerServiceId=" + ownerServiceId +
                ", deleted=" + deleted +
                ", ownerService=" + ownerService +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CampaignOwnerService that = (CampaignOwnerService) o;
        return id == that.id &&
                campaignId == that.campaignId &&
                ownerServiceId == that.ownerServiceId &&
                deleted == that.deleted;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, campaignId, ownerServiceId, deleted);
    }
}
