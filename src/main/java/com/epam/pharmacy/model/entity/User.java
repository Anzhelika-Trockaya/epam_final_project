package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class User extends AbstractEntity implements Serializable {
    private final long id;
    private final String login;
    private final UserRole role;
    private String password;
    private String lastName;
    private String name;
    private String patronymic;
    private LocalDate birthdayDate;
    private Sex sex;

    public enum Sex {
        MALE,
        FEMALE
    }

    public User(long id, String login) {
        this.id = id;
        this.login = login;
        this.role = UserRole.CUSTOMER;
    }

    public User(long id, String login, UserRole role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public static class UserBuilder {
        private final User user;

        public UserBuilder(long id, String login) {
            user = new User(id, login);
        }

        public UserBuilder(long id, String login, UserRole role) {
            user = new User(id, login, role);
        }

        public UserBuilder buildLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public UserBuilder buildName(String name) {
            user.setName(name);
            return this;
        }

        public UserBuilder buildPatronymic(String patronymic) {
            user.setPatronymic(patronymic);
            return this;
        }

        public UserBuilder buildSex(User.Sex sex) {
            user.setSex(sex);
            return this;
        }

        public UserBuilder buildPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public UserBuilder buildBirthdayDate(LocalDate birthdayDate) {
            user.setBirthdayDate(birthdayDate);
            return this;
        }

        public User build() {
            return user;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                role == user.role &&
                Objects.equals(password, user.password) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(name, user.name) &&
                Objects.equals(patronymic, user.patronymic) &&
                Objects.equals(birthdayDate, user.birthdayDate) &&
                sex == user.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, role, password, lastName, name, patronymic, birthdayDate, sex);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthdayDate=" + birthdayDate +
                ", sex=" + sex +
                '}';
    }
}
