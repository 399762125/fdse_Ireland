// JavaScript Document

$(document).ready(function(){ 
	
var noticeArray=new Array();
	
	$.ajax({  
        url:'../../NoticeServlet',
        type:'POST',
        data:{method:'getNoticeEntity'},
        async:false,
        dataType:'json',
        error:function(){  
        alert("error occured!!!");  
        },  
        success:function(data){
        $("#noticelist").empty();
        $("#noticelist").append("<caption>公告列表</caption>");
        $("#noticelist").append("<tr><td style= 'width:50px;'>id</td><td style= 'width:300px;'>公告内容</td><td style= 'width:100px;'>日期</td><td style= 'width:80px;'>操作</td></tr>");
        	
      	  for(var i=0;i<data.length;i++)
      	  {
      		noticeArray[i]=data[i];

    	var tbBody = "";
      	  tbBody +="<tr>"
      		  	 +"<td>"+noticeArray[i].id+"</td>"
      		     +"<td style= 'width:300px;word-break:   break-all '>"+noticeArray[i].notice+"</td>"
      		     +"<td>"+noticeArray[i].date+"</td>"
      		     +"<td><a href='javascript:void(0);' onclick='gotoUpdateNotice("+noticeArray[i].id+")'>修改</a> | <a href='javascript:void(0);' onclick='deleteNotice("+noticeArray[i].id+")'>    删除 </a></td>"
      		     +"</tr>";
      	  
      	  $("#noticelist").append(tbBody);
        }
        }  
  	});
	
	$("#insertNotice").mouseover(
			function(){
				$(this).css("cursor","pointer");
			});
	$("#updateNotice").mouseover(
			function(){
				$(this).css("cursor","pointer");
			});
	
	$("#insertNotice").click(function(){
		$.ajax({  
		      url:'../../NoticeServlet', 
		      type:'POST',
		      data:{method:'insertNotice',notice:$('#notice').val(),notice_date:$('#notice_date').val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("保存成功！");
		    		  window.location.href='./notice_admin.jsp';
		    		  }
		    	  else 
		    		  {
		    		  alert("保存失败！请重新编辑");
		    		  }
		      }  
		    });
		
	});
	
	$("#updateNotice").click(function(){
		$.ajax({  
		      url:'../../NoticeServlet', 
		      type:'POST',
		      data:{method:'updateNotice',notice_id:$('#notice_id').val(),notice_content:$('#notice_content').val(),notice_date:$('#notice_date').val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("保存成功！");
		    		  window.location.href='./notice_admin.jsp';
		    		  }
		    	  else 
		    		  {
		    		  alert("保存失败！请重新编辑");
		    		  }
		      }  
		    });
		
	});


	});


function gotoUpdateNotice(id)
{
	window.location.href="./updateNotice.jsp?noticeId="+id;//给链接href属性赋值
	
}

function deleteNotice(id)
{
	if(confirm("确实删除？"))	{
	$.ajax({  
	    url:'../../NoticeServlet', 
	    type:'POST',
	    async:true,
	    data:{method:'deleteNotice',noticeId:id},
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
	