package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.model.entity.AbstractEntity;
import com.epam.pharmacy.exception.DaoException;

import java.util.List;

public abstract class BaseDao<T extends AbstractEntity> {///fixme interface
public abstract boolean insert(T t) throws DaoException;
    public abstract boolean delete(T t) throws DaoException;
    public abstract List<T> findAll() throws DaoException;
    public abstract T update(T t) throws DaoException;
}
