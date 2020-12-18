var companyPk = $("#companyPk").val(); 
$(function() {
	var cfg = {
			url : './searchApproveBillHistoryList.do?companyPk='+ $("#companyPk").val(),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			onLoadSuccess : change
			
		};
	
	createDataGrid('approveId', cfg);
	
	$('#approveId').on('click-row.bs.table', function (e, row, element)   
	    {  	
			$('.success').removeClass('success');//去除之前选中的行的，选中样式
			$(element).addClass('success');//添加当前选中的 success样式用于区别
	        //历史记录资料详情
	        onclickInfo(row);
	    });  
})

var columns = [
            		{
            			title : '申请产品',
            			field : 'productName',
            			width : 20,
            			sortable : true,
            			
            		}, {
    					title : '审批状态',
    					field : 'status',
    					width : 20,
    					sortable : true,
    					formatter : function (value, row, index) {
    						if(value==1){
    							return "待审批";
    						}else if(value==2){
    							return "审批中";
    						}else if(value==3){
    							return "已审批";
    						}else if(value==4){
    							return "驳回";
    						}  
    					}
    				}, {
            			title : '申请时间',
            			field : 'insertTime',
            			width : 20,
            			sortable : true
            		}, {
            			title : '审批时间',
            			field : 'approveTime',
            			width : 20,
            			sortable : true
            		} ];

/**
 * 金融中心-申请记录 历史记录资料详情
 */
function onclickInfo(row) {
	
	$("#approveForm").find("input,select").each(function(){
		if(!$(this).is(":hidden")){
			$(this).val("");
		}
	});
	
	$("#approveForm").find("label").each(function(){
		 if($(this).hasClass('score')){
			$(this).text("");
		}
	});
	 
	$("#companyName").text(row.companyName);
	$("#contacts").text(row.contacts);
	$("#contactsTel").text(row.contactsTel);
	$("#productName").text(row.productName);
	$("#insertTime").text(row.insertTime);

	
	//遍历审批产品
	var test = "";
	if(row.goodsList!=undefined&&row.goodsList.length>0){
		var content = row.goodsList;
		for(var  i=0 ; content.length>i ; i++ ){
			var  suggestAmount = typeof(content[i].suggestAmount)=="undefined"?0:content[i].suggestAmount;
			var economicsGoodsName = typeof(content[i].goodsName)=="undefined"?"":content[i].goodsName;

			test  =  test +' <div class="col-ms-12" style="height: 33px; display: block;">'+
			'<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;">审批产品：'+
			'<label class="control-label" >'+economicsGoodsName+'</label></label>';
			if(content[i].goodsShotName == "tx"){
				test  =  test +	'<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;">产品金额：'+
				'<label class="control-label" >'+suggestAmount+ ' </label>W</label>';
			}
			test  =  test 	+'</div >';
		}
	}
	
	$("#editHtml").html(test);

}





/**
 * 获得改变选择框并获取数据
 */
function change(){
	$("#approveId tbody").find("tr").eq(0).addClass('success');
	 var index = $('#approveId').find('tr.success').data('index');//获得选中的行
	onclickInfo($('#approveId').bootstrapTable('getData')[index]);//返回选中行所有数据

}
