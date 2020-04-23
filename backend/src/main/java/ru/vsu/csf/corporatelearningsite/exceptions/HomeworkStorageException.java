package ru.vsu.csf.corporatelearningsite.exceptions;

public class HomeworkStorageException extends RuntimeException {
    public HomeworkStorageException(String message) {
        super(message);
    }

    public HomeworkStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
