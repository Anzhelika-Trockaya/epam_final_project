<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>
<fmt:message key="international_names.page_title" var="page_title"/>
<fmt:message key="international_names.title" var="title"/>
<fmt:message key="international_names.empty" var="empty_msg"/>
<fmt:message key="international_names.enter_international_name" var="enter_international_name"/>
<fmt:message key="action.add" var="add"/>
<fmt:message key="action.edit" var="edit"/>
<fmt:message key="action.delete" var="delete"/>
<fmt:message key="international_names.incorrect_international_name" var="msg_text_incorrect_intern_name"/>
<fmt:message key="international_names.international_name_already_exists" var="msg_text_intern_name_exists"/>
<html>
<head>
    <title>${page_title}</title>
</head>
<body>
<c:set var="current_page" value="jsp/pharmacist/international_names.jsp" scope="session"/>
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
    <c:if test="${empty international_names_list}">
        <h4>${empty_msg}</h4>
    </c:if>
    <br/>
    <table class="center_table">
        <tbody>
        <tr>
            <form name="add_intern_name_form" action="${context_path}/controller" method="post"
                  onsubmit="return validate(this['name'], 'incorrect_new_msg')">
                <input type="hidden" name="command" value="add_international_name"/>
                <td><input type="text" style="width: 400px" name="name" placeholder="${enter_international_name}" value="${name}"/></td>
                <td><input type="submit" value="${add}"></td>
                <td></td>
            </form>
        </tr>
        <tr>
            <td colspan="3"><p id="incorrect_new_msg" class="incorrect_data_msg"></p><br/></td>
        </tr>
        <c:forEach var="international_name" items="${international_names_list}">
            <tr>
                <form action="${context_path}/controller" method="post"
                      onsubmit="return validate(this['name'], 'incorrect_msg_${international_name.id}')">
                    <input type="hidden" name="command" value="edit_international_name">
                    <input type="hidden" name="international_name_id" value="${international_name.id}"/>
                    <td><input type="text" style="width: 400px" name="name" value="${international_name.internationalName}"/></td>
                    <td><input type="submit" value="${edit}"></td>
                </form>
                <form action="${context_path}/controller" method="post">
                    <input type="hidden" name="command" value="delete_international_name">
                    <input type="hidden" name="international_name_id" value="${international_name.id}"/>
                    <td><input type="submit" value="${delete}"></td>
                </form>
            </tr>
            <tr>
                <td colspan="3"><p id='incorrect_msg_${international_name.id}' class="incorrect_data_msg"></p><br/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script>
    const internationalNamePattern = /^[A-Z][a-zA-Z-' ]{0,99}$/;

    function validate(nameInput, msgPlaceId) {
        return validatePatternMismatch(nameInput, internationalNamePattern, msgPlaceId, "${msg_text_incorrect_intern_name}");
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
