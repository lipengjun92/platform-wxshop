package com.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.platform.dao.SysLogDao;
import com.platform.entity.SysLogEntity;
import com.platform.service.SysLogService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public SysLogEntity queryObject(Long id) {
        return sysLogDao.queryObject(id);
    }

    @Override
    public List<SysLogEntity> queryList(Map<String, Object> map) {
        List<SysLogEntity> list = sysLogDao.queryList(map);

        for (SysLogEntity sysLogEntity : list) {
            String str = getIpDetails(sysLogEntity.getIp());
            JSONObject jsonObject = null;
            try {
                jsonObject = JSONObject.parseObject(str.toString());
                sysLogEntity.setIp(sysLogEntity.getIp() + ":" + jsonObject.getString("country") + " " + jsonObject.getString("province") + " "
                        + jsonObject.getString("city") + " " + jsonObject.getString("district") + " " + jsonObject.getString("isp"));
            } catch (Exception e) {
            }

        }
        return list;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysLogDao.queryTotal(map);
    }

    @Override
    public void save(SysLogEntity sysLog) {
        sysLogDao.save(sysLog);
    }

    @Override
    public void update(SysLogEntity sysLog) {
        sysLogDao.update(sysLog);
    }

    @Override
    public void delete(Long id) {
        sysLogDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        sysLogDao.deleteBatch(ids);
    }

    /**
     * 向指定URL发送GET方法的请求
     */
    public static String getIpDetails(String ip) {
        BufferedReader in = null;
        try {
            URL realUrl = new URL("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
