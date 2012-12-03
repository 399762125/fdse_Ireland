// JavaScript Document

$(document).ready(function() {

		var albumId = 0;
		var photoArray=new Array();
		getUrlParms();
		$.ajax({  
		      url:'../../AlbumServlet',
		      type:'POST',
		      data:{method:'getPhotoByAlbum',albumId:albumId},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){

		    	  for(var i=0;i<data.length;i++)
		    	  {
		    		  photoArray[i]=data[i];
		    	  }    	  

		    	  $("#photoTable").empty();
			        $("#photoTable").append("<caption>该相册图片列表</caption>");
			        $("#photoTable").append("<tr><td style= 'width:30px;'>id</td><td style= 'width:300px;'>图片预览</td><td style= 'width:150px;'>图片简介</td><td style= 'width:100px;'>操作</td></tr>");
			        	
			      	  for(var i=0;i<data.length;i++)
			      	  {
			      		photoArray[i]=data[i];

			    	var tbBody = "";
			      	  tbBody +="<tr>"
			      		  	 +"<td>"+photoArray[i].id+"</td>"
			      		     +"<td >"+"<img src='../../"+photoArray[i].imageURL+"'  style='width:300px; height:200px;'  />"+"</td>"
			      		     +"<td style= 'width:100px;word-break:   break-all '>"+photoArray[i].introduction+"</td>"
			      		     +"<td><a href='javascript:void(0);' onclick='deletePhoto("+photoArray[i].id+", "+albumId+")'>删除</a></td>"
			      		     +"</tr>";
			      	  
			      	  $("#photoTable").append(tbBody);
		    	  }
		      }  
		    }); 
		
		function getUrlParms()    // 方法名字
		{
		     var args =  new Object();   // 声明并初始化一个 "类"(姑且叫类吧)
			
			// 获得地址(URL)"?"后面的字符串.
		     var query = location.search.substring(1); 
		     var pairs=query.split("&");  // 分割URL(别忘了'&'是用来连接下一个参数)
		     for(var i=0;i<pairs.length;i++)   
		     {   
		         var pos=pairs[i].indexOf('=');   
		            if(pos==-1)   continue; // 它在找有等号的 数组[i]
		            var argname=pairs[i].substring(0,pos); // 参数名字
		            var value=pairs[i].substring(pos+1);  // 参数值
					// 以键值对的形式存放到"args"对象中
		            args[argname]=unescape(value);      
		     }
		    // 这个不需要解释吧.除非你不懂什么叫做 键值对
		     albumId = parseInt(args['albumId']); 
		}
	
});



function deletePhoto(photoId,albumId)
{
	if(confirm("确认删除吗？"))
	{
	$.ajax({  
	      url:'../../AlbumServlet', 
	      type:'POST',
	      async:true,
	      data:{method:'deletePhoto', photoId:photoId,albumId:albumId},
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
