$(function() {
	//search();
	btnList();
});

/**
 * 查询申请表页面列表
 */
function searchTabs(){
    window.location = getRootPath() + "/approvalManage.do?bankPk=" + $("#selectBank").val()+"&year="+$("#years").val();
}

/**
 * 导出申请表报表数据
 */
function exportData() {

    var turl = "?bankPk=" + $("#selectBank").val() + "&years=" + $("#years").val();
    $.ajax({
        type: 'POST',
        url: './exportApprovalManageList.do' + turl,
        data: {},
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                new $.flavr({
                    modal: false,
                    content: data.msg
                });
            }
        }
    });
}