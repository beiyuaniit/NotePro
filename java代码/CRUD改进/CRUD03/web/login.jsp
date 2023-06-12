<%--
  Created by IntelliJ IDEA.
  User: beilinanju
  Date: 2022/3/30
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>欢迎来到CRUD03</title>
</head>
<body>

<%-- 上写文路径，与就是项目名--%>
<%--<a href="<%=request.getContextPath()%>/dept/list">查看部门列表</a>--%>

<h1>Login Page</h1>
<%--登录--%>
<form action="<%=request.getContextPath()%>/user/login" method="post">
	UserName<input type="text" name="username"  value="" /><br>
	Password <input type="password" name="password"  value="" /><br>
	<input type="checkbox" name="isOk"  value="ok" />十天免登录<br>
	<input type="submit" value="登录"/>
</form>

</body>
</html>
