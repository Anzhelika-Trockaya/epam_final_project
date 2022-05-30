package com.epam.pharmacy.controller.listener;

import com.epam.pharmacy.model.pool.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}
