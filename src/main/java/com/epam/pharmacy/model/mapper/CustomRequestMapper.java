package com.epam.pharmacy.model.mapper;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface CustomRequestMapper {
    Map<String, String> mapRequest(HttpServletRequest request);
}
