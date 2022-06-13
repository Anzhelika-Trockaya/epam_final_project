<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>
<fmt:message key="forms.page_title" var="page_title"/>
<fmt:message key="forms.title" var="title"/>
<fmt:message key="forms.empty" var="empty_msg"/>
<fmt:message key="forms.enter_form_name" var="enter_form_name"/>
<fmt:message key="forms.incorrect_form" var="msg_text_incorrect_form"/>
<fmt:message key="forms.form_already_exists" var="msg_text_form_exists"/>
<fmt:message key="forms.milliliters" var="ml"/>
<fmt:message key="forms.tables" var="tables"/>
<fmt:message key="forms.pieces" var="pieces"/>
<fmt:message key="forms.choose_unit" var="choose_unit"/>
<fmt:message key="action.add" var="add"/>
<fmt:message key="action.edit" var="edit"/>
<fmt:message key="action.delete" var="delete"/>

<html>
<head>
    <title>${page_title}</title>
</head>
<body>
<div>
    <h3>${title}</h3>
    <hr>
    <br/>
    <c:if test="${not empty successful_change_message}">
        <div><p class="successful_msg"><fmt:message key="${successful_change_message}"/></p></div>
        <br/>
    </c:if>
    <c:if test="${not empty failed_change_message}">
        <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
        <br/>
    </c:if>
    <c:if test="${empty forms_list}">
        <h4>${empty_msg}</h4>
    </c:if>
    <br/>
    <table class="center_table">
        <tbody>
        <tr>
            <form name="add_medicine_form_form" action="${context_path}/controller" method="post"
                  onsubmit="return validate(this, 'incorrect_name_msg', 'incorrect_unit_msg')">
                <input type="hidden" name="command" value="add_medicine_form"/>
                <td><input type="text" style="width: 400px" name="name" placeholder="${enter_form_name}"/></td>
                <td>
                    <select name="form_unit" size="1">
                        <option selected value="">-</option>
                        <option value="PIECES">${pieces}</option>
                        <option value="TABLES">${tables}</option>
                        <option value="MILLILITERS">${ml}</option>
                    </select>
                </td>
                <td><input type="submit" value="${add}"></td>
                <td></td>
            </form>
        </tr>
        <tr>
            <td colspan="4">
                <p id="incorrect_name_msg" class="incorrect_data_msg"></p>
                <p id="incorrect_unit_msg" class="incorrect_data_msg"></p>
            </td>
        </tr>
        <c:forEach var="form" items="${forms_list}">
            <tr>
                <form action="${context_path}/controller" method="post"
                      onsubmit="return validate(this, 'incorrect_name_${form.id}', 'incorrect_unit_${form.id}')">
                    <input type="hidden" name="command" value="edit_medicine_form">
                    <input type="hidden" name="form_id" value="${form.id}"/>
                    <td><input type="text" style="width: 400px" name="name" value="${form.name}"/></td>
                    <td>
                        <select name="form_unit" size="1">
                            <option selected value="">-</option>
                            <option <c:if test="${form.unit eq 'PIECES'}">selected</c:if> value="PIECES">
                                    ${pieces}
                            </option>
                            <option <c:if test="${form.unit eq 'TABLES'}">selected</c:if> value="TABLES">
                                    ${tables}
                            </option>
                            <option <c:if test="${form.unit eq 'MILLILITERS'}">selected</c:if> value="MILLILITERS">
                                    ${ml}
                            </option>
                        </select>
                    </td>
                    <td><input type="submit" value="${edit}"></td>
                </form>
                <form action="${context_path}/controller" method="post">
                    <input type="hidden" name="command" value="delete_medicine_form">
                    <input type="hidden" name="form_id" value="${form.id}"/>
                    <td><input type="submit" value="${delete}"></td>
                </form>
            </tr>
            <tr>
                <td colspan="4">
                    <p id='incorrect_name_${form.id}' class="incorrect_data_msg"></p>
                    <p id='incorrect_unit_${form.id}' class="incorrect_data_msg"></p>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script>
    function validate(form, nameMsgPlaceId, unitMsgPlaceId) {
        const formNamePattern = /^[a-zA-Zа-яА-ЯёЁ/,_:;.'"-]{1,100}$/;
        return (validatePatternMismatch(form["name"], formNamePattern, nameMsgPlaceId, "${msg_text_incorrect_form}") &&
            validateRequired(form["form_unit"], unitMsgPlaceId, "${choose_unit}"));
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

    function validateRequired(input, msgPlaceId, msg) {
        const value = input.value;
        if (value === "") {
            makeInputIncorrect(input, msgPlaceId, msg);
            return false;
        }
        makeInputCorrect(input, msgPlaceId);
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
