<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="CSS/people.css">
<script language="javascript" type="text/javascript" src="JS/lib/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript" src="JS/peoplegallery.js"></script>
<title>People</title>
</head>
<body>
	<div class="peopleheader">
    	<div class="headerlogo">人物志</div>
    </div>
    <div class="peoplediscription">
    	<div class="peoplehead"><img id="nowpeople" src="" style="filter: blendTrans(duration=0.3)" width="150px" height="150px" border="0px"/></div>
        <div class="peoplename" id="nowname"></div>
        <div class="peopleintro" id="nowpeopleIntro"></div>
    </div>
    <div class="peoplegallery">
        <input id="backward" class="peoplebutton1" type="button" value="&uarr;" onclick="pback()"/>
    	<div class="peoplegallerypicture"><img class="peoplekuang" src="Img/People/PeopleKuang.png" width="90px" height="90px"/><img id="people1" src="" style="filter: blendTrans(duration=0.3)" width="90px" height="90px" border="0px"/></div>
        <div class="shortdescription" id="name1"></div>
        <div class="peoplegallerypicture"><img class="peoplekuang" src="Img/People/PeopleKuang.png" width="160px" height="160px"/><img id="people2" src="" style="filter: blendTrans(duration=0.3)" width="160px" height="160px" border="0px"/></div>
        <div class="peoplegallerypicture"><img class="peoplekuang" src="Img/People/PeopleKuang.png" width="90px" height="90px"/><img id="people3" src="" style="filter: blendTrans(duration=0.3)" width="90px" height="90px" border="0px"/></div>
        <div class="shortdescription" id="name3"></div>
        <input id="forward" class="peoplebutton2" type="button" value="&darr;" onclick="pgo()"/>
    </div>
</body>
</html>