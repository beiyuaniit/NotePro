## 什么是 Spring  

Spring 是一个开源应用框架，旨在降低应用程序开发的复杂度。它是轻量级、松 散耦合的。它具有分层体系结构，允许用户选择组件，同时还为 J2EE 应用程序 开发提供了一个有凝聚力的框架。它可以集成其他框架，如 Structs、Hibernate、 EJB 等，所以又称为框架的框架。

## Spring 优点

由于 Spring Frameworks 的分层架构，用户可以自由选择自己需要的组件。 Spring Framework 支持 POJO(Plain Old Java Object) 编程，从而具备持续集 成和可测试性。由于依赖注入和控制反转，JDBC 得以简化。它是开源免费的。

## 常用功能

轻量级 - Spring 在代码量和透明度方面都很轻便。

IOC - 控制反转 AOP - 面向 切面编程可以将应用业务逻辑和系统服务分离，以实现高内聚。

容器 - Spring 负 责创建和管理对象（Bean）的生命周期和配置。

MVC - 对 web 应用提供了高 度可配置性，其他框架的集成也十分方便。

事务管理 - 提供了用于事务管理的通 用抽象层。Spring 的事务支持也可用于容器较少的环境。

JDBC 异常 - Spring 的 JDBC 抽象层提供了一个异常层次结构，简化了错误处理策略。

## Spring 模块

Spring 核心容器 – 该层基本上是 Spring Framework 的核心。

​		  Spring Core 

​		 Spring Bean

​		  SpEL (Spring Expression Language) 

​		 Spring Context 

数据访问/集成 – 该层提供与数据库交互的支持。

​		  JDBC (Java DataBase Connectivity) 

​		 ORM (Object Relational Mapping) 

​		 OXM (Object XML Mappers) 

​		  JMS (Java Messaging Service)  Transaction

 Web – 该层提供了创建 Web 应用程序的支持。

​		它包含以下模块：  Web  Web – Servlet  Web – Socket  Web – Portlet 

AOP 

​		 该层支持面向切面编程 Instrumentation  该层为类检测和类加载器实现提供支持。

Test 

​		 该层为使用 JUnit 和 TestNG 进行测试提供支持。 

几个杂项模块:

​	 	Messaging – 该模块为 STOMP 提供支持。它还支持注解编程模型，该模型用 于从 WebSocket 客户端路由和处理 STOMP 消息。 		Aspects – 该模块为与 AspectJ 的集成提供支持。

## Spring配置文件

 Spring 配置文件是 XML 文件。该文件主要包含类信息。它描述了这些类是如何 配置以及相互引入的。但是，XML 配置文件冗长且更加干净。如果没有正确规划 和编写，那么在大项目中管理变得非常困难。

## Spring IOC

- 简介

  Spring IOC 负责创建对象，管理对象（通过依赖注入（DI），装配对象，配置对 象，并且管理这些对象的整个生命周期

- IOC容器

  Spring 框架的核心是 Spring IOC容器。

  容器创建对象，将它们装配在一起，配置它 们并管理它们的完整生命周期。

  Spring 容器使用依赖注入来管理组成应用程序的 组件。

  容器通过读取提供的配置元数据来接收对象进行实例化，配置和组装的指 令。

  该元数据可以通过 XML，Java 注解或 Java 代码提供。

- 优点

  IOC 或 依赖注入把应用的代码量降到最低。它使应用容易测试，单元测试不再需 要单例和 JNDI 查找机制。最小的代价和最小的侵入性使松散耦合得以实现。IOC 容器支持加载服务时的饿汉式初始化和懒加载

## 依赖注入 

常用依赖注入方式

​	构造函数注入 

​	setter 注入 

​	接口注入

Spring 使用的

​	构造函数

​	setter 注入

构造函数和setter注入区别

​	构造函数

​			没有部分注入

​			不会覆盖 setter 属性

​			任意修改都会创建一个新实例 

​			适用于设置很多属性

​	setter注入

​			有部分注入

​			会覆盖 setter 属性

​			任意修改不会创建一个新实例

​			适用于设置少量属性

## 容器类型（BeanFactory，ApplicationContext）

BeanFactory

​		就像一个包含 bean 集合的工厂类。它会在客户端 要求时实例化 bean。 

​		是工厂模式的一个实现，提供了控制反转功能，用来把应用的配置和依 赖从正真的应用代码中分离

    ```java
public interface BeanFactory {
    String FACTORY_BEAN_PREFIX = "&";

    Object getBean(String var1) throws BeansException;
    
    <T> T getBean(String var1, Class<T> var2) throws BeansException;
    
    Object getBean(String var1, Object... var2) throws BeansException;
    
    <T> T getBean(Class<T> var1) throws BeansException;
    
    <T> T getBean(Class<T> var1, Object... var2) throws BeansException;
    
    <T> ObjectProvider<T> getBeanProvider(Class<T> var1);
    
    <T> ObjectProvider<T> getBeanProvider(ResolvableType var1);
    
    boolean containsBean(String var1);
    
    boolean isSingleton(String var1) throws NoSuchBeanDefinitionException;
    
    boolean isPrototype(String var1) throws NoSuchBeanDefinitionException;
    
    boolean isTypeMatch(String var1, ResolvableType var2) throws NoSuchBeanDefinitionException;
    
    boolean isTypeMatch(String var1, Class<?> var2) throws NoSuchBeanDefinitionException;
    
    @Nullable
    Class<?> getType(String var1) throws NoSuchBeanDefinitionException;
    
    @Nullable
    Class<?> getType(String var1, boolean var2) throws NoSuchBeanDefinitionException;
    
    String[] getAliases(String var1);
}
    ```

​	XmlBeanFactory：

​			BeanFactory的一个实现类

​			它 根据 XML 文件中的定义加载 beans。

​			该容器从 XML 文件读取配置元数据并用它 去创建一个完全配置的系统或应用

ApplicationContext 

​		扩展了 BeanFactory 接口。它 在 BeanFactory 基础上提供了一些额外的功能。

     ```java
//继承了很多接口
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory, MessageSource, ApplicationEventPublisher, ResourcePatternResolver {
    @Nullable
    String getId();

    String getApplicationName();
    
    String getDisplayName();
    
    long getStartupDate();
    
    @Nullable
    ApplicationContext getParent();
    
    AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;
}

     ```

​     常用实现

​			FileSystemXmlApplicationContext ：此容器从一个 XML 文件中加 载 beans 的定义，XML Bean 配置文件的全路径名必须提供给			它的构造函数。 第 396 页 共 485 页  

​			ClassPathXmlApplicationContext：此容器也从一个 XML 文件中加 载 beans 的定义，这里，你需要正确设置 classpath 因为这			个容器将在 classpath 里找 bean 配置。 

​			 WebXmlApplicationContext：此容器加载一个 XML 文件，此文件定 义了一个 WEB 应用的所有 bean。

​	子接口

​			WebApplicationContext

​			 		是 ApplicationContext 的扩展。它具有 Web 应用 程序所需的一些额外功能。它与普通的 ApplicationContext 在解析主题和					决定 与哪个 servlet 关联的能力方面有所不同

        ```java
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";
    String SCOPE_REQUEST = "request";
    String SCOPE_SESSION = "session";
    String SCOPE_APPLICATION = "application";
    String SERVLET_CONTEXT_BEAN_NAME = "servletContext";
    String CONTEXT_PARAMETERS_BEAN_NAME = "contextParameters";
    String CONTEXT_ATTRIBUTES_BEAN_NAME = "contextAttributes";

    @Nullable
    ServletContext getServletContext();
}
        ```



区别

​	BeanFactory

​			懒加载 

​			使用语法显式提供资源对象		

​			不支持国际化

​			不支持基于依赖的注解 

​	ApplicationContext

​			即时加载

​			自己创建和管理资源对象

​			支持国际化

​			支持基于依赖的注

## IOC 的实现机制

工厂模式加反射机制

## Bean的创建

- 过程
  - 1、Spring 容器根据配置中的 bean 定义中实例化 bean。
  - 2、Spring 使用依赖 注入填充所有属性，如 bean 中所定义的配置。
  - 3、如果 bean 实现 BeanNameAware 接口，则工厂通过传递 bean 的 ID 来调用 setBeanName()。
  - 4、如果 bean 实现 BeanFactoryAware 接口，工厂通过传 递自身的实例来调用 setBeanFactory()。
  - 5、如果存在与 bean 关联的任何 BeanPostProcessors，则调用 preProcessBeforeInitialization() 方法。
  - 6、如 果为 bean 指定了 init 方法（  的 init-method 属性），那么将调 用它。
  - 7、最后，如果存在与 bean 关联的任何 BeanPostProcessors，则将调用 postProcessAfterInitialization() 方法。
  - 8、如果 bean 实现 DisposableBean 接口，当 spring 容器关闭时，会调用 destory()。
  - 9、如果为 bean 指定了 destroy 方法（  的 destroy-method 属性），那么将 调用它。

## 依赖注入DI

​	依赖注入，是 IOC 的一个方面，

​	不用自己创建对象，而只需要描述它如何被创建。

​	可 不在代码里直接组装你的组件和 服务，但是要在配置文件里描述哪些组件需要哪些服务，之后一个容器（IOC 容 器）负责把他们组	装起来

​	使用时直接注入使用

## Bean scope

Bean的作用范围

- Singleton - 每个 Spring IoC 容器仅有一个单实例。
- Prototype - 每次请求都 会产生一个新的实例。
- Request - 每一次 HTTP 请求都会产生一个新的实例，并 且该 bean 仅在当前 HTTP 请求内有效。
- Session - 每一次 HTTP 请求都会产 生一个新的 bean，同时该 bean 仅在当前 HTTP session 内有效。
-  Global-session - 类似于标准的 HTTP Session 作用域，不过它仅仅在基于 portlet 的 web 应用中才有意义。Portlet 规范定义了全局 Session 的概念， 它被所有构成某个 portlet web 应用的各种不同的 portlet 所共享。在 global session 作用域中定义的 bean 被限定于全局 portlet Session 的生命周期范 围内。如果你在 web 中使用 global session 作用域来标识 bean，那么 web 会自动当成 session 类型来使用。

仅当用户使用支持 Web 的 ApplicationContext 时，最后三个才可用。



内部Bean

​		当一个 bean 仅被用作另一个 bean 的属性时，它能被声明为一个内部 bean，为 了定义 inner bean，在 Spring 的 基于 XML 的 配		置元数据中，可以在 或 元 素内使用 元素，内部 bean 通常是匿名的，它们的 Scope 一般是 prototype。

​		内部类

## 自动装配方式

- 什么是装配
  - 当 bean 在 Spring 容器中组合在一起时，它被称为装配或 bean 装配。Spring 容器需要知道需要什么 bean 以及容器应该如何使用依赖注入来将 bean 绑定 在一起，同时装配 bean。
  - Spring 容器中把 bean 组装到一起，前提是容器需要 知道 bean 的依赖关系，如何通过依赖注入来把它们装配到一起
- 自动装配
  - Spring 容器能够自动装配 bean。也就是说，可以通过检查 BeanFactory 的内 容让 Spring 自动解析 bean 的协作者。
- 自动装配方式
  - no 
    - 这是默认设置，表示没有自动装配。应使用显式 bean 引用进行装配。 
  - byName 
    - 它根据 bean 的名称注入对象依赖项。它匹配并装配其属性与 XML 文件中由相同名称定义的 bean。
  - byType 
    - 它根据类型注入对象依赖项。如果属 性的类型与 XML 文件中的一个 bean 名称匹配，则匹配并装配属性。构造函数 - 它通过调用类的构造函数来注入依赖项。它有大量的参数。
  - autodetect 
    - 首先 容器尝试通过构造函数使用 autowire 装配，如果不能，则尝试通过 byType 自 动装配。
- 自动装配局限性
  - 覆盖的可能性 - 您始终可以使用  和  设 置指定依赖项，这将覆盖自动装配。
  - 基本元数据类型 - 简单属性（如原数据类型， 字符串和类）无法自动装配。
  - 令人困惑的性质 - 总是喜欢使用明确的装配，因为 自动装配不太精确。

## Bean配置方式

Bean简介

​	是构成用户应用程序主干的对象

​	由 Spring IoC 容器实例化，配置，装配和管理（基于用户提供给容器的配置元数据创建）

配置方式

​	基于 xml 

​			bean 所需的依赖项和服务在 XML 格式的配置文件中指定。这些配置文件通常 包含许多 bean 定义和特定于应用程序的配置选			项。它们通常以 bean 标签开 头

​	基于注解

​			通过在相关的类，方法或字段声明上使用注解，将 bean 配置为组件类本 身

​			默认情况下，Spring 容器中未打开 注解装配，需要在Spring 配置文件中启用

​			@Bean 注解扮演与  元素相同的角色。 

​			@Configuration 类允许通过简单地调用同一个类中的其他 @Bean 方法 来定义 bean 间依赖关系

## 常用注解

### @Component, @Controller, @Repository,@Service

- @Component
  - 将 java 类标记为 bean。它是任何 Spring 管理组件的通 用构造型。spring 的组件扫描机制现在可以将其拾取并将其拉入应用程序环境 中。
- @Controller 
  - 这将一个类标记为 Spring Web MVC 控制器。标有它的 Bean 会自动导入到 IOC 容器中。
- @Service 
  - 此注解是组件注解的特化。它不 会对 @Component 注解提供任何其他行为。您可以在服务层类中使用  @Service 而不是 @Component，因为它以更好的方式指定了意图。 
- @Repository 
  - 这个注解是具有类似用途和功能的 @Component 注解的特 化。它为 DAO 提供了额外的好处。它将 DAO 导入 IoC 容器，并使未经检查 的异常有资格转换为 Spring DataAccessException。

### @Required

- @Required 
  - 应用于 bean 属性 setter 方法。
  - 此注解仅指示必须在配置时使用 bean 定义中的显式属性值或使用自动装配填充受影响的 bean 属性。
  - 如果尚未 填充受影响的 bean 属性，则容器将抛出 BeanInitializationException。

### @Autowired

- @Autowired 
  - 可以更准确地控制应该在何处以及如何进行自动装配。
  - 此注解用于 在 setter 方法，构造函数，具有任意名称或多个参数的属性或方法上自动装配 bean。
  - 默认情况下，它是类型驱动的注入。

### @Qualifier

- qualifier：合格者
- Qualifier
  - 创建多个相同类型的 bean 并希望仅使用属性装配其中一个 bean 时，
  - 可 以使用@Qualifier 注解和 @Autowired 通过指定应该装配哪个确切的 bean 来消除歧义。

### @RequestMapping 

- @RequestMapping 
  - 用于将特定 HTTP 请求方法映射到将处理相应请求的 控制器中的特定类/方法。
  - 类级别：映射请求的 URL 方法级别：映射 URL 以及 HTTP 请求方法

## Spring事务管理类型

- 程序化事务管理
  - 在此过程中，在编程的帮助下管理事务。它为您提供极大 的灵活性，但维护起来非常困难。 
- 声明式事务管理
  - 在此，事务管理与业务代码分离。仅使用注解或基于 XML 的配置来管理事务。

## Spring AOP

- AOP(Aspect-Oriented Programming), 
  - 即 面向切面编程, 它与 OOP( Object-Oriented Programming, 面向对象编程) 相辅相成, 提供了与 OOP 不同的抽象软件结构的视角. 在 OOP 中, 我们以类(class)作为我们的基 本单元, 而 AOP 中的基本单元是 Aspect(切面)
  - 允许程序模块化横向切割关注点， 或横切典型的责任划分，如日志和事务管理

- Aspect（切面）

  - 它将多个类的通用行为封装成可重用的模块，该模块含有一 组 API 提供横切功能。

      比如，一个日志模块可以被称作日志的 AOP 切面。根据需 求的不同，一个应用程序可以有若干切面。在 Spring AOP 中，切面    通过带 有 @Aspect 注解的类实现

  - aspect 由 pointcount 和 advice 组成,

  - 它既包含了横切逻辑的定义, 也包 括了连接点的定义. 

  - Spring AOP 就是负责实施切面的框架, 它将切面所定义的横 切逻辑编织到切面所指定的连接点中. 

  - AOP 的工作重心在于如何将增强编织目标 对象的连接点上, 

  - 包含两个工作: 

    - 如何通过 Pointcut和 advice 定位到特定的 joinpoint 上 
    - 如何在 advice 中编写切面代码

- Pointcut（切点）
  - 程序运行中的一些时间点, 例如一个方法的执行, 或者是一个异常的处理. 
  - 在 Spring AOP 中,Pointcut 总是方法的执行点

- Advice（通知）
  - 特定 Pointcut 处的 Aspect（切面） 所采取的动作称为 Advice。
  - Spring AOP 使用一 个 Advice 作为拦截器，在 JoinPoint “周围”维护一系列的拦截器
- Weaving（编织）
  
  - 

## AOP通知(Advice)类型

- Advice
  - Before - 这些类型的 Advice 在 joinpoint 方法之前执行，并使用 @Before 注解标记进行配置。 
  - After Returning - 这些类型的 Advice 在连接点方法正常执行后执 行，并使用@AfterReturning 注解标记进行配置。 
  -  After Throwing - 这些类型的 Advice 仅在 joinpoint 方法通过抛出 异常退出并使用 @AfterThrowing 注解标记配置时执行。 
  -  After (finally) - 这些类型的 Advice 在连接点方法之后执行，无论方 法退出是正常还是异常返回，并使用 @After 注解标记进行配置。 
  -  Around - 这些类型的 Advice 在连接点之前和之后执行，并使用 @Around 注解标记进行配置。

## AOP实现方式

- 实现方式	
  - 静态代理 
    - 指使用 AOP 框架提供的命令进行编译，从而在编译阶段就可生成 AOP 代理类， 因此也称为编译时增强
    - 方式：
      - 编译时编织（特殊编译器实现）  
      - 类加载时编织（特殊的类加载器实现）
  - 动态代理 
    - 在运行时在内存中“临时”生成 AOP 动态代理类，因此也被称为运行时增强
    - 方式
      - JDK 动态代理 
      -  CGLIB动态代理

- Spring AOP and AspectJ AOP区别
  - 代理方式
    - Spring AOP 基于动态代理方式实现
    - AspectJ 基于静态代理方式实现
  - 支持级别
    - Spring AOP 仅支持方法级别的 PointCut
    - AspectJ 提供了完全的 AOP 支持，它还支持属性级 别的 PointCut

## SpringMVC

Spring Web MVC 框架提供 模型-视图-控制器 架构和随时可用的组件，用于开 发灵活且松散耦合的 Web 应用程序。MVC 模式有助于分离应用程序的不同方 面，如输入逻辑，业务逻辑和 UI 逻辑，同时在所有这些元素之间提供松散耦合。

它将多个类的通用行为封装成可重用的模块，该模块含有一 组 API 提供横切功能。比如，一个日志模块可以被称作日志的 AOP 切面。根据需 求的不同，一个应用程序可以有若干切面。在 Spring AOP 中，切面通过带有 @Aspect 注解的类实现

## DispatcherServlet 的工作流程

- 过程
  - 向服务器发送 HTTP 请求，请求被前端控制器 DispatcherServlet 捕获。 
  - DispatcherServlet 根据 -servlet.xml 中的配置对请求的 URL 进行解 析，得到请求资源标识符（URI）。然后根据该 URI，调用 HandlerMapping 获得该 Handler 配置的所有相关的对象（包括 Handler 对象以及 Handler 对 象对应的拦截器），最后以 HandlerExecutionChain 对象的形式返回。 
  - DispatcherServlet 根据获得的 Handler，选择一个合适的 HandlerAdapter。（附注：如果成功获得 HandlerAdapter 后，此时将开始 执行拦截器的 preHandler(...)方法）。 
  - 提取 Request 中的模型数据，填充 Handler 入参，开始执行 Handler （ Controller)。在填充 Handler 的入参过程中，根据你的配置，Spring 将 帮你做一些额外的工作
    - HttpMessageConveter：将请求消息（如 Json、xml 等数据）转换 成一个对象，将对象转换为指定的响应信息。  
    - 数据转换：对请求消息进行数据转换。如 String 转换成 Integer、 Double 等。  
    - 数据根式化：对请求消息进行数据格式化。如将字符串转换成格式化数字 或格式化日期等。  
    - 数据验证：验证数据的有效性（长度、格式等），验证结果存储到 BindingResult 或 Error 中。
  - Handler(Controller)执行完成后，向 DispatcherServlet 返回一个 ModelAndView 对象； 
  - 根据返回的 ModelAndView，选择一个适合的 ViewResolver（必须是已 经注册到 Spring 容器中的 ViewResolver)返回给 DispatcherServlet。 
  -  ViewResolver 结合 Model 和 View，来渲染视图。 
  - 视图负责将渲染结果返回给客户端

## 什么是 Spring Boot

在spring 框架 之上，增加了样板代码和配置，避免了Spring繁杂的配置。因 此，Spring Boot 可以帮助我们以最少的工作量，更加健壮地使用现有的 Spring 功能

只用Spring

​	须添 加构建路径或添加 Maven 依赖关系，配置应用程序服务器，添加 spring 配置

​	每次新建一个项目都要这样配置，而很多都是重复的

## SpringBoot好处

- 优点
  - 减少开发，测试时间和努力。 
  - 使用 JavaConfig 有助于避免使用 XML。 
  - 避免大量的 Maven 导入和各种版本冲突。 
  - 通过提供默认值快速开始开发。 
  - 没有单独的 Web 服务器需要。这意味着你不再需要启动 Tomcat，Glassfish 或其他任何东西。 
  - 需要更少的配置 因为没有 web.xml 文件。只需添加用@ Configuration 注释 的类，然后添加用@Bean 注释的方法，Spring 将自动加载对象并像以前一样对其 进行管理。您甚至可以将@Autowired 添加到 bean 方法中，以使 Spring 自动装 入需要的依赖关系中。 
  - 基于环境的配置 使用这些属性，您可以将您正在使用的环境传递到应用程序： -Dspring.profiles.active = {enviornment}。在加载主应用程序属性文件后， Spring 将在（application{environment} .properties）中加载后续的应用程序属 性文件。
  - 基于JavaConfig:java config是指基于java配置的spring。传统的Spring一般都是基本xml配置的，后来spring3.0新增了许多java config的注解，特别是spring boot，基本都是清一色的java config

## 端点监控

​	Spring Boot 提供监视器端点服务的度量。

​	这些端点对于获取有关应 用程序的信息（如它们是否已启动）以及它们的组件（如数据库等）是否正常运 行很有帮助。

​	在 Spring Boot Actuator 之上，它提供了一个 Web UI，使 我们能够可视化多个应用程序的度量。

