$(function () {
    let userId = getQueryString("userId");
    let url = '../address/list';
    if (userId) {
        url += '?userId=' + userId;
    }
    $("#jqGrid").jqGrid({
        url: url,
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '会员', name: 'shopUserName', index: 'user_id', width: 90},
            {label: '收货人姓名', name: 'userName', index: 'user_name', width: 80},
            {label: '手机', name: 'telNumber', index: 'tel_number', width: 80},
            {label: '收货地址国家码', name: 'nationalCode', index: 'national_Code', width: 80},
            {label: '省', name: 'provinceName', index: 'province_Name', width: 80},
            {label: '市', name: 'cityName', index: 'city_Name', width: 80},
            {label: '区', name: 'countyName', index: 'county_Name', width: 80},
            {label: '详细收货地址信息', name: 'detailInfo', index: 'detail_Info', width: 150},
            {label: '邮编', name: 'postalCode', index: 'postal_Code', width: 80}],
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
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        q: {
            userName: '',
            telNumber: ''
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'userName': vm.q.userName,
                    'telNumber': vm.q.telNumber
                },
                page: page
            }).trigger("reloadGrid");
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../address/delete",
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
        }
    }
});