package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 nideshop_feedback
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-23 15:03:25
 */
@Data
public class FeedbackEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer msgId;
    //父节点
//    private Integer parentId;
    //会员Id
    private Integer userId;
    //会员名称
    private String userName;

    //移动电话
    private String mobile;
    //类型
    private Integer feedType;
    //状态
    private Integer status;
    //详细内容
    private String content;
    //反馈时间
    private Date addTime;
}
