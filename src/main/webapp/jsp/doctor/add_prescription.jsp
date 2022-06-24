<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="add_prescription.page_title" var="customers_page_title"/>
<fmt:message key="add_prescription.prescription" var="prescription_title"/>
<fmt:message key="add_prescription.month" var="month"/>
<fmt:message key="action.save" var="save"/>
<fmt:message key="action.cancel" var="cancel"/>
<fmt:message key="prescriptions.customer_name_title" var="customer_name_title"/>
<fmt:message key="users.birthday_date" var="birthday_title"/>
<fmt:message key="users.sex" var="sex_title"/>
<fmt:message key="registration.male" var="title_male"/>
<fmt:message key="registration.female" var="title_female"/>
<fmt:message key="adding_medicine.international_name" var="international_name_title"/>
<fmt:message key="adding_medicine.form" var="form_title"/>
<fmt:message key="prescriptions.quantity_title" var="quantity_title"/>
<fmt:message key="prescriptions.dosage_title" var="dosage_title"/>
<fmt:message key="prescriptions.expiration_date_title" var="exp_date_title"/>
<fmt:message key="medicines.milliliter" var="unit_name_ml"/>
<fmt:message key="medicines.milligram" var="unit_name_mg"/>
<fmt:message key="medicines.gram" var="unit_name_g"/>
<fmt:message key="medicines.microgram" var="unit_name_mcg"/>
<fmt:message key="medicines.me" var="unit_name_me"/>
<fmt:message key="medicines.nanogram" var="unit_name_ng"/>
<fmt:message key="adding_medicine.incorrect_required" var="incorrect_required_msg_text"/>
<fmt:message key="adding_medicine.incorrect_not_integer" var="incorrect_integer_msg_text"/>
<html>
<head>
    <title>${customers_page_title}</title>
    <c:set var="current_page" value="jsp/doctor/add_prescription.jsp" scope="session"/>
</head>
<body>
<div>
    <h3>${prescription_title}</h3>
    <br>
    <c:if test="${not empty failed_change_message}">
        <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
        <br/>
    </c:if>
    <p>${customer_name_title}: ${customer_lastname} ${customer_name} ${customer_patronymic}</p>
    <p>${birthday_title}: ${customer_birthday}</p>
    <p>
        ${sex_title}:
        <c:choose>
            <c:when test="${customer_sex eq 'MALE'}">${title_male}</c:when>
            <c:when test="${customer_sex eq 'FEMALE'}">${title_female}</c:when>
        </c:choose>
    </p>
    <form id="add_prescription_form" action="${context_path}/controller" method="post" onsubmit="return validate()">
        <input type="hidden" name="command" value="add_prescription"/>
        <input type="hidden" name="prescription_customer_id" value="${prescription_customer_id}">
        <input type="hidden" name="customer_lastname" value="${customer_lastname}"/>
        <input type="hidden" name="customer_name" value="${customer_name}"/>
        <input type="hidden" name="customer_patronymic" value="${customer_patronymic}"/>
        <input type="hidden" name="customer_birthday" value="${customer_birthday}"/>
        <input type="hidden" name="customer_sex" value="${customer_sex}"/>
        <br>
        <label for="prescription_international_name">${international_name_title}</label>
        <select id="prescription_international_name" name="prescription_international_name_id" size="1">
            <option id="default_international_name" selected value="">-</option>
            <c:forEach var="international_name" items="${international_names_list}">
                <option
                        <c:if test="${prescription_international_name_id eq international_name.id}">selected</c:if>
                        value="${international_name.id}">
                        ${international_name.internationalName}
                </option>
            </c:forEach>
        </select>
        <p id="incorrect_international_name_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_international_name}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_international_name}"/></p>
        </c:if>
        <br>
        <label for="prescription_form">${form_title}</label>
        <select id="prescription_form" name="prescription_form_id" size="1">
            <option id="default_form" selected value="">-</option>
            <c:forEach var="form" items="${forms_list}">
                <option
                        <c:if test="${prescription_form_id eq form.id}">selected</c:if> value="${form.id}">
                        ${form.name}
                </option>
            </c:forEach>
        </select>
        <p id="incorrect_form_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_form}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_form}"/></p>
        </c:if>
        <br>
        <label for="dosage">${dosage_title}</label>
        <input type="number" id="dosage" name="prescription_dosage" value="${prescription_dosage}"/>
        <select id="dosage_unit" name="prescription_dosage_unit" size="1">
            <option id="default_dosage_unit" selected value="">-</option>
            <option
                    <c:if test="${prescription_dosage_unit eq 'MILLILITER'}">selected</c:if> value="MILLILITER">
                ${unit_name_ml}
            </option>
            <option
                    <c:if test="${prescription_dosage_unit eq 'MILLIGRAM'}">selected</c:if> value="MILLIGRAM">
                ${unit_name_mg}
            </option>
            <option
                    <c:if test="${prescription_dosage_unit eq 'GRAM'}">selected</c:if> value="GRAM">
                ${unit_name_g}
            </option>
            <option
                    <c:if test="${prescription_dosage_unit eq 'MICROGRAM'}">selected</c:if> value="MICROGRAM">
                ${unit_name_mcg}
            </option>
            <option
                    <c:if test="${prescription_dosage_unit eq 'ME'}">selected</c:if> value="ME">
                ${unit_name_me}
            </option>
            <option
                    <c:if test="${prescription_dosage_unit eq 'NANOGRAM'}">selected</c:if> value="NANOGRAM">
                ${unit_name_ng}
            </option>
        </select>
        <p id="incorrect_dosage_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_dosage}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_dosage}"/></p>
        </c:if>
        <c:if test="${not empty incorrect_dosage_unit}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_dosage_unit}"/></p>
        </c:if>
        <br>
        <label for="prescription_quantity">${quantity_title}</label>
        <input type="number" id="prescription_quantity" name="prescription_quantity" value="${prescription_quantity}"/>
        <p id="incorrect_quantity_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_quantity}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_quantity}"/></p>
        </c:if>
        <br>
        <label for="validity">${exp_date_title}</label>
        <select id="validity" name="prescription_validity" size="1">
            <option id="default_validity" selected value="1">
                1 ${month}
            </option>
            <option
                    <c:if test="${prescription_validity eq '2'}">selected</c:if> value="2">
                2 ${month}
            </option>
        </select>
        <c:if test="${not empty incorrect_validity}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_validyty}"/></p>
        </c:if>
        <br><br>
        <input type="submit" value="${save}"/>  <input type="submit" form="cancel_form" value="${cancel}"/>
    </form>
    <form id="cancel_form" action="${context_path}/controller" method="post">
        <input type="hidden" name="command" value="go_customers_page"/>
    </form>
</div>
</body>
<script>
    function validate() {
        const intPattern = /^[1-9]\d{0,8}$/;
        const internationalNameInput = document.forms["add_prescription_form"]["prescription_international_name"];
        const formInput = document.forms["add_prescription_form"]["prescription_form"];
        const dosageInput = document.forms["add_prescription_form"]["prescription_dosage"];
        const unitInput = document.forms["add_prescription_form"]["prescription_dosage_unit"];
        const quantityInput = document.forms["add_prescription_form"]["prescription_quantity"];
        let result = true;
        if (!validateRequired(internationalNameInput, "incorrect_international_name_msg", "${incorrect_required_msg_text}")) {
            result = false;
        }
        if (!validateRequired(formInput, "incorrect_form_msg", "${incorrect_required_msg_text}")) {
            result = false;
        }
        if (!(validateRequired(dosageInput, "incorrect_dosage_msg", "${incorrect_required_msg_text}") &&
            validatePatternMismatch(dosageInput, intPattern, "incorrect_dosage_msg", "${incorrect_integer_msg_text}") &&
            validateRequired(unitInput, "incorrect_dosage_msg", "${incorrect_required_msg_text}"))) {
            result = false;
        }
        if (!(validateRequired(quantityInput, "incorrect_quantity_msg", "${incorrect_required_msg_text}") &&
            validatePatternMismatch(quantityInput, intPattern, "incorrect_quantity_msg", "${incorrect_integer_msg_text}"))) {
            result = false;
        }
        return result;
    }

    function validateRequired(input, msgPlaceId, msg) {
        const value = input.value;
        if (value === "") {
            makeInputIncorrect(msgPlaceId, msg);
            return false;
        }
        makeInputCorrect(msgPlaceId);
        return true;
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
