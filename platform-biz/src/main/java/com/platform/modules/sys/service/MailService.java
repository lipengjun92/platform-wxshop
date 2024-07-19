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
package com.platform.modules.sys.service;

/**
 * 邮件相关接口
 * 系统发送：使用application.yml文件中的mail配置
 *
 * @author 李鹏军
 */
public interface MailService {

    /**
     * 发送邮件
     *
     * @param to      收件人Email地址，多个收件人以,（英文逗号）隔开
     * @param subject 邮件主题
     * @param content 发送内容(普通文本、html内容)
     * @return 是否发送成功
     */
    boolean sendMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人Email地址，多个收件人以,（英文逗号）隔开
     * @param subject  邮件主题
     * @param content  发送内容
     * @param filePath 附件路径
     * @return 是否发送成功
     */
    boolean sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送正文中有静态资源（图片）的邮件(系统发送)
     *
     * @param to      收件人Email地址，多个收件人以,（英文逗号）隔开
     * @param subject 邮件主题
     * @param content 发送内容
     * @param rscPath 静态资源路径
     * @param rscId   contentId
     * @return 是否发送成功
     */
    boolean sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
