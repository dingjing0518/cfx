$(function () {
    createDataGrid('GuaranteeTargetListId', cfg);
});





var toolbar = [];
var columns = [
	{
	    title: '担保对象',
	    field: 'target',
	    width: 20,
	    sortable: true
	},
    {
        title: '担保总金额/纳税销售收入比',
        field: 'rateName',
        width: 20,
        sortable: true
    },  {
        title: '担保总金额',
        field: 'accountName',
        width: 20,
        sortable: true
    }, {
        title: '得分',
        field: 'score',
        width: 20,
        sortable: true
    }, {
        title: '关联年份',
        field: 'year',
        width: 20,
        sortable: true
    },
    {
        title: '新增时间',
        field: 'insertTime',
        width: 20,
        sortable: true
    },
    {
        title: '状态',
        field: 'open',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            if (value == 1) {
                return "已启用";
            } else if (value == 2) {
                return "已禁止";
            }

        }

    }, {
        title: '操作',
        field: 'pk',
        width: 120,
        formatter: function (value, row, index) {
            var str = "";
            if (row.open == 2) {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editGuaranteeTargetStatus(\''
                    + value + '\',1);">启用</button></a>'
            } else {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editGuaranteeTargetStatus(\''
                    + value + '\',2);">禁用</button></a>'
            }
            str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_UPDATECAPACITY" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editGuaranteeTarget(\''
                + value
                + '\');">编辑</button></a>'

            return str;
        }
    }];

var cfg = {
    url: './guaranteeTarget_list.do',
    columns: columns,
    uniqueId: 'pk',
    sortOrder: 'asc',
    toolbar: toolbar,
    onLoadSuccess: btnList
};


function searchTabs(type) {
    if (type == 2) {
        cleanpar();
    }
    var cfg = {
        url : './guaranteeTarget_list.do' + urlParams(1),
        columns : columns,
        uniqueId : 'pk',
        sortName : 'sort',
        sortOrder : 'desc',
        toolbar : toolbar,
        onLoadSuccess : btnList
    };
    createDataGrid('GuaranteeTargetListId', cfg);
}


function editGuaranteeTargetStatus(pk, isv) {
    var parm = {
        pk: pk,
        open: isv
    };
    $.ajax({
        type: 'POST',
        url: './updateGuaranteeTargetStatus.do',
        data: parm,
        dataType: 'json',
        success: function (data) {
            $('#GuaranteeTargetListId').bootstrapTable('refresh');

        }
    });
}

function submit(flag) {
    if (valid("#dataForm")) {

        var minAccount = $("#minAccount").val();
        var maxAccount = $("#maxAccount").val();
        if (!/^\d+$/.test(minAccount) || !/^\d+$/.test(maxAccount)) {
            new $.flavr({
                modal: false,
                content: "输入的数值需大于0并且为整数！"
            });
            return;
        }
        var minRate = $("#minRate").val();
        var maxRate= $("#maxRate").val();
        if (!/^\d+$/.test(minRate) || !/^\d+$/.test(maxRate)) {
            new $.flavr({
                modal: false,
                content: "输入的数值需大于0并且为整数！"
            });
            return;
        }
        var score = $("#score").val();
        if (!/^\d+$/.test(score) ) {
            new $.flavr({
                modal: false,
                content: "输入的得分需大于0并且为整数！"
            });
            return;
        }
        $.ajax({
            type: 'POST',
            url: "./updateGuaranteeTarget.do",
            data: {
                pk: $("#pk").val(),
                minAccount: $("#minAccount").val(),
                minRate: $("#minRate").val(),
                target: $('input:radio[name="target"]:checked').val(),
                year: $("#year").val(),
                open: 1,
                maxAccount: $("#maxAccount").val(),
                maxRate: $("#maxRate").val(),
                score: $("#score").val()
            },
            dataType: 'json',
            async: true,
            success: function (data) {
                window.location = getRootPath() + "/guaranteeTarget.do";

            }
        });
    }
}

function editGuaranteeTarget(pk) {
    window.location = getRootPath() + "/addGuaranteeTarget.do?pk=" + pk;
}

