package com.moulika.platform.productservice.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
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
@Table(name = "owner_product", schema = "product_service")
public class OwnerProduct {
    private long id;
    private String ownerId;
    private String productName;
    private long brickCode;
    private boolean deleted;
    private Collection<CampaignOwnerProduct> campaignOwnerProducts;
    private ProductBrick productBrick;

    public OwnerProduct() {
        super();
    }

    public OwnerProduct(ProductBrick productBrick, String ownerId, String productName, long brickCode, boolean deleted) {
        super();
        this.productBrick = productBrick;
        this.ownerId = ownerId;
        this.productName = productName;
        this.brickCode = brickCode;
        this.deleted = deleted;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @Column(name = "product_name")
    @Size(max = 500)
    @NotNull(message = "Product Name cannot be empty")
    @ApiModelProperty(position = 2, required = true)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "brick_code")
    @Digits(integer = 10, fraction = 0)
    @NotNull(message = "Brick Code cannot be empty")
    @ApiModelProperty(position = 3, required = true)
    public Long getBrickCode() {
        return brickCode;
    }

    public void setBrickCode(Long brickCode) {
        this.brickCode = brickCode;
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
        OwnerProduct that = (OwnerProduct) o;
        return id == (that.id) &&
                Objects.equals(ownerId, that.ownerId) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(brickCode, that.brickCode) &&
                Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ownerId, productName, brickCode, deleted);
    }

    @Override
    public String toString() {
        return "OwnerProduct{" +
                "id=" + id +
                ", ownerId='" + ownerId + '\'' +
                ", productName='" + productName + '\'' +
                ", brickCode=" + brickCode +
                '}';
    }

    @JsonBackReference
    @OneToMany(mappedBy = "ownerProduct", fetch = FetchType.EAGER)
    @ApiModelProperty(hidden=true)
    public Collection<CampaignOwnerProduct> getCampaignOwnerProducts() {
        return campaignOwnerProducts;
    }

    public void setCampaignOwnerProducts(Collection<CampaignOwnerProduct> campaignOwnerProducts) {
        this.campaignOwnerProducts = campaignOwnerProducts;
    }

    @JsonIgnore
    @ManyToOne(optional=false)
    @JoinColumn(name = "brick_code", referencedColumnName = "brick_code",insertable=false, updatable=false)
    public ProductBrick getProductBrick() {
        return productBrick;
    }

    public void setProductBrick(ProductBrick productBrick) {
        this.productBrick= productBrick;
    }
}
