package com.moulika.platform.productservice.exception;

public class ProductServiceException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ProductServiceException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public ProductServiceException() {
        super();
    }
}
