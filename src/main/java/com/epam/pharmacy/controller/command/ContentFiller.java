package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.*;
import com.epam.pharmacy.model.service.*;
import com.epam.pharmacy.model.service.impl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.AttributeName.CUSTOMER_BIRTHDAY_DATE;
import static com.epam.pharmacy.controller.AttributeName.CUSTOMER_LASTNAME;
import static com.epam.pharmacy.controller.AttributeName.CUSTOMER_NAME;
import static com.epam.pharmacy.controller.AttributeName.CUSTOMER_PATRONYMIC;
import static com.epam.pharmacy.controller.AttributeName.CUSTOMER_SEX;
import static com.epam.pharmacy.controller.AttributeName.MEDICINE;
import static com.epam.pharmacy.controller.AttributeName.PRESCRIPTION_CUSTOMER_ID;
import static com.epam.pharmacy.controller.ParameterName.*;

public class ContentFiller {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ZERO_QUANTITY = 0;
    public static ContentFiller instance;

    private ContentFiller() {
    }

    public static ContentFiller getInstance() {
        if (instance == null) {
            instance = new ContentFiller();
        }
        return instance;
    }

    public void addForms(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        MedicineFormService formService = provider.getMedicineFormService();
        try {
            List<MedicineForm> formsList = formService.findAll();
            request.setAttribute(AttributeName.FORMS_LIST, formsList);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill forms ", e);
            throw new CommandException("Exception when fill forms  ", e);
        }
    }

    public void addInternationalNames(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        InternationalNameService internationalNameService = provider.getInternationalNameService();
        try {
            List<InternationalMedicineName> internationalNamesList = internationalNameService.findAll();
            request.setAttribute(AttributeName.INTERNATIONAL_NAMES_LIST, internationalNamesList);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill international names ", e);
            throw new CommandException("Exception when fill international names ", e);
        }
    }

    public void addMedicinesData(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        MedicineService medicineService = provider.getMedicineService();
        HttpSession session = request.getSession();
        UserRole currentUserRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        try {
            Map<Long, Map<String, Object>> medicinesMap;
            switch (currentUserRole) {
                case CUSTOMER:
                    long customerId = (long) session.getAttribute(CURRENT_USER_ID);
                    medicinesMap = medicineService.findAllAvailableForCustomer(customerId);
                    break;
                case PHARMACIST:
                case ADMIN:
                    medicinesMap = medicineService.findAll();
                    break;
                default:
                    medicinesMap = medicineService.findAllAvailable();
                    break;
            }
            request.setAttribute(MEDICINES_DATA_MAP, medicinesMap);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill medicines ", e);
            throw new CommandException("Exception when fill medicines ", e);
        }
    }

    public void addManufacturers(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        ManufacturerService manufacturerService = provider.getManufacturerService();
        try {
            List<Manufacturer> manufacturersList = manufacturerService.findAll();
            request.setAttribute(AttributeName.MANUFACTURERS_LIST, manufacturersList);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill manufacturers ", e);
            throw new CommandException("Exception when fill manufacturers", e);
        }
    }

    public void addCartContent(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        HttpSession session = request.getSession();
        long customerId = (long) session.getAttribute(CURRENT_USER_ID);
        try {
            List<Map<String, Object>> cartContent = orderService.findCartContent(customerId);
            request.setAttribute(CART_CONTENT_LIST, cartContent);
            Map<Long, Integer> medicineIdWithQuantityFromCart = orderService.findMedicineInCartWithQuantity(customerId);
            boolean isCorrect = checkValidityOfCartContent(cartContent, medicineIdWithQuantityFromCart);
            request.setAttribute(AttributeName.IS_CORRECT_ORDER, isCorrect);
            BigDecimal totalCost = countTotalCost(cartContent);
            request.setAttribute(AttributeName.TOTAL_COST, totalCost);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill cart. customerId=" + customerId, e);
            throw new CommandException("Exception when fill customerId=" + customerId, e);
        }
    }


    public void addCurrentUser(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        long currentUserId = (long) session.getAttribute(CURRENT_USER_ID);
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        try {
            Optional<User> userOptional = userService.findById(currentUserId);
            if (!userOptional.isPresent()) {
                LOGGER.error("Exception when add current user. currentUserId=" + currentUserId);
                throw new CommandException("Exception when add current user. currentUserId=" + currentUserId);
            }
            User user = userOptional.get();
            request.setAttribute(AttributeName.USER, user);
        } catch (ServiceException e) {
            LOGGER.error("Exception when add current user. currentUserId=" + currentUserId, e);
            throw new CommandException("Exception when add current user. currentUserId=" + currentUserId, e);
        }
    }

    public void updateBalanceInSession(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        HttpSession session = request.getSession();
        long customerId = (long) session.getAttribute(CURRENT_USER_ID);
        try {
            BigDecimal actualBalance = userService.findUserAccountBalance(customerId);
            session.setAttribute(CURRENT_USER_BALANCE, actualBalance);
        } catch (ServiceException e) {
            LOGGER.error("Exception when update account balance in session. customerId=" + customerId, e);
            throw new CommandException("Exception when update account balance in session. customerId=" + customerId, e);
        }

    }

    public void addPrescriptions(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        PrescriptionService prescriptionService = provider.getPrescriptionService();
        HttpSession session = request.getSession();
        UserRole currentUserRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        long id = (long) session.getAttribute(CURRENT_USER_ID);
        try {
            Map<Long, Map<String, Object>> prescriptionsMap;
            switch (currentUserRole) {
                case DOCTOR:
                    prescriptionsMap = prescriptionService.findAllByDoctor(id);
                    int renewalRequestsQuantity = findNeedRenewal(prescriptionsMap);
                    request.setAttribute(AttributeName.RENEWAL_REQUESTS_QUANTITY, renewalRequestsQuantity);
                    break;
                case CUSTOMER:
                    prescriptionsMap = prescriptionService.findAllByCustomer(id);
                    addPrescriptionActiveParam(prescriptionsMap);
                    break;
                default:
                    prescriptionsMap = null;
                    break;
            }
            if (prescriptionsMap != null) {
                request.setAttribute(AttributeName.PRESCRIPTIONS_MAP, prescriptionsMap);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill prescriptions ", e);
            throw new CommandException("Exception when fill prescriptions ", e);
        }
    }

    public void addPrescriptionRenewalRequests(HttpServletRequest httpServletRequest) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        PrescriptionService prescriptionService = provider.getPrescriptionService();
        HttpSession session = httpServletRequest.getSession();
        UserRole currentUserRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        long id = (long) session.getAttribute(CURRENT_USER_ID);
        try {
            Map<Long, Map<String, Object>> prescriptionsMap;
            if (currentUserRole == UserRole.DOCTOR) {
                prescriptionsMap = prescriptionService.findRenewalRequestsByDoctor(id);
                httpServletRequest.setAttribute(AttributeName.PRESCRIPTIONS_MAP, prescriptionsMap);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill prescription renewal requests", e);
            throw new CommandException("Exception when fill prescription renewal requests", e);
        }
    }

    public void addUsersExceptCurrent(HttpServletRequest request) throws CommandException {
        try {
            HttpSession session = request.getSession();
            long currentUserId = (long) session.getAttribute(CURRENT_USER_ID);
            ServiceProvider provider = ServiceProvider.getInstance();
            UserService userService = provider.getUserService();
            List<User> listUsers = userService.findAllExceptId(currentUserId);
            request.setAttribute(AttributeName.USERS_LIST, listUsers);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill users ", e);
            throw new CommandException("Exception when fill users ", e);
        }
    }

    public void addActiveCustomers(HttpServletRequest request) throws CommandException {
        try {
            ServiceProvider provider = ServiceProvider.getInstance();
            UserService userService = provider.getUserService();
            List<User> listUsers = userService.findActiveCustomers();
            request.setAttribute(AttributeName.CUSTOMERS_LIST, listUsers);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill customers ", e);
            throw new CommandException("Exception when fill customers ", e);
        }
    }

    public void addCurrentPharmacistOrdersWithState(HttpServletRequest request,
                                                    Order.State state) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        HttpSession session = request.getSession();
        long pharmacistId = (long) session.getAttribute(CURRENT_USER_ID);
        try {
            Set<Order> orders = orderService.findPharmacistOrdersWithState(pharmacistId, state);
            request.setAttribute(AttributeName.ORDERS_SET, orders);
            request.setAttribute(AttributeName.STATE_TO_SHOW, state.name());
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill orders. UserId=" + pharmacistId + ", state=" + state, e);
            throw new CommandException("Exception when fill orders. UserId=" + pharmacistId + ", state=" + state, e);
        }
    }

    public void addCurrentUserOrders(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        HttpSession session = request.getSession();
        UserRole currentUserRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        try {
            long currentUserId = (long) session.getAttribute(CURRENT_USER_ID);
            Set<Order> orders = orderService.findAllUserOrders(currentUserId, currentUserRole);
            request.setAttribute(AttributeName.ORDERS_SET, orders);
        } catch (ServiceException e) {
            long currentUserId = (long) session.getAttribute(CURRENT_USER_ID);
            LOGGER.error("Exception when fill orders. UserId=" + currentUserId, e);
            throw new CommandException("Exception when fill orders. UserId=" + currentUserId, e);
        }
    }

    public void addCustomerPersonalData(HttpServletRequest request, User customer) {
        request.setAttribute(PRESCRIPTION_CUSTOMER_ID, customer.getId());
        request.setAttribute(CUSTOMER_LASTNAME, customer.getLastname());
        request.setAttribute(CUSTOMER_NAME, customer.getName());
        request.setAttribute(CUSTOMER_PATRONYMIC, customer.getPatronymic());
        request.setAttribute(CUSTOMER_SEX, customer.getSex());
        request.setAttribute(CUSTOMER_BIRTHDAY_DATE, customer.getBirthdayDate());
    }

    public void addOrderContentToSession(HttpSession session, long orderId) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        UserService userService = provider.getUserService();
        UserRole currentUserRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        try {
            Optional<Order> orderOptional = orderService.findById(orderId);
            if (!orderOptional.isPresent()) {
                LOGGER.warn("Order not found. OrderId=" + orderId);
                throw new CommandException("Order not found. OrderId=" + orderId);
            }
            Order order = orderOptional.get();
            if (UserRole.CUSTOMER != currentUserRole) {
                Optional<User> customerOptional = userService.findById(order.getCustomerId());
                if (!customerOptional.isPresent()) {
                    LOGGER.warn("Customer not found. CustomerId=" + order.getCustomerId());
                    throw new CommandException("Customer not found. CustomerId=" + order.getCustomerId());
                }
                session.setAttribute(TEMP_CUSTOMER, customerOptional.get());
            }
            session.setAttribute(TEMP_ORDER, order);
            List<Map<String, Object>> orderContent = orderService.findOrderContent(orderId);
            session.setAttribute(TEMP_ORDER_CONTENT_LIST, orderContent);
        } catch (ServiceException e) {
            LOGGER.error("Exception when add order content to session. ", e);
            throw new CommandException("Exception when add order content to session. ", e);
        }
    }


    public void addDataToRequest(HttpServletRequest request, Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public void addNewOrdersQuantity(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        try {
            int newOrdersQuantity = orderService.findPaidOrdersQuantity();
            request.setAttribute(AttributeName.NEW_ORDERS_QUANTITY, newOrdersQuantity);
        } catch (ServiceException e) {
            LOGGER.error("Exception when find new orders quantity.", e);
            throw new CommandException("Exception when find new orders quantity.", e);
        }
    }

    private int findNeedRenewal(Map<Long, Map<String, Object>> prescriptionsMap) {
        Map<String, Object> currentParamsMap;
        int quantity = ZERO_QUANTITY;
        for (Map.Entry<Long, Map<String, Object>> prescription : prescriptionsMap.entrySet()) {
            currentParamsMap = prescription.getValue();
            boolean needRenewal = (boolean) currentParamsMap.get(ParameterName.NEED_RENEWAL);
            if (needRenewal) {
                quantity++;
            }
        }
        return quantity;
    }

    private void addPrescriptionActiveParam(Map<Long, Map<String, Object>> prescriptionsMap) {
        LocalDate currentExpirationDate;
        int currentQuantity;
        int currentSoldQuantity;
        boolean isActiveCurrent;
        for (Map<String, Object> prescription : prescriptionsMap.values()) {
            currentExpirationDate = (LocalDate) prescription.get(EXPIRATION_DATE);
            currentQuantity = (int) prescription.get(QUANTITY);
            currentSoldQuantity = (int) prescription.get(SOLD_QUANTITY);
            isActiveCurrent = !currentExpirationDate.isBefore(LocalDate.now()) &&
                    currentQuantity - currentSoldQuantity > ZERO_QUANTITY;
            prescription.put(ParameterName.IS_ACTIVE, isActiveCurrent);
        }
    }

    private boolean checkValidityOfCartContent(List<Map<String, Object>> cartContent,
                                               Map<Long, Integer> medicineIdWithQuantityFromCart) {
        boolean isValid = true;
        for (Map<String, Object> positionContent : cartContent) {
            isValid = isAvailableQuantity(positionContent, medicineIdWithQuantityFromCart) && isValid;
            if (positionContent.containsKey(PRESCRIPTION)) {
                Prescription prescription = (Prescription) positionContent.get(PRESCRIPTION);
                if (isInvalidPrescriptionExpirationDate(prescription)) {
                    positionContent.put(INVALID_PRESCRIPTION, INVALID_PRESCRIPTION);
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    private boolean isAvailableQuantity(Map<String, Object> positionContent,
                                        Map<Long, Integer> medicineIdWithQuantityFromCart) {
        boolean result = true;
        Medicine medicine = (Medicine) positionContent.get(MEDICINE);
        int medicineQuantityInCart = medicineIdWithQuantityFromCart.get(medicine.getId());
        int totalPackages = medicine.getTotalPackages();
        if (totalPackages == ZERO_QUANTITY) {
            positionContent.put(OUT_OF_STOCK, OUT_OF_STOCK);
            result = false;
        } else if (totalPackages < medicineQuantityInCart) {
            positionContent.put(AVAILABLE_QUANTITY, totalPackages);
            result = false;
        }
        return result;
    }

    private boolean isInvalidPrescriptionExpirationDate(Prescription prescription) {
        LocalDate expirationDate = prescription.getExpirationDate();
        LocalDate now = LocalDate.now();
        return expirationDate.isBefore(now);
    }


    private BigDecimal countTotalCost(List<Map<String, Object>> cartContent) {
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal positionCost;
        Medicine positionMedicine;
        BigDecimal positionPrice;
        OrderPosition position;
        int positionQuantity;
        for (Map<String, Object> positionContent : cartContent) {
            if (!positionContent.containsKey(OUT_OF_STOCK)) {
                positionMedicine = (Medicine) positionContent.get(MEDICINE);
                positionPrice = positionMedicine.getPrice();
                position = (OrderPosition) positionContent.get(POSITION);
                positionQuantity = position.getQuantity();
                positionCost = positionPrice.multiply(BigDecimal.valueOf(positionQuantity));
                totalCost = totalCost.add(positionCost);
            }
        }
        return totalCost;
    }
}
