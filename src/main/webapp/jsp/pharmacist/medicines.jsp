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
        <c:if test="${not empty failed}">
            <div><p class="failed_msg"><fmt:message key="${failed}"/></p></div>
            <br/>
        </c:if>
        <%@include file="../fragment/search_medicine_form.jspf" %>
        <br/>
        <form action="${context_path}/controller">
            <input type="hidden" name="command" value="go_add_medicine_page"/>
            <input type="submit" value="${add_medicine}"/>
        </form>
        <hr>
        <br/>
        <%@include file="../fragment/show_medicines_list_fragment.jspf" %>
    </div>
</div>
</body>
</html>
