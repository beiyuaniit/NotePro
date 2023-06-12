<%--
  Created by IntelliJ IDEA.
  User: beilinanju
  Date: 2022/5/6
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Ajax</title>
      <script type="text/javascript">

        function doAjax() {
          //创建异步对象
          var xmlHttp=new XMLHttpRequest();

          //绑定事件
          xmlHttp.onreadystatechange=function () {

          }

          //初始请求数据
          xmlHttp.open("get","/getData",true);

          //发送请求
          xmlHttp.send();
        }


      </script>
  </head>
  <body>
<%--  <p>全局刷新</p>--%>
<%--    <form method="get" action="/AJ/getData">--%>
<%--      姓名：<input type="text" name="name"><br>--%>
<%--      身高：<input type="text" name="h"><br>--%>
<%--      体重:<input type="text" name="w"><br>--%>
<%--      <input type="submit" value="计算">--%>
<%--    </form>--%>


  <p>局部刷新</p>

      姓名：<input type="text" name="name"><br>
      身高：<input type="text" name="h"><br>
      体重:<input type="text" name="w"><br>
      <input type="button" value="计算" onclick="doAjax()">
  </body>
</html>
