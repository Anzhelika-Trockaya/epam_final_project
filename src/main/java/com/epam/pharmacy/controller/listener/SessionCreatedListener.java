package com.epam.pharmacy.controller.listener;

import com.epam.pharmacy.model.entity.UserRole;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ROLE;

@WebListener
public class SessionCreatedListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(CURRENT_USER_ROLE, UserRole.GUEST);//todo cookies????
        /* Session is created. */
    }
}
