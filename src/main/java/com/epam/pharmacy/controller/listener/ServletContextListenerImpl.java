package com.epam.pharmacy.controller.listener;

import com.epam.pharmacy.model.pool.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

/**
 * The type Servlet context listener.
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    /**
     * Causes the connection pool creation
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
    }

    /**
     * Causes the connection pool destroying
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}
