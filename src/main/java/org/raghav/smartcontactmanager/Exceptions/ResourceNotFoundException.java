package org.raghav.smartcontactmanager.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceNotFound) {
        super(resourceNotFound);
    }

    public ResourceNotFoundException() {
        super("Resource not found");
    }
}
