package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoHomePageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContentFiller.getInstance().addInternationalNames(request);
        return new Router(PagePath.HOME);
    }


}
