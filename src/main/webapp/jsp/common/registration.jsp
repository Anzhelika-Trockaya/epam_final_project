<%--
  Created by IntelliJ IDEA.
  User: Анжелика
  Date: 02.05.2022
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="context_path" value="${pageContext.request.contextPath}"/>

<fmt:setBundle basename="context.pagecontent"/>

<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'en_US'}" scope="session"/></c:when>
</c:choose>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Registration</title>
</head>
<body>
<c:import url="../header/header.jsp"/>
<form name="registration_form" action="${context_path}/controller" onsubmit="return validate()" novalidate>
    <input type="hidden" name="command" value="register"/>
    <label>Lastname*:</label>
    <input type="text" name="lastname" value="" required
           pattern="[A-Za-zА-Яа-яёЁ][A-Za-zА-Яа-яёЁ-]{4,45}"/>
    <p id="incorrect_lastname_msg" class="incorrect_data_msg"></p>
    <br/>
    <label>Name*: </label>
    <input type="text" name="name" value="" required pattern="[A-Za-zА-Яа-яёЁ][A-Za-zА-Яа-яёЁ-]{6,44}"/>
    <p id="incorrect_name_msg" class="incorrect_data_msg"></p>
    <br/>
    <label>Patronymic*:</label>
    <input type="text" name="patronymic" value="" required
           pattern="[A-Za-zА-Яа-яёЁ][A-Za-zА-Яа-яёЁ-]{0,44}"/>
    <p id="incorrect_patronymic_msg" class="incorrect_data_msg"></p>
    <br/>
    <label>Sex*:</label>
    <select name="sex" size="1" required>
        <option disabled selected value="">-</option>
        <option value="FEMALE">FEMALE</option>
        <option value="MALE">MALE</option>
    </select>
    <p id="incorrect_sex_msg" class="incorrect_data_msg"></p>
    <br/>
    <label>Birthday date*:</label>
    <input type="date" name="birthday_date" required>
    <p id="incorrect_birthday_date_msg" class="incorrect_data_msg"></p>
    <br/>
    <label>Phone number* (+375XXXXXXXXX):</label>
    <input type="text" name="phone" value="+375" required pattern="\+375(33|29|25|44)\d{7}">
    <p id="incorrect_phone_msg" class="incorrect_data_msg"></p>
    <br/>
    <label>Address*:</label>
    <textarea type="text" name="address" value="" maxlength="100" required></textarea>
    <p id="incorrect_address_msg" class="incorrect_data_msg"></p>
    <br/>
    <label>Login*:</label>
    <input type="text" name="login" value="" required pattern="[a-zA-Zа-яА-ЯёЁ0-9._-]{4,45}"/>
    <p id="incorrect_login_msg" class="incorrect_data_msg"></p>
    <br/>
    <label>Password*:</label>
    <input type="password" name="password" value="" required
           pattern="(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,45}"/>
    <p id="incorrect_password_msg" class="incorrect_data_msg"></p>
    <br/>
    <label for="repeat_pass">Repeat password*:</label>
    <input id="repeat_pass" type="password" name="password" value="" required/>
    <p id="incorrect_repeat_pass_msg" class="incorrect_data_msg"></p>
    <br/>
    <input type="submit" name="sub" value="Register"/>
    <br/>
    <script type="text/javascript">
        function validate() {
            const loginValue = document.forms["registration_form"]["login"].value;
            const passwordValue = document.forms["registration_form"]["password"].value;

            const loginMsgId = "incorrect_login_msg";
            const passwordMsgId = "incorrect_password_msg";

            var result=validateRequired(loginValue, loginMsgId);
            result=result&&validateRequired(passwordValue, passwordMsgId);
            result=result&&validatePattern(loginValue, loginMsgId, <fmt:message key="registration.incorrect_login"/>);
            result=result&&validatePattern(passwordValue, passwordMsgId, <fmt:message key="registration.incorrect_password"/>);
            return result;
        }

        function validateRequired(value, msgElementId) {
            if (value === "") {
                document.getElementById(msgElementId).innerHTML = <fmt:message key="registration.required_field"/>;
                return false;
            }
            document.getElementById(msgElementId).innerHTML = "";
            return true;
        }

        function validatePattern(value, msgElementId, msg) {
            if (value.validity.patternMismatch) {
                document.getElementById(msgElementId).innerHTML = msg;
                return false;
            }
            return true;
        }
    </script>
</form>
</body>
</html>
