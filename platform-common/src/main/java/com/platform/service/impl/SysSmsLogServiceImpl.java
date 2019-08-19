package com.platform.service.impl;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.platform.dao.SysSmsLogDao;
import com.platform.entity.SmsConfig;
import com.platform.entity.SysSmsLogEntity;
import com.platform.service.SysConfigService;
import com.platform.service.SysSmsLogService;
import com.platform.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("smsLogService")
public class SysSmsLogServiceImpl implements SysSmsLogService {
    @Autowired
    private SysSmsLogDao smsLogDao;
    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public SysSmsLogEntity queryObject(String id) {
        return smsLogDao.queryObject(id);
    }

    @Override
    public List<SysSmsLogEntity> queryList(Map<String, Object> map) {
        return smsLogDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return smsLogDao.queryTotal(map);
    }

    @Override
    public int save(SysSmsLogEntity smsLog) {
        smsLog.setId(IdUtil.createIdbyUUID());
        return smsLogDao.save(smsLog);
    }

    @Override
    public int update(SysSmsLogEntity smsLog) {
        return smsLogDao.update(smsLog);
    }

    @Override
    public int delete(String id) {
        return smsLogDao.delete(id);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return smsLogDao.deleteBatch(ids);
    }

    @Override
    public SysSmsLogEntity sendSms(SysSmsLogEntity smsLog) {
        //获取云存储配置信息
        SmsConfig config = sysConfigService.getConfigObject(Constant.SMS_CONFIG_KEY, SmsConfig.class);
        if (StringUtils.isNullOrEmpty(config)) {
            throw new RRException("请先配置短信平台信息");
        }
        if (StringUtils.isNullOrEmpty(config.getAppid())) {
            throw new RRException("请先配置短信平台APPID");
        }
        if (StringUtils.isNullOrEmpty(config.getAppkey())) {
            throw new RRException("请先配置短信平台KEY");
        }
        if (StringUtils.isNullOrEmpty(config.getSign())) {
            throw new RRException("请先配置短信平台签名");
        }
        SmsSingleSenderResult result;
        try {
            /**
             * 状态,发送编号,无效号码数,成功提交数,黑名单数和消息，无论发送的号码是多少，一个发送请求只返回一个sendid，如果响应的状态不是“0”，则只有状态和消息
             */
            result = SmsUtil.crSendSms(config.getAppid(), config.getAppkey(), smsLog.getMobile(), smsLog.getTemplateId(), new String[]{smsLog.getCode()}, smsLog.getSign());

            //发送成功
            if (result.result == 0) {
                smsLog.setSendId(result.sid);
                smsLog.setSuccessNum(1);
                smsLog.setReturnMsg(result.errMsg);
            } else {
                //发送失败
                smsLog.setReturnMsg(result.errMsg);
            }
        } catch (Exception e) {
            throw new RRException("短信发送失败");
        }
        smsLog.setSendStatus(result.result);
        try {
            smsLog.setUserId(ShiroUtils.getUserId());
        } catch (Exception e) {
            //外部发送短信
            smsLog.setUserId(1L);
        }
        smsLog.setSign(config.getSign());
        //保存发送记录
        save(smsLog);
        return smsLog;
    }
}
