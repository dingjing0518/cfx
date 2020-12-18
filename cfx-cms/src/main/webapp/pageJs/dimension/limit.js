$(function () {
    createDataGrid('LimitListId', cfg);
});

var toolbar = [];
var columns = [

    {
        title: '维度',
        field: 'category',
        width: 20,
        formatter: function (value, row, index) {
            if (value == 'operation') {
                return "企业经营管理";
            } else if (value =='offline') {
                return "线下交易数据";
            }else if (value =='online') {
                return "线上交易数据";
            }else if (value =='credit') {
                return "授信管理";
            }else if (value =='guarantee') {
                return "担保管理";
            }

        }
    }, {
        title: '得分',
        field: 'limit',
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
            var estimatedTime = row.estimatedTime;
            if (row.open == 2) {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editLimitStatus(\''
                    + value + '\',1);">启用</button></a>'
            } else {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editLimitStatus(\''
                    + value + '\',2);">禁用</button></a>'
            }
            // str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="BTN_DELETEHELPS" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:deleteHelps(\''
            //     + value + '\',2);">删除</button></a>&nbsp;&nbsp;&nbsp;'
            str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_UPDATECAPACITY" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editLimit(\''
                + value
                + '\');">编辑</button></a>'

            return str;
        }
    }];

var cfg = {
    url: './limit_list.do',
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
        url : './limit_list.do' + urlParams(1),
        columns : columns,
        uniqueId : 'pk',
        sortName : 'sort',
        sortOrder : 'desc',
        toolbar : toolbar,
        onLoadSuccess : btnList
    };
    createDataGrid('LimitListId', cfg);
}


function editLimitStatus(pk, isv) {
    var parm = {
        pk: pk,
        open: isv
    };
    $.ajax({
        type: 'POST',
        url: './updateLimitStatus.do',
        data: parm,
        dataType: 'json',
        success: function (data) {
            $('#LimitListId').bootstrapTable('refresh');

        }
    });
}

function submit(flag) {

    if (valid("#dataForm")) {

        var limit = $("#limit").val();
        if (!/^\d+$/.test(limit) ) {
            new $.flavr({
                modal: false,
                content: "输入的最高分需大于0并且为整数！"
            });
            return;
        }
        $.ajax({
            type: 'POST',
            url: "./updateLimit.do",
            data: {
                pk: $("#pk").val(),
                category: $("#category").val(),
                limit: $("#limit").val(),
                open: 1,
            },
            dataType: 'json',
            async: true,
            success: function (data) {
                window.location = getRootPath() + "/limit.do";

            }
        });
    }
}

function editLimit(pk) {
    window.location = getRootPath() + "/addLimit.do?pk=" + pk;
}

