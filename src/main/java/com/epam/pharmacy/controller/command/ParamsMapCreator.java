package com.epam.pharmacy.controller.command;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Params map creator.
 */
public class ParamsMapCreator {
    /**
     * Create params map from request.
     *
     * @param request    the request
     * @param paramNames the param names
     * @return the params map
     */
    public static Map<String, String> create(HttpServletRequest request, String... paramNames) {
        Map<String, String> params = new HashMap<>();
        String currentParamValue;
        for (String paramName : paramNames) {
            currentParamValue = request.getParameter(paramName);
            if (currentParamValue != null) {
                currentParamValue = currentParamValue.trim();
            }
            params.put(paramName, currentParamValue);
        }
        return params;
    }
}
