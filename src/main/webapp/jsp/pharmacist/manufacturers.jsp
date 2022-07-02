<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>
<fmt:message key="manufacturers.page_title" var="page_title"/>
<fmt:message key="manufacturers.title" var="title"/>
<fmt:message key="manufacturers.empty" var="empty_msg"/>
<fmt:message key="manufacturers.enter_name" var="enter_name"/>
<fmt:message key="manufacturers.enter_country" var="enter_country"/>
<fmt:message key="manufacturers.incorrect_name" var="msg_text_incorrect_name"/>
<fmt:message key="manufacturers.incorrect_country" var="msg_text_incorrect_country"/>
<fmt:message key="manufacturers.manufacturer_already_exists" var="msg_text_manufacturer_exists"/>
<fmt:message key="action.add" var="add"/>
<fmt:message key="action.edit" var="edit"/>
<fmt:message key="action.delete" var="delete"/>

<html>
<head>
    <title>${page_title}</title>
</head>
<body>
<c:set var="current_page" value="jsp/pharmacist/manufacturers.jsp" scope="session"/>
<div>
    <h3>${title}</h3>
    <hr>
    <br/>
    <c:if test="${not empty temp_successful_change_message}">
        <div><p class="successful_msg"><fmt:message key="${temp_successful_change_message}"/></p></div>
        <br/>
    </c:if>
    <c:if test="${not empty failed_change_message}">
        <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
        <br/>
    </c:if>
    <c:if test="${empty manufacturers_list}">
        <h4>${empty_msg}</h4>
    </c:if>
    <br/>
    <table class="center_table">
        <tbody>
        <tr>
            <form name="add_manufacturer_form" action="${context_path}/controller" method="post"
                  onsubmit="return (validate(this['name'],'incorrect_name_msg') &&
                          validate(this['country'],'incorrect_country_msg'))">
                <input type="hidden" name="command" value="add_manufacturer"/>
                <td><input type="text" style="width: 400px" name="name" placeholder="${enter_name}"/></td>
                <td><input type="text" style="width: 400px" name="country" placeholder="${enter_country}"/></td>
                <td><input type="submit" value="${add}"></td>
                <td></td>
            </form>
        </tr>
        <tr>
            <td colspan="4">
                <p id="incorrect_name_msg" class="incorrect_data_msg"></p>
                <p id="incorrect_country_msg" class="incorrect_data_msg"></p>
            </td>
        </tr>
        <c:forEach var="manufacturer" items="${manufacturers_list}">
            <tr>
                <form action="${context_path}/controller" method="post"
                      onsubmit="return (validate(this['name'],'incorrect_name_${manufacturer.id}') &&
                              validate(this['country'],'incorrect_country_${manufacturer.id}'))">
                    <input type="hidden" name="command" value="edit_manufacturer">
                    <input type="hidden" name="manufacturer_id" value="${manufacturer.id}"/>
                    <td><input type="text" style="width: 400px" name="name" value="${manufacturer.name}"/></td>
                    <td><input type="text" style="width: 400px" name="country" value="${manufacturer.country}"/></td>
                    <td><input type="submit" value="${edit}"></td>
                </form>
                <form action="${context_path}/controller" method="post">
                    <input type="hidden" name="command" value="delete_manufacturer">
                    <input type="hidden" name="manufacturer_id" value="${manufacturer.id}"/>
                    <td><input type="submit" value="${delete}"></td>
                </form>
            </tr>
            <tr>
                <td colspan="4">
                    <p id='incorrect_name_${manufacturer.id}' class="incorrect_data_msg"></p>
                    <p id='incorrect_country_${manufacturer.id}' class="incorrect_data_msg"></p>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script>
    const namePattern = /^[A-ZА-ЯЁІЎ][a-zA-Z0-9а-яА-ЯёЁіІўЎ/,_:;.'"&\s-]{0,44}$/;
    const countryPattern = /^[A-ZА-ЯЁІЎ][a-zA-Zа-яА-ЯёЁіІўЎ.'\s-]{0,44}$/;
    function validateName(input, msgPlace) {
        return validatePatternMismatch(input, namePattern, msgPlace, "${msg_text_incorrect_name}");
    }
    function validateCountry(input, msgPlace) {
        return validatePatternMismatch(input, countryPattern, msgPlace, "${msg_text_incorrect_country}");
    }

    function validatePatternMismatch(input, pattern, msgPlaceId, msg) {
        const value = input.value;
        if (!pattern.test(value)) {
            makeInputIncorrect(msgPlaceId, msg);
            return false;
        }
        makeInputCorrect(msgPlaceId);
        return true;
    }

    function makeInputIncorrect(msgPlaceId, msg) {
        document.getElementById(msgPlaceId).innerHTML = msg;
    }

    function makeInputCorrect(msgPlaceId) {
        document.getElementById(msgPlaceId).innerHTML = "";
    }
</script>
</html>
