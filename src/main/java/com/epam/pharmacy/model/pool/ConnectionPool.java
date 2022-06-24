package com.epam.pharmacy.model.pool;

import com.mysql.cj.jdbc.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ConnectionPool instance;
    private static final String PROPERTY_FILE_NAME = "connectionPoolProperties.properties";
    private static final String POOL_SIZE_KEY = "poolSize";
    private static final int POOL_SIZE;
    private static final ReentrantLock createLocker = new ReentrantLock(true);
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final Properties properties;
    private final BlockingQueue<ProxyConnection> availableConnections = new LinkedBlockingQueue<>(POOL_SIZE);
    private final BlockingQueue<ProxyConnection> busyConnections = new LinkedBlockingQueue<>(POOL_SIZE);

    static {
        properties = new Properties();
        try (InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException exception) {
            LOGGER.fatal("ConnectionPool properties not loaded.", exception);
            throw new ExceptionInInitializerError("ConnectionPool properties not loaded." + exception.getMessage());
        }
        POOL_SIZE = Integer.parseInt(properties.getProperty(POOL_SIZE_KEY));
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException exception) {
            LOGGER.fatal("Driver not registered.", exception);
            throw new ExceptionInInitializerError("Driver not registered. " + exception.getMessage());
        }
    }

    private ConnectionPool() {
        ProxyConnection connection;
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                connection = createConnection();
                availableConnections.add(connection);
            } catch (SQLException e) {
                LOGGER.warn("Exception when creating connection", e);
            }
        }
        if (availableConnections.size() < POOL_SIZE) {
            int missingConnectionsNumber = POOL_SIZE - availableConnections.size();
            for (int j = 0; j < missingConnectionsNumber; j++) {
                try {
                    connection = createConnection();
                    availableConnections.add(connection);
                } catch (SQLException e) {
                    LOGGER.fatal("Connection was not created!", e);
                    throw new ExceptionInInitializerError("Connection was not created!" + e.getMessage());
                }
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                createLocker.lock();
                if (!isCreated.get()) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                createLocker.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            busyConnections.put(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Thread was interrupted. ", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        boolean released;
        if (!(connection instanceof ProxyConnection) || !busyConnections.contains(connection)) {
            LOGGER.warn("Illegal connection. " + connection);
            return false;
        }
        released = busyConnections.remove(connection);
        try {
            availableConnections.put((ProxyConnection) connection);
        } catch (InterruptedException e) {
            LOGGER.error("Thread was interrupted.", e);
            Thread.currentThread().interrupt();
        }
        return released;
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                availableConnections.take().reallyClose();
                LOGGER.info("Connection closed.");
            } catch (SQLException | InterruptedException e) {
                LOGGER.error("Exception when destroying pool.", e);
            }
        }
        deregisterDrivers();
    }

    private ProxyConnection createConnection() throws SQLException {
        ProxyConnectionFactory connectionFactory = ProxyConnectionFactory.getInstance();
        ProxyConnection connection = connectionFactory.createProxyConnection();
        LOGGER.info("Connection created. " + connection);
        return connection;
    }

    private void deregisterDrivers() {
        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
                LOGGER.debug("Driver deregistered.");
            } catch (SQLException e) {
                LOGGER.error("Exception when deregister driver", e);
            }
        }
    }
}
