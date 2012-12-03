	var current = 1;

	var int = setInterval("slide()",4000);
	
$(document).ready(function(){ 

	var newsArray = new Array();
	var totalNum=0;
	var sizePerPage=4;

	$.ajax({  
	      url:'NoticeServlet',
	      type:'POST',
	      data:{method:'getNotice'},
	      async:false,
	      dataType:'json',
	      error:function(){  
	      alert("error occured!!!");  
	      },  
	      success:function(data){

	    	  $('#container').roller({
	    		 	showNum:3,
	    		 	interval: 2000,
	    			direction: 'up', //滚动方向
	    		 	items: data
	    		});
	    	  
	    	  
	    	  
	      }  
		});
    
    $.ajax({  
        url:'NewsServlet',
        type:'POST',
        data:{method:'getNews'},
        async:false,
        dataType:'json',
        error:function(){  
        alert("error occured!!!");  
        },  
        success:function(data){

      	  for(var i=0;i<4;i++)
      	  {
      		  newsArray[i]=data[i];

    	var tbBody = "";
      	  tbBody +="<div class='recentnews'>"
      		  +"<div class='piclogo'>["+newsArray[i].date+"]</div>"
      		  +"<div class='pic'><img src='"+newsArray[i].imageURL+"' width='170px' height='125px' border='0px'/></div>"
      		  +"<div class='newstitle'>"+newsArray[i].title+"</div>"
      		  +"</div>";
      	  
      	  $("#fourrecentnews").append(tbBody);
        }
      	  
        }  
  	});
    
    
    
});

function slide(){
	current++;
	if(current>5){
		current = 1;
	}
	var img = document.getElementById("newsimages");
	if(document.all){
		img.filters.blendTrans.apply();
		img.src = "Img/Announcement/"+current+".JPG";
		img.filters.blendTrans.play();
	}else{
		img.src = "Img/Announcement/"+current+".JPG";
	}
	document.getElementById("combo1").innerHTML = "";
	document.getElementById("combo2").innerHTML = "";
	document.getElementById("combo3").innerHTML = "";
	document.getElementById("combo4").innerHTML = "";
	document.getElementById("combo5").innerHTML = "";
	document.getElementById("combo"+current).innerHTML = "<img src='Img/onfocus.png' width='14' height='13'/>";
}

function go(number){
	number--;
	if(number==0){
		number = 5;
	}
	current =  number;
	slide();
	
}



