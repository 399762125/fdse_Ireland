$(document).ready(function() {

	$("#logout").click(function(){
		$.ajax({  
		      url:'LogoutServlet',
		      type:'POST',
		      data:{},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  alert("注销成功！");
		    		  window.location.href="index.jsp";
		    		  }
		      }  
		    });
		
	});
});

//function show(id)
//{
//	if(id=1)
//	{
//		$("#content").empty();
//		$("#content").append("<div><jsp:include page='admin/intro/intro_admin.jsp' flush='true' ></jsp:include></div>");
//	}
//	else if(id=2)
//	{
//		$("#content").empty();
//		$("#content").append("<jsp:include page='admin/notice/notice_admin.jsp' flush='true'></jsp:include>");
//	}
//	else if(id=3)
//	{
//		$("#content").empty();
//		$("#content").append("<jsp:include page='admin/news/news_admin.jsp' ></jsp:include>");
//	}
//	else if(id=4)
//	{
//		$("#content").empty();
//		$("#content").append("<jsp:include page='admin/course/course_admin.jsp' ></jsp:include>");
//	}
//	else if(id=5)
//	{
//		$("#content").empty();
//		$("#content").append("<jsp:include page='admin/activity/activity_admin.jsp' ></jsp:include>");
//	}
//	else if(id=6)
//	{
//		$("#content").empty();
//		$("#content").append("<jsp:include page='admin/people/people_admin.jsp' ></jsp:include>");
//	}
//	else if(id=7)
//	{
//		$("#content").empty();
//		$("#content").append("<jsp:include page='admin/teacher/teacher_admin.jsp' ></jsp:include>");
//	}
//	else if(id=8)
//	{
//		$("#content").empty();
//		$("#content").append("<jsp:include page='admin/album/album_admin.jsp' ></jsp:include>");
//	}
//	
//}