/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.common.utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author 李鹏军
 */
public class StringUtils {

    public static boolean isEmpty(Object str) {
        return str == null || str.toString().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 加一拼成字符串
     * 如传入 10, 4 返回 1005
     *
     * @param parentId parentId
     * @param maxId    maxId
     * @return String 加1
     */
    public static String addOne(String parentId, String maxId) {
        int ten = 10;
        if (Constant.STR_ZERO.equals(parentId)) {
            parentId = "";
        }
        if (isNullOrEmpty(maxId)) {
            return parentId + "01";
        }

        maxId = maxId.substring(maxId.length() - 2);

        int result = Integer.parseInt(maxId) + 1;

        if (result < ten) {
            return parentId + "0" + result;
        } else {
            return parentId + result + "";
        }
    }

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }

        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (Object anObject : object) {
                if (!isNullOrEmpty(anObject)) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    /**
     * 正则从一段字符串中取email地址
     *
     * @param context 内容
     * @return String
     */
    public static String parseEmail(String context) {
        String regex = "\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)";
        /*
         * \w代表[a-zA-z0-9_],之所以写成\\w是因为\有转义的意思，所以要输出 \必须得写成\\ + 代表可以有一次或者多次
         * \\.就代表着.,因为.在正则表达式中代表一个任意的字符，所以要想写出.来就得要用到转义字符\ 要输出\就得要写成\\
         * （）代表着一个组合，例如（com）会去匹配含有com的字符串，而com会去匹配含有c或者o或者m 的字符串 | 代表这或
         * 注意：按照正则表达式去匹配的时候是有优先级的，从左至右，（）也可以看做是一 优先级的控制，\\w+@\\w+\\.(com|cn)+
         * 改为\\w+@\\w+\\.com|cn+程序会以为\\w+@\\w+\\.com或者cn
         * 这样当对sadfsd@sdfsd.cn匹配时下面的程序会输出cn,而我们的初衷是让com与cn或
         */
        // String regex = "[a-zA-Z0-9_]+[@][a-zA-Z0-9]+([\\.com]|[\\.cn])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(context);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /**
     * 支付交易ID
     *
     * @return 支付交易ID
     */
    public static String getBundleId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String tradeno = dateFormat.format(new Date());
        String str = "000000" + new Random().nextInt();
        tradeno = tradeno + str.substring(str.length() - 6);
        return tradeno;
    }

    /**
     * 生成订单的编号order_sn
     *
     * @return order_sn
     */
    public static String generateOrderNumber() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String timeStr = DateUtils.format(cal.getTime(), DateUtils.DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS);
        return timeStr + CharUtil.getRandomNum(6);
    }

    public static String transFromType(Integer fromType) {
        String result;
        switch (fromType) {
            case 2:
                result = "MP";
                break;
            case 3:
                result = "APP";
                break;
            case 4:
                result = "H5";
                break;
            case 5:
                result = "AL";
                break;
            case 6:
                result = "QQ";
                break;
            default:
                result = "MA";
        }
        return result;
    }

    /**
     * 去出字符串的空格，至null
     *
     * @param str str
     * @return String
     */
    public static String trimToNull(String str) {
        String ts = str == null ? null : str.trim();
        return isEmpty(ts) ? null : ts;
    }
}
