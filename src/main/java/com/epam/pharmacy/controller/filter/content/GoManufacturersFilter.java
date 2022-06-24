package com.epam.pharmacy.controller.filter.content;

import com.epam.pharmacy.controller.command.RequestFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "GoManufacturersFilter", urlPatterns = "/jsp/pharmacist/manufacturers.jsp",
        dispatcherTypes = DispatcherType.REQUEST)
public class GoManufacturersFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        RequestFiller requestFiller = RequestFiller.getInstance();
        try {
            requestFiller.addManufacturers(httpServletRequest);
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page manufacturers.jsp", e);
            throw new ServletException("Exception when fill page manufacturers.jsp", e);
        }
        chain.doFilter(request, response);
    }
}
