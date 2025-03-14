package com.library.app.exception;

/*
 * Author: Francesca Murano
 */

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message) {
        super(message);
    }
}
