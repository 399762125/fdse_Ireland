	var count = 10002;
	var nowNumber = 0;
	var namearray = new Array();
	
$(document).ready(function(){ 
	
	$("#backward").mouseover(
			function(){
				$(this).css("cursor","pointer");
			});
	$("#forward").mouseover(
			function(){
				$(this).css("cursor","pointer");
			});
	
    $.ajax({  
      url:'PeopleServlet', 
      type:'POST',
      data:{method:'getPeople'},
      dataType:'json',
      error:function(){  
      alert("error occured!!!");  
      },  
      success:function(data){
    	  for(var i=0;i<data.length;i++)
    	  {
    		  namearray[i]=data[i];
    	  }
    	  nowNumber=0;
    	  //以下设置左栏
    	  $('#nowpeople').attr("src",namearray[nowNumber].imageURL);
    	  $('#nowname').html(namearray[nowNumber].name);
    	  $('#nowpeopleIntro').html(namearray[nowNumber].introduction);

    	  
    	  //以下是右栏
    	  $('#people1').attr("src",namearray[(nowNumber-1)<0? (nowNumber-1+namearray.length) :nowNumber-1].imageURL);
    	  $('#name1').html(namearray[(nowNumber-1)<0? (nowNumber-1+namearray.length) :nowNumber-1].name);
    	  
    	  $('#people2').attr("src",namearray[nowNumber].imageURL);
  
    	  $('#people3').attr("src",namearray[(nowNumber+1)>namearray.length-1? (nowNumber+1-namearray.length) : nowNumber+1].imageURL);
    	  $('#name3').html(namearray[(nowNumber+1)>namearray.length-1? (nowNumber+1-namearray.length) : nowNumber+1].name);
      }  
    }); 
    });

function pback(){
	
	nowNumber++;
	if(nowNumber>=namearray.length)nowNumber=nowNumber - namearray.length;
	
	$('#nowpeople').attr("src",namearray[nowNumber].imageURL);
	$('#nowname').html(namearray[nowNumber].name);
	$('#nowpeopleIntro').html(namearray[nowNumber].introduction);

	  
	  //以下是右栏
	$('#people1').attr("src",namearray[(nowNumber-1)<0? (nowNumber-1+namearray.length) :nowNumber-1].imageURL);
	$('#name1').html(namearray[(nowNumber-1)<0? (nowNumber-1+namearray.length) :nowNumber-1].name);
	  
	$('#people2').attr("src",namearray[nowNumber].imageURL);

	$('#people3').attr("src",namearray[(nowNumber+1)>namearray.length-1? (nowNumber+1-namearray.length) : nowNumber+1].imageURL);
	$('#name3').html(namearray[(nowNumber+1)>namearray.length-1? (nowNumber+1-namearray.length) : nowNumber+1].name);

}

function pgo(){
	
	nowNumber--;
	if(nowNumber<0)nowNumber=nowNumber + namearray.length;
	
	$('#nowpeople').attr("src",namearray[nowNumber].imageURL);
	$('#nowname').html(namearray[nowNumber].name);
	$('#nowpeopleIntro').html(namearray[nowNumber].introduction);

	  
	  //以下是右栏
	$('#people1').attr("src",namearray[(nowNumber-1)<0? (nowNumber-1+namearray.length) :nowNumber-1].imageURL);
	$('#name1').html(namearray[(nowNumber-1)<0? (nowNumber-1+namearray.length) :nowNumber-1].name);
	  
	$('#people2').attr("src",namearray[nowNumber].imageURL);

	$('#people3').attr("src",namearray[(nowNumber+1)>namearray.length-1? (nowNumber+1-namearray.length) : nowNumber+1].imageURL);
	$('#name3').html(namearray[(nowNumber+1)>namearray.length-1? (nowNumber+1-namearray.length) : nowNumber+1].name);

}























