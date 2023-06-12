## JSP

- 概述
  - Java Servlet Page
  - JavaEE的13个子规范之一
  - 每一个web容器都内置一个JSP翻译器

- 解决在直接java中写前端代码问题的思路
  - 能不能不手写Servlet类，而是让机器自动生成？我们只负责写“前端代码”，机器自动翻译为Servlet这样的java程序。然后机器自动将其编译成.class文件,JVM调用.class文件中方法.
- 翻译后生成的Servlet类和编译后的class文件的位置

```java
CATALINA_BASE:     C:\Users\beilinanju\AppData\Local\JetBrains\IntelliJIdea2020.1\tomcat\Tomcat_9_0_60_CRUD改进_3
    
C:\Users\beilinanju\AppData\Local\JetBrains\IntelliJIdea2020.1\tomcat\Tomcat_9_0_60_CRUD改进_3\work\Catalina\localhost\JSP\org\apache\jsp

//index.jsp->index_jsp.java和index_jsp.class			1.jsp->_1_jsp.java
```

- 为什么给客户演示项目时，先把JSP文件访问一遍？
  - 第一次访问较慢
    - .jsp翻译为.java
    - .java编译为.class
    - 创建对象，调用service()方法
  - 之后较快
    - 直接调用单例servlet对象的service方法

## JSP和Servlet

- JSP实际上是一个Servlet

  - index_jsp继承HttpBase，而HttpBase继承HttpServlt

  - 和Servlet一样，JSP也是单例的（假单例

- 区别:职责不一样

  - Servlet:收集数据
    - 逻辑处理,业务处理等
  - JSP:展示数据

## JSP语法

- 注释

```jsp
<%--JSP 注释 --%>

如果是在<%  %>  <%= %>等编写java代码的地方使用注释,则应该使用java的注释//  /**/  /**  */
```

- 直接编写
  - 当作字符串输出到浏览器。out.writer("str");
  - 如果只是普通字符串或者前端代码,直接输出就行了.没必要用其他形式 

- <%   %>
  - 要以分号结尾
  - Java程序，翻译到service方法中。
  - 和Java方法内编写规则一致,如不能定义修饰符的成员变量,不能有代码块等
  - 多个<% %>  <%= %>是有上下执行顺序的,因为都是翻译到service方法中
- <%!  %>
  - 要以分号结尾
  - Java程序块,翻译到类体中,也就是service方法外
  - 多个<%!  %>之间有顺序.但是<%! %>和<% %>没有上下顺序,因为一个在方法内,一个在方法外.
  - 这个语法很少用,也不建议使用.为什么?
    - JSP是Servlet,Servlet是单例的.
    - Tomcat支持多线程环境
    - 在多线程环境下,静态变量和实例变量一旦涉及修改,就会导致线程安全问题
  - 可以用来定义成员方法
- <%=  %>
  - 取值<%= name %>   可以是表达式,变量名等
  - 翻译成out.print(name)   .放到service方法中
  - 不能有分号结尾,内部可以有任意空格
  - 用于 输出动态数据
- 若JSP中有Java语法错误，则能够翻译生成java文件.但是编译无法通过

## JSP内置对象

- 都是在service方法内有效,也就是<% %>或直接编写的代码中

- out
  - JspWriter  out;
  - 向浏览器输出

## JSP指令

- page
  - 解决response乱码

	```jsp
	<%@page contentType="text/html;charset=UTF-8"%>	
	```

  - 导包
  
  ```jsp
  <%@ page import="java.util.List" %>
  ```
  
  - 不创建session
  
  ```jsp
  <%@page session="false"%>
  ```
  
  - 

## JSP文件后缀名一定是.jsp吗

- 不是固定的,可以配置

- CATALINA_HOME/conf/web.xml中

- ```xml
  <servlet-mapping>
  	<servlet-name>jsp</servlet-name>
      <url-pattern>*.jsp</url-pattern>
      <url-pattern>*.jspx</url-pattern>
  </servlet-mapping>
  ```

- 在Tomcat看来,xx.jsp文件只是普通文本文件.真正执行时和jsp文件没有关系

## JSP指令

- 指导JSP翻译引擎如何工作

- 指令格式

  - <%@指令名 name=value   name=value...  %>

- 指令有哪些

  - include指令：包含指令，在JSP中完成静态代码块的包含（很少用

  - taglib指令：引入标签库。

  - page指令

    ```jsp
    //是否启用session内置对象。禁用时则无法使用
    <%@page session="true|false" %>
    
    //设置响应类型
    <%@page contentType="text/html"  %>  
    <%@page contentType="text/json"  %>
    
    //设置响应类型和响应字符集
    <%@page contentType="text/html;charset=UTF-8"   %>
    <%@page contentType="text/html" pageEnding="UTF-8" %>
    
    //导入Java包。可用逗号隔开
    <%@page import="java.util.List,java.util.Date" %>
    
    /*
    当前Jsp页面发生错误时跳转的页面
    	不会在浏览器和控制台打印错处信息
    一般会在error.jsp中添加友好的提示。
    	因为就算打印出错信息到浏览器，用户也看不懂
    	<h1>
        	服务器繁忙，请稍后    
    	</h1>
    一般也可以在error.jsp中添加如下代码打印信息到控制台
    	启用Jsp9大内置对象exception
    <% page isErrorPage="true" %>
    	<%
    		exception.printStackTrace();
    	%>
    
    */
    <%@page errorPage="/JSP/error.jsp" %> 
    
    //启用内置对象exception.默认是false
    <% page isErrorPage="true" %>
    
    //忽略EL表达式
    <%@page isELIgnored="true" %>
    ```

## JSP9大内置对象

- 有哪些

  - pageContext

  - request

  - session

  - application

    - application是ServletContext  
    - 存取数据时，尽量使用小的域

    

  - exception

    - Throwable类型

  

  - servletConfig

    - 配置 信息

    

  - page

    - page是页面对象，也就是当前jsp，相当于this

    

  - out

    - JspWriter。负责输出

  - response
    - 负责响应

## EL表达式

- 简介

  - Expression Language （表达式语言
  - EL可以代替JSP中的java代码，让程序开启来更加美观、优雅。更容易维护
  - EL表达式可以算是JSP语法的一部分
    - 最终还是翻译生成java代码
  - 

- 作用

  - 从某个域中取出数据

    - pageContext、request、session、application

    - 只能把数据存进这四个域之一后，才能使用${}取值

    - <%%><%!%>直接定义的变量不能取

      - ```jsp
        <%
            pageContext.setAttribute("user","admin");
            String name="lisi";
        %>
        <%!
            String str="yes";
        %>
        //取得到
        ${user}
        
        //取不到。
        ${name}
        ${str}
        
        ```

  - 将取出的数据转为字符串

    - 如果是对象，自动调用对象的toString()方法

  - 将字符串输出到浏览器

    - 相当于<%= %>

- 语法

  - ${表达式}
  - 从哪里取
    - 不指定范围：优先取范围小的域的数据，取不到大范围的同名数据
    - 指定：
      - ${pageScope.data}
      - ${requestScope.data}
      - ${sessionScope.data}
      - ${applicationScope.data}
    - 实际开发中，存数据时name都是不相同。所以xxScope可以省略

  ```jsp
  //取变量的值
  ${name}
  
  /*
  取对象属性
  	底层调用getName()方法。与类有没有name这个属性无关。
  	没有get方法则报错
  */
  ${obj.name}
  ${obj.["name"]}//可用于name中有特殊字符如${obj["abc.def"]}
  ${obj1.obj2.name}
  
  //输出字符串常量。加个双引号就行
  ${"str"}
  
  //Map
  map.put("name","zhangsan");
  request.setAttribute("userMap",map);
  //${map.key}
  ${userMap.name}
  ${userMap["name"]}//也行
  
  //数组
  ${arr[0]}//越界或者找不到则不显示任何东西
  
  //List
  ${list[3]}//和数组一样
  
  //Set.好像不行
  ```

  - 空值处理

    - <%=%>取不到时会显示null
    - EL表达式：浏览不显示任何信息。包括null，更加友好，因为用户不知道null是什么
      - EL表达式本身作用就是用来页面展示的

  - 忽略EL表达式

    ```jsp
    /*
    该页面下，所有EL表达式失效
    	即${name} 会直接变成字符串"${name}"输出到浏览器 
    */
    <%@page isELIgnored="true" %>
    
    //忽略单个
    \${name}
    ```

    

- 使用JSP九大内置对象

  ```jsp
  //从以下代码来看。pageContext.getRequest()没啥用
  <%=pageContext.getRequest()%>
  <%=request%>//都可以拿到request对象
  
  /*
  EL表达式中没有request这个隐式对象。但是有pageContext
  只能这样拿。其他没有的对象也可以通过pageContext拿
  */
  //因为提供了getRequest()方法，所以用.直接拿
  ${pageContext.request}
  
  //获取根路径。<%=%>拿到的是ServletRequest
  <%=((HttpServletRequest)pageContext).getContextPath()%>//Http才有最后那个
  ${pageContext.request.contextPath}
  ```

- EL表达式的其他隐含对象

  - 有哪些

    - pageContext
    - param
    - paramValues
    - initParam
    - ...

  - 怎么用

    ```jsp
    //以下写等价式
    
    //获取用户提交的数据。htpp://localhost:8080/jsp/3.jsp?username=lisi
    //如果是提交多个同名的数据，则都是获取第一个元素
    <%=request.getParamter("username")%>
    ${param.username}
    
    //经典复选框提交多个同名数据。http://localhost:8080/jsp/3.jsp?whos=lisi&whos=wangwu
    ${param.whos[0]},${param.whos[1]}
    
    //获取ServletContext上下文初始化参数。即在web.xml中配置的全局参数
    <%=application.getInitParamter("pageSize")%>
    ${initParam.pageSize}
    ```
  
- EL表达式中的运算符

  - 有哪些

  ```jsp
  <%--
  	算术运算符
      	+	-	/	%
      关系运算符
      	==	!=	<	>	
      	eq	gt	lt
      	//!=\==和eq调用的都是equal()方法
      逻辑运算符
      	!	&&	||
      	not	and	or
      三木运算符
      	?	:
  	取值运算符
          .	[]
      是否为空
          empty
  --%>
  //加法。不会进行字符串拼接，
  ${10+20}		30
  ${10+"20"}		30
  ${10+"abc"}		报错
  
  //三目
  
  //是否为空
  ${empty param.username}//返回true或false
  ```
  

## JSTL标签库

- 概述
  - Java Standard Tag Lib
  - JSTL标签库和EL表达式结合来做页面展示，为了让Jsp中的java代码消失
  
- 配置步骤
  - 以下网址Tomcat9使用的JSTL版本的教程
    - https://m.runoob.com/jsp/jsp-jstl.html
    
    - 将**standard.jar** 和 **jstl.jar** 文件拷贝到 **/WEB-INF/lib/** 下。
    
    - 将需要的库的tld文件放在WEB-INF下。如
    
      - c.tld
    
    - 将需要的库配置web.xml
    
      ```xml
         <jsp-config>
              <taglib>
                  <taglib-uri>http://java.sun.com/jsp/jstl/core</taglib-uri>
                  <taglib-location>/WEB-INF/c.tld</taglib-location>
              </taglib>
          </jsp-config>
      ```
    
    
    
  - TomCat10中
    
    - 在WEB-INF中放入就可以了
      - jakarta.servlet.jsp.jstl-2.0.0.jar
      - jakarta.servlet.jsp.jstl-api-2.0.0.jar
    - 这包里已经有了.tld文件，也不用就行web.xml配置
  
  - 在Jsp中用taglib指令引入
  
  ```jsp
  //c是前缀。可以随意取
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>//核心标签库
  /*
  uri指向一个xxx.tld文件
  .tld文件是一个xml配置文件，描述了标签和类之间的关系
  ```
  
  -
  
  - .tld解析
  
  ```xml
   <tag>
      <description>
          //描述
      </description>
      <name>catch</name>//标签名
      <tag-class>org.apache.taglibs.standard.tag.common.core.CatchTag</tag-class>//对应java类
      <body-content>JSP</body-content>//标签体中可以出现的内容类型
      <attribute>
          <description>
  		//对属性的描述
          </description>
          <name>var</name>//属性名
          <required>false</required>//属性是否为必需
          <rtexprvalue>false</rtexprvalue>//是否支持EL表达式。即该属性的值是否可以为EL
      </attribute>
    </tag>
  ```
  
  
  
- 用法

    - 通过前缀使用标签


```jsp
//遍历
<c:forEach items="${list}" var="name">
    ${name}<br>
</c:forEach>
<c:forEach var="i" begin="0" end="9" step="1">
    ${i}//i被存到某个域中了，（目前来看是pageScope）才能用EL取出
</c:forEach>
<c:forEach items="${list}" var="name1" varStatus="num">
    ${name1}<br>${num.count}// varStatus状态对象的count属性可以获取循环过程的序号
</c:forEach>

//判断
<c:if test="${empty param.username}">
    <h3>用户名不能为空</h3>
    <h3>test的结果只能是true或false</h3>
</c:if>
<c:if test="${empty param.username}" var="isNull" scope="request">
    <h3>var中存的是test的结果。只能是true或false。
        scope表示把var存进某个域，相当于setAttribute("isNull",false)</h3>
</c:if>

//多重判断。if(){}  else if(){} else(){}
<c:choose>
    <c:when test="${param.age<10}">
        
    </c:when>
    <c:when test="${param.age<20}">
        
    </c:when>
    <c:otherwise>
        
    </c:otherwise>
</c:choose>
```

- jstl更高深可以学习自定义标签 
