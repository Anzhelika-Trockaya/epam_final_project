package com.epam.pharmacy.controller.filter.content;

import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ROLE;

@WebFilter(filterName = "GoOrdersFilter", urlPatterns = "/jsp/common/orders.jsp",
        dispatcherTypes = DispatcherType.REQUEST)
public class GoOrdersFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ContentFiller contentFiller = ContentFiller.getInstance();
        HttpSession session = httpServletRequest.getSession();
        UserRole currentUserRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        try {
            if (UserRole.CUSTOMER == currentUserRole) {
                contentFiller.addCurrentUserOrders(httpServletRequest);
            } else {
                contentFiller.addCurrentPharmacistOrdersWithState(httpServletRequest, Order.State.IN_PROGRESS);
                contentFiller.addNewOrdersQuantity(httpServletRequest);
            }
        } catch (CommandException e) {
            LOGGER.error("Exception when fill page orders.jsp", e);
            throw new ServletException("Exception when fill page orders.jsp", e);
        }
        chain.doFilter(request, response);
    }
}
