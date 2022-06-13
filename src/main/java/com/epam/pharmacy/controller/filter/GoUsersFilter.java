package com.epam.pharmacy.controller.filter;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.RequestFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "GoToUsersFilter", urlPatterns = "/jsp/admin/users.jsp", dispatcherTypes = DispatcherType.REQUEST)
public class GoUsersFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        RequestFiller requestFiller = RequestFiller.getInstance();
        try {
            requestFiller.addUsers(httpServletRequest);
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page users.jsp", e);
            throw new ServletException("Exception when fill page users.jsp", e);
        }
        requestFiller.moveSessionAttributeToRequest(httpServletRequest, AttributeName.SUCCESSFUL_CHANGE_MESSAGE);
        requestFiller.moveSessionAttributeToRequest(httpServletRequest, AttributeName.FAILED_CHANGE_MESSAGE);
        chain.doFilter(request, response);
    }
}
