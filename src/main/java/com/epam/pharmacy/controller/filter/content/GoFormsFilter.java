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
 * The type Go forms filter. Fills page forms.jsp.
 */
@WebFilter(filterName = "GoFormsFilter", urlPatterns = "/jsp/pharmacist/forms.jsp",
        dispatcherTypes = DispatcherType.REQUEST)
public class GoFormsFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ContentFiller contentFiller = ContentFiller.getInstance();
        try {
            contentFiller.addForms(httpServletRequest);
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page forms.jsp", e);
            throw new ServletException("Exception when fill page forms.jsp", e);
        }
        chain.doFilter(request, response);
    }
}
