package com.epam.pharmacy.controller;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.epam.pharmacy.controller.ParameterName.IMAGE_PATH;

/**
 * The type Image controller. Uploads the image to the page.
 */
@WebServlet(name = "ImageController", urlPatterns = {"/uploadImage"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 25)
public class ImageController extends HttpServlet {
    private static final String CONTENT_TYPE_IMAGE = "image/jpeg";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getParameter(IMAGE_PATH);
        byte[] imageBytes = Files.readAllBytes(Paths.get(path));
        response.setContentType(CONTENT_TYPE_IMAGE);
        response.setContentLength(imageBytes.length);
        response.getOutputStream().write(imageBytes);
    }
}
