<%--
  Created by IntelliJ IDEA.
  User: Анжелика
  Date: 10.04.2022
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <c:set var="failed_msg" value="Incorrect login or password!"/>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${context_path}/css/style.css">
    <title>Pharmacy | Sign In</title>
</head>
<body>
<header>
    <%@include file="../header/header.jsp" %>
</header>
<div class="login_form">
    <c:if test="${not empty sessionScope.successful_registration}">
        <p><fmt:message key="registration.successful_msg"/></p>
        <c:remove var="successful_registration" scope="session"/>
    </c:if>
    <form name="sign_in_form" action="${context_path}/controller"
          onsubmit="return validate()" novalidate>
        <input type="hidden" name="command" value="sign_in"/>
        <div>
            <label>Login</label>
            <input type="text" id="login" name="login" required pattern="[a-zA-Z0-9а-яА-ЯёЁ._-]{4,45}"/>
        </div>
        </br>
        <div>
            <label>Password</label>
            <input type="password" id="password" name="password" required pattern="
                   (?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-zа-яё])(?=.*[A-ZА-ЯЁ])[A-ZА-ЯЁa-zа-яё0-9!@#$%^&*]{6,45}"/>
        </div>
        <br/>
        <p id="incorrect_login_data_msg" class="incorrect_data_msg">
            <c:if test="${not empty failed}">
                ${failed_msg}
            </c:if>
        </p>
        <input type="submit" class="btn" value="Sign In"/>
        <br/>
        <script type="text/javascript">
            function validate() {
                const loginValue = document.forms["sign_in_form"]["login"].value;
                const passwordValue = document.forms["sign_in_form"]["password"].value;
                if (loginValue === "" || passwordValue === "" ||
                    loginValue.validity.patternMismatch || passwordValue.validity.patternMismatch) {
                    document.getElementById("incorrect_login_data_msg").innerHTML = "${failed_msg}";
                    return false;
                }
                return true;
            }
        </script>
    </form>
    <a href="${context_path}/jsp/common/registration.jsp">Go to registration page</a>
</div>
</body>
</html>
