<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="CSS/announcement.css">
<link rel="stylesheet" type="text/css" href="CSS/roller.css">
<script language="javascript" type="text/javascript" src="JS/lib/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript" src="JS/lib/jquery.roller.js"></script>
<script language="javascript" type="text/javascript" src="JS/announcement.js"></script>
<title>Announcement</title>
</head>

<body>
	<div class="announcementheader">
    	<div class="headerlogo">最新公告</div>
    </div>
    
    <div class="recent">
    	<div id="container">
	</div>
    </div>
    
    <div class="announcementgallery">
    	<div class="newsimage"><img id="newsimages" name="newsimages" style="filter: blendTrans(duration=1)" src="Img/Announcement/1.JPG" width="668" height="250"/></div>
    	<div class="kuang"></div>
        <div class="combo">
        	<div class="comboimg" id="combo5" onclick="go(5)"></div>
            <div class="comboimg" id="combo4" onclick="go(4)"></div>
            <div class="comboimg" id="combo3" onclick="go(3)"></div>
            <div class="comboimg" id="combo2" onclick="go(2)"></div>
            <div class="comboimg" id="combo1" onclick="go(1)"><img src="Img/onfocus.png" width="14" height="13"/></div>
        </div>
    </div>
    
    <div id="fourrecentnews">
    </div>
</body>
</html>