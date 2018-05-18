$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/oss/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', key: true, hidden: true},
            {label: 'URL地址', name: 'url', width: 160},
            {
                label: '创建时间', name: 'createDate', width: 40, formatter: function (value) {
                return transDate(value);
            }
            }
        ],
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

    new AjaxUpload('#upload', {
        action: '../sys/oss/upload',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (vm.config.type == null) {
                alert("云存储配置未配置");
                return false;
            }
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r.code == 0) {
                alert(r.url);
                vm.reload();
            } else {
                alert(r.msg);
            }
        }
    });

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        config: {},
        aliRuleValidate: {
            aliyunDomain: [
                {required: true, message: '阿里云绑定的域名不能为空', trigger: 'blur'}
            ],
            aliyunAccessKeyId: [
                {required: true, message: '阿里云AccessKeyId不能为空', trigger: 'blur'}
            ],
            aliyunAccessKeySecret: [
                {required: true, message: '阿里云AccessKeySecret不能为空', trigger: 'blur'}
            ],
            aliyunBucketName: [
                {required: true, message: '阿里云BucketName不能为空', trigger: 'blur'}
            ]
        },
        qcloudRuleValidate: {
            qcloudDomain: [
                {required: true, message: '腾讯云绑定的域名不能为空', trigger: 'blur'}
            ],
            qcloudAppId: [
                {required: true, message: '腾讯云AppId不能为空', trigger: 'blur'}
            ],
            qcloudSecretId: [
                {required: true, message: '腾讯云SecretId不能为空', trigger: 'blur'}
            ],
            qcloudSecretKey: [
                {required: true, message: '腾讯云SecretKey不能为空', trigger: 'blur'}
            ],
            qcloudBucketName: [
                {required: true, message: '腾讯云BucketName不能为空', trigger: 'blur'}
            ],
            qcloudRegion: [
                {required: true, message: 'Bucket所属地区不能为空', trigger: 'blur'}
            ]
        }
    },
    created: function () {
        this.getConfig();
    },
    methods: {
        query: function () {
            vm.reload();
        },
        getConfig: function () {
            $.getJSON("../sys/oss/config", function (r) {
                vm.config = r.config;
            });
        },
        addConfig: function () {
            vm.showList = false;
            vm.title = "云存储配置";
        },
        saveOrUpdate: function () {
            var url = "../sys/oss/saveConfig";
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.config),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function () {
            var ossIds = getSelectedRows();
            if (ossIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../sys/oss/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ossIds),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        lookImg: function () {
            var grid = $("#jqGrid");
            var id = grid.jqGrid('getGridParam', 'selrow');//根据点击行获得点击行的id（id为jsonReader: {id: "id" },）
            if (!id) {
                alert("请选择一条记录");
                return;
            }
            var ids = grid.jqGrid('getGridParam', 'selarrrow');
            var data = [];
            for (var i = 0; i < ids.length; i++) {
                id = ids[i];
                var rowData = grid.jqGrid("getRowData", id);
                var url = rowData.url;
                data.push({"src": url});
            }

            eyeImages(data);
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
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