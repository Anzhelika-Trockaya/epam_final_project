<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>
<fmt:message key="international_names.page_title" var="page_title"/>
<fmt:message key="international_names.title" var="title"/>
<fmt:message key="international_names.empty" var="empty_msg"/>
<fmt:message key="international_names.enter_international_name" var="enter_international_name"/>
<fmt:message key="action.add" var="add"/>
<fmt:message key="action.edit" var="edit"/>
<html>
<head>
    <title>${page_title}</title>
</head>
<body>
<div>
    <h3>${title}</h3>
    <hr>
    <br/>
    <c:if test="${not empty successful_change_message}">
        <div><p class="successful_msg"><fmt:message key="${successful_change_message}"/></p></div>
        <br/>
    </c:if>
    <c:if test="${not empty failed_change_message}">
        <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
        <br/>
    </c:if>
    <c:if test="${empty international_names_list}">
        <h4>${empty_msg}</h4>
    </c:if>
    <br/>
    <table class="table">
        <tbody>
        <tr>
            <form action="${context_path}/controller">
                <input type="hidden" name="command" value="add_international_name"/>
                <input type="text" name="name" placeholder="${enter_international_name}"/>
                <input type="submit" value="${add}">
            </form>
        </tr>
        <c:forEach var="international_name" items="${international_names_list}">
            <tr>
                <form action="${context_path}/controller" method="post">
                    <input type="hidden" name="command" value="edit_international_name">
                    <input type="hidden" name="international_name_id" value="${international_name.id}"/>
                    <input type="text" name="name" value="${international_name.internationalName}"/>
                    <input type="submit" value="${edit}">
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
