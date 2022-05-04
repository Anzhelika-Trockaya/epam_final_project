<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext}css/style.css">
    <title>Home</title>
</head>
<body>

<div>
    <header>
        <%@include file="header/header.jsp"%>
    </header>
    <form action="controller">
        <input type="hidden" name="command" value="search_medicine"/>
        <input type="text" name="medicine_name" placeholder="Enter medicine name"/>
        <input type="submit" name="sub" value="Search"/>
        <br/>
    </form>
</div>
</body>
</html>
