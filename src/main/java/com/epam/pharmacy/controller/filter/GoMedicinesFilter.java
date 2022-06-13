package com.epam.pharmacy.controller.filter;

import com.epam.pharmacy.controller.RequestFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.pharmacy.controller.AttributeName.FAILED;
import static com.epam.pharmacy.controller.AttributeName.SUCCESSFUL_ADDED;

@WebFilter(filterName = "GoMedicinesFilter", urlPatterns = "/jsp/pharmacist/medicines.jsp", dispatcherTypes = DispatcherType.REQUEST)
public class GoMedicinesFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        RequestFiller requestFiller = RequestFiller.getInstance();
        try {
            requestFiller.addForms(httpServletRequest);
            requestFiller.addInternationalNames(httpServletRequest);
            requestFiller.addMedicines(httpServletRequest);
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page medicines.jsp", e);
            throw new ServletException("Exception when fill page medicines.jsp", e);
        }


        //fixme deleted!!! added!! not_added.....
        requestFiller.moveSessionAttributeToRequest(httpServletRequest, SUCCESSFUL_ADDED);
        requestFiller.moveSessionAttributeToRequest(httpServletRequest, FAILED);
        chain.doFilter(request, response);
    }
}
