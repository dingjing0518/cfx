$(function () {
    createDataGrid('billCustomerId', cfg);
});


var toolbar = [];
var columns = [
    {
        title: '操作',
        field: 'pk',
        width: 100,
        formatter: function (value, row, index) {
            var str = "";
                  str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_BILLCUST_BTN_BILL" class="btn btn-warning" onclick="javascript:editState(\'' + row.pk + '\',\'' + row.companyName + '\',\'' + row.contacts + '\',\'' + row.contactsTel + '\',\'' + row.shotName + '\'); ">授信 </button> </a>';
           
                if(row.status == 3){
                    str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_BILLCUST_BTN_PASS" class="btn btn-warning" onclick="javascript:updateBillSatuts(\'' + row.companyPk + '\',2); ">审核通过</button> </a>';
                }
                if(row.status == 2){
                    str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_BILLCUST_BTN_REFUSE" class="btn btn-warning" onclick="javascript:updateBillSatuts(\'' + row.companyPk + '\',3); ">审核不通过</button> </a>';
                }
                str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_BILLCUST_BTN_UPDATE" class="btn btn-warning" onclick="javascript:updateCredit(\'' + row.companyPk + '\'); ">更新</button> </a>';

            return str;
        }
    },
    {
        title: '公司名称',
        field: 'companyName',
        width: 50,
        sortable: true
    },
    {
        title: '申请人',
        field: 'contacts',
        width: 20,
        sortable: true
    },
    {
        title: '手机号码',
        field: 'contactsTel',
        width: 20,
        sortable: true
    },
    {
        title: '票据产品',
        field: 'goodsName',
        width: 20,
        sortable: true
    },
    {
        title: '审核状态',
        field: 'status',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            if (value == 1) {
                return "未审核";
            } else if (value == 2) {
                return "审核通过";
            } else {
                return "审核失败";
            }
        }
    }
];
var cfg = {
    url: './billCustomerList.do',
    columns: columns,
    uniqueId: 'pk',
    sortName: 'insertTime',
    sortOrder: 'desc',
    toolbar: toolbar,
    onLoadSuccess: btnList
};

var columnTwo = [
    {
        title: '操作',
        field: 'pk',
        width: 100,
        formatter: function (value, row, index) {
        	
            var str = "";
                if(row.isVisable == 2){
                	  str += '<a class="save" href="javascript:;" style="text-decoration:none;">  '+
                      ' <button id="editable-sample_new"  showname="EM_BILLCUST_BILL_BTN_ENABLE" style="display:none;" class="btn btn btn-primary" onclick="updateIsVisable(\'' + value + '\',1); ">启用 </button></a>';
                }else{
                	  str += '<a class="save" href="javascript:;" style="text-decoration:none;">  '+
                      ' <button id="editable-sample_new"  showname="EM_BILLCUST_BILL_BTN_DISABLE" style="display:none;" class="btn btn btn-primary" onclick="updateIsVisable(\'' + value + '\',2); ">禁用</button></a>';
                }
                
                if (row.goodsShotName != 'tx') {
                    if (row.bindStatus == 2) {
                        str += '<a class="save" href="javascript:;" style="text-decoration:none;">  ' +
                            ' <button id="editable-sample_new"  showname="EM_BILLCUST_BTN_ONSEARCHQUATO" style="display:none;" class="btn btn btn-primary" onclick="searchBankAmount(\'' + row.pk + '\',\'' + row.bankNo + '\',\'' + row.companyPk + '\',\'' + row.account + '\',2); ">解约</button></a>';
                    } else {
                        str += '<a class="save" href="javascript:;" style="text-decoration:none;">  ' +
                            ' <button id="editable-sample_new"  showname="EM_BILLCUST_BTN_SEARCHQUATO" style="display:none;" class="btn btn btn-primary" onclick="searchBankAmount(\'' + row.pk + '\',\'' + row.bankNo + '\',\'' + row.companyPk + '\',\'' + row.account + '\',1); ">签约</button></a>';
                    }
                }
                    str += '<a class="save" href="javascript:;" style="text-decoration:none;">' +
                        '<button id="editable-sample_new"  style="display:none;" showname="EM_BILLCUST_BTN_MODIFYCUSTNO" class="btn btn-warning" onclick="javascript:updateBankAccount(\'' + row.pk + '\',\'' +
                            row.goodsName + '\',\'' + row.goodsShotName + '\',\'' + row.account + '\',\'' + row.bankName + '\',\'' + row.bankNo + '\',\'' + row.platformAmount + '\',\'' + row.useAmount + '\'); ">修改</button> </a>';

            return str;
        }
    },
    {
        title: '票据产品名称',
        field: 'goodsName',
        width: 50,
        sortable: true
    },
    {
        title: '票据英文简称',
        field: 'goodsShotName',
        width: 20,
        sortable: true
    },
    {
        title: '电票账户',
        field: 'account',
        width: 20,
        sortable: true
    },
    {
        title: '行名',
        field: 'bankName',
        width: 20,
        sortable: true
    },
    {
        title: '行号',
        field: 'bankNo',
        width: 20,
        sortable: true
    },{
        title: '银行绑定状态',
        field: 'bindStatus',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
        	if(row.goodsShotName == "pft"){
        		 if (row.bindStatus == 2) {
                     return "已签约";
                 }else if(row.bindStatus == 1){
                	 return "签约中";
                 }else{
                	 return "未签约";
                 }
    		}
        }
    },
    {
        title: '启用/禁用',
        field: 'isVisable',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            if (row.isVisable == 1) {
                return "启用";
            } else if (row.isVisable == 2) {
                return "禁用";
            }
        }
    }
];

/**
 * 授信额度管理状态table页面切换
 * @param s
 */
function findPurchaser(s) {
    if (s == 0) {
        $("#status").val("");
    } else {
        $("#status").val(s);
    }
    var cfg = {
        url: './billCustomerList.do' + urlParams(1),
        columns: columns,
        uniqueId: 'pk',
        sortName: 'insertTime',
        sortOrder: 'desc',
        toolbar: toolbar,
        onLoadSuccess: btnList
    };
    createDataGrid('billCustomerId', cfg);
}

/**
 * 授信额度管理 搜索和清除
 * @param type
 */
function searchTabs(type) {
    //清除所有内容
    if (type == 2) {
        cleanpar();
    }
    var cfg = {
        url: './billCustomerList.do' + urlParams(1),
        columns: columns,
        uniqueId: 'pk',
        sortName: 'insertTime',
        sortOrder: 'desc',
        toolbar: toolbar,
        onLoadSuccess: btnList
    };
    createDataGrid('billCustomerId', cfg);
}

/**
 * 授信操作-展示授信列表
 * @param pk
 * @param companyPk
 * @param goodsType
 */
function editState(pk,companyName,contacts,contactsTel,shotName) {

	    $("#dataForm").hide();
	    $("#billGoodsName").val("");
	    $("#shotNameGoods").val("");
	    $("#editcompanyName").val("");
	    $("#editcontacts").val("");
	    $("#editcontactsTel").val("");
        $("#companyName").val(companyName);
        $("#contacts").val(contacts);
        $("#contactsTel").val(contactsTel);
        $("#shotName").val(shotName);
	    $("#account").val("");
	    $("#bankName").val("");
	    $("#bankNo").val("");
    $('#creditCustomer').modal({
        show: true,
        backdrop: true
    });
    var cfgTwo = {
        url: './billCustGoodsList.do?customerPk=' + pk,
        columns: columnTwo,
        uniqueId: 'pk',
        sortName: 'bankNo',
        sortOrder: 'desc',
        toolbar: toolbar,
        onLoadSuccess: function (data) {
           btnList();

        }
    };
    createDataGrid('billGoodsId', cfgTwo);
}

/**
 * 修改银行客户号
 * @param creditPk
 */
function updateBankAccount(pk,goodsName,goodsShotName,account,bankName,bankNo,platformAmount,useAmount){

    $("#billCustGoodsPk").val(pk);
    $("#billGoodsName").val(goodsName);
    $("#shotNameGoods").val(goodsShotName);
    $("#editcompanyName").val($("#companyName").val());
    $("#editcontacts").val($("#contacts").val());
    $("#editcontactsTel").val($("#contactsTel").val());

    if("tx" == goodsShotName){
        $("#useAmount").val(useAmount);
        $("#platformAmount").val(platformAmount);
        $("#useAmountDiv").show();
        $("#platformAmountDiv").show();
        $("#account").val("");
        $("#bankName").val("");
        $("#bankNo").val("");
        $("#accountDiv").hide();
        $("#bankNameDiv").hide();
        $("#bankNoDiv").hide();
    }else{
        $("#account").val(account);
        $("#bankName").val(bankName);
        $("#bankNo").val(bankNo);
        
        $("#useAmount").val("");
        $("#platformAmount").val("");
        
        $("#useAmountDiv").hide();
        $("#platformAmountDiv").hide();
        $("#accountDiv").show();
        $("#bankNameDiv").show();
        $("#bankNoDiv").show();
    }
    $("#dataForm").show();
}

/**
 * 保存授信修改
 */
function submit() {
    if (valid("#dataForm")) {
    	 var bankName = $("#bankName").val();
    	 var bankNo = $("#bankNo").val();
        var account = $("#account").val();
       if( $("#shotNameGoods").val() =="pft"){
    		 if(bankName==""){
        		 new $.flavr({
                     modal: false,
                     content:"行名不能为空！"
                 });
                 return;
        	 }
        	 if(bankNo==""){
        		 new $.flavr({
                     modal: false,
                     content:"行号不能为空！"
                 });
                 return;
        	 }
            if(account==""){
                new $.flavr({
                    modal: false,
                    content:"电票账号不能为空！"
                });
                return;
            }
    	   
    	   
       }
    
        var params = {};
        
        params['pk'] = $("#billCustGoodsPk").val();
        params['bankName'] = bankName;
        params['bankNo'] = bankNo;
        params['account'] = account;
        if( $("#shotNameGoods").val() =="tx"){
        if(isNaN($("#platformAmount").val())) {
            new $.flavr({
                modal: false,
                content: "平台额度须为数字"
            });
            return;
        }
        if(!checkIsEmpty($("#platformAmount").val())) {
            new $.flavr({
                modal: false,
                content: "平台额度不能为空"
            });
            return;
        }
        }
        params['platformAmount'] =  $("#platformAmount").val();
        $.ajax({
            type: 'POST',
            url: './updateCustGoodsStatus.do',
            data: params,
            dataType: 'json',
            success: function (data) {
                new $.flavr({
                    modal: false,
                    content: data.msg
                });
                if (data.success == true) {
                    //$("#quxiao").click();
                    $('#billGoodsId').bootstrapTable('refresh');
                }
            }
        });
    }
}
/**
 * 查询银行额度
 * @param companyPk
 */
function searchBankAmount(pk,bankNo,companyPk,account,bindStatus){

		$.ajax({
			type: 'POST',
			url: './searchCustGoodsBind.do',
			data: {
                pk:pk,
                bankNo:bankNo,
                account:account,
                companyPk:companyPk,
                bindStatus:bindStatus
            },
			dataType: 'json',
			success: function (data) {
				new $.flavr({
					modal: false,
					content: data.msg
				});
				if (data.success == true) {
					$("#quxiao").click();
					$('#billCustomerId').bootstrapTable('refresh');
				}
			}
		});
}



Date.prototype.format = function (format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
}

/**
 * 审核通过/不通过
 * @param companyPk
 * @param creditStatus
 */
function updateBillSatuts(companyPk ,status) {
	var params = {
			companyPk:companyPk,
			status:status
		};
		$.ajax({
			type: 'POST',
			url: './updateBillAndGoods.do',
			data:params,
			dataType: 'json',
			success: function (data) {
				new $.flavr({
					modal: false,
					content: data.msg
				});
				if (data.success == true) {
					$('#billCustomerId').bootstrapTable('refresh');
				}
			}
		});
}

/**
 * 授信额度更新功能
 * @param companyPk
 */
function updateCredit(companyPk) {
	
	var params = {
			companyPk:companyPk,
		};
		$.ajax({
			type: 'POST',
			url: './updateBillCustomerCredit.do',
			data:params,
			dataType: 'json',
			success: function (data) {
				new $.flavr({
					modal: false,
					content: data.msg
				});
				if (data.success == true) {
					$('#billCustomerId').bootstrapTable('refresh');
				}
			}
		});
}


/**
 * 授信启用/禁用
 * @param pk
 * @param isVisable
 */
function updateIsVisable(pk , isVisable){
	var params = {
			pk:pk,
			isVisable:isVisable,
		};
		$.ajax({
			type: 'POST',
			url: './updateCustGoodsStatus.do',
			data:params,
			dataType: 'json',
			success: function (data) {
				new $.flavr({
					modal: false,
					content: data.msg
				});
				if (data.success == true) {
					$('#billGoodsId').bootstrapTable('refresh');
				}
			}
		});
}
