<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
 <meta http-equiv="description" content="this is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../../JS/lib/jquery.uploadify-v2.1.4/uploadify.css">
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/activity_admin.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/swfobject.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.js"></script>
<title>新增特色活动</title>
</head>
<body style="text-align:center;background-color: #F0F5F8;">
<br>
<div style="text-align:center;width: 500px; height: 620px;background-color: #F0F5F8;border:1px solid;">
<div style="text-align:left;width: 420px; height: 300px;">
<h4>  新增特色活动 </h4>
<p><label>活动名称: </label>
<input type="text" id="activity_name" name="activity_name" />
</p>
<p><label>活动简介(字数不要超过本框！): </label>
<textarea id="activity_briefIntroduction" name="activity_briefIntroduction" style="height: 84px; width: 374px"></textarea>
</p>
<P><label>活动详细介绍：</label>
<textarea id="activity_introduction" style="height: 211px; width: 374px"></textarea>
</P>

<div>
<label>活动图片：</label>
<img id="uploadsign" src="../../Img/Admin/upload/success.gif" style="display:none;height: 23px; width: 34px"></div>

<div id="fileQueue"></div>
    <input type="file" name="uploadImage" id="uploadImage" />
    <p>
      <a href="javascript:$('#uploadImage').uploadifyUpload()">上传</a>| 
      <a href="javascript:$('#uploadImage').uploadifyClearQueue()">取消上传</a>
      
    </p>
   
   <input type="hidden" id="activity_id" name="activity_id" value=""></input> 
   <input type="hidden" id="activity_imageURL" name="activity_imageURL" value=""></input>
<input type="button" id="insertActivity" name="insertActivity" value="提交" style="background-color:#005eac; width: 87px; height: 35px;font-size:15px" >
<input type="button" value="取消" onclick="window.location.href='./activity_admin.jsp'" style="cursor:pointer;background-color:#005eac; width: 87px; height: 35px;font-size:15px" >

</div>
</div>
</body>
</html>