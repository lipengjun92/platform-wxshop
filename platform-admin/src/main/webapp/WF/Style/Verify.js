
/**
* 检查最小长度最大长度。
* 输入:str  字符串
*  返回:true 或 flase; true表示格式正确
*/
function checkLength(tb, minLen, maxLen) {
    if (tb.value.length < minLen || tb.value.length > maxLen) {
        alert('错误：输入的长度必须在' + minLen + ' 到 ' + maxLen + '之间.');
    }
}

/* 是否是邮箱 */
function isEmail(tb) {
    if (tb.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
        return true;
    alert('错误的邮件格式.');
}

/**
* 检查输入的手机号码格式是否正确
* 输入:str  字符串
* 返回:true 或 flase; true表示格式正确
*/
function checkMobilePhone(str) {
    if (str.match(/^(?:13\d|15[89])-?\d{5}(\d{3}|\*{3})$/) == null) {
        return false;
    }
    else {
        return true;
    }
}

/**
* 检查输入的固定电话号码是否正确
* 输入:str  字符串
* 返回:true 或 flase; true表示格式正确
*/
function checkTelephone(str) {
    if (str.match(/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/) == null) {
        return false;
    }
    else {
        return true;
    }
}


/**
* 检查输入的身份证号是否正确
* 输入:str  字符串
*  返回:true 或 flase; true表示格式正确
*/
function checkCard(str) {
    //15位数身份证正则表达式
    var arg1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
    //18位数身份证正则表达式
    var arg2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/;
    if (str.match(arg1) == null && str.match(arg2) == null) {
        return false;
    }
    else {
        return true;
    }
}




/**************************** 未整理 **************************************/

/**
* 2009-10-01
* 贺  臣
* 情  缘
* js各种表单数据验证
*/
/**************************************************************************************/
/*************************************数字的验证*****************************************/
/**************************************************************************************/
/**
* 检查输入的一串字符是否全部是数字
* 输入:str  字符串
* 返回:true 或 flase; true表示为数字
*/
function checkNum(str) {
    return str.match(/\D/) == null;
}


/**
* 检查输入的一串字符是否为小数
* 输入:str  字符串
* 返回:true 或 flase; true表示为小数
*/
function checkDecimal(str) {
    if (str.match(/^-?\d+(\.\d+)?$/g) == null) {
        return false;
    }
    else {
        return true;
    }
}

/**************************************************************************************/
/*************************************字符的验证*****************************************/
/**************************************************************************************/


/**
* 检查输入的一串字符是否是字符
* 输入:str  字符串
* 返回:true 或 flase; true表示为全部为字符 不包含汉字
*/
function checkStr(str) {
    if (/[^\x00-\xff]/g.test(str)) {
        return false;
    }
    else {
        return true;
    }
}


/**
* 检查输入的一串字符是否包含汉字
* 输入:str  字符串
* 返回:true 或 flase; true表示包含汉字
*/
function checkChinese(str) {
    if (escape(str).indexOf("%u") != -1) {
        return true;
    }
    else {
        return false;
    }
}


/**
* 检查输入的邮箱格式是否正确
* 输入:str  字符串
* 返回:true 或 flase; true表示格式正确
*/
function checkEmail(str) {
    if (str.match(/[A-Za-z0-9_-]+[@](\S*)(net|com|cn|org|cc|tv|[0-9]{1,3})(\S*)/g) == null) {
        return false;
    }
    else {
        return true;
    }
}





/**
* 检查QQ的格式是否正确
* 输入:str  字符串
*  返回:true 或 flase; true表示格式正确
*/
function checkQQ(str) {
    if (str.match(/^\d{5,10}$/) == null) {
        return false;
    }
    else {
        return true;
    }
}


/**
* 检查输入的IP地址是否正确
* 输入:str  字符串
*  返回:true 或 flase; true表示格式正确
*/
function checkIP(str) {
    var arg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    if (str.match(arg) == null) {
        return false;
    }
    else {
        return true;
    }
}

/**
* 检查输入的URL地址是否正确
* 输入:str  字符串
*  返回:true 或 flase; true表示格式正确
*/
function checkURL(str) {
    if (str.match(/(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i) == null) {
        return false
    }
    else {
        return true;
    }
}


/**************************************************************************************/
/*************************************时间的验证*****************************************/
/**************************************************************************************/

/**
* 检查日期格式是否正确
* 输入:str  字符串
* 返回:true 或 flase; true表示格式正确
* 注意：此处不能验证中文日期格式
* 验证短日期（2007-06-05）
*/
function checkDate(str) {
    //var value=str.match(/((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/);
    var value = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    if (value == null) {
        return false;
    }
    else {
        var date = new Date(value[1], value[3] - 1, value[4]);
        return (date.getFullYear() == value[1] && (date.getMonth() + 1) == value[3] && date.getDate() == value[4]);
    }
}

/**
* 检查时间格式是否正确
* 输入:str  字符串
* 返回:true 或 flase; true表示格式正确
* 验证时间(10:57:10)
*/
function checkTime(str) {
    var value = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/)
    if (value == null) {
        return false;
    }
    else {
        if (value[1] > 24 || value[3] > 60 || value[4] > 60) {
            return false
        }
        else {
            return true;
        }
    }
}

/**
* 检查全日期时间格式是否正确
* 输入:str  字符串
* 返回:true 或 flase; true表示格式正确
* (2007-06-05 10:57:10)
*/
function checkFullTime(str) {
    //var value = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
    var value = str.match(/^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/);
    if (value == null) {
        return false;
    }
    else {
        //var date = new Date(checkFullTime[1], checkFullTime[3] - 1, checkFullTime[4], checkFullTime[5], checkFullTime[6], checkFullTime[7]);
        //return (date.getFullYear() == value[1] && (date.getMonth() + 1) == value[3] && date.getDate() == value[4] && date.getHours() == value[5] && date.getMinutes() == value[6] && date.getSeconds() == value[7]);
        return true;
    }

}

/**************************************************************************************/
/************************************身份证号码的验证*************************************/
/**************************************************************************************/

/**  
* 身份证15位编码规则：dddddd yymmdd xx p
* dddddd：地区码
* yymmdd: 出生年月日
* xx: 顺序类编码，无法确定
* p: 性别，奇数为男，偶数为女
* <p />
* 身份证18位编码规则：dddddd yyyymmdd xxx y
* dddddd：地区码
* yyyymmdd: 出生年月日
* xxx:顺序类编码，无法确定，奇数为男，偶数为女
* y: 校验码，该位数值可通过前17位计算获得
* <p />
* 18位号码加权因子为(从右到左) Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,1 ]
* 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
* 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
* i为身份证号码从右往左数的 2...18 位; Y_P为脚丫校验码所在校验码数组位置
*
*/
var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1]; // 加权因子   
var ValideCode = [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2]; // 身份证验证位值.10代表X   
function IdCardValidate(idCard) {
    idCard = trim(idCard.replace(/ /g, ""));
    if (idCard.length == 15) {
        return isValidityBrithBy15IdCard(idCard);
    }
    else
        if (idCard.length == 18) {
            var a_idCard = idCard.split(""); // 得到身份证数组   
            if (isValidityBrithBy18IdCard(idCard) && isTrueValidateCodeBy18IdCard(a_idCard)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
}

/**  
* 判断身份证号码为18位时最后的验证位是否正确
* @param a_idCard 身份证号码数组
* @return
*/
function isTrueValidateCodeBy18IdCard(a_idCard) {
    var sum = 0; // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {
        a_idCard[17] = 10; // 将最后位为x的验证码替换为10方便后续操作   
    }
    for (var i = 0; i < 17; i++) {
        sum += Wi[i] * a_idCard[i]; // 加权求和   
    }
    valCodePosition = sum % 11; // 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {
        return true;
    }
    else {
        return false;
    }
}

/**  
* 通过身份证判断是男是女
* @param idCard 15/18位身份证号码
* @return 'female'-女、'male'-男
*/
function maleOrFemalByIdCard(idCard) {
    idCard = trim(idCard.replace(/ /g, "")); // 对身份证号码做处理。包括字符间有空格。   
    if (idCard.length == 15) {
        if (idCard.substring(14, 15) % 2 == 0) {
            return 'female';
        }
        else {
            return 'male';
        }
    }
    else
        if (idCard.length == 18) {
            if (idCard.substring(14, 17) % 2 == 0) {
                return 'female';
            }
            else {
                return 'male';
            }
        }
        else {
            return null;
        }
}

/**  
* 验证18位数身份证号码中的生日是否是有效生日
* @param idCard 18位书身份证字符串
* @return
*/
function isValidityBrithBy18IdCard(idCard18) {
    var year = idCard18.substring(6, 10);
    var month = idCard18.substring(10, 12);
    var day = idCard18.substring(12, 14);
    var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if (temp_date.getFullYear() != parseFloat(year) ||
    temp_date.getMonth() != parseFloat(month) - 1 ||
    temp_date.getDate() != parseFloat(day)) {
        return false;
    }
    else {
        return true;
    }
}

/**  
* 验证15位数身份证号码中的生日是否是有效生日
* @param idCard15 15位书身份证字符串
* @return
*/
function isValidityBrithBy15IdCard(idCard15) {
    var year = idCard15.substring(6, 8);
    var month = idCard15.substring(8, 10);
    var day = idCard15.substring(10, 12);
    var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
    // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
    if (temp_date.getYear() != parseFloat(year) ||
    temp_date.getMonth() != parseFloat(month) - 1 ||
    temp_date.getDate() != parseFloat(day)) {
        return false;
    }
    else {
        return true;
    }
}

//去掉字符串头尾空格   
function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
