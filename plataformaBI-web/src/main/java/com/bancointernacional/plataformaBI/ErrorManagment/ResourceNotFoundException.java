package com.bancointernacional.plataformaBI.ErrorManagment;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
