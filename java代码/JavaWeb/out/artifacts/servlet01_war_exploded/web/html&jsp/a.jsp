<%--
  Created by IntelliJ IDEA.
  User: beilinanju
  Date: 2022/1/23
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!//定义求和函数
    int add(int x,int y){
        return x+y;
    }
%>


<html>
<head>
    <title>a Title</title>
</head>
<body>
    <%
        int result=add(1,2);
    %>
    1+2=<%=result%>
</body>
</html>
