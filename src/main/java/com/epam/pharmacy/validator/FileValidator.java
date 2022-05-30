package com.epam.pharmacy.validator;

public interface FileValidator {
    boolean isCorrectSize(long size);

    boolean isCorrectExtension(String extension);

    boolean isCorrectContentType(String contentType);
}
