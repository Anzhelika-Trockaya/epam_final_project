package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.impl.OrderDaoImpl;
import com.epam.pharmacy.model.dao.impl.PrescriptionDaoImpl;
import com.epam.pharmacy.model.entity.DosageUnit;
import com.epam.pharmacy.model.entity.FormUnit;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.service.PrescriptionService;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static com.epam.pharmacy.controller.ParameterName.*;

public class PrescriptionServiceImpl implements PrescriptionService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int RENEWAL_MONTH_QUANTITY = 1;
    private static final int NOT_USING_PRESCRIPTION_SOLD_QUANTITY = 0;

    @Override
    public boolean renewalForAMonth(String prescriptionId) throws ServiceException {
        long id = Long.parseLong(prescriptionId);
        LocalDate newDate = LocalDate.now().plusMonths(RENEWAL_MONTH_QUANTITY);
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(prescriptionDao);
            return prescriptionDao.updateExpirationDateAndSetNeedRenewalFalse(id, newDate);
        } catch (DaoException e) {
            LOGGER.error("Exception when increase prescription's validity.", e);
            throw new ServiceException("Exception when increase prescription's validity.", e);
        }
    }

    @Override
    public Map<Long, Map<String, Object>> findAllByDoctor(long doctorId) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(prescriptionDao);
            return prescriptionDao.findAllOfDoctor(doctorId);
        } catch (DaoException e) {
            LOGGER.error("Exception when find all doctor's prescriptions. DoctorId=" + doctorId, e);
            throw new ServiceException("Exception when find all doctor's prescriptions. DoctorId=" + doctorId, e);
        }
    }

    @Override
    public Map<Long, Map<String, Object>> findRenewalRequestsByDoctor(long doctorId) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(prescriptionDao);
            return prescriptionDao.findNeededRenewalOfDoctor(doctorId);
        } catch (DaoException e) {
            LOGGER.error("Exception when find renewal requests of doctor. DoctorId=" + doctorId, e);
            throw new ServiceException("Exception when find renewal requests of doctor. DoctorId=" + doctorId, e);
        }
    }

    @Override
    public Map<Long, Map<String, Object>> findAllByCustomer(long customerId) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(prescriptionDao);
            return prescriptionDao.findAllOfCustomer(customerId);
        } catch (DaoException e) {
            LOGGER.error("Exception when find all customer's prescriptions. customerId=" + customerId, e);
            throw new ServiceException("Exception when find all customer's prescriptions. customerId=" + customerId, e);
        }
    }

    @Override
    public boolean create(long doctorId, Map<String, String> data) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectPrescriptionData(data)) {
            return false;
        }
        Prescription prescription = buildPrescription(data);
        prescription.setDoctorId(doctorId);
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(prescriptionDao);
            return prescriptionDao.create(prescription);
        } catch (DaoException e) {
            LOGGER.error("Exception when create prescription." + prescription, e);
            throw new ServiceException("Exception when create prescriptions." + prescription, e);
        }
    }

    @Override
    public boolean makeNeedRenewal(String prescriptionId) throws ServiceException {
        long id = Long.parseLong(prescriptionId);
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(prescriptionDao);
            return prescriptionDao.makeNeedRenewal(id);
        } catch (DaoException e) {
            LOGGER.error("Exception when making need renewal request for prescription. id=" +
                    prescriptionId, e);
            throw new ServiceException("Exception when making need renewal request for prescription. id=" +
                    prescriptionId, e);
        }
    }

    @Override
    public Optional<Prescription> findById(String prescriptionId) throws ServiceException {
        try {
            long id = Long.parseLong(prescriptionId);
            return findById(id);
        } catch (ServiceException e) {
            LOGGER.error("Exception when find prescription. id=" + prescriptionId, e);
            throw new ServiceException("Exception when find prescription. id=" + prescriptionId, e);
        }
    }

    @Override
    public Optional<Prescription> findById(long id) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(prescriptionDao);
            return prescriptionDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Exception when find prescription. id=" + id, e);
            throw new ServiceException("Exception when find prescription. id=" + id, e);
        }
    }

    @Override
    public boolean deleteIfIsNotUsed(String prescriptionId) throws ServiceException {
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(prescriptionDao, orderDao);
            long id = Long.parseLong(prescriptionId);
            if (orderDao.existsPrescriptionInOrders(id)) {
                return false;
            } else {
                return prescriptionDao.deleteById(id);
            }
        } catch (DaoException e) {
            LOGGER.error("Exception when delete prescription. id=" + prescriptionId, e);
            throw new ServiceException("Exception when delete prescription. id=" + prescriptionId, e);
        }
    }

    private Prescription buildPrescription(Map<String, String> data) {
        String internationalNameIdString = data.get(PRESCRIPTION_INTERNATIONAL_NAME_ID);
        long internationalNameId = Long.parseLong(internationalNameIdString);
        String quantityString = data.get(PRESCRIPTION_QUANTITY);
        int quantity = Integer.parseInt(quantityString);
        String customerIdString = data.get(PRESCRIPTION_CUSTOMER_ID);
        long customerId = Long.parseLong(customerIdString);
        String validityString = data.get(PRESCRIPTION_VALIDITY);
        int validityMonthQuantity = Integer.parseInt(validityString);
        LocalDate expirationDate = LocalDate.now().plusMonths(validityMonthQuantity);
        String unitString = data.get(ParameterName.PRESCRIPTION_UNIT);
        FormUnit unit = FormUnit.valueOf(unitString);
        String dosageString = data.get(PRESCRIPTION_DOSAGE);
        int dosage = Integer.parseInt(dosageString);
        String dosageUnitString = data.get(PRESCRIPTION_DOSAGE_UNIT);
        DosageUnit dosageUnit = DosageUnit.valueOf(dosageUnitString);
        Prescription.Builder prescriptionBuilder = new Prescription.Builder();
        return prescriptionBuilder.buildInternationalNameId(internationalNameId).
                buildQuantity(quantity).
                buildCustomerId(customerId).
                buildExpirationDate(expirationDate).
                buildUnit(unit).
                buildDosage(dosage).
                buildDosageUnit(dosageUnit).
                buildNeedRenewal(false).
                build();
    }
}
