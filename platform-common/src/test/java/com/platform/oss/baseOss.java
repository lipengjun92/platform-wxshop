package com.platform.oss;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.platform.common.baseCommon;
import com.platform.validator.group.AliyunGroup;
import com.platform.validator.group.QcloudGroup;
import com.platform.validator.group.QiniuGroup;

public abstract class baseOss extends baseCommon {

	/**
	 * 这里配置oss单元测试的初始化对象和方法，被其他对象继承
	 * 配置单元测试的用例都在这里，将来整体做map对象用例
	 */
	public CloudStorageConfig config;
	
	// 如果是真，则跳过测试，上传开源为了防止私人信息泄漏
	public boolean skipTest=true;
	
 
	// src/test/resources/images
	public String updateFilePath ="images/boombox.png";
	
	
	    //七牛绑定的域名
	    private String qiniuDomain="七牛绑定的域名";
	    //七牛路径前缀
	    private String qiniuPrefix="七牛路径前缀";
	    //七牛ACCESS_KEY
	    private String qiniuAccessKey="七牛AccessKey不能为空";
	    //七牛SECRET_KEY
	    private String qiniuSecretKey= "七牛SecretKey不能为空";
	    //七牛存储空间名
	    private String qiniuBucketName= "七牛空间名不能为空";

	    //阿里云绑定的域名
	    private String aliyunDomain= "阿里云绑定的域名不能为空";
	    //阿里云路径前缀
	    private String aliyunPrefix="阿里云路径前缀";
	    //阿里云EndPoint
	    private String aliyunEndPoint = "阿里云EndPoint不能为空";
	    //阿里云AccessKeyId
	    private String aliyunAccessKeyId= "阿里云AccessKeyId不能为空";
	    //阿里云AccessKeySecret
	    private String aliyunAccessKeySecret= "阿里云AccessKeySecret不能为空";
	    //阿里云BucketName
	    private String aliyunBucketName= "阿里云BucketName不能为空";

	    //腾讯云绑定的域名
	    private String qcloudDomain="https://paddy-1256559626.cosbj.myqcloud.com";
	    //腾讯云路径前缀
	    private String qcloudPrefix="images/demo";
	    //腾讯云AppId
	    private Integer qcloudAppId=1256559626; 
	    //腾讯云SecretId
	    private String qcloudSecretId="AKIDhDUVN21geRyGI5KuwiOp1xe5GBrHiLzO";
	    //腾讯云SecretKey
	    private String qcloudSecretKey="NVRAdy98rKXWFiuqWw60e2A24rOL3Wjg";
	    //腾讯云BucketName
	    private String qcloudBucketName="paddy-1256559626";
	    //腾讯云COS所属地区
	    private String qcloudRegion="ap-beijing";	 
	 
	    
	 
	 void init(){
		 
		 // 阿里云存储配置初始化
		  config=new CloudStorageConfig();
		  config.setAliyunAccessKeyId(aliyunAccessKeyId);
		  config.setAliyunAccessKeySecret(aliyunAccessKeySecret);
		  config.setAliyunBucketName(aliyunBucketName);
		  config.setAliyunDomain(aliyunDomain);
		  config.setAliyunEndPoint(aliyunEndPoint);
		  config.setAliyunPrefix(aliyunPrefix);
		  
		// 腾讯云存储配置初始化
		  config.setQcloudAppId(qcloudAppId);
		  config.setQcloudBucketName(qcloudBucketName);
		  config.setQcloudDomain(qcloudDomain);
		  config.setQcloudPrefix(qcloudPrefix);
		  config.setQcloudRegion(qcloudRegion);
		  config.setQcloudSecretId(qcloudSecretId);
		  config.setQcloudSecretKey(qcloudSecretKey);
		  
		// 七牛存储配置初始化
		  config.setQiniuAccessKey(qiniuAccessKey);
		  config.setQiniuBucketName(qiniuBucketName);
		  config.setQiniuDomain(qiniuDomain);
		  config.setQiniuPrefix(qiniuPrefix);
		  config.setQiniuSecretKey(qiniuSecretKey);
		 
		  
	 }
	
	 
	 
}
