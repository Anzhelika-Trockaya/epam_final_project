<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="user.page_title" var="page_title"/>
<fmt:message key="user.change_password" var="change_pass"/>
<fmt:message key="user.old_password" var="label_old_password"/>
<fmt:message key="user.new_password" var="label_new_password"/>
<fmt:message key="user.repeat_new_password" var="label_repeat_new_password"/>
<fmt:message key="user.incorrect_old_password" var="msg_text_incorrect_old_password"/>
<fmt:message key="action.edit" var="edit"/>
<fmt:message key="action.save" var="save"/>
<fmt:message key="action.cancel" var="cancel"/>
<fmt:message key="users.lastname" var="lastname_title"/>
<fmt:message key="users.name" var="name_title"/>
<fmt:message key="users.patronymic" var="patronymic_title"/>
<fmt:message key="users.birthday_date" var="birthday_title"/>
<fmt:message key="users.sex" var="sex_title"/>
<fmt:message key="registration.male" var="title_male"/>
<fmt:message key="registration.female" var="title_female"/>
<fmt:message key="users.phone" var="phone_title"/>
<fmt:message key="users.address" var="address_title"/>
<fmt:message key="registration.repeat_password" var="label_repeat_password"/>
<fmt:message key="registration.incorrect_lastname" var="msg_text_incorrect_lastname"/>
<fmt:message key="registration.incorrect_name" var="msg_text_incorrect_name"/>
<fmt:message key="registration.incorrect_patronymic" var="msg_text_incorrect_patronymic"/>
<fmt:message key="registration.incorrect_phone" var="msg_text_incorrect_phone"/>
<fmt:message key="registration.incorrect_address" var="msg_text_incorrect_address"/>
<fmt:message key="registration.required_field" var="msg_text_required"/>
<fmt:message key="registration.incorrect_birthday_date" var="msg_text_incorrect_birthday_date"/>
<fmt:message key="registration.incorrect_password" var="msg_text_incorrect_new_password"/>
<fmt:message key="registration.incorrect_repeat_password" var="msg_text_incorrect_repeat_password"/>
<c:if test="${not empty user}">
    <c:set value="${user.lastname}" var="user_lastname"/>
    <c:set value="${user.name}" var="user_name"/>
    <c:set value="${user.patronymic}" var="user_patronymic"/>
    <c:set value="${user.sex}" var="user_sex"/>
    <c:set value="${user.birthdayDate}" var="user_birthday_date"/>
    <c:set value="${user.phone}" var="user_phone"/>
    <c:set value="${user.address}" var="user_address"/>
</c:if>
<html>
<head>
    <title>${page_title}</title>
    <c:set var="current_page" value="jsp/common/user.jsp" scope="session"/>
</head>
<body>
<br>
<c:if test="${not empty temp_successful_change_message}">
    <div><p class="successful_msg"><fmt:message key="${temp_successful_change_message}"/></p></div>
    <br>
</c:if>
<form id="user_info" <c:if test="${empty user}">hidden</c:if>>
    <h4>${user.lastname} ${user.name} ${user.patronymic}</h4>
    <p>${birthday_title}: ${user.birthdayDate}</p>
    <p>${sex_title}:
        <c:choose>
            <c:when test="${user.sex eq 'MALE'}">${title_male}</c:when>
            <c:when test="${user.sex eq 'FEMALE'}">${title_female}</c:when>
        </c:choose>
    </p>
    <p>${phone_title}: ${user.phone}</p>
    <p>${address_title}: ${user.address}</p>
    <input type="button" onclick="showEditForm(document.getElementById('edit_form'))" value="${edit}">
    <br><br>
    <input type="button" onclick="showEditForm(document.getElementById('change_password_form'))" value="${change_pass}">
</form>
<form id="edit_form"
      <c:if test="${empty show_edit_form}">hidden</c:if> action="${context_path}/controller" method="post"
      onsubmit="return validateEditForm()">
    <input type="hidden" name="command" value="edit_user_data"/>
    <label for="lastname">${lastname_title}</label>
    <input type="text" id="lastname" name="user_lastname" value="${user_lastname}"/>
    <p id="incorrect_lastname_msg" class="incorrect_data_msg">
        <c:if test="${not empty incorrect_lastname}">
            <fmt:message key="${incorrect_lastname}"/>
        </c:if>
    </p>
    <br>
    <label for="name">${name_title}</label>
    <input type="text" id="name" name="user_name" value="${user_name}"/>
    <p id="incorrect_name_msg" class="incorrect_data_msg">
        <c:if test="${not empty incorrect_name}">
            <fmt:message key="${incorrect_name}"/>
        </c:if>
    </p>
    <br>
    <label for="patronymic">${patronymic_title}</label>
    <input type="text" id="patronymic" name="user_patronymic" value="${user_patronymic}"/>
    <p id="incorrect_patronymic_msg" class="incorrect_data_msg">
        <c:if test="${not empty incorrect_patronymic}">
            <fmt:message key="${incorrect_patronymic}"/>
        </c:if>
    </p>
    <br>
    <label for="sex">${sex_title}</label>
    <select id="sex" name="user_sex" size="1">
        <option id="default" selected value="">-</option>
        <option
                <c:if test="${user_sex eq 'FEMALE'}">selected</c:if> value="FEMALE">${title_female}</option>
        <option
                <c:if test="${user_sex eq 'MALE'}">selected</c:if> value="MALE">${title_male}</option>
    </select>
    <p id="incorrect_sex_msg" class="incorrect_data_msg">
        <c:if test="${not empty incorrect_sex}">
            <fmt:message key="${incorrect_sex}"/>
        </c:if>
    </p>
    <br>
    <label for="birthday_date">${birthday_title}</label>
    <input type="date" id="birthday_date" name="user_birthday_date" value="${user_birthday_date}">
    <p id="incorrect_birthday_date_msg" class="incorrect_data_msg">
        <c:if test="${not empty incorrect_birthday_date}">
            <fmt:message key="${incorrect_birthday_date}"/>
        </c:if>
    </p>
    <br>
    <label for="phone">${phone_title}</label>
    <input type="text" id="phone" name="user_phone" value="${not empty user_phone? user_phone:"+375"}">
    <p id="incorrect_phone_msg" class="incorrect_data_msg">
        <c:if test="${not empty incorrect_phone}">
            <fmt:message key="${incorrect_phone}"/>
        </c:if>
    </p>
    <br>
    <label for="address">${address_title}</label>
    <textarea id="address" name="user_address" style="width: 500px; height: 100px">${user_address}</textarea>
    <p id="incorrect_address_msg" class="incorrect_data_msg">
        <c:if test="${not empty incorrect_address}">
            <fmt:message key="${incorrect_address}"/>
        </c:if>
    </p>
    <br>
    <input type="submit" form="refresh" value="${cancel}"/>
    <input type="submit" value="${save}"/>
</form>
<form id="change_password_form"
      <c:if test="${empty show_change_password_form}">hidden</c:if> action="${context_path}/controller" method="post"
      onsubmit="return validateChangePasswordForm()">
    <input type="hidden" name="command" value="change_password"/>
    <label for="password">${label_old_password}</label>
    <input type="password" id="password" name="old_password" value="${old_password}"/>
    <p id="incorrect_old_password_msg" class="incorrect_data_msg">
        <c:if test="${not empty incorrect_old_password}">
            ${msg_text_incorrect_old_password}
        </c:if>
    </p>
    <br>
    <label for="password">${label_new_password}</label>
    <input type="password" id="password" name="new_password" value="${new_password}"/>
    <p id="incorrect_new_password_msg" class="incorrect_data_msg">
        <c:if test="${not empty incorrect_new_password}">
            ${msg_text_incorrect_new_password}
        </c:if>
    </p>
    <br>
    <label for="repeat_password">${label_repeat_new_password}</label>
    <input type="password" id="repeat_password" name="repeat_password" value="${repeat_password}"/>
    <p id="incorrect_repeat_password_msg" class="incorrect_data_msg">
        <c:if test="${not empty incorrect_repeat_password}">
            ${msg_text_incorrect_repeat_password}
        </c:if>
    </p>
    <br>
    <input type="submit" form="refresh" value="${cancel}"/>
    <input type="submit" value="${save}"/>
</form>
<form id="refresh" action="${context_path}/jsp/common/user.jsp"></form>
</body>
<script>
    const userInfo = document.getElementById('user_info');
    const passwordPattern =
        /^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-zа-яёіў])(?=.*[A-ZА-ЯЁІЎ])[0-9a-zA-Zа-яА-ЯёЁІіЎў!@#$%^&*]{6,45}$/;
    const namePattern = /^[A-Za-zА-Яа-яёЁІіЎў][A-Za-zА-Яа-яёЁІіЎў-]{0,44}$/;
    const phonePattern = /^\+375(33|29|25|44)\d{7}$/;
    const addressPattern = /^[a-zA-Z0-9а-яА-ЯёЁўІіЎ\s/,_:;.'"-]+$/;
    const lastnameInput = document.forms["edit_form"]["user_lastname"];
    const nameInput = document.forms["edit_form"]["user_name"];
    const patronymicInput = document.forms["edit_form"]["user_patronymic"];
    const sexInput = document.forms["edit_form"]["user_sex"];
    const phoneInput = document.forms["edit_form"]["user_phone"];
    const addressInput = document.forms["edit_form"]["user_address"];
    const birthdayDateInput = document.forms["edit_form"]["user_birthday_date"];
    const oldPasswordInput = document.forms["change_password_form"]["old_password"];
    const newPasswordInput = document.forms["change_password_form"]["new_password"];
    const repeatPasswordInput = document.forms["change_password_form"]["repeat_password"];

    function showEditForm(form) {
        userInfo.hidden = true;
        form.hidden = false;
    }

    function validateEditForm() {
        let result = true;
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
        if (!(validateRequired(addressInput, "incorrect_address_msg", "${msg_text_required}") &&
            validatePatternMismatch(addressInput, addressPattern, "incorrect_address_msg", "${msg_text_incorrect_address}"))) {
            result = false;
        }
        if (!validateRequired(sexInput, "incorrect_sex_msg", "${msg_text_required}")) {
            result = false;
        }
        if (!(validateRequired(birthdayDateInput, "incorrect_birthday_date_msg", "${msg_text_required}") &&
            validateBirthdayDate(birthdayDateInput))) {
            result = false;
        }
        return result;
    }

    function validateChangePasswordForm() {
        let result = true;
        if (!(validateRequired(oldPasswordInput, "incorrect_old_password_msg", "${msg_text_required}") &&
            validatePatternMismatch(oldPasswordInput,
                passwordPattern, "incorrect_old_password_msg", "${msg_text_incorrect_old_password}"))) {
            result = false;
        }
        if (!(validateRequired(newPasswordInput, "incorrect_new_password_msg", "${msg_text_required}") &&
            validatePatternMismatch(newPasswordInput,
                passwordPattern, "incorrect_new_password_msg", "${msg_text_incorrect_new_password}"))) {
            result = false;
        }
        if(!validatePasswordRepeat()){
            result=false;
        }
        return result;
    }

    function validateBirthdayDate(birthdayDateInput) {
        const dateValue = new Date(birthdayDateInput.value);
        const now = new Date();
        const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
        const birthdayDateThisYear = new Date(today.getFullYear(), dateValue.getMonth(), dateValue.getDate());
        let age = today.getFullYear() - dateValue.getFullYear();
        if (today < birthdayDateThisYear) {
            age = age - 1;
        }
        if (age > 17) {
            makeInputCorrect(birthdayDateInput, "incorrect_birthday_date_msg");
            return true;
        } else {
            makeInputIncorrect(birthdayDateInput, "incorrect_birthday_date_msg", "${msg_text_incorrect_birthday_date}");
            return false;
        }
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
    }

    function makeInputCorrect(input, msgPlaceId) {
        document.getElementById(msgPlaceId).innerHTML = "";
    }

    function validatePasswordRepeat() {
        const passwordValue = newPasswordInput.value;
        const repeatPasswordValue = repeatPasswordInput.value;
        if (passwordValue !== repeatPasswordValue) {
            makeInputIncorrect(repeatPasswordInput, "incorrect_repeat_password_msg", "${msg_text_incorrect_repeat_password}");
            return false;
        }
        makeInputCorrect(repeatPasswordInput, "incorrect_repeat_password_msg");
        return true;
    }

</script>
</html>
