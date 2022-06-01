<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header/header.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${context_path}/css/style.css">
    <title>Home</title>
    <c:set var="current_page" value="jsp/home.jsp" scope="session"/>
</head>
<body>
<fmt:message key="home.add_to_basket" var="add_to_basket"/>
<fmt:message key="home.dosage" var="dosage"/>
<fmt:message key="home.in_part" var="in_part"/>
<fmt:message key="home.parts_in_package" var="parts_in_package"/>
<div>
    <form action="controller">
        <input type="hidden" name="command" value="search_medicine"/>
        <input type="text" name="medicine_name" placeholder="Enter medicine name"/>
        <input type="submit" name="sub" value="Search"/>
        <br/>
    </form>
    <div>
        <table>
            <tbody>
            <c:forEach var="medicine" items="${medicines_list}">
                <tr>
                    <td><img src="<c:out value="${medicine.imagePath}"/>" height="216" width="297" alt="no image"></td>
                    <td>
                        <p>
                            <a href="/controller?command=go_medicine_page&id=<c:out value="${medicine.id}"/>">
                                <c:out value="${medicine.name}"/>
                            </a>
                        </p>
                        <c:forEach var="international_name" items="${international_names_list}">
                            <c:if test="${medicine.internationalNameId eq international_name.id}">
                                <p>${international_name.internationalName}</p>
                            </c:if>
                        </c:forEach>
                        <p>${dosage} <c:out value="${medicine.dosage}"/> <c:out value="${medicine.dosageUnit}"/></p>
                        <c:forEach var="form" items="${forms_list}">
                            <c:if test="${medicine.formId eq form.id}">
                                <p>${form.name}</p>
                                <p><c:out value="${medicine.amountInPart}"/> ${form.unit} ${in_part} (
                                    <c:out value="${medicine.partsInPackage}"/> ${parts_in_package})</p>
                            </c:if>
                        </c:forEach>
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="add_medicine_to_basket"/>
                            <input type="hidden" name="medicine_id" value="<c:out value="${medicine.id}"/>"/>
                            <div class="numbers">
                                <button type="button" id="${medicine.id}_btn_minus" onclick="minus(${medicine.id})">-
                                </button>
                                <input type="number" value="1"
                                       onkeyup="validate(${medicine.id}, ${medicine.totalNumberOfParts})"
                                       id="${medicine.id}_item_number" name="number"/>
                                <button type="button" id="${medicine.id}_btn_plus" onclick="plus(${medicine.id},
                                    ${medicine.totalNumberOfParts})">+</button>
                                parts
                                <input type="submit" value="${add_to_basket}"/>
                            </div>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
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
        var itemNumber = document.getElementById(medicineId + '_item_number');
        var value = parseInt(itemNumber.value);
        if (value > totalNumber) {
            itemNumber.value = totalNumber;
        } else if (value < 1) {
            itemNumber.value = 1;
        }
    }
</script>
</body>
</html>
