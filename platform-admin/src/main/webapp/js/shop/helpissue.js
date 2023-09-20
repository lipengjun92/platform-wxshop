$(function () {
    $("#jqGrid").Grid({
        url: '../helpissue/list',
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '问题分类', name: 'typeName', index: 'type_id', width: 80},
            {label: '问', name: 'question', index: 'question', width: 80},
            {label: '答', name: 'answer', index: 'answer', width: 80},
            {label: '排序', name: 'sort', index: 'sort', width: 80}]
    });
});

let vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        helpIssue: {},
        ruleValidate: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            typeName: ''
        },
        helpTypes: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.helpIssue = {};

            vm.getHelpType();
        },
        update: function (event) {
            let id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id);
        },
        saveOrUpdate: function (event) {
            let url = vm.helpIssue.id == null ? "../helpissue/save" : "../helpissue/update";
            Ajax.request({
                type: "POST",
                url: url,
                contentType: "application/json",
                params: JSON.stringify(vm.helpIssue),
                successCallback: function (r) {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });
        },
        del: function (event) {
            let ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    type: "POST",
                    url: "../helpissue/delete",
                    contentType: "application/json",
                    params: JSON.stringify(ids),
                    successCallback: function (r) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    }
                });
            });
        },
        getInfo: function (id) {
            Ajax.request({
                url: "../helpissue/info/" + id,
                async: true,
                successCallback: function (r) {
                    vm.helpIssue = r.helpIssue;
                    vm.getHelpType();
                }
            });
        },
        reloadSearch: function () {
            vm.q = {
                typeName: ''
            };
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'typeName': vm.q.typeName},
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
        getHelpType: function () {
            Ajax.request({
                url: "../helptype/queryAll",
                async: true,
                successCallback: function (r) {
                    vm.helpTypes = r.list;
                }
            });
        }
    }
});