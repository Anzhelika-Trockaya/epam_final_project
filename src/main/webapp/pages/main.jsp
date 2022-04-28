<%--
  Created by IntelliJ IDEA.
  User: Анжелика
  Date: 10.04.2022
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>main</title>
</head>
<form>
    Hello ${user_name}
    <br/>
    <form action="controller">
        <input type="hidden" name="command" value="logout"/>
        <input type="submit" value="logout"/>
    </form>
    </body>
</form>
</html>
