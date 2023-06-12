<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>部门列表</title>
<%--	<base href="http://localhost:8080${pageContext.request.contextPath}/">--%>
	<base href="${pageContext.request.scheme}://${pageContext.request.serverName}
	:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
	<script type="text/javascript">
		function del(deptno){
			var ok=window.confirm("删除了不可恢复");
			if(ok){
				// 发送请求
				window.location="dept/del?deptno="+deptno;
			}
		}
	</script>
	
	
</head>

<body>
<h3>Welcome ${username}</h3>
<h1>部门</h1><br >
<table border="1px" align="center" width="60%">
	<tr>
		<th>编号</th>
		<th>名称</th>
		<th>地区</th>
		<th>操作</th>
	</tr>

	<c:forEach items="${deptList}" var="dept" varStatus="deptStatus">
		<tr>
			<td>${deptStatus.count}</td>
			<td>${dept.deptno}</td>
			<td>${dept.dname}</td>
			<td>${dept.loc}</td>
			<td>
				<a href="javascript:void(0)" onclick="del(${dept.deptno})">删除</a>
				<a href="dept/detail?path=edit&deptno=${dept.deptno}">修改</a>
				<a href="dept/detail?path=detail&deptno=${dept.deptno}">详情</a>
			</td>

		</tr>
	</c:forEach>
</table>

<%--${pageContext.request.serverName}--%>
<a href="JSP/add.jsp">新增部门</a><br>
<a href="user/logout">[安全退出]</a>
</body>
</html>