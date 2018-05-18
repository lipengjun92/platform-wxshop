$(function () {
    let status = getQueryString("status");
    let url = '../comment/list';
    if (status) {
        url += '?status=' + status;
    }
    debugger
    $("#jqGrid").jqGrid({
        url: url,
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '类型', name: 'typeId', index: 'type_id', width: 80},
            {label: '商品', name: 'valueName', index: 'value_id', width: 80},
            {label: '评价', name: 'content', index: 'content', width: 80},
            {
                label: '评论时间', name: 'addTime', index: 'add_time', width: 80, formatter: function (value) {
                return transDate(value);
            }
            },
            {
                label: '状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                if (value === 0) {
                    return '<span class="label label-success">显示</span>';
                }
                return '<span class="label label-danger">隐藏</span>';
            }
            },
            {label: '会员', name: 'userName', index: 'user_id', width: 80}],
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
        comment: {id: ''},
        q: {
            userName: '',
            valueName: '',
            picUrl: ''
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        toggleStatus: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.comment.id = id;

            confirm('确定要切换状态？', function () {
                $.ajax({
                    type: "POST",
                    url: "../comment/toggleStatus",
                    contentType: "application/json",
                    data: JSON.stringify(vm.comment),
                    success: function (r) {
                        if (r.code === 0) {
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
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../comment/delete",
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
        seePic: function () {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            $.get("../commentpicture/queryAll?commentId=" + id, function (r) {
                var data = [];
                for (var i = 0; i < r.list.length; i++) {
                    var picUrl = r.list[i].picUrl;
                    data.push({"src": picUrl});
                }
                eyeImages(data);
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'userName': vm.q.userName, 'valueName': vm.q.valueName, 'picUrl': vm.q.picUrl},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        }
    }
});