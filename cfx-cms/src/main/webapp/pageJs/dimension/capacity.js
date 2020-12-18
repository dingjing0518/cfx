$(function () {
    createDataGrid('CapacityListId', cfg);
});

var toolbar = [];
var columns = [

    {
        title: '产能范围',
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
            var estimatedTime = row.estimatedTime;
            if (row.open == 2) {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editCapacityStatus(\''
                    + value + '\',1);">启用</button></a>'
            } else {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editCapacityStatus(\''
                    + value + '\',2);">禁用</button></a>'
            }
            // str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="BTN_DELETEHELPS" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:deleteHelps(\''
            //     + value + '\',2);">删除</button></a>&nbsp;&nbsp;&nbsp;'
            str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_UPDATECAPACITY" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editCapacity(\''
                + value
                + '\');">编辑</button></a>'

            return str;
        }
    }];

var cfg = {
    url: './capacity_list.do',
    columns: columns,
    uniqueId: 'pk',
    sortName: 'sort',
    sortOrder: 'asc',
    toolbar: toolbar,
    onLoadSuccess: btnList
};

/**
 * 启用、禁用
 * @param pk
 * @param isv
 */
function editCapacityStatus(pk, isv) {
    var parm = {
        pk: pk,
        open: isv
    };
    $.ajax({
        type: 'POST',
        url: './updateCapacityStatus.do',
        data: parm,
        dataType: 'json',
        success: function (data) {
            $('#CapacityListId').bootstrapTable('refresh');

        }
    });
}

/**
 * 页面搜索和清除功能
 * @param type
 */
function searchTabs(type) {
    if (type == 2) {
        cleanpar();
    }
    var cfg = {
	    url: './capacity_list.do' + urlParams(1),
	    columns: columns,
	    uniqueId: 'pk',
	    sortName: 'sort',
	    sortOrder: 'asc',
	    toolbar: toolbar,
	    onLoadSuccess: btnList
};
    createDataGrid('CapacityListId', cfg);
}


/**
 * 提交 新增产能范围 页面，成功后跳转到  金融中心 =>维度管理 =>企业经营管理 =>产能范围 页面
 * @param flag
 */
function submit(flag) {

    if (valid("#dataForm")) {

        var minCapacity = $("#minCapacity").val();
        var maxCapacity = $("#maxCapacity").val();
        if (!/^\d+$/.test(minCapacity) || !/^\d+$/.test(maxCapacity)) {
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
            url: "./updateCapacity.do",
            data: {
                pk: $("#pk").val(),
                minCapacity: $("#minCapacity").val(),
                open: 1,
                maxCapacity: $("#maxCapacity").val(),
                score: $("#score").val()
            },
            dataType: 'json',
            async: true,
            success: function (data) {
                window.location = getRootPath() + "/capacity.do";

            }
        });
    }
}

/**
 * 新增时跳转到 新增产能范围页面
 * @param pk
 */
function editCapacity(pk) {
    window.location = getRootPath() + "/addCapacity.do?pk=" + pk;
}

