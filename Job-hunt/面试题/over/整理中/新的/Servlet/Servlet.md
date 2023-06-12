## Servlet（Server Applet）

- Java Servlet的简称
  - 称为小服务程序或服务连接器，用Java编写的服务器.具有独立于平台和协议的特性，主要功能在于交互式地浏览和生成数据，生成动态Web内容。
  - 狭义
    - java语言实现的一个接口
  - 广义
    - 任何实现了Servlet接口的类

## 软件系统架构

- C/S架构(Client/Server--客户端-服务器 
  - 优点
    - 速度快--大量数据已经在客户端
    - 界面酷炫
      - 专门语言去写，更加灵活
    - 服务器压力小
    - 安全
      - 数据在客户端有缓存，服务器崩了也影响不大
  - 缺点
    - 升级维护难
      - 每个客户端都要更新
- B/S架构(Browser/Server) --浏览器-服务器
  - 优点
    - 升级维护方便
      - 只需要升级浏览器即可
    - 不需要下载特定的客户端
  -  缺点
    - 速度慢
    - 体验差
       - Browser只支持三种语言
    - 不安全
       - 服务器崩溃了就没了

## 服务器软件

- Web服务器
  - 实现了JavaEE的Servlet和Jsp两个核心规范
    - TomCat
    - Jetty
- 应用服务器
  	实现了JavaEE的所有规范（13个）

  - Jboss

  - WebLogic

  - WebSphere
- 应用服务器包含了Web服务器
    		Jboss内嵌了一个TomCat服务器
- TomCat（又名Catalina)
     - Apche旗下，免费开源的轻量级服务器，体积小，运行速度快
     - java语言编写
     - 作者在该风景秀丽的岛Catalina上开发了该服务器
- Tomcat服务器要想运行，需要先有jre，所以要先安装JDK，配置java运行环境。

## 资源

- 一个路径代表一个资源
- 可能是静态。或者动态资源（Java小程序）
- 请求路径和Servlet之间有对应关系

##  协议

![角色和协议](imgs/BS结构系统的角色和协议.png)

## JavaEE版本

- 如果你之前的项目还是在使用javax.servlet.Servlet，那么你的项目无法直接部署到Tomcat10+版本上。
- 你只能部署到Tomcat9-版本上。在Tomcat9以及Tomcat9之前的版本中还是能够识别javax.servlet这个包。
## Servlet对象生命周期

- Servlet对象由Web Container来维护

  - Tomcat服务器也被称为Web容器
  - 创建、调用、销毁等由Tomcat来管理
  - Web容器底层维护一个HashMap存储请求路径和Servlet对象的映射关系
    - 如key=/sutdent			value=ServletList对象

  - 自己手动new的Servlet对象不受Tomcat管理

- 服务器启动时不会创建Servlet对象，只有对响应路径发送了请求才创建。减少了内存开销
  - 怎么让服务器启动的时候创建Servlet对象呢？
  - 在servlet标签中添加<load-on-startup>子标签，在该子标签中填写整数，越小的整数优先级越高。

```xml
<servlet>
    <servlet-name>aservlet</servlet-name>
    <servlet-class>com.bjpowernode.javaweb.servlet.AServlet</servlet-class>
    <load-on-startup>0</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>aservlet</servlet-name>
    <url-pattern>/a</url-pattern>
</servlet-mapping>
```

- 生命周期
  - 默认情况下服务器启动的时，Servlet对象并没有被实例化
  - 用户发送第一次请求的时

	```java
	Servlet无参数构造方法执行了				//调用无参数构造方法。创建对象
	Servlet's init method execute!		   //执行init，完成初始化。此时已经存在对象
	Servlet's service method execute!		//执行service，提供服务
	```

	- 用户每发送一次请求都执行一次service()
	- 服务器关闭时，调用destory()方法，此时对象还存在。destory()执行结束后，内存被Tomcat释放
	
- 是假单例模式。对象只有一个，但是构造方法不是私有化

## init()和无参构造

- 当我们Servlet类中编写一个有参数的构造方法，没有无参数的构造方法时。会导致出现500错误，无法实例化Servlet对象
- 能代替吗
  - Servlet规范中有要求，作为javaweb程序员，编写Servlet类的时候，不建议手动编写构造方法，因为编写构造方法，很容易让无参数构造方法消失，这个操作可能会导致Servlet对象无法实例化。所以init方法是有存在的必要的。
- 什么时候使用init方法
  - 很少用
  - 通常在init方法当中做初始化操作，并且这个初始化操作只需要执行一次。例如：初始化数据库连接池，初始化线程池....
- 什么时候使用init方法
  - 也是很少用
  - 关闭资源等

## 适配器模式改造Servlet

- Servlet的实现类只有2个
  - All Known Implementing Classes:GenericServlet`, `HttpServlet

- 背景
  - 通常只需要service方法，其他方法大部分情况下是不需要使用的。
  - 为了不每次都要实现其他四个方法，代码更加简洁优雅
- 编写GenericServlet类。
  - 实现Servlet接口除service的四个方法。
  - 将service设为抽象方法
  - 子类继承GenericServlet类，只实现service即可

```java
public abstract class GenericServlet implements Servlet{
    //transient 修饰后不可序列化
    private transient ServletConfig config;//为了保留init中的参数(局部变量)servletConfig
    
    //final不希望重写。因为重写后有可能忘了设置config。出现null引用异常
    public final void init(ServletConfig servletConfig) throw ServletException{//有ServletConfig参数的一定会被Tomcat执行
        this.config=servletConfig;
        this.init();//提供可能
    }
    
    //留一个不同参数的给子类重写
    public void init() throw ServletException{
        
    }
    
    public abstract void service(ServletRequest request,ServletResponse response)
        throw ServletException,IOException;
    
    public ServletConfig getServletConfig(){//拿到ServletConfig的信息
        return config;
    }
    ... 
}
```

- Tomcat服务器伪代码
  - 这些类都是Servlet规范里的。Tomcat只是实现
```java
public class Tomcat {
        public static void main(String[] args){
            // .....
            // Tomcat服务器伪代码
            // 创建LoginServlet对象（通过反射机制，调用无参数构造方法来实例化LoginServlet对象）
            Class clazz = Class.forName("LoginServlet");
            Object obj = clazz.newInstance();
            
            // 向下转型
            Servlet servlet = (Servlet)obj;
            
            // 创建ServletConfig对象
            // Tomcat服务器负责将ServletConfig对象实例化出来。
            // 多态（Tomcat服务器完全实现了Servlet规范）
            ServletConfig servletConfig = new org.apache.catalina.core.StandardWrapperFacade();
            
            // 调用Servlet的init方法
            servlet.init(servletConfig);//调用的是有参的、不希望重写的那个
            
            // 调用Servlet的service方法
            // ....
            
        }
    }
```

## ServletConfig

- 什么是ServletConfig？
  - Configuration
  - Servlet对象的配置信息对象。
  - ServletConfig对象中封装了<servlet></servlet>标签中的配置信息。（web.xml文件中servlet的配置信息）

- 相关

  - Tomcat解析web.xml文件，将<servlet></servlet>标签的配置信息封装到ServletConfig对象中
  - Tomcat服务器调用Servlet对象的init方法的时候需要传一个ServletConfig对象的参数给init方法

  - ServletConfig接口的实现类是Tomcat服务器提供

  - 一个Servlet对象对应有一个ServletConfig对象。

- 配置信息

```xml
<servlet>
        <servlet-name>conf</servlet-name>
        <servlet-class>ServletConfig02</servlet-class>

        <init-param>
            <param-name>driver</param-name>
            <param-value>com.mysql.cj.jdbc.Driver</param-value>
        </init-param>

    </servlet>
```



- 获取信息

```java
//继承GenericServlet类后
ServletConfig conf=this.getServletConfig();

//获取配置信息
String driver=conf.getInitParameter("dirver");
//也可以直接通过this调用。因为GenericServlet实现了ServletConfig接口
String driver=this.getInitParameter("dirver");
/*
public ServletContext getServletContext() {
        return this.getServletConfig().getServletContext();
    }//所以调用的是同一个
*/
```

## ServletContext

- Servlet上下文（Servlet的四周环境对象）。也叫应用域
  - 一个webapp对应一个ServletContext对象，通常对应web.xml文件
  - 封装了全局配置信息。所有Servlet对象都可以访问
- 用来存放全局数据
  - 数据量小、所有用户共享、又不修改，这样的数据放到ServletContext这个应用域当中，会大大提升效率。
  - 因为应用域相当于一个缓存，放到缓存中的数据，用的时候，不需要从数据库中再次获取

- 配置信息

```xml
    <context-param>
        <param-name>pageSize</param-name>
        <param-value>10</param-value>
    </context-param>
```

- 获取信息

```java
ServletContext application=this.getServletContext();
String pageSize=application.getInitParameter("pageSize");
```

- 存储数据

```java

public void setAttribute(String name, Object value); // map.put(k, v)

public Object getAttribute(String name); // Object v = map.get(k)

public void removeAttribute(String name); // map.remove(k)
```

- 其他信息

```java
public String getContextPath();//webapp的根路径。可以不写死

public String getRealPath(String path);// 通过相对路径获取文件的绝对路径（真实路径）。文件IO

/*
通过ServletContext对象也是可以记录日志的。存放在CATAKINA_HOME/logs
IDEA的Tomcat是一个副本，所以CATAKINA_HOME是
C:\Users\beilinanju\AppData\Local\JetBrains\IntelliJIdea2020.1\tomcat\Tomcat_9_0_60_Servlet\logs
*/
/*
日志文件
catalina.2021-11-05.log 服务器端的java程序运行的控制台信息。
localhost.2021-11-05.log ServletContext对象的log方法记录的日志信息存储到这个文件中。
localhost_access_log.2021-11-05.txt 访问日志
*/
public void log(String message);
public void log(String message, Throwable t);
```

## 缓存机制

- 向ServletContext应用域中存储数据，也等于是将数据存放到缓存cache当中了。
- 堆内存当中的字符串常量池。
  - "abc" 先在字符串常量池中查找，如果有，直接拿来用。如果没有则新建，然后再放入字符串常量池。
- 堆内存当中的整数型常量池。
  - [-128 ~ 127] 一共256个Integer类型的引用，放在整数型常量池中。没有超出这个范围的话，直接从常量池中取。
- 连接池(Connection Cache)
  - 这里所说的连接池中的连接是java语言连接数据库的连接对象：java.sql.Connection对象。
  - JVM是一个进程。MySQL数据库是一个进程。进程和进程之间建立连接，打开通道是很费劲的。是很耗费资源的。怎么办？可以提前先创建好N个Connection连接对象，将连接对象放到一个集合当中，我们把这个放有Connection对象的集合称为连接池。每一次用户连接的时候不需要再新建连接对象，省去了新建的环节，直接从连接池中获取连接对象，大大提升访问效率。
  - 连接池
    - 最小连接数
    - 最大连接数
    - 连接池可以提高用户的访问效率。当然也可以保证数据库的安全性。
- 线程池
  - Tomcat服务器本身就是支持多线程的。
  - Tomcat服务器是在用户发送一次请求，就新建一个Thread线程对象吗？
    - 当然不是，实际上是在Tomcat服务器启动的时候，会先创建好N多个线程Thread对象，然后将线程对象放到集合当中，称为线程池。用户发送请求过来之后，需要有一个对应的线程来处理这个请求，这个时候线程对象就会直接从线程池中拿，效率比较高。
    - 所有的WEB服务器，或者应用服务器，都是支持多线程的，都有线程池机制。
- redis
  - NoSQL数据库。非关系型数据库。缓存数据库。
- mongoDB

## 模板方法

- 定义核心的算法骨架，具体的实现步骤可以延迟到子类中实现
- 什么是设计模式？
  - 某个问题的固定的解决方案。(可以被重复使用)
- 核心算法通常是final(也可以不是)。有(抽象)方法给子类实现

```java
public abstract class Person{
    //final防止覆盖了核心算法被覆盖，得到了保护，提高了代码的复用性
    public final void day(){//固定的步骤
        //首先
        qiChuang();
        
        //第二步
        chiZao();
        
        //第三步
        doSome();
        
        //第四步
        chiWan();
        
        //最后
        goBed();
    }
    
    //子类的某些行为是相同的，可以先在模板类中定义，得到复用
    public void qiChuang(){}
    public void chiZao(){}
   	
    //具体的步骤留给子类实现
    public abstract doSome();
    
    public ...
}
```



## HttpServlet源码分析

```java
// HttpServlet模板类。
public abstract class HttpServlet extends GenericServlet {//init方法在GenericServlet
    // 用户只要发送一次请求，这个service方法就会执行一次。
    @Override
    //req和res传递进来时本身是由Tomcat创建的HttpServletRequest，HttpServletResponse。只不过是用父类引用创建的吧
    public void service(ServletRequest req, ServletResponse res)
        throws ServletException, IOException {

        HttpServletRequest  request;
        HttpServletResponse response;

        try {
            // 将ServletRequest和ServletResponse向下转型为带有Http的HttpServletRequest和HttpServletResponse
            //为什么可以向下转型？？？只是对引用向下转而已
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) {
            throw new ServletException(lStrings.getString("http.non_http"));
        }
        // 调用重载的service方法。
        service(request, response);
    }
    
    // 这个service方法的两个参数都是带有Http的。
    // 这个service是一个模板方法。
    // 在该方法中定义核心算法骨架，具体的实现步骤延迟到子类中去完成。
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // 获取请求方式
        // 这个请求方式最终可能是：""
        // 注意：request.getMethod()方法获取的是请求方式，可能是七种之一：
        // GET POST PUT DELETE HEAD OPTIONS TRACE
        String method = req.getMethod();

        // 如果请求方式是GET请求，则执行doGet方法。
        if (method.equals(METHOD_GET)) {
            long lastModified = getLastModified(req);
            if (lastModified == -1) {
                // servlet doesn't support if-modified-since, no reason
                // to go through further expensive logic
                doGet(req, resp);
            } else {
                long ifModifiedSince;
                try {
                    ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                } catch (IllegalArgumentException iae) {
                    // Invalid date header - proceed as if none was set
                    ifModifiedSince = -1;
                }
                if (ifModifiedSince < (lastModified / 1000 * 1000)) {
                    // If the servlet mod time is later, call doGet()
                    // Round down to the nearest second for a proper compare
                    // A ifModifiedSince of -1 will always be less
                    maybeSetLastModified(resp, lastModified);
                    doGet(req, resp);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                }
            }

        } else if (method.equals(METHOD_HEAD)) {
            long lastModified = getLastModified(req);
            maybeSetLastModified(resp, lastModified);
            doHead(req, resp);

        } else if (method.equals(METHOD_POST)) {
            // 如果请求方式是POST请求，则执行doPost方法。
            doPost(req, resp);

        } else if (method.equals(METHOD_PUT)) {
            doPut(req, resp);

        } else if (method.equals(METHOD_DELETE)) {
            doDelete(req, resp);

        } else if (method.equals(METHOD_OPTIONS)) {
            doOptions(req,resp);

        } else if (method.equals(METHOD_TRACE)) {
            doTrace(req,resp);

        } else {
            //
            // Note that this means NO servlet supports whatever
            // method was requested, anywhere on this server.
            //

            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[1];
            errArgs[0] = method;
            errMsg = MessageFormat.format(errMsg, errArgs);

            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
        }
    }
    
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        // 报405错误
        String msg = lStrings.getString("http.method_get_not_supported");
        sendMethodNotAllowed(req, resp, msg);
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // 报405错误
        String msg = lStrings.getString("http.method_post_not_supported");
        sendMethodNotAllowed(req, resp, msg);
    }
    
}

/*
通过以上源代码分析：
	假设前端发送的请求是get请求，后端程序员重写的方法是doPost
	假设前端发送的请求是post请求，后端程序员重写的方法是doGet
	会发生什么呢？
		发生405这样的一个错误。（因为调用的是父类HttpServlet的doGet或doPost）
		405表示前端的错误，发送的请求方式不对。和服务器不一致。不是服务器需要的请求方式。
	
	通过以上源代码可以知道：只要HttpServlet类中的doGet方法或doPost方法执行了，必然405.

怎么避免405的错误呢？
	后端重写了doGet方法，前端一定要发get请求。
	后端重写了doPost方法，前端一定要发post请求。
	这样可以避免405错误。
	
	这种前端到底需要发什么样的请求，其实应该后端说了算。后端让发什么方式，前端就得发什么方式。
	
有的人，你会看到为了避免405错误，在Servlet类当中，将doGet和doPost方法都进行了重写。
这样，确实可以避免405的发生，但是不建议，405错误还是有用的。该报错的时候就应该让他报错。
如果你要是同时重写了doGet和doPost，那还不如你直接重写service方法好了。这样代码还能
少写一点。
*/
```

## HttpServletRequest接口

- 简介
  - HttpServletRequest接口的实现类由Tomcat写的，并创建实现类的对象。
    - 实现类RequestFacade:out.write(request);//org.apache.catalina.connector.RequestFacade
  - 用户发送请求的时，遵循了HTTP协议，发送的是HTTP的请求协议，Tomcat服务器将HTTP协议中的信息以及数据全部解析出来，然后Tomcat服务器把这些信息封装到HttpServletRequest对象当中
  - javaweb程序员面向HttpServletRequest接口编程，调用方法就可以获取到请求的信息

- request和response对象的生命周期

  - request对象和response对象，一个是请求对象，一个是响应对象。这两个对象只在当前请求中有效。
  - 一次请求对应一个request。
  - 两次请求则对应两个request。
  - .....
- 获取前端form提交的数据

```java
Map<String,String[]> getParameterMap() //这个是获取Map
Enumeration<String> getParameterNames() //这个是获取Map集合中所有的key
String[] getParameterValues(String key) //根据key获取Map集合的value。复选框提交的可以用这个
String getParameter(String key)  //获取value这个一维数组当中的第一个元素。这个方法最常用。
```

- 其他常用方法

```java
// 获取客户端的IP地址
String remoteAddr = request.getRemoteAddr();

// get请求在请求行上提交数据。
// post请求在请求体中提交数据。

/*
POST请求的乱码问题
	设置请求体的字符集。（显然这个方法是处理POST请求的乱码问题。这种方式并不能解决get请求的乱码问题。）
	Tomcat10之后，request请求体当中的字符集默认就是UTF-8，不需要设置字符集，不会出现乱码问题。
	Tomcat9前（包括9在内），如果前端请求体提交的是中文，后端获取之后出现乱码，怎么解决这个乱码？执行以下代码。
*/
request.setCharacterEncoding("UTF-8");

/*
乱码显示?
	在Tomcat9之前（包括9），响应中文也是有乱码的，怎么解决这个响应的乱码？
	在Tomcat10之后，包括10在内，响应中文的时候就不在出现乱码问题了。以上代码就不需要设置UTF-8了
	只在HttpServlet中有这个方法。Servlet和GenericServlet中都没有
*/
response.setContentType("text/html;charset=UTF-8");



/*get请求乱码问题怎么解决？
	修改CATALINA_HOME/conf/server.xml配置文件
	不过从Tomcat8之后，URIEncoding的默认值就是UTF-8，所以GET请求也没有乱码问题了。
*/
<Connector URIEncoding="UTF-8" />

    
// 获取应用的根路径
String contextPath = request.getContextPath();

// 获取请求方式
String method = request.getMethod();

// 获取请求的URI
String uri = request.getRequestURI();  // /aaa/testRequest

// 获取servlet path
String servletPath = request.getServletPath(); //   /testRequest
```



## 前端提交数据格式

- 前端的form表单提交了数据username=abc&userpwd=111&aihao=s&aihao=d&aihao=tt

```java
Map<String,String>
    key存储String
    value存储String
    这种想法对吗？不对。
    如果采用以上的数据结构存储会发现key重复的时候value覆盖。
    key         value
    ---------------------
    username    abc
    userpwd     111
    aihao       s
    aihao       d
    aihao       tt
    这样是不行的，因为map的key不能重复。
Map<String, String[]>
    key存储String
    value存储String[]
    key				value
    -------------------------------
    username		{"abc"}
    userpwd			{"111"}
    aihao			{"s","d","tt"}
```

- 前端永远提交的是字符串，后端获取的也永远是字符串

## 应用域对象

- ServletContext （Servlet上下文对象。）
- 什么情况下会考虑向ServletContext这个应用域当中绑定数据呢？

  - 第一：所有用户共享的数据。
  - 第二：这个共享的数据量很小。
  - 第三：这个共享的数据很少的修改操作。
  - 实际上向应用域当中绑定数据，就相当于把数据放到了缓存（Cache）当中，然后用户访问的时候直接从缓存中取，减少IO的操作，大大提升系统的性能，所以缓存技术是提高系统性能的重要手段。

## 请求域对象

- ServletRequest

  - 一个请求对应一个请求域对象。请求域只在一次请求内有效。
  - “请求域”对象要比“应用域”对象范围小很多。生命周期短很多。

- 请求域对象也有这三个方法
 ```java
 void setAttribute(String name, Object obj); // 向域当中绑定数据。
 Object getAttribute(String name); // 从域当中根据name获取数据。
 void removeAttribute(String name); // 将域当中绑定的数据移除
 
 //使用
 request.setAttribute("name",obj);
 ```

- 请求域和应用域的选用原则？

  - 尽量使用小的域对象，因为小的域对象占用的资源较少。

## 转发

- 保证是一次请求
  - 实现两个Servlet怎么共享数据
  - forward()调用后会跳转到目标路径
- 不能直接在AServlet中new  BServlet。
  - 可以实现数据共享，但是因为自己创建的Servlet对象不受Tomcat管理，是非法对象。不被销毁等

```java
//第0步：可以用request.setAttribute()存一些数据

// 第一步：获取请求转发器对象
RequestDispatcher dispatcher = request.getRequestDispatcher("/student");//转发的路径以“/”开始，不加项目名。
// 第二步：调用转发器的forward方法完成跳转/转发
dispatcher.forward(request,response);
// 第一步和第二步代码可以联合在一起。
request.getRequestDispatcher("/b").forward(request,response);
```



## 重定向

- 形式

```java
//要加项目名，因为是前端发送请求。服务器中可能有多个webapp
response.sendRedirest(request.getContextPath()+"/dept/list");
```

- 发送响应到浏览器，浏览器根据响应发送一次请求到所给地址
- 浏览器一共发送了2次请求，浏览器地址栏上显示的是最后一次请求的地址

## 转发or重定向

- 相同
  - 转发的目标可以是(servlet, JSP file, or HTML file)
  - 都是实现资源跳转
- 不同
  - 跳转页面后，由于地址栏信息不变。转发-刷新页面会再发一次第一个页面的请求（可能导致刷新问题，也就是重复服务而出错）。重定向-刷新则是发送第二个页面的请求。（第一个页面是服务，第二个页面是处理结果展示的情况下）
  - 转发是服务器内部完成，重定向是浏览器再次发送新的请求
- 选择
  - 如果一个Servlet向request域中绑定了数据，希望转给另一个Servlet，则使用转发。
    - 如使用JavaBean实现Servlet到JSP传递数据
  - 其他的均使用重定向（因为转发有刷新问题）
