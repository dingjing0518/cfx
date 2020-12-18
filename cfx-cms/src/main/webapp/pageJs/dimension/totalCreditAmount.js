$(function () {
    createDataGrid('totalCreditAmountId', cfg);
});

var toolbar = [];
var columns = [

    {
        title: '授信金额',
        field: 'name',
        width: 20,
        sortable: true
    },{
        title: '得分',
        field: 'score',
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
            var estimatedTime = row.estimatedTime;
            if (row.open == 2) {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editStatus(\''
                    + value + '\',1);">启用</button></a>'
            } else {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editStatus(\''
                    + value + '\',2);">禁用</button></a>'
            }
            // str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="BTN_DELETEHELPS" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:deleteHelps(\''
            //     + value + '\',2);">删除</button></a>&nbsp;&nbsp;&nbsp;'
            str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_UPDATECAPACITY" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal1" onclick="javascript:edit(\''
                + value
                + '\');">编辑</button></a>'

            return str;
        }
    }];

var cfg = {
    url: './totalCreditAmount_data.do',
    columns: columns,
    uniqueId: 'pk',
    sortName: 'sort',
    sortOrder: 'asc',
    toolbar: toolbar,
    onLoadSuccess: btnList
};

function searchTabs(type) {
    if (type == 2) {
        cleanpar();
    }
    var cfg = {
        url : './totalCreditAmount_data.do' + urlParams(1),
        columns : columns,
        uniqueId : 'pk',
        sortName : 'sort',
        sortOrder : 'desc',
        toolbar : toolbar,
        onLoadSuccess : btnList
    };
    createDataGrid('totalCreditAmountId', cfg);
}

function editStatus(pk, isv) {
    var parm = {
        pk: pk,
        open: isv
    };
    $.ajax({
        type: 'POST',
        url: './updateTotalCreditAmountStatus.do',
        data: parm,
        dataType: 'json',
        success: function (data) {
            $('#totalCreditAmountId').bootstrapTable('refresh');

        }
    });
}


function submit(flag) {
    if (valid("#dataForm")) {

        var minAmount = $("#minAmount").val();
        var maxAmount = $("#maxAmount").val();
        if (!/^\d+$/.test(maxAmount) || !/^\d+$/.test(minAmount)) {
            new $.flavr({
                modal: false,
                content: "输入的授信数大于0并且为整数！"
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
            url: "./updateTotalCreditAmount.do",
            data: {
                pk: $("#pk").val(),
                minAmount: $("#minAmount").val(),
                maxAmount: $("#maxAmount").val(),
                year:$("#year").val(),
                open: 1,
                score: $("#score").val()
            },
            dataType: 'json',
            async: true,
            success: function (data) {
                window.location = getRootPath() + "/totalCreditAmount.do";

            }
        });
    }
}

function edit(pk) {
    window.location = getRootPath() + "/addTotalCreditAmount.do?pk=" + pk;
}

