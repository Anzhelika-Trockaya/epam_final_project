package com.epam.pharmacy.controller.filter.content;

import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.pharmacy.controller.AttributeName.*;

@WebFilter(filterName = "GoPrescriptionsFilter", urlPatterns = "/jsp/doctor/prescriptions.jsp",
        dispatcherTypes = DispatcherType.REQUEST)
public class GoPrescriptionsFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ContentFiller contentFiller = ContentFiller.getInstance();
        HttpSession session = httpServletRequest.getSession();
        try {
            if (session.getAttribute(TEMP_SHOW_RENEWAL_REQUESTS) != null) {
                contentFiller.addPrescriptionRenewalRequests(httpServletRequest);
            } else {
                contentFiller.addPrescriptions(httpServletRequest);
            }
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page prescriptions.jsp", e);
            throw new ServletException("Exception when fill page prescriptions.jsp", e);
        }
        chain.doFilter(request, response);
    }
}
