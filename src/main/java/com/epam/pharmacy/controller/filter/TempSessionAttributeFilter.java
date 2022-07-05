package com.epam.pharmacy.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

/**
 * The type Temp session attribute filter. Moves temp session attributes to request.
 */
@WebFilter(filterName = "TempSessionAttributeFilter", urlPatterns = "/jsp/*", dispatcherTypes = DispatcherType.REQUEST)
public class TempSessionAttributeFilter implements Filter {
    private static final String TEMP_ATTR_PREFIX = "temp_";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpServletRequest.getSession(false);
        Enumeration<String> attrsNames = httpSession.getAttributeNames();
        String currentName;
        while (attrsNames.hasMoreElements()) {
            currentName = attrsNames.nextElement();
            if (currentName.startsWith(TEMP_ATTR_PREFIX)) {
                httpServletRequest.setAttribute(currentName, httpSession.getAttribute(currentName));
                httpSession.removeAttribute(currentName);
            }
        }
        chain.doFilter(request, response);
    }
}
