package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoPrescriptionsPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContentFiller contentFiller = ContentFiller.getInstance();
        String showRenewalRequests = request.getParameter(ParameterName.SHOW_RENEWAL_REQUESTS);
        if (showRenewalRequests != null) {
            contentFiller.addPrescriptionRenewalRequests(request);
            request.setAttribute(AttributeName.TEMP_SHOW_RENEWAL_REQUESTS, true);
        } else {
            contentFiller.addPrescriptions(request);
        }
        return new Router(PagePath.PRESCRIPTIONS_PAGE);
    }
}
