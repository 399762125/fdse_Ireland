// JavaScript Document

function iFrameHeight() { 
	var ifm= document.getElementById("target_frame"); 
	var subWeb = document.frames ? document.frames["target_frame"].document : ifm.contentDocument; 
	if(ifm != null && subWeb != null) { 
		ifm.height = subWeb.body.scrollHeight-15; 
	} 
}
function marg(){
	if(!document.all){
		document.getElementById("header").style.marginTop = 14;
	}
}
