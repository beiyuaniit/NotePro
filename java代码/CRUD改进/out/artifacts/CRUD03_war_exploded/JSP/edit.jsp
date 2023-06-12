<%@ page import="Beans.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
	Dept dept=(Dept)request.getAttribute("dept");
%>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>修改部门</title>
	</head>
	<body>
			<form action="<%=request.getContextPath()%>/dept/doEdit" method="post">
				编号<input type="text" name="deptno" value="<%=dept.getDeptno()%>" readonly/><br>
				名称<input type="text" name="dname" value="<%=dept.getDname()%>" /><br>
				位置<input type="text" name="loc"  value="<%=dept.getLoc()%>" /><br>
				<input type="submit" value="修改"/><br>
			</form>
	</body>
</html>
