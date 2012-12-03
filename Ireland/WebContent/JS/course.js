	
$(document).ready(function(){ 

	var courseArray = new Array();
    $.ajax({  
      url:'CourseServlet',
      type:'POST',
      data:{method:'getCourse'},
      async:false,
      dataType:'json',
      error:function(){  
      alert("error occured!!!");  
      },  
      success:function(data){
    	  
    	  for(var i=0;i<data.length;i++)
    	  {
    		  courseArray[i]=data[i];
    		  
    		  var tbBody = "";
          	  tbBody +="<div class='course'>"
          		  +"<div class='course_p1'>"+courseArray[i].name+"</div>"
          		  +"<img src='Img/Course/img1.png' class='courseimg2'/>"
          		  +"<img src='"+"Img/Course/"+courseArray[i].imageURL+"' class='courseimg'/>"
          		  +"<p class='course_p2'>"+"简介："+"</p>"
          		  +"<p class='course_p3'>"+courseArray[i].introduction+"</p>"
          		  +"</div>";
          	  
          	  $("#courseTable").append(tbBody);
    	  }
    	  $("#content").css({height:$("#course").height()},200);
    	  $("#contentinner").css({left:-2604,top:0},200);
    	  
      }  
	});  
});





