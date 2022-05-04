<%--
  Created by IntelliJ IDEA.
  User: Анжелика
  Date: 02.05.2022
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Registration</title>
</head>
<body>
<c:import url="../header/header.jsp"/>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="register"/>
    Lastname*: <input type="text" name="lastname" value="" required pattern="[A-Za-zА-Яа-яёЁ][A-Za-zА-Яа-яёЁ-]{0,44}"/>
    <br/><br/>
    Name*: <input type="text" name="name" value="" required pattern="[A-Za-zА-Яа-яёЁ][A-Za-zА-Яа-яёЁ-]{0,44}"/>
    <br/><br/>
    Patronymic*: <input type="text" name="patronymic" value="" required pattern="[A-Za-zА-Яа-яёЁ][A-Za-zА-Яа-яёЁ-]{0,44}"/>
    <br/><br/>
    Sex*: <select name="sex" size="1" required>
    <option disabled selected value="">-</option>
    <option value="FEMALE">FEMALE</option>
    <option value="MALE">MALE</option>
</select>
    <br/><br/>
    Birthday date*: <input type="date" name="birthday_date" required>
    <br/><br/>
    Phone number* (+375XXXXXXXXX): <input type="text" name="phone" value="+375" required pattern="\+375(33|29|25|44)\d{7}">
    <br/><br/>
    Address*: <textarea type="text" name="address" value="" maxlength="100" required></textarea>
    <br/><br/>
    Login*: <input type="text" name="login" value="" required pattern="[a-zA-Zа-яА-ЯёЁ0-9._-]{4,45}"/>
    <br/><br/>
    Password*: <input type="password" name="password" value="" required pattern="(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,45}"/>
    <br/><br/>
    <input type="submit" name="sub" value="Register"/>
    <br/><br/>
    ${failed_registration_message}
</form>
</body>
</html>
