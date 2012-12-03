// JavaScript Document

$(document).ready(function() {

	var albumArray = new Array();
	var uploadPhotoArray = new Array();

	$.ajax( {
		url : '../../AlbumServlet',
		type : 'POST',
		data : {method:'getAlbum'},
		async : false,
		dataType : 'json',
		error : function() {
			alert("error occured!!!");
		},
		success : function(data) {
	      	  for(var i=0;i<data.length;i++)
	      	  {
	      		albumArray[i]=data[i];
	      	  }
			
			var select = document.getElementById("albumlist");
			for ( var i = 0; i < albumArray.length; i++) {

				var op = new Option(albumArray[i].title, albumArray[i].id);

				select.options.add(op);
			}

		}
	});

	$("#uploadify").uploadify( {
		'uploader' : '../../JS/lib/jquery.uploadify-v2.1.4/uploadify.swf',
		'script' : '../../AlbumServlet?method=uploadify',
		'cancelImg' : '../../JS/lib/jquery.uploadify-v2.1.4/cancel.png',
		'folder' : '11',
		'queueID' : 'fileQueue',
		'method' : 'POST',
		'simUploadLimit' : 100,
		'queueSizeLimit' : 100,//可以一次选定几个文件
		'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.icon;*.bmp',
		'fileDesc' : '图片文件',
		'auto' : false,
		'multi' : true,
		'buttonText' : 'Browser',//浏览按钮图片
		'buttonImg' : '../../Img/Admin/upload/browser.jpg',
		onComplete : function(event, queueID, fileObj, response, data) {
			uploadPhotoArray.push(fileObj.name);
		},
		onAllComplete : function(filesUploaded, errors, allBytesLoaded, speed) {
			$.ajax( {
				url : '../../AlbumServlet',
				type : 'POST',
				data : {
					method:'insertPhoto',
					albumId : $("#albumlist option:selected").val(),
					photolist : uploadPhotoArray.join(';')
				},
				dataType : 'json',
				error : function() {
				alert("error occured!!!");  
				},
			success : function(data) {
				if ("yes" == data) {
					alert("所有文件上传成功！");
					window.location.href="./album_admin.jsp";
			} else {
				alert("提交失败！请重新编辑");
			}
		}
			});

			uploadPhotoArray = new Array();
		},
		onError : function(event, queueID, fileObj) {
			alert("文件:" + fileObj.name + "上传失败");
			$("#uploadsign").attr("src", "../../Img/Admin/upload/fail.gif");
			$("#uploadsign").attr("style", "display: ");

			uploadPhotoArray = new Array();
		}
	});
});
