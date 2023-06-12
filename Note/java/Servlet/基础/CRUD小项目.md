## 简介

- 增减Create、查询Retrieve、更新Update、删除Delete
- 纯Servlet实现表单的增删改查

## 前期准备

- 数据表

```sql
drop table if exists dept;
create table dept(
	deptno int primary key,
	dname varchar(255),
	loc varchar(255)
);
insert into dept values(10,'XiaoShou','BeiJing');
insert into dept values(20,'YanFa','ShangHai');
insert into dept values(30,'JiShu','GuangZhou');
insert into dept values(40,'MeiTi','ShenZhen');
commit;
```

- HTML页面原型
  
  - 准备的一套html模板，动态的则写进Servlet里
  - 当然有些是静态页面
    - 首页index.html
    - 列表list.html
    - 新增add.html
    - 修改edit.html
    - 详情detail.html
- 在IDEA中搭建环境

  - 不同功能的类放在src不同的包
- 添加一个JDBC工具类来管理数据库连接

## 配置

- 前端页面要加项目名

  - ```xml
    <!-- 前端页面，以/开始，且要带项目名 -->
    <a href="/CRUD/dept/list">查看部门列表</a>
    String contextPath=req.getContextPath();//获取应用根路径。也就是项目名
    <a href='"+contextPath+"/dept/detail?deptno="+deptno+"'>详情</a>//?deptno="+deptno+"表示携带的参数
    ```

- 后端与之对应的不用加项目名

  - ```xml
      <servlet>
          <servlet-name>list</servlet-name>
          <servlet-class>Servlets.ListServlet</servlet-class>
      </servlet>
      <servlet-mapping>
          <servlet-name>List</servlet-name>
          <!--     以/开始，但是不加项目名   -->
          <url-pattern>/dept/list</url-pattern>
    </servlet-mapping>

  
## 分析页面
  - 静态的写在外面
  - 动态的写在while(rs.next()){}获取数据
  - 把.html中的双引号""换成单引号''。因为会和java字符串形式的双引号""冲突

## 删除功能

```javascript
<a href="javascript:void(0)" onclick="del(10)">删除</a>
  //点击删除的确认
<script type="text/javascript">
  		function del(deptno){
  			var ok=window.confirm("删除了不可恢复");
  			if(ok){
  				// 发送请求
  				window.location="/CRUD/dept/delete?deptno="+deptno;
  			}
  		}
</script>

//是否删除成功
	if(count==1){
            //转发回到部门列表
            req.getRequestDispatcher("/dept/list").forward(req,resp);
        }else {
            req.getRequestDispatcher("/HTML/error.html").forward(req,resp);
    	}
```



## 新增功能

- 以下代码转发后是Post

```java
//要从请求中拿数据。Tomcat10不会存在乱码问题
req.setCharacterEncoding("UTF-8");//不是下划线_

if(count==1){//成功添加
        req.getRequestDispatcher("/dept/list").forward(req,resp);
      }else {
       req.getRequestDispatcher("/HTML/error.html").forward(req,resp);
  	 }

//在原来/dept/list对应的ListServlet只有doGet()。第一种解决：也重写doPost()
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
}

//第二种解法：重定向
```

## 修改功能

- EditServlet负责展示修改界面，DoEditServlet负责修改数据

