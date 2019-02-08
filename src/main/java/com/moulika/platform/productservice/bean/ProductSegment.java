package com.moulika.platform.productservice.bean;

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
@Table(name = "product_segment", schema = "product_service")
public class ProductSegment {
    private long segmentCode;
    private String segmentDescription;
    private Set<ProductFamily> families;

    public ProductSegment() {
        super();
    }

    public ProductSegment(long segmentCode, String segmentDescription, Set<ProductFamily> families) {
        super();
        this.segmentCode = segmentCode;
        this.segmentDescription = segmentDescription;
        this.families = families;
    }

    @Id
    @Column(name = "segment_code")
    public long getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(long segmentCode) {
        this.segmentCode = segmentCode;
    }

    @Basic
    @Column(name = "segment_description")
    public String getSegmentDescription() {
        return segmentDescription.trim();
    }

    public void setSegmentDescription(String segmentDescription) {
        this.segmentDescription = segmentDescription.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSegment that = (ProductSegment) o;
        return segmentCode == that.segmentCode &&
                Objects.equals(segmentDescription, that.segmentDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(segmentCode, segmentDescription);
    }

    @JsonBackReference
    @OneToMany(mappedBy = "productSegment", fetch = FetchType.EAGER)
    @ApiModelProperty(hidden = true)
    public Set<ProductFamily> getFamilies() {
        return families;
    }

    public void setFamilies(Set<ProductFamily> families) {
        this.families = families;
    }

    @Override
    public String toString() {
        return "ProductSegment{" +
                "segmentCode=" + segmentCode +
                ", segmentDescription='" + segmentDescription + '\'' +
                ", families=" + families +
                '}';
    }
}
