<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>修改部门</title>
	</head>
	<body>
	<h3>Welcome ${username}</h3>
			<form action="${pageContext.request.contextPath}/dept/doEdit" method="post">
				编号<input type="text" name="deptno" value="${dept.deptno}" readonly/><br>
				名称<input type="text" name="dname" value="${dept.dname}" /><br>
				位置<input type="text" name="loc"  value="${dept.loc}" /><br>
				<input type="submit" value="修改"/><br>
			</form>
	</body>
</html>
