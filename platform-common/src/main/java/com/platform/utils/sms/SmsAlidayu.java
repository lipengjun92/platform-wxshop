package com.platform.utils.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.platform.utils.CharUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 阿里大鱼短信
 */
public class SmsAlidayu {

    private static Log logger = LogFactory.getLog(SmsAlidayu.class);

    private static SmsAlidayu smsAlidayu;
    public static String url = "http://gw.api.taobao.com/router/rest";
    public static String apikey = "111111";
    public static String secret = "1111111111111111111111111111111111";

    private SmsAlidayu() {
    }

    public static String sendTplShortMessage(String param, String mobile, String smsTemplateCode) {
        if (null == smsAlidayu) {
            smsAlidayu = new SmsAlidayu();
        }
        TaobaoClient client = new DefaultTaobaoClient(url, apikey, secret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend(CharUtil.getRandomNum(32));
        req.setSmsType("normal");
        req.setSmsFreeSignName("云杉智慧");
        req.setSmsParamString(param);
        req.setRecNum(mobile);
        req.setSmsTemplateCode(smsTemplateCode);
        String result = "99";
        try {
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            String resBody = rsp.getBody();
            logger.info("**********阿里大于反馈信息：" + resBody);
            JSONObject bodyMap = JSON.parseObject(resBody);
            result = getSendMsCode(bodyMap);
        } catch (Exception err) {
            err.printStackTrace();
            result = "99";
        }
        return result;
    }

    /**
     * 提取编码
     */
    private static String getSendMsCode(Map resultMap) {
        Set keySet = resultMap.keySet();
        Iterator keyIte = keySet.iterator();
        while (keyIte.hasNext()) {
            String key = (String) keyIte.next();
            if (key.equals("code") || key.equals("err_code")) {
                if (null != resultMap.get(key) && resultMap.get(key) instanceof String) {
                    String val = (String) resultMap.get(key);
                    return val;
                } else if (null != resultMap.get(key) && resultMap.get(key) instanceof Integer) {
                    Integer val = (Integer) resultMap.get(key);
                    return val.toString();
                }

            }
            Object valObj = resultMap.get(key);
            if (valObj instanceof Map) {
                Map valMap = (Map) valObj;
                return getSendMsCode(valMap);
            }
        }
        return null;
    }
}
