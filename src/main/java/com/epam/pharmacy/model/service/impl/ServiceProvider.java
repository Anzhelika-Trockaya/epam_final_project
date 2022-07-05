package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.model.service.*;

/**
 * The type Service provider.
 */
public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserService userService;
    private final MedicineFormService medicineFormService;
    private final InternationalNameService internationalNameService;
    private final ManufacturerService manufacturerService;
    private final MedicineService medicineService;
    private final OrderService orderService;
    private final PrescriptionService prescriptionService;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ServiceProvider getInstance() {
        return instance;
    }

    private ServiceProvider() {
        userService = new UserServiceImpl();
        medicineFormService = new MedicineFormServiceImpl();
        internationalNameService = new InternationalNameServiceImpl();
        manufacturerService = new ManufacturerServiceImpl();
        medicineService = new MedicineServiceImpl();
        orderService = new OrderServiceImpl();
        prescriptionService = new PrescriptionServiceImpl();
    }

    /**
     * Gets user service.
     *
     * @return the user service
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Gets medicine form service.
     *
     * @return the medicine form service
     */
    public MedicineFormService getMedicineFormService() {
        return medicineFormService;
    }

    /**
     * Gets international name service.
     *
     * @return the international name service
     */
    public InternationalNameService getInternationalNameService() {
        return internationalNameService;
    }

    /**
     * Gets manufacturer service.
     *
     * @return the manufacturer service
     */
    public ManufacturerService getManufacturerService() {
        return manufacturerService;
    }

    /**
     * Gets medicine service.
     *
     * @return the medicine service
     */
    public MedicineService getMedicineService() {
        return medicineService;
    }

    /**
     * Gets order service.
     *
     * @return the order service
     */
    public OrderService getOrderService() {
        return orderService;
    }

    /**
     * Gets prescription service.
     *
     * @return the prescription service
     */
    public PrescriptionService getPrescriptionService() {
        return prescriptionService;
    }
}
