<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header/header.jsp" %>
<fmt:message key="home.title" var="home_title"/>
<fmt:message key="home.empty_msg" var="empty_msg"/>
<html>
<head>
    <meta charset="utf-8">
    <title>${home_title}</title>
    <c:set var="current_page" value="jsp/home.jsp" scope="session"/>
</head>
<body>
<div>
    <br/>
    <c:if test="${current_user_role eq 'CUSTOMER'}">
        <%@include file="fragment/search_medicine_form.jspf" %>
    </c:if>
    <c:choose>
        <c:when test="${current_user_role != 'CUSTOMER' or(empty show_search_result)}">
            <div>
                <br/>
                <p><b><fmt:message key="home.text_one"/> </b></p>
                <p>
                    &#10004;<fmt:message key="home.text_two"/>
                </p>
                <p>
                    &#10004;<fmt:message key="home.text_three"/>
                </p>
                <p>
                    &#10004;<fmt:message key="home.text_four"/>
                </p>
                <p>
                    &#10004;<fmt:message key="home.text_five"/>
                </p>
                <p>
                    &#10004;<fmt:message key="home.text_six"/>
                </p>
                <br/>
                <p><b><fmt:message key="home.text_seven"/> </b></p>
                <p>
                    &#10004;<fmt:message key="home.text_eight"/>
                </p>
                <p>
                    &#10004;<fmt:message key="home.text_nine"/>
                </p>
                <p>
                    &#10004;<fmt:message key="home.text_ten"/>
                </p>
                <p>
                    &#10004;<fmt:message key="home.text_eleven"/>
                </p>
                <p>
                    &#10004;<fmt:message key="home.text_twelve"/>
                </p>
                <br/>
                <p><b><fmt:message key="home.text_thirteen"/> </b></p>
            </div>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${empty medicines_list}">
                    <p class="successful_msg">${not_found_msg}</p>
                </c:when>
                <c:otherwise>
                    <%@include file="fragment/show_medicines_list_fragment.jspf" %>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
</div>
<script>
    function minus(medicineId) {
        var itemNumber = document.getElementById(medicineId + '_item_number');
        var value = parseInt(itemNumber.value);
        value = value - 1;
        if (value > 0) {
            itemNumber.value = value;
        }
    }

    function plus(medicineId, max) {
        var itemNumber = document.getElementById(medicineId + '_item_number');
        var value = parseInt(itemNumber.value);
        value = value + 1;
        if (value <= max) {
            itemNumber.value = value;
        }
    }

    function validate(medicineId, totalNumber) {
        const numberPattern = /^\d{1,6}$/;
        var itemNumber = document.getElementById(medicineId + '_item_number');
        if (!numberPattern.test(itemNumber.value)) {
            itemNumber.value = 1;
        } else {
            var value = parseInt(itemNumber.value);
            if (value < 1) {
                itemNumber.value = 1;
            } else if (value > totalNumber) {
                itemNumber.value = totalNumber;
            }
        }
    }
</script>
</body>
</html>
