package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The type User.
 */
public class User extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -3995979375755262375L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private String login;
    private UserRole role;
    private String password;
    private String lastname;
    private String name;
    private String patronymic;
    private State state;
    private LocalDate birthdayDate;
    private Sex sex;
    private String phone;
    private String address;

    /**
     * The enum user sex.
     */
    public enum Sex {
        /**
         * Male sex.
         */
        MALE,
        /**
         * Female sex.
         */
        FEMALE
    }

    /**
     * The enum user state
     */
    public enum State {
        /**
         * State active.
         */
        ACTIVE,
        /**
         * State blocked.
         */
        BLOCKED
    }

    /**
     * Instantiates a new User with default id value.
     */
    public User() {
        id = DEFAULT_ID;
    }

    /**
     * Instantiates a new User.
     *
     * @param id    user id
     * @param login user login
     */
    public User(long id, String login) {
        this.id = id;
        this.login = login;
        this.role = UserRole.CUSTOMER;
    }

    /**
     * Instantiates a new User.
     *
     * @param id    user id
     * @param login user login
     * @param role  user role
     */
    public User(long id, String login, UserRole role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    /**
     * Instantiates a new User.
     *
     * @param login user login
     */
    public User(String login) {
        id = DEFAULT_ID;
        this.login = login;
        this.role = UserRole.CUSTOMER;
    }

    /**
     * Instantiates a new User.
     *
     * @param login user login
     * @param role  user role
     */
    public User(String login, UserRole role) {
        id = DEFAULT_ID;
        this.login = login;
        this.role = role;
    }

    /**
     * Gets user id.
     *
     * @return the user id.
     */
    public long getId() {
        return id;
    }

    /**
     * Gets user login.
     *
     * @return the user login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets user login.
     *
     * @param login the user login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets user role.
     *
     * @return the user role.
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets user role.
     *
     * @param role the user role.
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Gets user password.
     *
     * @return the user password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user password.
     *
     * @param password the user password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets user lastname.
     *
     * @return the user lastname.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets user lastname.
     *
     * @param lastname the user lastname.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Gets user name.
     *
     * @return the user name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets user name.
     *
     * @param name the user name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets user patronymic.
     *
     * @return the user patronymic.
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Sets user patronymic.
     *
     * @param patronymic the user patronymic.
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Gets user birthday date.
     *
     * @return the user birthday date.
     */
    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    /**
     * Sets user birthday date.
     *
     * @param birthdayDate the user birthday date.
     */
    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    /**
     * Gets user sex.
     *
     * @return the user sex.
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Sets user sex.
     *
     * @param sex the user sex.
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * Gets user phone.
     *
     * @return the user phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets user phone.
     *
     * @param phone the user phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets user address.
     *
     * @return the user address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets user address.
     *
     * @param address the user address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets user state.
     *
     * @return the user state.
     */
    public State getState() {
        return state;
    }

    /**
     * Sets user state.
     *
     * @param state the user state.
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * The type Builder. Used for building new User object.
     */
    public static class Builder {
        private final User user;

        /**
         * Instantiates a new user Builder.
         *
         * @param id    user id
         * @param login user login
         */
        public Builder(long id, String login) {
            user = new User(id, login);
        }

        /**
         * Instantiates a new user Builder.
         *
         * @param id    user id
         * @param login user login
         * @param role  user role
         */
        public Builder(long id, String login, UserRole role) {
            user = new User(id, login, role);
        }

        /**
         * Instantiates a new user Builder.
         *
         * @param login user login
         */
        public Builder(String login) {
            user = new User(login);
        }

        /**
         * Instantiates a new user Builder.
         *
         * @param login user login
         * @param role  user role
         */
        public Builder(String login, UserRole role) {
            user = new User(login, role);
        }

        /**
         * Builds the user lastname.
         *
         * @param lastname the user lastname.
         * @return the builder.
         */
        public Builder buildLastname(String lastname) {
            user.setLastname(lastname);
            return this;
        }

        /**
         * Builds the user name.
         *
         * @param name the user name.
         * @return the builder.
         */
        public Builder buildName(String name) {
            user.setName(name);
            return this;
        }

        /**
         * Builds the user patronymic.
         *
         * @param patronymic the user patronymic.
         * @return the builder.
         */
        public Builder buildPatronymic(String patronymic) {
            user.setPatronymic(patronymic);
            return this;
        }

        /**
         * Builds the user sex.
         *
         * @param sex the user sex.
         * @return the builder.
         */
        public Builder buildSex(User.Sex sex) {
            user.setSex(sex);
            return this;
        }

        /**
         * Builds the user state.
         *
         * @param state the user state.
         * @return the builder.
         */
        public Builder buildState(User.State state) {
            user.setState(state);
            return this;
        }

        /**
         * Builds the user password.
         *
         * @param password the user password.
         * @return the builder.
         */
        public Builder buildPassword(String password) {
            user.setPassword(password);
            return this;
        }

        /**
         * Builds the user phone.
         *
         * @param phone the user phone.
         * @return the builder.
         */
        public Builder buildPhone(String phone) {
            user.setPhone(phone);
            return this;
        }

        /**
         * Builds the user address.
         *
         * @param address the user address.
         * @return the builder.
         */
        public Builder buildAddress(String address) {
            user.setAddress(address);
            return this;
        }

        /**
         * Builds the user birthday date.
         *
         * @param birthdayDate the user birthday date.
         * @return the builder.
         */
        public Builder buildBirthdayDate(LocalDate birthdayDate) {
            user.setBirthdayDate(birthdayDate);
            return this;
        }

        /**
         * Builds new User object.
         *
         * @return new User object.
         */
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
