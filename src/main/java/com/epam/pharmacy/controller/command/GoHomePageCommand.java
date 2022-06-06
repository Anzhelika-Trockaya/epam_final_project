package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.RequestFiller;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoHomePageCommand implements Command{

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        RequestFiller.getInstance().addInternationalNames(request);
        return new Router(PagePath.HOME);
    }


}
