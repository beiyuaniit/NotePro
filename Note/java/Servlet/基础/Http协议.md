## Http协议

- 什么是协议？
  - 协议是一套规范，一套标准。由其他人或其他组织来负责制定的。大家都按照这个规范来，这样可以做到沟通无障碍
- 什么是HTTP协议？
  - W3C制定的一种超文本传输协议。（通信协议：发送消息的模板提前被制定好。
  - Browser和Server通过Http协议解耦合
- 什么是超文本？
  - 不是普通文本，比如流媒体：声音、视频、图片等。
  - HTTP协议：不但可以传送普通字符串，同样支持传递声音、视频、图片等流媒体信息



## HTTP请求

- 请求行
  - 第一部分：请求方式（7种）
    - get（常用的）
    - post（常用的）
    - delete
    - put
    - head
    - options
    - trace
  - 第二部分：URI
    - 什么是URI？ 统一资源标识符。代表网络中某个资源的名字。但是通过URI是无法定位资源的。
    - 什么是URL？统一资源定位符。代表网络中某个资源，同时，通过URL是可以定位到该资源的。
    - URI和URL什么关系，有什么区别？
      - URL包括URI
      - http://localhost:8080/servlet05/index.html 这是URL。
      - /servlet05/index.html 这是URI。
  - 第三部分：HTTP协议版本号

- 请求头

  - 请求的主机
  - 主机的端口
  - 浏览器信息
  - 平台信息
  - cookie等信息
  - ....
- 空白行

  - 空白行是用来区分“请求头”和“请求体”
- 请求体

  - 向服务器发送的具体数据。

## Request例子

- HTTP请求协议的具体报文：GET请求

  - ```
    GET /servlet05/getServlet?username=lucy&userpwd=1111 HTTP/1.1                           请求行
    Host: localhost:8080                                                                    请求头
    Connection: keep-alive
    sec-ch-ua: "Google Chrome";v="95", "Chromium";v="95", ";Not A Brand";v="99"
    sec-ch-ua-mobile: ?0
    sec-ch-ua-platform: "Windows"
    Upgrade-Insecure-Requests: 1
    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36
    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
    Sec-Fetch-Site: same-origin
    Sec-Fetch-Mode: navigate
    Sec-Fetch-User: ?1
    Sec-Fetch-Dest: document
    Referer: http://localhost:8080/servlet05/index.html
    Accept-Encoding: gzip, deflate, br
    Accept-Language: zh-CN,zh;q=0.9
                                                                                            空白行
                                                                                            请求体
    ```

  

- HTTP请求协议的具体报文：POST请求

  - ```
    POST /servlet05/postServlet HTTP/1.1                                                  请求行
    Host: localhost:8080                                                                  请求头
    Connection: keep-alive
    Content-Length: 25
    Cache-Control: max-age=0
    sec-ch-ua: "Google Chrome";v="95", "Chromium";v="95", ";Not A Brand";v="99"
    sec-ch-ua-mobile: ?0
    sec-ch-ua-platform: "Windows"
    Upgrade-Insecure-Requests: 1
    Origin: http://localhost:8080
    Content-Type: application/x-www-form-urlencoded
    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36
    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
    Sec-Fetch-Site: same-origin
    Sec-Fetch-Mode: navigate
    Sec-Fetch-User: ?1
    Sec-Fetch-Dest: document
    Referer: http://localhost:8080/servlet05/index.html
    Accept-Encoding: gzip, deflate, br
    Accept-Language: zh-CN,zh;q=0.9
                                                                                          空白行
    username=lisi&userpwd=123                                                             请求体
    ```

## HTTP响应

- 状态行

  - 三部分组成
    - 第一部分：协议版本号（HTTP/1.1）
    - 第二部分：状态码（HTTP协议中规定的响应状态号。不同的响应结果对应不同的号码。）
      - 200 表示请求响应成功，正常结束。
      - 404表示访问的资源不存在，通常是因为要么是你路径写错了，要么是路径写对了，但是服务器中对应的资源并没有启动成功。总之404错误是前端错误。
      - 405表示前端发送的请求方式与后端请求的处理方式不一致时发生：
        - 比如：前端是POST请求，后端的处理方式按照get方式进行处理时，发生405
        - 比如：前端是GET请求，后端的处理方式按照post方式进行处理时，发生405
      - 500表示服务器端的程序出现了异常。一般会认为是服务器端的错误导致的。
      - 以4开始的，一般是浏览器端的错误导致的。
      - 以5开始的，一般是服务器端的错误导致的。
    - 第三部分：状态的描述信息
      - ok 表示正常成功结束。
      - not found 表示资源找不到。

- 响应头：

  - 响应的内容类型
  - 响应的内容长度
  - 响应的时间
  - ....

- 空白行：

  - 用来分隔“响应头”和“响应体”的。

- 响应体：

  - 响应体就是响应的正文，这些内容是一个长的字符串，这个字符串被浏览器渲染，解释并执行，最终展示出效果。

## Response例子

 ```
  HTTP/1.1 200 ok                                     状态行
  Content-Type: text/html;charset=UTF-8               响应头
  Content-Length: 160
  Date: Mon, 08 Nov 2021 13:19:32 GMT
  Keep-Alive: timeout=20
  Connection: keep-alive
                                                      空白行
  <!doctype html>                                     响应体
  <html>
      <head>
          <title>from get servlet</title>
      </head>
      <body>
          <h1>from get servlet</h1>
      </body>
  </html>
 ```

## 如何提交GET或POST

- 到目前为止，只有一种情况可以发送POST请求
  - 使用form表单，并且form标签中的method属性值为：method="post"。

- 其他所有情况一律都是get请求：
  - 在浏览器地址栏上直接输入URL，敲回车，属于get请求。
  - 在浏览器上直接点击超链接，属于get请求。
  - 使用form表单提交数据时，form标签中没有写method属性，默认就是get
  - 或者使用form的时候，form标签中method属性值为：method="get"
  - ....

## GET和POST区别

- GET
  - get请求在W3C中是这样说的：get请求比较适合从服务器端获取数据。
  - get请求发送数据的时候，数据会挂在URI的后面，并且在URI后面添加一个“?”，"?"后面是数据。这样会导致发送的数据回显在浏览器的地址栏上。（get请求在“请求行”上发送数据）
    - http://localhost:8080/servlet05/getServlet?username=zhangsan&userpwd=1111
  - get请求只能发送普通的字符串。并且发送的字符串长度有限制，不同的浏览器限制不同。这个没有明确的规范
  - get请求无法发送大数据量。
  - get请求是安全的。因为get请求只是为了从服务器上获取数据。不会对服务器造成威胁。
  - get请求支持缓存。只要发送get请求，浏览器做的第一件事都是先从本地浏览器缓存中找，找不到的时候才会去服务器上获取。这种缓存机制目的是为了提高用户的体验。
- POST
  - post请求在W3C中是这样说的：post请求比较适合向服务器端传送数据。
  - post请求发送数据的时候，在请求体当中发送。不会回显到浏览器的地址栏上。也就是说post发送的数据，在浏览器地址栏上看不到。（post在“请求体”当中发送数据）
  - post请求可以发送任何类型的数据，包括普通字符串，流媒体等信息：视频、声音、图片。
  - post请求可以发送大数据量，理论上没有长度限制。
  - post请求是危险的。因为post请求是向服务器提交数据，如果这些数据通过后门的方式进入到服务器当中，服务器是很危险的。另外post是为了提交数据，所以一般情况下拦截请求的时候，大部分会选择拦截（监听）post请求
  - post请求之后，服务器“响应的结果”不会被浏览器缓存起来。因为这个缓存没有意义。

## 选择GET和POST

- 想从服务器上获取资源，建议使用GET请求，向服务器提交数据，建议使用POST请求。
  - 大部分的form表单提交，都是post方式，因为form表单中要填写大量的数据，这些数据是收集用户的信息，一般是需要传给服务器，服务器将这些数据保存/修改等。
  - 如果表单中有敏感信息，还是建议适用post请求，因为get请求会回显敏感信息到浏览器地址栏上。（例如：密码信息）
  - 做文件上传，一定是post请求。要传的数据不是普通文本。
  - 其他情况都可以使用get请求。
- 不管你是get请求还是post请求，发送的请求数据格式是完全相同的，只不过位置不同，格式都是统一的：

  - name=value&name=value&name=value&name=value
  - name是什么？
    - 以form表单为例：form表单中input标签的name。
  - value是什么？
    - 以form表单为例：form表单中input标签的value。

