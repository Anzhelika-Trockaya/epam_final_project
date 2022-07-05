package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.model.entity.AbstractEntity;
import com.epam.pharmacy.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * The type Abstract dao.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractDao<T extends AbstractEntity> {
    /**
     * The Connection.
     */
    protected Connection connection;

    /**
     * Create boolean.
     *
     * @param t the t
     * @return {@code true} if created
     * @throws DaoException the dao exception
     */
    public abstract boolean create(T t) throws DaoException;

    /**
     * Delete by id boolean.
     *
     * @param id the id
     * @return {@code true} if deleted
     * @throws DaoException the dao exception
     */
    public abstract boolean deleteById(Long id) throws DaoException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<T> findAll() throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    public abstract Optional<T> findById(long id) throws DaoException;

    /**
     * Update optional.
     *
     * @param t the t
     * @return the optional
     * @throws DaoException the dao exception
     */
    public abstract Optional<T> update(T t) throws DaoException;

    /**
     * Sets connection.
     *
     * @param connection the connection
     */
    void setConnection(Connection connection) {
        this.connection = connection;
    }
}
