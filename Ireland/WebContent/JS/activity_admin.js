// JavaScript Document

$(document).ready(function(){
	
	var activityArray=new Array();
	
	$("#insertActivity").mouseover(
			function(){
				$(this).css("cursor","pointer");
			});
	$("#updateActivity").mouseover(
			function(){
				$(this).css("cursor","pointer");
			});
	
	$.ajax({  
        url:'../../ActivityServlet',
        type:'POST',
        data:{method:'getActivity'},
        async:false,
        dataType:'json',
        error:function(){  
        alert("error occured!!!");  
        },  
        success:function(data){
        $("#activitylist").empty();
        $("#activitylist").append("<caption>特色活动列表</caption>");
        $("#activitylist").append("<tr><td style= 'width:30px;'>id</td><td style= 'width:150px;'>活动名称</td><td style= 'width:400px;'>活动简介</td><td style= 'width:80px;'>操作</td></tr>");
        	
      	  for(var i=0;i<data.length;i++)
      	  {
      		activityArray[i]=data[i];

    	var tbBody = "";
      	  tbBody +="<tr>"
      		  	 +"<td>"+activityArray[i].id+"</td>"
      		     +"<td>"+activityArray[i].name+"</td>"
      		     +"<td style= 'width:200px;word-break:   break-all '>"+activityArray[i].briefIntroduction+"</td>"
      		     +"<td><a href='javascript:void(0);' onclick='gotoUpdateActivity("+activityArray[i].id+")'>修改</a> | <a href='javascript:void(0);' onclick='deleteActivity("+activityArray[i].id+")'>删除</a></td>"
      		     +"</tr>";
      	  
      	  $("#activitylist").append(tbBody);
        }
        }  
  	});
	
	
	 $("#uploadImage").uploadify({
         'uploader': '../../JS/lib/jquery.uploadify-v2.1.4/uploadify.swf',
         'script': '../../ActivityServlet',
         'cancelImg': '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
         'folder': 'UploadFile',
         'queueID': 'fileQueue',
         'method' : 'GET',
         'simUploadLimit' : 1,
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
		 		var activity_id = str.split(";")[0] ;
        	    var activity_imageURL =str.split(";")[1];

        	    $("#activity_id").val(activity_id);
        	    $("#activity_imageURL").val(activity_imageURL);
        	    },  
 
        onError: function(event, queueID, fileObj) {  
        	alert("文件:" + fileObj.name + "上传失败");  
        	$("#uploadsign").attr("src","../Img/Admin/upload/fail.gif");
        	$("#uploadsign").attr("style","display: ");
        	     }
     });
	
	$("#insertActivity").click(function(){
		$.ajax({  
		      url:'../../ActivityServlet', 
		      type:'POST',
		      data:{method:'insertActivity',activity_id:$('#activity_id').val(),activity_name:$("#activity_name").val(),activity_briefIntroduction:$("#activity_briefIntroduction").val(),activity_introduction:$("#activity_introduction").val(),activity_imageURL:$("#activity_imageURL").val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("提交成功！");
		    		  window.location.href="./activity_admin.jsp";
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
         'script': '../../ActivityServlet',
         'cancelImg': '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
         'folder': 'UploadFile',
         'queueID': 'fileQueue2',
         'method' : 'GET',
         'simUploadLimit' : 1,
         'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
         'fileDesc' : '图片文件',
         'auto': false,
         'multi': false,
         'buttonText' : 'Browser',//浏览按钮图片
         'buttonImg':'../../Img/Admin/upload/browser.jpg',
         'onSelect': function (event, queueID, fileObj)
         {
         	$("#updateImage").uploadifySettings('scriptData',{'method':'updateImage','activity_imageURL':$('#activity_imageURL').val()});
         },
         onComplete: function (event, queueID, fileObj, response, data) {
        	    
		 		alert("文件:" + fileObj.name + "上传成功"); 
		 		$("#uploadsign").attr("style","display: ");
        	    },  
 
        onError: function(event, queueID, fileObj) {  
        	alert("文件:" + fileObj.name + "上传失败");  
        	$("#uploadsign").attr("src","../Img/Admin/upload/fail.gif");
        	$("#uploadsign").attr("style","display: ");
        	     }
     });
	
	 $("#updateActivity").click(function(){
			$.ajax({  
			      url:'../../ActivityServlet', 
			      type:'POST',
			      data:{method:'updateActivity',activity_id:$('#activity_id').val(),activity_name:$("#activity_name").val(),activity_briefIntroduction:$("#activity_briefIntroduction").val(),activity_introduction:$("#activity_introduction").val(),activity_imageURL:$("#activity_imageURL").val()},
			      dataType:'json',
			      error:function(){  
			      alert("error occured!!!");  
			      },  
			      success:function(data){
			    	  if("yes"==data)
			    		  {
			    		  alert("提交成功！");
			    		  window.location.href="./activity_admin.jsp";
			    		  }
			    	  else 
			    		  {
			    		  alert("提交失败！请重新编辑");
			    		  }
			      }  
			    });
			
		});
});


function gotoUpdateActivity(id)
{
	window.location.href="./updateActivity.jsp?activityId="+id;
}

function deleteActivity(id)
{
	if(confirm("确认删除？"))
	{
	$.ajax({  
	      url:'../../ActivityServlet', 
	      type:'POST',
	      async:true,
	      data:{method:'deleteActivity',activityId:id},
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
	