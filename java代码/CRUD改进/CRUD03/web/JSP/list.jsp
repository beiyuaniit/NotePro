<%@ page import="java.util.List" %>
<%@ page import="Beans.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>部门列表</title>
	<script type="text/javascript">
		function del(deptno){
			var ok=window.confirm("删除了不可恢复");
			if(ok){
				// 发送请求
				window.location="<%= request.getContextPath()%>/dept/del?deptno="+deptno;
			}
		}
	</script>
	
	
</head>
<body>

<h1>部门</h1><br >
<table border="1px" align="center" width="60%">
	<tr>
		<th>编号</th>
		<th>名称</th>
		<th>地区</th>
		<th>操作</th>
	</tr>

	<%

		List<Dept> deptList=(List<Dept>)request.getAttribute("deptList");
		for (Dept dept:deptList){
	%>

	<tr>
		<td><%=dept.getDeptno()%></td>
		<td><%=dept.getDname()%></td>
		<td><%=dept.getLoc()%></td>
		<td>
			<a href="javascript:void(0)" onclick="del(<%=dept.getDeptno()%>)">删除</a>
			<a href="<%=request.getContextPath()%>/dept/detail?path=edit&deptno=<%=dept.getDeptno()%>">修改</a>
			<a href="<%=request.getContextPath()%>/dept/detail?path=detail&deptno=<%=dept.getDeptno()%>">详情</a>
		</td>
	</tr>
	<%
		}
	%>

</table>
<a href="<%=request.getContextPath()%>/JSP/add.jsp">新增部门</a><br>
<a href="<%=request.getContextPath()%>/user/logout">[安全退出]</a>
</body>
</html>