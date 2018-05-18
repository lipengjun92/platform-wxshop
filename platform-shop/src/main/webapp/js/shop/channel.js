$(function () {
    $("#jqGrid").jqGrid({
        url: '../channel/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '名称', name: 'name', index: 'name', width: 80},
            {label: 'url', name: 'url', index: 'url', width: 80},
            {
                label: 'iconUrl', name: 'iconUrl', index: 'icon_url', width: 80, formatter: function (value) {
                return transImg(value);
            }
            },
            {label: '排序', name: 'sortOrder', index: 'sort_order', width: 80}],
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
        channel: {},
        ruleValidate: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ],
            url: [
                {required: true, message: 'url不能为空', trigger: 'blur'}
            ],
            iconUrl: [
                {required: true, message: 'icon链接不能为空', trigger: 'blur'}
            ]
        },
        q: {
            name: ''
        },
        categoryList: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.channel = {};
            vm.getParentCategory();
        },
        getParentCategory: function () {
            $.get("../category/getCategorySelect", function (r) {
                vm.categoryList = r.list;
            });
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
            vm.getParentCategory();
        },
        saveOrUpdate: function (event) {
            var url = vm.channel.id == null ? "../channel/save" : "../channel/update";
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.channel),
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
                    url: "../channel/delete",
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
            $.get("../channel/info/" + id, function (r) {
                vm.channel = r.channel;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
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
        handleFormatError: function (file) {
            this.$Notice.warning({
                title: '文件格式不正确',
                desc: '文件 ' + file.name + ' 格式不正确，请上传 jpg 或 png 格式的图片。'
            });
        },
        handleMaxSize: function (file) {
            this.$Notice.warning({
                title: '超出文件大小限制',
                desc: '文件 ' + file.name + ' 太大，不能超过 10k。'
            });
        },
        handleSuccessIconUrl: function (res, file) {
            vm.channel.iconUrl = file.response.url;
        },
        eyeImageIconUrl: function () {
            var url = vm.channel.iconUrl;
            eyeImage(url);
        }
    }
});