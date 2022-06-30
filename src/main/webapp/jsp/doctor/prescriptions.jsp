<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>
<fmt:message key="prescriptions.page_title" var="prescriptions_page_title"/>
<fmt:message key="prescriptions.empty_message" var="empty_msg"/>
<fmt:message key="prescriptions.prescriptions" var="prescriptions_title"/>
<fmt:message key="prescriptions.renewal_btn" var="renewal_btn"/>
<fmt:message key="prescriptions.request_renewal_btn" var="request_renewal_btn"/>
<fmt:message key="prescriptions.doctor_name_title" var="doctor_name_title"/>
<fmt:message key="prescriptions.customer_name_title" var="customer_name_title"/>
<fmt:message key="prescriptions.birthday_date_title" var="birthday_date_title"/>
<fmt:message key="prescriptions.international_name_title" var="international_name_title"/>
<fmt:message key="prescriptions.form_name_title" var="form_name_title"/>
<fmt:message key="prescriptions.dosage_title" var="dosage_title"/>
<fmt:message key="prescriptions.quantity_title" var="quantity_title"/>
<fmt:message key="prescriptions.sold_quantity_title" var="sold_quantity_title"/>
<fmt:message key="prescriptions.expiration_date_title" var="expiration_date_title"/>
<fmt:message key="prescriptions.show_renewal_requests" var="show_renewal_requests_title"/>
<fmt:message key="prescriptions.request_quantity_msg" var="request_quantity_msg"/>
<fmt:message key="prescriptions.no_requests_msg" var="no_requests_msg"/>
<fmt:message key="prescriptions.find_medicines" var="find_medicines_btn"/>
<fmt:message key="prescriptions.requested" var="requested"/>
<fmt:message key="medicines.milliliter" var="ml"/>
<fmt:message key="action.delete" var="delete"/>
<html>
<head>
    <title>${prescriptions_page_title}</title>
    <c:set var="current_page" value="jsp/doctor/prescriptions.jsp" scope="session"/>
</head>
<body>
<h3>${prescriptions_title}</h3>
<hr>
<br/>
<div class="data_table">
    <c:if test="${not empty temp_successful_change_message}">
        <div><p class="successful_msg"><fmt:message key="${temp_successful_change_message}"/></p></div>
        <br/>
    </c:if>
    <c:if test="${not empty failed_change_message}">
        <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
        <br/>
    </c:if>
    <c:if test="${current_user_role eq 'DOCTOR'}">
        <c:if test="${empty temp_show_renewal_requests}">
            <c:choose>
                <c:when test="${renewal_requests_quantity > 0}">
                    <p class="failed_msg">${request_quantity_msg} ${renewal_requests_quantity}</p>
                </c:when>
                <c:otherwise>
                    <p class="successful_msg">${no_requests_msg}</p>
                </c:otherwise>
            </c:choose>
        </c:if>
        <form id="show_renewal_requests_form" action="${context_path}/controller">
            <input type="hidden" name="command" value="go_prescriptions_page">
            <label for="show_renewal_requests">${show_renewal_requests_title}</label>
            <input type="checkbox" onclick="this.form.submit()"
                   id="show_renewal_requests" name="show_renewal_requests"
                   <c:if test="${temp_show_renewal_requests}">checked</c:if> value="true"/>
        </form>
        <br/>
    </c:if>
    <c:choose>
        <c:when test="${empty prescriptions_map}">
            <h4>${empty_msg}</h4>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                <tr>
                    <c:if test="${current_user_role eq 'CUSTOMER'}">
                        <th>${doctor_name_title}</th>
                    </c:if>
                    <c:if test="${current_user_role eq 'DOCTOR'}">
                        <th>${customer_name_title}</th>
                        <th>${birthday_date_title}</th>
                    </c:if>
                    <th>${international_name_title}</th>
                    <th>${dosage_title}</th>
                    <th>${quantity_title}</th>
                    <th>${sold_quantity_title}</th>
                    <th>${expiration_date_title}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${prescriptions_map}">
                    <tr>
                        <c:set value="${entry.getKey()}" var="prescription_id"/>
                        <c:set value="${entry.getValue()}" var="prescription_data_map"/>
                        <td>
                            <c:out value="${prescription_data_map.get(\"user_full_name\")}"/>
                        </td>
                        <c:if test="${current_user_role eq 'DOCTOR'}">
                            <td>
                                <c:out value="${prescription_data_map.get(\"birthday_date\")}"/>
                            </td>
                        </c:if>
                        <td>
                            <c:out value="${prescription_data_map.get(\"international_name\")}"/>
                        </td>
                        <td>
                            <c:out value="${prescription_data_map.get(\"dosage\")}"/>
                            <c:out value="${prescription_data_map.get(\"dosage_unit\")}"/>
                        </td>
                        <td>
                            <c:set var="current_quantity" value="${prescription_data_map.get(\"quantity\")}"/>
                            <c:set var="current_unit" value="${prescription_data_map.get(\"prescription_unit\")}"/>
                            <c:if test="${!(current_unit eq 'MILLILITERS')}">N</c:if>
                            <c:out value="${current_quantity}"/>
                            <c:if test="${current_unit eq 'MILLILITERS'}">${ml}</c:if>
                        </td>
                        <td>
                            <c:set var="current_sold_quantity" value="${prescription_data_map.get(\"sold_quantity\")}"/>
                            <c:out value="${current_sold_quantity}"/>
                        </td>
                        <td>
                            <c:set var="current_expiration_date"
                                   value="${prescription_data_map.get(\"expiration_date\")}"/>
                            <c:out value="${current_expiration_date}"/>
                        </td>
                        <td>
                            <div>
                                <c:choose>
                                    <c:when test="${(current_user_role eq 'DOCTOR') &&
                                    (prescription_data_map.get(\"need_renewal\") eq 'true')}">
                                        <div>
                                            <form action="${context_path}/controller" method="post">
                                                <input type="hidden" name="command" value="renewal_prescription">
                                                <input type="hidden" name="prescription_id" value="${prescription_id}"/>
                                                <input type="submit" value="${renewal_btn}">
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:when test="${(current_user_role eq 'CUSTOMER') &&
                                    (current_quantity - current_sold_quantity > 0)}">
                                        <c:choose>
                                            <c:when test="${(prescription_data_map.get(\"need_renewal\") eq 'false')}">
                                                <div>
                                                    <form action="${context_path}/controller" method="post">
                                                        <input type="hidden" name="command"
                                                               value="request_renewal_prescription">
                                                        <input type="hidden" name="prescription_id"
                                                               value="${prescription_id}"/>
                                                        <input type="submit" value="${request_renewal_btn}">
                                                    </form>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="successful_msg">${requested}</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
                            </div>
                        </td>
                        <td>
                            <c:if test="${(current_user_role eq 'CUSTOMER') &&
                                    (prescription_data_map.get(\"is_active\") eq 'true')}">
                                <div>
                                    <form action="${context_path}/controller" method="post">
                                        <input type="hidden" name="command"
                                               value="show_medicines_for_prescription">
                                        <input type="hidden" name="prescription_id" value="${prescription_id}"/>
                                        <input type="submit" value="${find_medicines_btn}">
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${current_user_role eq 'DOCTOR'}">
                                <div>
                                    <form action="${context_path}/controller" method="post">
                                        <input type="hidden" name="command" value="delete_prescription">
                                        <input type="hidden" name="prescription_id" value="${prescription_id}"/>
                                        <input type="submit" value="${delete}">
                                    </form>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
</body>
<script>
    function sendFormByClickCheckbox(form, checkboxId) {
        if (document.getElementById(checkboxId).checked) {
            form.submit();
        }
    }
</script>
</html>
