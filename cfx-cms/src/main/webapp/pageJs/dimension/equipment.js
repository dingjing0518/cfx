$(function () {
    createDataGrid('EquipmentListId', cfg);
});

var toolbar = [];
var columns = [

    {
        title: '设备类型',
        field: 'category',
        width: 20,
        sortable: true
    }, {
        title: '设备权属',
        field: 'ownership',
        width: 20,
        sortable: true
    },{
        title: '设备台数',
        field: 'number',
        width: 20,
        sortable: true
    }, {
        title: '设备年限',
        field: 'ages',
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
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editEquipmentStatus(\''
                    + value + '\',1);">启用</button></a>'
            } else {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_VISCAPACITY" class="btn btn-warning"   onclick="javascript:editEquipmentStatus(\''
                    + value + '\',2);">禁用</button></a>'
            }
            // str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="BTN_DELETEHELPS" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:deleteHelps(\''
            //     + value + '\',2);">删除</button></a>&nbsp;&nbsp;&nbsp;'
            str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_UPDATECAPACITY" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editEquipment(\''
                + value
                + '\');">编辑</button></a>'

            return str;
        }
    }];

var cfg = {
    url: './equipment_list.do',
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
function editEquipmentStatus(pk, isv) {
    var parm = {
        pk: pk,
        open: isv
    };
    $.ajax({
        type: 'POST',
        url: './updateEquipmentStatus.do',
        data: parm,
        dataType: 'json',
        success: function (data) {
            $('#EquipmentListId').bootstrapTable('refresh');

        }
    });
}
/**
 * 提交 国有商业银行授信数页面 新增数据 成功后跳转到 金融中心 =>维度管理 =>企业经营管理 =>设备管理 页面
 * @param flag
 */
function submit(flag) {

    if (valid("#dataForm")) {

        var minNumber = $("#minNumber").val();
        var maxNumber = $("#maxNumber").val();
        if (!/^\d+$/.test(minNumber) || !/^\d+$/.test(maxNumber)) {
            new $.flavr({
                modal: false,
                content: "输入的台数需大于0并且为整数！"
            });
            return;
        }
        var minAges= $("#minAges").val();
        var maxAges = $("#maxAges").val();
        if (!/^\d+$/.test(minAges) || !/^\d+$/.test(maxAges)) {
            new $.flavr({
                modal: false,
                content: "输入的年限需大于0并且为整数！"
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
            url: "./updateEquipment.do",
            data: {
                pk: $("#pk").val(),
                minNumber: $("#minNumber").val(),
                category: $("#category").val(),
                ownership: $("#ownership").val(),
                minAges: $("#minAges").val(),
                maxAges: $("#maxAges").val(),
                open: 1,
                maxNumber: $("#maxNumber").val(),
                score: $("#score").val()
            },
            dataType: 'json',
            async: true,
            success: function (data) {
                window.location = getRootPath() + "/equipment.do";

            }
        });
    }
}
/**
 * 新增时 跳转到 新增设备管理 新增页面
 * @param pk
 */
function editEquipment(pk) {
    window.location = getRootPath() + "/addEquipment.do?pk=" + pk;
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
	    url: './equipment_list.do' + urlParams(1),
	    columns: columns,
	    uniqueId: 'pk',
	    sortName: 'sort',
	    sortOrder: 'asc',
	    toolbar: toolbar,
	    onLoadSuccess: btnList
    };
    createDataGrid('EquipmentListId', cfg);
}



