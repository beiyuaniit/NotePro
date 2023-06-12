# ==二版==

## 关于转发

- 大部分改为重定向

```java
response.sendReddirest(request.getContextPath()+"/dept/list")
```

## 使用模板方法来改造

```java
/**
 * 二版
 * 使用注解配置
 * 由于每个请求写一个类，导致类的数量太多，改为一个方法对应一个请求。（从类爆炸到方法爆炸
 *
 * 项目名不写死  window.location='"+contextPath+"/dept/delete?deptno='+deptno;
 *
 * 问题
 * 静态的页面还是写死了
 * 现在就不能使用doGet和doPost。但是对象还是HttpServletRequest req, HttpServletResponse res，也还行
 */
protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path=req.getServletPath();

        //switch通过equal判断字符串
        switch (path){
            case "/dept/list":
                doList(req,resp);
                break;
            case "/dept/edit":
                Edit(req,resp);
                break;
            case "/dept/doEdit":
                doEdit(req,resp);
                break;
            case "/dept/detail":
                doDetail(req,resp);
                break;
            case "/dept/del":
                doDel(req,resp);
                break;
            case "/dept/add":
                doAdd(req,resp);
                break;
 }
```

# ==版三==

## 分析
- Java程序中写前端代码缺点
  - 编写难度大（不会报错
  - 代码不美观 
  - 耦合度很高
  - 维护成本高。改动一点点java代码都要重新编译生成.class文件，重新打war包发布到服务器
- 解决
  - Servlet收集数据
  - JavaBean封装数据
  - JSP展示数据
## 是否存在刷新问题
  - 尽管内部Servlet和JSP是转发而不是重定向
  - 但是原页面发送请求，直到JSP展示。跳到了新的请求路径，所以没有在JSP页面刷新会重复处理的问题。（跟内部没有关系

## Servlet收集并转发

```java
public class DeptServlet extends HttpServlet {
    
    private void doList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
        //声明一个容器.装咖啡豆的袋子
        List<Dept> deptList=new ArrayList<>();

		//核心步骤
        try {
           
            while (rs.next()){
                //从结果集中取出
                String deptno=rs.getString("deptno");
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");

                //封装到JavaBean中
                Dept dept=new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);

                //放到袋子里，方便转发
                deptList.add(dept);

            }
        }

        //存到请求域中
        req.setAttribute("deptList",deptList);

        //转发给Servlet展示
        req.getRequestDispatcher("/JSP/list.jsp").forward(req,resp);//内部转发不用项目名

    }
}
```

## JSP取出展示

```jsp
	<%

		List<Dept> deptList=(List<Dept>)request.getAttribute("deptList");
		for (Dept dept:deptList){
	%>

	<tr>
		<td><%=dept.getDeptno()%></td>
		<td><%=dept.getDname()%></td>
		<td><%=dept.getLoc()%></td>
		<td>
			<a href="javascript:void(0)" onclick="del(10)">删除</a>
			<a href="<%=request.getContextPath()%>/JSP/edit.jsp">修改</a>
			<a href="<%=request.getContextPath()%>/JSP/detail.jsp">详情</a>
		</td>
	</tr>
	<%
		}
	%>
```

## 访问页面顺序
  - index.jsp->发送请求到Servlet->JSP展示
  - 如果直接访问JSP,则还没有拿到数据,会报错

## 修改和详情请求合在一起

  - 业务逻辑一致，返回路径不同
  
  - 因为都是先取出当前部门数据。所以取数据可以合在一个请求，也就是一个方法中
  
  - ```jsp
    <%-- 定义不同的path参数做区分--%>
    <a href="<%=request.getContextPath()%>/dept/detail?path=edit&deptno=<%=dept.getDeptno()%>">修改</a>
    <a href="<%=request.getContextPath()%>/dept/detail?path=detail&deptno=<%=dept.getDeptno()%>">详情</a>
    ```
  
  - 根据不同path参数重定向
  
  - ```java
    req.getRequestDispatcher("/JSP/"+path+".jsp").forward(req,resp);
    ```

## 实现登录界面

- 思路

  - 添加用户表t_user
    - 存储用户信息，最基本要包括用户名和密码
    - 密码应使用密文而不是明文
      - 可用cmd5加密
    - 最好有与业务无关的递增id
  - 创建登录界面
    - form表单，post方式提交
  - 后台有Servlet处理
    - 成功则跳到部门列表页面
    - 失败则跳转失败页面
  - 提供登录失败页面

  - 按照思路来一般不会错

- 直接编写一个登录页面存在问题

  - 这个登录没有真正起到拦截作用
    - 不输入用户名和密码，直接输入部门列表url也可以访问
## session登录拦截

  - 思路

    - 用户登录成功后，将用户信息存储在session中

       ```java
       //LoginServlet中
       if(isSuccess){
           HttpSession session =req.getSession();//不要加false参数，因为密码正确，必须拿到session对象。没有则创建
           session.setAttribute("username",username);//存储信息进去
       }
       ```

    - 访问该项目下其他路径时要验证

       ```java
       //DeptServlet中
       HttpSession session=req.getSession(false);//没有拿到说明没有登录成功，也就不用新建了。当然新建也没有问题
       
       //验证
       if(session!=null&&session.getAttribute("username")!=null){
           /**
           *为什么还要判断username不为null？
           因为即使没有登录成功，但是用户访问了其中某个jsp资源。
           服务器创建jsp对象，session作为jsp的9大内置对象之一，会被创建，只是还没有存用户登录成功的数据
           jsp禁用session,不创建session
           	<%@page session="false"%>
           */
       }else{
           
       }
       ```
       
    - 小功能
    
       - 在每个页面都输出相同信息
    
       ```java
       <h2>Welcome <%=session.getAttribute()%></h2>
       ```
    
    - 安全退出
    
      ```java
      @WebServlet("user/lonout")
      if(session!=null){
          session.invalidate();//手动销毁  
      }
      resp.sendRedirect(req.getContextPath());//根路径就是用户首页了
      ```

## Cookie实现十天免登录

- 步骤

  - 实现登录功能

    - 登录成功
      - 跳转到部门列表页面
    - 登录失败
      - 跳转到登录失败页面

  - 添加选项功能

    ```html
    <input type="checkbox" name="isOk" value="ok">十天免登录
    ```
    
  - 添加cookie
  
  ```java
  //都Login函数中
  HttpSession session=req.getSession();
  session.setAttribute("username",username);
  
  //10天免密登录
  String isOk=req.getParameter("isOK");
  if(isOk.equals("ok")){
  	//创建Cookie
  	Cookie cookie1=new Cookie("username",username);
      Cookie cookie2=new Cookie("password",password);//实际开发中是加密
                  
      //设置有效期
      cookie1.setMaxAge(60*60*24*10);
      cookie2.setMaxAge(60*60*24*10);
                  
      //设置关联路径。只要访问项目下路径都携带
      cookie1.setPath(req.getContextPath());
      cookie2.setPath(req.getContextPath());
                  
      //响应给浏览器
      resp.addCookie(cookie1);
      resp.addCookie(cookie2);
  }
  ```
  
  - 使用cookie
  
  ```java
  case"/index.jsp":
      freeLogin(req,resp);//目前只拦截index.jsp。也就是默认界面
   	break;	
  //默认路径中实现判断是否十天免密登录
  	freeLogin(req,resp){
          Cookie[]cookies= req.getCookies();
          String username=null;
          String password=null;
  
          //有则拿到用户名和密码
          if (cookies != null) {
              for(Cookie cookie :cookies){
                  String name=cookie.getName();
                  if(name.equals("username")){
                      username=cookie.getValue();
                  }else if(name.equals("password")){
                      password=cookie.getValue();
                  }
              }
          }
  
          //判断是否为用户名和密码正确
          if(username!=null&&password!=null){
             		...
                  if(rs.next()){
                      isSuccess=true;
                  }
          
              if(isSuccess){
                  //同样要网session中放信息
                  HttpSession session=req.getSession();
                  session.setAttribute("username",username);
                  
                  resp.sendRedirect(req.getContextPath()+"/dept/list");//密码正确登录
              }else {
                  resp.sendRedirect(req.getContextPath()+"login.jsp");//密码错误返回登录界面
              }
              
          }
          else {
              resp.sendRedirect(req.getContextPath()+"login.jsp");//没有从cookie同时拿到用户名和密码
          }
  }
  ```
  
  - 区别和联系
    - cookie都是用户密码正确且勾选了10天免密登录后创建
    - session也可以是在用户手动登录时创建，也可以时cookie通过验证时创建    (都要通过数据库验证才创建)
    - 有了session后，就可以通过DeptServlet的session拦截了
  
  - 安全退出。也要删除cookie
  
  ```java
  private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
          HttpSession session=req.getSession(false);
          if(session!=null){
              session.invalidate();
          }
          //也要把cookie删除
          Cookie [] cookies = req.getCookies();
          if (cookies != null) {
              for(Cookie cookie:cookies){
                  /*
                  不设置的话会存在路径问题
                      可能原cookie是/CRUD,现在的cookie是/CRUD/logout。即使名字啥的相同也不能覆盖
                   所以要重新设置路径，来覆盖该路径下所有cookie
                   */
                  cookie.setMaxAge(0);
                  cookie.setPath(req.getContextPath());
                  resp.addCookie(cookie);
  
              }
          }
          //session和cookie都销毁后才跳转
          resp.sendRedirect(req.getContextPath()+"/login.jsp");
      }
  ```
  

# ==四版==

## 总

- 哪些技术

  - Servlet+JSP+EL+JSTL

- 用HTML的base标签进一步完善。

  - 设置整个页面的基础路径

  ```jsp
  <head>
      //只对页面中没有以/开头的所有路径起作用
      //<base href="http://localhost:8080/CRUD04/">
      /*
      ${pageContext.request.scheme}		通讯协议，如http
      ${pageContext.request.serverName}	服务器ip，如localhost
      ${pageContext.request.serverPort}	端口号，如8080
      ${pageContext.request.contextPath}	项目名，如/CRUD04
      */
      //动态获取
      <base href="${pageContext.request.scheme}://${pageContext.request.serverName}
  	:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
  </head>
  
  <a href="user/logout">[安全退出]</a>
  	等价于<a href="${pageContext.request.contextPath}/user/logout">[安全退出]</a>
  
  //注意，base是HTML的语法，可能在JS中不起作用。所以JS中最好还是这样写.
  //不过测试了下window.location="dept/del?deptno="+deptno;也行
  <script type="text/javascript">
  	function del(deptno){
  		var ok=window.confirm("删除了不可恢复");
  		if(ok){
  			// 发送请求
  			window.location="${pageContext.request.contextPath}/dept/del?deptno="+deptno;
  		}
  	}
  </script>
  
  ```

- 如果遇到了把代码粘贴过来，第一次运行时浏览器报各种500错误。直接重启IDEA就差不多了

# ==版五==

## 总

- 当前项目存在问题
  - 一般会有多个业务处理的Servlet。如DeptServlet、EmpServlet、OrderServlet
  - 显然访问每个Servlet都要用session登录验证。这些代码都是一样的，造成了重复编写

- Filter解决

```java
/*
哪些页面不用拦截
    用户已经登录   session有值
    用户通过index.jsp  cookie免密登录
    下方else去登录界面时"/loginjsp"  因为要再次经过过滤器
    用户在登录界面访问"/user/login"
 */
String path=request.getServletPath();
if(path.equals("/index.jsp")||path.equals("/user/login")||
        path.equals("/login.jsp")||
        (session!=null && session.getAttribute("username")!=null)){
    chain.doFilter(request,response);
}else {
    response.sendRedirect(request.getContextPath()+"/login.jsp");
}
```



- 统计在线人数

  - 服务器的session对象有多少个，有则count++
  - 缺点：不登陆都可能使count++。如就访问个登录界面

- 统计登录的在线人数

  - 增加
    - session.setAttribute("user",userobj); //则count++，
  - 减少
    - session.removeAttribute(user);则count--
    - 或者对象销毁(手动销毁，超时等)
  - 思路
    - User类实现HttpSessionBindingListener接口。在相应的方法中编写代码
    - 将应用域application中的count++
  - 代码

  ```java
  //User类中
  @Override
      public void valueBound(HttpSessionBindingEvent event) {
          ServletContext application=event.getSession().getServletContext();
          
          Object count=application.getAttribute("count");
          if(count==null){//第一个人上线，count为null
              application.setAttribute("count",1);
          }else {
              int i=(Integer)count;
              i++;
              application.setAttribute("count",i);
          }
      }
  
      @Override
      public void valueUnbound(HttpSessionBindingEvent event) {
          ServletContext application=event.getSession().getServletContext();
  
          Integer count=(Integer)application.getAttribute("count");
          count--;
          application.setAttribute("count",count);
      }
  ```

  - 缺点：同一账号，同时在不同浏览器登录，或关闭浏览器后又马上重新登录。都会使多次count++
    - user存应用域可以解决，不过开销大

