package com.web.discounts.exceptions;

public class NoDiscountTemplatesAvailableException extends RuntimeException {

    public NoDiscountTemplatesAvailableException(String message) {
        super(message);
    }
}
