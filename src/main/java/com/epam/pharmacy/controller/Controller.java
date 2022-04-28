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

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = {"/controller", "*.do"})
public class Controller extends HttpServlet {
    @Override
    public void init() {

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");//todo to filter
        String commandStr = request.getParameter("command");
        Command command = CommandType.define(commandStr);
        try {
            Router router = command.execute(request);
            if(Router.Type.FORWARD==router.getType()) {
                request.getRequestDispatcher(router.getPage()).forward(request, response);
            } else {
                response.sendRedirect(router.getPage());//todo request.getContextPath()+
            }
        } catch (CommandException e) {
            //todo throw new ServletException(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher(PagePath.ERROR_500).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}