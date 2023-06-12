<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: beilinanju
  Date: 2022/4/3
  Time: 23:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page isELIgnored="true" %>--%>
<%
    pageContext.setAttribute("user","Usrenae");
    pageContext.setAttribute("mine","Usrenae");
    request.setAttribute("user","hello");

    String []arr={"Alaska","Boston"};
    pageContext.setAttribute("array",arr);

    Map<String,String >map=new HashMap<>();
    map.put("who","lisi");
    request.setAttribute("whoMap",map);

    List<String>list=new ArrayList<>();
    list.add("Hawaii");
    request.setAttribute("list",list);
%>

${user}
${user==mine}

${requestScope.user}
${array[0]}
${whoMap.who}
\${whoMap["who"]}
${list[0]}

<br>

${pageContext.request.contextPath}
${10+"20"}
${empty user}
${user!=null ?20:30}
${user eq null?20:30}