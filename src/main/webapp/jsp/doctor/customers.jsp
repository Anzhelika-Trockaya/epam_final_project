<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="customers.page_title" var="customers_page_title"/>
<fmt:message key="customers.empty_message" var="empty_msg"/>
<fmt:message key="customers.customers" var="customers_title"/>
<fmt:message key="customers.add_prescription_btn" var="create_prescription_btn"/>
<fmt:message key="users.lastname" var="lastname_title"/>
<fmt:message key="users.name" var="name_title"/>
<fmt:message key="users.patronymic" var="patronymic_title"/>
<fmt:message key="users.birthday_date" var="birthday_title"/>
<fmt:message key="users.sex" var="sex_title"/>
<fmt:message key="users.phone" var="phone_title"/>
<fmt:message key="registration.male" var="title_male"/>
<fmt:message key="registration.female" var="title_female"/>
<fmt:message key="action.search" var="search"/>
<fmt:message key="action.reset" var="reset_search_results"/>
<html>
<head>
    <title>${customers_page_title}</title>
    <c:set var="current_page" value="jsp/doctor/customers.jsp" scope="session"/>
</head>
<body>
<h3>${customers_title}</h3>
<hr>
<br>
<div class="data_table">
    <c:if test="${not empty temp_successful_change_message}">
        <div><p class="successful_msg"><fmt:message key="${temp_successful_change_message}"/></p></div>
        <br/>
    </c:if>
    <c:if test="${not empty failed_change_message}">
        <div><p class="failed_msg"><fmt:message key="${failed_change_message}"/></p></div>
        <br/>
    </c:if>
    <form action="${context_path}/controller">
        <input type="hidden" name="command" value="search_user"/>
        <input type="text" name="lastname" placeholder="${lastname_title}" value="${user_lastname}"/>
        <input type="text" name="name" placeholder="${name_title}" value="${user_name}"/>
        <input type="text" name="patronymic" placeholder="${patronymic_title}" value="${user_patronymic}"/>
        <input type="date" name="birthday_date" placeholder="${birthday_title}" value="${user_birthday_date}"/>
        <input type="submit" value="${search}"/>
        <input type="submit" form="reset_search_results_form" value="${reset_search_results}"/>
    </form>
    <form id="reset_search_results_form" action="${context_path}/controller">
        <input type="hidden" name="command" value="go_customers_page"/>
    </form>
    <br/>
    <c:choose>
        <c:when test="${empty customers_list}">
            <h4>${empty_msg}</h4>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                <tr>
                    <th>${lastname_title}</th>
                    <th>${name_title}</th>
                    <th>${patronymic_title}</th>
                    <th>${birthday_title}</th>
                    <th>${sex_title}</th>
                    <th>${phone_title}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${customers_list}">
                    <tr>
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
                        <td>
                            <div>
                                <form action="${context_path}/controller">
                                    <input type="hidden" name="command" value="go_add_prescription_page"/>
                                    <input type="hidden" name="customer_id" value="${user.id}">
                                    <input type="submit" value="${create_prescription_btn}">
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
