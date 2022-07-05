package com.epam.pharmacy.controller.command.impl.doctor;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.PrescriptionService;
import com.epam.pharmacy.model.service.impl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Delete prescription command.
 */
public class DeletePrescriptionCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String prescriptionId = request.getParameter(ParameterName.PRESCRIPTION_ID);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        PrescriptionService prescriptionService = serviceProvider.getPrescriptionService();
        Router router=new Router(PagePath.PRESCRIPTIONS_PAGE);
        try {
            boolean result = prescriptionService.deleteIfIsNotUsed(prescriptionId);
            if(result){
                router.setTypeRedirect();
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.PRESCRIPTIONS_DELETED);
            } else{
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.PRESCRIPTIONS_NOT_DELETED);
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addPrescriptions(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the DeletePrescriptionCommand", e);
            throw new CommandException("Exception in the RDeletePrescriptionCommand", e);
        }
        return router;
    }
}
