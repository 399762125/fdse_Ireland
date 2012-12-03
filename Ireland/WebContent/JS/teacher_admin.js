// JavaScript Document

$(document).ready(function(){
	
var teacherArray = new Array();

    $.ajax({  
        url:'../../TeacherServlet',
        type:'POST',
        data:{method:'getTeacher'},
        async:false,
        dataType:'json',
        error:function(){  
        alert("error occured!!!");  
        },  
        success:function(data){

        $("#teacherlist").empty();
        $("#teacherlist").append("<caption>教师信息列表</caption>");
        $("#teacherlist").append("<tr><td style= 'width:30px;'>id</td><td style= 'width:100px;'>姓名</td><td style= 'width:100px;'>职位</td><td style= 'width:100px;'>办公室</td><td style= 'width:120px;'>电话</td><td style= 'width:100px;'>邮箱</td><td style= 'width:80px;'>操作</td></tr>");
        	
      	  for(var i=0;i<data.length;i++)
      	  {
      		teacherArray[i]=data[i];

    	var tbBody = "";
      	  tbBody +="<tr>"
      		  	 +"<td>"+teacherArray[i].id+"</td>"
      		     +"<td>"+teacherArray[i].name+"</td>"
      		     +"<td>"+teacherArray[i].position+"</td>"
      		     +"<td>"+teacherArray[i].office+"</td>"
      		     +"<td>"+teacherArray[i].telephone+"</td>"
      		     +"<td>"+teacherArray[i].email+"</td>"
      		     +"<td><a href='javascript:void(0);' onclick='gotoUpdateTeacher("+teacherArray[i].id+")'>修改</a> | <a href='javascript:void(0);' onclick='deleteTeacher("+teacherArray[i].id+")'>删除</a></td>"
      		     +"</tr>";
      	  
      	  $("#teacherlist").append(tbBody);
        }
        }  
  	});
	
	$("#insertTeacher").mouseover(
			function(){
				$(this).css("cursor","pointer");
	});
	$("#updateTeacher").mouseover(
			function(){
				$(this).css("cursor","pointer");
	});
	
	
	 $("#uploadImage").uploadify({
         'uploader': '../../JS/lib/jquery.uploadify-v2.1.4/uploadify.swf',
         'script': '../../TeacherServlet',
         'cancelImg': '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
         'folder': 'UploadFile',
         'queueID': 'fileQueue',
         'simUploadLimit' : 1,
         'method': 'GET', 
         'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
         'fileDesc' : '图片文件',
         'auto': false,
         'multi': false,
         'buttonText' : 'Browser',//浏览按钮图片
         'buttonImg':'../../Img/Admin/upload/browser.jpg',
         'onSelect': function (event, queueID, fileObj)
         {
         $("#uploadImage").uploadifySettings('scriptData',{'method':'uploadImage'});
         },
         onComplete: function (event, queueID, fileObj, response, data) {
        	    
		 		alert("文件:" + fileObj.name + "上传成功"); 
		 		$("#uploadsign").attr("style","display: ");
		 		var str = response.substring(1,response.length-1);
		 		var teacher_id = str.split(";")[0] ;
        	    var teacher_imageURL =str.split(";")[1];

        	    $("#teacher_id").val(teacher_id);
        	    $("#teacher_imageURL").val(teacher_imageURL);
        	    },  
        onError: function(event, queueID, fileObj) {  
        	alert("文件:" + fileObj.name + "上传失败");  
        	$("#uploadsign").attr("src","../../Img/Admin/upload/fail.gif");
        	$("#uploadsign").attr("style","display: ");
        	     }
     });
	
	$("#insertTeacher").click(function(){
		$.ajax({  
		      url:'../../TeacherServlet', 
		      type:'POST',
		      data:{method:'insertTeacher',teacher_id:$('#teacher_id').val(),teacher_name:$("#teacher_name").val(),teacher_position:$("#teacher_position").val(),teacher_office:$("#teacher_office").val(),teacher_telephone:$("#teacher_telephone").val(),teacher_email:$("#teacher_email").val(),teacher_area:$("#teacher_area").val(),teacher_imageURL:$("#teacher_imageURL").val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("提交成功！");
		    		  window.location.href="./teacher_admin.jsp";
		    		  }
		    	  else 
		    		  {
		    		  alert("提交失败！请重新编辑");
		    		  }
		      }  
		    });
		
	});
	
	$("#updateImage").uploadify({
        'uploader': '../../JS/lib/jquery.uploadify-v2.1.4/uploadify.swf',
        'script': '../../TeacherServlet',
        'cancelImg': '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
        'folder': 'UploadFile',
        'queueID': 'fileQueue2',
        'simUploadLimit' : 1,
        'method': 'GET', 
        'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
        'fileDesc' : '图片文件',
        'auto': false,
        'multi': false,
        'buttonText' : 'Browser',//浏览按钮图片
        'buttonImg':'../../Img/Admin/upload/browser.jpg',
        'onSelect': function (event, queueID, fileObj)
        {
        $("#updateImage").uploadifySettings('scriptData',{'method':'updateImage','teacher_imageURL': $("#teacher_imageURL").val()});
        },
        onComplete: function (event, queueID, fileObj, response, data) {
       	    
		 		alert("文件:" + fileObj.name + "上传成功"); 
		 		$("#uploadsign").attr("style","display: ");
       	    },  
       onError: function(event, queueID, fileObj) {  
       	alert("文件:" + fileObj.name + "上传失败");  
       	$("#uploadsign").attr("src","../../Img/Admin/upload/fail.gif");
       	$("#uploadsign").attr("style","display: ");
       	     }
    });
	
	$("#updateTeacher").click(function(){
		$.ajax({  
		      url:'../../TeacherServlet', 
		      type:'POST',
		      data:{method:'updateTeacher',teacher_id:$('#teacher_id').val(),teacher_name:$("#teacher_name").val(),teacher_position:$("#teacher_position").val(),teacher_office:$("#teacher_office").val(),teacher_telephone:$("#teacher_telephone").val(),teacher_email:$("#teacher_email").val(),teacher_area:$("#teacher_area").val(),teacher_imageURL:$("#teacher_imageURL").val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("提交成功！");
		    		  window.location.href="./teacher_admin.jsp";
		    		  }
		    	  else 
		    		  {
		    		  alert("提交失败！请重新编辑");
		    		  }
		      }  
		    });
		
	});

	});


function gotoUpdateTeacher(id)
{
	window.location.href="./updateTeacher.jsp?teacherId="+id;
}

function deleteTeacher(id)
{
	if(confirm("确认删除？"))
	{
	$.ajax({  
	      url:'../../TeacherServlet', 
	      type:'POST',
	      async:true,
	      data:{method:'deleteTeacher',teacherId:id},
	      dataType:'json',
  
	      success:function(data){
	    	  if("yes"==data)
	    		  {
	    		  alert("删除成功！");
	    		  window.location.reload("true");
	    		  }
	    	  else 
	    		  {
	    		  alert("删除失败！");
	    		  }
	      }, 
	      error:function(){  
		      alert("error occured!!!");  
		      }
	    });
	}
}
	