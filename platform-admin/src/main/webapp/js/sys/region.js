$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/region/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '上级区域', name: 'parentName', index: 'parent_id', width: 80},
            {label: '区域', name: 'name', index: 'name', width: 80},
            {
                label: '类型', name: 'type', index: 'type', width: 80,
                formatter: function (value) {
                    if (value === '0' || value === 0) {
                        return '国家';
                    }
                    if (value == '1') {
                        return '省份';
                    }
                    if (value == '2') {
                        return '地市';
                    }
                    if (value == '3') {
                        return '区县';
                    }
                }
            }],
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
        region: {type: '1'},
        ruleValidate: {
            name: [
                {required: true, message: '区域名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            parentName: '',
            name: '',
            type: ''
        },
        regionList: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.region = {type: '1'};
            vm.changeType(1);
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
            var url = vm.region.id == null ? "../sys/region/save" : "../sys/region/update";
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.region),
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
                    url: "../sys/region/delete",
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
            $.get("../sys/region/info/" + id, function (r) {
                vm.region = r.region;
                vm.changeType(vm.region.type);
            });
        },
        changeType: function (type) {
            $.get("../sys/region/getAreaByType?type=" + type, function (r) {
                vm.regionList = r.list;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'name': vm.q.name,
                    'parentName': vm.q.parentName,
                    'type': vm.q.type
                },
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
        }
    }
});