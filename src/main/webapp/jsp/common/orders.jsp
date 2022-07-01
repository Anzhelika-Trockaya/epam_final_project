<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="orders.page_title" var="orders_page_title"/>
<fmt:message key="orders.title" var="orders_label"/>
<fmt:message key="orders.new_orders" var="new_orders"/>
<fmt:message key="orders.there_are_no_new_orders" var="there_are_no_new_orders"/>
<fmt:message key="orders.get_order" var="get_order"/>
<fmt:message key="orders.empty_msg" var="empty_msg"/>
<fmt:message key="orders.all" var="all"/>
<fmt:message key="order.title" var="order_label"/>
<fmt:message key="order.state" var="state_label"/>
<fmt:message key="order.creation_date" var="creation_date_label"/>
<fmt:message key="order.state" var="state_label"/>
<fmt:message key="order.completed" var="completed"/>
<fmt:message key="order.in_progress" var="in_progress"/>
<fmt:message key="cart.total_cost" var="total_cost_label"/>
<fmt:message key="medicines.prescription" var="prescription_title"/>
<html>
<head>
    <title>${orders_page_title}</title>
    <c:set var="current_page" value="jsp/common/orders.jsp" scope="session"/>
</head>
<body>
<h3>${orders_label}</h3>
<hr>
<br>
<c:if test="${!(current_user_role eq 'CUSTOMER')}">
    <c:choose>
        <c:when test="${new_orders_quantity >0}">
            <p class="successful_msg">${new_orders_quantity} ${new_orders}</p>
        </c:when>
        <c:otherwise>
            <p class="successful_msg">${there_are_no_new_orders}</p>
        </c:otherwise>
    </c:choose>
    <form action="${context_path}/controller" method="post">
        <input type="hidden" name="command" value="get_order_for_process"/>
        <input <c:if test="${new_orders_quantity ==0}">disabled</c:if> type="submit" value="${get_order}"/>
    </form>
    <hr>
    <br>
    <form action="${context_path}/controller">
        <input type="hidden" name="command" value="go_orders_page"/>
        <select name="state_to_show" size="1" onchange="this.form.submit()">
            <option selected value="">${all}</option>
            <option
                    <c:if test="${state_to_show eq 'IN_PROGRESS'}">selected</c:if> value="IN_PROGRESS">
                    ${in_progress}
            </option>
            <option
                    <c:if test="${state_to_show eq 'COMPLETED'}">selected</c:if> value="COMPLETED">
                    ${completed}
            </option>
        </select>
    </form>
    <hr>
    <br>
</c:if>
<c:if test="${empty orders_set}">
    ${empty_msg}
</c:if>
<c:forEach var="order" items="${orders_set}">
    <a href="${context_path}/controller?command=go_order_page&order_id=${order.id}">
        <p>${order_label}№${order.id} ${creation_date_label}:${order.paymentDate}, ${state_label}:
            <c:choose>
                <c:when test="${order.state eq 'IN_PROGRESS'}">
                    <span style="color: red">${in_progress}</span>
                </c:when>
                <c:when test="${order.state eq 'COMPLETED'}">
                    <span style="color: darkblue">${completed}</span>
                </c:when>
            </c:choose>
        </p>
    </a>
    <hr>
    <br>
</c:forEach>
</body>
</html>
