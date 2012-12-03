<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
 <meta http-equiv="description" content="this is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/intro_admin.js"></script>
<title>爱班介绍编辑页面</title>
</head>
<body style="text-align:center;background-color: #F0F5F8;">
<div style="width: 500px; height: 300px;background-color: #F0F5F8;">

<h4>爱班介绍编辑</h4>
<textarea id="introduction" style="height: 266px; width: 434px"></textarea>
<br>
<br>

<input type="button" id="updateIntro" name="updateIntro" value="提交" style="background-color:#005eac; width: 87px; height: 35px;font-size:15px" >
<input type="button" value="取消" onclick="window.location.href='./intro_admin.jsp'" style="cursor:pointer;background-color:#005eac; width: 87px; height: 35px;font-size:15px" >
</div>
</body>
</html>