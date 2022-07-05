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
 * The type Go balance filter. Fills page balance.jsp.
 */
@WebFilter(filterName = "GoBalanceFilter",urlPatterns = "/jsp/customer/balance.jsp",
        dispatcherTypes = DispatcherType.REQUEST)
public class GoBalanceFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ContentFiller contentFiller = ContentFiller.getInstance();
        try{
        contentFiller.updateBalanceInSession(httpServletRequest);
        chain.doFilter(request, response);
        } catch (CommandException e) {
            LOGGER.error("Exception in GoBalanceFilter", e);
            throw new ServletException("Exception in GoBalanceFilter", e);
        }
    }
}
