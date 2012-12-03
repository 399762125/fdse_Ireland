// JavaScript Document

var current = 1;

var int = setInterval("slide()",4000);

function slide(){
	current++;
	if(current>5){
		current = 1;
	}
	var img = document.getElementById("newsimages");
	if(document.all){
		img.filters.blendTrans.apply();
		img.src = "Img/News/"+current+".JPG";
		img.filters.blendTrans.play();
	}else{
		img.src = "Img/News/"+current+".JPG";
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


























