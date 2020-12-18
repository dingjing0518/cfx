
$(function () {

    //审批历史-查看按钮
    var cfg = {
        url: './searchApproveHistoryList.do?companyPk=' + $("#firstCompanyPk").val(),
        columns: columns,
        uniqueId: 'pk',
        sortName: 'insertTime',
        sortOrder: 'desc',
        toolbar: toolbar,
        onLoadSuccess: btnList
    };
    createDataGrid('perfectCustomerDatumId', cfg);


    //审批记录、历史审批记录
    var cfg = {
        url: './searchEconCustAuditDetailList.do?processInstanceId='+ $("#processInstanceId").val(),
        columns: columnsTwo,
        uniqueId: 'pk',
        sortName: 'insertTime',
        sortOrder: 'desc',
        toolbar: toolbar,
        onLoadSuccess: btnList
    };
    createDataGrid('approveHistoryTableId', cfg);
    
    /**
     * 资料录入进度
     */
    $.ajax({
        type : 'POST',
        url : './searchProcess.do?processInstanceId='+$("#processInstanceId").val(),
        data : {
        },
        dataType : 'json',
        success : function(data) {
            for(var i=0 ; i<data.length;i++){
                $("#il_"+data[i].key).addClass("active");
                $("#time_"+data[i].key).html(data[i].time);
            }
        }
    });

    var deviceNumber = 1;
    $('#addNewDevice').click(function () {
        if (deviceNumber < 10) {
            deviceNumber++;
            $('#addDeviceDiv')
                .append('<div class="step">'
                    + '<span>设备类型：</span>'
                    + '<select id="deviceType' + deviceNumber + '" name="deviceType" onchange="changeAddDevice(this.value,' + deviceNumber + ')">'
                    + '<option value="">--请选择--</option>'
                    + '<option value="1">圆机</option>'
                    + '<option value="2">经编机</option>'
                    + '<option value="3">加弹机</option>'
                    + '<option value="4">梭织机</option>'
                    + '</select>'
                    + '<span>设备品牌：</span>'
                    + '<select id="deviceBrand' + deviceNumber + '" name="deviceBrand">'
                    + '<option value="">--请选择--</option>'
                    + '</select>'
                    + '<span>设备数量：</span>'
                    + '<input type="text" onkeyup="format_input_nPlus(this,10)" maxlength="10" id="deviceNum' + deviceNumber + '" name="deviceNum"/>&nbsp;&nbsp;台'
                    + '</div>');
        } else {
            new $.flavr({
                modal: false,
                content: "最多添加10个"
            });
        }
        deviceNumberindex = deviceNumber;
    });

    var rawNumber = 1;
    $('#addRaw').click(function () {
        if (rawNumber < 10) {
            rawNumber++;
            $('#rawAddDiv')
                .append(
                    '<div class="step">'
                    + '<input class="form-controlgoods input-append" type="text"  readonly id="rawPurchaseYear' + rawNumber + '" onclick="getYear(\'rawPurchaseYear\',' + rawNumber + ')"/>'
                    + '<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="rawPurchaseAmount' + rawNumber + '"/>&nbsp;&nbsp;万元'
                    + '</div>'
                );
        } else {
            new $.flavr({
                modal: false,
                content: "最多添加10个"
            });
        }
        rawNumberindex = rawNumber;
        $(".input-append").datetimepicker({
        	startView: 'decade',
            minView: 'decade',
            format: 'yyyy',
            maxViewMode: 2,
            minViewMode:2,
            language : "zh-CN",
            autoclose: true  ,
            clearBtn: true,
            pickerPosition:"bottom-right"
        });
    });

    var consNumber = 1;
    $('#addConsum').click(function () {
        if (consNumber < 10) {
            consNumber++;
            $('#addConsumPerMonth')
                .append(
                    '<div class="step">'
                    + '<input class="form-controlgoods input-append" type="text"  readonly id="consumPerMonth' + consNumber + '" onclick="getMonth(\'consumPerMonth\',' + consNumber + ')"/>'
                    + '<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="consumPerMonthAmount' + consNumber + '"/>&nbsp;&nbsp;元'
                    + '</div>'
                );
        } else {
            new $.flavr({
                modal: false,
                content: "最多添加10个"
            });
        }
        consNumberindex = consNumber;
        $(".input-append").datetimepicker({
            format: 'yyyy-mm',
            weekStart: 1,
            autoclose: true,
            startView: 3,
            minView: 3,
            forceParse: false,
            language: 'zh-CN',
            autoclose : true,
            clearBtn: true,
            pickerPosition: "bottom-right"
        });
    });


    //主要设备编辑显示
    var deviceType = $('#deviceType').val();
    var deviceStatusJson = $("#deviceStatusJson").val();
    if (isNotZeroDNumber(deviceType)) {
        var jsonarray= $.parseJSON(deviceStatusJson);
        editDeviceType(deviceType,jsonarray.deviceBrand);
    }
    //编辑时解析json
    var otherDeviceStatusJson = $("#otherDeviceStatusJson").val();
    if (checkIsEmpty(otherDeviceStatusJson)){
        var jsonarray= $.parseJSON(otherDeviceStatusJson);
        for(var i = 0; i< jsonarray.length; i++){
            if(i > 0){
                deviceNumber++;
                $('#addDeviceDiv')
                    .append('<div class="step">'
                        + '<span>设备类型：</span>'
                        + '<select id="deviceType' + deviceNumber + '" name="deviceType" onchange="changeAddDevice(this.value,' + deviceNumber + ')">'
                        + '</select>'
                        + '<span>设备品牌：</span>'
                        + '<select id="deviceBrand' + deviceNumber + '" name="deviceBrand">'
                        + '</select>'
                        + '<span>设备数量：</span>'
                        + '<input type="text" onkeyup="format_input_nPlus(this,10)" maxlength="10" id="deviceNum' + deviceNumber + '" name="deviceNum" value="'+jsonarray[i].deviceNum+'"/>&nbsp;&nbsp;台'
                        + '</div>');
                $("#deviceType"+deviceNumber).selectpicker('refresh');//
                $("#deviceBrand"+deviceNumber).selectpicker('refresh');//

                changeEditDevice(deviceNumber,jsonarray[i].deviceType);
                var deviceType = $('#deviceType'+deviceNumber).val();
                editOtherDeviceType(deviceType,jsonarray[i].deviceBrand,deviceNumber);
            } else{
                changeEditDevice(1,jsonarray[i].deviceType);
                var deviceType = $('#deviceType'+1).val();
                editOtherDeviceType(deviceType,jsonarray[i].deviceBrand,1);
                $("#deviceNum1").val(jsonarray[0].deviceNum);
            }
            deviceNumberindex = deviceNumber;
        }
    }

    //年度原料累计采购金额
    var rawPurchaseAmountJson = $("#rawPurchaseAmountJson").val();
    if (checkIsEmpty(rawPurchaseAmountJson)) {
        var jsonarray = $.parseJSON(rawPurchaseAmountJson);

        for(var i = 0; i< jsonarray.length; i++){

            if (i == 0){
                $("#rawPurchaseYear1").val(jsonarray[0].rawPurchaseYear);
                $("#rawPurchaseAmount1").val(jsonarray[0].rawPurchaseAmount);
            } else{
                rawNumber ++;
                $('#rawAddDiv')
                    .append(
                        '<div class="step">'
                        + '<input class="form-controlgoods input-append date form-dateyear" type="text"  readonly id="rawPurchaseYear' + rawNumber + '" onclick="getYear(\'rawPurchaseYear\',' + rawNumber + ')" value="'+jsonarray[i].rawPurchaseYear+'"/>'
                        + '<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="rawPurchaseAmount' + rawNumber + '" value="'+jsonarray[i].rawPurchaseAmount+'"/>&nbsp;&nbsp;万元'
                        + '</div>'
                    );
            }

        }
        rawNumberindex = rawNumber;
        $(".input-append").datetimepicker({
            startView: 'decade',
            minView: 'decade',
            format: 'yyyy',
            maxViewMode: 2,
            minViewMode:2,
            language : "zh-CN",
            autoclose: true  ,
            clearBtn: true,
            pickerPosition:"bottom-right"
        });
    }

    //单月企业能耗
    var consumPerMonthJson = $("#consumPerMonthJson").val();
    if (checkIsEmpty(consumPerMonthJson)) {
        var jsonarray = $.parseJSON(consumPerMonthJson);
        for(var i = 0; i< jsonarray.length; i++){
            if (i == 0){
                $("#consumPerMonth1").val(jsonarray[0].consumPerMonth);
                $("#consumPerMonthAmount1").val(jsonarray[0].consumPerMonthAmount);
            } else{
                consNumber ++;
                $('#addConsumPerMonth')
                    .append(
                        '<div class="step">'
                        + '<input class="form-controlgoods input-append" type="text"  readonly id="consumPerMonth' + consNumber + '" onclick="getMonth(\'consumPerMonth\',' + consNumber + ')" value="'+jsonarray[i].consumPerMonth+'"/>'
                        + '<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="consumPerMonthAmount' + consNumber + '" value="'+jsonarray[i].consumPerMonthAmount+'"/>&nbsp;&nbsp;元'
                        + '</div>'
                    );
            }
        }
        consNumberindex = consNumber;
        $(".input-append").datetimepicker({
            format: 'yyyy-mm',
            weekStart: 1,
            autoclose: true,
            startView: 3,
            minView: 3,
            forceParse: false,
            language: 'zh-CN',
            autoclose : true,
            clearBtn: true,
            pickerPosition: "bottom-right"
        });
    }
    var auditStatus = $("#auditStatus").val();
    //定时保存数据
    if (auditStatus == 1||auditStatus == 4){
        var idInt = setInterval("saveDatum(2);", 30000);
    }
});
//用于动态添加计数
var deviceNumberindex = 1;
var rawNumberindex = 1;
var consNumberindex = 1;
var toolbar = [];

var columns = [
    {
        title : '申请时间',
        field : 'insertTime',
        width : 20,
        sortable : true
    },{
        title : '审批产品',
        field : 'productName',
        width : 20,
        sortable : true,

    }, {
        title : '审批时间',
        field : 'approveTime',
        width : 20,
        sortable : true
    }, {
        title : '审批状态',
        field : 'auditStatus',
        width : 20,
        sortable : true,
        formatter : function (value, row, index) {
            if(value==1){
                return "待审批";
            }else if(value==2){
                return "审批中";
            }else if(value==3){
                return "已审批";
            }else if(value==4){
                return "驳回";
            }
        }
    },
    {
        title: '操作',
        field: 'pk',
        width: 80,
        formatter: function (value, row, index) {
            var str = '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" onclick="javascript:showHistory(\'' + row.processInstanceId + '\'); ">查看</button>';
            return str;
        }
    }
];

var columnsTwo =  [ {
    title : '审批人',
    field : 'userId',
    width : 20,
    sortable : true
}, {
    title : '审批时间',
    field : 'time',
    width : 20,
    sortable : true
}, {
    title : '审批内容',
    field : 'fullMessage',
    width : 20,
    sortable : true
},  {
    title : '审批状态',
    field : 'state',
    width : 20,
    sortable : true
}];


function changeDeviceType(value) {
    var array = device_Brand[value];
    if (array != null && array.length > 0) {
        $("#deviceBrand").empty().append("<option value=''>--请选择--</option>");
        for (var i = 0; i < array.length; i++) {
            $("#deviceBrand").append("<option value=\"" + array[i] + "\">" + array[i] + "</option>");
        }
        $("#deviceBrand").selectpicker('refresh');//
    }
}


function changeAddDevice(value, index) {

    var array = device_Brand[value];
    if (array != null && array.length > 0) {
        $("#deviceBrand" + index).empty().append("<option value=''>--请选择--</option>");
        for (var i = 0; i < array.length; i++) {
            $("#deviceBrand" + index).append("<option value=\"" + array[i] + "\">" + array[i] + "</option>");
        }
        $("#deviceBrand" + index).selectpicker('refresh');//
    }
}

//编辑显示设备
function changeEditDevice(index, name) {
    $("#deviceType"+index).empty().append('<option value="">--请选择--</option>');

    for (var n = 0; n < device_Type.length; n++) {
        if (device_Type[n].value == name){
            $("#deviceType"+index).append("<option value=\"" + device_Type[n].key + "\" selected>" + device_Type[n].value + "</option>");
        }else{
            $("#deviceType"+index).append("<option value=\"" + device_Type[n].key + "\">" + device_Type[n].value + "</option>");
        }
    }
        $("#deviceType" + index).selectpicker('refresh');//
}

//编辑显示设备品牌
function editDeviceType(value,name) {
    var array = device_Brand[value];
    if (array != null && array.length > 0) {
        $("#deviceBrand").empty().append("<option value=''>--请选择--</option>");
        for (var i = 0; i < array.length; i++) {
            if (name == array[i]) {
                $("#deviceBrand").append("<option value=\"" + array[i] + "\" selected>" + array[i] + "</option>");
            }else{
                $("#deviceBrand").append("<option value=\"" + array[i] + "\">" + array[i] + "</option>");
            }

        }
        $("#deviceBrand").selectpicker('refresh');//
    }
}

//编辑显示其他设备品牌
function editOtherDeviceType(value,name,index) {
    var array = device_Brand[value];
    if (array != null && array.length > 0) {
        $("#deviceBrand"+index).empty().append("<option value=''>--请选择--</option>");
        for (var i = 0; i < array.length; i++) {
            if (name == array[i]) {
                $("#deviceBrand"+index).append("<option value=\"" + array[i] + "\" selected>" + array[i] + "</option>");
            }else{
                $("#deviceBrand"+index).append("<option value=\"" + array[i] + "\">" + array[i] + "</option>");
            }

        }
        $("#deviceBrand"+index).selectpicker('refresh');//
    }
}


//当前企业对外担保余额/企业一自然年度纳税销售金额
//当前关联企业对外担保余额/企业一自然年度纳税销售金额
//企业当期融资金额/企业上一自然年度纳税销售金额
//关联企业当期融资金额/关联企业上一自然年度纳税销售金额
//百分比计算
function guaraTaxSalesPer() {
    var amountGuara = $("#amountGuara").val();
    var lastTaxSales = $("#lastTaxSales").val();
    var guaraTaxSalesPer = 0.0;
    //当前企业对外担保余额/企业一自然年度纳税销售金额
    if (checkIsEmpty(amountGuara) && checkIsEmpty(lastTaxSales) && isGteZero(lastTaxSales)) {
        guaraTaxSalesPer = (amountGuara / lastTaxSales) * 100;
    }
    $("#guaraTaxSalesPer").val(guaraTaxSalesPer.toFixed(2));

    var releAmountGuara = $("#releAmountGuara").val();
    var releLastTaxSales = $("#releLastTaxSales").val();
    var releGuaraTaxSalesPer = 0.0;
    //当前关联企业对外担保余额/企业一自然年度纳税销售金额
    if (checkIsEmpty(releAmountGuara) && checkIsEmpty(releLastTaxSales) && isGteZero(releLastTaxSales)) {
        releGuaraTaxSalesPer = (releAmountGuara / releLastTaxSales) * 100
    }
    $("#releGuaraTaxSalesPer").val(releGuaraTaxSalesPer.toFixed(2));

    var finanAmount = $("#finanAmount").val();
    var releFinanAmount = $("#releFinanAmount").val();
    var finanAmountTaxSales = 0.0;
    //企业当期融资金额/企业上一自然年度纳税销售金额
    if (checkIsEmpty(finanAmount) && checkIsEmpty(lastTaxSales)  && isGteZero(lastTaxSales)) {
        finanAmountTaxSales = (finanAmount / lastTaxSales) * 100
    }
    $("#finanAmountTaxSales").val(finanAmountTaxSales.toFixed(2));

    var releFinanAmountTaxSales = 0.0;
    //关联企业当期融资金额/关联企业上一自然年度纳税销售金额
    if (checkIsEmpty(releFinanAmount) && checkIsEmpty(releLastTaxSales)  && isGteZero(releLastTaxSales)) {
        releFinanAmountTaxSales = (releFinanAmount / releLastTaxSales) * 100
    }
    $("#releFinanAmountTaxSales").val(releFinanAmountTaxSales.toFixed(2));

    //银行近期年化结算流水/企业上一自然年度纳税销售金额
    var bankAnnualizAmount = $("#bankAnnualizAmount").val();
    var annualizTaxSalesPer = 0.0;
    if (checkIsEmpty(bankAnnualizAmount) && checkIsEmpty(lastTaxSales)  && isGteZero(lastTaxSales)) {
        annualizTaxSalesPer = (bankAnnualizAmount/lastTaxSales)*100;
    }
    $("#annualizTaxSalesPer").val(annualizTaxSalesPer.toFixed(2));
}


//企业能耗计算
function consumPerMonthFunction(){
    var month = 0;
    var allMonthAmount = 0;
    var consNumber = [];
    for(var i = 1 ;i < consNumberindex+2; i++){
       var amount = $("#consumPerMonthAmount"+i).val();
       var consumMonth = $("#consumPerMonth"+i).val();

        if(isFloatTwo(amount) && checkIsEmpty(consumMonth)){
            allMonthAmount += parseFloat(amount);
            consNumber[i-1] = amount;
            month ++;
        }
    }
    //合计企业能耗
    $("#consumAddup").val(allMonthAmount.toFixed(2));

    if(isNotZeroDNumber(month)){
        var vagAmount = allMonthAmount/month;
        //企业能耗平均值
        $("#averageConsum").val(vagAmount.toFixed(2))
    }else{
        $("#averageConsum").val(0.0)
    }
    //求最大值-平均值绝对值
    var min = consNumber.min(consNumber);
    var max = consNumber.max(consNumber);
    var maxAbs = Math.abs(max-vagAmount);
    var minAbs = Math.abs(min-vagAmount);
    var stablePer = 0.0;
    if(maxAbs > minAbs){
        stablePer = (maxAbs/vagAmount)*100;
    } else{
        stablePer = (minAbs/vagAmount)*100;
    }
    //企业主要能耗稳定性
    if (stablePer > 0){
        $("#consumStable").val(stablePer.toFixed(2));
    }
    var averageConsum = $("#averageConsum").val();

    var industryAverageConsum = $("#industryAverageConsum").val();

    var averageIndustryConsumPer = 0;
    if (isGteZero(averageConsum) && isGteZero(industryAverageConsum)){
        averageIndustryConsumPer = (averageConsum/industryAverageConsum)*100
        //企业能耗平均值/行业平均能耗
        $("#averageIndustryConsumPer").val(averageIndustryConsumPer.toFixed(2));
    }

    //汇总面积
    var workshopOwnSquare = isDoubleNUllOrEmptyStr($("#workshopOwnSquare").val());
    var workshopRentSquare = isDoubleNUllOrEmptyStr($("#workshopRentSquare").val());
    var allSquare = parseFloat(workshopOwnSquare)+parseFloat(workshopRentSquare)*0.3;
    $("#workshopAllSquare").val(allSquare.toFixed(2));
}


//上下游客户稳定性计算
function upstreamCustomer(){
   var upstreamCustomerNum = $("#upstreamCustomerNum").val();
   var upstreamCustomerBaseNum = $("#upstreamCustomerBaseNum").val();
   var upstreamCustomerStable = 0;
   if(checkIsEmpty(upstreamCustomerNum) && checkIsEmpty(upstreamCustomerBaseNum)  && isGteZero(upstreamCustomerBaseNum)){
       upstreamCustomerStable = (upstreamCustomerNum/upstreamCustomerBaseNum)*100;
   }
   //上游客户稳定性
   $("#upstreamCustomerStable").val(upstreamCustomerStable.toFixed(2));

    var downstreamCustomerNum = $("#downstreamCustomerNum").val();
    var downstreamCustomerBaseNum = $("#downstreamCustomerBaseNum").val();
    var downstreamCustomerStable = 0;
    if(checkIsEmpty(downstreamCustomerNum) && checkIsEmpty(downstreamCustomerBaseNum)  && isGteZero(downstreamCustomerBaseNum)){
        downstreamCustomerStable = (downstreamCustomerNum/downstreamCustomerBaseNum)*100;
    }
    //下游游客户稳定性
    $("#downstreamCustomerStable").val(downstreamCustomerStable.toFixed(2));

   var vipCustPurchaserAddUp = $("#vipCustPurchaserAddUp").val();
    var inputTaxAmount = $("#inputTaxAmount").val();
    var upstreamVipCustStable = 0;
    if(checkIsEmpty(vipCustPurchaserAddUp) && checkIsEmpty(inputTaxAmount)  && isGteZero(inputTaxAmount)){
        upstreamVipCustStable = (vipCustPurchaserAddUp/inputTaxAmount)*100
    }
    //上游重点客户交易量稳定性
    $("#upstreamVipCustStable").val(upstreamVipCustStable.toFixed(2));

   var lastTaxSales = $("#lastTaxSales").val();
   var vipCustomerSales = $("#lastTaxSales").val();

   var downstreamCustSalesStable = 0;
   if (checkIsEmpty(vipCustomerSales) && checkIsEmpty(lastTaxSales)  && isGteZero(lastTaxSales)){
       downstreamCustSalesStable = (vipCustomerSales/lastTaxSales)*100;
   }
   //下游重点客户交易量稳定性
    $("#downstreamCustSalesStable").val(downstreamCustSalesStable.toFixed(2));


    var platformSales = $("#platformSales").val();
    var lineOfCredit = $("#lineOfCredit").val();
    var platformProfitPer = 0;
    if(checkIsEmpty(platformSales) && checkIsEmpty(lineOfCredit)  && isGteZero(lineOfCredit)){
        platformProfitPer = (platformSales/lineOfCredit)*100;
    }
    //企业近十二个月平台利润贡献度
    $("#platformProfitPer").val(platformProfitPer.toFixed(2));
}


Array.prototype.max = function(){
    return Math.max.apply({},this)
}
Array.prototype.min = function(){
    return Math.min.apply({},this)
}
//获取所有保存参数
function getAllParams(params){

    params['customerType'] = $('input:radio[name="customerType"]:checked').val();
    params['companyPk'] = $("#firstCompanyPk").val();
    params['econCustmerPk'] = $("#econCustmerPk").val();

    var createTime = $('#establishingTime').val();
    if (checkIsEmpty(createTime)){
        var myDate = new Date;
        var year = myDate.getFullYear();
        var month = myDate.getMonth();

        var sArr = createTime.split("-");
        var years = year - sArr[0];
        var months = month - sArr[1];
        params['establishingTime'] = years*12+(months+1);
        params['establishingYear'] = createTime;
    }

    params['releLastTaxSales'] = isDoubleNUllOrEmptyStr($('#releLastTaxSales').val());
    params['amountGuara'] = isDoubleNUllOrEmptyStr($('#amountGuara').val());
    params['releAmountGuara'] = isDoubleNUllOrEmptyStr($('#releAmountGuara').val());
    params['guaraTaxSalesPer'] = isDoubleNUllOrEmptyStr($('#guaraTaxSalesPer').val());
    params['releGuaraTaxSalesPer'] = isDoubleNUllOrEmptyStr($('#releGuaraTaxSalesPer').val());
    params['finanAmount'] = isDoubleNUllOrEmptyStr($('#finanAmount').val());
    params['releFinanAmount'] = isDoubleNUllOrEmptyStr($('#releFinanAmount').val());
    params['finanAmountTaxSales'] = isDoubleNUllOrEmptyStr($('#finanAmountTaxSales').val());
    params['releFinanAmountTaxSales'] = isDoubleNUllOrEmptyStr($('#releFinanAmountTaxSales').val());

    params['rawPurchaseIncrePer'] = isDoubleNUllOrEmptyStr($('#rawPurchaseIncrePer').val());
    params['transOnlineRawPurchPer'] = isDoubleNUllOrEmptyStr($('#transOnlineRawPurchPer').val());
    params['useMonthsNum'] = isIntNUllOrEmptyStr($('#useMonthsNum').val());
    params['econUseMonthsNum'] = isIntNUllOrEmptyStr($('#econUseMonthsNum').val());
    params['econUseMonthsAmount'] = isDoubleNUllOrEmptyStr($('#econUseMonthsAmount').val());
    params['econOverdueNum'] = isIntNUllOrEmptyStr($('#econOverdueNum').val());
    params['bankAnnualizAmount'] = isDoubleNUllOrEmptyStr($('#bankAnnualizAmount').val());
    params['annualizTaxSalesPer'] = isDoubleNUllOrEmptyStr($('#annualizTaxSalesPer').val());

    var annualizTaxAmountYear = $('#annualizTaxAmountYear').val();
    var inputAnnualizTax = $('#inputAnnualizTax').val();

    params['annualizTaxSalesTime'] = annualizTaxAmountYear;
    params['annualizTaxSalesAmount'] = isDoubleNUllOrEmptyStr(inputAnnualizTax);

    if(checkIsEmpty(annualizTaxAmountYear)){
        var splitYear = annualizTaxAmountYear.split("-");
        var month = splitYear[1];
        var taxAmount = (inputAnnualizTax/month)*12;
            params['annualizTaxAmount'] = taxAmount.toFixed(2);
    }
    var brandPk = $('#rawBrandPk').val();
    params['rawBrandPk'] = brandPk;
    if (checkIsEmpty(brandPk)){
        params['rawBrand'] = $("#rawBrandPk option:selected").text();
    }
    var rawAnnualizAmountYear = $('#rawAnnualizAmountYear').val()
    var inputRawAnnualiz = $('#inputRawAnnualiz').val()
    if(checkIsEmpty(rawAnnualizAmountYear)){
        var splitYear = rawAnnualizAmountYear.split("-");
        var month = splitYear[1];
        var rawAmount = (inputRawAnnualiz/month)*12;
        params['rawAnnualizAmount'] = rawAmount.toFixed(2);
    }
    params['rawAnnualizSalesAmount'] = isDoubleNUllOrEmptyStr(inputRawAnnualiz);
    params['rawAnnualizSalesTime'] = rawAnnualizAmountYear;

    params['finanInstitution'] = isIntNUllOrEmptyStr($('#finanInstitution').val());
    params['workshopOwnSquare'] = isDoubleNUllOrEmptyStr($('#workshopOwnSquare').val());
    params['workshopRentSquare'] = isDoubleNUllOrEmptyStr($('#workshopRentSquare').val());
    params['workshopAllSquare'] = isDoubleNUllOrEmptyStr($('#workshopAllSquare').val());
    params['flowOfProduction'] = $('#flowOfProduction').val();
    params['managementModel'] = $('#managementModel').val();
    params['businessShopNum'] = isIntNUllOrEmptyStr($('#businessShopNum').val());
    params['shareChangeNum'] = isIntNUllOrEmptyStr($('#shareChangeNum').val());
    params['enforcedNum'] = isIntNUllOrEmptyStr($('#enforcedNum').val());
    params['zxEnforcedNum'] = isIntNUllOrEmptyStr($('#zxEnforcedNum').val());
    params['ecomCooperateTime'] = isIntNUllOrEmptyStr($('#ecomCooperateTime').val());
    params['supplierCooperateTime'] = isIntNUllOrEmptyStr($('#supplierCooperateTime').val());
    params['purcherVariety'] = isIntNUllOrEmptyStr($('#purcherVariety').val());
    params['manageStable'] = isIntNUllOrEmptyStr($('#manageStable').val());
    params['consumAddup'] = isDoubleNUllOrEmptyStr($('#consumAddup').val());

    params['averageConsum'] = isDoubleNUllOrEmptyStr($('#averageConsum').val());

    params['consumStable'] = isDoubleNUllOrEmptyStr($('#consumStable').val());
    params['industryAverageConsum'] = isDoubleNUllOrEmptyStr($('#industryAverageConsum').val());
    params['averageIndustryConsumPer'] = isDoubleNUllOrEmptyStr($('#averageIndustryConsumPer').val());
    params['upstreamCustomerNum'] = isIntNUllOrEmptyStr($('#upstreamCustomerNum').val());
    params['upstreamCustomerBaseNum'] = isIntNUllOrEmptyStr($('#upstreamCustomerBaseNum').val());
    params['upstreamCustomerStable'] = isDoubleNUllOrEmptyStr($('#upstreamCustomerStable').val());
    params['downstreamCustomerNum'] = isIntNUllOrEmptyStr($('#downstreamCustomerNum').val());
    params['downstreamCustomerBaseNum'] = isIntNUllOrEmptyStr($('#downstreamCustomerBaseNum').val());
    params['downstreamCustomerStable'] = isDoubleNUllOrEmptyStr($('#downstreamCustomerStable').val());
    params['vipCustPurchaserAddUp'] = isDoubleNUllOrEmptyStr($('#vipCustPurchaserAddUp').val());
    params['inputTaxAmount'] = isDoubleNUllOrEmptyStr($('#inputTaxAmount').val());
    params['upstreamVipCustStable'] = isDoubleNUllOrEmptyStr($('#upstreamVipCustStable').val());

    var lastTaxSales = isDoubleNUllOrEmptyStr($('#lastTaxSales').val());

    params['lastTaxSales'] = lastTaxSales;
    var vipCustomerSales = isDoubleNUllOrEmptyStr($('#vipCustomerSales').val());
    params['vipCustomerSales'] = vipCustomerSales;
    if (lastTaxSales > 0){
        var downstreamCustSalesStable = (vipCustomerSales/lastTaxSales)*100;
        params['downstreamCustSalesStable'] = downstreamCustSalesStable.toFixed(2);
    }
    params['taxLevel'] = $('#taxLevel').val();

    var platformSales = isDoubleNUllOrEmptyStr($('#platformSales').val());

    params['platformSales'] = platformSales;

    var lineOfCredit = isDoubleNUllOrEmptyStr($('#lineOfCredit').val());

    params['lineOfCredit'] = lineOfCredit;
    var platformProfitPer = 0.0;
    if (lineOfCredit > 0){
        platformProfitPer = (platformSales/lineOfCredit)*100;
        params['platformProfitPer'] = platformProfitPer.toFixed(2);
    }

    var consumPerJson = "[";
    var isSumPerEmpty = 0;
    for(var i = 1 ;i < consNumberindex+1; i++) {
        var consumPerMonth = $("#consumPerMonth" + i).val();
        var consumPerMonthAmount = $("#consumPerMonthAmount" + i).val();
        if (checkIsEmpty(consumPerMonthAmount)){
            consumPerMonthAmount = parseFloat(consumPerMonthAmount);
        }
        if (checkIsEmpty(consumPerMonth) && checkIsEmpty(consumPerMonthAmount)){
            consumPerJson += "{\"consumPerMonth\":\"" + consumPerMonth + "\",\"consumPerMonthAmount\":\"" + consumPerMonthAmount + "\"},";
            isSumPerEmpty++;
        }
    }
    consumPerJson = consumPerJson.substring(0, consumPerJson.length - 1);
    consumPerJson += "]";
    if(consNumberindex != 0 && isSumPerEmpty != 0){
        params['consumPerMonth'] = consumPerJson;
    }
    var transAmountOnline = parseFloat(isDoubleNUllOrEmptyStr($('#transAmountOnline').val()));
    params['transAmountOnline'] = transAmountOnline;

    var rawJson = "[";
    var isRawEmpty = 0;
    var lastYearPurchaseAmount = 0;
    var beforeYearPurchaseAmount = 0;
    for(var i = 1 ;i < rawNumberindex+1; i++) {
        var rawPurchaseYear = $("#rawPurchaseYear" + i).val();
        var rawPurchaseAmount = $("#rawPurchaseAmount" + i).val();
        if (checkIsEmpty(rawPurchaseAmount)){
            rawPurchaseAmount = parseFloat(rawPurchaseAmount);
        }
        if ((parseInt(year)-1) == parseInt(rawPurchaseYear)){
            lastYearPurchaseAmount = parseFloat(rawPurchaseAmount);
        }
        if ((parseInt(year)-2) == parseInt(rawPurchaseYear)){
            beforeYearPurchaseAmount = parseFloat(rawPurchaseAmount);
        }
        if (checkIsEmpty(rawPurchaseYear) && checkIsEmpty(rawPurchaseAmount)) {
            isRawEmpty++;
            rawJson += "{\"rawPurchaseYear\":\"" + rawPurchaseYear + "\",\"rawPurchaseAmount\":\"" + rawPurchaseAmount + "\"},";
        }
    }
    rawJson = rawJson.substring(0, rawJson.length - 1);
    rawJson += "]";
    if (rawNumberindex != 0 && isRawEmpty != 0){
        params['rawPurchaseAmount'] = rawJson;
    }

    //企业原料采购环比稳定性
    if (isGteZero(beforeYearPurchaseAmount) > 0){
        var  stable = (lastYearPurchaseAmount/beforeYearPurchaseAmount)*100;
        params['rawPurchaseIncrePer'] = stable.toFixed(2)

    }
    //线上近12个月累计交易金额/原料去年累计采购金额
   var lastYear = parseFloat(isDoubleNUllOrEmptyStr(lastYearPurchaseAmount));
    if (isGteZero(lastYear) > 0){
        var dd = (transAmountOnline/lastYear)*100
        params['transOnlineRawPurchPer'] = dd.toFixed(2);
    }
    var deviceType = "";
    if (checkIsEmpty($("#deviceType").val())){
       deviceType = $("#deviceType option:selected").text();
    }
    var deviceBrand = "";
    if (checkIsEmpty($("#deviceBrand").val())) {
        deviceBrand  = $("#deviceBrand option:selected").text();
    }
    var deviceNum = $("#deviceNum").val();
    if (checkIsEmpty(deviceNum)){
        deviceNum = parseFloat(deviceNum);
    }
    if(checkIsEmpty(deviceType) && checkIsEmpty(deviceBrand)) {
        params['deviceStatus'] = "{\"deviceType\":\"" + deviceType + "\",\"deviceBrand\":\"" + deviceBrand + "\",\"deviceNum\":\"" + deviceNum + "\"}";
    }

    var otherDeviceJson = "[";
    var isEmpty = 0;
    for(var i = 1 ;i < deviceNumberindex+1; i++) {
        var deviceTypeI = "";
        if (checkIsEmpty($("#deviceType"+i).val())) {
            deviceTypeI = $("#deviceType"+i+" option:selected").text();
        }
        var deviceBrandI = "";
        if (checkIsEmpty($("#deviceBrand"+i).val())) {
            deviceBrandI = $("#deviceBrand"+i+" option:selected").text();
        }
        var deviceNumI = $("#deviceNum" + i).val();
        if (checkIsEmpty($("#deviceType"+i).val()) && checkIsEmpty($("#deviceBrand"+i).val())) {
            if (checkIsEmpty(deviceNumI)){
                deviceNumI = parseFloat(deviceNumI);
            }
            isEmpty++;
            otherDeviceJson += "{\"deviceType\":\"" + deviceTypeI + "\",\"deviceBrand\":\"" + deviceBrandI + "\",\"deviceNum\":\"" + deviceNumI + "\"},";
        }
    }
    otherDeviceJson = otherDeviceJson.substring(0, otherDeviceJson.length - 1);
    otherDeviceJson += "]";
    if (deviceNumberindex != 0 && isEmpty!= 0){
        params['otherDeviceStatus'] = otherDeviceJson;
    }
}

function isIntNUllOrEmptyStr(param){

    if(!checkIsEmpty(param)){
        return 0;
    } else{
        return param;
    }
}
function isDoubleNUllOrEmptyStr(param){

    if(!checkIsEmpty(param)){
        return 0.0;
    } else{
        return param;
    }
}


//完善资料保存、编辑
function submitDatum() {
    saveDatum(1);
}

function saveDatum(isSubmit){
    //计算
    guaraTaxSalesPer();
    consumPerMonthFunction();
    upstreamCustomer();
    var params = {};
    getAllParams(params);
    if (isSubmit == 1){
        //判断必填项
        if (validEmpty()){
            new $.flavr({
                modal : false,
                content : "请把资料填写完整！"
            });
            return ;
        }

        if (validAddScoreEmpty(params['customerType'])){
            new $.flavr({
                modal : false,
                content : "重点客户加分项是必填项！"
            });
            return ;
        }

    }
    $.ajax({
        type : "POST",
        url : './updateperfectCustDatum.do',
        data : params,
        dataType : "json",
        success : function(data) {
            if (isSubmit == 1){
                new $.flavr({
                    modal : false,
                    content : data.msg
                });
            }else{
                $("#lastSaveTimeShow").html("上次保存时间："+ josn_to_String(new Date()));
            }
        }
    });
}


function validEmpty(){
    var isNull = false
    $("[valids='notNull']").each(function(index,el){
        if(!checkIsEmpty($(el).val())){
            isNull = true;
            return false;
        }
    });
    return isNull;
}

function validAddScoreEmpty(customerType){
    var isNull = false
    if (customerType == 2){
        $("[valids='addIsNull']").each(function(index,el){
            if(!checkIsEmpty($(el).val())){
                isNull = true;
                return false;
            }
        });
    }
    return isNull;
}


//查看历史数据
function showHistory(processInstanceId){

    var cfg = {
        url : './searchEconCustAuditDetailList.do?processInstanceId='+ processInstanceId,
        columns : columnsTwo,
        uniqueId : 'pk',
        sortName : 'insertTime',
        sortOrder : 'desc',
        toolbar : toolbar,
        onLoadSuccess : btnList
    };
    createDataGrid('historyAuditId', cfg)
    
    $.ajax({
        type : "POST",
        url : './searchProcessDetail.do',
        data : {
            processInstanceId : processInstanceId
        },
        dataType : "json",
        success : function(data) {
            if (checkIsEmpty(data)){
                showCustomerHistory(data);
                $("li.showright").removeClass("none");
                $("li.bordershow").removeClass("width");
            }
        }
    });
}

//显示历史信息
function showCustomerHistory(data){

    if(data.customerType == 1){
        $("#customerTypeShow").html("一般客户");
    }else if(data.customerType == 2){
        $("#customerTypeShow").html("重点客户");
    }else{
        $("#customerTypeShow").html("");
    }
    if (checkIsEmpty(data.establishingYear)) {
        $("#establishingTimeShow").html(data.establishingYear);
    }else{
        $("#establishingTimeShow").html("");
    }
    $("#amountGuaraShow").html(data.amountGuara+" 万元");
    $("#lastTaxSalesShow").html(data.lastTaxSales+" 万元");
    $("#releLastTaxSalesShow").html(data.releLastTaxSales+" 万元");
    $("#releAmountGuaraShow").html(data.releAmountGuara+" 万元");
    $("#guaraTaxSalesPerShow").html(data.guaraTaxSalesPer+" %");
    $("#releGuaraTaxSalesPerShow").html(data.releGuaraTaxSalesPer+" %");
    $("#finanAmountShow").html(data.finanAmount+" 万元");
    $("#releFinanAmountShow").html(data.releFinanAmount+" 万元");
    $("#finanAmountTaxSalesShow").html(data.finanAmountTaxSales+" %");
    $("#releFinanAmountTaxSalesShow").html(data.releFinanAmountTaxSales+" %");
    if(checkIsEmpty(data.finanInstitution)){
        $("#finanInstitutionShow").html(data.finanInstitution+" 家");
    }else{
        $("#finanInstitutionShow").html(0 +" 家");
    }
    $("#transAmountOnlineShow").html(data.transAmountOnline+" 万元");
    $("#rawPurchaseIncrePerShow").html(data.rawPurchaseIncrePer+" %");
    $("#transOnlineRawPurchPerShow").html(data.transOnlineRawPurchPer+" %");
    $("#useMonthsNumShow").html(data.useMonthsNum+" 个月");
    $("#econUseMonthsNumShow").html(data.econUseMonthsNum+" 个月");
    $("#econUseMonthsAmountShow").html(data.econUseMonthsAmount+" 万元");
    $("#econOverdueNumShow").html(data.econOverdueNum+" 次");
    $("#bankAnnualizAmountShow").html(data.bankAnnualizAmount+" 万元");
    $("#annualizTaxSalesPerShow").html(data.annualizTaxSalesPer+" %");
    $("#annualizTaxAmountShow").html(data.annualizTaxAmount+" 万元");
    $("#rawBrandPkShow").html(data.rawBrand);
    $("#rawAnnualizAmountShow").html(data.rawAnnualizAmount+" 万元");
    $("#workshopOwnSquareShow").html("类型：自有  面积数：" + data.workshopOwnSquare+" 平方米");
    $("#workshopRentSquareShow").html("类型：租赁  面积数：" + data.workshopRentSquare+" 平方米");
    $("#workshopAllSquareShow").html("汇总面积："+data.workshopAllSquare+" m2");
    var flowName = "";
    if (data.flowOfProduction == 1){
        flowName = "一步";
    }else if (data.flowOfProduction == 2){
        flowName = "两步";
    }else if (data.flowOfProduction == 3) {
        flowName = "三步及以上";
    }
    $("#flowOfProductionShow").html(flowName);

    var managementName = "";
    if (data.managementModel == 1){
        managementName = "代加工";
    }else if (data.managementModel == 2){
        managementName = "自产自销";
    }else if (data.managementModel == 3){
        managementName = "自产自销+外发加工";
    }else if (data.managementModel == 4) {
        managementName = "自产自销+代加工";
    }
    $("#managementModelShow").html(managementName);
    $("#businessShopNumShow").html(data.businessShopNum+" 家");
    $("#shareChangeNumShow").html(data.shareChangeNum+" 次");
    $("#enforcedNumShow").html(data.enforcedNum+" 次");
    $("#zxEnforcedNumShow").html(data.zxEnforcedNum+" 次");
    $("#ecomCooperateTimeShow").html(data.ecomCooperateTime+" 年");
    if (checkIsEmpty(data.supplierCooperateTime)){
        $("#supplierCooperateTimeShow").html(data.supplierCooperateTime+" 年");
    } else {
        $("#supplierCooperateTimeShow").html(0 + " 年");
    }
    $("#purcherVarietyShow").html(data.purcherVariety);
    $("#manageStableShow").html(data.manageStable+" 年");
    $("#consumAddupShow").html(data.consumAddup+" 元");
    $("#averageConsumShow").html(data.averageConsum+" 元");
    $("#consumStableShow").html(data.consumStable+" %");
    $("#industryAverageConsumShow").html(data.industryAverageConsum+" 元");
    $("#averageIndustryConsumPerShow").html(data.averageIndustryConsumPer+" %");
    $("#upstreamCustomerNumShow").html(data.upstreamCustomerNum+" 家");
    $("#upstreamCustomerBaseNumShow").html(data.upstreamCustomerBaseNum+" 家");
    $("#upstreamCustomerStableShow").html(data.upstreamCustomerStable+" %");

    $("#downstreamCustomerNumShow").html(data.downstreamCustomerNum+" 家");
    $("#downstreamCustomerBaseNumShow").html(data.downstreamCustomerBaseNum+" 家");
    $("#downstreamCustomerStableShow").html(data.downstreamCustomerStable+" %");
    $("#vipCustPurchaserAddUpShow").html(data.vipCustPurchaserAddUp+" 万元");
    $("#inputTaxAmountShow").html(data.inputTaxAmount+" 万元");
    $("#upstreamVipCustStableShow").html(data.upstreamVipCustStable+" %");
    $("#vipCustomerSalesShow").html(data.vipCustomerSales+" 万元");
    $("#downstreamCustSalesStableShow").html(data. downstreamCustSalesStable+" %");
    var taxLevelName = "";
    if (data.taxLevel == 1){
        taxLevelName = "A级";
    }else if (data.taxLevel == 2){
        taxLevelName = "B级";
    }else if (data.taxLevel == 3){
        taxLevelName = "C级";
    }else if (data.taxLevel == 4) {
        taxLevelName = "D级";
    }
    $("#taxLevelShow").html(taxLevelName);
    $("#platformSalesShow").html(data.platformSales+" 万元");
    $("#lineOfCreditShow").html(data.lineOfCredit+" 万元");
    $("#platformProfitPerShow").html(data.platformProfitPer+" %");
    //设备概况
    if(checkIsEmpty(data.deviceStatus)){
        var jsonarray= $.parseJSON(data.deviceStatus);
        var html = "设备概况:<br />设备类型："+ jsonarray.deviceType+"; 设备品牌: "+jsonarray.deviceBrand+"; 设备数量: "+jsonarray.deviceNum+" 台";
        $("#deviceStatusShow").html(html);
    }else{
        $("#deviceStatusShow").html("");
    }
    if(checkIsEmpty(data.otherDeviceStatus)){
        var jsonarray= $.parseJSON(data.otherDeviceStatus);
        var html = "其他设备:<br />";
        for(var i = 0; i < jsonarray.length; i++){
            html += "设备类型："+ jsonarray[i].deviceType+"; 设备品牌: "+jsonarray[i].deviceBrand+"; 设备数量: "+jsonarray[i].deviceNum+" 台<br />";
        }
        $("#otherDeviceStatusShow").html(html);
    }else{
        $("#otherDeviceStatusShow").html("");
    }

    if(checkIsEmpty(data.deviceStatus)){
        var jsonarray= $.parseJSON(data.deviceStatus);
        var html = "设备概况:<br />设备类型："+ jsonarray.deviceType+"; 设备品牌: "+jsonarray.deviceBrand+"; 设备数量: "+jsonarray.deviceNum+" 台";
        $("#deviceStatusShow").html(html);
    }else{
        $("#deviceStatusShow").html("");
    }

    if(checkIsEmpty(data.rawPurchaseAmount)){
        var jsonarray= $.parseJSON(data.rawPurchaseAmount);
        var html = "";
        for(var i = 0; i < jsonarray.length; i++){
            html += jsonarray[i].rawPurchaseYear+" 年 ; 累计"+jsonarray[i].rawPurchaseAmount+" 万元<br />";
        }
        $("#rawPurchaseAmountShow").html(html);
    }else{
        $("#rawPurchaseAmountShow").html("");
    }

    if(checkIsEmpty(data.consumPerMonth)){
        var jsonarray= $.parseJSON(data.consumPerMonth);
        var html = "";
        for(var i = 0; i < jsonarray.length; i++){
            html += jsonarray[i].consumPerMonth+" ,"+jsonarray[i].consumPerMonthAmount+" 元<br />";
        }
        $("#consumPerMonthShow").html(html);
    }else{
        $("#consumPerMonthShow").html("");
    }
}


function backToCustomer(){
    window.location.href = getRootPath() + "/economicsCustomerManage.do";

}

/***************************************************************************************************************/

function getMonth(id, index) {
    $("#" + id + index).datetimepicker({
        format: 'yyyy-mm',
        weekStart: 1,
        autoclose: true,
        startView: 3,
        minView: 3,
        forceParse: false,
        language: 'zh-CN',
        pickerPosition: "bottom-right"
    });
}



function getYear(id, index) {
    $("#" + id + index).datetimepicker({
        startView: 'decade',
        minView: 'decade',
        format: 'yyyy',
        maxViewMode: 2,
        minViewMode:2,
        autoclose: true  ,
        language: 'zh-CN',
        pickerPosition:"bottom-right"
    });

}