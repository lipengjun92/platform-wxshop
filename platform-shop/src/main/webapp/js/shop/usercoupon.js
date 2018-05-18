$(function () {
    let userId = getQueryString("userId");
    let url = '../usercoupon/list';
    if (userId) {
        url += '?userId=' + userId;
    }
    $("#jqGrid").jqGrid({
        url: url,
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '会员', name: 'userName', index: 'user_id', width: 80},
            {label: '优惠券', name: 'couponName', index: 'coupon_id', width: 80},
            {label: '优惠券序号', name: 'couponNumber', index: 'coupon_number', width: 80},
            {
                label: '下发时间', name: 'addTime', index: 'add_time', width: 80, formatter: function (value) {
                return transDate(value);
            }
            },
            {
                label: '使用时间', name: 'usedTime', index: 'used_time', width: 80, formatter: function (value) {
                return transDate(value);
            }
            },
            {label: '订单Id', name: 'orderId', index: 'order_id', width: 80}],
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

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        userCoupon: {},
        q: {
            userName: '',
            couponName: ''
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.userCoupon = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.userCoupon.id == null ? "../usercoupon/save" : "../usercoupon/update";
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.userCoupon),
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
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../usercoupon/delete",
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
            $.get("../usercoupon/info/" + id, function (r) {
                vm.userCoupon = r.userCoupon;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'userName': vm.q.userName, 'couponName': vm.q.couponName},
                page: page
            }).trigger("reloadGrid");
        }
    }
});