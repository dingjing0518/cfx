(function ($) {
    'use strict';
    $.extend($.fn.bootstrapTable.defaults, {
        // 默认不显示
        paginationShowPageGo: false
    });

    $.extend($.fn.bootstrapTable.locales, {
        pageGo: function () {
            // 定义默认显示文字，其它语言需要扩展
            return '跳转到';
        }
    });
    $.extend($.fn.bootstrapTable.locales, {
        page: function () {
            // 定义默认显示文字，其它语言需要扩展
            return '页';
        }
    });
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales);

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initPagination = BootstrapTable.prototype.initPagination;

    // 扩展已有的初始化分页组件的方法
    BootstrapTable.prototype.initPagination = function() {
        _initPagination.apply(this, Array.prototype.slice.apply(arguments));
        // 判断是否显示跳转到指定页码的组件
        if(this.options.paginationShowPageGo){
            var html = [];
           var origionNumber = this.options.pageNumber;
            // 渲染跳转到指定页的元素
            html.push(
                '<ul class="pagination-jump">',
                '<li class=""><span>' + this.options.pageGo() + ':</span></li>',
                '<li class=""><input type="text" id="inputId" class="page-input" value="' + this.options.pageNumber + '"   /></li>',
                '<li class=""><input type="hidden" id="inputHiddenId" value="' + this.options.totalPages + '"   /></li>',
                '<li class=""><span>' + this.options.page() + '</span></li>',
                '<li class="page-go"><a class="jump-go" href="">跳转</a></li>',
                '</ul>');

            // 放到原先的分页组件后面
            this.$pagination.find('ul.pagination').after(html.join(''));
            // 点击按钮触发跳转到指定页函数
            this.$pagination.find('.page-go').off('click').on('click', $.proxy(this.onPageGo, this));
            
            this.$pagination.find('.page-input').off('keyup').on('keyup', $.proxy(this.onPageGo, this));
            
            // 手动输入页码校验，只允许输入正整数
            this.$pagination.find('.page-input').off('blur').on('blur', function(){

            //this.value = this.value.length == 1 ? this.value.replace(/[^1-9]/g,'') : this.value.replace(/\D/g,'');
            });
        }
    };

    // 自定义跳转到某页的函数
    BootstrapTable.prototype.onPageGo = function (event) {
        // 获取手动输入的要跳转到的页码元素
        var $toPage=this.$pagination.find('.page-input');
        var toPage = +$toPage.val();
        var allPages = this.options.totalPages;//获取全部分页页数
        
        if(toPage > allPages || toPage == 0){
            new $.flavr({
                modal : false,
                content : "输入的页码不在当前页码范围内或输入为空！"
            });
            return false;
        }
        
        if(!isNotZeroDNumber(toPage)){
            new $.flavr({
                modal : false,
                content : "请输入正整数页码！"
            });
            return false;
        }
        // 当前页不做处理
        if (this.options.pageNumber === toPage) {
            return false;
        }
        //点击按钮和Enter都会触发
        if(event.type == 'click' || event.keyCode == 13){
        	// 调用官方的函数
            this.selectPage(toPage);
        }
        return false;
    };
})(jQuery);
