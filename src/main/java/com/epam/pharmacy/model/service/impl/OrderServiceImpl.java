package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.OrderDao;
import com.epam.pharmacy.model.dao.impl.MedicineDaoImpl;
import com.epam.pharmacy.model.dao.impl.OrderDaoImpl;
import com.epam.pharmacy.model.dao.impl.UserDaoImpl;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.service.OrderService;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final long NOT_EXISTS_ORDER_ID_VALUE = -1;

    @Override
    public boolean addToCart(long customerId, String medicineIdString, String quantityString) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectQuantity(quantityString)) {
            return false;
        }
        long medicineId = Long.parseLong(medicineIdString);
        int quantity = Integer.parseInt(quantityString);
        boolean result = false;
        UserDaoImpl userDao = new UserDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao, medicineDao, orderDao);
            if (existsCustomer(customerId, userDao)) {
                Optional<Medicine> medicineOptional = medicineDao.findById(medicineId);
                if (medicineOptional.isPresent()) {
                    int totalQuantity = medicineOptional.get().getTotalNumberOfParts();
                    if (quantity <= totalQuantity) {
                        long orderId = getCartOrderId(customerId, orderDao);
                        if (!orderDao.existsPositionInCart(orderId, medicineId)) {
                            result = orderDao.addToCart(orderId, medicineId, quantity);
                        } else {
                            result = orderDao.increaseMedicineQuantityInOrder(orderId, medicineId, quantity);
                        }
                    }
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Adding to cart exception. customerId=" + customerId +
                    " medicineId=" + medicineIdString + " quantity=" + quantityString, e);
            throw new ServiceException("Adding to cart exception. customerId=" + customerId +
                    " medicineId=" + medicineIdString + " quantity=" + quantityString, e);
        }
        return result;
    }

    public Map<Medicine, Integer> findCartContent(long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            long cartOrderId = orderDao.getOrderIdWithoutPayment(customerId);
            return orderDao.findOrderPositions(cartOrderId);
        }catch (DaoException e) {
            LOGGER.error("Finding cart positions exception. customerId=" + customerId, e);
            throw new ServiceException("Finding cart positions exception. customerId=" + customerId, e);
        }
    }

    @Override
    public Map<Long, Integer> findCartPositions(long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            long cartOrderId = orderDao.getOrderIdWithoutPayment(customerId);
            return orderDao.findOrderMedicineIdAndQuantity(cartOrderId);
        }catch (DaoException e) {
            LOGGER.error("Finding cart content exception. customerId=" + customerId, e);
            throw new ServiceException("Finding cart content exception. customerId=" + customerId, e);
        }
    }

    private long getCartOrderId(long customerId, OrderDao orderDao) throws DaoException {
        long cartId = orderDao.getOrderIdWithoutPayment(customerId);
        if (cartId == NOT_EXISTS_ORDER_ID_VALUE) {
            orderDao.createEmptyOrder(customerId);
            cartId = orderDao.getOrderIdWithoutPayment(customerId);
        }
        return cartId;
    }

    private boolean existsCustomer(long customerId, UserDaoImpl userDao) throws DaoException {
        Optional<User> userOptional = userDao.findById(customerId);
        return userOptional.isPresent() && UserRole.CUSTOMER == userOptional.get().getRole();
    }
}
