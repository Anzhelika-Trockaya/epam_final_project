package com.epam.pharmacy.controller.command;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class ParamsMapCreator {
    public static Map<String, String> create(HttpServletRequest request, String... paramNames) {
        Map<String, String> params = new HashMap<>();
        String currentParamValue;
        for (String paramName : paramNames) {
            currentParamValue = request.getParameter(paramName);
            params.put(paramName, currentParamValue);
        }
        return params;
    }
}
