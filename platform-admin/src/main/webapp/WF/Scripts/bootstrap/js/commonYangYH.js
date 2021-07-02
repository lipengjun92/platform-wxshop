//格式化日期的函数 yyyy:年 MM:月 dd:日 HH:小时 mm:分钟 ss:秒 DAY:星期几 算是Date的一个扩展方法
$(function () {
    Date.prototype.FormateDate = function (formateStr) {
        //return formateStr.replace("yyyy", this.getFullYear()).replace("MM",this.getMonth()<9?'0'+ (this.getMonth() + 1):(this.getMonth() + 1)).replace("dd", this.getDate()).replace("HH", this.getHours()<10?'0'+this.getHours():this.getHours()).replace("mm", this.getMinutes()<10?'0'+this.getMinutes():this.getMinutes()).replace("ss", this.getMilliseconds()<10?'0'+this.getMilliseconds():this.getMilliseconds()).replace('DAY', WeekCns[this.getDay()]);

        return formateStr.replace("yyyy", this.getFullYear()).replace("MM", (this.getMonth() + 1)).replace("dd", this.getDate()).replace("HH",this.getHours()).replace("mm",  this.getMinutes()).replace("ss",  this.getMilliseconds()).replace('DAY', WeekCns[this.getDay()]);
    }

    var WeekCns = { 1: '一', 2: '二', 3: '三', 4: '四', 5: '五', 6: '六', 0: '日' };

    
})

var Common = {};
//为了IE9 不支持  new Date('2016-10-10 11:00')  不支持分和秒
Common.NewDae = function (dateStr) {
    //IE9 上的NEW date（）
    var date = '';
    if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0) ||
                    (navigator.userAgent.indexOf('Trident') >= 0)) {
        var dateStrs = dateStr.split(' ');
        var ymd = dateStrs[0];
        var hms = dateStrs[1];
        var ymdArr = ymd.split('-');
        var y = ymdArr[0];
        var m = ymdArr[1];
        var d = ymdArr[2];

        var hmsArr = hms.split(':');
        var h = hmsArr.length >= 1 ? hmsArr[0] : 0;
        var m = hmsArr.length >= 2 ? hmsArr[1] : 0;
        var s = hmsArr.length >= 3 ? hmsArr[2] : 0;
        date = new Date(y, m, d, h, m, s);
    } else {
        date = new Date(dateStr);
    }
    return date;
}

//判断是否是管理员登录
Common.IsAdministrator = function () {
    if ($ != undefined && $.cookie != undefined) {//先判断是否引入了$.COOKIE
        return $.cookie()["MenuGroupNames"] != undefined && $.cookie()["MenuGroupNames"].indexOf('管理员') >= 0;
    } else {
        return false;
    }
}
Common.MaxLengthError = function () {

    if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0) ||
            (navigator.userAgent.indexOf('Trident') >= 0)) {
        $('textarea[maxlength]').bind('blur', function () {
            var isCalendar = $(this).hasClass('TBcalendar');
            //日期控件部做截取控制
            if (isCalendar) { return; }

            var mx = parseInt($(this).attr('maxlength'));
            var str = $(this).val();
            if (str.length > mx) {
                $(this).val(str.substr(0, mx));
                return false;
            }
        });
        $('input[maxlength]').bind('blur', function () {
            var isCalendar = $(this).hasClass('TBcalendar');
            //日期控件部做截取控制
            if (isCalendar) { return; }

            var str = $(this).val();
            var mx = parseInt($(this).attr('maxlength'));
            if (str.length > mx) {
                $(this).val(str.substr(0, mx));
                return false;
            }
        });
    }
}
//把后台返回的错误打印到控制台
Common.ConsoleLogError = function (data, methodName) {
    //if (data == undefined || data == "") {
    //    console.log(methodName + ':');
    //    console.log("返回的data 为:" + data + "  ,methodName:" + methodName);
    //} else {
    //    var errorData = JSON.parse(data);
    //    if (errorData.Error == true) {
    //        console.log(methodName + ':' + errorData.ErrorMsg);
    //    }
    //}
}

//设置用户COOKIE
Common.SetStaffCookie = function () {
    var userId = Common.GetQueryString("userid");
    if (userId != null && userId != undefined && userId != "") {
        
        //如果cookie不存在STAFFID 或者STAFFID与传过来的STAFFID不一致，就写COOKIE，没有就是新增，有就是覆盖
        if ($.cookie("StaffID") != undefined && $.cookie("StaffID") == userId) {
            return;
        }
        //通过USERID请求USER相关数据，写COOKIE
        $.ajax({
            type: "post",
            async: true,
            url: "../../Ashx/CCD/OnDutyHandler.ashx?method=GetStaffById&userId=" + userId + "&u=" + Math.random(),
            dataType: 'html',
            success: function (data) {
                var userCookie = JSON.parse(data);
                for (var cookie in userCookie) {
                    $.cookie(cookie,
                        userCookie[cookie]);
                }
            },
            error: function (http, error) {
                //$("body").html(http.responseText);
                //console.log(http.responseText);
            }
        });
    }
    //如果没有使用框架传过来STAFFID，又没有登录的COOKIE就调转到登录页面
    else if ($.cookie("StaffID") == undefined) {
        window.location.href = "/PortalLogin.aspx";
    }
}

//通过参数名称获取参数值 url=reportDetail.html?reportId=12  时 name=reportId 即可取出来值12
Common.GetQueryString = function (name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]);
}
/*
不用 --先保留下 2016-06-02
自定义分页插件
默认值如下：
PageSize: 10,
PageIndex: 1, 
PageCount: 1, 
DivId: 'listDiv', 
countUrl: '', 
countParam: {},
listUrl: '', 
listParam:{},
RenderOverFun: undefined, 表格渲染完执行 
IsShowAll: false, 
LocalData: null, 
IsUseLocalData: false
DataEmptyRender:数据为空时的处理函数
*/
Common.CustomPagePlug1 = function (operation) {
    var PageData = function (operation) {
        //数据分页
        //未设置参数使用默认参数 对设置的进行配置
        for (var property in operation) {
            PageData.InitData[property] = operation[property];
        }

        PageData.SetDataToDivData($('#' + PageData.InitData.DivId), PageData.InitData);
        if (PageData.InitData.IsShowAll) {
            PageData.InitPlanList(operation.DivId);
        }
        else {
            PageData.InitPlan(PageData.InitData.DivId);
        }
    };
    PageData.DateFromMSJsonString = function (value) {
        var d = eval('new ' + (value.replace(/\//g, '')));
        var result = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
        return result;
    };

    PageData.InitData = {
        PageSize: 10, PageIndex: 1, PageCount: 1, DivId: 'listDiv', countUrl: '', listUrl: '', RenderOverFun: undefined, IsShowAll: false, LocalData: null, IsUseLocalData: false, DataEmptyRender: function () {
            var html = "";

//            html += "<tr style='text-align: center;'>";
//            html += "<td>";
//            html += '没有查询记录';
//            html += "</td>";
//            html += "</tr>";

            $("#" + PageData.InitData.DivId + ' table tbody').html(html);
            $("#" + PageData.InitData.DivId + " .loadDate").css("display", "none");
        }
    };

    //Load
    PageData.Load = function (obj) {
        PageData.InitData = obj;
    }
    //页面跳转
    PageData.ToPage = function (val) {
        if (val.target == undefined || typeof (val.target) == "function") {
            return;
        }
        val = val.target;
        if (val.nodeName == "I") {//类似冒泡的错误
            val = val.parentNode;
        }
        //初始化
        PageData.Load($(val.parentElement.parentElement).data());//GoToPage
        switch (val.title) {
            case "下一页":
                if (PageData.InitData.PageIndex == PageData.InitData.pageCount) {
                    alert("没有下一页，请重新选择。");
                    return;
                }
                else {
                    PageData.InitData.PageIndex++;
                }
                $("#" + PageData.InitData.DivId + " .pagination-panel input").val(PageData.InitData.PageIndex);
                break;
            case "上一页":
                if (PageData.InitData.PageIndex == 1) {
                    alert("没有上一页，请重新选择。");
                    return;
                }
                else {
                    PageData.InitData.PageIndex--;
                }
                $("#" + PageData.InitData.DivId + " .pagination-panel input").val(PageData.InitData.PageIndex);
                break;
            case "转到":
                var value = $("#" + PageData.InitData.DivId + " .pagination-panel input").val();
                var currentPageIndex = 0;
                if (isNaN(parseInt(value))) {
                    alert("请输入数字。");
                    $('#' + PageData.InitData.DivId + " .pagination-panel input").val(PageData.InitData.PageIndex);
                    return;
                }
                currentPageIndex = parseInt(value);
                if (currentPageIndex > PageData.InitData.PageCount) {
                    alert("输入的页码数大于总页数。");
                    $('#' + PageData.InitData.DivId + " .pagination-panel input").val(PageData.InitData.PageIndex);
                    return;
                }
                else if (currentPageIndex < 1) {
                    alert("请输入大于0的整数。");
                    $('#' + PageData.InitData.DivId + " .pagination-panel input").val(PageData.InitData.PageIndex);
                    return;
                }
                PageData.InitData.PageIndex = currentPageIndex;
                break;
        }

        $("#" + PageData.InitData.DivId + " .loadDate").css("display", "block");
        if (PageData.InitData.PageIndex == 1) {
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").addClass("disabled");
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").attr("disabled", "disabled");
        } else if (PageData.InitData.PageIndex == PageData.InitData.PageCount) {
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=下一页]").addClass("disabled");
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=下一页]").attr("disabled", "disabled");
        }

        if (PageData.InitData.PageIndex != 1) {
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").removeClass("disabled");
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").removeAttr("disabled", "disabled");
        }
        if (PageData.InitData.PageIndex != PageData.InitData.PageCount) {
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=下一页]").removeClass("disabled");
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=下一页]").removeAttr("disabled", "disabled");
        }

        PageData.InitPlanList(PageData.InitData.DivId);
    };
    PageData.CountButton = function () {

        $("#" + PageData.InitData.DivId + " .pagination-panel .pagination-panel-total").html(PageData.InitData.PageCount);
        $("#" + PageData.InitData.DivId + " .pagination-panel input").val(PageData.InitData.PageIndex);
        //刷新分页控件
        if (PageData.InitData.PageCount > 1) {
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=下一页]").removeClass('disabled');
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").removeClass('disabled');
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=转到]").removeClass('disabled');
            $("#" + PageData.InitData.DivId + " .pagination-panel input").removeAttr("disabled", "disabled");
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=下一页]").removeAttr("disabled", "disabled");
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").removeAttr("disabled", "disabled");
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=转到]").removeAttr("disabled", "disabled");
        }
        else {
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=下一页]").addClass('disabled');
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").addClass('disabled');
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=转到]").addClass('disabled');
            $("#" + PageData.InitData.DivId + " .pagination-panel input").addClass('disabled');

            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=下一页]").attr("disabled", "disabled");
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").attr("disabled", "disabled");
            $("#" + PageData.InitData.DivId + " .pagination-panel button[title=转到]").attr("disabled", "disabled");
            $("#" + PageData.InitData.DivId + " .pagination-panel input").attr("disabled", "disabled");
        }
    };
    PageData.InitPlanList = function (DivId) {
        var initList = function (data) {
            //初始化
            PageData.Load($('#' + DivId).data());

            Common.ConsoleLogError(data, PageData.InitData.IsShowAll ? PageData.InitData.listUrl : PageData.InitData.listUrl + "&pageIndex=" + PageData.InitData.PageIndex + "&pageSize=" + PageData.InitData.PageSize + "&u=" + Math.random());
            if (data == "" || data==undefined) {
                if (PageData.InitData.DataEmptyRender != null && typeof (PageData.InitData.DataEmptyRender) == "function") {
                    PageData.InitData.DataEmptyRender();
                }
                return;
            }


            var obj = JSON.parse(data);
            var html = "";
            $.each(obj, function (k, obje) {
                html += "<tr>";
                if (obje) {
                    var headers = $("#" + PageData.InitData.DivId + " table thead tr th");
                    for (var i = 0; i < headers.length; i++) {
                        if ($(headers[i]).data != undefined && $(headers[i]).data().colname != undefined && obje[$(headers[i]).data().colname] != undefined) {
                            if ($(headers[i]).data().coltype != undefined && $(headers[i]).data().coltype == "date") {//类型是日期的TD
                                html += "<td>" + PageData.DateFromMSJsonString(obje[$(headers[i]).data().colname]) + "</td>"
                            }
                            else if ($(headers[i]).data().opeation != undefined) {//存在操作按钮的TD
                                //添加字符截取功能
                                if ($(headers[i]).data().charlength != undefined) {
                                    var charlength = $(headers[i]).data().charlength;
                                    html += "<td title='" + obje[$(headers[i]).data().colname] + "'> <a onclick=" + $(headers[i]).data().opeation + ">" + (obje[$(headers[i]).data().colname].length <= charlength ? obje[$(headers[i]).data().colname] : (obje[$(headers[i]).data().colname].substr(0, charlength)) + "...") + "</a></td>"
                                } else {
                                    html += "<td> <a onclick=" + $(headers[i]).data().opeation + ">" + obje[$(headers[i]).data().colname] + "</a></td>"
                                }
                            }
                            //是否把TITLE放上去
                            else if ($(headers[i]).data().title != undefined) {
                                if ($(headers[i]).data().title != undefined) {
                                    var title = $(headers[i]).data().title;
                                    if (title == "true" || title) {
                                        html += "<td title='" + obje[$(headers[i]).data().colname] + "'>" + obje[$(headers[i]).data().colname] + "</td>"
                                    }
                                    else {
                                        html += "<td>" + obje[$(headers[i]).data().colname] + "</td>"
                                    }
                                }
                            }
                            else if ($(headers[i]).data().visiable != undefined) {
                                html += "<td style='display:none;'>" + obje[$(headers[i]).data().colname] + "</td>"
                            }
                            else {//添加字符截取功能 没有类型的TD
                                if ($(headers[i]).data().charlength != undefined) {
                                    var charlength = $(headers[i]).data().charlength;

                                    html += "<td title='" + obje[$(headers[i]).data().colname] + "'>" + (obje[$(headers[i]).data().colname].length <= charlength ? obje[$(headers[i]).data().colname] : (obje[$(headers[i]).data().colname].substr(0, charlength) + "...")) + "</td>"
                                } else {
                                    html += "<td>" + obje[$(headers[i]).data().colname] + "</td>";
                                }
                            }
                        }
                        else if ($(headers[i]).data().coltype != undefined && $(headers[i]).data().coltype == "SN") {//序号  序号的类型是SN colname列名称为空
                            html += "<td>" + (parseInt(k) + 1 + parseInt(PageData.InitData.PageSize) * (parseInt(PageData.InitData.PageIndex) - 1)) + "</td>";
                        }
                        else if ($(headers[i]).data().coltype != undefined && $(headers[i]).data().coltype == "Operation") {//序号  序号的类型是SN colname列名称为空){
                            html += ('<td><a style="text-decoration:underline;" href="#" onclick="updateReport(this)" class="btn btn-link btn_det">编辑</a>' + '<a href="#" style="text-decoration:underline;" onclick="delReport(this)" class="btn btn-link btn_det">删除</a></td>');
                            html += "</tr>";
                        }
                            //自定义内容
                        else if ($(headers[i]).data().custom != undefined && $(headers[i]).data().customcontent != undefined) {

                            var tmp = '';
                            var customCount = parseInt($(headers[i]).data().customcontent);
                            for (var j = 1; j <= customCount; j++) {
                                //标签; 属性;VALUE
                                var tmpC = $(headers[i]).data()["customcontent" + j];
                                tmpC = tmpC.split('@');
                                tmp += '<' + tmpC[0] + ' ' + tmpC[1] + '>' + tmpC[2] + '</' + tmpC[0] + '>';
                            }
                            html += ('<td>' + tmp + '</td>');
                        }
                        else {
                            html += "<td>" + "" + "</td>"
                        }
                    }
                }
            });
             
            $("#" + PageData.InitData.DivId + ' table tbody').html(html);
            $("#" + PageData.InitData.DivId + " .loadDate").css("display", "none");
            

            //改变一下父窗体中IFRAME的高度
            //当列表被嵌在id=dayReporFrame的IFRAME里时，初始完页面后对父页面中的IFRAME设置高度
            /*if (parent.document.getElementById('dayReporFrame') != null) {
                var bodyHeight = $('body').height() + 30;
                $(parent.document.getElementById('dayReporFrame')).height(bodyHeight);
            }*/
            //绑定完成的回调函数
            if (PageData.InitData.RenderOverFun != null && typeof (PageData.InitData.RenderOverFun) == 'function') {
                PageData.InitData.RenderOverFun();
            }
        }

        if (PageData.InitData.IsUseLocalData) {
            initList(PageData.InitData.LocalData);
        }
        else {
            $.ajax({
                type: "post",
                async: true,
                url: PageData.InitData.IsShowAll ? PageData.InitData.listUrl : PageData.InitData.listUrl + "&pageIndex=" + PageData.InitData.PageIndex + "&pageSize=" + PageData.InitData.PageSize + "&u=" + Math.random(),
                dataType: 'html',
                success: function (data) {
                    initList(data);
                },
                error: function (http, error) {
                    $("body").html(http.responseText);
                }
            });
        }
    };

    PageData.InitPlan = function (DivId) {
        $.ajax({
            type: "post",
            async: true,
            url: PageData.InitData.countUrl + "&u=" + Math.random(),
            dataType: 'html',
            success: function (Counts) {
                Common.ConsoleLogError(Counts, PageData.InitData.countUrl + "&u=" + Math.random());

                //初始化
                PageData.Load($('#' + DivId).data());
                //计算页数
                if (parseInt(Counts / PageData.InitData.PageSize) < Counts / PageData.InitData.PageSize) {
                    PageData.InitData.PageCount = parseInt(Counts / PageData.InitData.PageSize) + 1;
                }
                else if (parseInt(Counts / PageData.InitData.PageSize) == Counts / PageData.InitData.PageSize) {
                    PageData.InitData.PageCount = parseInt(Counts / PageData.InitData.PageSize);
                }
                PageData.CountButton();
                if (PageData.InitData.PageCount > 0) {
                    PageData.InitPlanList(PageData.InitData.DivId);
                }
                else {
                    if (PageData.InitData.DataEmptyRender != null && typeof (PageData.InitData.DataEmptyRender) == "function") {
                        PageData.InitData.DataEmptyRender();
                    }
                }
                //初始化表格数据 默认第一页
                $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").addClass('disabled');
                $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").attr("disabled", "disabled");

                //上一页，下一页，转到按钮的事件绑定
                $("#" + PageData.InitData.DivId + " .pagination-panel button[title=上一页]").bind('click', PageData.ToPage);
                $("#" + PageData.InitData.DivId + " .pagination-panel button[title=下一页]").bind('click', PageData.ToPage);
                $("#" + PageData.InitData.DivId + " .pagination-panel button[title=转到]").bind('click', PageData.ToPage);
                $("#" + PageData.InitData.DivId + " .pagination-panel label select").bind('change', PageData.UpdatePageSize);
            },
            error: function (http, error) {
                $("body").html(http.responseText);
            }
        });
    };
    //更新PAGESIZE
    PageData.UpdatePageSize = function (val) {
        //初始化
        val = val.target;
        PageData.Load($(val.parentElement.parentElement.parentElement).data());
        var selectValue = val.value;
        //改变PAGESIZE时，全部跳到第一页
        if (selectValue != -1) {
            PageData.InitData.PageIndex = 1;
            PageData.InitData.PageSize = selectValue;
        } else {
            PageData.InitData.PageIndex = 1;
            PageData.InitData.PageSize = parseInt(PageData.InitData.PageCount) * parseInt(PageData.InitData.PageSize);
        }

        $(PageData.InitData.DivId).data.PageSize = PageData.InitData.PageSize;
        PageData.InitPlan(PageData.InitData.DivId);
    };

    //将dataObj的每个属性依次赋值给divObj的data() 上 divObj是HTML元素即可
    PageData.SetDataToDivData = function (divObj, dataObj) {
        for (var property in dataObj) {
            divObj.data()[property] = dataObj[property];
        }
    }
    
    return new PageData(operation);
}


/*自定义分页插件
默认值如下：
PageSize: 10,//为-1时或者界面选中的SELECT 值为-1时，第一次显示全部
PageIndex: 1, 
PageCount: 1, 
DivId: 'listDiv', 
countUrl: '',  //获取COUNT值得URL
listUrl: '', //获取列表的URL
RenderOverFun: undefined, 表格渲染完时执行的函数
IsShowAll: false,  //也是是否显示全部   这个为TRUE时，分页条不好使  ...(不要分页条的时候用)
LocalData: null, // 和IsUseLocalData一起使用  使用时 不分页
IsUseLocalData: false
DataEmptyRender:数据为空时的处理函数
*/
Common.CustomPagePlug = function (operation) {
    var PageData = function (operation) {
        var _this = this;
        this.InitData = {
            PageSize: 10,
            PageIndex: 1,
            PageCount: 1,
            DivId: 'listDiv',
            countUrl: '',
            countParam: {},
            listUrl: '',
            listParam:{},
            ////渲染完TABLE执行的函数的参数
            RenderOverParam: {},
            //渲染完TABLE执行的函数
            RenderOverFun: undefined,
            IsShowAll: false,
            LocalData: null,
            IsUseLocalData: false,
            DataEmptyRender: function (obj) {
                var html = "";
                html += "<tr style='text-align: center;'>";
                var ths = $("#" + obj.InitData.DivId + " table thead tr th");
                var colSpan = ths.length;
                for (var i = 0; i < ths.length; i++) {
                    if ($(ths[i]).css('display') == "none") {
                        colSpan -= 1;
                    }
                }
                html += "<td colspan='" + colSpan + "'>";
                //console.log(colSpan)
             //   html += '没有查询记录';
                html += "</td>";
                html += "</tr>";
                $("#" + obj.InitData.DivId + ' table tbody').html(html);
                $("#" + obj.InitData.DivId + " .loadDate").css("display", "none");
            }
        };


        //数据分页
        //未设置参数使用默认参数 对设置的进行配置
        for (var property in operation) {
            _this.InitData[property] = operation[property];
        }
        _this.SetDataToDivData($('#' + _this.InitData.DivId), _this.InitData);
        if (_this.InitData.IsShowAll) {
            _this.InitPlanList(operation.DivId);
        }
        else {
            _this.InitPlan(_this.InitData.DivId);
        }
    };

    PageData.prototype.DateFromMSJsonString = function (value) {
        var d = eval('new ' + (value.replace(/\//g, '')));
        var result = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
        return result;
    };


    //Load
    PageData.prototype.Load = function (obj) {
        this.InitData = obj;
    }
    //页面跳转
    PageData.prototype.ToPage = function (val) {
        var _this = val.data.thisPage;

        if (val.target == undefined || typeof (val.target) == "function") {
            return;
        }
        val = val.target;

        //console.log(val.nodeName+"a");
        if (val.nodeName == "I") {//类似冒泡的错误
            val = val.parentNode;
            //return;
        }
        //初始化
        _this.Load($(val.parentElement.parentElement).data());//GoToPage

        var value = $("#" + _this.InitData.DivId + " .pagination-panel input").val();
        var currentPageIndex = 0;
        if (isNaN(parseInt(value))) {
            alert("请输入数字。");
            $('#' + _this.InitData.DivId + " .pagination-panel input").val(_this.InitData.PageIndex);
            return;
        }
        currentPageIndex = parseInt(value);
        switch (val.title) {
            case "下一页":
                //if (_this.InitData.PageIndex == _this.InitData.pageCount || currentPageIndex == _this.InitData.pageCount) {
                if (currentPageIndex == _this.InitData.PageCount) {
                    alert("没有下一页，请重新选择。");
                    return;
                }
                else {
                    //_this.InitData.PageIndex++;
                    currentPageIndex++;
                    _this.InitData.PageIndex = currentPageIndex;
                }
                $("#" + _this.InitData.DivId + " .pagination-panel input").val(_this.InitData.PageIndex);
                break;
            case "上一页":
                //if (_this.InitData.PageIndex == 1 || currentPageIndex == 1) {
                if (currentPageIndex == 1) {
                    alert("没有上一页，请重新选择。");
                    return;
                }
                else {
                    //_this.InitData.PageIndex--;
                    currentPageIndex--;
                    _this.InitData.PageIndex = currentPageIndex;
                }
                $("#" + _this.InitData.DivId + " .pagination-panel input").val(_this.InitData.PageIndex);
                break;
            case "转到":
                if (currentPageIndex > _this.InitData.PageCount) {
                    alert("输入的页码数大于总页数。");
                    $('#' + _this.InitData.DivId + " .pagination-panel input").val(_this.InitData.PageIndex);
                    return;
                }
                else if (currentPageIndex < 1) {
                    alert("请输入大于0的整数。");
                    $('#' + _this.InitData.DivId + " .pagination-panel input").val(_this.InitData.PageIndex);
                    return;
                }
                _this.InitData.PageIndex = currentPageIndex;
                break;
        }

        $("#" + _this.InitData.DivId + " .loadDate").css("display", "block");
        if (_this.InitData.PageIndex == 1) {
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").addClass("disabled");
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").attr("disabled", "disabled");
        } else if (_this.InitData.PageIndex == _this.InitData.PageCount) {
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=下一页]").addClass("disabled");
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=下一页]").attr("disabled", "disabled");
        }

        if (_this.InitData.PageIndex != 1) {
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").removeClass("disabled");
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").removeAttr("disabled", "disabled");
        }
        if (_this.InitData.PageIndex != _this.InitData.PageCount) {
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=下一页]").removeClass("disabled");
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=下一页]").removeAttr("disabled", "disabled");
        }

        _this.InitPlanList(_this.InitData.DivId);
    };
    PageData.prototype.CountButton = function () {
        var _this = this;
        $("#" + _this.InitData.DivId + " .pagination-panel .pagination-panel-total").html(_this.InitData.PageCount);
        $("#" + _this.InitData.DivId + " .pagination-panel input").val(_this.InitData.PageIndex);
        //刷新分页控件
        if (_this.InitData.PageCount > 1) {
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=下一页]").removeClass('disabled');
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").removeClass('disabled');
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=转到]").removeClass('disabled');
            $("#" + _this.InitData.DivId + " .pagination-panel input").removeAttr("disabled", "disabled");
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=下一页]").removeAttr("disabled", "disabled");
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").removeAttr("disabled", "disabled");
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=转到]").removeAttr("disabled", "disabled");
        }
        else {
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=下一页]").addClass('disabled');
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").addClass('disabled');
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=转到]").addClass('disabled');
            $("#" + _this.InitData.DivId + " .pagination-panel input").addClass('disabled');

            $("#" + _this.InitData.DivId + " .pagination-panel button[title=下一页]").attr("disabled", "disabled");
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").attr("disabled", "disabled");
            $("#" + _this.InitData.DivId + " .pagination-panel button[title=转到]").attr("disabled", "disabled");
            $("#" + _this.InitData.DivId + " .pagination-panel input").attr("disabled", "disabled");
        }
    };
    PageData.prototype.InitPlanList = function (DivId) {

        var _this = this;
        var initList = function (data) {
            //初始化
            _this.Load($('#' + DivId).data());

            Common.ConsoleLogError(data, _this.InitData.IsShowAll ? _this.InitData.listUrl : _this.InitData.listUrl + "&pageIndex=" + _this.InitData.PageIndex + "&pageSize=" + _this.InitData.PageSize + "&u=" + Math.random());
            var obj = JSON.parse(data).DTObjs;
            var html = "";
            $.each(obj, function (k, obje) {
                if (DivId == "sample_3Div" && window.document.URL.indexOf("portal.aspx") > 0) {
                    html += "<tr onclick='showUnReadMessages(this," + obje.MsgCategoryID + "," + obje.MsgID + ")'>";
                } else {
                    html += "<tr>";
                }
                if (obje) {
                    //把OBJE的值过一遍  replace 掉：  json 转的时候转化了这些值，转化回来
                    for (var ele in obje) {
                        obje[ele] = obje[ele];
                    }
                    var headers = $("#" + _this.InitData.DivId + " table thead tr th");
                    for (var i = 0; i < headers.length; i++) {
                        if ($(headers[i]).data != undefined && $(headers[i]).data().colname != undefined && obje[$(headers[i]).data().colname] != undefined) {
                            if ($(headers[i]).data().coltype != undefined && $(headers[i]).data().coltype == "date") {//类型是日期的TD
                                html += "<td>" + _this.DateFromMSJsonString(obje[$(headers[i]).data().colname]) + "</td>"
                            }
                            else if ($(headers[i]).data().opeation != undefined) {//存在操作按钮的TD
                                //添加字符截取功能
                                if ($(headers[i]).data().charlength != undefined) {
                                    var charlength = $(headers[i]).data().charlength;
                                    html += "<td title='" + obje[$(headers[i]).data().colname] + "'> <a onclick=" + $(headers[i]).data().opeation + ">" + (obje[$(headers[i]).data().colname].length <= charlength ? obje[$(headers[i]).data().colname] : (obje[$(headers[i]).data().colname].substr(0, charlength)) + "...") + "</a></td>"
                                } else {
                                    html += "<td> <a onclick=" + $(headers[i]).data().opeation + ">" + obje[$(headers[i]).data().colname] + "</a></td>"
                                }
                            }
                                //是否把TITLE放上去
                            else if ($(headers[i]).data().title != undefined) {
                                if ($(headers[i]).data().title != undefined) {
                                    var title = $(headers[i]).data().title;
                                    if (title == "true" || title) {
                                        html += "<td title='" + obje[$(headers[i]).data().colname] + "'>" + obje[$(headers[i]).data().colname] + "</td>"
                                    }
                                    else {
                                        html += "<td>" + obje[$(headers[i]).data().colname] + "</td>"
                                    }
                                }
                            }
                            else if ($(headers[i]).data().visiable != undefined) {
                                html += "<td style='display:none;'>" + obje[$(headers[i]).data().colname] + "</td>"
                            }
                            else {//添加字符截取功能 没有类型的TD
                                if ($(headers[i]).data().charlength != undefined) {
                                    var charlength = $(headers[i]).data().charlength;

                                    html += "<td title='" + obje[$(headers[i]).data().colname] + "'>" + (obje[$(headers[i]).data().colname].length <= charlength ? obje[$(headers[i]).data().colname] : (obje[$(headers[i]).data().colname].substr(0, charlength) + "...")) + "</td>"
                                } else {
                                    html += "<td>" + obje[$(headers[i]).data().colname] + "</td>";
                                }
                            }
                        }
                        else if ($(headers[i]).data().coltype != undefined && $(headers[i]).data().coltype == "SN") {//序号  序号的类型是SN colname列名称为空
                            html += "<td>" + (parseInt(k) + 1 + parseInt(_this.InitData.PageSize) * (parseInt(_this.InitData.PageIndex) - 1)) + "</td>";
                        }
                        else if ($(headers[i]).data().coltype != undefined && $(headers[i]).data().coltype == "Operation") {//序号  序号的类型是SN colname列名称为空){
                            html += ('<td><a style="text-decoration:underline;" href="#" onclick="updateReport(this)" class="btn btn-link btn_det">编辑</a>' + '<a href="#" style="text-decoration:underline;" onclick="delReport(this)" class="btn btn-link btn_det">删除</a></td>');
                            html += "</tr>";
                        }
                            //自定义内容
                        else if ($(headers[i]).data().custom != undefined && $(headers[i]).data().customcontent != undefined) {

                            var tmp = '';
                            var customCount = parseInt($(headers[i]).data().customcontent);
                            for (var j = 1; j <= customCount; j++) {
                                //标签; 属性;VALUE
                                var tmpC = $(headers[i]).data()["customcontent" + j];
                                tmpC = tmpC.split('@');
                                tmp += '<' + tmpC[0] + ' ' + tmpC[1] + '>' + tmpC[2] + '</' + tmpC[0] + '>';
                            }
                            html += ('<td>' + tmp + '</td>');
                        }
                        else {
                            html += "<td>" + "" + "</td>"
                        }
                    }
                }
            });
            if (obj == "") {
                if (_this.InitData.DataEmptyRender != null && typeof (_this.InitData.DataEmptyRender) == "function") {
                    _this.InitData.DataEmptyRender(_this);
                }
            } else {
                $("#" + _this.InitData.DivId + ' table tbody').html(html);
                $("#" + _this.InitData.DivId + " .loadDate").css("display", "none");
            }

            //把TR的 DATA-DATA设置为该行的对象值
            var trData = $("#" + _this.InitData.DivId + " table thead tr");
            if (trData != undefined && trData.data != undefined && trData.data() != undefined && trData.data().data != undefined && trData.data().data == true) {
                $.each(obj, function (i, obje) {
                    $($('#' + _this.InitData.DivId + ' table tbody tr')[i]).data().data = obje;
                });
            }

            //改变一下父窗体中IFRAME的高度
            //当列表被嵌在id=dayReporFrame的IFRAME里时，初始完页面后对父页面中的IFRAME设置高度
            /*if (parent.document.getElementById('dayReporFrame') != null) {
                var bodyHeight = $('body').height() + 30;
                $(parent.document.getElementById('dayReporFrame')).height(bodyHeight);
            }*/
            //绑定完成的回调函数
            if (_this.InitData.RenderOverFun != null && typeof (_this.InitData.RenderOverFun) == 'function') {
                _this.InitData.RenderOverFun(_this.InitData.RenderOverParam);
            }
        }

        if (_this.InitData.IsUseLocalData) {
            initList(_this.InitData.LocalData);
        }
        else {
            $.ajax({
                type: "post",
                async: true,
                url: _this.InitData.IsShowAll ? _this.InitData.listUrl : _this.InitData.listUrl + "&pageIndex=" + _this.InitData.PageIndex + "&pageSize=" + _this.InitData.PageSize + "&u=" + Math.random(),
                dataType: 'html',
                success: function (data) {
                    initList(data);
                },
                error: function (http, error) {
                    $("body").html(http.responseText);
                }
            });
        }
    };

    PageData.prototype.InitPlan = function (DivId) {
        var _this = this;
        $.ajax({
            type: "post",
            async: true,
            url: _this.InitData.countUrl + "&u=" + Math.random(),
            dataType: 'html',
            success: function (Counts) {
                Counts = JSON.parse(Counts).DTCout[0].Count;
                //如果是ALL就把PAGESIZE设成PAGECOUNT+1 为了解决首次加载默认显示全部 
                if ($('#' + DivId + ' .pagination-panel label select').val() == '-1' || _this.InitData.PageSize==-1) {
                    _this.InitData.PageSize = Counts + 1;
                }
                //初始化
                _this.Load($('#' + DivId).data());
                //计算页数
                if (parseInt(Counts / _this.InitData.PageSize) < Counts / _this.InitData.PageSize) {
                    _this.InitData.PageCount = parseInt(Counts / _this.InitData.PageSize) + 1;
                }
                else if (parseInt(Counts / _this.InitData.PageSize) == Counts / _this.InitData.PageSize) {
                    _this.InitData.PageCount = parseInt(Counts / _this.InitData.PageSize);
                }
                _this.CountButton();
                if (_this.InitData.PageCount > 0) {
                    _this.InitPlanList(_this.InitData.DivId);
                }
                else {
                    if (_this.InitData.DataEmptyRender != null && typeof (_this.InitData.DataEmptyRender) == "function") {
                        _this.InitData.DataEmptyRender(_this);
                    }
                }
                //初始化表格数据 默认第一页
                $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").addClass('disabled');
                $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").attr("disabled", "disabled");

                //上一页，下一页，转到按钮的事件绑定
                $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").unbind('click');
                $("#" + _this.InitData.DivId + " .pagination-panel button[title=上一页]").bind('click', { thisPage: _this }, _this.ToPage);
                $("#" + _this.InitData.DivId + " .pagination-panel button[title=下一页]").unbind('click');
                $("#" + _this.InitData.DivId + " .pagination-panel button[title=下一页]").bind('click', { thisPage: _this }, _this.ToPage);
                $("#" + _this.InitData.DivId + " .pagination-panel button[title=转到]").unbind('click');
                $("#" + _this.InitData.DivId + " .pagination-panel button[title=转到]").bind('click', { thisPage: _this }, _this.ToPage);
                //绑定页数的选择
                $("#" + _this.InitData.DivId + " .pagination-panel label select").unbind('change');
                $("#" + _this.InitData.DivId + " .pagination-panel label select").bind('change', { thisPage: _this }, _this.UpdatePageSize);
            },
            error: function (http, error) {
                $("body").html(http.responseText);
            }
        });
    };
    //更新PAGESIZE
    PageData.prototype.UpdatePageSize = function (val) {
        var _this = val.data.thisPage;
        //初始化
        val = val.target;
        _this.Load($(val.parentElement.parentElement.parentElement).data());
        var selectValue = val.value;
        //改变PAGESIZE时，全部跳到第一页
        if (selectValue != -1) {
            _this.InitData.PageIndex = 1;
            _this.InitData.PageSize = selectValue;
        } else {
            _this.InitData.PageIndex = 1;
            _this.InitData.PageSize = parseInt(_this.InitData.PageCount) * parseInt(_this.InitData.PageSize);
        }

        $(_this.InitData.DivId).data.PageSize = _this.InitData.PageSize;
        _this.InitPlan(_this.InitData.DivId);
    };
 
    //将dataObj的每个属性依次赋值给divObj的data() 上 divObj是HTML元素即可
    PageData.prototype.SetDataToDivData = function (divObj, dataObj) {
        for (var property in dataObj) {
            divObj.data()[property] = dataObj[property];
        }
    }

    return new PageData(operation);
}






