##查看jsp编译后资源
	C:\Users\beilinanju\AppData\Local\JetBrains\IntelliJIdea2021.3\tomcat\1bb9a9d1-19d3-4664-9864-f3569ad0bc55\work\Catalina\localhost\xmm\org\apache\jsp\html_0026jsp
	目录下的a_jsp.class和a_jsp.java
##继承和实现的类
	public final class _a_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports 
##注释
	//
	<%--  --%>
##<%!  %>
	类的成员变量和成员方法，所有用户共享，直到服务器关闭才释放
	作用域为整个jsp页面，与书写位置无关（但方法中声明的变量只在方法内有效）
##<%   %>
	Java程序片
	其中声明的变量为局部变量，作用域为声明位置之后的程序片和表达式都可以
##<%=  %>
	Java表达式（可以是单个变量
	Web服务器将结果以字符串形式输出到HTML页面
##jsp指令标记
	page\include指令
##<%@ page name="value" ... %>
	contentType:MIME类型（只能指定一个
		charset:Server->Browser
	import:导包（可指定多个
	language:java
	pageEncoding:jsp文件本身的编码
##MIME类型
	MIME(Multipurpose Internet Mail Extensions)多用途互联网邮件扩展类型。是设定某种
	扩展名的文件用一种应用程序来打开的方式类型，当该扩展名文件被访问的时候，浏览器会自动用
	指定应用程序来打开。多用于指定一些客户端自定义的文件名，以及一些媒体文件打开方式。
##<% include file="" %>
	静态嵌入：先包含后处理，在编译阶段完成文件的嵌入，合并为一个新的jsp页面
	可：.jsp\.html\其他文本文件

##JSP动作标记
	include、forward、param、useBean、getProperty、setProperty
##带有子标记时
	如
	<jsp:include page="">
		子标记
	<jsp:include/>
	不带的话可以用以下写法
	</jsp:include page"">
##<%jsp:include page="" />
	动态嵌入：先处理后包含，在运行阶段完成文件的嵌入。Jsp文件转译为Java文件时不合并成一个页
	面，字节码加载时才处理引入的文件
	与静态嵌入相比：执行速度较慢，但灵活性较高
##<jsp:forward page="" />
	停止当前jsp页面执行，转向page值的页面执行（浏览器地址栏url不变
##param
	传递参数
	不能独立使用，要包含在父标记中
	<jsp:父标记 page="目标页面" >
		<jsp:param name="" value="" />
		<jsp:param name="' value="" />
		...
	</jsp:父标记>
##JSP内置对象
	Input/Output相关
		request,response,out
	Context相关
		session,application,pageContext
	Servlt相关
		page,config
	Error相关
		exception
##request
	服务器将客户端的所有请求信息封装在request对象中
	