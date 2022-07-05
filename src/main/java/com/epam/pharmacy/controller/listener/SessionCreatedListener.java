package com.epam.pharmacy.controller.listener;

import com.epam.pharmacy.model.entity.UserRole;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ROLE;

/**
 * The type Session created listener.
 */
@WebListener
public class SessionCreatedListener implements HttpSessionListener {

    /**
     * Adds current user role to session.
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(CURRENT_USER_ROLE, UserRole.GUEST);
    }
}
