<%--
  Created by IntelliJ IDEA.
  User: Анжелика
  Date: 10.04.2022
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Pharmacy | Sign In</title>
</head>
<body>
<c:import url="../header/header.jsp" />
<div>
    ${successful_registration_message}
    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="login"/>
        Login:  <input type="text" name="login" value=""/>
        <br/>
        Password:  <input type="password" name="password" value=""/>
        <br/>
        <input type="submit" name="sub" value="Sign In"/>
        <br/>
        ${failed_login_message}
    </form>
    <a href="${pageContext.request.contextPath}/jsp/common/registration.jsp">Go to registration page</a>
</div>
</body>
</html>
