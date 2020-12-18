$(function () {
    createDataGrid('buildPropertyListId', cfg);
});

var toolbar = [];
var columns = [

    {
        title: '房地产抵押金额',
        field: 'mortgageName',
        width: 20,
        sortable: true
    }, {
        title: '房地产抵押金额/授信总金额得分',
        field: 'totalMortgageName',
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
    url: './buildProperty_data.do',
    columns: columns,
    uniqueId: 'pk',
    sortName: 'sort',
    sortOrder: 'asc',
    toolbar: toolbar,
    onLoadSuccess: btnList
};

/**
 * 页面搜索和清除操作
 * @param type
 */
function searchTabs(type) {
    if (type == 2) {
        cleanpar();
    }
    var cfg = {
        url : './buildProperty_data.do' + urlParams(1),
        columns : columns,
        uniqueId : 'pk',
        sortName : 'sort',
        sortOrder : 'desc',
        toolbar : toolbar,
        onLoadSuccess : btnList
    };
    createDataGrid('buildPropertyListId', cfg);
}

/**
 * 启用、禁用
 * @param pk
 * @param isv
 */
function editStatus(pk, isv) {
    var parm = {
        pk: pk,
        open: isv
    };
    $.ajax({
        type: 'POST',
        url: './updateStatus.do',
        data: parm,
        dataType: 'json',
        success: function (data) {
            $('#buildPropertyListId').bootstrapTable('refresh');

        }
    });
}

/**
 * 保存新增，成功后跳转到 =>金融中心 => 维度管理 =>授信管理 => 房地产抵押金额
 * @param flag
 */
function submit(flag) {
    if (valid("#dataForm")) {

        var minMortgage = $("#minMortgage").val();
        var maxMortgage = $("#maxMortgage").val();
        if (!/^\d+$/.test(minMortgage) || !/^\d+$/.test(maxMortgage)) {
            new $.flavr({
                modal: false,
                content: "输入的房地产抵押金额需大于0并且为整数！"
            });
            return;
        }
        var minTotalMortgage = $("#minTotalMortgage").val();
        var maxTotalMortgage = $("#maxTotalMortgage").val();
        if (!/^\d+$/.test(minTotalMortgage) || !/^\d+$/.test(maxTotalMortgage)) {
            new $.flavr({
                modal: false,
                content: "输入的输入的房地产抵押金额/授信总金额需大于0并且为整数！"
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
            url: "./updateBuildProperty.do",
            data: {
                pk: $("#pk").val(),
                minMortgage: $("#minMortgage").val(),
                maxMortgage: $("#maxMortgage").val(),
                open: 1,
                minTotalMortgage: $("#minTotalMortgage").val(),
                maxTotalMortgage: $("#maxTotalMortgage").val(),
                score: $("#score").val()
            },
            dataType: 'json',
            async: true,
            success: function (data) {
                window.location = getRootPath() + "/buildProperty.do";

            }
        });
    }
}

/**
 * 跳转房产抵押金额新增页面
 * @param pk
 */
function edit(pk) {
    window.location = getRootPath() + "/addBuildProperty.do?pk=" + pk;
}

