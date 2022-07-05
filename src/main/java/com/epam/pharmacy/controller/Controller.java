package com.epam.pharmacy.controller;

import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.CommandType;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The type Controller. Manages clients requests, forms and sends responses.
 * Override GET and POST methods.
 */
@WebServlet(name = "helloServlet", urlPatterns = {"/controller"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 25)
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandStr = request.getParameter(ParameterName.COMMAND);
        Command command = CommandType.commandOf(commandStr);
        try {
            Router router = command.execute(request);
            switch (router.getType()) {
                case FORWARD:
                    request.getRequestDispatcher(router.getPage()).forward(request, response);
                    break;
                case REDIRECT:
                    response.sendRedirect(router.getPage());
                    break;
                default:
                    LOGGER.warn("Unknown Router type" + router.getType());
                    break;
            }
        } catch (CommandException e) {
            request.getRequestDispatcher(PagePath.ERROR_500_PAGE).forward(request, response);
        }
    }
}