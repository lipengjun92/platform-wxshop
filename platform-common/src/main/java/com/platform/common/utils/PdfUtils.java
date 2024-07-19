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

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author 李鹏军
 */
public class PdfUtils {
    /**
     * pdf添加文字水印
     *
     * @param filePath 原PDF位置
     * @param attrName PDF文件名称
     * @param text     要添加的水印
     * @return 添加水印的文件路径
     * @throws Exception Exception
     */
    public static String addWatermark(String filePath, String attrName, String text) {
        String result = filePath + File.separator + "autoMail" + File.separator + attrName;
        File file = new File(filePath + attrName);
        try {
            float formWidth = 35f;
            float formHeight = 35f;
            float fontSize = 25;
            int length1 = 14;
            int length2 = 22;
            int length3 = 46;
            int length4 = 62;
            if (text.length() <= length1) {
                formWidth = 2.4f;
                formHeight = 2.4f;
                fontSize = 50;
            } else if (text.length() <= length2) {
                formWidth = 4;
                formHeight = 4;
                fontSize = 50;
            } else if (text.length() <= length3) {
                formWidth = 10;
                formHeight = 10;
                fontSize = 40;
            } else if (text.length() <= length4) {
                formWidth = 20;
                formHeight = 20;
            }
            PDDocument document = Loader.loadPDF(file);
            document.setAllSecurityToBeRemoved(true);
            for (PDPage page : document.getPages()) {
                float width = page.getBBox().getWidth();
                float height = page.getBBox().getHeight();

                PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
                //pdf扩展图形对象
                PDExtendedGraphicsState graphicsState = new PDExtendedGraphicsState();
                // 透明度
                graphicsState.setNonStrokingAlphaConstant(0.2f);
                graphicsState.setAlphaSourceFlag(true);
                cs.setGraphicsStateParameters(graphicsState);
                cs.setNonStrokingColor(Color.red);
                cs.beginText();
                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), fontSize);
                // 获取旋转实例
                cs.setTextMatrix(Matrix.getRotateInstance(Math.toRadians(45), width / formWidth, height / formHeight));
                cs.showText(text);
                cs.endText();
                cs.close();
            }
            document.save(result);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * pdf去除水印
     */
    public static boolean removeWatermark(File file) {
        try {
            PDDocument document = Loader.loadPDF(file);
            // 移除加密
            document.setAllSecurityToBeRemoved(true);
            PDPageTree pages = document.getPages();
            for (PDPage page : pages) {
                PDResources resources = page.getResources();
                resources.getExtGStateNames().forEach(ext -> {
                    PDExtendedGraphicsState gState = resources.getExtGState(ext);
                    gState.setNonStrokingAlphaConstant(0.0f);
                });
            }
            String folderPath = file.getParent();
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            document.save(folderPath + File.separator + fileName + "_无水印" + ".pdf");
            document.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * pdf去除加密
     */
    public static boolean removePwd(File file) {
        try {
            PDDocument document = Loader.loadPDF(file);
            // 移除加密
            document.setAllSecurityToBeRemoved(true);
            String folderPath = file.getParent();
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            document.save(folderPath + File.separator + fileName + "_无加密" + ".pdf");
            document.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        removeWatermark(new File("/Users/pengjun/828.pdf"));
    }
}
