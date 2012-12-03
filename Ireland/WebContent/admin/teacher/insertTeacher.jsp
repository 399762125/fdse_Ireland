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
<script language="javascript" type="text/javascript" src="../../JS/teacher_admin.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/swfobject.js"></script>
<script language="javascript" type="text/javascript" src="../../JS/lib/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.js"></script>
<title>教师编辑页面</title>
</head>
<body style="text-align:center;background-color: #F0F5F8;">

<br>
<div style="text-align:center;width: 500px; height: 480px;background-color: #F0F5F8;border:1px solid;">
<div style="text-align:left;width: 420px; height: 300px;">
<h5>  新增教师信息 </h5>
<p><label>姓名: </label>
<input type="text" id="teacher_name" name="teacher_name" />
</p>
<p><label>职位: </label>
<input type="text" id="teacher_position" name="teacher_position" />
</p>
<p><label>办公室: </label>
<input type="text" id="teacher_office" name="teacher_office" />
</p>
<p><label>电话: </label>
<input type="text" id="teacher_telephone" name="teacher_telephone" />
</p>
<p><label>邮箱: </label>
<input type="text" id="teacher_email" name="teacher_email" />
</p>
<p><label>工作内容/研究领域： </label>
<input type="text" id="teacher_area" name="teacher_area" style="width: 374px"/>
</p>

<div>
<label>教师图片：</label>
<img id="uploadsign" src="../../Img/Admin/upload/success.gif" style="display:none;height: 23px; width: 34px"></div>

<div id="fileQueue"></div>
    <input type="file" name="uploadImage" id="uploadImage"/>
    <p>
      <a href="javascript:$('#uploadImage').uploadifyUpload()">上传</a>| 
      <a href="javascript:$('#uploadImage').uploadifyClearQueue()">取消上传</a>
      
    </p>
   
   <input type="hidden" id="teacher_id" name="teacher_id" value=""></input> 
   <input type="hidden" id="teacher_imageURL" name="teacher_imageURL" value=""></input>
<input type="button" id="insertTeacher" name="insertTeacher" value="提交" style="background-color:#005eac; width: 87px; height: 35px;font-size:15px" >
<input type="button" value="取消" onclick="window.location.href='./teacher_admin.jsp'" style="cursor:pointer;background-color:#005eac; width: 87px; height: 35px;font-size:15px" >

</div>
</div>
</body>
</html>