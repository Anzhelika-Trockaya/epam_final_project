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
            <tr>
                <td><img src="${context_path}/image/medicine/1.jpg" height="216" width="297" alt="no image"></td>
                <td>
                        <p>Paracetamol 500 mg link</p>
                        <p>international name</p>
                        <p>dosage</p>
                        <p>number in package</p>
                        <p>number in plate</p>
                        <p>
                            <button onclick="">-</button>
                            <select>
                                <option>packages</option>
                                <option>plates</option>
                            </select>
                            <input type="submit" value="Add to basket"/>
                        </p>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
