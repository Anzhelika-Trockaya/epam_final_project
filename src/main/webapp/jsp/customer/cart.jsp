<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="cart.page_title" var="cart_page_title"/>
<fmt:message key="cart.title" var="cart_title"/>
<fmt:message key="cart.empty_msg" var="empty_msg"/>
<fmt:message key="cart.available" var="available"/>
<fmt:message key="cart.prescription_invalid" var="prescription_invalid_msg"/>
<fmt:message key="cart.order" var="order_btn"/>
<fmt:message key="cart.total_cost" var="total_cost_label"/>
<fmt:message key="cart.pack" var="pack"/>
<fmt:message key="cart.prescription_available_quantity" var="prescr_available_quant"/>
<fmt:message key="action.delete" var="delete"/>
<fmt:message key="action.clear" var="clear"/>
<fmt:message key="medicines.milliliter" var="ml"/>
<fmt:message key="medicines.milligram" var="mg"/>
<fmt:message key="medicines.gram" var="g"/>
<fmt:message key="medicines.microgram" var="mcg"/>
<fmt:message key="medicines.me" var="me"/>
<fmt:message key="medicines.nanogram" var="ng"/>
<fmt:message key="medicines.milliliter" var="unit_name_ml"/>
<fmt:message key="medicines.milligram" var="unit_name_mg"/>
<fmt:message key="medicines.gram" var="unit_name_g"/>
<fmt:message key="medicines.microgram" var="unit_name_mcg"/>
<fmt:message key="medicines.me" var="unit_name_me"/>
<fmt:message key="medicines.nanogram" var="unit_name_ng"/>
<fmt:message key="medicines.need_prescription_msg" var="need_prescription_msg"/>
<fmt:message key="medicines.out_of_stock" var="out_of_stock_msg"/>
<fmt:message key="medicines.prescription" var="prescription_title"/>
<fmt:message key="medicines.expiration_date" var="exp_date"/>
<fmt:message key="medicines.tab_or_ml" var="tab_or_ml"/>
<html>
<head>
    <title>${cart_page_title}</title>
    <c:set var="current_page" value="jsp/customer/cart.jsp" scope="session"/>
</head>
<body>
<div>
    <h3>${cart_title}</h3>
    <c:if test="${!(empty cart_content_list)}">
        <form action="${context_path}/controller">
            <input type="hidden" name="command" value="clear_cart"/>
            <input type="submit" value="${clear}"/>
        </form>
    </c:if>
    <hr>
    <br>
    <c:if test="${not empty temp_successful_change_message}">
        <div><p class="successful_msg"><fmt:message key="${temp_successful_change_message}"/></p></div>
        <br>
    </c:if>
    <c:if test="${not empty failed_change_message}">
        <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
        <br>
    </c:if>
    <div>
        <table>
            <tbody>
            <c:choose>
                <c:when test="${empty cart_content_list}">
                    <p>${empty_msg}</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="data_map" items="${cart_content_list}">
                        <c:set var="medicine" value="${data_map.get(\"medicine\")}"/>
                        <c:set var="medicine_id" value="${medicine.id}"/>
                        <c:set var="manufacturer" value="${data_map.get(\"manufacturer\")}"/>
                        <c:set var="form" value="${data_map.get(\"form\")}"/>
                        <c:set var="international_name" value="${data_map.get(\"international_name\")}"/>
                        <c:set var="prescription" value="${data_map.get(\"prescription\")}"/>
                        <c:set var="prescription_id" value="0"/>
                        <c:if test="${!(empty prescription)}">
                            <c:set var="prescription_id" value="${prescription.id}"/>
                        </c:if>
                        <c:set var="quantity" value="${data_map.get(\"quantity\")}"/>
                        <c:set var="out_of_stock" value="${!(data_map.get(\"out_of_stock\") eq null)}"/>
                        <c:set var="available_quantity" value="${data_map.get(\"available_quantity\")}"/>
                        <c:set var="invalid_prescription" value="${!(data_map.get(\"invalid_prescription\") eq null)}"/>
                        <tr>
                            <td><img src="${context_path}/uploadImage?image_path=${medicine.imagePath}" height="108"
                                     width="148" alt="no image"></td>
                            <td>
                                <p class="medicine_name_link">
                                    <b>
                                        <c:out value="${medicine.name}"/>(
                                        <i>${international_name.internationalName}</i>,
                                        <c:out value="${medicine.dosage}"/>
                                        <c:choose>
                                            <c:when test="${medicine.dosageUnit eq 'MILLILITER'}">${ml}</c:when>
                                            <c:when test="${medicine.dosageUnit eq 'MILLIGRAM'}">${mg}</c:when>
                                            <c:when test="${medicine.dosageUnit eq 'GRAM'}">${g}</c:when>
                                            <c:when test="${medicine.dosageUnit eq 'MICROGRAM'}">${mcg}</c:when>
                                            <c:when test="${medicine.dosageUnit eq 'ME'}">${me}</c:when>
                                            <c:when test="${medicine.dosageUnit eq 'NANOGRAM'}">${ng}</c:when>
                                        </c:choose>,
                                            ${form.name},
                                        <c:choose>
                                            <c:when test="${form.unit eq 'MILLILITERS'}">
                                                <c:out value="${medicine.numberInPackage}"/>${ml}
                                            </c:when>
                                            <c:otherwise>
                                                №<c:out value="${medicine.numberInPackage}"/>
                                            </c:otherwise>
                                        </c:choose>
                                        )
                                        <c:if test="${medicine.needPrescription()}">
                                            <span>${need_prescription_msg}</span>
                                        </c:if>
                                    </b>
                                </p>
                                <p>${manufacturer_title} ${manufacturer.name}(${manufacturer.country})</p>
                                <c:if test="${!(out_of_stock)}">
                                    <form action="${context_path}/controller" method="post">
                                        <input type="hidden" name="command" value="change_medicine_quantity_in_cart"/>
                                        <input type="hidden" name="medicine_id" value="${medicine_id}"/>
                                        <input type="hidden" name="prescription_id" value="${prescription_id}"/>
                                        <div class="numbers">
                                            <button type="submit" name="minus"
                                                    onclick="minusAndSubmit(this.form)">
                                                -
                                            </button>
                                            <input type="text" name="quantity" onclick="this.select()"
                                                   value="${quantity}"
                                                   onkeyup="validatePattern(this)"
                                                   onchange="validateAndSubmit(this.form,
                                                       ${medicine.totalPackages},
                                                       ${quantity})"
                                                   style="width: 30px"/>
                                            <button type="button" name="plus"
                                                    onclick="plusAndSubmit(this.form, ${medicine.totalPackages})">
                                                +
                                            </button>
                                        </div>
                                    </form>
                                </c:if>
                                <p class="incorrect_data_msg">
                                    <c:if test="${not empty available_quantity}">
                                        ${available} ${available_quantity} ${pack}
                                    </c:if>
                                    <c:if test="${out_of_stock}">
                                        ${out_of_stock_msg}
                                    </c:if>
                                </p>
                            </td>
                            <td>
                                <c:if test="${!(prescription_id eq '0')}">
                                    <p>${prescription_title} №${prescription_id}</p>
                                    <p>${exp_date}: ${prescription.expirationDate}</p>
                                    <c:choose>
                                        <c:when test="${invalid_prescription}">
                                            <p class="incorrect_data_msg">${prescription_invalid_msg}</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p>${prescr_available_quant}:
                                                    ${prescription.quantity-prescription.soldQuantity} ${tab_or_ml}</p>

                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${!out_of_stock}">
                                        <h4><b><c:out value="${medicine.price * quantity}"/> BYN</b></h4>
                                    </c:when>
                                    <c:otherwise>
                                        <p>-</p>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form action="${context_path}/controller" method="post">
                                    <input type="hidden" name="command" value="delete_position_from_cart"/>
                                    <input type="hidden" name="medicine_id" value="${medicine_id}"/>
                                    <input type="hidden" name="prescription_id" value="${prescription_id}"/>
                                    <input type="submit" value="${delete}"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
    <hr>
    <h3>${total_cost_label}: ${total_cost}</h3>
    <form action="${context_path}/controller">
        <input type="hidden" name="command" value="order"/>
        <input type="hidden" name="total_cost" value="${total_cost}"/>
        <input type="submit" value="${order_btn}" <c:if test="${!is_correct_order}">disabled</c:if>/>
    </form>
</div>
</body>
<script>
    function minusAndSubmit(form) {
        var quantityInput = form["quantity"];
        var value = parseInt(quantityInput.value);
        value = value - 1;
        if (value > 0) {
            quantityInput.value = value;
            form.submit();
        }
    }

    function plusAndSubmit(form, max) {
        var quantityInput = form["quantity"];
        var value = parseInt(quantityInput.value);
        value = value + 1;
        if (value <= max) {
            quantityInput.value = value;
            form.submit();
        }
    }

    function validatePattern(quantityInput) {
        const numberPattern = /^[1-9]\d{0,5}$/;
        if (!numberPattern.test(quantityInput.value)) {
            quantityInput.value = 1;
        }
    }

    function validateAndSubmit(form, max, quantity) {
        var quantityInput = form["quantity"];
        var value = parseInt(quantityInput.value);
        if (value > max) {
            quantityInput.value = max;
        }
        if (value !== quantity) {
            form.submit();
        }
    }
</script>
</html>
