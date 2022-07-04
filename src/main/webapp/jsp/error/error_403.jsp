<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header/header.jsp" %>
<html>
<head>
    <title>Error 403</title>
    <c:set var="current_page" value="jsp/error/error_403.jsp" scope="session"/>
</head>
<body>
<h1>Error 403</h1>
<h2><fmt:message key="error_403.no_access_rights_msg"/></h2>
</body>
</html>
