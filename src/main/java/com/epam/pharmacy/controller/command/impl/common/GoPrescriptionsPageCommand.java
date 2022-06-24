package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.RequestFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoPrescriptionsPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        RequestFiller requestFiller = RequestFiller.getInstance();
        String showRenewalRequests = request.getParameter(ParameterName.SHOW_RENEWAL_REQUESTS);
        if (showRenewalRequests != null) {
            requestFiller.addPrescriptionRenewalRequests(request);
            request.setAttribute(AttributeName.SHOW_RENEWAL_REQUESTS, true);
        } else {
            requestFiller.addPrescriptions(request);
        }
        return new Router(PagePath.PRESCRIPTIONS);
    }
}
