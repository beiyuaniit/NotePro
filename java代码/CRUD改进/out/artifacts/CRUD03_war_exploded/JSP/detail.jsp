<%@ page import="Beans.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
	Dept dept=(Dept) request.getAttribute("dept");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<h1>部门详情</h1>
		<hr >
		编号：<%=dept.getDeptno()%><br>
		名称：<%=dept.getDname()%><br>
		位置：<%=dept.getLoc()%><br>
		<input type="button" name="" id="" value="后退" onclick="window.history.back()" />
	</body>
</html>
