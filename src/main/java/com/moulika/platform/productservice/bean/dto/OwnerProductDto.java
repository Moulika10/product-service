package com.mapp.platform.productservice.bean.dto;

import io.swagger.annotations.ApiModelProperty;

public class OwnerProductDto {

    private long id;
    private String ownerId;
    private String productName;
    private long brickCode;
    private String brickDescription;
    private long classCode;
    private String classDescription;

    public OwnerProductDto(long id, String ownerId, String productName, long brickCode, String brickDescription, long classCode, String classDescription) {
        this.id = id;
        this.ownerId = ownerId;
        this.productName = productName;
        this.brickCode = brickCode;
        this.brickDescription = brickDescription;
        this.classCode = classCode;
        this.classDescription = classDescription;
    }

    public OwnerProductDto() {
        super();
    }

    @ApiModelProperty(position = 1, name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ApiModelProperty(position = 2, name = "ownerId")
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @ApiModelProperty(position = 3, name = "productName")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @ApiModelProperty(position = 4, name = "brickCode")
    public long getBrickCode() {
        return brickCode;
    }

    public void setBrickCode(long brickCode) {
        this.brickCode = brickCode;
    }

    @ApiModelProperty(position = 5, name = "brickDescription")
    public String getBrickDescription() {
        return brickDescription;
    }

    public void setBrickDescription(String brickDescription) {
        this.brickDescription = brickDescription;
    }

    @ApiModelProperty(position = 6, name = "classCode")
    public long getClassCode() {
        return classCode;
    }

    public void setClassCode(long classCode) {
        this.classCode = classCode;
    }

    @ApiModelProperty(position = 7, name = "classDescription")
    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }
}
