package com.epam.pharmacy.controller.command.impl.customer;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoCartCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContentFiller contentFiller = ContentFiller.getInstance();
        contentFiller.addCartContent(request);
        return new Router(PagePath.CART);
    }
}
