package com.epam.pharmacy.controller.filter;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.CommandType;
import com.epam.pharmacy.controller.command.impl.admin.GoChangeUsersPageCommand;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "GoToUsersFilter", urlPatterns = "/jsp/admin/users.jsp", dispatcherTypes = DispatcherType.REQUEST)
public class GoToUsersFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        GoChangeUsersPageCommand command = (GoChangeUsersPageCommand) CommandType.GO_CHANGE_USERS_PAGE.getCommand();
        try {
            command.addUsersList(httpServletRequest);
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page users.jsp" + e);
            throw new ServletException("Exception when fill page users.jsp", e);
        }
        HttpSession session = httpServletRequest.getSession();
        String successfulMessage = (String) session.getAttribute(AttributeName.SUCCESSFUL_CHANGE_MESSAGE);
        if (successfulMessage != null) {
            httpServletRequest.setAttribute(AttributeName.SUCCESSFUL_CHANGE_MESSAGE, successfulMessage);
            session.removeAttribute(AttributeName.SUCCESSFUL_CHANGE_MESSAGE);
        }
        String failedMessage = (String) session.getAttribute(AttributeName.FAILED_CHANGE_MESSAGE);
        if (failedMessage != null) {
            httpServletRequest.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, failedMessage);
            session.removeAttribute(AttributeName.FAILED_CHANGE_MESSAGE);
        }
        chain.doFilter(request, response);
    }
}
