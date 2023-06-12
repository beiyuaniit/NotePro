<%--
  Created by IntelliJ IDEA.
  User: beilinanju
  Date: 2022/3/29
  Time: 23:07
  To change this template use File | Settings | File Templates.
--%>

<%--<%@page isErrorPage="true" %>--%>
<%--<%@page import="java.util.Map" %>--%>
<%--<%@page session="true"%>--%>
<%--<%@page errorPage="index.jsp" %>--%>
<%--<%@page contentType="text/html" pageEncoding="UTF-8" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>--%>
<%--  <head>--%>
<%--    <title>$Title$</title>--%>
<%--  </head>--%>

<%--  <body>--%>


<%
  //System.out.println("Hello JSP");
  out.print(name);
%>
<%--<-- HTML -->--%>

<%!
  public String name="jackson";

%>

${name}
<%--  </body>--%>



<%--</html>--%>

