package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class User extends CustomEntity implements Serializable {
    private static final long serialVersionUID = -3995979375755262375L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private final String login;
    private final UserRole role;
    private String password;
    private String lastname;
    private String name;
    private String patronymic;
    private State state;
    private LocalDate birthdayDate;
    private Sex sex;
    private String phone;
    private String address;

    public enum Sex {
        MALE,
        FEMALE
    }

    public enum State{
        ACTIVE,
        BLOCKED
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

    public User(String login) {
        id = DEFAULT_ID;
        this.login = login;
        this.role = UserRole.CUSTOMER;
    }

    public User(String login, UserRole role) {
        id = DEFAULT_ID;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static class Builder {
        private final User user;

        public Builder(long id, String login) {
            user = new User(id, login);
        }

        public Builder(long id, String login, UserRole role) {
            user = new User(id, login, role);
        }

        public Builder(String login) {
            user = new User(login);
        }

        public Builder(String login, UserRole role) {
            user = new User(login, role);
        }

        public Builder buildLastname(String lastname) {
            user.setLastname(lastname);
            return this;
        }

        public Builder buildName(String name) {
            user.setName(name);
            return this;
        }

        public Builder buildPatronymic(String patronymic) {
            user.setPatronymic(patronymic);
            return this;
        }

        public Builder buildSex(User.Sex sex) {
            user.setSex(sex);
            return this;
        }

        public Builder buildState(User.State state) {
            user.setState(state);
            return this;
        }

        public Builder buildPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder buildPhone(String phone) {
            user.setPhone(phone);
            return this;
        }

        public Builder buildAddress(String address) {
            user.setAddress(address);
            return this;
        }

        public Builder buildBirthdayDate(LocalDate birthdayDate) {
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
                Objects.equals(lastname, user.lastname) &&
                Objects.equals(name, user.name) &&
                Objects.equals(patronymic, user.patronymic) &&
                Objects.equals(birthdayDate, user.birthdayDate) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(address, user.address) &&
                sex == user.sex &&
                state == user.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, role, password, lastname, name, patronymic, birthdayDate, phone, address, sex, state);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", lastName='" + lastname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", state='" + state + '\'' +
                ", birthdayDate=" + birthdayDate +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", sex=" + sex +
                '}';
    }
}
