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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者: @author Lipengjun <br>
 * 时间: 2018-01-30 18:04<br>
 * 描述: 二维码生成工具类 <br>
 */
public class QRCodeUtil {
    /**
     * 二维码颜色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 二维码颜色
     */
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * ZXing 方式生成二维码
     *
     * @param text      二维码内容
     * @param width     二维码宽
     * @param height    二维码高
     * @param imageType 图片类型
     * @return byte[]
     */
    public static byte[] zxingCodeCreate(String text, int width, int height, String imageType) {
        Map<EncodeHintType, String> his = new HashMap<>(4);
        //设置编码字符集
        his.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            //1、生成二维码
            BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);

            //2、获取二维码宽高
            int codeWidth = encode.getWidth();
            int codeHeight = encode.getHeight();

            //3、将二维码放入缓冲流
            BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < codeWidth; i++) {
                for (int j = 0; j < codeHeight; j++) {
                    //4、循环将二维码内容定入图片
                    image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);
                }
            }
            //5、将二维码写入图片
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, imageType, out);
            return out.toByteArray();
        } catch (WriterException e) {
            e.printStackTrace();
            System.out.println("二维码生成失败");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("二维码生成失败");
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String file = "D:/qrcode.jpg";
        byte[] qrcodeData = QRCodeUtil.zxingCodeCreate("https://mmbizurl.cn/s/naTzHSslN", 300, 300, "jpg");
        FileUtils.writeByteArrayToFile(new File(file), qrcodeData, false);
    }
}
