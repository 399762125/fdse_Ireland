

	var nowNumber = 0;
	var albumArray = new Array();
	var photoArray = new Array();
	
$(document).ready(function(){ 
    $.ajax({  
        url:'AlbumServlet',
        type:'POST',
        data:{method:'getAlbum'},
        async:false,
        dataType:'json',
        error:function(){  
        alert("error occured!!!");  
        },
        success:function(data){
      	  for(var i=0;i<data.length;i++)
      	  {
      		albumArray[i]=data[i];

    	var tbBody = "";
      	  tbBody +="<div class='album_out' onClick='getAlbumPhoto("+albumArray[i].id+");'>"
      		  +"<img src='"+"Img/Album/"+albumArray[i].id+"/"+albumArray[i].firstPageURL+"'>"
      		  +"<p>"+albumArray[i].title+"</p>"
      		  +"<p>"+"["+albumArray[i].creatTime+"]"+"</p>"
      		  +"</div>";
      	  
      	  $("#albumtable").append(tbBody);
     
        }
    	$("#albumout").show();
    	$("#album").hide();
        }  
  	});
});


function getAlbumPhoto(albumId)
{
	$.ajax({  
	      url:'AlbumServlet',
	      type:'POST',
	      data:{method:'getPhotoByAlbum',albumId:albumId},
	      dataType:'json',
	      error:function(){  
	      alert("error occured!!!");  
	      },  
	      success:function(data){
	    	  photoArray=new Array();
	    	  for(var i=0;i<data.length;i++)
	    	  {
	    		  photoArray[i]=data[i];
	    	  }    	  

	    	  $("#album_gallery_content").empty();
	    	     for(var j=0;j<photoArray.length &&j<5;j++)
	    	  {
	    		  var tbBody = "";
	          	  tbBody +="<div class='album_gallerypicture' id='gallery_"+photoArray[j].id+"' "+"onMouseover='mouseov("+j+");'>"
	          		  +"<img id='pic_"+photoArray[j].id+"' src='"+photoArray[j].imageURL+"' "
	          		  +"width='100px' height='80px' border='0px'"+"/>"
	          		  +"</div>";
	          	  
	          	  $("#album_gallery_content").append(tbBody);
	    	  }
	    	  nowNumber=0;
	    	  //以下设置上层大图标

	    	  $('#bigpicture').attr("src",photoArray[nowNumber].imageURL);
	    	  $('#album_discription').html(photoArray[nowNumber].introduction);

	    	  if(photoArray.length<=5)
	    		  {
	    		  $("#backward").attr("disabled",true);  
	    		  $("#forward").attr("disabled",true);  
	    		  }
	    	  
    		  $("#forward").attr("disabled",true);  
	      }  
	    }); 

	$("#albumout").hide();
	$("#album").show();
}

function mouseov(j)
{
	$(this).css("cursor","pointer");

	$('#bigpicture').attr("src",photoArray[j].imageURL);
	$('#album_discription').html(photoArray[j].introduction);
	
	
}

function toAlbumOut(){

	window.location.href="album.jsp";
	nowNumber = 0;
	albumArray = new Array();
	photoArray = new Array();
	
}


function back(){

	nowNumber++;
	var i=0;
	
	for(i=0;i<=4 && nowNumber+i<=photoArray.length;i++)
	{
		$('#pic_'+photoArray[i].id).attr("src",photoArray[nowNumber+i].imageURL);
		$('#gallery_'+photoArray[i].id).attr("onMouseover","mouseov("+(nowNumber+i)+")");
	}
	  //以下设置上层大图标
	  $('#bigpicture').attr("src",photoArray[nowNumber].imageURL);
	  $('#album_discription').html(photoArray[nowNumber].introduction);

	  if(nowNumber+i ==photoArray.length)
		  $("#backward").attr("disabled",true);
	  
	  if(nowNumber >=1)
		  $("#forward").attr("disabled",false);
}
function go(){
	
	nowNumber--;
	var i=0;
	
	for(i=4;i>=0 && nowNumber>=0;i--)
	{
		$('#pic_'+photoArray[i].id).attr("src",photoArray[nowNumber+i].imageURL);
		$('#gallery_'+photoArray[i].id).attr("onMouseover","mouseov("+(nowNumber+i)+")");
	}
	  //以下设置上层大图标
	  $('#bigpicture').attr("src",photoArray[nowNumber].imageURL);
	  $('#album_discription').html(photoArray[nowNumber].introduction);

	  if(nowNumber ==0 )
		  $("#forward").attr("disabled",true);
	  
	  if(nowNumber +5<=photoArray.length)
		  $("#backward").attr("disabled",false);
}