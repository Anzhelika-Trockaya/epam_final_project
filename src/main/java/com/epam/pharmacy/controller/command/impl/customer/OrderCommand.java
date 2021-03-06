package com.epam.pharmacy.controller.command.impl.customer;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.OrderService;
import com.epam.pharmacy.model.service.impl.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ID;
import static com.epam.pharmacy.controller.AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE;

/**
 * The type Order command.
 */
public class OrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int COMPARE_RESULT_IF_ORDER_COST_IS_BIGGER_THAT_ACCOUNT_BALANCE = 1;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String totalFromJspString = request.getParameter(ParameterName.TOTAL_COST);
        BigDecimal expectedTotalCost = new BigDecimal(totalFromJspString);
        HttpSession session = request.getSession();
        long customerId = (long) session.getAttribute(CURRENT_USER_ID);
        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        Router router = new Router(PagePath.CART_PAGE);
        ContentFiller contentFiller = ContentFiller.getInstance();
        try {
            if (isNotEnoughMoneyToOrder(customerId, expectedTotalCost)) {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE,
                        PropertyKey.CART_NOT_ENOUGH_MONEY_IN_THE_ACCOUNT);
                contentFiller.addCartContent(request);
                return router;
            }
            boolean result = orderService.order(customerId, expectedTotalCost);
            if (result) {
                router.setTypeRedirect();
                session.setAttribute(TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.CART_SUCCESSFULLY_ORDERED);
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.CART_FAILED_ORDER);
                contentFiller.addCartContent(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the ChangeMedicineQuantityInCartCommand", e);
            throw new CommandException("Exception in the ChangeMedicineQuantityInCartCommand", e);
        }
        return router;
    }

    private boolean isNotEnoughMoneyToOrder(long customerId, BigDecimal totalFromJsp) throws ServiceException {
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        BigDecimal accountBalance = userService.findUserAccountBalance(customerId);
        return totalFromJsp.compareTo(accountBalance) == COMPARE_RESULT_IF_ORDER_COST_IS_BIGGER_THAT_ACCOUNT_BALANCE;
    }
}
