// JavaScript Document

$(document).ready(function(){ 
	
	$("#login").click(function(){
		$.ajax({  
		      url:'LoginServlet',
		      type:'POST',
		      data:{email:$('#email').val(),password:$('#password').val()},
		      dataType:'json',
		      error:function(){  
		      alert("error occured!!!");  
		      },  
		      success:function(data){
		    	  if("yes"==data)
		    		  {
		    		  window.location.href="admin.jsp";
		    		  }
		    	  else 
		    		  {
		    		  alert("登录失败！请重新登录");
		    		  window.location.href="login.jsp";
		    		  }
		      }  
		    });
		
	});

	});
	