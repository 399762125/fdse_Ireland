var current = 0;
var number = 0;
var imgArray=new Array();

$(document).ready(function(){ 
	
	var int = setInterval("slide()",3000);
	
    $.ajax({  
      url:'IntroductionServlet',
      type:'POST',
      data:{method:'getIntroduction'},
      dataType:'json',
      error:function(){  
      alert("error occured!!!");  
      },  
      success:function(data){  
    	  $('#introduction').html(data.introduction);
      }  
    }); 
    
    $.ajax({  
        url:'IntroductionServlet',
        type:'POST',
        data:{method:'getPhotoList'},
        dataType:'json',
        error:function(){  
        alert("error occured!!!");  
        },  
        success:function(data){  
        	number=data.length;
        	
        	imgArray=new Array();
        	
        	for(var i=0;i<data.length;i++)
        	{
        		imgArray[i]=data[i].imageURL;
        	}

        }  
      }); 
    
    });

function slide(){
	    	current++;
	    	if(current>number){
	    		current = 1;
	    	}
	    	var img = document.getElementById("introductionimage");
	    	if(document.all){
	    		img.filters.blendTrans.apply();
	    		img.src = imgArray[current-1];
	    		img.filters.blendTrans.play();
	    	}else{
	    		img.src = imgArray[current-1];
	    	}
	    }



