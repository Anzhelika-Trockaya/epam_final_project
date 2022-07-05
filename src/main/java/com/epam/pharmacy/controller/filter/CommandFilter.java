package com.epam.pharmacy.controller.filter;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.command.CommandType;
import com.epam.pharmacy.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ROLE;
import static com.epam.pharmacy.controller.PagePath.ERROR_404_PAGE;
import static com.epam.pharmacy.controller.ParameterName.COMMAND;

/**
 * The type Command filter class determines what commands the client can call.
 */
@WebFilter(filterName = "CommandFilter", urlPatterns = "/controller")
public class CommandFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final char DELIMITER = '/';

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String command = httpRequest.getParameter(COMMAND);
        if (command == null) {
            request.getRequestDispatcher(ERROR_404_PAGE).forward(httpRequest, httpResponse);
            return;
        }
        UserRole userRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        if (userRole == null) {
            userRole = UserRole.GUEST;
            session.setAttribute(CURRENT_USER_ROLE, UserRole.GUEST);
        }
        Set<String> availableCommands = userRole.getAvailableCommands();
        boolean existsCommand = Arrays.stream(CommandType.values())
                .anyMatch(commandType -> command.equalsIgnoreCase(commandType.toString()));
        if (existsCommand && !availableCommands.contains(command.toUpperCase())) {
            if(UserRole.GUEST == userRole) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + DELIMITER + PagePath.HOME_PAGE);
                return;
            }
            request.getRequestDispatcher(PagePath.ERROR_403_PAGE).forward(request, response);
            return;
        }
        if (!existsCommand) {
            LOGGER.info("Command not exists. Command='" + command+"'");
            request.getRequestDispatcher(ERROR_404_PAGE).forward(httpRequest, httpResponse);
            return;
        }
        chain.doFilter(request, response);
    }
}
