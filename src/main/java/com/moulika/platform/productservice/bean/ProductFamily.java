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
@Table(name = "product_family", schema = "product_service")
public class ProductFamily {
    private long familyCode;
    private String familyDescription;
    private Set<ProductClass> classes;
    private ProductSegment productSegment;

    public ProductFamily() {
        super();
    }

    public ProductFamily(long familyCode, String familyDescription, Set<ProductClass> classes) {
        super();
        this.familyCode = familyCode;
        this.familyDescription = familyDescription;
        this.classes = classes;
    }

    @Id
    @Column(name = "family_code")
    public long getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(long familyCode) {
        this.familyCode = familyCode;
    }

    @Basic
    @Column(name = "family_description")
    public String getFamilyDescription() {
        return familyDescription.trim();
    }

    public void setFamilyDescription(String familyDescription) {
        this.familyDescription = familyDescription.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductFamily that = (ProductFamily) o;
        return familyCode == that.familyCode &&
                Objects.equals(familyDescription, that.familyDescription);
    }

    @Override
    public String toString() {
        return "ProductFamily{" +
                "familyCode=" + familyCode +
                ", familyDescription='" + familyDescription + '\'' +
                ", classes=" + classes +
                ", productSegment=" + productSegment +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(familyCode, familyDescription);
    }

    @JsonBackReference
    @OneToMany(mappedBy = "productFamily",fetch = FetchType.EAGER)
    @ApiModelProperty(hidden = true)
    public Set<ProductClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<ProductClass> classes) {
        this.classes = classes;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "segment_code", referencedColumnName = "segment_code")
    @ApiModelProperty(hidden = true)
    public ProductSegment getProductSegment() {
        return productSegment;
    }

    public void setProductSegment(ProductSegment productSegment) {
        this.productSegment = productSegment;
    }
}
