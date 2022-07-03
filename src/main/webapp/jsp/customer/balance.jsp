<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="balance.page_title" var="page_title"/>
<fmt:message key="balance.account_balance" var="account_balance"/>
<fmt:message key="balance.deposit" var="deposit"/>
<fmt:message key="balance.failed_deposit" var="failed_deposit"/>
<fmt:message key="balance.enter_the_amount" var="enter_the_amount"/>
<fmt:message key="balance.incorrect_value" var="incorrect_value"/>
<html>
<head>
    <title>${page_title}</title>
    <c:set var="current_page" value="jsp/customer/balance.jsp" scope="session"/>
</head>
<body>
<div>
    <h3>${account_balance}: ${current_user_balance}BYN</h3>
    <c:if test="${not empty temp_successful_change_message}">
        <div><p class="successful_msg"><fmt:message key="${temp_successful_change_message}"/></p></div>
        <br>
    </c:if>
    <c:if test="${not empty failed_change_message}">
        <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
        <br>
    </c:if>
    <br>
    <form action="${context_path}/controller" method="post" onkeyup="validateKeyUp()"
          onsubmit="return validateSubmit()">
        <input type="hidden" name="command" value="deposit_to_user_account"/>
        <input id="value_input" type="text" name="value" placeholder="${enter_the_amount}"/>
        <input type="submit" value="${deposit}">
        <p id="failed_msg" class="incorrect_data_msg"></p>
    </form>
</div>
</body>
<script>
    var valueInput = document.getElementById('value_input');
    var previousValue = "";

    function validateSubmit() {
        const valuePattern = /^([1-9]\d?(\.\d{2})?|100(\.00)?)$/;
        if (!valuePattern.test(valueInput.value)) {
            document.getElementById('failed_msg').innerHTML = '${incorrect_value}';
            return false;
        }
        return true;
    }

    function validateKeyUp() {
        const numberPattern = /^(|([1-9]\d{0,2}(\.\d{0,2})?))$/;
        if (!numberPattern.test(valueInput.value)) {
            valueInput.value = previousValue;
        }
        previousValue = valueInput.value;
    }
</script>
</html>
