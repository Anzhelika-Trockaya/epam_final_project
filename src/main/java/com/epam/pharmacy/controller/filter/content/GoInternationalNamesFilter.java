package com.epam.pharmacy.controller.filter.content;

import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The type Go international names filter. Fills page international_names.jsp.
 */
@WebFilter(filterName = "GoInternationalNamesFilter", urlPatterns = "/jsp/pharmacist/international_names.jsp",
        dispatcherTypes = DispatcherType.REQUEST)
public class GoInternationalNamesFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException,
            IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ContentFiller contentFiller = ContentFiller.getInstance();
        try {
            contentFiller.addInternationalNames(httpServletRequest);
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page international_names.jsp", e);
            throw new ServletException("Exception when fill page international_names.jsp", e);
        }
        chain.doFilter(request, response);
    }
}
