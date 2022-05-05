<%--
  Created by IntelliJ IDEA.
  User: Анжелика
  Date: 02.05.2022
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="context_path" value="${pageContext.request.contextPath}"/>

<fmt:setBundle basename="context.pagecontent"/>
<fmt:message key="users.page_title" var="users_page_title"/>
<fmt:message key="users.empty_message" var="empty_msg"/>
<fmt:message key="users.users" var="users_title"/>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'en_US'}" scope="session"/></c:when>
</c:choose>

<html>
<head>
    <title>${users_page_title}</title>

</head>
<body>
<header>
    <%@include file="../header/header.jsp" %>
</header>
<div>
    <div>
        <c:choose>
            <c:when test="${empty users_list}">
                <h3>${empty_msg}</h3>
            </c:when>
            <c:otherwise>
                <h3>${users_title}</h3>
                <hr>
                <br>
                <table>
                    <thead>
                    <tr>
                        <th>Login</th>
                        <th>Role</th>
                        <th>Lastname</th>
                        <th>Name</th>
                        <th>Patronymic</th>
                        <th>Birthday date</th>
                        <th>Sex</th>
                        <th>Phone</th>
                        <th>Address</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users_list}">
                        <input type="hidden" value="<c:out value="${user.id}"/>"/>
                        <tr>
                            <td><c:out value="${user.login}"/></td>
                            <td><c:out value="${user.role}"/></td>
                            <td><c:out value="${user.lastname}"/></td>
                            <td><c:out value="${user.name}"/></td>
                            <td><c:out value="${user.patronymic}"/></td>
                            <td><c:out value="${user.birthdayDate}"/></td>
                            <td><c:out value="${user.sex}"/></td>
                            <td><c:out value="${user.phone}"/></td>
                            <td><c:out value="${user.address}"/></td>
                            <td>
                                <div>
                                    <c:choose>
                                        <c:when test="${user.role eq 'ADMIN'}">
                                            <div class="col">
                                                <form action="${context_path}/controller" method="post">
                                                    <input type="hidden" name="command" value="delete_user">
                                                    <input type="hidden" name="id" value="${user.userId}">
                                                    <button type="submit">Delete</button>
                                                </form>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
