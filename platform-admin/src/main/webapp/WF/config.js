
var domain = "";

// for java
var host = "http://127.0.0.1:8080/jflow-web";
// for java
var ccbpmHostDevelopAPI = host + "/DataUser/DevelopAPI/ProcessRequest";
//访问的ccbpm服务器地址.
// var host = "http://140.143.236.168:8078";
// //驰骋BPM流程服务器地址.
// var ccbpmHostDevelopAPI = host + "/DataUser/DevelopAPI.ashx";
//私钥. 这里明文定义到这里了, 为了安全需要写入到后台.
var PrivateKey = "di gua di gua,i am ccbpm";

var UserNo = null;
/**
 * 执行登录：返回SID.
 * 私约是本地服务器与BPM服务器双方约定的一个字符串.
 * BPM服务配置在 java的jflow.properties .net 的Web.config，
 *
 * 本地应用系统在登录成功后，访问bpm服务器登录的时候传入 私约 + 用户编号，让其登录并且返回一个 sid (与token概念一样)。
 * 您获得这个SID后，记住它，并且在访问的时候，使用UserNo+SID来访问ccbpm的功能页面。
 * 为了安全期间：您可以把这个获得sid的方法放入后台实现。
 *
 * @param {私钥} privateKey
 * @param {用户账号} userNo
 */
function LoginCCBPM(privateKey, userNo) {

    //url 地址。
    var url = ccbpmHostDevelopAPI + "?DoWhat=Portal_Login_Submit&PrivateKey=" + privateKey + "&UserNo=" + userNo;
    var token = RunUrlReturnString(url);

    //赋值给公共变量.
    UserNo = userNo;
    return token;
}
/**
 * 获得当前的用户.
 * */
function GetUserNo() {
    if (UserNo != null)
        return UserNo;

    var userNo = GetQueryString("UserNo");
    if (userNo == null || userNo == undefined) {

        var parent = window.parent;
        if (parent == null || parent == undefined) {
            UserNo = null;
            return null;
        }

        var url = parent.location.href;
        token = getQueryStringByNameFromUrl(url, "UserNo");
        if (token == null || token == undefined) {
            UserNo = null;
            return null;
        }
    }

    UserNo = userNo;
    return userNo;
}
//到达后台.
function GotoCCBPMAdmin() {
    var userNo = GetUserNo();
    if (userNo == null || userNo != 'admin') {
        alert("非管理员用户不能登录。");
        return;
    }
    var url = host + "/WF/Admin/Portal/Login.htm?SID=" + GetToken() + "&UserNo=" + GetUserNo();
    window.open(url);
}
/**
 * 退出登录
 * */
function LoginOut() {

    //url 地址。
    var url = ccbpmHostDevelopAPI + "?DoWhat=Portal_LoginOut&Token=" + GetToken();
    var token = RunUrlReturnString(url);

    //赋值给公共变量
    UserNo = "";
}
/**
 * 执行URL转化为json对象.转化失败为null.
 * @param {url} url
 */
function RunUrlReturnJSON(url) {

    var str = RunUrlReturnString(url);
    if (str == null) return null;

    try {
        return JSON.parse(str);
    } catch (e) {
        alert("json解析错误: " + str);
        return null;
    }
}

/**
 * 执行URL返回string.
 * @param {any} url
 */
function RunUrlReturnString(url) {

    if (url == null || typeof url === "undefined") {
        alert("err@url无效");
        return;
    }

    var string;

    $.ajax({
        type: 'post',
        async: false,
        url: url,
        dataType: 'html',
        xhrFields: {
            withCredentials: IsIELower10 == true ? false : true
        },
        crossDomain: IsIELower10 == true ? false : true,
        success: function (data) {
            if (data.indexOf("err@") != -1) {
                alert(data);
                return;
            }
            string = data;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(url);
            alert("HttpHandler-RunUrlCrossReturnString-", textStatus);
        }
    });
    return string;
}

function basePath() {
    //获取当前网址，如： http://localhost:80/jflow-web/index.jsp  
    var curPath = window.document.location.href;
    //获取主机地址之后的目录，如： jflow-web/index.jsp
    var pathName = window.document.location.pathname;
    if (pathName == "/") { //说明不存在项目名
        if ("undefined" != typeof ccbpmPath && ccbpmPath != null && ccbpmPath != "") {
            if (ccbpmPath != curPath)
                return ccbpmPath;
        }
        return curPath;
    }
    var pos = curPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:80  
    var localhostPath = curPath.substring(0, pos);
    //获取带"/"的项目名，如：/jflow-web
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

    if ("undefined" != typeof ccbpmPath && ccbpmPath != null && ccbpmPath != "") {
        if (ccbpmPath != localhostPath)
            return ccbpmPath;
    }

    return localhostPath;
}


var IsIELower10 = false;

var ver = IEVersion();
if (ver == 6 || ver == 7 || ver == 8 || ver == 9)
    IsIELower10 = true;

function IEVersion() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if (isIE) {
        if (document.documentMode) return document.documentMode;
    } else if (isEdge) {
        return 'edge';//edge
    } else if (isIE11) {
        return 11; //IE11  
    } else {
        return -1;//不是ie浏览器
    }
}


/**
* 动态异步加载JS的方法
* @param {any} url 加载js的路径
* @param {any} callback 加载完成后的回调函数
*/
function loadScript(url, callback, scriptID) {
    var script = document.createElement("script");
    script.type = "text/javascript";
    if (callback != null && typeof (callback) != "undefined") {
        if (script.readyState) {
            script.onreadystatechange = function () {
                if (script.readyState == "loaded" || script.readyState == "complete") {
                    script.onreadystatechange = null;
                    callback();
                }
            };
        } else {
            script.onload = function () {
                callback();
            };
        }
    }
    script.src = url;
    if (scriptID != null && scriptID != undefined)
        script.id = scriptID;
    // document.head.appendChild(script);
    var tmp = document.getElementsByTagName('script')[0];
    tmp.parentNode.insertBefore(script, tmp);
}

function GetToken() {
    var token = GetQueryString("Token");
    if (token == null || token == undefined) {

        var msg = "提示:";
        msg += "\t\n 1. 没有获得当前页面的token数据.";
        msg += "\t\n 2. 该token是在登录的时候产生的，您需要把token用参数的方式传入到每个要执行的页面。";
        msg += "\t\n 3. 我们在2021年7.1取消对sid的概念，使用token 并且token不在写入cookes. ";

        var parent = window.parent;
        if (parent == null || parent == undefined) {
            alert(msg);
            return;
        }

        var url = parent.location.href;
        token = getQueryStringByNameFromUrl(url, "Token");
        if (token == null || token == undefined) {
            alert(msg);
            return;
        }
    }
    return token;
}

/**
 * 父页面监听子页面调用方法
 */
window.addEventListener('message', function (event) {
    var data = event.data;
    var info = event.data.info;
    if (true) {
        switch (data.action) {
            case 'returnWorkWindowClose':
                if (typeof returnWorkWindowClose != 'undefined' && returnWorkWindowClose instanceof Function)
                    returnWorkWindowClose(info);
                break;
            case 'WindowCloseReloadPage':
                if (typeof WindowCloseReloadPage != 'undefined' && WindowCloseReloadPage instanceof Function)
                    WindowCloseReloadPage(info);
                break;
            default:
                break;
        }
    }
}, false);



