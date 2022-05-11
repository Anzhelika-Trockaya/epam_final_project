<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${context_path}/css/style.css">
    <title>Home</title>
    <c:set var="current_page" value="jsp/home.jsp" scope="session"/>
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
