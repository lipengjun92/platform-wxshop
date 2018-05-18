$(function () {
    let goodsId = getQueryString("goodsId");
    let url = '../product/list';
    if (goodsId) {
        url += '?goodsId=' + goodsId;
    }
    $("#jqGrid").jqGrid({
        url: url,
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '商品', name: 'goodsName', index: 'goods_id', width: 120},
            {
                label: '商品规格',
                name: 'specificationValue',
                index: 'goods_specification_ids',
                width: 100,
                formatter: function (value, options, row) {
                    return value.replace(row.goodsName + " ", '');
                }
            },
            {label: '商品序列号', name: 'goodsSn', index: 'goods_sn', width: 80},
            {label: '商品库存', name: 'goodsNumber', index: 'goods_number', width: 80},
            {label: '零售价格(元)', name: 'retailPrice', index: 'retail_price', width: 80},
            {label: '市场价格(元)', name: 'marketPrice', index: 'market_price', width: 80}],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

let vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        product: {},
        ruleValidate: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            goodsName: ''
        },
        goodss: [],
        attribute: [],
        color: [], guige: [], weight: [],
        colors: [],
        guiges: [],
        weights: [],
        type: ''
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.product = {};
            vm.getGoodss();
            vm.type = 'add';
        },
        update: function (event) {
            let id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.type = 'update';

            vm.getInfo(id)
        },
        changeGoods: function (opt) {
            let goodsId = opt.value;
            $.get("../goods/info/" + goodsId, function (r) {
                if (vm.type == 'add') {
                    vm.product.goodsSn = r.goods.goodsSn;
                    vm.product.goodsNumber = r.goods.goodsNumber;
                    vm.product.retailPrice = r.goods.retailPrice;
                    vm.product.marketPrice = r.goods.marketPrice;
                }
                $.get("../goodsspecification/queryAll?goodsId=" + goodsId + "&specificationId=1", function (r) {
                    vm.colors = r.list;
                });
                $.get("../goodsspecification/queryAll?goodsId=" + goodsId + "&specificationId=2", function (r) {
                    vm.guiges = r.list;
                });
                $.get("../goodsspecification/queryAll?goodsId=" + goodsId + "&specificationId=4", function (r) {
                    vm.weights = r.list;
                });
            });
        },
        saveOrUpdate: function (event) {
            let url = vm.product.id == null ? "../product/save" : "../product/update";
            vm.product.goodsSpecificationIds = vm.color + '_' + vm.guige + '_' + vm.weight;
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.product),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            let ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../product/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get("../product/info/" + id, function (r) {
                vm.product = r.product;
                vm.getGoodss();
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'goodsName': vm.q.goodsName},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        },
        getGoodss: function () {
            $.get("../goods/queryAll/", function (r) {
                vm.goodss = r.list;
            });
        }
    }
});