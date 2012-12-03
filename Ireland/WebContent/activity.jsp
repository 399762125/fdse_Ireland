<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="CSS/newmain.css">
<script language="javascript" type="text/javascript" src="JS/lib/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript" src="JS/activity.js"></script>
<title>teacher</title>
</head>
<body>
<div class="main">
        <div class="content" id="content">
            <div class="contentinner" id="contentinner">
                <div class="pages" id="specialevent" style="left:5208px;"><!--////////////////////////////////Special Event//////////////////////////////// -->
                	<div class="news_courseheader">
                    	<div class="news_headerlogo">特色活动</div>
                    </div>
                    <div id="eventtable" class="specialeventinnerdiv" style="margin-top:0px;">
                    </div>
                </div>
                
                <div class="pages" id="specialinside" style="left:5208px; display:none;"><!--///////////////////////Special Event Down//////////// -->
                	<div class="news_courseheader">
                    	<div class="news_headerlogo">活动详细信息</div>
                    </div>
                    <div class="specialindiv" style="margin-top:0px;">
                		<img class="specialinimg" id="eventimage" src="" />
                    	<input type="button" class="specialoutbtn" value="返回" onClick="backToActivity()"/>
                    	<p class="special_p3" id="eventname"></p>
                    	<p class="special_p4" id="eventIntroduction"></p>
                    </div>
                </div>

            </div>
        </div>
	</div>
  </body>
</html>