package com.epam.pharmacy.controller;

import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.CommandType;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.model.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init() {
        ConnectionPool.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");//todo to filter
        String commandStr = request.getParameter("command");
        Command command = CommandType.define(commandStr);
        try {
            Router router = command.execute(request);
            switch (router.getType()) {
                case FORWARD:
                    request.getRequestDispatcher(router.getPage()).forward(request, response);
                    break;
                case REDIRECT:
                    response.sendRedirect(router.getPage());//todo request.getContextPath()+
                    break;
                default:
                    LOGGER.warn("Unknown Router type" + router.getType());
                    break;
            }
        } catch (CommandException e) {
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher(PagePath.ERROR_500).forward(request, response);//todo redirect??
        }
    }
}