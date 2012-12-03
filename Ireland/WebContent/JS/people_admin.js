// JavaScript Document

$(document).ready(function(){

var peopleArray=new Array();

	$("#insertPeople").mouseover(
			function(){
				$(this).css("cursor","pointer");
			});
	$("#updatePeople").mouseover(
			function(){
				$(this).css("cursor","pointer");
			});
	
    $.ajax({  
        url:'../../PeopleServlet',
        type:'POST',
        data:{method:'getPeople'},
        async:false,
        dataType:'json',
        error:function(){  
        alert("error occured!!!");  
        },  
        success:function(data){

        $("#peoplelist").empty();
        $("#peoplelist").append("<caption>人物志列表</caption>");
        $("#peoplelist").append("<tr><td style= 'width:30px;'>id</td><td style= 'width:90px;'>姓名</td><td style= 'width:400px;'>简介</td><td style= 'width:80px;'>操作</td></tr>");
        	
      	  for(var i=0;i<data.length;i++)
      	  {
      		peopleArray[i]=data[i];

    	var tbBody = "";
      	  tbBody +="<tr>"
      		  	 +"<td>"+peopleArray[i].id+"</td>"
      		     +"<td>"+peopleArray[i].name+"</td>"
      		     +"<td style= 'width:300px;word-break:break-all'>"+peopleArray[i].introduction+"</td>"
      		     +"<td><a href='javascript:void(0);' onclick='gotoUpdatePeople("+peopleArray[i].id+")'>修改</a> | <a href='javascript:void(0);' onclick='deletePeople("+peopleArray[i].id+")'>删除</a></td>"
      		     +"</tr>";
      	  
      	  $("#peoplelist").append(tbBody);
        }
        }  
  	});
	
	 $("#insertImage").uploadify({
         'uploader': '../../JS/lib/jquery.uploadify-v2.1.4/uploadify.swf',
         'script': '../../PeopleServlet',
         'cancelImg': '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
         'method': 'GET',
         'folder': 'UploadFile',
         'queueID': 'fileQueue',
         'simUploadLimit' : 1,
         'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
         'fileDesc' : '图片文件',
         'auto': false,
         'multi': false,
         'buttonText' : 'Browser',//浏览按钮图片
         'buttonImg':'../../Img/Admin/upload/browser.jpg',
         'onSelect': function (event, queueID, fileObj)
         {
         $("#insertImage").uploadifySettings('scriptData',{'method':'uploadImage'});
         },
         onComplete: function (event, queueID, fileObj, response, data) {
        	    
		 		alert("文件:" + fileObj.name + "上传成功"); 
		 		$("#uploadsign").attr("style","display: ");
		 		var str = response.substring(1,response.length-1);
		 		var people_id = str.split(";")[0] ;
        	    var people_imageURL =str.split(";")[1];

        	    $("#people_id").val(people_id);
        	    $("#people_imageURL").val(people_imageURL);
        	    },  
//        onAllComplete: function (filesUploaded, errors, allBytesLoaded, speed ) {  	
//    	    alert("所有文件上传成功！");
//    	    },  
        onError: function(event, queueID, fileObj) {  
        	alert("文件:" + fileObj.name + "上传失败");  
        	$("#uploadsign").attr("src","../../Img/Admin/upload/fail.gif");
        	$("#uploadsign").attr("style","display: ");
        	     }
     });
	
	$("#insertPeople").click(function(){
		$.ajax({  
		      url:'../../PeopleServlet', 
		      type:'POST',
		      data:{method:'insertPeople',people_id:$('#people_id').val(),people_name:$('#people_name').val(),people_introduction:$('#people_introduction').val(),people_imageURL:$('#people_imageURL').val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("提交成功！");
		    		  window.location.href="./people_admin.jsp";
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
        'script': '../../PeopleServlet',
        'cancelImg': '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
        'method': 'GET',
        'folder': 'UploadFile',
        'queueID': 'fileQueue2',
        'simUploadLimit' : 1,
        'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
        'fileDesc' : '图片文件',
        'auto': false,
        'multi': false,
        'buttonText' : 'Browser',//浏览按钮图片
        'buttonImg':'../../Img/Admin/upload/browser.jpg',
        'onSelect': function (event, queueID, fileObj)
        {
        $("#updateImage").uploadifySettings('scriptData',{'method':'updateImage','people_imageURL':$('#people_imageURL').val()});
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
	
	
	$("#updatePeople").click(function(){
		$.ajax({  
		      url:'../../PeopleServlet', 
		      type:'POST',
		      data:{method:'updatePeople',people_id:$('#people_id').val(),people_name:$('#people_name').val(),people_introduction:$('#people_introduction').val(),people_imageURL:$('#people_imageURL').val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("提交成功！");
		    		  window.location.href="./people_admin.jsp";
		    		  }
		    	  else 
		    		  {
		    		  alert("提交失败！请重新编辑");
		    		  }
		      }  
		    });
		
	});

});

function gotoUpdatePeople(id)
{
	window.location.href="./updatePeople.jsp?peopleId="+id;
}

function deletePeople(id)
{
	if(confirm("确认删除？"))
	{
	$.ajax({  
	      url:'../../PeopleServlet', 
	      type:'POST',
	      async:true,
	      data:{method:'deletePeople',peopleId:id},
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
	