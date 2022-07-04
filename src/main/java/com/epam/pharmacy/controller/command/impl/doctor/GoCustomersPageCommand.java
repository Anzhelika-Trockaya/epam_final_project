package com.epam.pharmacy.controller.command.impl.doctor;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoCustomersPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContentFiller contentFiller = ContentFiller.getInstance();
        contentFiller.addActiveCustomers(request);
        return new Router(PagePath.CUSTOMERS_PAGE);
    }
}
