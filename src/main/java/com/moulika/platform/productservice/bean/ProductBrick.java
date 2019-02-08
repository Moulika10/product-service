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
@Table(name = "product_brick", schema = "product_service")
public class ProductBrick {
    private long brickCode;
    private String brickDescription;
    private ProductClass productClass;
    private Collection<OwnerProduct> ownerProducts;

    public ProductBrick() {
        super();
    }

    public ProductBrick(ProductClass productClass, long brickCode, String brickDescription) {
        super();
        this.productClass = productClass;
        this.brickCode = brickCode;
        this.brickDescription = brickDescription;
    }

    @Id
    @Column(name = "brick_code")
    public long getBrickCode() {
        return brickCode;
    }

    public void setBrickCode(long brickCode) {
        this.brickCode = brickCode;
    }

    @Basic
    @Column(name = "brick_description")
    public String getBrickDescription() {
        return brickDescription.trim();
    }

    public void setBrickDescription(String brickDescription) {
        this.brickDescription = brickDescription.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductBrick that = (ProductBrick) o;
        return brickCode == that.brickCode &&
                Objects.equals(brickDescription, that.brickDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(brickCode, brickDescription);
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "class_code", referencedColumnName = "class_code", nullable = false)
    @ApiModelProperty(hidden = true)
    public ProductClass getProductClass() {
        return productClass;
    }

    public void setProductClass(ProductClass productClass) {
        this.productClass = productClass;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "productBrick")
    @ApiModelProperty(hidden = true)
    public Collection<OwnerProduct> getOwnerProducts() {
        return ownerProducts;
    }

    public void setOwnerProducts(Collection<OwnerProduct> ownerProducts) {
        this.ownerProducts = ownerProducts;
    }
}
