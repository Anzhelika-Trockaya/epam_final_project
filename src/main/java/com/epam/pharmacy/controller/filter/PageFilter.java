package com.epam.pharmacy.controller.filter;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ROLE;

/**
 * The type Page filter class determines what pages
 * the client can use.
 */
@WebFilter(filterName = "PageFilter", urlPatterns = "/jsp/*")
public class PageFilter implements Filter {

    private static final char DELIMITER = '/';

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String requestURI = httpRequest.getServletPath();
        UserRole userRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        if (userRole == null) {
            userRole = UserRole.GUEST;
            session.setAttribute(CURRENT_USER_ROLE, UserRole.GUEST);
        }
        boolean isPermitted;
        Set<String> pages = userRole.getAvailablePages();
        isPermitted = pages.stream().anyMatch(requestURI::contains);
        if (!isPermitted && UserRole.GUEST == userRole) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + DELIMITER + PagePath.HOME_PAGE);
            return;
        } else if (!isPermitted) {
            request.getRequestDispatcher(DELIMITER + PagePath.ERROR_403_PAGE).forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}
