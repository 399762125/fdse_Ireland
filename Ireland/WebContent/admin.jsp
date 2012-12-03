<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="CSS/admin.css">
<script language="javascript" type="text/javascript" src="JS/lib/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript" src="JS/admin.js"></script>

<title>爱班课程简介</title>
<title>复旦软院爱班后台管理系统</title>

</head>
<body>
<div id="top">
<h2>管理菜单</h2>
<div class=jg></div>
<div id="topTags">
<ul>
</ul>
</div>
</div>
<div id="main"> 
<div id="leftMenu">
<ul>
<li><a href="admin/intro/intro_admin.jsp" target="viewContent">爱班介绍管理</a></li>
<li><a href="admin/notice/notice_admin.jsp" target="viewContent">爱班公告管理</a></li>
<li><a href="admin/news/news_admin.jsp" target="viewContent">新闻动态管理</a></li>
<li><a href="admin/course/course_admin.jsp" target="viewContent">课程简介管理</a></li>
<li><a href="admin/activity/activity_admin.jsp" target="viewContent">特色活动管理</a></li>
<li><a href="admin/people/people_admin.jsp" target="viewContent">人物志管理</a></li>
<li><a href="admin/teacher/teacher_admin.jsp" target="viewContent">师资队伍管理</a></li>
<li><a href="admin/album/album_admin.jsp" target="viewContent">爱班相册管理</a></li>

</ul>
<input type="button" id="logout" name="logout" value="注销退出" style="margin-left :10pt;margin-top : 20pt; width: 87px; height: 35px;font-size:15px" >
</div>
<div class=jg></div>

<div id="content">
<iframe name="viewContent" id="viewContent" src="admin/welcome.jsp" frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>
</div>
</div>
<div id="footer">Copyright © 复旦大学软件学院爱尔兰班</div>
</body>
</html>