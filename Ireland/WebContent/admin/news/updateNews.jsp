<%@ page language="java" import="cn.edu.fudan.software.servlet.* , cn.edu.fudan.software.entity.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String newsId=request.getParameter("newsId"); 
   NewsServlet sServlet= new NewsServlet();
   News n= sServlet.getNewsById(newsId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
 <meta http-equiv="description" content="this is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../../JS/lib/jquery.uploadify-v2.1.4/uploadify.css">
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/news_admin.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/swfobject.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.js"></script>
<title>编辑新闻</title>
</head>
<body style="text-align:center;background-color: #F0F5F8;">

<br>

<div style="text-align:center;width: 500px; height: 550px;background-color: #F0F5F8;border:1px solid;">
<div style="text-align:left;width: 483px; height: 300px;">
<h4>  编辑新闻 </h4>
<p><label>新闻标题: </label>
<input type="text" id="news_title" name="news_title" value="<%=n.getTitle() %>" />
</p>
<p><label>新闻日期: </label>
<input type="text" id="news_date" name="news_date" value="<%=n.getDate() %>"/>
(请按“yyyy-mm-dd”格式填写)
</p>
<P><label>新闻内容：</label>
<textarea id="news_content" style="height: 211px; width: 457px"><%=n.getContent() %></textarea>
</P>

<div>
<label>新闻图片：</label>
<img id="uploadsign" src="../../Img/Admin/upload/success.gif" style="display:none;height: 23px; width: 34px"></div>

<div id="fileQueue2"></div>
    <input type="file" name="updateImage" id="updateImage" />
    <p>
      <a href="javascript:$('#updateImage').uploadifyUpload()">上传</a>| 
      <a href="javascript:$('#updateImage').uploadifyClearQueue()">取消上传</a>
      
    </p>
   
   <input type="hidden" id="news_id" name="news_id" value="<%=n.getId() %>"></input> 
   <input type="hidden" id="news_imageURL" name="news_imageURL" value="<%=n.getImageURL() %>"></input>
<input type="button" id="updateNews" name="updateNews" value="提交" style="background-color:#005eac; width: 87px; height: 35px;font-size:15px" >
<input type="button" value="取消" onclick="window.location.href='./news_admin.jsp'" style="cursor:pointer;background-color:#005eac; width: 87px; height: 35px;font-size:15px" >

</div>
</div>
</body>
</html>