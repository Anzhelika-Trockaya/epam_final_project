package com.epam.pharmacy.validator.impl;

import com.epam.pharmacy.validator.FileValidator;

public class FileValidatorImpl implements FileValidator {
    private static final String CONTENT_BMP = "image/bmp";
    private static final String CONTENT_PNG = "image/png";
    private static final String CONTENT_JPEG = "image/jpeg";
    private static final String EXTENSION_JPEG = ".jpeg";
    private static final String EXTENSION_JPG = ".jpg";
    private static final String EXTENSION_PNG = ".bmp";
    private static final String EXTENSION_BMP = ".png";
    private static final int MAX_SIZE = 1024 * 1024;
    public static FileValidatorImpl instance;

    private FileValidatorImpl() {
    }

    public static FileValidatorImpl getInstance() {
        if (instance == null) {
            instance = new FileValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isCorrectSize(long size) {
        return size > 0 && size <= MAX_SIZE;
    }

    @Override
    public boolean isCorrectExtension(String extension) {
        return extension != null && (extension.equals(EXTENSION_JPG) || extension.equals(EXTENSION_BMP) || extension.equals(EXTENSION_PNG) || extension.equals(EXTENSION_JPEG));
    }

    @Override
    public boolean isCorrectContentType(String contentType) {
        return contentType != null && (contentType.equals(CONTENT_BMP) || contentType.equals(CONTENT_PNG) || contentType.equals(CONTENT_JPEG));
    }
}
