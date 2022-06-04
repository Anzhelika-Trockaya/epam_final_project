package com.epam.pharmacy.controller.filter;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.command.CommandType;
import com.epam.pharmacy.controller.command.impl.pharmacist.GoChangeMedicinesPageCommand;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

//@WebFilter(filterName = "GoMedicinesFilter", urlPatterns = "/jsp/pharmacist/medicines.jsp", dispatcherTypes = DispatcherType.REQUEST)
public class GoMedicinesFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        GoChangeMedicinesPageCommand command = (GoChangeMedicinesPageCommand) CommandType.GO_CHANGE_MEDICINES_PAGE.getCommand();
        try {
            command.fillRequestFormsInternationalNamesMedicines(httpServletRequest);
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page medicines.jsp" + e);
            throw new ServletException("Exception when fill page medicines.jsp", e);
        }
        HttpSession session = httpServletRequest.getSession();


        //fixme
        String successfulMessage = (String) session.getAttribute(AttributeName.SUCCESSFUL_ADDED);
        if (successfulMessage != null) {
            httpServletRequest.setAttribute(AttributeName.SUCCESSFUL_ADDED, successfulMessage);
            session.removeAttribute(AttributeName.SUCCESSFUL_ADDED);
        }
        String failedMessage = (String) session.getAttribute(AttributeName.FAILED);
        if (failedMessage != null) {
            httpServletRequest.setAttribute(AttributeName.FAILED, failedMessage);
            session.removeAttribute(AttributeName.FAILED);
        }
        chain.doFilter(request, response);
    }
}
