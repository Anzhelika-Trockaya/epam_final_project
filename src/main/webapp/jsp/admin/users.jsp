<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="users.page_title" var="users_page_title"/>
<fmt:message key="users.empty_message" var="empty_msg"/>
<fmt:message key="users.users" var="users_title"/>
<fmt:message key="users.add" var="add_user"/>
<fmt:message key="users.block" var="block_btn"/>
<fmt:message key="users.unblock" var="unblock_btn"/>
<fmt:message key="action.delete" var="delete_btn"/>
<fmt:message key="users.login" var="login_title"/>
<fmt:message key="users.role" var="role_title"/>
<fmt:message key="users.state" var="state_title"/>
<fmt:message key="users.blocked" var="blocked_title"/>
<fmt:message key="users.active" var="active_title"/>
<fmt:message key="users.lastname" var="lastname_title"/>
<fmt:message key="users.name" var="name_title"/>
<fmt:message key="users.patronymic" var="patronymic_title"/>
<fmt:message key="users.birthday_date" var="birthday_title"/>
<fmt:message key="users.sex" var="sex_title"/>
<fmt:message key="users.phone" var="phone_title"/>
<fmt:message key="users.address" var="address_title"/>
<fmt:message key="registration.admin" var="title_admin"/>
<fmt:message key="registration.pharmacist" var="title_pharmacist"/>
<fmt:message key="registration.doctor" var="title_doctor"/>
<fmt:message key="registration.customer" var="title_customer"/>
<fmt:message key="registration.male" var="title_male"/>
<fmt:message key="registration.female" var="title_female"/>
<fmt:message key="action.search" var="search"/>
<fmt:message key="action.reset" var="reset_search_results"/>

<html>
<head>
    <title>${users_page_title}</title>
    <c:set var="current_page" value="jsp/admin/users.jsp" scope="session"/>
</head>
<body>
<h3>${users_title}</h3>
<hr>
<div class="data_table">
    <c:if test="${not empty temp_successful_change_message}">
        <div><p class="successful_msg"><fmt:message key="${temp_successful_change_message}"/></p></div>
        <br>
    </c:if>
    <c:if test="${not empty failed_change_message}">
        <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
        <br>
    </c:if>
    <form action="${context_path}/controller">
        <input type="hidden" name="command" value="search_user"/>
        <input type="text" name="user_lastname" placeholder="${lastname_title}" value="${user_lastname}"/>
        <input type="text" name="user_name" placeholder="${name_title}" value="${user_name}"/>
        <input type="text" name="user_patronymic" placeholder="${patronymic_title}" value="${user_patronymic}"/>
        <input type="date" name="user_birthday_date" placeholder="${birthday_title}" value="${user_birthday_date}"/>
        <br><br>
        <label for="role">${role_title}</label>
        <select id="role" name="user_role" size="1">
            <option id="default_role" selected value="">-</option>
            <option
                    <c:if test="${user_role eq 'ADMIN'}">selected</c:if> value="ADMIN">${title_admin}
            </option>
            <option
                    <c:if test="${user_role eq 'DOCTOR'}">selected</c:if> value="DOCTOR">${title_doctor}
            </option>
            <option
                    <c:if test="${user_role eq 'PHARMACIST'}">selected</c:if> value="PHARMACIST">${title_pharmacist}
            </option>
            <option
                    <c:if test="${user_role eq 'CUSTOMER'}">selected</c:if> value="CUSTOMER">${title_customer}
            </option>
        </select>
        <label for="state">${state_title}</label>
        <select id="state" name="user_state" size="1">
            <option id="default_state" selected value="">-</option>
            <option
                    <c:if test="${user_state eq 'ACTIVE'}">selected</c:if> value="ACTIVE">${active_title}
            </option>
            <option
                    <c:if test="${user_state eq 'BLOCKED'}">selected</c:if> value="BLOCKED">${blocked_title}
            </option>
        </select>
        <br><br>
        <input type="submit" value="${search}"/>
        <input type="submit" form="reset_search_results_form" value="${reset_search_results}"/>
    </form>
    <form id="reset_search_results_form" action="${context_path}/jsp/admin/users.jsp"></form>
    <hr>
    <br>
    <c:if test="${empty users_list}">
        <h4>${empty_msg}</h4>
    </c:if>
    <form action="${context_path}/jsp/common/registration.jsp">
        <input type="submit" value="${add_user}"/>
    </form>
    <br>
    <table class="table">
        <thead>
        <tr>
            <th>${login_title}</th>
            <th>${role_title}</th>
            <th>${state_title}</th>
            <th>${lastname_title}</th>
            <th>${name_title}</th>
            <th>${patronymic_title}</th>
            <th>${birthday_title}</th>
            <th>${sex_title}</th>
            <th>${phone_title}</th>
            <th>${address_title}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users_list}">
            <tr>
                <td><c:out value="${user.login}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${user.role eq 'ADMIN'}">
                            ${title_admin}
                        </c:when>
                        <c:when test="${user.role eq 'CUSTOMER'}">
                            ${title_customer}
                        </c:when>
                        <c:when test="${user.role eq 'DOCTOR'}">
                            ${title_doctor}
                        </c:when>
                        <c:when test="${user.role eq 'PHARMACIST'}">
                            ${title_pharmacist}
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${user.state eq 'ACTIVE'}">
                            ${active_title}
                        </c:when>
                        <c:when test="${user.state eq 'BLOCKED'}">
                            ${blocked_title}
                        </c:when>
                    </c:choose>
                </td>
                <td><c:out value="${user.lastname}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.patronymic}"/></td>
                <td><c:out value="${user.birthdayDate}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${user.sex eq 'MALE'}">
                            ${title_male}
                        </c:when>
                        <c:when test="${user.sex eq 'FEMALE'}">
                            ${title_female}
                        </c:when>
                    </c:choose>
                </td>
                <td><c:out value="${user.phone}"/></td>
                <td><c:out value="${user.address}"/></td>
                <td>
                    <div>
                        <form action="${context_path}/controller" method="post">
                            <input type="hidden" name="command" value="change_user_state">
                            <input type="hidden" name="user_id" value="${user.id}"/>
                            <c:choose>
                                <c:when test="${user.state eq 'ACTIVE'}">
                                    <input type="hidden" name="user_state" value="BLOCKED">
                                    <input type="submit" value="${block_btn}">
                                </c:when>
                                <c:when test="${user.state eq 'BLOCKED'}">
                                    <input type="hidden" name="user_state" value="ACTIVE">
                                    <input type="submit" value="${unblock_btn}">
                                </c:when>
                            </c:choose>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
