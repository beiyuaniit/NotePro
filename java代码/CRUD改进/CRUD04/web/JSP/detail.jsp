
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
	<h3>Welcome ${username}</h3>
		<h1>部门详情</h1>
		<hr >
		编号：${dept.deptno}<br>
		名称：${dept.dname}<br>
		位置：${dept.loc}<br>
		<input type="button" name="" id="" value="后退" onclick="window.history.back()" />
	</body>
</html>
