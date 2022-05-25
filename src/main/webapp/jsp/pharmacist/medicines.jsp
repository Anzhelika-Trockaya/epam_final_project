<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="medicines.page_title" var="medicines_page_title"/>
<fmt:message key="medicines.title" var="medicines_title"/>
<fmt:message key="medicines.empty_msg" var="empty_msg"/>
<fmt:message key="medicines.add_medicine" var="add_medicine"/>
<html>
<head>
    <title>${medicines_page_title}</title>
    <c:set var="current_page" value="jsp/pharmacist/medicines.jsp" scope="session"/>
</head>
<body>
<div>
    <div>
        <h3>${medicines_title}</h3>
        <hr>
        <br/>
        <c:if test="${empty medicines_list}">
            <h4>${empty_msg}</h4>
        </c:if>
        <form action="${context_path}/controller">
            <input type="hidden" name="command" value="go_add_medicine_page"/>
            <input type="submit" value="${add_medicine}"/>
        </form>
        <br/>
    </div>
</div>
</body>
</html>
