// JavaScript Document

$(document).ready(function(){ 
	
	var uploadPhotoArray = new Array();
	
	$("#updateIntro").mouseover(
			function(){
				$(this).css("cursor","pointer");
			});
	
	$("#updateIntro").click(function(){
		$.ajax({  
		      url:'../../IntroductionServlet', 
		      type:'POST',
		      data:{method:'updateIntro',introduction:$('#introduction').val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("保存成功！");
		    		  window.location.href="./intro_admin.jsp";
		    		  }
		    	  else 
		    		  {
		    		  alert("保存失败！请重新编辑");
		    		  }
		      }  
		    });
		
	});
	
	$("#uploadify").uploadify( {
		'uploader' : '../../JS/lib/jquery.uploadify-v2.1.4/uploadify.swf',
		'script' : '../../IntroductionServlet?method=uploadify',
		'cancelImg' : '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
		'folder' : '11',
		'queueID' : 'fileQueue',
		'method' : 'POST',
		'simUploadLimit' : 100,
		'queueSizeLimit' : 100,//可以一次选定几个文件
		'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
		'fileDesc' : '图片文件',
		'auto' : false,
		'multi' : true,
		'buttonText' : 'Browser',//浏览按钮图片
		'buttonImg' : '../../Img/Admin/upload/browser.jpg',
		'onComplete' : function(event, queueID, fileObj, response, data) {
			uploadPhotoArray.push(fileObj.name);
		},
		'onAllComplete' : function(filesUploaded, errors, allBytesLoaded, speed) {
			$.ajax( {
				url : '../../IntroductionServlet',
				type : 'POST',
				data : {
					method:'insertPhoto',
					photolist : uploadPhotoArray.join(';')
				},
				dataType : 'json',
				error : function() {
				alert("error occured!!!");  
				},
			success : function(data) {
				if ("yes" == data) {
					alert("所有文件上传成功！");
					window.location.href="./intro_admin.jsp";
				} else {
					alert("提交失败！请重新编辑");
				}
			}
			});

			uploadPhotoArray = new Array();
		},
		'onError' : function(event, queueID, fileObj) {
			alert("文件:" + fileObj.name + "上传失败");
			$("#uploadsign").attr("src", "../../Img/Admin/upload/fail.gif");
			$("#uploadsign").attr("style", "display: ");

			uploadPhotoArray = new Array();
		}
	});

});

function loadPhoto()
{
	var photo=new Array();
	$.ajax({  
	      url:'../../IntroductionServlet',
	      type:'POST',
	      data:{method:'getPhotoList'},
	      dataType:'json',
	      error:function(){  
	      alert("error occured!!!");  
	      },  
	      success:function(data){
 	  
	    	  $("#photoTable").empty();
		        $("#photoTable").append("<caption>图片列表</caption>");
		        $("#photoTable").append("<tr><td style= 'width:80px;'>id</td><td style= 'width:300px;'>图片预览</td><td style= 'width:100px;'>操作</td></tr>");
		        	
		      	  for(var i=0;i<data.length;i++)
		      	  {
		      		photo[i]=data[i];

		    	var tbBody = "";
		      	  tbBody +="<tr>"
		      		  	 +"<td>"+photo[i].id+"</td>"
		      		     +"<td >"+"<img src='../../"+photo[i].imageURL+"'  style='width:300px; height:200px;'  />"+"</td>"
		      		     +"<td><a href='javascript:void(0);' onclick='deletePhoto("+photo[i].id+")'>删除</a></td>"
		      		     +"</tr>";
		      	  
		      	  $("#photoTable").append(tbBody);
	    	  }
	      }  
	    }); 
}

function deletePhoto(photoId)
{
	if(confirm("确实删除？"))
	{
	$.ajax({  
	      url:'../../IntroductionServlet', 
	      type:'POST',
	      async:true,
	      data:{method:'deletePhoto', photoId:photoId},
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
	