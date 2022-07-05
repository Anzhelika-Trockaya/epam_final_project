package com.epam.pharmacy.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The type Encoding filter.
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {
    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "text/html";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletRequest.setCharacterEncoding(ENCODING);
        httpServletResponse.setCharacterEncoding(ENCODING);
        httpServletResponse.setContentType(CONTENT_TYPE);
        chain.doFilter(request, response);
    }
}
