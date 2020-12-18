$(function() {
	createDataGrid('storeAlbumId', cfg);
	$("#updatePics").attr('disabled',true);
	GetRequest('storePk');
});

/**
 * 根据参数名称获取传入参数
 * @param key
 * @returns {*}
 * @constructor
 */
function GetRequest(key) {   
	   var url = location.search; //获取url中"?"符后的字串   
	   var theRequest = new Object();   
	   if (url.indexOf("?") != -1) {   
	      var str = url.substr(1);   
	      strs = str.split("&");   
	      for(var i = 0; i < strs.length; i ++) {   
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
	      }   
	   } 
	   return theRequest[key];   
	}

var toolbar = [];
var columns = [
		
		{
			field : 'imgOne',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				var img = row.imgOne;
				var str="";
				if(img != null && img != ""){
				str+="<div class='images'><img class='bigpicture' src='"+img[2]+"'><a class='images' rel='group"+index+"' title='公司相册' href='javascript:;' onclick=\"deletePic(\'"+img[0]+"\',\'"+img[1]+"\')\"><span>×</span></a></div>";
				str+="<p class='time'>上传时间："+img[3]+"</p>";
				}
				/*else{
					str+="<img class='bigpicture' src='./style/images/bgbg.png'>";
				}*/
				return str;
			}
		},
		{
			field : 'imgTwo',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				var img = row.imgTwo;
				var str="";
				if(img != null && img != ""){
				str+="<div class='images'><img class='bigpicture' src='"+img[2]+"'><a class='images' rel='group"+index+"' title='公司相册' href='javascript:;' onclick=\"deletePic(\'"+img[0]+"\',\'"+img[1]+"\')\"><span>×</span></a></div>";
				str+="<p class='time'>上传时间："+img[3]+"</p>";
				}
				/*else{
					str+="<img class='bigpicture' src='./style/images/bgbg.png'>";
				}*/
				return str;
			}
		},
		{
			field : 'imgThree',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				var img = row.imgThree;
				var str="";
				if(img != null && img != ""){
				str+="<div class='images'><img class='bigpicture' src='"+img[2]+"'><a class='images' rel='group"+index+"' title='公司相册' href='javascript:;' onclick=\"deletePic(\'"+img[0]+"\',\'"+img[1]+"\')\"><span>×</span></a></div>";
				str+="<p class='time'>上传时间："+img[3]+"</p>";
				}
				/*else{
					str+="<img class='bigpicture' src='./style/images/bgbg.png'>";
				}*/
				return str;
			}
		},
		{
			field : 'imgFour',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				var img = row.imgFour;
				var str="";
				if(img != null && img != ""){
					str+="<div class='images'><img class='bigpicture' src='"+img[2]+"'><a class='images' rel='group"+index+"' title='公司相册' href='javascript:;' onclick=\"deletePic(\'"+img[0]+"\',\'"+img[1]+"\')\"><span>×</span></a></div>";
					str+="<p class='time'>上传时间："+img[3]+"</p>";	
				}
				/*else{
					str+="<img class='bigpicture' src='./style/images/bgbg.png'>";
				}*/
				
				return str;
			}
		}
		];
var cfg = {
	url : './enditStoreAblum.do?storePk='+GetRequest('storePk'),
	columns : columns,
	uniqueId : 'pk',
	pageSize:12,
	sortName : 'insertTime',
	sortOrder : 'desc'
};

/**
 * 更新公司相册
 */
function updatePic(){

	var url = $("#fileUpdateLoad").val();
	var storePk = $("#storePk").val();
	$.ajax({
		type : 'POST',
		url : "./updateStoreAblum.do",
		data : {
			storePk:storePk,
			url:url
		},
		dataType : 'json',
		async : true,
		success : function(data) {
			if(data.success){
				setTimeout(function(){
					new $.flavr({
						modal : false,
						content : data.msg
					});
				},1000);
				var cfg = {
						url : './enditStoreAblum.do?storePk='+storePk,
						columns : columns,
						uniqueId : 'pk',
						pageSize:12,
						sortName : 'insertTime',
						sortOrder : 'desc'
					};
				createDataGrid('storeAlbumId', cfg);	
			}
		}
	});
	
}

/**
 * 删除公司相册
 * @param pk
 * @param storePk
 */
function deletePic(pk,storePk){
	
	var confirm = new $.flavr({
		content : '确定删除所选照片吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : "./updateStoreAblum.do",
				data : {
					pk:pk
				},
				dataType : 'json',
				async : true,
				success : function(data) {
					if(data.success){
						setTimeout(function(){
							new $.flavr({
								modal : false,
								content : data.msg
							});
						},1000);
						var cfg = {
								url : './enditStoreAblum.do?storePk='+storePk,
								columns : columns,
								uniqueId : 'pk',
								pageSize:12,
								sortName : 'insertTime',
								sortOrder : 'desc'
							};
						createDataGrid('storeAlbumId', cfg);	
					}
				}
			});
			
		}
	});
}

