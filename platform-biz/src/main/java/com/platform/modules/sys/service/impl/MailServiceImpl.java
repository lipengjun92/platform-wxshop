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
package com.platform.modules.sys.service.impl;

import com.platform.common.utils.Constant;
import com.platform.modules.sys.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author 李鹏军
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendMail(String to, String subject, String content) {
        boolean result = false;
        try {
            MimeMessage message = mailSender.createMimeMessage();
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to.split(","));
            helper.setSubject(subject);
            helper.setText(content + Constant.SIGNATURE_STR, true);

            mailSender.send(message);
            log.info("邮件发送成功");

            result = true;
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
        }
        return result;
    }

    @Override
    public boolean sendAttachmentsMail(String to, String subject, String content, String filePath) {
        boolean result = false;
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to.split(","));
            helper.setSubject(subject);
            helper.setText(content + Constant.SIGNATURE_STR, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator)).replace(File.separator, "");
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            log.info(to + "带附件的邮件已经发送成功。");

            result = true;
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
        }
        return result;
    }

    @Override
    public boolean sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        boolean result = false;
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to.split(","));
            helper.setSubject(subject);
            helper.setText(content + Constant.SIGNATURE_STR, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            log.info("嵌入静态资源的邮件已经发送。");

            result = true;
        } catch (MessagingException e) {
            log.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
        return result;
    }
}
