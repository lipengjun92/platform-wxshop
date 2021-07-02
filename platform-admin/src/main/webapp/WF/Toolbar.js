/*
 * 工具栏按钮
 * 1. 当您需要使用sdk模式的表单开发的时候，如果引用该js，您就可以安心专注于业务的开发.
 * 2. 您需要在您的页面里增加一个 id=Toolbar 的div. 请参考：/AppDemo/Frms/F001QingJia.htm 
 * 3. 您还需要在该页面里增加一个Save()的方法，用于保存数据，如果保存成功就return true, 失败就return false.
 * 4. 要禁用或者启用那些按钮，您需要在流程中心中设置节点属性按钮权限控制即可.
 */
//页面启动函数.
$(function () {

    var sid = $.cookie("MySID");
    if (sid == null || sid == '') {
        alert('登录信息丢失，请重新登录.');
        return;
    }
   
    //引入CSS样式
    $('head').append('<link href="' + host + '/DataUser/Style/CSS/Default/ccbpm.css" rel="stylesheet" type="text/css" />');
    $('head').append('<link href="' + host + '/DataUser/Style/MyFlow.css" rel="stylesheet" type="text/css" />');

    var JSUrl = host + "/WF/ccbpm.js?SID=" + sid + "&type=MyGener&Version=" + Math.random();
    loadScript(JSUrl, null, "ccbpmJS");

    //引入BootStrap的JS
    var basePathStr = basePath();
    $('head').append('<link href="' + basePathStr + '/WF/Scripts/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />');
    $('head').append('<link rel="stylesheet" href="' + basePathStr + '/WF/Scripts/bootstrap/css/css.css" type="text/css" media="all" />');
    loadScript(basePathStr + "/WF/Scripts/bootstrap/js/bootstrap.min.js",null);
    loadScript(basePathStr + "/WF/Scripts/bootstrap/BootstrapUIDialog.js",null);
    return;

});



