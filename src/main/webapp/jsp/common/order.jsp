<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="order.order_page_title" var="order_page_title"/>
<fmt:message key="order.title" var="order_label"/>
<fmt:message key="order.creation_date" var="creation_date_label"/>
<fmt:message key="order.state" var="state_label"/>
<fmt:message key="order.completed" var="completed"/>
<fmt:message key="order.in_progress" var="in_progress"/>
<fmt:message key="action.back" var="back"/>
<fmt:message key="cart.total_cost" var="total_cost_label"/>
<fmt:message key="users.address" var="address"/>
<fmt:message key="users.phone" var="phone"/>
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
<fmt:message key="medicines.prescription" var="prescription_title"/>
<fmt:message key="medicines.expiration_date" var="exp_date"/>
<fmt:message key="medicines.tab_or_ml" var="tab_or_ml"/>
<html>
<head>
    <title>${order_page_title}</title>
    <c:set var="current_page" value="jsp/common/order.jsp" scope="session"/>
</head>
<body>
<div>
    <c:if test="${not empty failed_change_message}">
        <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
        <br>
    </c:if>
    <p>${order_label}№${temp_order.id}</p>
    <p>${creation_date_label}:${temp_order.paymentDate}, ${state_label}:
        <c:choose>
            <c:when test="${temp_order.state eq 'IN_PROGRESS'}">
                <span style="color: red">${in_progress}</span>
            </c:when>
            <c:when test="${temp_order.state eq 'COMPLETED'}">
                <span style="color: darkblue">${completed}</span>
            </c:when>
        </c:choose>
    </p>
    <hr>
    <br>
    <c:if test="${!(current_user_role eq 'CUSTOMER')}">
        <p><b>${temp_customer.lastname} ${temp_customer.name} ${temp_customer.patronymic}</b></p>
        <p><b>${phone}:</b>${temp_customer.phone}</p>
        <p><b>${address}:</b>${temp_customer.address}</p>
        <hr>
        <br>
    </c:if>
    <div>
        <table>
            <tbody>
            <c:forEach var="data_map" items="${temp_order_content_list}">
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
                <c:set var="position" value="${data_map.get(\"position\")}"/>
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
                    </td>
                    <td>
                        <c:if test="${!(prescription_id eq '0')}">
                            <p>${prescription_title} №${prescription_id}</p>
                            <p>${exp_date}: ${prescription.expirationDate}</p>
                        </c:if>
                    </td>
                    <td>
                        <h4><b><c:out value="${position.price * position.quantity}"/> BYN</b></h4>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <hr>
    <h3>${total_cost_label}: ${temp_order.totalCost}</h3>
    <form action="${context_path}/jsp/common/orders.jsp">
        <input type="submit" value="${back}"/>
    </form>
    <c:if test="${!(current_user_role eq 'CUSTOMER' || temp_order.state eq 'COMPLETED')}">
        <form action="${context_path}/controller" method="post">
            <input type="hidden" name="command" value="complete_order"/>
            <input type="hidden" name="order_id" value="${temp_order.id}"/>
            <input type="submit" value="${completed}"/>
        </form>
    </c:if>
</div>
</body>
</html>
