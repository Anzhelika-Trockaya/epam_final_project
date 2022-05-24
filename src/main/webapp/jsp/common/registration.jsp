<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="registration.page_title" var="registration_title"/>
<fmt:message key="registration.add_user_title" var="add_user_title"/>
<fmt:message key="registration.login" var="label_login"/>
<fmt:message key="registration.password" var="label_password"/>
<fmt:message key="registration.lastname" var="label_lastname"/>
<fmt:message key="registration.name" var="label_name"/>
<fmt:message key="registration.patronymic" var="label_patronymic"/>
<fmt:message key="registration.sex" var="label_sex"/>
<fmt:message key="registration.male" var="title_male"/>
<fmt:message key="registration.female" var="title_female"/>
<fmt:message key="registration.role" var="label_role"/>
<fmt:message key="registration.admin" var="title_admin"/>
<fmt:message key="registration.pharmacist" var="title_pharmacist"/>
<fmt:message key="registration.doctor" var="title_doctor"/>
<fmt:message key="registration.customer" var="title_customer"/>
<fmt:message key="registration.birthday_date" var="label_birthday_date"/>
<fmt:message key="registration.phone" var="label_phone"/>
<fmt:message key="registration.address" var="label_address"/>
<fmt:message key="registration.repeat_password" var="label_repeat_password"/>
<fmt:message key="registration.btn" var="register_btn_text"/>
<fmt:message key="registration.incorrect_login" var="msg_text_incorrect_login"/>
<fmt:message key="registration.incorrect_password" var="msg_text_incorrect_password"/>
<fmt:message key="registration.incorrect_lastname" var="msg_text_incorrect_lastname"/>
<fmt:message key="registration.incorrect_name" var="msg_text_incorrect_name"/>
<fmt:message key="registration.incorrect_patronymic" var="msg_text_incorrect_patronymic"/>
<fmt:message key="registration.incorrect_phone" var="msg_text_incorrect_phone"/>
<fmt:message key="registration.incorrect_address" var="msg_text_incorrect_address"/>
<fmt:message key="registration.required_field" var="msg_text_required"/>
<fmt:message key="registration.incorrect_repeat_password" var="msg_text_incorrect_repeat_pass"/>

<html>
<head>
    <meta charset="utf-8">
    <title><c:choose>
        <c:when test="${current_user_role eq 'ADMIN'}">${add_user_title}</c:when>
        <c:otherwise>${registration_title}</c:otherwise>
    </c:choose></title>
    <c:set var="current_page" value="jsp/common/registration.jsp" scope="session"/>
</head>
<body>
<div class="registration_form">
    <form name="registration_form" id="registration_form" action="${context_path}/controller" method="post"
          onsubmit="return validate()">
        <input type="hidden" name="command" value="register"/>
        <c:if test="${current_user_role eq 'ADMIN'}">
            <c:if test="${not empty successful_registration}">
                <br/>
                <div class="successful_msg"><fmt:message key="users.successful_msg"/></div>
                <br/>
                <br/>
            </c:if>
            <label for="role">${label_role}</label>
            <select id="role" name="role" size="1">
                <option id="default_role" selected value="">-</option>
                <option
                        <c:if test="${user_role eq 'ADMIN'}">selected</c:if> value="ADMIN">${title_admin}</option>
                <option
                        <c:if test="${user_role eq 'DOCTOR'}">selected</c:if> value="DOCTOR">${title_doctor}</option>
                <option
                        <c:if test="${user_role eq 'PHARMACIST'}">selected</c:if>
                        value="PHARMACIST">${title_pharmacist}</option>
                <option
                        <c:if test="${user_role eq 'CUSTOMER'}">selected</c:if>
                        value="CUSTOMER">${title_customer}</option>
            </select>
            <p id="incorrect_role_msg" class="incorrect_data_msg"></p>
            <c:if test="${not empty incorrect_role}">
                <p class="incorrect_data_msg"><fmt:message key="${incorrect_role}"/></p>
            </c:if>
            <br/>
        </c:if>
        <label for="lastname">${label_lastname}</label>
        <input type="text" id="lastname" name="lastname" value="${user_lastname}"/>
        <p id="incorrect_lastname_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_lastname}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_lastname}"/></p>
        </c:if>
        <br/>
        <label for="name">${label_name}</label>
        <input type="text" id="name" name="name" value="${user_name}"/>
        <p id="incorrect_name_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_name}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_name}"/></p>
        </c:if>
        <br/>
        <label for="patronymic">${label_patronymic}</label>
        <input type="text" id="patronymic" name="patronymic" value="${user_patronymic}"/>
        <p id="incorrect_patronymic_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_patronymic}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_patronymic}"/></p>
        </c:if>
        <br/>
        <label for="sex">${label_sex}</label>
        <select id="sex" name="sex" size="1">
            <option id="default" selected value="">-</option>
            <option
                    <c:if test="${user_sex eq 'FEMALE'}">selected</c:if> value="FEMALE">${title_female}</option>
            <option
                    <c:if test="${user_sex eq 'MALE'}">selected</c:if> value="MALE">${title_male}</option>
        </select>
        <p id="incorrect_sex_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_sex}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_sex}"/></p>
        </c:if>
        <br/>
        <label for="birthday_date">${label_birthday_date}</label>
        <input type="date" id="birthday_date" name="birthday_date" value="${user_birthday_date}">
        <p id="incorrect_birthday_date_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_birthday_date}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_birthday_date}"/></p>
        </c:if>
        <br/>
        <label for="phone">${label_phone}</label>
        <input type="text" id="phone" name="phone" value="${not empty user_phone? user_phone:"+375"}">
        <p id="incorrect_phone_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_phone}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_phone}"/></p>
        </c:if>
        <br/>
        <label for="address">${label_address}</label>
        <textarea id="address" name="address" maxlength="100">${user_address}</textarea>
        <p id="incorrect_address_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_address}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_address}"/></p>
        </c:if>
        <br/>
        <label for="login">${label_login}</label>
        <input type="text" id="login" name="login" value="${user_login}"/>
        <p id="incorrect_login_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_login}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_login}"/></p>
        </c:if>
        <br/>
        <label for="password">${label_password}</label>
        <input type="password" id="password" name="password" value="${user_password}"/>
        <p id="incorrect_password_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_password}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_password}"/></p>
        </c:if>
        <br/>
        <label for="repeat_password">${label_repeat_password}</label>
        <input type="password" id="repeat_password" name="repeat_password" value="${repeat_password}"/>
        <p id="incorrect_repeat_password_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_repeat_password}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_repeat_password}"/></p>
        </c:if>
        <br/>
        <input type="submit" name="sub" value="${register_btn_text}"/>
        <br/>
    </form>
</div>
</body>
<script>    function validate() {
    const loginPattern = /^[a-zA-Z0-9а-яА-ЯёЁ._-]{4,45}$/;
    const passwordPattern = /^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,45}$/;
    const namePattern = /^[A-Za-zА-Яа-яёЁ][A-Za-zА-Яа-яёЁ-]{0,44}$/;
    const phonePattern = /^\+375(33|29|25|44)\d{7}$/;
    const loginInput = document.forms["registration_form"]["login"];
    const passwordInput = document.forms["registration_form"]["password"];
    const lastnameInput = document.forms["registration_form"]["lastname"];
    const nameInput = document.forms["registration_form"]["name"];
    const patronymicInput = document.forms["registration_form"]["patronymic"];
    const sexInput = document.forms["registration_form"]["sex"];
    const phoneInput = document.forms["registration_form"]["phone"];
    const addressInput = document.forms["registration_form"]["address"];
    const birthdayDateInput = document.forms["registration_form"]["birthday_date"];
    let result = true;
    if (${current_user_role eq 'ADMIN'}) {
        const roleInput = document.forms["registration_form"]["role"];
        if (!validateRequired(roleInput, "incorrect_role_msg", "${msg_text_required}")) {
            result = false;
        }
    }
    if (!(validateRequired(loginInput, "incorrect_login_msg", "${msg_text_required}") &&
        validatePatternMismatch(loginInput, loginPattern, "incorrect_login_msg", "${msg_text_incorrect_login}"))) {
        result = false;
    }
    if (!(validateRequired(passwordInput, "incorrect_password_msg", "${msg_text_required}") &&
        validatePatternMismatch(passwordInput, passwordPattern, "incorrect_password_msg", "${msg_text_incorrect_password}"))) {
        result = false;
    }
    if (!(validateRequired(lastnameInput, "incorrect_lastname_msg", "${msg_text_required}") &&
        validatePatternMismatch(lastnameInput, namePattern, "incorrect_lastname_msg", "${msg_text_incorrect_lastname}"))) {
        result = false;
    }
    if (!(validateRequired(nameInput, "incorrect_name_msg", "${msg_text_required}") &&
        validatePatternMismatch(nameInput, namePattern, "incorrect_name_msg", "${msg_text_incorrect_name}"))) {
        result = false;
    }
    if (!(validateRequired(patronymicInput, "incorrect_patronymic_msg", "${msg_text_required}") &&
        validatePatternMismatch(patronymicInput, namePattern, "incorrect_patronymic_msg", "${msg_text_incorrect_patronymic}"))) {
        result = false;
    }
    if (!(validateRequired(phoneInput, "incorrect_phone_msg", "${msg_text_required}") &&
        validatePatternMismatch(phoneInput, phonePattern, "incorrect_phone_msg", "${msg_text_incorrect_phone}"))) {
        result = false;
    }
    if (!validateRequired(sexInput, "incorrect_sex_msg", "${msg_text_required}")) {
        result = false;
    }
    if (!validateRequired(birthdayDateInput, "incorrect_birthday_date_msg", "${msg_text_required}")) {
        result = false;
    }
    if (!validateRequired(addressInput, "incorrect_address_msg", "${msg_text_required}")) {
        result = false;
    }
    if (!validatePasswordRepeat(passwordInput)) {
        result = false;
    }
    return result;
}

function validateRequired(input, msgPlaceId, msg) {
    const value = input.value;
    if (value === "") {
        makeInputIncorrect(input, msgPlaceId, msg);
        return false;
    }
    makeInputCorrect(input, msgPlaceId);
    return true;
}

function validatePatternMismatch(input, pattern, msgPlaceId, msg) {
    const value = input.value;
    if (!pattern.test(value)) {
        makeInputIncorrect(input, msgPlaceId, msg);
        return false;
    }
    makeInputCorrect(input, msgPlaceId);
    return true;
}

function makeInputIncorrect(input, msgPlaceId, msg) {
    document.getElementById(msgPlaceId).innerHTML = msg;
    input.setAttribute("class", "red-input");
}

function makeInputCorrect(input, msgPlaceId) {
    document.getElementById(msgPlaceId).innerHTML = "";
    input.setAttribute("class", "");
}

function validatePasswordRepeat(passwordInput) {
    const passwordValue = passwordInput.value;
    const repeatPasswordInput = document.forms["registration_form"]["repeat_password"];
    const repeatPasswordValue = repeatPasswordInput.value;
    if (passwordValue !== repeatPasswordValue) {
        makeInputIncorrect(repeatPasswordInput, "incorrect_repeat_password_msg", "${msg_text_incorrect_repeat_pass}");
        return false;
    }
    makeInputCorrect(repeatPasswordInput, "incorrect_repeat_password_msg");
    return true;
}</script>
</html>

