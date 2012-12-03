// JavaScript Document

$(document).ready(function() {

	var albumArray = new Array();

	$.ajax( {
		url : '../../AlbumServlet',
		type : 'POST',
		data : {method:'getAlbum'},
		async : false,
		dataType : 'json',
		error : function() {
			alert("error occured!!!");
		},
		success : function(data) {
	        $("#albumtable").empty();
	        $("#albumtable").append("<caption>相册列表</caption>");
	        $("#albumtable").append("<tr><td style= 'width:30px;'>id</td><td style= 'width:150px;'>相册名称</td><td style= 'width:200px;'>相册简介</td><td style= 'width:100px;'>创建时间</td><td style= 'width:180px;'>操作</td></tr>");
	        	
	      	  for(var i=0;i<data.length;i++)
	      	  {
	      		albumArray[i]=data[i];

	    	var tbBody = "";
	      	  tbBody +="<tr>"
	      		  	 +"<td>"+albumArray[i].id+"</td>"
	      		     +"<td>"+albumArray[i].title+"</td>"
	      		     +"<td style= 'width:200px;word-break:   break-all '>"+albumArray[i].introduction+"</td>"
	      		     +"<td>"+albumArray[i].creatTime+"</td>"
	      		     +"<td><a href='javascript:void(0);' onclick='gotoUpdatePhoto("+albumArray[i].id+")'>照片浏览</a> | <a href='javascript:void(0);' onclick='gotoUpdateAlbum("+albumArray[i].id+")'>修改</a> | <a href='javascript:void(0);' onclick='deleteAlbum("+albumArray[i].id+")'>删除</a></td>"
	      		     +"</tr>";
	      	  
	      	  $("#albumtable").append(tbBody);
	        }
		}
	});
	

	$("#insertAlbum").click(function() {
		$.ajax( {
			url : '../../AlbumServlet',
			type : 'POST',
			data : {method:'insertAlbum',album_title : $('#album_title').val(),
				    album_introduction : $('#album_introduction').val()},
			dataType : 'json',
			error : function() {
				alert("error occured!!!");
			},
			success : function(data) {
				if ("yes" == data) {
					alert("提交成功！");
					window.location.href="./album_admin.jsp";
				} else {
					alert("提交失败！请重新编辑");
				}
			}
		});
	});
	
	$("#updateAlbum").click(function() {
		$.ajax( {
			url : '../../AlbumServlet',
			type : 'POST',
			data : {method:'updateAlbum',album_id:$('#album_id').val() , album_title : $('#album_title').val(),
				    album_introduction : $('#album_introduction').val()},
			dataType : 'json',
			error : function() {
				alert("error occured!!!");
			},
			success : function(data) {
				if ("yes" == data) {
					alert("提交成功！");
					window.location.href="./album_admin.jsp";
				} else {
					alert("提交失败！请重新编辑");
				}
			}
		});

	});
});


function gotoUpdateAlbum(id)
{
	window.location.href="./updateAlbum.jsp?albumId="+id;
}


function gotoUpdatePhoto(id)
{
	window.location.href="./updatePhoto.jsp?albumId="+id;
}

function deleteAlbum(id)
{
	if(confirm("确认删除？"))
	{
	$.ajax({  
	      url:'../../AlbumServlet', 
	      type:'POST',
	      async:true,
	      data:{method:'deleteAlbum', albumId:id},
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
