<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
 <meta http-equiv="description" content="this is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/notice_admin.js"></script>
<title>增加公告</title>
</head>
<body style="text-align:center;background-color: #F0F5F8;">
<br>
<br>

<div style="text-align:center;width: 500px; height: 400px;background-color: #F0F5F8;border:1px solid blue;">
<div style="text-align:left;width: 483px; height: 300px;">
<h5>  新增公告 </h5>
<p><label>发布日期: </label>
<input type="text" id="notice_date" name="notice_date" />
(请按“yyyy-mm-dd”格式填写)
</p>
<P><label>公告内容：</label>
<textarea id="notice" style="height: 211px; width: 406px"></textarea>
</P>

<input type="button" id="insertNotice" name="insertNotice" value="提交" style="background-color:#005eac; width: 87px; height: 35px;font-size:15px" >
<input type="button" value="取消" onclick="window.location.href='./notice_admin.jsp'" style="cursor:pointer;background-color:#005eac; width: 87px; height: 35px;font-size:15px" >

</div>
</div>
</body>
</html>