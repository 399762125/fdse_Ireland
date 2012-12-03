<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="CSS/newmain.css">
<script language="javascript" type="text/javascript" src="JS/lib/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript" src="JS/album.js"></script>
<title>teacher</title>
</head>
	<body>
 <div class="main">
        <div class="content" id="content">
            <div class="contentinner" id="contentinner">
                <div class="pages" id="albumout"><!--///////////////////////////////Album/////////////////////////////////////// -->
            		<div class="news_courseheader">
                    	<div class="news_headerlogo">爱班相册</div>
                    </div>
                    <div id="albumtable" class="albuminnerdiv">
                    </div>
            	</div>
                
                <div class="pages" id="album">
                	<div class="album_albumheader">
                        <div class="album_headerlogo">爱班相册</div>
                    	<input type="button" class="albumoutbtn" value="返回" onClick="toAlbumOut()"/>
                    </div>
                    <div class="album_bigpicture">
                        <div class="album_picturecontent"><img id="bigpicture" src="" width="440px" height="330px"/></div>
                        <div class="album_discription" id="album_discription"></div>
                    </div>
                    <div class="album_gallery">
                        <input id="forward" class="album_picbutton1" type="button" value="&lt;" onClick="go()"/>
                        <div id="album_gallery_content">            
                        </div>
                        <input id="backward" class="album_picbutton2" type="button" value="&gt;" onClick="back()"/>
                    </div>
                </div>
   
            </div>
            
        </div>
	</div>
  </body>
</html>