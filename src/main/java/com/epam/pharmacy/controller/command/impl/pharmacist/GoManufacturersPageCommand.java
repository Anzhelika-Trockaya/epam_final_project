package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.RequestFiller;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoManufacturersPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        RequestFiller requestFiller = RequestFiller.getInstance();
        requestFiller.addManufacturers(request);
        return new Router(PagePath.MANUFACTURERS);
    }
}
