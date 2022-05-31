<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'en_US'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.pagecontent"/>

<html>
<head>
    <meta charset="utf-8">
    <c:set var="context_path" value="${pageContext.request.contextPath}"/>
    <link rel="stylesheet" href="${context_path}/css/style.css">
</head>
<body>
<header>
    <fmt:message key="header.pharmacy" var="label_pharmacy"/>
    <fmt:message key="header.cabinet" var="cabinet"/>
    <fmt:message key="language" var="lang"/>
    <fmt:message key="navigation.users" var="users_nav_title"/>
    <fmt:message key="navigation.medicines" var="medicines_nav_title"/>
    <fmt:message key="navigation.orders" var="orders_nav_title"/>
    <fmt:message key="navigation.prescriptions" var="prescriptions_nav_title"/>
    <fmt:message key="navigation.edit_medicines" var="edit_medicines_nav_title"/>
    <fmt:message key="navigation.edit_manufacturers" var="edit_manufacturers_nav_title"/>
    <fmt:message key="navigation.edit_forms" var="edit_forms_nav_title"/>
    <fmt:message key="navigation.edit_international_names" var="edit_international_names_nav_title"/>
    <div style="height: 25px; text-align: right; right: 3%">
        <c:choose>
            <c:when test="${language eq 'be_BY'}">
                <a href="${context_path}/controller?command=change_language&language=en_US">${lang}</a>
            </c:when>
            <c:when test="${language eq 'en_US'}">
                <a href="${context_path}/controller?command=change_language&language=be_BY">${lang}</a>
            </c:when>
            <c:otherwise>
                <a href="${context_path}/controller?command=change_language&language=be_BY">${lang}</a>
            </c:otherwise>
        </c:choose></div>
    <div class="header">
        <a href="${context_path}/jsp/home.jsp"><h1>${label_pharmacy}</h1></a>
    </div>
    <div class="sign_in">
        <c:choose>
            <c:when test="${sessionScope.current_user_role == null}">
                <a href="${pageContext.request.contextPath}/jsp/common/sign_in.jsp"><fmt:message key="sign_in"/></a>
            </c:when>
            <c:otherwise>
                <c:if test="${not(sessionScope.current_user_role eq 'ADMIN')}">
                    <a href="${pageContext.request.contextPath}/controller?command=get_user_info">${cabinet}</a>
                </c:if>
                <a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="log_out"/></a>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="navbar">
        <c:choose>
            <c:when test="${current_user_role eq 'ADMIN'}">
                <%@include file="fragment/admin_fragment.jspf" %>
            </c:when>
            <c:when test="${current_user_role eq 'PHARMACIST'}">
                <%@include file="fragment/pharmacist_fragment.jspf" %>
            </c:when>
            <c:when test="${current_user_role eq 'DOCTOR'}">
                <%@include file="fragment/doctor_fragment.jspf" %>
            </c:when>
            <c:when test="${current_user_role eq 'CUSTOMER'}">
                <%@include file="fragment/customer_fragment.jspf" %>
            </c:when>
        </c:choose>
    </div>
</header>
</body>
</html>
