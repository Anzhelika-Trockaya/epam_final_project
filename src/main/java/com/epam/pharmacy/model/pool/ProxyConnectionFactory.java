package com.epam.pharmacy.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Proxy connection factory.
 */
class ProxyConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ProxyConnectionFactory instance;
    private static final String POOL_PROPERTIES_FILE_NAME = "connectionPoolProperties.properties";
    private static final String URL_KEY = "url";
    private static final String PROPERTIES_FILE_NAME = "connectionProperties.properties";
    private static final String URL;
    private static final Properties properties;


    static {
        Properties poolProperties = new Properties();
        try (InputStream inputStream = ProxyConnectionFactory.class.getClassLoader().getResourceAsStream(POOL_PROPERTIES_FILE_NAME)) {
            poolProperties.load(inputStream);
        } catch (IOException exception) {
            LOGGER.fatal("ConnectionPool properties not loaded.", exception);
            throw new ExceptionInInitializerError(exception);
        }
        URL = poolProperties.getProperty(URL_KEY);
        properties = new Properties();
        try (InputStream inputStream = ProxyConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException exception) {
            LOGGER.fatal("ConnectionPool properties not loaded.", exception);
            throw new ExceptionInInitializerError(exception);
        }
    }

    private ProxyConnectionFactory() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    static ProxyConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ProxyConnectionFactory();
        }
        return instance;
    }

    /**
     * Create proxy connection.
     *
     * @return the proxy connection
     * @throws SQLException the sql exception
     */
    ProxyConnection createProxyConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, properties);
        return new ProxyConnection(connection);
    }


}
