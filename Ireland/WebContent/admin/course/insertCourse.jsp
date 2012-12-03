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
<script language="javascript" type="text/javascript" src="../../JS/course_admin.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/swfobject.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.js"></script>
<title>增加课程</title>
</head>
<body style="text-align:center;background-color: #F0F5F8;">

<br>
<div style="text-align:center;width: 420px; height: 530px;background-color: #F0F5F8;border:1px solid;">
<div style="text-align:left;width: 420px; height: 300px;padding-top:10px;padding-left:10px">
<h4 >  &nbsp;&nbsp;增加课程 </h4>
<p><label>课程号: </label>
<input type="text" id="course_no" name="course_no" />
</p>
<p><label>课程名: </label>
<input type="text" id="course_name" name="course_name" />
</p>
<P><label>课程简介：</label>
<textarea id="course_introduction" style="height: 211px; width: 374px"></textarea>
</P>

<div>
<label>课程图片：</label>
<img id="uploadsign" src="../../Img/Admin/upload/success.gif" style="display:none;height: 23px; width: 34px"></div>

<div id="fileQueue"></div>
    <input type="file" name="uploadify" id="uploadify" />
    <p>
      <a href="javascript:$('#uploadify').uploadifyUpload()">上传</a>| 
      <a href="javascript:$('#uploadify').uploadifyClearQueue()">取消上传</a>
      
    </p>
   
   <input type="hidden" id="course_id" name="course_id" value=""></input> 
   <input type="hidden" id="course_imageURL" name="course_imageURL" value=""></input>
<input type="button" id="course_submit" name="course_submit" value="提交" style="background-color:#005eac; width: 87px; height: 35px;font-size:15px" >
<input type="button" value="取消" onclick="window.location.href='./course_admin.jsp'" style="cursor:pointer;background-color:#005eac; width: 87px; height: 35px;font-size:15px" >

</div>
</div>
</body>
</html>