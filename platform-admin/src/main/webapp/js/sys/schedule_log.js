$(function () {
    $("#jqGrid").Grid({
        url: '../sys/scheduleLog/list',
        colModel: [
            {label: '日志ID', name: 'logId', key: true, hidden: true},
            {label: '任务ID', name: 'jobId', width: 50},
            {label: 'bean名称', name: 'beanName', width: 60},
            {label: '方法名称', name: 'methodName', width: 60},
            {label: '参数', name: 'params', width: 60},
            {
                label: '状态', name: 'status', width: 50, formatter: function (value, options, row) {
                    return value === 0 ?
                        '<span class="label label-success">成功</span>' :
                        '<span class="label label-danger pointer" onclick="vm.showError(' + row.logId + ')">失败</span>';
                }
            },
            {label: '耗时(单位：毫秒)', name: 'times', width: 70},
            {
                label: '执行时间', name: 'createTime', width: 80, formatter: function (value) {
                    return transDate(value);
                }
            }
        ]
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            jobId: null
        }
    },
    methods: {
        query: function () {
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'jobId': vm.q.jobId},
                page: 1
            }).trigger("reloadGrid");
        },
        showError: function (logId) {
            $.get("../sys/scheduleLog/info/" + logId, function (r) {
                openWindow({
                    title: '失败信息',
                    closeBtn: 0,
                    content: r.log.error
                });
            });
        },
        back: function (event) {
            history.go(-1);
        }
    }
});

