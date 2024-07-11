package com.colvir.spring_boot_homework.exception;

public class RecordFoundException extends RuntimeException {
    public RecordFoundException(String message) {
        super(message);
    }
}
