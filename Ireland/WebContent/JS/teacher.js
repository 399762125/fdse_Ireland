$(document).ready(function(){ 
	
	var teacherArray = new Array();
	
    $.ajax({  
        url:'TeacherServlet',
        type:'POST',
        data:{method:'getTeacher'},
        async:false,
        dataType:'json',
        error:function(){  
        alert("error occured!!!");  
        },  
        success:function(data){

      	  for(var i=0;i<data.length;i++)
      	  {
      		teacherArray[i]=data[i];

    	var tbBody = "";
      	  tbBody +="<div class='teacher'>"
      		  +"<img src='"+teacherArray[i].imageURL+"'>"
      		  +"<p>"+teacherArray[i].name+"   ("+teacherArray[i].position+")</p>"
      		  +"<p>"+"办公室："+teacherArray[i].office+"</p>"
      		  +"<div class='teacherworkcontent'>"+"研究领域："+teacherArray[i].researchArea+"</div>"
      		  +"<p>"+"电    话："+teacherArray[i].telephone+"</p>"
      		  +"</div>";
      	  
      	  $("#teachertable").append(tbBody);
        }
      	$("#content").css({height:$("#teacher").height()},200);
    	$("#contentinner").css({left:-6076,top:0},200);
        }  
  	});
    
    
    
});