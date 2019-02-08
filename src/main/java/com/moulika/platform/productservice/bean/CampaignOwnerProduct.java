package com.moulika.platform.productservice.bean;

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
@Table(name = "campaign_owner_product", schema = "product_service")

public class CampaignOwnerProduct {
    private long id;
    private int campaignId;
    private long ownerProductId;
    private boolean deleted;
    private OwnerProduct ownerProduct;

    public CampaignOwnerProduct() {
        super();
    }

    public CampaignOwnerProduct(OwnerProduct ownerProduct, int campaignId, long ownerProductId, boolean deleted) {
        super();
        this.ownerProduct = ownerProduct;
        this.campaignId = campaignId;
        this.ownerProductId = ownerProductId;
        this.deleted = deleted;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(position = 1, required = true, notes = "used to display generated Id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "campaign_id")
    @NotNull(message = "CampaignId of CampaignOwnerProduct cannot be Null")
    @ApiModelProperty(position = 2, required = true, notes = "used to display Campaign Id of CampaignOwnerProduct")
    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    @Basic
    @Column(name = "owner_product_id")
    @NotNull(message = "ProductId cannot be Null")
    @ApiModelProperty(position = 3, required = true, notes = "used to display OwnerProductId")
    public long getOwnerProductId() {
        return ownerProductId;
    }

    public void setOwnerProductId(long ownerProductId) {
        this.ownerProductId = ownerProductId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignOwnerProduct that = (CampaignOwnerProduct) o;
        return id == that.id &&
                campaignId == that.campaignId &&
                ownerProductId == that.ownerProductId &&
                deleted == that.deleted;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, campaignId, ownerProductId, deleted);
    }

    @Override
    public String toString() {
        return "CampaignOwnerProduct{" +
                "id=" + id +
                ", campaignId=" + campaignId +
                ", ownerProductId=" + ownerProductId +
                ", deleted=" + deleted +
                ", ownerProduct=" + ownerProduct +
                '}';
    }

    @JsonIgnoreProperties("id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_product_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ApiModelProperty(position = 4, name = "ownerProduct", allowableValues = "brickCode, ownerId, productName")
    public OwnerProduct getOwnerProduct() {
        return ownerProduct;
    }

    public void setOwnerProduct(OwnerProduct ownerProduct) {
        this.ownerProduct = ownerProduct;
    }
}
