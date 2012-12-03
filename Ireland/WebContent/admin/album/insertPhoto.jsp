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
<script language="javascript" type="text/javascript" src="../../JS/photo_admin.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/swfobject.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.js"></script>
<title>上传照片</title>
</head>
<body onload="loadInsertPhoto()" style="text-align:left;background-color: #F0F5F8;">
<div style="text-align:center;" id="albumta">
<table id="albumtable" style="font-size:14px;border:1px solid blue;" border="1">
</table>
</div>
<br>
<br>
<div style="text-align:left;width: 500px; height: 500px;background-color: #F0F5F8;border:1px solid;">

<div id="uploadPhoto" style="text-align:left;width: 420px; height: 300px;">
<h4>上传照片</h4>

<P><label>选择相册：</label>
<select id="albumlist" name="albumlist" ></select>
</P>
<div>
<label>相册图片：</label>
<img id="uploadsign" src="../../Img/Admin/upload/success.gif" style="display:none;height: 23px; width: 34px"></div>

<div id="fileQueue"></div>
    <input type="file" name="uploadify" id="uploadify" />
    <p>
      <a href="javascript:$('#uploadify').uploadifyUpload()">上传</a>| 
      <a href="javascript:$('#uploadify').uploadifyClearQueue()">取消上传</a>
      |&nbsp;
      <a href="./album_admin.jsp" > 返回 </a>
      
    </p>

  <input type="hidden" id="album_id" name="album_id" value=""></input> 
</div>
</div>
</body>
</html>