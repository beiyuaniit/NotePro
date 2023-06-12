function Jquery(selector ){
    if(typeof selector == "string"){//处理选择器
		if(selector.charAt(0)=="#"){//id选择器
        	//var domObj=document.getElementById(selector.substring(1));//var定义的是局部变量
        	//return domObj;//封装html函数时domObj不能dom的对象，没有html()函数
            domObj=document.getElementById(selector.substring(1));//没有var，全局变量，后面的添加this.html函数
            return new Jquery();
    	}
    }
    if(typeof selector =="function"){//函数。如可以绑定给window.onload
        window.onload=selector;
    }

    //这个函数为了代替domObj.innerHtml=""   domObj.html("")   调用时传递一个字符串
    this.html=function(htmlStr){
        domObj.innerHTML=htmlStr;
    }
    //domObj.click甚至也能封装onclick()    调用时传递一个函数
    this.click=function(fun){
        domObj.onclick=fun;
    }
    //还能封装各种事件函数
    this.focus=function(fun){
        domObj.onfocus=fun;
    }
    this.change=function(fun){
        domObj.onchange=fun;
    }
    this.blur=function(fun){
        domObj.onblur=fun;
    }
    //上面返回的Jquery对象。Jquery可没有value的属性值。
    this.val=function (v){//无参数时
        /*
        不传递参数时，是返回value属性值。
        如document.getElementById("username").value;
        */
        if(v==undefined){//没传递则是undefined
            return domObj.value;//专门返回value属性值
        }else{//有参数时是修改value属性值
            domObj.value=v;
        }
        
    }
    
    Jquery.ajax=function(jsonArgs){
		var xhr=new XMLHttpRequest();
		xhr.onreadystatechange=function(){
			if(this.readyState==4){
				if(this.status=200){
				    if(jsonArgs.dataType=="json") {//为了和官方的一致
                        var jsonObj = JSON.parse(this.responseText);//获取响应数据
                        jsonArgs.success(jsonObj);//传入响应的json数据并调用success函数
                    }
				}else{
					console.log(this.status)
				}
			}
		}
		if(jsonArgs.type.toUpperCase()=="GET"){//小写也可以用
			xhr.open("GET",jsonArgs.url+"?"+jsonArgs.data,jsonArgs.async);//jsondata中多个数据用&隔开
			xhr.send();
		}
		if(jsonArgs.type.toUpperCase()=="POST"){
			xhr.open("POST",jsonArgs.url,jsonArgs.async);
			xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded")
			xhr.send(jsonArgs.data);
		}
	}
}
//取个更加简单的别名。一个变量可以接收任何类型的数据。
$=Jquery
//让静态代码生效，可以通过类名调用
new Jquery()