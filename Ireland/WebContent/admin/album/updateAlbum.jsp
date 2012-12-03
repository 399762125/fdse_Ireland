<%@ page language="java" import="cn.edu.fudan.software.servlet.* , cn.edu.fudan.software.entity.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String albumId=request.getParameter("albumId"); 
   AlbumServlet aServlet= new AlbumServlet();
   Album a= aServlet.getAlbumById(albumId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
 <meta http-equiv="description" content="this is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../../JS/lib/jquery.uploadify-v2.1.4/uploadify.css">
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/album_admin.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/swfobject.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.js"></script>
<title>新建相册</title>
</head>
<body style="text-align:left;background-color: #F0F5F8;">

<br>
<div style="text-align:left;width: 500px; height: 300px;background-color: #F0F5F8;border:1px solid;">
<h4> 修改相册 </h4>
<p><label>相册名称: </label>
<input type="text" id="album_title" name="album_title" value="<%=a.getTitle() %>"/>
</p>
<p><label>相册简介: </label>
<br>
<textarea id="album_introduction" name="album_introduction" style="height: 77px; width: 374px"><%=a.getIntroduction() %></textarea>
</p>
<input type="hidden" id="album_id" name="album_id" value="<%=a.getId() %>"></input>
<p>
<input type="button" id="updateAlbum" name="updateAlbum" value="提交" style="cursor:pointer;background-color:#005eac; width: 87px; height: 35px;font-size:15px"/>
<input type="button" value="取消" onclick="window.location.href='./album_admin.jsp'" style="cursor:pointer;background-color:#005eac; width: 87px; height: 35px;font-size:15px" >

</p>
</div >

</body>
</html>