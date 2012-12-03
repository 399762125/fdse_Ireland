<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Ireland</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="CSS/main.css">
    <script language="javascript" type="text/javascript" src="JS/main.js"></script>
    
  </head>
  <body onLoad="marg()">
    <div class="header" id="header">
    	<ul class="headmenu">
        	<li class="headmenulist"><a target="target_frame" href="announcement.jsp">爱班首页</a></li>
        	<li class="headmenulist">网站地图</li>
            <li class="headmenulist"><a target="_blank" href="http://www.software.fudan.edu.cn/">软件学院</a></li>
            <li class="headmenulist"><a target="_blank" href="http://www.fudan.edu.cn/">复旦大学</a></li>
        </ul>
        <span class="language">English</span>
        <span class="go">GO</span>
        <input name="search" class="search" type="text" />
	</div>
    <div class="main">
    	<div class="left">
        	<ul class="menu">
            	<li class="list"><a target="target_frame" href="introduction.jsp">爱班介绍</a></li>
                <li class="list"><a target="target_frame" href="news.jsp">新闻动态</a></li>
                <li class="list"><a target="target_frame" href="course.jsp">课程简介</a></li>
                <li class="list"><a target="target_frame" href="activity.jsp">特色活动</a></li>
                <li class="list"><a target="target_frame" href="people.jsp">人物志</a></li>
                <li class="list"><a target="target_frame" href="teacher.jsp">师资队伍</a></li>
                <li class="list"><a target="target_frame" href="album.jsp">爱班相册</a></li>
            </ul>
        </div>
        <div class="content">
        	<iframe class="content_frame" name="target_frame" id="target_frame" frameborder="0" scrolling="no" src="announcement.jsp" onLoad="iFrameHeight()">
            </iframe>
        </div>
	</div>
	<div class="footer">
    	<span class="authority">Copyright © 复旦大学软件学院</span>
	</div>
  </body>
</html>
