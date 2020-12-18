$(function() {
	createDataGrid('dimensionalityManageId', cfg);
});

var toolbar = [];
var columns = [
				{
					title : '操作',
					field : 'pk',
					width : 50,
					formatter : function(value, row, index) {
						var str = "";
							if(row.isVisable == 2){
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1); " style="display:none;" showname="OPER_MSS_DIMENSION_BTN_ENABLE" class="btn btn-primary active">启用</button>';
							}else{
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2); " style="display:none;" showname="OPER_MSS_DIMENSION_BTN_DISABLE" class="btn btn-primary active">禁用</button>';
								str += '<button type="button" onclick="javascript:setType(\'' + value + '\',\'' + row.type + '\',\'' + row.name + '\'); " style="display:none;" showname="OPER_MSS_DIMENSION_BTN_TYPE" class="btn btn-primary active">设置类型</button>';
							}
							str += '<button type="button" onclick="javascript:edit(\'' + value + '\'); " style="display:none;" showname="OPER_MSS_DIMENSION_BTN_EDIT" class="btn btn-primary active">编辑</button>';
						return str;
					}
				}, 
				{
					title : '维度',
					field : 'name',
					width : 20,
					sortable : true
				},
				{
					title : '启用/禁用',
					field : 'isVisable',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "启用";
						} else  if(value==2){
							return "禁用";
						}
		
					}
		
				},{
					title : '更新时间',
					field : 'updateTime',
					width : 20,
					sortable : true
				}];
var cfg = {
	url : './searchDimensionalityList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function isVisable(pk,isVisable) {
			$.ajax({
				type : 'POST',
				url : './updateDimensionality.do',
				data : {
					 pk : pk,
					 isVisable : isVisable
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#dimensionalityManageId').bootstrapTable('refresh');
					}

				}
			});
}
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchDimensionalityList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('dimensionalityManageId', cfg);
}
function insertDimensionality(){
	 $('#editDimensionalityId').modal();
	 $("#name").val('');
	 $("#pk").val('');
	 var html = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< member_dimansionality.length; i++){
		 html +="<option value="+member_dimansionality[i].key+">"+member_dimansionality[i].value+"</option>";
		}
	$("#name").empty().append(html); 
}
function edit(pk){
	$('#editDimensionalityId').modal();
	$("#pk").val(pk);
	
	$.ajax({
		type : "POST",
		url : "./geteByDimensionalityPk.do",
		data : {
			pk : pk
		},
		dataType : "json",
		success : function(data) {
			var html = "<option value=''>--请选择--</option>";
			if(data != ""){
				for(var i = 0; i< member_dimansionality.length; i++){
					if(member_dimansionality[i].key == data.type){
						html +="<option value="+data.type+" selected>"+data.name+"</option>";
					}else{
						html +="<option value="+member_dimansionality[i].key+">"+member_dimansionality[i].value+"</option>";
					}
				}
			}
			$("#name").empty().append(html);
		}
	});
}

function submit(){
	var name="";
	 var type=$("#name").val();
	 if(type.length>0){
		 name = $("#name option:selected").text();
	 }
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : "./updateDimensionality.do",
			data : {
				pk : $("#pk").val(),
				name :name,
				type:type
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#dimensionalityManageId').bootstrapTable('refresh');
				}
			}
		});
	}
}

function setType(pk,type,name){
		window.location.href = getRootPath()+"/dimensionalityRelevancy.do?pk="+pk+"&type="+type+"&name="+name;
	}

	
