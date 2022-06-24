package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection;

    public void begin(AbstractDao dao) throws DaoException {
        initConnection(false);
        dao.setConnection(connection);
    }

    public void beginWithAutoCommit(AbstractDao dao) throws DaoException {
        initConnection(true);
        dao.setConnection(connection);
    }

    public void beginWithAutoCommit(AbstractDao... daos) throws DaoException {
        initConnection(true);
        for (AbstractDao dao : daos) {
            dao.setConnection(connection);
        }
    }

    public void begin(AbstractDao... daos) throws DaoException {
        initConnection(false);
        for (AbstractDao dao : daos) {
            dao.setConnection(connection);
        }
    }

    @Override
    public void close() {
        end();
    }

    public void end() {
        if (connection != null) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                LOGGER.error("Exception when change autoCommit to false", e);
            } finally {
                ConnectionPool connectionPool = ConnectionPool.getInstance();
                connectionPool.releaseConnection(connection);
                connection = null;
            }
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Exception when commit transaction", e);
            throw new DaoException("Exception when commit transaction", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Exception when rollback transaction", e);
        }
    }

    private void initConnection(boolean autoCommit) throws DaoException {
        if (connection == null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
        }
        try {
            if (connection.getAutoCommit() != autoCommit) {
                connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception when setting autoCommit in Connection. Value=" + autoCommit, e);
            throw new DaoException("Exception when setting autoCommit in Connection. Value=" + autoCommit, e);
        }
    }
}
