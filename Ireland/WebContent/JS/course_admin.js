// JavaScript Document

$(document).ready(function(){
	var courseArray=new Array();
	
	$.ajax({  
        url:'../../CourseServlet',
        type:'POST',
        data:{method:'getCourse'},
        async:false,
        dataType:'json',
        error:function(){  
        alert("error occured!!!");  
        },  
        success:function(data){
        $("#courselist").empty();
        $("#courselist").append("<caption>课程列表</caption>");
        $("#courselist").append("<tr><td style= 'width:30px;'>id</td><td style= 'width:50px;'>课程号</td><td>课程名</td><td style= 'width:200px;'>课程简介</td><td style= 'width:80px;'>操作</td></tr>");
        	
      	  for(var i=0;i<data.length;i++)
      	  {
      		courseArray[i]=data[i];

      		var tbBody = "";
      		tbBody +="<tr>"
      		  	 +"<td>"+courseArray[i].id+"</td>"
      		     +"<td>"+courseArray[i].number+"</td>"
      		     +"<td>"+courseArray[i].name+"</td>"
      		     +"<td style= 'width:200px;word-break:   break-all '>"+courseArray[i].introduction+"</td>"
      		     +"<td><a href='javascript:void(0);' onclick='gotoUpdateCourse("+courseArray[i].id+")'>修改</a> | <a href='javascript:void(0);' onclick='deleteCourse("+courseArray[i].id+")'>删除</a></td>"
      		     +"</tr>";
      	  
      	  $("#courselist").append(tbBody);
        }
        }  
  	});
	
	
	
	$("#course_submit").mouseover(
			function(){
				$(this).css("cursor","pointer");
			});
	
	 $("#uploadify").uploadify({
         'uploader': '../..//JS/lib/jquery.uploadify-v2.1.4/uploadify.swf',
         'script': '../../CourseServlet?method='+'insertImage',
         'cancelImg': '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
         'folder': 'UploadFile',
         'queueID': 'fileQueue',
         'simUploadLimit' : 1,
         'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
         'fileDesc' : '图片文件',
         'auto': false,
         'multi': false,
         'buttonText' : 'Browser',//浏览按钮图片
         'buttonImg':'../../Img/Admin/upload/browser.jpg',
         onComplete: function (event, queueID, fileObj, response, data) {
        	    
		 		alert("文件:" + fileObj.name + "上传成功"); 
		 		$("#uploadsign").attr("style","display: ");
		 		var str = response.substring(1,response.length-1);
		 		var course_id = str.split(";")[0] ;
        	    var course_imageURL =str.split(";")[1];

        	    $("#course_id").val(course_id);
        	    $("#course_imageURL").val(course_imageURL);
        	    },  

        onError: function(event, queueID, fileObj) {  
        	alert("文件:" + fileObj.name + "上传失败");  
        	$("#uploadsign").attr("src","../../Img/Admin/upload/fail.gif");
        	$("#uploadsign").attr("style","display: ");
        }
     });
	
	$("#course_submit").click(function(){
		$.ajax({  
		      url:'../../CourseServlet', 
		      type:'POST',
		      data:{method:'insertCourse',course_no:$('#course_no').val(),course_name:$("#course_name").val(),course_introduction:$("#course_introduction").val(),course_id:$("#course_id").val(),course_imageURL:$("#course_imageURL").val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("提交成功！");
		    		  window.location.href='./course_admin.jsp';
		    		  }
		    	  else 
		    		  {
		    		  alert("提交失败！请重新编辑");
		    		  }
		      }  
		    });
		
	});
	
	$("#updateImage").uploadify({
        'uploader': '../..//JS/lib/jquery.uploadify-v2.1.4/uploadify.swf',
        'script': '../../CourseServlet',
        'cancelImg': '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
        'folder': 'temp',
        'queueID': 'fileQueue2',
        'method': 'GET', 
        'simUploadLimit' : 1,
        'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
        'fileDesc' : '图片文件',
        'auto': false,
        'multi': false,
        'buttonText' : 'Browser',//浏览按钮图片
      //  'buttonImg':'../../Img/Admin/upload/browser.jpg',
        'onSelect': function (event, queueID, fileObj)
        {
        $("#updateImage").uploadifySettings('scriptData',{'method':'updateImage','course_imageURL':$("#course_imageURL").val()});
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
	
	
	$("#updateCourse").click(function(){
		$.ajax({  
		      url:'../../CourseServlet', 
		      type:'POST',
		      data:{method:'updateCourse',course_id:$('#course_id').val(),course_no:$('#course_no').val(),course_name:$("#course_name").val(),course_introduction:$("#course_introduction").val(),course_imageURL:$("#course_imageURL").val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("提交成功！");
		    		  window.location.href='./course_admin.jsp';
		    		  }
		    	  else 
		    		  {
		    		  alert("提交失败！请重新编辑");
		    		  }
		      }  
		    });
		
	});

});


function gotoUpdateCourse(id)
{
	window.location.href="./updateCourse.jsp?courseId="+id;//给链接href属性赋值
	
}

function deleteCourse(id)
{
	if(confirm("确认删除？"))
	{
	$.ajax({  
	      url:'../../CourseServlet', 
	      type:'POST',
	      async:true,
	      data:{courseId:id,method:'deleteCourse'},
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
	