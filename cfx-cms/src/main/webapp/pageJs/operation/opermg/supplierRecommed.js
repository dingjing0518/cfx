$(function () {

    var cfg = {
        url: './searchSupplierRecommedList.do' + urlParams(1),
        columns: columns,
        uniqueId: 'pk',
        sortName: 'sort',
        sortOrder: 'desc',
        toolbar: toolbar,
        onLoadSuccess: loadFancyBox
    };
    createDataGrid('supplierRecommedId', cfg);

});
var toolbar = [];
var columns = [
    {
        title: '操作',
        field: 'pk',
        width: 80,
        formatter: function (value, row, index) {
            var str = "";
            var startt = row.startTime;
            var endt = row.endTime;
            if (row.isOnline == 2) {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_AD_RECOMMEND_BTN_ONLINE" class="btn btn-warning"   onclick="javascript:updateSysSupRecommed(\''
                    + value + '\',1,1);">上线</button></a>';
            } else {
                str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_AD_RECOMMEND_BTN_OFFLINE" class="btn btn-warning"   onclick="javascript:updateSysSupRecommed(\''
                    + value + '\',2,1);">下线</button></a>';
            }
            str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_MG_AD_RECOMMEND_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:updateSysSupRecommed(\''
                + value + '\',2,2);">删除</button></a>';
            str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_AD_RECOMMEND_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editSupplierRE(\''
                + value
                + '\',\''
                + row.storePk
                + '\',\''
                + row.isRecommed
                + '\',\''
                + row.url
                + '\',\''
                + row.sort
                + '\',\''
                + row.position
                + '\',\''
                + row.linkUrl
                + '\',\''
                + row.brandPk
                + '\',\'' + row.block + '\',\'' + row.platform + '\',\'' + row.region + '\');">编辑</button></a>';

            return str;
        }
    }, {
        title: '放置终端',
        field: 'platform',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            var platforms = "";
            if(checkIsEmpty(value)){
                var  platform = value.split(",");
                for (var n = 0; n < platform_Type.length; n ++) {
                    for (var i = 0; i < platform.length; i++) {
                        if(platform_Type[n].key == platform[i]){
                            platforms += platform_Type[n].value+",";
                        }
                    }
                }
            }
            if (checkIsEmpty(platforms)) {
                platforms = platforms.substring(0,platforms.length - 1);
            }
            return platforms;
        }
    }, {
        title: '是否上线',
        field: 'isOnline',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            if (value == 1) {
                return "上线";
            } else if (value == 2) {
                return "下线";
            }
        }
    }, {
        title: '品牌名称',
        field: 'brandName',
        width: 20,
        sortable: true
    }, {
        title: '放置位置',
        field: 'position',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            var positions = "";
            if(checkIsEmpty(value)){
                var  position = value.split(",");
                for (var n = 0; n < position_Type.length; n ++) {
                    for (var i = 0; i < position.length; i++) {
                        if(position_Type[n].key == position[i]){
                            positions += position_Type[n].value+",";
                        }
                    }
                }
            }
            if (checkIsEmpty(positions)) {
                positions = positions.substring(0,positions.length - 1);
            }
            return positions;
        }
    }, {
        title: '所属板块',
        field: 'block',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
           var blocks = "";
            if(checkIsEmpty(value)){
               var  block = value.split(",");
                for (var n = 0; n < block_Type.length; n ++) {
                    for (var i = 0; i < block.length; i++) {
                        if(block_Type[n].key == block[i]){
                            blocks += block_Type[n].value+",";
                        }
                    }
                }
            }
            if (checkIsEmpty(blocks)) {
                blocks = blocks.substring(0,blocks.length - 1);
            }
            return blocks;
        }
    }, {
        title: '关联店铺',
        field: 'storeName',
        width: 20,
        sortable: true
    }, {
        title: '对应地址',
        field: 'linkUrl',
        width: 20,
        sortable: true
    },
    {
        title: '所属区域',
        field: 'region',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            if (value == "1") {
                return "太仓";
            } else if (value == "2") {
                return "常熟";
            } else if (value == "3") {
                return "张家港";
            } else if (value == "4") {
                return "萧山";
            } else if (value == "5") {
                return "绍兴";
            } else if (value == "6") {
                return "桐乡";
            } else if (value == "7") {
                return "吴江";
            } else if (value == "8") {
                return "其他";
            } else {
                return "";
            }
        }
    }, {
        title: '图片',
        field: 'url',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            var url = "";
            if (value != "") {
                url = "<a class='fancybox' href='" + value + "'><img style='width:auto;height:30px;' src='" + value + "'/></a>";
            }
            return url;
        }
    }, {
        title: '是否推荐',
        field: 'isRecommed',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            if (value == 1) {
                return "是";
            } else if (value == 2) {
                return "否";
            }
        }
    }, {
        title: '推荐排序',
        field: 'sort',
        width: 20,
        sortable: true
    }, {
        title: '添加时间',
        field: 'insertTime',
        width: 50,
        sortable: true
    }];


function updateSysSupRecommed(pk, status, flag) {
    var params = {};
    params['pk'] = pk;
    //上线下线
    if (flag == 1) {
        params['isOnline'] = status;
    } else {
        params['isDelete'] = status;
    }

    if (flag == 1) {

        $.ajax({
            type: "POST",
            url: "./updateSupplierRecommedStatus.do",
            data: params,
            dataType: "json",
            success: function (data) {
                new $.flavr({
                    modal: false,
                    content: data.msg
                });
                /* Closing the dialog */
                if (data.success) {
                    $("#quxiao").click();
                    $('#supplierRecommedId').bootstrapTable('refresh');
                }
            }
        });

    } else {

        var confirm = new $.flavr({
            content : '确定删除吗?',
            dialog : 'confirm',
            onConfirm : function() {
                confirm.close();
                $.ajax({
                    type: "POST",
                    url: "./updateSupplierRecommedStatus.do",
                    data: params,
                    dataType: "json",
                    success: function (data) {
                        new $.flavr({
                            modal: false,
                            content: data.msg
                        });
                        /* Closing the dialog */
                        if (data.success) {
                            $("#quxiao").click();
                            $('#supplierRecommedId').bootstrapTable('refresh');
                        }
                    }
                });
            },
            onCancel : function() {
                $("#quxiao").click();
            }
        });
    }
}

/**
 * 编辑
 * @param pk
 * @param companyId
 * @param name
 * @param type
 * @param url
 * @param starttime
 * @param endtime
 * @param sort
 * @param position
 * @param linkUrl
 * @param productPk
 * @param position
 * @param productName
 */
function editSupplierRE(pk, storePk, isRecommed, url, sort, position, linkUrl, brandPk, block, platform, region) {
    $(".kv-file-remove").click();
    $('#myModal2').modal();
    if (url == null || url == '') {
        $("#PURL_flag").val(0);
    } else {
        $("#PURL_flag").val(1);
    }
    $('input:checkbox').each(function () {
        $(this).attr('checked',false);
    });
    var imgId = document.getElementById("PURL");
    if (null != pk && '' != pk) {
        if (url.length == 0) {
            imgId.style.display = "none";
        } else {
            imgId.style.display = "";
        }
        $('#pk').val(pk);
        if (sort != 'undefined') {
            $('#sort').val(sort);
        } else {
            $('#sort').val('');
        }
        if (linkUrl != 'undefined') {
            $('#linkUrl').val(linkUrl);
        } else {
            $('#linkUrl').val('');
        }
        $("#PURL").attr("src", url);
        $('#tupian').val(url);
        if(checkIsEmpty(platform)){
           var plat =  platform.split(",");
            for(var i = 0;i < plat.length; i++){
                $("input[name='platform']:checkbox[value='" + plat[i] + "']").prop("checked", true);
            }
        }
        if(checkIsEmpty(position)){
            var pos =  position.split(",");
            for(var n = 0;n < pos.length; n++){
                $("input[name='position']:checkbox[value='" + pos[n] + "']").prop("checked", true);
            }
        }
        if(checkIsEmpty(block)){
            var blo =  block.split(",");
            for(var m = 0;m < blo.length; m++){
                $("input[name='block']:checkbox[value='" + blo[m] + "']").prop("checked", true);
            }
        }
        $("input[name='isRecommed'][value='" + isRecommed + "']").prop("checked", true);

        selectedOptionRegion(region);
        selectedOptionBrand(brandPk);
        selectedOptionStore(storePk,brandPk);

    } else {
        var imgId = document.getElementById("PURL");
        imgId.style.display = "none";
        $('#pk').val('');
        $('#sort').val('');
        $("#linkUrl").val('');
        $("#tupian").val('');
        selectedOptionRegion("");
        selectedOptionBrand("");
        $("#storeName").empty().append("<option value=''>--请选择--</option>");
        $("#storeName").selectpicker('refresh');//
        $("input[name='isRecommed'][value='" + 2 + "']").prop("checked", true);
    }
}

function selectedOptionBrand(brandPk){
    $.ajax({
        type: "POST",
        url: "./getAllBrandList.do",
        data: {},
        dataType: "json",
        success: function (data) {
            $("#brandName").empty().append("<option value=''>--请选择--</option>");
            for (var m = 0; m < data.length; m++) {
                if (data[m].brandPk == brandPk) {
                    $("#brandName").append("<option value=" + data[m].brandPk + " selected>" + data[m].brandName + "</option>");
                } else {
                    $("#brandName").append("<option value=" + data[m].brandPk + ">" + data[m].brandName + "</option>");
                }
            }
            $("#brandName").selectpicker('refresh');//
        }
    });
}

function selectedOptionStore(storePk,brandPk){
    $.ajax({
        type: "POST",
        url: "./getGoodsBrandListByBrandPk.do",
        data: {brandPk:brandPk},
        dataType: "json",
        success: function (data) {
            $("#storeName").empty().append("<option value=''>--请选择--</option>");
            if (checkIsEmpty(brandPk)) {
                for (var m = 0; m < data.length; m++) {
                    if (data[m].storePk == storePk) {
                        $("#storeName").append("<option value=" + data[m].storePk + " selected>" + data[m].storeName + "</option>");
                    } else {
                        $("#storeName").append("<option value=" + data[m].storePk + ">" + data[m].storeName + "</option>");
                    }
                }
            }
            $("#storeName").selectpicker('refresh');//
        }
    });
}

function selectedOptionRegion(region){

    $("#region").empty().append("<option value=''>--请选择--</option>");
    for (var m = 0; m < region_Type.length; m++) {
        if (region_Type[m].key == region) {
            $("#region").append("<option value=" + region_Type[m].key + " selected>" + region_Type[m].value + "</option>");
        } else {
            $("#region").append("<option value=" + region_Type[m].key + ">" + region_Type[m].value + "</option>");
        }
    }
    $("#region").selectpicker('refresh');//
}


function changeGoodsBrand(brandPk){

    $.ajax({
        type: "POST",
        url: "./getGoodsBrandListByBrandPk.do",
        data: {brandPk:brandPk},
        dataType: "json",
        success: function (data) {
            $("#storeName").empty().append("<option value=''>--请选择--</option>");
            if (checkIsEmpty(brandPk)) {
                for (var m = 0; m < data.length; m++) {
                    $("#storeName").append("<option value=" + data[m].storePk + ">" + data[m].storeName + "</option>");
                }
            }
            $("#storeName").selectpicker('refresh');//
        }
    });
}



/**
 * 保存
 */
function submit() {
    if (!validNotHidden("#dataForm")) {
        return;
    }
    var params = {};
    var pk = $("#pk").val();
    params['pk'] = pk;
    var sotr = $("#sort").val();
    var platform = "";
    $("input[name='platform']:checkbox:checked").each(function(){
        if($(this).val() != null && $(this).val() != ''){
            platform +=$(this).val()+",";
        }
    })
    if(platform != ""){
        platform = platform.substring(0,platform.length - 1);
    }else{
        new $.flavr({
            modal: false,
            content: "请选择放置终端！"
        });
        return;
    }

    var position = "";
    $("input[name='position']:checkbox:checked").each(function(){
        if($(this).val() != null && $(this).val() != ''){
            position +=$(this).val()+",";
        }
    })
    if(position != ""){
        position = position.substring(0,position.length - 1);
    }else{
        new $.flavr({
            modal: false,
            content: "请选择放置位置！"
        });
        return;
    }

    var block = "";
    $("input[name='block']:checkbox:checked").each(function(){
        if($(this).val() != null && $(this).val() != ''){
            block +=$(this).val()+",";
        }
    })
    if(block != ""){
        block = block.substring(0,block.length - 1);
    }else{
        new $.flavr({
            modal: false,
            content: "请选择模块！"
        });
        return;
    }
    params['platform'] = platform;
    params['position'] = position;
    params['block'] = block;
    params['sort'] = sotr;
    params['isRecommed'] = $('input:radio[name="isRecommed"]:checked').val();
    params['linkUrl'] = $("#linkUrl").val();

    params['brandPk'] = $("#brandName").val();
    params['region'] = $("#region").val();
    params['brandName'] = $("#brandName").val() == "" ? null : $("#brandName option:selected").text();
    params['storePk'] = $("#storeName").val();
    params['storeName'] = $("#storeName").val() == "" ? null : $("#storeName option:selected").text();


    if (!checkIsEmpty(params['brandPk'])){
        new $.flavr({
            modal: false,
            content: "品牌名称不能为空"
        });
        return;
    }

    if (!checkIsEmpty(params['storePk'])){
        new $.flavr({
            modal: false,
            content: "关联店铺不能为空"
        });
        return;
    }

    if (!checkIsEmpty(params['region'])){
        new $.flavr({
            modal: false,
            content: "所在区域不能为空"
        });
        return;
    }

    if (!checkIsEmpty($('#tupian').val())){
        new $.flavr({
            modal: false,
            content: "请选上传图片！"
        });
        return;
    }
    if (isNotZeroDNumber(sotr) == false || sotr.length > 9) {
        new $.flavr({
            modal: false,
            content: "排序必须大于零的整数并且小于十位数字"
        });
        return;
    }
    params['url'] = $('#tupian').val();
    var url = "";
    if (pk == '' || pk == null) {
        url = "./insertSupplierRecommed.do";
    } else {
        url = "./updateSupplierRecommed.do";
    }
    $.ajax({
        type: "POST",
        url: url,
        data: params,
        dataType: "json",
        success: function (data) {
            new $.flavr({
                modal: false,
                content: data.msg
            });
            /* Closing the dialog */
            if (data.success) {
                $("#quxiao").click();
                $('#supplierRecommedId').bootstrapTable('refresh');
            }
        }
    });
}

/**
 * 列表table切换
 * @param s
 */
function findSuppRE(s) {
    $("#isOnline").val(s);
    var cfg = {
        url: './searchSupplierRecommedList.do' + urlParams(1),
        columns: columns,
        uniqueId: 'pk',
        sortName: 'sort',
        sortOrder: 'desc',
        toolbar: toolbar,
        onLoadSuccess: loadFancyBox
    };
    createDataGrid('supplierRecommedId', cfg);
}

function searchTabs(type) {
    //清除所有内容
    if (type == 2) {
        cleanpar();
    }
    var cfg = {
        url: './searchSupplierRecommedList.do' + urlParams(1),
        columns: columns,
        uniqueId: 'pk',
        sortName: 'sort',
        sortOrder: 'desc',
        toolbar: toolbar,
        onLoadSuccess: loadFancyBox
    };
    createDataGrid('supplierRecommedId', cfg);
}

function loadFancyBox() {
    $(".fancybox").fancybox({
        'transitionIn': 'elastic',
        'transitionOut': 'elastic',
        'titlePosition': 'inside'
    });
    btnList();
}
 
