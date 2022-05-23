package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.model.entity.CustomEntity;
import com.epam.pharmacy.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends CustomEntity> {
    protected Connection connection;

    public abstract boolean create(T t) throws DaoException;

    public abstract boolean deleteById(Long id) throws DaoException;

    public abstract List<T> findAll() throws DaoException;

    public abstract Optional<T> findById(long id) throws DaoException;

    public abstract T update(T t) throws DaoException;

    public void close(Statement statement){
        try{
            if(statement!=null){
                statement.close();
            }
        } catch (SQLException e) {
            //fixme log?
        }
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }
}
