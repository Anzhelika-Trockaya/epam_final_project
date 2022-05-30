<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <c:set var="current_page" value="jsp/common/sign_in.jsp" scope="session"/>
    <fmt:message key="sign_in.incorrect_data_msg" var="incorrect_data_msg"/>
    <title><fmt:message key="sign_in.page_title"/></title>
</head>
<body>
<div class="login_form">
    <c:if test="${not empty successful_registration}">
        <p><fmt:message key="registration.successful_msg"/></p>
    </c:if>
    <form name="sign_in_form" action="${context_path}/controller" method="post" onsubmit="return validate()">
        <input type="hidden" name="command" value="sign_in"/>
        <div>
            <label for="login"><fmt:message key="login"/></label>
            <input type="text" id="login" value="${login}" name="login"/>
        </div>
        <br/>
        <div>
            <label for="password"><fmt:message key="password"/></label>
            <input type="password" id="password" value="${password}" name="password"/>
        </div>
        <br/>
        <p id="incorrect_login_data_msg" class="incorrect_data_msg">
            <c:if test="${not empty failed}">
                ${incorrect_data_msg}
            </c:if>
        </p>
        <input type="submit" class="btn" value="<fmt:message key="sign_in"/>"/>
        <br/>
    </form>
    <a href="${context_path}/jsp/common/registration.jsp"><fmt:message key="sign_in.go_to_registration_page"/></a>
</div>
</body>
</html>
<script type="text/javascript">
    function validate() {
        const loginPattern = /[a-zA-Z0-9а-яА-ЯёЁ._-]{4,45}/;
        const passwordPattern = /(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,45}/;
        const loginValue = document.forms["sign_in_form"]["login"].value;
        const passwordValue = document.forms["sign_in_form"]["password"].value;
        return validateRequired(loginValue) && validatePatternMismatch(loginValue, loginPattern) &&
            validateRequired(passwordValue) && validatePatternMismatch(passwordValue, passwordPattern);
    }
    function validateRequired(value) {
        if (value === "") {
            document.getElementById("incorrect_login_data_msg").innerHTML = "${incorrect_data_msg}";
            return false;
        }
        return true;
    }
    function validatePatternMismatch(value, pattern) {
        if (!pattern.test(value)) {
            document.getElementById("incorrect_login_data_msg").innerHTML = "${incorrect_data_msg}";
            return false;
        }
        return true;
    }
</script>
