<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header/header.jsp" %>
<fmt:message key="home.title" var="home_title"/>
<html>
<head>
    <meta charset="utf-8">
    <title>${home_title}</title>
    <c:set var="current_page" value="jsp/home.jsp" scope="session"/>
</head>
<body>
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
</body>
</html>
