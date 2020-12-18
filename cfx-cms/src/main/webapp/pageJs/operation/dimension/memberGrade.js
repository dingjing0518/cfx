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
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1,\'' + row.gradeNumber + '\'); " style="display:none;" showname="OPER_MSS_GRADE_BTN_ENABLE" class="btn btn-primary active">启用</button>';
							}else{
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2,\'' + row.gradeNumber + '\'); " style="display:none;" showname="OPER_MSS_GRADE_BTN_DISABLE" class="btn btn-primary active">禁用</button>';
							}
							str += '<button type="button" onclick="javascript:edit(\'' + value + '\',\'' + row.gradeName + '\',\'' + row.gradeNumber + '\',\'' 
							+ row.numberStart + '\',\'' + row.numberEnd + '\',\'' + row.fbGradeRatio + '\',\'' + row.emGradeRatio + '\'); " style="display:none;" showname="OPER_MSS_GRADE_BTN_EDIT" class="btn btn-primary active">编辑</button>';
						return str;
					}
				}, 
				{
					title : '等级名称',
					field : 'gradeName',
					width : 20,
					sortable : true
				}, 
				{
					title : '等级',
					field : 'gradeNumber',
					width : 20,
					sortable : true
				}, 
				{
					title : '对应分值范围',
					field : 'rangeNumber',
					width : 20,
					sortable : true
				}, 
				{
					title : '等级系数（纤贝）',
					field : 'fbGradeRatio',
					width : 20,
					sortable : true
				}, 
				{
					title : '等级系数（经验）',
					field : 'emGradeRatio',
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
	url : './searchMemberGradeList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function isVisable(pk,isVisable,gradeNumber) {
			$.ajax({
				type : 'POST',
				url : './updateMemberGrade.do',
				data : {
					 pk : pk,
					 isVisable : isVisable,
					 gradeNumber:gradeNumber
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

function del(pk,isDelete){
	
	var confirm = new $.flavr({
		content : '确定要删除?',
		dialog : 'confirm',
		onConfirm : function() {
			$.ajax({
				type : 'POST',
				url : './updateMemberGrade.do',
				data : {
					 pk : pk,
					 isDelete : isDelete
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
	});
}

function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchMemberGradeList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('dimensionalityManageId', cfg);
}
function insertDimenReward(){
	 $('#editDimensionalityREId').modal();
	 $("#pk").val('');
	 $("#gradeName").val('');
	 $("#gradeNumber").val('');
	 $("#numberStart").val('');
	 $("#numberEnd").val('');
	 $("#fbGradeRatio").val('');
	 $("#emGradeRatio").val('');
}

function edit(pk,gradeName,gradeNumber,numberStart,numberEnd,fbGradeRatio,emGradeRatio){
	$('#editDimensionalityREId').modal();
	$("#pk").val(pk);
	$("#gradeName").val(gradeName);
	$("#gradeNumber").val(gradeNumber);
	$("#numberStart").val(numberStart);
	$("#numberEnd").val(numberEnd);
	$("#fbGradeRatio").val(fbGradeRatio);
	$("#emGradeRatio").val(emGradeRatio);
}

function submit(){
	var gradeName = $("#gradeName").val();
	var gradeNumber = $("#gradeNumber").val();
	var numberStart = $("#numberStart").val();
	var numberEnd = $("#numberEnd").val();
	var fbGradeRatio = $("#fbGradeRatio").val();
	var emGradeRatio = $("#emGradeRatio").val();
	if(valid("#dataForm")){
		 if(!isDNumber(gradeNumber)||gradeNumber<1||gradeNumber>=1000){
			 new $.flavr({
					modal : false,
					content : "等级数不是非正整数，范围【1,1000）！"
				});
				return;
		 }
		 
		 
		 if(!isDNumber(numberStart)|| numberStart>1000000000){
			 new $.flavr({
					modal : false,
					content : "对应分值起始值非正整数,范围（0,1000000000】！"
				});
				return;
		 }
		 
		 
		 if(!isDNumber(numberEnd)|| numberEnd>1000000000){
			 new $.flavr({
					modal : false,
					content : "对应分值结束值非正整数,范围（0,1000000000】！"
				});
				return;
		 }
		 if(numberStart>numberEnd){
			 new $.flavr({
					modal : false,
					content : "对应分值起始值不能大于结束值！"
				});
				return;
		 }
		$.ajax({
			type : "POST",
			url : "./updateMemberGrade.do",
			data : {
				pk : $("#pk").val(),
				gradeName :gradeName,
				gradeNumber:gradeNumber,
				numberStart:numberStart,
				numberEnd:numberEnd,
				fbGradeRatio:fbGradeRatio,
				emGradeRatio:emGradeRatio
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
