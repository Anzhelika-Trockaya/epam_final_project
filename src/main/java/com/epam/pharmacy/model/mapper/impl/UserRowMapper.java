package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static com.epam.pharmacy.controller.ParameterName.*;

public class UserRowMapper implements CustomRowMapper<User> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static UserRowMapper instance;

    private UserRowMapper() {
    }

    public static UserRowMapper getInstance() {
        if (instance == null) {
            instance = new UserRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<User> mapRow(ResultSet resultSet) throws DaoException {
        Optional<User> optionalUser;
        try {
            long id = resultSet.getLong(USER_ID);
            String login = resultSet.getString(USER_LOGIN);
            String roleString = resultSet.getString(USER_ROLE);
            UserRole role = UserRole.valueOf(roleString);
            String password = resultSet.getString(USER_PASSWORD);
            String lastname = resultSet.getString(USER_LASTNAME);
            String name = resultSet.getString(USER_NAME);
            String patronymic = resultSet.getString(USER_PATRONYMIC);
            String sexString = resultSet.getString(USER_SEX);
            User.Sex sex = User.Sex.valueOf(sexString);
            LocalDate birthdayDate = resultSet.getDate(USER_BIRTHDAY_DATE).toLocalDate();
            String phone = resultSet.getString(USER_PHONE);
            String address = resultSet.getString(USER_ADDRESS);
            User.Builder builder = new User.Builder(id, login, role);
            User user = builder.buildName(name).
                    buildLastname(lastname).
                    buildPatronymic(patronymic).
                    buildPassword(password).
                    buildSex(sex).
                    buildBirthdayDate(birthdayDate).
                    buildPhone(phone).
                    buildAddress(address).
                    build();
            optionalUser = Optional.of(user);
        } catch (SQLException e) {
            LOGGER.warn("Exception when map user row. " + e);
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
