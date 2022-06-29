<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="medicines.page_title" var="medicines_page_title"/>
<fmt:message key="medicines.title" var="medicines_title"/>
<fmt:message key="medicines.empty_msg" var="empty_msg"/>
<fmt:message key="medicines.add_medicine" var="add_medicine"/>
<fmt:message key="action.edit" var="edit"/>
<fmt:message key="medicines.total_number" var="total_number"/>
<fmt:message key="medicines.dosage" var="dosage_title"/>
<fmt:message key="medicines.form" var="med_form_title"/>
<fmt:message key="medicines.manufacturer" var="manufacturer_title"/>
<fmt:message key="medicines.milliliter" var="ml"/>
<fmt:message key="medicines.milligram" var="mg"/>
<fmt:message key="medicines.gram" var="g"/>
<fmt:message key="medicines.microgram" var="mcg"/>
<fmt:message key="medicines.me" var="me"/>
<fmt:message key="medicines.nanogram" var="ng"/>
<fmt:message key="medicines.need_prescription_msg" var="need_prescription_msg"/>
<fmt:message key="medicines.add_to_cart" var="add_to_cart"/>
<fmt:message key="medicines.not_found" var="not_found_msg"/>
<fmt:message key="medicines.enter_medicine_name" var="enter_medicine_name"/>
<fmt:message key="medicines.added_to_cart" var="medicine_added_to_cart"/>
<fmt:message key="action.search" var="search"/>
<fmt:message key="action.reset" var="reset_search_results"/>
<fmt:message key="adding_medicine.international_name" var="label_international_name"/>
<fmt:message key="adding_medicine.form" var="label_form"/>
<fmt:message key="adding_medicine.dosage" var="label_dosage"/>
<fmt:message key="medicines.milliliter" var="unit_name_ml"/>
<fmt:message key="medicines.milligram" var="unit_name_mg"/>
<fmt:message key="medicines.gram" var="unit_name_g"/>
<fmt:message key="medicines.microgram" var="unit_name_mcg"/>
<fmt:message key="medicines.me" var="unit_name_me"/>
<fmt:message key="medicines.nanogram" var="unit_name_ng"/>
<fmt:message key="medicines.out_of_stock" var="out_of_stock"/>
<fmt:message key="medicines.go_to_prescriptions" var="go_to_prescriptions"/>
<fmt:message key="medicines.prescription" var="prescription_title"/>
<fmt:message key="medicines.expiration_date" var="expiration_date"/>
<fmt:message key="medicines.available" var="available"/>
<fmt:message key="medicines.tab_or_ml" var="tab_or_ml"/>
<html>
<head>
    <title>${medicines_page_title}</title>
    <c:set var="current_page" value="jsp/common/medicines.jsp" scope="session"/>
</head>
<body>
<div>
    <div>
        <h3>${medicines_title}</h3>
        <hr>
        <br>
        <c:if test="${!(current_user_role eq 'CUSTOMER')}">
            <form action="${context_path}/jsp/pharmacist/adding_medicine.jsp">
                <input type="submit" value="${add_medicine}"/>
            </form>
            <hr>
            <br>
        </c:if>
        <c:if test="${not empty temp_successful_change_message}">
            <div><p class="successful_msg"><fmt:message key="${temp_successful_change_message}"/></p></div>
            <br>
        </c:if>
        <c:if test="${not empty failed_change_message}">
            <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
            <br>
        </c:if>
        <c:choose>
            <c:when test="${empty show_prescriptions_medicines}">
                <form action="${context_path}/controller">
                    <input type="hidden" name="command" value="search_medicines"/>
                    <input type="text" name="medicine_name" placeholder="${enter_medicine_name}"
                           value="${medicine_name}"/>
                    <label for="international_name">${label_international_name}</label>
                    <select id="international_name" name="medicine_international_name_id" size="1">
                        <option id="default_international_name" selected value="">-</option>
                        <c:forEach var="international_name" items="${international_names_list}">
                            <option
                                    <c:if test="${medicine_international_name_id eq international_name.id}">selected</c:if>
                                    value="${international_name.id}">
                                    ${international_name.internationalName}
                            </option>
                        </c:forEach>
                    </select>
                    <label for="form">${label_form}</label>
                    <select id="form" name="medicine_form_id" size="1">
                        <option id="default_form" selected value="">-</option>
                        <c:forEach var="form" items="${forms_list}">
                            <option
                                    <c:if test="${medicine_form_id eq form.id}">selected</c:if> value="${form.id}">
                                    ${form.name}
                            </option>
                        </c:forEach>
                    </select>
                    <br><br>
                    <label for="dosage">${label_dosage}</label>
                    <input type="text" id="dosage" name="medicine_dosage" value="${medicine_dosage}"/>
                    <select id="dosage_unit" name="medicine_dosage_unit" size="1">
                        <option id="default_dosage_unit" selected value="">-</option>
                        <option
                                <c:if test="${medicine_dosage_unit eq 'MILLILITER'}">selected</c:if> value="MILLILITER">
                                ${unit_name_ml}
                        </option>
                        <option
                                <c:if test="${medicine_dosage_unit eq 'MILLIGRAM'}">selected</c:if> value="MILLIGRAM">
                                ${unit_name_mg}
                        </option>
                        <option
                                <c:if test="${medicine_dosage_unit eq 'GRAM'}">selected</c:if> value="GRAM">
                                ${unit_name_g}
                        </option>
                        <option
                                <c:if test="${medicine_dosage_unit eq 'MICROGRAM'}">selected</c:if> value="MICROGRAM">
                                ${unit_name_mcg}
                        </option>
                        <option
                                <c:if test="${medicine_dosage_unit eq 'ME'}">selected</c:if> value="ME">
                                ${unit_name_me}
                        </option>
                        <option
                                <c:if test="${medicine_dosage_unit eq 'NANOGRAM'}">selected</c:if> value="NANOGRAM">
                                ${unit_name_ng}
                        </option>
                    </select>
                    <br><br>
                    <input type="submit" name="sub" value="${search}"/>
                    <input type="submit" form="reset_search_results_form" value="${reset_search_results}"/>
                    <br>
                </form>
                <form id="reset_search_results_form" action="${context_path}/jsp/common/medicines.jsp"></form>
                <hr>
                <br/>
            </c:when>
            <c:otherwise>
                <p>${prescription_title} № ${prescription_id} ${expiration_date}: ${prescription_expiration_date}.</p>
                <p>${available}: ${prescription_available_quantity} ${tab_or_ml}</p>
            </c:otherwise>
        </c:choose>
        <c:if test="${empty medicines_data_map}">
            <c:choose>
                <c:when test="${not empty show_search_result}">
                    <p class="successful_msg">${not_found_msg}</p>
                </c:when>
                <c:otherwise>
                    <h4>${empty_msg}</h4>
                </c:otherwise>
            </c:choose>
        </c:if>
        <div>
            <table>
                <tbody>
                <c:forEach var="entry" items="${medicines_data_map}">
                    <c:set var="medicine_id" value="${entry.getKey()}"/>
                    <c:set var="data_map" value="${entry.getValue()}"/>
                    <c:set var="medicine" value="${data_map.get(\"medicine\")}"/>
                    <c:set var="manufacturer" value="${data_map.get(\"manufacturer\")}"/>
                    <c:set var="form" value="${data_map.get(\"form\")}"/>
                    <c:set var="international_name" value="${data_map.get(\"international_name\")}"/>
                    <tr>
                        <td><img src="${context_path}/uploadImage?image_path=${medicine.imagePath}" height="216"
                                 width="297"
                                 alt="no image"></td>
                        <td>
                            <p class="medicine_name_link">
                                <b> <c:out value="${medicine.name}"/>
                                    <c:if test="${medicine.needPrescription()}">
                                        <span>${need_prescription_msg}</span>
                                    </c:if>
                                </b>
                            </p>
                            <p><i>${international_name.internationalName}</i></p>
                            <p>${dosage_title} <c:out value="${medicine.dosage}"/>
                                <c:choose>
                                    <c:when test="${medicine.dosageUnit eq 'MILLILITER'}">${ml}</c:when>
                                    <c:when test="${medicine.dosageUnit eq 'MILLIGRAM'}">${mg}</c:when>
                                    <c:when test="${medicine.dosageUnit eq 'GRAM'}">${g}</c:when>
                                    <c:when test="${medicine.dosageUnit eq 'MICROGRAM'}">${mcg}</c:when>
                                    <c:when test="${medicine.dosageUnit eq 'ME'}">${me}</c:when>
                                    <c:when test="${medicine.dosageUnit eq 'NANOGRAM'}">${ng}</c:when>
                                </c:choose>
                            </p>
                            <p>${med_form_title} ${form.name}
                                <c:choose>
                                    <c:when test="${form.unit eq 'MILLILITERS'}">
                                        <c:out value="${medicine.numberInPackage}"/>${ml}
                                    </c:when>
                                    <c:otherwise>
                                        №<c:out value="${medicine.numberInPackage}"/>
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            <p>${manufacturer_title} ${manufacturer.name}(${manufacturer.country})</p>
                            <c:choose>
                                <c:when test="${current_user_role eq 'CUSTOMER'}">
                                    <form id="add_to_cart_form_${medicine_id}" action="${context_path}/controller"
                                          method="post">
                                        <input type="hidden" name="command" value="add_medicine_to_cart"/>
                                        <input type="hidden" name="order_medicine_id" value="${medicine_id}"/>
                                        <input type="hidden" name="prescription_id" value="${prescription_id}"/>
                                        <div class="numbers">
                                            <button type="button" id="${medicine_id}_btn_minus"
                                                    onclick="minus(${medicine_id})">
                                                -
                                            </button>
                                            <input type="text" value="1" onclick="this.select()"
                                                   onkeyup="validate(${medicine_id}, ${medicine.totalPackages})"
                                                   id="${medicine_id}_item_number" name="order_medicine_number"
                                                   style="width: 30px"/>
                                            <button type="button" id="${medicine_id}_btn_plus"
                                                    onclick="plus(${medicine_id},
                                                        ${medicine.totalPackages})">+
                                            </button>
                                        </div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <p>${total_number} <c:out value="${medicine.totalPackages}"/></p>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${medicine.totalPackages > 0}">
                                    <h2><b><c:out value="${medicine.price}"/> BYN</b></h2>
                                    <c:if test="${current_user_role eq 'CUSTOMER'}">
                                        <input type="submit" form="add_to_cart_form_${medicine_id}"
                                               <c:if test="${medicine.needPrescription() &&
                                               (empty show_prescriptions_medicines)}">disabled</c:if>
                                               value="${add_to_cart}"/>
                                        <c:if test="${medicine.needPrescription()&&
                                               (empty show_prescriptions_medicines)}">
                                            <p class="successful_msg">${go_to_prescriptions}</p>
                                        </c:if>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <h4>${out_of_stock}</h4>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${!(current_user_role eq 'CUSTOMER')}">
                                <form action="${context_path}/controller">
                                    <input type="hidden" name="command" value="go_edit_medicine_page"/>
                                    <input type="hidden" name="medicine_id" value="${medicine_id}"/>
                                    <input type="submit" value="${edit}"/>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<script>
    function minus(medicineId) {
        var itemNumber = document.getElementById(medicineId + '_item_number');
        var value = parseInt(itemNumber.value);
        value = value - 1;
        if (value > 0) {
            itemNumber.value = value;
        }
    }

    function plus(medicineId, max) {
        var itemNumber = document.getElementById(medicineId + '_item_number');
        var value = parseInt(itemNumber.value);
        value = value + 1;
        if (value <= max) {
            itemNumber.value = value;
        }
    }

    function validate(medicineId, totalNumber) {
        const numberPattern = /^\d{1,6}$/;
        var itemNumber = document.getElementById(medicineId + '_item_number');
        if (!numberPattern.test(itemNumber.value)) {
            itemNumber.value = 1;
        } else {
            var value = parseInt(itemNumber.value);
            if (value < 1) {
                itemNumber.value = 1;
            } else if (value > totalNumber) {
                itemNumber.value = totalNumber;
            }
        }
    }
</script>
</html>
