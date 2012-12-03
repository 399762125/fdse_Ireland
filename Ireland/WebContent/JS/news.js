	
$(document).ready(function(){ 

	var newsArray = new Array();
	var totalNum=0;
	var sizePerPage=4;

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
    	  
    	  for(var i=0;i<data.length;i++)
    	  {
    		  newsArray[i]=data[i];
    	  }
    	  totalNum=newsArray.length;
    	  
    	  Pagination(totalNum,sizePerPage);
    	    
    	  pageselectCallback(0,null);
    	  
      }  
	});

    
    //定义分页
    function Pagination(totalNum,sizePerPage)
    {
    	$("#Pagination").pagination(totalNum, {
    		items_per_page:sizePerPage,
    		prev_text:"上一页",
    		next_text:"下一页",
    	    callback: pageselectCallback
    	});
    }
    
    //点击页面跳转
    function pageselectCallback(page_id, jq){
    	
    	$("#newsTable tr").remove();
    	var nowNumber=page_id*sizePerPage; //从0开始
    	var endNumber=(page_id*sizePerPage+sizePerPage-1)<=(totalNum-1)?(page_id*sizePerPage+sizePerPage-1):(totalNum-1);

    	for(var i=nowNumber;i<=endNumber;i++)
    	{
    	var tbBody = "";
        var trColor;
      	  if (i % 2 == 0) {
                trColor = "newsodd";
            }
            else {
                trColor = "newseven";
            }
      	  tbBody +="<tr><td><div class='"+trColor+"'>"
      	  			+"<img class='newsimg' "+ "src='"+newsArray[i].imageURL+"' />"
      	  			+"<div class='newscontent'>"
      	  			+"<div class='contenthead'>"+newsArray[i].title+" ["+newsArray[i].date+"]" +"</div>"
      	  			+"<span>"+newsArray[i].content+"</span>"
      	  			+"</div>"
      	  			+"</div>"
      	  			+"</td></tr>";
      
      	  $("#newsTable").append(tbBody);
        }
    }
    
    
});





