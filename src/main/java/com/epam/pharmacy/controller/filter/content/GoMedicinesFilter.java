package com.epam.pharmacy.controller.filter.content;

import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ROLE;

/**
 * The type Go medicines filter. Fills page medicines.jsp.
 */
@WebFilter(filterName = "GoMedicinesFilter", urlPatterns = "/jsp/common/medicines.jsp",
        dispatcherTypes = DispatcherType.REQUEST)
public class GoMedicinesFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ContentFiller contentFiller = ContentFiller.getInstance();
        try {
            contentFiller.addMedicinesData(httpServletRequest);
            contentFiller.addInternationalNames(httpServletRequest);
            contentFiller.addForms(httpServletRequest);
            HttpSession session= httpServletRequest.getSession();
            UserRole userRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
            if(UserRole.CUSTOMER == userRole) {
                contentFiller.updateBalanceInSession(httpServletRequest);
            }
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page medicines.jsp", e);
            throw new ServletException("Exception when fill page medicines.jsp", e);
        }
        chain.doFilter(request, response);
    }
}
