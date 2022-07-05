package com.epam.pharmacy.validator;

/**
 * The interface FileValidator.
 */
public interface FileValidator {
    /**
     * Validate the file size.
     *
     * @param size the file size.
     * @return {@code true}, if the file size is valid.
     */
    boolean isCorrectSize(long size);

    /**
     * Validate the file extension.
     *
     * @param extension the file extension.
     * @return {@code true}, if the file extension is valid.
     */
    boolean isCorrectExtension(String extension);

    /**
     * Validate the file content type.
     *
     * @param contentType the file content type.
     * @return {@code true}, if the file content type is valid.
     */
    boolean isCorrectContentType(String contentType);
}
