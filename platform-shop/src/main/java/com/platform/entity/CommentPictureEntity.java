package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 评价图片实体
 * 表名 nideshop_comment_picture
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-29 14:45:55
 */
@Data
public class CommentPictureEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //评价Id
    private Integer commentId;
    //评价图片
    private String picUrl;
    //排序
    private Integer sortOrder;
}
