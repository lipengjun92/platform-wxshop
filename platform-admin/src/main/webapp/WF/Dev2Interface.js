/* 开发接口JS: 需要引入 /WF/config.js . 
 * 1. 该文件里提供了一些高级开发接口,
 * 2. 比如：创建WorkID,执行发送,催办. 批量删除.
 * 3. 每个接口都有明确的注释.
 */


/**
 * 创建空白的WorkID.
 * @param {校验码(登录时候产生的)} sid
 * @param {流程编号} flowNo
 */
function Node_CreateBlankWorkID( flowNo) {
    var url = ccbpmHostDevelopAPI + "?DoType=Node_CreateBlankWorkID&Token=" + GetToken() + "&FK_Flow=" + flowNo;
    return RunUrlReturnString(url);
}

/**
 * 执行发送
 * @param {校验码} sid
 * @param {流程编号} flowNo
 * @param {节点ID} nodeID
 * @param {工作实例ID} workid
 * @param {要达到的节点,为0不指定节点,由节点配置自动计算} toNodeID
 * @param {要发送的人员，为null,不指定人员，由流程配置自动计算} toEmps
 * @param {参数，格式为:@Key1=val1@Key2=val2 } paras
 */
function Node_SendWork( flowNo, nodeID, workid, toNodeID, toEmps, paras = "") {

    paras = paras.replace('@', '&');

    var url = ccbpmHostDevelopAPI + "?DoType=Node_SendWork&Token=" + GetToken() + "&FK_Flow=" + flowNo + "&FK_Node=" + nodeID;
    url += "&WorkID=" + workid + "&ToNodeID=" + toNodeID;
    url += "&ToEmps=" + toEmps + "&1=2" + paras;
    return RunUrlReturnString(url);
}

/**
 * 获得可以退回的节点
 * @param {校验码} sid
 * @param {流程编号} flowNo
 * @param {工作实例ID} workid
 * @param {FID} fid
 */
function DB_GenerWillReturnNodes( flowNo, workid, fid = 0) {

    var url = ccbpmHostDevelopAPI + "?DoType=DB_GenerWillReturnNodes&Token=" + GetToken() + "&FK_Flow=" + flowNo;
    url += "&WorkID=" + workid + "&FID=" + fid;
    return RunUrlReturnString(url);
}

/**
 * 退回
 * @param {校验码} sid
 * @param {工作实例ID} workid
 * @param {退回到节点ID} returnToNodeID
 * @param {退回给人员} returnToEmp
 * @param {退回意见} msg
 * @param {是否原路返回?} isBackToThisNode
 */
function Node_ReturnWork( workid, returnToNodeID, returnToEmp, msg, isBackToThisNode = false) {
    var url = ccbpmHostDevelopAPI + "?DoType=Node_ReturnWork&Token=" + GetToken();
    url += "&WorkID=" + workid;
    url += "&ReturnToNodeID=" + returnToNodeID;
    url += "&ReturnToEmp=" + returnToEmp;
    url += "&Msg=" + msg;

    if (isBackToThisNode == true)
        url += "&IsBackToThisNode=1";
    else
        url += "&IsBackToThisNode=0";
    return RunUrlReturnString(url);
}

/**
 * 催办
 * @param {要执行的实例,多个实例用逗号分开比如：1001,1002,1003} workidStrs
 */
function Flow_DoPress( workidStrs,msg) {
    var url = ccbpmHostDevelopAPI + "?DoType=Flow_DoPress&Token=" + GetToken();
    url += "&WorkIDs=" + workidStrs;
    url += "&Msg=" + msg;
    return RunUrlReturnString(url);
}

/**
 * 撤销发送,如果产生失败就会返回 err@+失败信息.
 * @param {要执行的实例,多个实例用逗号分开比如：1001,1002,1003} workidStrs
 */
function Flow_DoUnSend( workidStrs) {

    var url = ccbpmHostDevelopAPI + "?DoType=Flow_DoUnSend&Token=" + GetToken();
    url += "&WorkIDs=" + workidStrs;
    return RunUrlReturnString(url);
}

/**
 * 删除流程
 * @param {要删除的实例,多个实例用逗号分开比如：1001,1002,1003} workidStrs
 * @param {是否删除子流程} isDeleteSubFlows
 */
function Flow_BatchDeleteByReal( workidStrs, isDeleteSubFlows = true) {

    var url = ccbpmHostDevelopAPI + "?DoType=Flow_BatchDeleteByReal&Token=" + GetToken();
    url += "&WorkIDs=" + workidStrs;

    if (isDeleteSubFlows == false)
        url += "&IsDeleteSubFlows=0";
    else
        url += "&IsDeleteSubFlows=1";
    return RunUrlReturnString(url);
}
/**
 * 恢复删除
 * @param {any} workidStrs
 */
function Flow_BatchDeleteByFlagAndUnDone( workidStrs) {

    var url = ccbpmHostDevelopAPI + "?DoType=Flow_BatchDeleteByFlagAndUnDone&Token=" + GetToken();
    url += "&WorkIDs=" + workidStrs;

    return RunUrlReturnString(url);
}



/**
 * 设置流程结束
 * @param {要执行的实例,多个实例用逗号分开比如：1001,1002,1003} workidStrs
 */
function Flow_DoFlowOver(sid, workidStrs) {

    var url = ccbpmHostDevelopAPI + "?DoType=Flow_DoFlowOver&Token=" + GetToken();
    url += "&WorkIDs=" + workidStrs;
    return RunUrlReturnString(url);
}

/**
 * 批量设置抄送查看完毕
 * @param {any} workidStrs
 */

function CC_BatchCheckOver( workidStrs) {

    var url = ccbpmHostDevelopAPI + "?DoType=CC_BatchCheckOver&Token=" + GetToken();
    url += "&WorkIDs=" + workidStrs;

    return RunUrlReturnString(url);
}


/**
 * 删除草稿
 * @param {要执行的实例,多个实例用逗号分开比如：1001,1002,1003} workidStrs
 */
function Flow_DeleteDraft(sid, workidStrs) {

    var url = ccbpmHostDevelopAPI + "?DoType=Flow_DeleteDraft&Token=" + GetToken();
    url += "&WorkIDs=" + workidStrs;
    return RunUrlReturnString(url);
}

