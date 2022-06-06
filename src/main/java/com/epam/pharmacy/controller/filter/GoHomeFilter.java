package com.epam.pharmacy.controller.filter;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.RequestFiller;
import com.epam.pharmacy.controller.command.CommandType;
import com.epam.pharmacy.controller.command.GoHomePageCommand;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "GoHomeFilter", urlPatterns = "/jsp/home.jsp", dispatcherTypes = DispatcherType.REQUEST)
public class GoHomeFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        GoHomePageCommand command = (GoHomePageCommand) CommandType.GO_HOME_PAGE.getCommand();
        try {
            RequestFiller.getInstance().addInternationalNames(httpServletRequest);
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page home.jsp" + e);
            throw new ServletException("Exception when fill page home.jsp", e);
        }
        HttpSession session = httpServletRequest.getSession();
        String successfulMessage = (String) session.getAttribute(AttributeName.SUCCESSFUL_ADDED);
        if (successfulMessage != null) {
            httpServletRequest.setAttribute(AttributeName.SUCCESSFUL_ADDED, successfulMessage);
            session.removeAttribute(AttributeName.SUCCESSFUL_ADDED);
        }
        chain.doFilter(request, response);
    }
}
