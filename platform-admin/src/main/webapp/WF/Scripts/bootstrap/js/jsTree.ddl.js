var JsTree = function () {
    var bindTree = function (control, dataUrl, loadedfunction, selectfunction) {
        //复选框树的初始化
        control.jstree({
            "plugins": ["themes", "json_data", ],
            "core": {
                "data": {
                    'url': function (node) {
                        return dataUrl;
                    },
                    'data': function (node) {
                        return { 'id': node.id };
                    }
                }
            }
        }).bind('loaded.jstree', loadedfunction)
         .bind('select_node.jstree', selectfunction);
    }
    var bindCheckTree = function (control, dataUrl,three_state, loadedfunction, selectfunction) {
        control.jstree({
            "plugins": ["themes", "json_data", "checkbox"],
            'checkbox': { cascade: "", three_state: three_state }, //不级联
            "core": {
                "data": {
                    'url': function (node) {
                        return dataUrl;
                    },
                    'data': function (node) {
                        return { 'id': node.id };
                    }
                }
            }
        }).bind('loaded.jstree', loadedfunction)
        .bind('select_node.jstree', selectfunction);
        //changed
    }

    var tree;
    return {
        //main function to initiate the module
        init: function (treeName, dataUrl, check,three_state, loadedfunction, selectfunction) {
            tree = $('#' + treeName);
            tree.data('jstree', false);//清空数据，必须
            if (check) {
                bindCheckTree(tree, dataUrl,three_state, loadedfunction, selectfunction);
            } else {
                bindTree(tree, dataUrl, loadedfunction, selectfunction);
            }
        },
        //获取checkbox 选中的id
        GetChecked: function (treeName) {
            var jsTree = $('#' + treeName);
            var str = jsTree.jstree("get_checked");
            var checkeds = str.join(",");
            return checkeds;
        }
    };
}();