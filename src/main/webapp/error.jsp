<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 1/15/2025
  Time: 2:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>error</title>
</head>
<body>
    <%
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<h1>Something Wrong happened</h1>");
        printWriter.println("    <h2>Check your input carefully and try again</h2>");
        printWriter.println("<a href=\"register\">Register New Student</a>");
    %>
</body>
</html>
