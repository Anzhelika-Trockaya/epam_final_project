package com.epam.pharmacy.controller.filter;

import com.epam.pharmacy.controller.RequestFiller;
import com.epam.pharmacy.controller.command.CommandType;
import com.epam.pharmacy.controller.command.impl.pharmacist.GoAddMedicinePageCommand;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.pharmacy.controller.AttributeName.*;

@WebFilter(filterName = "GoToAddingMedicineFilter", urlPatterns = "/jsp/pharmacist/adding_medicine.jsp", dispatcherTypes = DispatcherType.REQUEST)
public class GoToAddingMedicineFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try {
            RequestFiller requestFiller = RequestFiller.getInstance();
            requestFiller.addManufacturers(httpServletRequest);
            requestFiller.addForms(httpServletRequest);
            requestFiller.addInternationalNames(httpServletRequest);
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page adding_medicine.jsp" + e);
            throw new ServletException("Exception when fill page adding_medicine.jsp", e);
        }
        HttpSession session = httpServletRequest.getSession();
        String successfulMessage = (String) session.getAttribute(SUCCESSFUL_ADDED);
        if (successfulMessage != null) {
            httpServletRequest.setAttribute(SUCCESSFUL_ADDED, successfulMessage);
            session.removeAttribute(SUCCESSFUL_ADDED);
        }
        chain.doFilter(request, response);
    }
}
