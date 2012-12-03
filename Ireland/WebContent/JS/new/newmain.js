// JavaScript Document
/////////////////////////////////////////////////////////////////////////////Main
function marg(){
	if(!document.all){
		document.getElementById("header").style.marginTop = 14;
	}
}

function toAnnouncement(){
	$("#content").css({height:$("#index").height()},200);
	$("#contentinner").css({left:0,top:0},200);
}
function toIntroduction(){
	$("#content").css({height:$("#introduction").height()},200);
	$("#contentinner").css({left:-868,top:0},200);
}
function toNews(){
	$("#content").css({height:$("#news").height()},200);
	$("#contentinner").css({left:-1736,top:0},200);
}
function toCourse(){
	$("#content").css({height:$("#course").height()},200);
	$("#contentinner").css({left:-2604,top:0},200);
}
function toPeople(){
	$("#content").css({height:$("#people").height()},200);
	$("#contentinner").css({left:-3472,top:0},200);
}
function toAlbumOut(){
	$("#content").css({height:$("#albumout").height()},200);
	$("#contentinner").css({left:-4340,top:0},200);
}
function toAlbum(){
	$("#content").css({height:$("#album").height()},200);
	$("#contentinner").css({left:-4340,top:-420},200);
}
function toSpecial(){
	$("#content").css({height:$("#specialevent").height()},200);
	$("#contentinner").css({left:-5208,top:0},200);
}
function toSpecial_in(){
	$("#content").css({height:$("#specialinside").height()},200);
	$("#contentinner").css({left:-5208,top:-420},200);
}
function toTeacher(){
	$("#content").css({height:$("#teacher").height()},200);
	$("#contentinner").css({left:-6076,top:0},200);
}
function mainLoad(){
	toTeacher();
}
/////////////////////////////////////////////////////////////////////////////introductionPPT
var PPT_current = 0;
var PPT_number = 8;

var PPT_int = setInterval("slide1()",3000);

function slide1(){
	PPT_current++;
	if(PPT_current>PPT_number){
		PPT_current = 1;
	}
	var img = document.getElementById("introductionimage").innerHTML;
	if(document.all){
		img.filters.blendTrans.apply();
		img.src = "Img/Introduction/"+PPT_current+".png";
		img.filters.blendTrans.play();
	}else{
		img.src = "Img/Introduction/"+PPT_current+".png";
	}
}
/////////////////////////////////////////////////////////////////////////////gallary
var gallary_count = 3;
var gallary_number = 8;
var currentgallary_count = 3;

function back(){
	if(gallary_count>1){
		gallary_count--;
	}
	if(gallary_count>=1){
		if(currentgallary_count == 1){
			document.getElementById("picture1").innerHTML = "<img id='picture11' src='Img/Album/"+(gallary_count)+".JPG' width='100px' height='80px' border='2px'/>";
			document.getElementById("picture2").innerHTML = "<img id='picture22' src='Img/Album/"+(gallary_count+1)+".JPG' width='100px' height='80px' border='0px'/>";
			document.getElementById("picture3").innerHTML = "<img id='picture33' src='Img/Album/"+(gallary_count+2)+".JPG' width='100px' height='80px' border='0px'/>";
			document.getElementById("picture4").innerHTML = "<img id='picture44' src='Img/Album/"+(gallary_count+3)+".JPG' width='100px' height='80px' border='0px'/>";
			document.getElementById("picture5").innerHTML = "<img id='picture55' src='Img/Album/"+(gallary_count+4)+".JPG' width='100px' height='80px' border='0px'/>";
		}else{
			currentgallary_count--;
			//var content = document.getElementById("picture"+currentgallary_count).innerHTML;
	        //var result = content.replace("border=0","border=2");
	        //document.getElementById("picture"+currentgallary_count).innerHTML = result;
			var content = document.getElementById("picture"+currentgallary_count+currentgallary_count);
			content.border = 2;
			
			//var content2 = document.getElementById("picture"+(currentgallary_count+1)).innerHTML;
	        //var result2 = content2.replace("border=2","border=0");
			//document.getElementById("picture"+(currentgallary_count+1)).innerHTML = result2;
			var content2 = document.getElementById("picture"+(currentgallary_count+1)+(currentgallary_count+1));
			content2.border = 0;
		}
	}
	var img = document.getElementById("bigpicture");
	if(document.all){
		img.filters.blendTrans.apply();
		img.src = "Img/Album/"+gallary_count+".JPG";
		img.filters.blendTrans.play();
	}else{
		img.src = "Img/Album/"+gallary_count+".JPG";
	}
	document.getElementById("bigpicture").src = 'Img/Album/'+gallary_count+'.JPG';
}
function go(){
	if(gallary_count<gallary_number){
		gallary_count++;
	}
	if(gallary_count<=gallary_number){
		if(currentgallary_count == 5){
			document.getElementById("picture1").innerHTML = "<img id='picture11' src='Img/Album/"+(gallary_count-4)+".JPG' width='100px' height='80px' border='0px'/>";
			document.getElementById("picture2").innerHTML = "<img id='picture22' src='Img/Album/"+(gallary_count-3)+".JPG' width='100px' height='80px' border='0px'/>";
			document.getElementById("picture3").innerHTML = "<img id='picture33' src='Img/Album/"+(gallary_count-2)+".JPG' width='100px' height='80px' border='0px'/>";
			document.getElementById("picture4").innerHTML = "<img id='picture44' src='Img/Album/"+(gallary_count-1)+".JPG' width='100px' height='80px' border='0px'/>";
			document.getElementById("picture5").innerHTML = "<img id='picture55' src='Img/Album/"+(gallary_count)+".JPG' width='100px' height='80px' border='2px'/>";
		}else{
			currentgallary_count++;
			var content = document.getElementById("picture"+currentgallary_count+currentgallary_count);
			content.border = 2;
			var content2 = document.getElementById("picture"+(currentgallary_count-1)+(currentgallary_count-1));
			content2.border = 0;
		}
		
	}
	var img = document.getElementById("bigpicture");
	if(document.all){
		img.filters.blendTrans.apply();
		img.src = "Img/Album/"+gallary_count+".JPG";
		img.filters.blendTrans.play();
	}else{
		img.src = "Img/Album/"+gallary_count+".JPG";
	}
	document.getElementById("bigpicture").src = 'Img/Album/'+gallary_count+'.JPG';
}
/////////////////////////////////////////////////////////////////////////////news
var news_current = 1;

var news_int = setInterval("slide2()",4000);

function slide2(){
	news_current++;
	if(news_current>5){
		news_current = 1;
	}
	var img = document.getElementById("newsimages");
	if(document.all){
		img.filters.blendTrans.apply();
		img.src = "Img/News/"+news_current+".JPG";
		img.filters.blendTrans.play();
	}else{
		img.src = "./Img/News/"+news_current+".JPG";
	}
	document.getElementById("combo1").innerHTML = "";
	document.getElementById("combo2").innerHTML = "";
	document.getElementById("combo3").innerHTML = "";
	document.getElementById("combo4").innerHTML = "";
	document.getElementById("combo5").innerHTML = "";
	document.getElementById("combo"+news_current).innerHTML = "<img src='Img/onfocus.png' width='14' height='13'/>";
}

function goto(number){
	number--;
	if(number==0){
		number = 5;
	}
	news_current =  number;
	slide2();
	
}
/////////////////////////////////////////////////////////////////////////////people
var people_count = 10002;
var people_number = 5
var namearray = new Array();
namearray[0]="Hu Jintao";namearray[1]="Ao Bama";namearray[2]="Pu Jin";namearray[3]="De Yue";namearray[4]="Fei Dele";

function pback(){
	people_count--;
	var current = people_count%people_number;
	var img1 = document.getElementById("people1");
	if(document.all){
		img1.filters.blendTrans.apply();
		img1.src = "Img/People/"+((people_count-1)%people_number+1)+".jpg";
		img1.filters.blendTrans.play();
	}else{
		img1.src = "Img/People/"+((people_count-1)%people_number+1)+".jpg";
	}
	
	var img2 = document.getElementById("people2");
	if(document.all){
		img2.filters.blendTrans.apply();
		img2.src = "Img/People/"+((people_count)%people_number+1)+".jpg";
		img2.filters.blendTrans.play();
	}else{
		img2.src = "Img/People/"+((people_count)%people_number+1)+".jpg";
	}
	var img3 = document.getElementById("people3");
	if(document.all){
		img3.filters.blendTrans.apply();
		img3.src = "Img/People/"+((people_count+1)%people_number+1)+".jpg";
		img3.filters.blendTrans.play();
	}else{
		img3.src = "Img/People/"+((people_count+1)%people_number+1)+".jpg";
	}
	var img4 = document.getElementById("nowpeople");
	if(document.all){
		img4.filters.blendTrans.apply();
		img4.src = "Img/People/"+((people_count)%people_number+1)+".jpg";
		img4.filters.blendTrans.play();
	}else{
		img4.src = "Img/People/"+((people_count)%people_number+1)+".jpg";
	}
	document.getElementById("name1").innerHTML = namearray[((people_count-1)%people_number)];
	document.getElementById("name2").innerHTML = namearray[((people_count+1)%people_number)];
	document.getElementById("nowname").innerHTML = namearray[(people_count%people_number)];
}

function pgo(){
	people_count++;
	var current = people_count%people_number;
	var img1 = document.getElementById("people1");
	if(document.all){
		img1.filters.blendTrans.apply();
		img1.src = "Img/People/"+((people_count-1)%people_number+1)+".jpg";
		img1.filters.blendTrans.play();
	}else{
		img1.src = "Img/People/"+((people_count-1)%people_number+1)+".jpg";
	}
	
	var img2 = document.getElementById("people2");
	if(document.all){
		img2.filters.blendTrans.apply();
		img2.src = "Img/People/"+((people_count)%people_number+1)+".jpg";
		img2.filters.blendTrans.play();
	}else{
		img2.src = "Img/People/"+((people_count)%people_number+1)+".jpg";
	}
	var img3 = document.getElementById("people3");
	if(document.all){
		img3.filters.blendTrans.apply();
		img3.src = "Img/People/"+((people_count+1)%people_number+1)+".jpg";
		img3.filters.blendTrans.play();
	}else{
		img3.src = "Img/People/"+((people_count+1)%people_number+1)+".jpg";
	}
	var img4 = document.getElementById("nowpeople");
	if(document.all){
		img4.filters.blendTrans.apply();
		img4.src = "Img/People/"+((people_count)%people_number+1)+".jpg";
		img4.filters.blendTrans.play();
	}else{
		img4.src = "Img/People/"+((people_count)%people_number+1)+".jpg";
	}
	//document.getElementById("people1").innerHTML = "<img src='Img/People/"+((people_count-1)%people_number+1)+".jpg' width='90px' height='90px' border='0px'/>";
	//document.getElementById("people2").innerHTML = "<img src='Img/People/"+(people_count%people_number+1)+".jpg' width='160px' height='160px' border='0px'/>";
	//document.getElementById("people3").innerHTML = "<img src='Img/People/"+((people_count+1)%people_number+1)+".jpg' width='90px' height='90px' border='0px'/>";
	//document.getElementById("nowpeople").innerHTML = "<img src='Img/People/"+(people_count%people_number+1)+".jpg' width='150px' height='150px' border='0px'/>";
	document.getElementById("name1").innerHTML = namearray[((people_count-1)%people_number)];
	document.getElementById("name2").innerHTML = namearray[((people_count+1)%people_number)];
	document.getElementById("nowname").innerHTML = namearray[(people_count%people_number)];
}












