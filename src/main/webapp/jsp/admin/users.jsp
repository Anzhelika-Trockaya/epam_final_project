<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="users.page_title" var="users_page_title"/>
<fmt:message key="users.empty_message" var="empty_msg"/>
<fmt:message key="users.users" var="users_title"/>
<fmt:message key="users.add" var="add_user"/>

<html>
<head>
    <title>${users_page_title}</title>
    <c:set var="current_page" value="jsp/admin/users.jsp" scope="session"/>
</head>
<body>
<div>
    <div>
        <c:choose>
            <c:when test="${empty users_list}">
                <h3>${empty_msg}</h3>
            </c:when>
            <c:otherwise>
                <h3>${users_title}</h3>
                <hr>
                <br/>
                <form action="${context_path}/jsp/common/registration.jsp">
                    <input type="submit" value="${add_user}"/>
                </form>
                <br/>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Login</th>
                        <th>Role</th>
                        <th>State</th>
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
                        <tr>
                            <td><c:out value="${user.login}"/></td>
                            <td><c:out value="${user.role}"/></td>
                            <td><c:out value="${user.state}"/></td>
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
                                            <div>
                                                <form action="${context_path}/controller" method="post">
                                                    <input type="hidden" name="command" value="delete_user">
                                                    <input type="hidden" name="user_id" value="${user.id}"/>
                                                    <input type="submit" value="Delete">
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div>
                                                <form action="${context_path}/controller" method="post">
                                                    <input type="hidden" name="command" value="change_user_state">
                                                    <c:choose>
                                                        <c:when test="${user.state eq 'ACTIVE'}">
                                                            <input type="hidden" name="state" value="BLOCKED">
                                                        </c:when>
                                                        <c:when test="${user.state eq 'BLOCKED'}">
                                                            <input type="hidden" name="state" value="ACTIVE">
                                                        </c:when>
                                                    </c:choose>
                                                    <input type="hidden" name="user_id" value="${user.id}"/>
                                                    <input type="submit" value="Block">
                                                </form>
                                            </div>
                                        </c:otherwise>
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
