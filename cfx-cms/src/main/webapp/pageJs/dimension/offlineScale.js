$(function () {
    createDataGrid('OfflineScaleListId', cfg);
});

var toolbar = [];
var columns = [

    {
        title: '销售范围',
        field: 'name',
        width: 20,
        sortable: true
    }, {
        title: '得分',
        field: 'score',
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
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editOfflineScaleStatus(\''
                    + value + '\',1);">启用</button></a>'
            } else {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editOfflineScaleStatus(\''
                    + value + '\',2);">禁用</button></a>'
            }
            // str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="BTN_DELETEHELPS" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:deleteHelps(\''
            //     + value + '\',2);">删除</button></a>&nbsp;&nbsp;&nbsp;'
            str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_UPDATECAPACITY" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editOfflineScale(\''
                + value
                + '\');">编辑</button></a>'

            return str;
        }
    }];

var cfg = {
    url: './offlineScale_list.do',
    columns: columns,
    uniqueId: 'pk',
    sortName: 'sort',
    sortOrder: 'asc',
    toolbar: toolbar,
    onLoadSuccess: btnList
};

function editOfflineScaleStatus(pk, isv) {
    var parm = {
        pk: pk,
        open: isv
    };
    $.ajax({
        type: 'POST',
        url: './updateOfflineScaleStatus.do',
        data: parm,
        dataType: 'json',
        success: function (data) {
            $('#OfflineScaleListId').bootstrapTable('refresh');

        }
    });
}

function submit(flag) {

    if (valid("#dataForm")) {

        var minNumber = $("#minNumber").val();
        var maxNumber = $("#maxNumber").val();
        if (!/^\d+$/.test(minNumber) || !/^\d+$/.test(maxNumber)) {
            new $.flavr({
                modal: false,
                content: "输入的产能需大于0并且为整数！"
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
            url: "./updateOfflineScale.do",
            data: {
                pk: $("#pk").val(),
                minNumber: $("#minNumber").val(),
                open: 1,
                maxNumber: $("#maxNumber").val(),
                score: $("#score").val()
            },
            dataType: 'json',
            async: true,
            success: function (data) {
                window.location = getRootPath() + "/offlineScale.do";

            }
        });
    }
}

function editOfflineScale(pk) {
    window.location = getRootPath() + "/addOfflineScale.do?pk=" + pk;
}

