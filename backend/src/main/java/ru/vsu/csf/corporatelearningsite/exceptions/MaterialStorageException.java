package ru.vsu.csf.corporatelearningsite.exceptions;

public class MaterialStorageException extends RuntimeException {
    public MaterialStorageException(String message) {
        super(message);
    }

    public MaterialStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}