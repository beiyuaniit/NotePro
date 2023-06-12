## Ajax

- Asynchronous JavaScript and XML
- 异步对象(XMLHttpRequest
  - 存在于浏览器内存中，用来作局部刷新
  - 使用JavaScript创建异步对象，发送请求，更新dom对象

## 刷新

- 全局刷新
  - 获取一个页面的数据，浏览器需要重新渲染页面
- 局部刷新
  - 由浏览器发起请求，获取数据，改变页面中的局部内容。用户体验好

## 步骤

```javascript
//创建异步对象
var xmlHttp=new XMLHttpRequest();

//给异步对象绑定状态变化事件。在状态readyState变化时会回调该函数
xmlHttp.onreadystatechange=funtion(){
    //处理请求状态变化
    /*
    readyState的值，请求状态的变化
    0		对象创建			  -对应new XMLHttpRequest()
    1		初始化请求对象			-xmlHttp.open(method,url,true/false);
    2		发送请求			  -xmlHttp.send();
    3		异步对象接收到服务器的应答数据			-是原始数据
    4		异步对象已经将数据解析完毕，此时才可以读取数据		-开发人员常使用的状态
    */
    /*
    status	网络请求状态
    200
    404
    500	
    ...
    */
    //可以从responseText获取到服务器返回的数据
}

//初始化请求对象
xmlHttp.open(get,"login?user=bei&pwd=yuan",true);

//发送请求
xmlHttp.send();
```



## 看到第10了，打算放弃

## 局部 刷新

- Ajax不是用form提交数据，所有有没有form标签都行