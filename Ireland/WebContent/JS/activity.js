
var activityArray = new Array();

	
$(document).ready(function(){ 

    $.ajax({  
        url:'ActivityServlet',
        type:'POST',
        data:{method:'getActivity'},
        async:false,
        dataType:'json',
        error:function(){  
        alert("error occured!!!");  
        },
        success:function(data){
      	  for(var i=0;i<data.length;i++)
      	  {
      		activityArray[i]=data[i];

    	var tbBody = "";
      	  tbBody +="<div class='specialevent' >"
      		  +"<img src='"+activityArray[i].imageURL+"' />"
      		  +"<p class='special_p1'>"+activityArray[i].name+"</p>"
      		  +"<p class='special_p2'>"+activityArray[i].briefIntroduction
      		  +"<a style='color:#0000FF; font-size:13px;text-decoration:underline;' href='javascript:void(0);' onclick='gotoActivity("+i+");'>"+"  >>详情"+"</a></p>"
      		  +"</div>";
      	  
      	  $("#eventtable").append(tbBody);
     
        }
      	$("#content").css({height:$("#specialevent").height()},200);
    	$("#contentinner").css({left:-5208,top:0},200);
        }  
  	});
    
});

function gotoActivity(index)
{
	$("#eventimage").attr("src",activityArray[index].imageURL);
	$("#eventname").html(activityArray[index].name);
	$("#eventIntroduction").html(activityArray[index].introduction);

	$("#specialevent").hide();
	$("#specialinside").show();

}

function backToActivity()
{
	window.location.href="activity.jsp";
	activityArray = new Array();
}