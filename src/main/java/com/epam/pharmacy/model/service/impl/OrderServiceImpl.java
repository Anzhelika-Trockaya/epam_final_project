package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.controller.PropertyKey;
import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.OrderDao;
import com.epam.pharmacy.model.dao.impl.MedicineDaoImpl;
import com.epam.pharmacy.model.dao.impl.OrderDaoImpl;
import com.epam.pharmacy.model.dao.impl.PrescriptionDaoImpl;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.OrderPosition;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.service.OrderService;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.epam.pharmacy.controller.AttributeName.FAILED_CHANGE_MESSAGE;
import static com.epam.pharmacy.controller.ParameterName.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final long NOT_EXISTS_ORDER_ID_VALUE = -1;
    private static final long PRESCRIPTION_ID_IF_DO_NOT_NEED = 0;

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
            long cartOrderId = getCartOrderId(customerId, orderDao);
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
                result = orderDao.addPositionToOrder(cartOrderId, medicineIdValue, quantity);
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
            long cartOrderId = getCartOrderId(customerId, orderDao);
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
                result = orderDao.addPositionToOrder(cartOrderId, medicineIdValue, quantity, prescriptionId);
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
            long cartOrderId = orderDao.getOrderIdWithoutPayment(customerId);
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
            long cartOrderId = orderDao.getOrderIdWithoutPayment(customerId);
            return orderDao.findNumberForPrescriptionInOrder(cartOrderId, prescriptionId);
        } catch (DaoException e) {
            LOGGER.error("Finding number for prescription exception. customerId=" + customerId +
                    " prescriptionId=" + prescriptionId, e);
            throw new ServiceException("Finding number for prescription exception. customerId=" + customerId +
                    " prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public Set<OrderPosition> findCartPositions(long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            long cartOrderId = orderDao.getOrderIdWithoutPayment(customerId);
            return orderDao.findOrderPositions(cartOrderId);
        } catch (DaoException e) {
            LOGGER.error("Finding cart positions exception. customerId=" + customerId, e);
            throw new ServiceException("Finding cart positions exception. customerId=" + customerId, e);
        }
    }

    @Override
    public boolean deletePositionFromCart(long medicineId,
                                          long prescriptionId,
                                          long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao);
            long cartOrderId = orderDao.getOrderIdWithoutPayment(customerId);
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
            long cartOrderId = orderDao.getOrderIdWithoutPayment(customerId);
            return orderDao.deleteAllPositionsFromOrder(cartOrderId);
        } catch (DaoException e) {
            LOGGER.error("Clear cart exception. customerId=" + customerId, e);
            throw new ServiceException("Clear cart exception. customerId=" + customerId, e);
        }
    }

    @Override
    public boolean changePositionQuantityInCart(long medicineId,
                                                long prescriptionId,
                                                int quantity,
                                                long customerId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(orderDao, medicineDao, prescriptionDao);
            long cartOrderId = orderDao.getOrderIdWithoutPayment(customerId);
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
                int prescriptionNumberInCartExceptCurrentPosition=
                        orderDao.findNumberForPrescriptionInOrderExceptCurrentPosition(cartOrderId,
                                medicineId, prescriptionId);
                int prescriptionAvailableNumber = prescription.getQuantity() - prescription.getSoldQuantity()-
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

    private int findPositionAvailableQuantityIfNotExistInCart(Medicine medicine,
                                                              long prescriptionId,
                                                              PrescriptionDaoImpl prescriptionDao) throws DaoException {
        int prescriptionAvailableNumber = prescriptionDao.findPrescriptionAvailableNumber(prescriptionId);
        int resultAvailablePackagesForPrescription = prescriptionAvailableNumber / medicine.getNumberInPackage();
        int medicineAvailablePackagesQuantity = medicine.getTotalPackages();
        return Math.min(resultAvailablePackagesForPrescription, medicineAvailablePackagesQuantity);
    }

    private int findPositionsAvailableQuantityIfExistInCart(Medicine medicine,
                                                            long cartOrderId,
                                                            long prescriptionId,
                                                            OrderDaoImpl orderDao,
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

    private int findPositionsAvailableQuantityIfExistInCart(Medicine medicine,
                                                            long cartOrderId,
                                                            OrderDaoImpl orderDao) throws DaoException {
        int medicineQuantityInCart = orderDao.findMedicineQuantityInOrder(cartOrderId, medicine.getId());
        return medicine.getTotalPackages() - medicineQuantityInCart;
    }

    private long getCartOrderId(long customerId, OrderDao orderDao) throws DaoException {
        long cartId = orderDao.getOrderIdWithoutPayment(customerId);
        if (cartId == NOT_EXISTS_ORDER_ID_VALUE) {
            orderDao.createEmptyOrder(customerId);
            cartId = orderDao.getOrderIdWithoutPayment(customerId);
        }
        return cartId;
    }

}
