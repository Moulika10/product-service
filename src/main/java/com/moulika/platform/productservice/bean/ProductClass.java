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
import javax.persistence.FetchType;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "product_class", schema = "product_service")
public class ProductClass {
    private long classCode;
    private String classDescription;
    private Set<ProductBrick> bricks;
    private ProductFamily productFamily;

    public ProductClass() {
        super();
    }

    public ProductClass(ProductFamily productFamily, long classCode, String classDescription, Set<ProductBrick> bricks) {
        super();
        this.productFamily = productFamily;
        this.classCode = classCode;
        this.classDescription = classDescription;
        this.bricks = bricks;
    }

    @Id
    @Column(name = "class_code")
    public long getClassCode() {
        return classCode;
    }

    public void setClassCode(long classCode) {
        this.classCode = classCode;
    }

    @Basic
    @Column(name = "class_description")
    public String getClassDescription() {
        return classDescription.trim();
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductClass that = (ProductClass) o;
        return classCode == that.classCode &&
                Objects.equals(classDescription, that.classDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(classCode, classDescription);
    }

    @JsonBackReference
    @OneToMany(mappedBy = "productClass", fetch = FetchType.EAGER)
    @ApiModelProperty(hidden = true)
    public Set<ProductBrick> getBricks() {
        return bricks;
    }

    public void setBricks(Set<ProductBrick> bricks) {
        this.bricks = bricks;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "family_code", referencedColumnName = "family_code", nullable = false)
    @ApiModelProperty(hidden = true)
    public ProductFamily getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(ProductFamily productFamily) {
        this.productFamily = productFamily;
    }

    @Override
    public String toString() {
        return "ProductClass{" +
                "classCode=" + classCode +
                ", classDescription='" + classDescription + '\'' +
                ", bricks=" + bricks +
                ", productFamily=" + productFamily +
                '}';
    }
}
