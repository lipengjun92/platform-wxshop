package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:41
 */
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long userId;
    //会员名称
    private String username;
    //会员密码
    private String password;
    //性别
    private Integer gender;
    //出生日期
    private Date birthday;
    //注册时间
    private Date registerTime;
    //最后登录时间
    private Date lastLoginTime;
    //最后登录Ip
    private String lastLoginIp;
    //会员等级
    private Integer userLevelId;
    //别名
    private String nickname;
    //手机号码
    private String mobile;
    //注册Ip
    private String registerIp;
    //头像
    private String avatar;
    //微信Id
    private String weixinOpenid;
}
