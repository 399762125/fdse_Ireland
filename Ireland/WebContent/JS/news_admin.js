// JavaScript Document

$(document)
		.ready(
				function() {

					var newsArray = new Array();

					$
							.ajax( {
								url : '../../NewsServlet',
								type : 'POST',
								data : {
									method : 'getNews'
								},
								async : false,
								dataType : 'json',
								error : function() {
									alert("error occured!!!");
								},
								success : function(data) {
									$("#newslist").empty();
									$("#newslist").append(
											"<caption>新闻列表</caption>");
									$("#newslist")
											.append(
													"<tr><td style= 'width:30px;'>id</td><td style= 'width:100px;'>标题</td><td style= 'width:80px;'>日期</td><td style= 'width:300px;'>新闻内容</td><td style= 'width:80px;'>操作</td></tr>");

									for ( var i = 0; i < data.length; i++) {
										newsArray[i] = data[i];

										var tbBody = "";
										tbBody += "<tr>"
												+ "<td>"
												+ newsArray[i].id
												+ "</td>"
												+ "<td>"
												+ newsArray[i].title
												+ "</td>"
												+ "<td>"
												+ newsArray[i].date
												+ "</td>"
												+ "<td style= 'width:350px;word-break:   break-all '>"
												+ newsArray[i].content
												+ "</td>"
												+ "<td><a href='javascript:void(0);' onclick='gotoUpdateNews("
												+ newsArray[i].id
												+ ")'>修改</a> | <a href='javascript:void(0);' onclick='deleteNews("
												+ newsArray[i].id
												+ ")'>删除</a></td>" + "</tr>";

										$("#newslist").append(tbBody);
									}
								}
							});

					$("#insertNews").mouseover(function() {
						$(this).css("cursor", "pointer");
					});
					$("#updateNews").mouseover(function() {
						$(this).css("cursor", "pointer");
					});

					$("#uploadImage")
							.uploadify(
									{
										'uploader' : '../../JS/lib/jquery.uploadify-v2.1.4/uploadify.swf',
										'script' : '../../NewsServlet',
										'cancelImg' : '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
										'folder' : 'UploadFile',
										'queueID' : 'fileQueue',
										'simUploadLimit' : 1,
										'method' : 'GET',
										'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
										'fileDesc' : '图片文件',
										'auto' : false,
										'multi' : false,
										'buttonText' : 'Browser',// 浏览按钮图片
										'buttonImg' : '../../Img/Admin/upload/browser.jpg',
										'onSelect' : function(event, queueID,
												fileObj) {
											$("#uploadImage")
													.uploadifySettings(
															'scriptData',
															{
																'method' : 'uploadImage'
															});
										},
										onComplete : function(event, queueID,
												fileObj, response, data) {

											alert("文件:" + fileObj.name + "上传成功");
											$("#uploadsign").attr("style",
													"display: ");
											var str = response.substring(1,
													response.length - 1);
											var news_id = str.split(";")[0];
											var news_imageURL = str.split(";")[1];

											$("#news_id").val(news_id);
											$("#news_imageURL").val(
													news_imageURL);
										},
										onError : function(event, queueID,
												fileObj) {
											alert("文件:" + fileObj.name + "上传失败");
											$("#uploadsign")
													.attr("src",
															"../../Img/Admin/upload/fail.gif");
											$("#uploadsign").attr("style",
													"display: ");
										}
									});

					$("#insertNews").click(function() {
						$.ajax( {
							url : '../../NewsServlet',
							type : 'POST',
							data : {
								method : 'insertNews',
								news_id : $('#news_id').val(),
								news_title : $("#news_title").val(),
								news_date : $("#news_date").val(),
								news_content : $("#news_content").val(),
								news_imageURL : $("#news_imageURL").val()
							},
							dataType : 'json',
							error : function() {
								alert("error occured!!!");
							},
							success : function(data) {
								if ("yes" == data) {
									alert("提交成功！");
									window.location.href = "./news_admin.jsp";
								} else {
									alert("提交失败！请重新编辑");
								}
							}
						});

					});

					$("#updateImage")
							.uploadify(
									{
										'uploader' : '../../JS/lib/jquery.uploadify-v2.1.4/uploadify.swf',
										'script' : '../../NewsServlet',
										'cancelImg' : '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
										'folder' : 'UploadFile',
										'queueID' : 'fileQueue2',
										'simUploadLimit' : 1,
										'method' : 'GET',
										'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
										'fileDesc' : '图片文件',
										'auto' : false,
										'multi' : false,
										'buttonText' : 'Browser',// 浏览按钮图片
										'buttonImg' : '../../Img/Admin/upload/browser.jpg',
										'onSelect' : function(event, queueID,
												fileObj) {
											$("#updateImage")
													.uploadifySettings(
															'scriptData',
															{
																'method' : 'updateImage',
																'news_imageURL' : $(
																		'#news_imageURL')
																		.val()
															});
										},
										onComplete : function(event, queueID,
												fileObj, response, data) {

											alert("文件:" + fileObj.name + "上传成功");
											$("#uploadsign").attr("style",
													"display: ");
										},
										onError : function(event, queueID,
												fileObj) {
											alert("文件:" + fileObj.name + "上传失败");
											$("#uploadsign")
													.attr("src",
															"../../Img/Admin/upload/fail.gif");
											$("#uploadsign").attr("style",
													"display: ");
										}
									});

					$("#updateNews").click(function() {
						$.ajax( {
							url : '../../NewsServlet',
							type : 'POST',
							data : {
								method : 'updateNews',
								news_id : $('#news_id').val(),
								news_title : $("#news_title").val(),
								news_date : $("#news_date").val(),
								news_content : $("#news_content").val(),
								news_imageURL : $("#news_imageURL").val()
							},
							dataType : 'json',
							error : function() {
								alert("error occured!!!");
							},
							success : function(data) {
								if ("yes" == data) {
									alert("提交成功！");
									window.location.href = "./news_admin.jsp";
								} else {
									alert("提交失败！请重新编辑");
								}
							}
						});

					});

				});

function gotoUpdateNews(id) {
	window.location.href = "./updateNews.jsp?newsId=" + id;
}

function deleteNews(id) {
	if (confirm("确认删除？")) {
		$.ajax( {
			url : '../../NewsServlet',
			type : 'POST',
			async : true,
			data : {
				method : 'deleteNews',
				newId : id
			},
			dataType : 'json',

			success : function(data) {
				if ("yes" == data) {
					alert("删除成功！");
					window.location.href = "./news_admin.jsp";
				} else {
					alert("删除失败！");
				}
			},
			error : function() {
				alert("error occured!!!");
			}
		});
	}
}
