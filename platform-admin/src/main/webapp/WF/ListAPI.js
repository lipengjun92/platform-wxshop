/*
 * 说明:
 * 1. 该API 是被发起、待办、在途三个菜单列表页面引入，并获取的数据的API。
 * 2. 其中需要  config.js 获得 ccbpmHostDevelopAPI 定义的服务器IP. 
 * 3. 需要cookies 中的sid 校验码(token).
 * 4. 获取流程发起列表: DB_Start()
 * 5. 获得待办 DB_Todolist()
 * 6. 获得在途 DB_Runing()
 * 7. 打开表单 OpenForm() 发起、待办、在途三个列表都需要打开表单.
 */

//获得发起列表.
function DB_Start() {
     
    var myurl = ccbpmHostDevelopAPI + "?DoType=DB_Start&Token=" + GetToken();
    return RunUrlReturnJSON(myurl);
}

/**
 * 1.获得待办. 返回的Json数据源.
 * 2.列: Title,WorkID,FK_Flow,FK_Node .
 * 3.获得该数据源,调用
 * */
function DB_Todolist() {
     
    var myurl = ccbpmHostDevelopAPI + "?DoType=DB_Todolist&Token=" + GetToken() + "&t=" + new Date().getTime();
    return RunUrlReturnJSON(myurl);
}

//获得在途.
function DB_Runing() {
     
    var myurl = ccbpmHostDevelopAPI + "?DoType=DB_Runing&Token=" + GetToken();
    return RunUrlReturnJSON(myurl);
}

//获得草稿.
function DB_Draft() {

   
    var myurl = ccbpmHostDevelopAPI + "?DoType=DB_Draft&Token=" + GetToken();
    return RunUrlReturnJSON(myurl);
}

/**
 * 打开表单， 如果是仅仅传入的是FlowNo就是启动流程.
 * @param {any} flowNo
 * @param {any} nodeID
 * @param {any} workid
 * @param {any} fid
 * @param {any} paras
 */
function OpenForm(flowNo, nodeID, workid, fid, paras) {
    var url = GenerFrmUrl(flowNo, nodeID, workid, fid, paras);
    window.open(url);
}

/**
 * 获得表单的 URL.
 * 该表单的URL存储在开始节点表单方案里.
 * @param {流程编号} flowNo
 * @param {节点ID默认为0} nodeID
 * @param {实例的ID} workid
 * @param {默认为:0} fid
 * @param {参数:格式为 &KeHuBianHao=001&KeHuMingCheng=新疆天业} paras
 */
function GenerFrmUrl(flowNo, nodeID = 0, workid = 0, fid = 0, paras = "") {

    // ccbpmHostDevelopAPI 变量是定义在 /config.js 的服务地址. 访问必须两个参数DoWhat,SID.
    //首先获得表单的URL.
    var myUrl = ccbpmHostDevelopAPI + "?DoType=GenerFrmUrl&Token=" + GetToken() + "&WorkID=" + workid + "&FK_Flow=" + flowNo + "&FK_Node=" + nodeID + "&FID=" + fid;
    var frmUrl = RunUrlReturnString(myUrl);
    frmUrl += paras;

    //if (frmUrl.indexOf('?') == -1)
    //    frmUrl += "?WorkID=" + workid + "&FK_Flow=" + flowNo + "&FK_Node=" + nodeID + "&FID=" + fid + paras + "&Token=" + sid;
    //else
    //    frmUrl += "&WorkID=" + workid + "&FK_Flow=" + flowNo + "&FK_Node=" + nodeID + "&FID=" + fid + paras + "&Token=" + sid;

    //如果包含了通用的工作处理器.
    if (frmUrl.indexOf("WF/MyFlow.htm") >= 0) {
        frmUrl = host + frmUrl;
    }

    return frmUrl;
    //return frmUrl;
}
