<%--
  Created by IntelliJ IDEA.
  User: Анжелика
  Date: 01.05.2022
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="context_path" value="${pageContext.request.contextPath}"/>

<fmt:setBundle basename="context.pagecontent"/>

<fmt:message key="header.cabinet" var="cabinet"/>
<fmt:message key="language" var="lang"/>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'en_US'}" scope="session"/></c:when>
</c:choose>

<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${context_path}/css/style.css">
    <title>Pharmacy</title>
</head>
<body>
<div style="height: 25px; text-align: right; right: 3%">
    <c:choose>
        <c:when test="${language eq 'be_BY'}">
            <a href="${context_path}/controller?command=change_language&language=en_US">${lang}</a>
        </c:when>
        <c:when test="${language eq 'en_US'}">
            <a href="${context_path}/controller?command=change_language&language=be_BY">${lang}</a>
        </c:when>
        <c:otherwise>
            <a href="${absolutePath}/controller?command=change_language&language=be_BY">${lang}</a>
        </c:otherwise>
    </c:choose></div>
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
                <a href="${pageContext.request.contextPath}/controller?command=get_user_info">${cabinet}</a>
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
