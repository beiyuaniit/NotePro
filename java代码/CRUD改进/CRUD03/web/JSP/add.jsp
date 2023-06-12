<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>新增部门</title>
	</head>
	<body>
		<form action="<%=request.getContextPath()%>/dept/add" method="post">
			编号<input type="text" name="deptno" /><br>
			名称<input type="text" name="dname"   /><br>
			位置<input type="text" name="loc"  /><br>
			<input type="submit" value="新增"/><br>
		</form>
	</body>
</html>
