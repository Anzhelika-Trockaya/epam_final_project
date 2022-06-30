package com.epam.pharmacy.controller.command.impl.customer;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.RequestFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.service.MedicineService;
import com.epam.pharmacy.model.service.OrderService;
import com.epam.pharmacy.model.service.PrescriptionService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE;
import static com.epam.pharmacy.controller.ParameterName.PRESCRIPTION_ID;

public class ShowMedicinesForPrescriptionCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ZERO_QUANTITY = 0;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MedicineService medicineService = serviceProvider.getMedicineService();
        PrescriptionService prescriptionService = serviceProvider.getPrescriptionService();
        OrderService orderService = serviceProvider.getOrderService();
        Router router = new Router(PagePath.MEDICINES);
        String prescriptionId = request.getParameter(PRESCRIPTION_ID);
        try {
            Optional<Prescription> prescriptionOptional = prescriptionService.findById(prescriptionId);
            if (!prescriptionOptional.isPresent()) {
                LOGGER.warn("Prescription with id=" + prescriptionId + " not found");
                return router;
            }
            Prescription prescription = prescriptionOptional.get();
            HttpSession session = request.getSession();
            long currentUserId = (long) session.getAttribute(CURRENT_USER_ID);
            int numberForPrescriptionInCart =
                    orderService.findNumberForPrescriptionInCart(prescription.getId(), currentUserId);
            int prospectiveSoldNumber = prescription.getSoldQuantity() + numberForPrescriptionInCart;
            if (prescription.getQuantity() > prospectiveSoldNumber) {
                Map<Long, Map<String, Object>> medicinesForPrescriptionData =
                        medicineService.findByPrescription(prescription);
                int prescriptionAvailableNumber = prescription.getQuantity() - prospectiveSoldNumber;
                changeTotalQuantitiesToMaxPossible(currentUserId,
                        medicinesForPrescriptionData, prescriptionAvailableNumber);
                request.setAttribute(MEDICINES_DATA_MAP, medicinesForPrescriptionData);
                request.setAttribute(AttributeName.SHOW_PRESCRIPTIONS_MEDICINES, true);
                request.setAttribute(AttributeName.PRESCRIPTION_ID, prescription.getId());
                request.setAttribute(AttributeName.PRESCRIPTION_EXPIRATION_DATE, prescription.getExpirationDate());
                request.setAttribute(AttributeName.PRESCRIPTION_AVAILABLE_QUANTITY, prescriptionAvailableNumber);
            } else {
                router = new Router(PagePath.PRESCRIPTIONS);
                request.setAttribute(FAILED_CHANGE_MESSAGE, PropertyKey.PRESCRIPTIONS_NOT_AVAILABLE_IN_CART_YET);
                RequestFiller requestFiller= RequestFiller.getInstance();
                requestFiller.addPrescriptions(request);
            }
            return router;
        } catch (ServiceException e) {
            LOGGER.error("Exception in the ShowMedicinesForPrescriptionCommand", e);
            throw new CommandException("Exception in the ShowMedicinesForPrescriptionCommand", e);
        }
    }

    private void changeTotalQuantitiesToMaxPossible(long customerId,
                                                    Map<Long, Map<String, Object>> medicinesForPrescriptionData,
                                                    int prescriptionAvailableNumber) throws ServiceException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        Map<Long, Integer> cartContent = orderService.findMedicineInCartWithQuantity(customerId);
        Medicine medicine;
        int medicineFromCartPackages;
        int totalWithoutCartPackages;
        for (long medicineId : cartContent.keySet()) {
            if (medicinesForPrescriptionData.containsKey(medicineId)) {
                medicine = (Medicine) medicinesForPrescriptionData.get(medicineId).get(MEDICINE);
                medicineFromCartPackages = cartContent.get(medicineId);
                totalWithoutCartPackages = medicine.getTotalPackages() - medicineFromCartPackages;
                medicine.setTotalPackages(totalWithoutCartPackages);
            }
        }
        Iterator<Map.Entry<Long, Map<String, Object>>> medicinesForPrescriptionDataIterator =
                medicinesForPrescriptionData.entrySet().iterator();
        while (medicinesForPrescriptionDataIterator.hasNext()) {
            Map.Entry<Long, Map<String, Object>> entry = medicinesForPrescriptionDataIterator.next();
            Medicine entryMedicine = (Medicine) entry.getValue().get(MEDICINE);
            int prescriptionAvailablePackages = prescriptionAvailableNumber / entryMedicine.getNumberInPackage();
            int availableResult = Math.min(entryMedicine.getTotalPackages(), prescriptionAvailablePackages);
            if (availableResult > ZERO_QUANTITY) {
                entryMedicine.setTotalPackages(availableResult);
            } else {
                medicinesForPrescriptionDataIterator.remove();
            }
        }
    }
}
