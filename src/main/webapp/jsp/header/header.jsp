<%--
  Created by IntelliJ IDEA.
  User: Анжелика
  Date: 01.05.2022
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Pharmacy</title>
</head>
<body>

<div class="header">
    <h1 class="header_text">PHARMACY</h1>
</div>
<div class="sign_in">
    <c:choose>
        <c:when test="${sessionScope.user_role == null}">
            <a href="${pageContext.request.contextPath}/jsp/common/sign_in.jsp">Sign In</a>
        </c:when>
        <c:otherwise>
            <c:if test="${not(sessionScope.user_role eq 'ADMIN')}">
                <a href="${pageContext.request.contextPath}/controller?command=get_user_info">My cabinet</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/controller?command=logout">Log out</a>
        </c:otherwise>
    </c:choose>
</div>
<div>
    <ul class="menu">
        <c:choose>
            <c:when test="${user_role eq 'ADMIN'}">
                <%@include file="fragment/admin_fragment.jspf" %>
            </c:when>
            <c:when test="${user_role eq 'PHARMACIST'}">
                <%@include file="fragment/pharmacist_fragment.jspf" %>
            </c:when>
            <c:when test="${user_role eq 'DOCTOR'}">
                <%@include file="fragment/doctor_fragment.jspf" %>
            </c:when>
            <c:when test="${user_role eq 'CUSTOMER'}">
                <%@include file="fragment/customer_fragment.jspf" %>
            </c:when>
        </c:choose>
    </ul>
</div>
</body>
</html>
