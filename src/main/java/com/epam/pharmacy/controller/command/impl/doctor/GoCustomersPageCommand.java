package com.epam.pharmacy.controller.command.impl.doctor;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.command.RequestFiller;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoCustomersPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        RequestFiller requestFiller = RequestFiller.getInstance();
        requestFiller.addActiveCustomers(request);
        return new Router(PagePath.CUSTOMERS);
    }
}
