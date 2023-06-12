<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: beilinanju
  Date: 2022/4/5
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
    List<String >list=new ArrayList<>();
    list.add("lisi");
    list.add("wangwu");
    request.setAttribute("list",list);
%>

<c:forEach items="${list}" var="name">
    ${name}<br>
</c:forEach>
<c:forEach var="i" begin="0" end="9" step="1">
    ${i}
</c:forEach>
<c:forEach items="${list}" var="name1" varStatus="num">
    ${name1}<br>${num.count}
</c:forEach>

<c:if test="${empty param.username}">
    <h3>用户名不能为空</h3>
</c:if>
<c:if test="${empty param.username}" var="isNull" scope="request">
    <h3>var中存的是test的结果。只能是true或false。
        scope表示把var存进某个域，相当于setAttribute("isNull",false)</h3>
</c:if>

