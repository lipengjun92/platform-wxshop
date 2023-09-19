package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:40
 */
@Data
public class CommentVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //用户评论的类型;0评论的是商品,1评论的是文章
    private Integer typeId;
    //产品Id
    private Integer valueId;
    //储存为base64编码
    private String content;
    //记录时间
    private Long addTime;
    //状态 是否被管理员批准显示;1是;0未批准显示
    private Integer status;
    //会员Id
    private Long userId;

    //会员Id
    private UserVo userInfo;
    private List<CommentPictureVo> picList;
}
