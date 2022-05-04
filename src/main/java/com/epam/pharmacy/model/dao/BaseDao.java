package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.model.entity.CustomEntity;
import com.epam.pharmacy.exception.DaoException;

import java.util.List;

public interface BaseDao<T extends CustomEntity> {

    boolean create(T t) throws DaoException;

    boolean delete(T t) throws DaoException;

    List<T> findAll() throws DaoException;

    T update(T t) throws DaoException;
}
