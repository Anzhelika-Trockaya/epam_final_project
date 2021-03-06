package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_PAGE;
import static com.epam.pharmacy.controller.ParameterName.LANGUAGE;

/**
 * The type Change language command.
 */
public class ChangeLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String language = request.getParameter(LANGUAGE);
        Router router = new Router(currentPage, Router.Type.REDIRECT);
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isCorrectLanguage(language)) {
            session.setAttribute(LANGUAGE, language);
        }
        return router;
    }
}
