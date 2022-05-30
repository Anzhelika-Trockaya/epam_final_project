package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_PAGE;
import static com.epam.pharmacy.controller.ParameterName.LANGUAGE;

public class ChangeLanguageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();//fixme delete
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String language = request.getParameter(LANGUAGE);
        Router router = new Router(currentPage, Router.Type.REDIRECT);
        DataValidator validator = DataValidatorImpl.getInstance();//fixme!!!!! add attrs data
        if(validator.isCorrectLanguage(language)){
            session.setAttribute(LANGUAGE, language);
        }
        return router;
    }
}
