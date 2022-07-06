package com.epam.pharmacy.controller;

import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.validator.FileValidator;
import com.epam.pharmacy.validator.impl.FileValidatorImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.UUID;

import static com.epam.pharmacy.controller.AttributeName.MEDICINE_IMAGE_LINK;

/**
 * The type Medicine image uploader. Uploads the image to the server.
 */
public class MedicineImageUploader {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PROPERTY_FILE_NAME = "imageUploader.properties";
    private static final String PATH_KEY = "path";
    private static final String UPLOAD_DIRECTORY;
    private static final String EMPTY_STRING = "";
    private static MedicineImageUploader instance;

    static {
        Properties properties = new Properties();
        try (InputStream inputStream = MedicineImageUploader.class.getClassLoader().
                getResourceAsStream(PROPERTY_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException exception) {
            LOGGER.fatal("MedicineImageUploader properties not loaded.", exception);
            throw new ExceptionInInitializerError("MedicineImageUploader properties not loaded." +
                    exception.getMessage());
        }
        UPLOAD_DIRECTORY = properties.getProperty(PATH_KEY);
        File directory = new File(UPLOAD_DIRECTORY);
        if (!directory.exists()) {
            try {
                boolean created = directory.createNewFile();
                if (!created) {
                    LOGGER.fatal("MedicineImageUploader image directory not created. " +
                            "Directory path = '" + UPLOAD_DIRECTORY + "'");
                    throw new ExceptionInInitializerError("MedicineImageUploader image directory not created. " +
                            "Directory path = '" + UPLOAD_DIRECTORY + "'");
                }
            } catch (IOException e) {
                LOGGER.fatal("MedicineImageUploader image directory not created." +
                        "Directory path = '" + UPLOAD_DIRECTORY + "'", e);
                throw new ExceptionInInitializerError("MedicineImageUploader image directory not created." +
                        "Directory path = '" + UPLOAD_DIRECTORY + "'" + e.getMessage());
            }
        }
    }

    private MedicineImageUploader() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MedicineImageUploader getInstance() {
        if (instance == null) {
            instance = new MedicineImageUploader();
        }
        return instance;
    }

    /**
     * Upload image to the server.
     *
     * @param request the request
     * @return the image path string if uploaded, and empty string if not
     * @throws CommandException the command exception
     */
    public String uploadImage(HttpServletRequest request) throws CommandException {
        try {
            Part part = request.getPart(MEDICINE_IMAGE_LINK);
            String submittedFileName = part.getSubmittedFileName();
            if (submittedFileName.isEmpty()) {
                request.setAttribute(AttributeName.INCORRECT_FILE, PropertyKey.ADDING_MEDICINE_INCORRECT_REQUIRED);
                return EMPTY_STRING;
            }
            long fileSize = part.getSize();
            String extension = submittedFileName.toLowerCase().substring(submittedFileName.lastIndexOf('.'));
            String contentType = part.getContentType();
            FileValidator validator = FileValidatorImpl.getInstance();
            if (!validator.isCorrectSize(fileSize)) {
                request.setAttribute(AttributeName.INCORRECT_FILE, PropertyKey.ADDING_MEDICINE_INCORRECT_FILE_SIZE);
                return EMPTY_STRING;
            }
            if (!validator.isCorrectContentType(contentType) || !validator.isCorrectExtension(extension)) {
                request.setAttribute(AttributeName.INCORRECT_FILE, PropertyKey.ADDING_MEDICINE_INCORRECT_FILE_TYPE);
                return EMPTY_STRING;
            }
            try (InputStream inputStream = part.getInputStream()) {
                String pathString = UPLOAD_DIRECTORY + generateFileName(extension);
                Path imagePath = new File(pathString).toPath();
                long bytes = Files.copy(
                        inputStream,
                        imagePath,
                        StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("Upload result is successfully " + bytes + " " + pathString);
                return pathString;
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Upload photo failed ", e);
            throw new CommandException("Upload photo failed ", e);
        }
    }

    private String generateFileName(String extension) {
        return new StringBuilder().
                append(UUID.randomUUID()).
                append(extension).
                toString();
    }
}
