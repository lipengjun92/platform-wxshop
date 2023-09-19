package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:41
 */
@Data
public class TopicVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //活动主题
    private String title;
    //活动内容
    private String content;
    //化名
    private String avatar;
    //活动条例图片
    private String itemPicUrl;
    //子标题
    private String subtitle;
    //活动类别
    private Integer topicCategoryId;
    //活动价格
    private BigDecimal priceInfo;
    //
    private String readCount;
    //场景图片链接
    private String scenePicUrl;
    //活动模板Id
    private Integer topicTemplateId;
    //活动标签Id
    private Integer topicTagId;
}
