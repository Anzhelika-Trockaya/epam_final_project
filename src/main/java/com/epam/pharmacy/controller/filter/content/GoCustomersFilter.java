package com.epam.pharmacy.controller.filter.content;

import com.epam.pharmacy.controller.command.RequestFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "GoCustomersFilter", urlPatterns = "/jsp/doctor/customers.jsp",
        dispatcherTypes = DispatcherType.REQUEST)
public class GoCustomersFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        RequestFiller requestFiller = RequestFiller.getInstance();
        try {
            requestFiller.addActiveCustomers(httpServletRequest);
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page customers.jsp", e);
            throw new ServletException("Exception when fill page customers.jsp", e);
        }
        chain.doFilter(request, response);
    }
}