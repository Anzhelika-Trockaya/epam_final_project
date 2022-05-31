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
import java.util.UUID;

public class MedicineImageUploader {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String UPLOAD_DIRECTORY = "D:/medicines/";//fixme to property
    private static MedicineImageUploader instance;

    private MedicineImageUploader() {
    }

    public static MedicineImageUploader getInstance() {
        if (instance == null) {
            instance = new MedicineImageUploader();
        }
        return instance;
    }

    public String uploadImage(HttpServletRequest request) throws CommandException {
        try {
            Part part = request.getPart(ParameterName.IMAGE);
            String submittedFileName = part.getSubmittedFileName();
            if(submittedFileName.isEmpty()){
                request.setAttribute(AttributeName.INCORRECT_FILE, PropertyKey.ADDING_MEDICINE_INCORRECT_REQUIRED);
                return new String();
            }
            long fileSize = part.getSize();
            String extension = submittedFileName.toLowerCase().substring(submittedFileName.lastIndexOf('.'));
            String contentType = part.getContentType();
            FileValidator validator = FileValidatorImpl.getInstance();
            if (!validator.isCorrectSize(fileSize)) {
                request.setAttribute(AttributeName.INCORRECT_FILE, PropertyKey.ADDING_MEDICINE_INCORRECT_FILE_SIZE);
                return new String();
            }
            if (!validator.isCorrectContentType(contentType) || !validator.isCorrectExtension(extension)) {
                request.setAttribute(AttributeName.INCORRECT_FILE, PropertyKey.ADDING_MEDICINE_INCORRECT_FILE_TYPE);
                return new String();
            }
            try (InputStream inputStream = part.getInputStream()) {//fixme create directory if not exists
                String pathString = UPLOAD_DIRECTORY + generateFileName(request, extension);
                Path imagePath = new File(pathString).toPath();
                long bytes = Files.copy(
                        inputStream,
                        imagePath,
                        StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("Upload result is successfully " + bytes + " " + pathString);
                return pathString;
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Upload photo failed " + e);
            throw new CommandException("Upload photo failed ", e);
        }
    }

    private String generateFileName(HttpServletRequest request, String extension) {
        StringBuilder nameBuilder = new StringBuilder();
        return nameBuilder.append(UUID.randomUUID()).
                append(extension).toString();
    }
}
