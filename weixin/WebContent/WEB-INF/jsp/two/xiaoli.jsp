<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 	<meta charset="UTF-8"> -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta content="yes" name="apple-touch-fullscreen" />
    <meta content="telephone=no,email=no" name="format-detection" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <% 
		String path = request.getContextPath(); 
		// 获得本项目的地址(例如: http://localhost:8080/MyApp/)赋值给basePath变量 
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/resources"; 
		// 将 "项目路径basePath" 放入pageContext中，待以后用EL表达式读出。 
		pageContext.setAttribute("basePath",basePath); 
	%>
    <script>
    (function(g,h){function f(){var a;a=b.getBoundingClientRect().width;540<a/c&&(a=540*c);b.style.fontSize=a/10+"px";g._rem=a/10}var b,a,c,d,e,k;b=document.documentElement;a=h.getElementById("view");k=window.navigator.userAgent;a||(a=h.createElement("meta"));c=window.devicePixelRatio;d=parseFloat(1/c).toFixed(2);k.match("iPhone")?(a.setAttribute("name","viewport"),a.setAttribute("content","width=device-width,initial-scale="+d+", maximum-scale="+d+", minimum-scale="+d+", user-scalable=no"),b.setAttribute("dpi",c)):(a.setAttribute("name","viewport"),a.setAttribute("content","width=device-width,initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no"),b.setAttribute("dpi",1));b.firstChild.appendChild(a);g.addEventListener("pageshow",function(){clearTimeout(e);e=setTimeout(function(){f()},300)},!1);g.addEventListener("resize",function(){clearTimeout(e);e=setTimeout(function(){f()},300)},!1);f()})(window,document);
    </script>
    <link rel="stylesheet"  href="<%=basePath%>/css/weixin_project/schoolCalendar.css?v=0.0.1" />
    <title>校历</title>
</head>
<body>
	<section class="wx-calendar-box">
    <section id="wx-calendar" class="wx-calendar-box"></section>
	</section>

	<script src="<%=basePath%>/js/weixin_proj/schoolCalendar.min.js"></script>
</body>
</html>