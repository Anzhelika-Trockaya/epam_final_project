package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.controller.PropertyKey;
import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.OrderDao;
import com.epam.pharmacy.model.dao.impl.MedicineDaoImpl;
import com.epam.pharmacy.model.dao.impl.OrderDaoImpl;
import com.epam.pharmacy.model.dao.impl.PrescriptionDaoImpl;
import com.epam.pharmacy.model.dao.impl.UserDaoImpl;
import com.epam.pharmacy.model.entity.*;
import com.epam.pharmacy.model.service.MedicineService;
import com.epam.pharmacy.model.service.OrderService;
import com.epam.pharmacy.model.service.PrescriptionService;
import com.epam.pharmacy.util.CartPositionsInAscendingMedicineIdComparator;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.epam.pharmacy.controller.AttributeName.FAILED_CHANGE_MESSAGE;
import static com.epam.pharmacy.controller.ParameterName.*;

/**
 * The type Order service.
 */
class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final long NOT_EXISTS_ID_VALUE = -1;
    private static final long PRESCRIPTION_ID_IF_DO_NOT_NEED = 0;
    private static final int COMPARE_RESULT_IF_EQUALS = 0;

    @Override
    public boolean addToCartWithoutPrescription(long customerId,
                                                Map<String, String> positionParams) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        String quantityString = positionParams.get(QUANTITY);
        if (!validator.isCorrectQuantity(quantityString)) {
            return false;
        }
        String medicineIdString = positionParams.get(MEDICINE_ID);
        int quantity = Integer.parseInt(quantityString);
        boolean result;
        OrderDaoImpl orderDao = new OrderDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao, medicineDao);
            long medicineIdValue = Long.parseLong(medicineIdString);
            Optional<Medicine> medicineOptional = medicineDao.findById(medicineIdValue);
            if (!medicineOptional.isPresent()) {
                LOGGER.warn("Medicine with id=" + medicineIdString + " not found");
                return false;
            }
            Medicine medicine = medicineOptional.get();
            long cartOrderId = findCartOrderId(customerId, orderDao);
            if (orderDao.existsPositionInOrder(cartOrderId, medicine.getId(), PRESCRIPTION_ID_IF_DO_NOT_NEED)) {
                int availableQuantity =
                        findPositionsAvailableQuantityIfExistInCart(medicine, cartOrderId, orderDao);
                if (quantity > availableQuantity) {
                    positionParams.put(FAILED_CHANGE_MESSAGE, PropertyKey.MEDICINES_TOO_BIG_QUANTITY);
                    return false;
                }
                result = orderDao.increaseQuantityInOrderPosition(
                        cartOrderId, medicineIdValue, quantity, PRESCRIPTION_ID_IF_DO_NOT_NEED);
            } else {
                int availableQuantity = medicine.getTotalPackages();
                if (quantity > availableQuantity) {
                    positionParams.put(FAILED_CHANGE_MESSAGE, PropertyKey.MEDICINES_TOO_BIG_QUANTITY);
                    return false;
                }
                result = orderDao.addPositionWithoutPrescriptionIdToOrder(cartOrderId, medicineIdValue, quantity);
            }
        } catch (DaoException e) {
            LOGGER.error("Adding to cart exception. customerId=" + customerId +
                    " medicineId=" + medicineIdString + " quantity=" + quantityString, e);
            throw new ServiceException("Adding to cart exception. customerId=" + customerId +
                    " medicineId=" + medicineIdString + " quantity=" + quantityString, e);
        }
        return result;
    }

    @Override
    public boolean addToCartWithPrescription(long customerId,
                                             Map<String, String> positionParams) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        String quantityString = positionParams.get(QUANTITY);
        if (!validator.isCorrectQuantity(quantityString)) {
            return false;
        }
        String medicineIdString = positionParams.get(MEDICINE_ID);
        String prescriptionIdString = positionParams.get(PRESCRIPTION_ID);
        int quantity = Integer.parseInt(quantityString);
        boolean result;
        OrderDaoImpl orderDao = new OrderDaoImpl();
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao, medicineDao, prescriptionDao);
            long medicineIdValue = Long.parseLong(medicineIdString);
            Optional<Medicine> medicineOptional = medicineDao.findById(medicineIdValue);
            if (!medicineOptional.isPresent()) {
                LOGGER.warn("Medicine with id=" + medicineIdString + " not found");
                return false;
            }
            Medicine medicine = medicineOptional.get();
            long prescriptionId = Long.parseLong(prescriptionIdString);
            long cartOrderId = findCartOrderId(customerId, orderDao);
            if (prescriptionIsInvalid(prescriptionDao, prescriptionId)) {
                positionParams.put(FAILED_CHANGE_MESSAGE, PropertyKey.MEDICINES_PRESCRIPTION_IS_NOT_VALID);
                return false;
            }
            if (orderDao.existsPositionInOrder(cartOrderId, medicine.getId(), prescriptionId)) {
                int availableQuantity = findPositionsAvailableQuantityIfExistInCart(
                        medicine, cartOrderId, prescriptionId, orderDao, prescriptionDao);
                if (quantity > availableQuantity) {
                    positionParams.put(FAILED_CHANGE_MESSAGE, PropertyKey.MEDICINES_TOO_BIG_QUANTITY);
                    return false;
                }
                result = orderDao.increaseQuantityInOrderPosition(cartOrderId,
                        medicineIdValue, quantity, prescriptionId);
            } else {
                int availableQuantity =
                        findPositionAvailableQuantityIfNotExistInCart(medicine, prescriptionId, prescriptionDao);
                if (quantity > availableQuantity) {
                    positionParams.put(FAILED_CHANGE_MESSAGE, PropertyKey.MEDICINES_TOO_BIG_QUANTITY);
                    return false;
                }
                result = orderDao.addPositionWithPrescriptionIdToOrder(cartOrderId, medicineIdValue, quantity, prescriptionId);
            }
        } catch (DaoException e) {
            LOGGER.error("Adding to cart exception. customerId=" + customerId +
                    " medicineId=" + medicineIdString + " quantity=" + quantityString +
                    " prescriptionId" + prescriptionIdString, e);
            throw new ServiceException("Adding to cart exception. customerId=" + customerId +
                    " medicineId=" + medicineIdString + " quantity=" + quantityString +
                    " prescriptionId" + prescriptionIdString, e);
        }
        return result;
    }

    @Override
    public Map<Long, Integer> findMedicineInCartWithQuantity(long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            long cartOrderId = findCartOrderId(customerId, orderDao);
            return orderDao.findMedicineIdWithQuantityInOrder(cartOrderId);
        } catch (DaoException e) {
            LOGGER.error("Finding cart medicines id and quantity exception. customerId=" + customerId, e);
            throw new ServiceException("Finding cart medicines id and quantity exception" + customerId, e);
        }
    }

    @Override
    public int findNumberForPrescriptionInCart(long prescriptionId, long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            long cartOrderId = findCartOrderId(customerId, orderDao);
            return orderDao.findNumberForPrescriptionInOrder(cartOrderId, prescriptionId);
        } catch (DaoException e) {
            LOGGER.error("Finding number for prescription exception. customerId=" + customerId +
                    " prescriptionId=" + prescriptionId, e);
            throw new ServiceException("Finding number for prescription exception. customerId=" + customerId +
                    " prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public List<Map<String, Object>> findCartContent(long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            long cartId = findCartOrderId(customerId, orderDao);
            return findOrderContent(cartId, orderDao);
        } catch (DaoException e) {
            LOGGER.error("Finding cart content exception. customerId=" + customerId, e);
            throw new ServiceException("Finding order positions exception. customerId=" + customerId, e);
        }
    }

    @Override
    public List<Map<String, Object>> findOrderContent(long orderId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            return findOrderContent(orderId, orderDao);
        } catch (DaoException e) {
            LOGGER.error("Finding order content exception. orderId=" + orderId, e);
            throw new ServiceException("Finding order positions exception. orderId=" + orderId, e);
        }
    }


    @Override
    public boolean deletePositionFromCart(long medicineId,
                                          long prescriptionId, long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            long cartOrderId = findCartOrderId(customerId, orderDao);
            return orderDao.deletePositionFromOrder(cartOrderId, medicineId, prescriptionId);
        } catch (DaoException e) {
            LOGGER.error("Delete position from cart exception. medicineId=" + medicineId +
                    ", customerId=" + customerId + ", prescriptionId=" + prescriptionId, e);
            throw new ServiceException("Delete position from cart exception. medicineId=" + medicineId +
                    ", customerId=" + customerId + ", prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public boolean clearCart(long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            long cartOrderId = findCartOrderId(customerId, orderDao);
            return orderDao.deleteAllPositionsFromOrder(cartOrderId);
        } catch (DaoException e) {
            LOGGER.error("Clear cart exception. customerId=" + customerId, e);
            throw new ServiceException("Clear cart exception. customerId=" + customerId, e);
        }
    }

    @Override
    public boolean changePositionQuantityInCart(long medicineId, long prescriptionId,
                                                int quantity, long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao, medicineDao, prescriptionDao);
            long cartOrderId = findCartOrderId(customerId, orderDao);
            Optional<Medicine> medicineOptional = medicineDao.findById(medicineId);
            if (!medicineOptional.isPresent()) {
                LOGGER.warn("Medicine with id=" + medicineId + " not found");
                return false;
            }
            Medicine medicine = medicineOptional.get();
            if (medicine.needPrescription() && prescriptionIsInvalid(prescriptionDao, prescriptionId)) {
                return false;
            }
            int medicineQuantityInCartExceptCurrentPosition =
                    orderDao.findMedicineQuantityInOrderExceptCurrentPosition(cartOrderId, medicineId, prescriptionId);
            int prospectiveMedicineQuantityInCart = medicineQuantityInCartExceptCurrentPosition + quantity;
            if (prospectiveMedicineQuantityInCart > medicine.getTotalPackages()) {
                return false;
            }
            if (medicine.needPrescription()) {
                Optional<Prescription> prescriptionOptional = prescriptionDao.findById(prescriptionId);
                if (!prescriptionOptional.isPresent()) {
                    LOGGER.warn("Prescription with id=" + prescriptionId + " not found");
                    return false;
                }
                Prescription prescription = prescriptionOptional.get();
                int prescriptionNumberInCartExceptCurrentPosition =
                        orderDao.findNumberForPrescriptionInOrderExceptCurrentPosition(cartOrderId,
                                medicineId, prescriptionId);
                int prescriptionAvailableNumber = prescription.getQuantity() - prescription.getSoldQuantity() -
                        prescriptionNumberInCartExceptCurrentPosition;
                int medicineAvailablePackagesForPrescription =
                        prescriptionAvailableNumber / medicine.getNumberInPackage();
                if (quantity > medicineAvailablePackagesForPrescription) {
                    return false;
                }
            }
            return orderDao.changePositionQuantityInOrder(cartOrderId, medicineId, prescriptionId, quantity);
        } catch (DaoException e) {
            LOGGER.error("Change position quantity in cart exception. medicineId=" + medicineId +
                    ", customerId=" + customerId + ", prescriptionId=" + prescriptionId + ", quantity=" + quantity, e);
            throw new ServiceException("Change position quantity in cart exception. medicineId=" + medicineId +
                    ", customerId=" + customerId + ", prescriptionId=" + prescriptionId + ", quantity=" + quantity, e);
        }
    }

    @Override
    public boolean order(long customerId, BigDecimal expectedTotalCost) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        BigDecimal totalCost = BigDecimal.ZERO;
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.begin(orderDao, medicineDao, userDao, prescriptionDao);
            long cartOrderId = findCartOrderId(customerId, orderDao);
            Set<OrderPosition> positions = orderDao.findOrderPositions(cartOrderId);
            Optional<BigDecimal> currentPriceOptional;
            BigDecimal currentPrice;
            BigDecimal currentPositionCost;
            boolean isUpdated;
            try {
                for (OrderPosition position : positions) {
                    isUpdated = medicineDao.increaseTotalPackages(position.getMedicineId(), -position.getQuantity());
                    if (!isUpdated) {
                        LOGGER.warn("Medicine total packages not updated. MedicineId=" + position.getMedicineId() +
                                ", increase value=" + (-position.getQuantity()));
                        transaction.rollback();
                        return false;
                    }
                    currentPriceOptional = medicineDao.findMedicinePrice(position.getMedicineId());
                    if (!currentPriceOptional.isPresent()) {
                        LOGGER.warn("Position medicine price not found. MedicineId=" + position.getMedicineId());
                        transaction.rollback();
                        return false;
                    }
                    currentPrice = currentPriceOptional.get();
                    isUpdated = orderDao.updatePriceInPosition(position.getOrderId(), position.getMedicineId(),
                            position.getPrescriptionId(), currentPrice);
                    if (!isUpdated) {
                        LOGGER.warn("Position price not updated." + position + " Price=" + currentPrice);
                        transaction.rollback();
                        return false;
                    }
                    if (position.getPrescriptionId() != PRESCRIPTION_ID_IF_DO_NOT_NEED) {
                        if (prescriptionIsInvalid(prescriptionDao, position.getPrescriptionId())) {
                            LOGGER.warn("Prescription is invalid." + position);
                            transaction.rollback();
                            return false;
                        }
                        isUpdated = prescriptionDao.increaseSoldQuantity(position.getPrescriptionId(),
                                position.getMedicineId(), position.getQuantity());
                        if (!isUpdated) {
                            LOGGER.warn("Position sold quantity is not updated." + position);
                            transaction.rollback();
                            return false;
                        }
                    }
                    currentPositionCost = currentPrice.multiply(new BigDecimal(position.getQuantity()));
                    totalCost = totalCost.add(currentPositionCost);
                }
                if (expectedTotalCost.compareTo(totalCost) == COMPARE_RESULT_IF_EQUALS) {
                    isUpdated = userDao.decreaseAccountBalance(customerId, totalCost);
                    if (!isUpdated) {
                        LOGGER.warn("User balance not updated. userId=" + customerId + " totalCost=" + totalCost);
                        transaction.rollback();
                        return false;
                    }
                    isUpdated = orderDao.makeOrderPaidAndUpdateTotalCostAndPaidDate(cartOrderId, totalCost);
                    if (!isUpdated) {
                        LOGGER.warn("Not made order paid. cartOrderId=" + cartOrderId + " totalCost=" + totalCost);
                        transaction.rollback();
                        return false;
                    }
                    transaction.commit();
                    return true;
                } else {
                    LOGGER.warn("Expected total cost(" + expectedTotalCost +
                            ") not equals Real total cost(" + totalCost + ")");
                    transaction.rollback();
                    return false;
                }
            } catch (DaoException e) {
                LOGGER.warn("Exception when make order transaction. CustomerId=" + customerId +
                        ", expectedTotalCost=" + expectedTotalCost, e);
                transaction.rollback();
                return false;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception when make order. customerId=" + customerId +
                    ", expectedTotalCost=" + expectedTotalCost, e);
            throw new ServiceException("Exception when make order. customerId=" + customerId +
                    ", expectedTotalCost=" + expectedTotalCost, e);
        }//todo check it
    }

    @Override
    public Set<Order> findAllUserOrders(long userId, UserRole role) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            return UserRole.CUSTOMER == role ?
                    orderDao.findAllWithCustomerId(userId) : orderDao.findAllWithPharmacistId(userId);
        } catch (DaoException e) {
            LOGGER.error("Find all orders exception. userId=" + userId + ", role=" + role, e);
            throw new ServiceException("Find all orders exception. userId=" + userId + ", role=" + role, e);
        }
    }

    @Override
    public Set<Order> findPharmacistOrdersWithState(long userId, Order.State state) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            return orderDao.findAllWithPharmacistIdAndState(userId, state);
        } catch (DaoException e) {
            LOGGER.error("Find all orders exception. userId=" + userId + ", state=" + state, e);
            throw new ServiceException("Find all orders exception. userId=" + userId + ", state=" + state, e);
        }
    }

    @Override
    public Optional<Order> findNextPaidOrder(long pharmacistId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            long id;
            while ((id = orderDao.findNextPaidOrderId()) != NOT_EXISTS_ID_VALUE) {
                boolean updated = orderDao.updateOrderStateByOrderId(id, Order.State.IN_PROGRESS);
                if (updated) {
                    updated = orderDao.updateOrderPharmacistId(id, pharmacistId);
                    if (!updated) {
                        LOGGER.warn("Order state not updated. OrderId=" + id);
                        return Optional.empty();
                    }
                    return orderDao.findById(id);
                }
            }
            return Optional.empty();
        } catch (DaoException e) {
            LOGGER.error("Find next paid order exception", e);
            throw new ServiceException("Find next paid order exception", e);
        }
    }

    @Override
    public int findPaidOrdersQuantity() throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            return orderDao.findPaidOrdersQuantity();
        } catch (DaoException e) {
            LOGGER.error("Find new orders quantity exception.", e);
            throw new ServiceException("Find new orders quantity exception.", e);
        }
    }

    @Override
    public Optional<Order> findById(long orderId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            return orderDao.findById(orderId);
        } catch (DaoException e) {
            LOGGER.error("Find order by id exception.", e);
            throw new ServiceException("Find order by id exception.", e);
        }
    }

    @Override
    public boolean completeOrder(long orderId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            return orderDao.updateOrderStateByOrderId(orderId, Order.State.COMPLETED);
        } catch (DaoException e) {
            LOGGER.error("Complete order exception. id=" + orderId, e);
            throw new ServiceException("Complete order exception. id=" + orderId, e);
        }
    }

    private List<Map<String, Object>> findOrderContent(long orderId,
                                                       OrderDaoImpl orderDao) throws ServiceException, DaoException {
        Set<OrderPosition> orderPositions = orderDao.findOrderPositions(orderId);
        List<Map<String, Object>> orderContent = new ArrayList<>();
        if (!orderPositions.isEmpty()) {
            ServiceProvider provider = ServiceProvider.getInstance();
            MedicineService medicineService = provider.getMedicineService();
            PrescriptionService prescriptionService = provider.getPrescriptionService();
            Map<String, Object> positionContent;
            for (OrderPosition position : orderPositions) {
                positionContent = medicineService.findMedicineContentById(position.getMedicineId());
                long prescriptionId = position.getPrescriptionId();
                if (prescriptionId != PRESCRIPTION_ID_IF_DO_NOT_NEED) {
                    Optional<Prescription> prescriptionOptional = prescriptionService.findById(prescriptionId);
                    if (!prescriptionOptional.isPresent()) {
                        LOGGER.warn("Prescription with id=" + prescriptionId + " not found.");
                        continue;
                    }
                    Prescription prescription = prescriptionOptional.get();
                    positionContent.put(PRESCRIPTION, prescription);
                }
                positionContent.put(POSITION, position);
                orderContent.add(positionContent);
            }
        }
        orderContent.sort(new CartPositionsInAscendingMedicineIdComparator());
        return orderContent;
    }

    private boolean prescriptionIsInvalid(PrescriptionDaoImpl prescriptionDao, long prescriptionId) throws DaoException {
        Optional<Prescription> prescription = prescriptionDao.findById(prescriptionId);
        if (!prescription.isPresent()) {
            LOGGER.warn("Prescription not found. id=" + prescriptionId);
            return true;
        }
        LocalDate expirationDate = prescription.get().getExpirationDate();
        LocalDate now = LocalDate.now();
        return !now.isBefore(expirationDate);
    }

    private int findPositionAvailableQuantityIfNotExistInCart(Medicine medicine, long prescriptionId,
                                                              PrescriptionDaoImpl prescriptionDao) throws DaoException {
        int prescriptionAvailableNumber = prescriptionDao.findPrescriptionAvailableNumber(prescriptionId);
        int resultAvailablePackagesForPrescription = prescriptionAvailableNumber / medicine.getNumberInPackage();
        int medicineAvailablePackagesQuantity = medicine.getTotalPackages();
        return Math.min(resultAvailablePackagesForPrescription, medicineAvailablePackagesQuantity);
    }

    private int findPositionsAvailableQuantityIfExistInCart(Medicine medicine, long cartOrderId,
                                                            long prescriptionId, OrderDaoImpl orderDao,
                                                            PrescriptionDaoImpl prescriptionDao) throws DaoException {
        int prescriptionNumberInCart = orderDao.findNumberForPrescriptionInOrder(cartOrderId, prescriptionId);
        int prescriptionAvailableNumber = prescriptionDao.findPrescriptionAvailableNumber(prescriptionId);
        int resultAvailableNumberForPrescription = prescriptionAvailableNumber - prescriptionNumberInCart;
        int resultAvailablePackagesForPrescription =
                resultAvailableNumberForPrescription / medicine.getNumberInPackage();
        int medicineQuantityInCart = orderDao.findMedicineQuantityInOrder(cartOrderId, medicine.getId());
        int medicineAvailablePackages = medicine.getTotalPackages() - medicineQuantityInCart;
        return Math.min(resultAvailablePackagesForPrescription, medicineAvailablePackages);
    }

    private int findPositionsAvailableQuantityIfExistInCart(Medicine medicine, long cartOrderId,
                                                            OrderDaoImpl orderDao) throws DaoException {
        int medicineQuantityInCart = orderDao.findMedicineQuantityInOrder(cartOrderId, medicine.getId());
        return medicine.getTotalPackages() - medicineQuantityInCart;
    }

    private long findCartOrderId(long customerId, OrderDao orderDao) throws DaoException {
        long cartId = orderDao.getOrderIdWithoutPaymentIfExists(customerId);
        if (cartId == NOT_EXISTS_ID_VALUE) {
            orderDao.createEmptyOrder(customerId);
            cartId = orderDao.getOrderIdWithoutPaymentIfExists(customerId);
        }
        return cartId;
    }

}
