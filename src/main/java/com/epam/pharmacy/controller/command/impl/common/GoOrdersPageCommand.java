package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.model.entity.Order;
import jakarta.servlet.http.HttpServletRequest;

public class GoOrdersPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ContentFiller contentFiller = ContentFiller.getInstance();
        String stateToShow = request.getParameter(ParameterName.STATE_TO_SHOW);
        if (stateToShow != null && !stateToShow.isEmpty()) {
            Order.State state = Order.State.valueOf(stateToShow);
            contentFiller.addCurrentPharmacistOrdersWithState(request, state);
        } else {
            contentFiller.addCurrentUserOrders(request);
        }
        contentFiller.addNewOrdersQuantity(request);
        return new Router(PagePath.ORDERS_PAGE);
    }
}
