package com.platform.config;

import com.platform.modules.sys.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Slf4j
public class RequestUriLogInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("REQUEST_START_TIME", new Date());
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Date start = (Date) request.getAttribute("REQUEST_START_TIME");
        Date end = new Date();

        log.info("Take " + (end.getTime() - start.getTime()) + "ms Time; " + getRequestInfo(request).toString());
    }

    /**
     * 主要功能:获取请求详细信息
     * 注意事项:无
     *
     * @param request 请求
     * @return 请求信息
     */
    private StringBuilder getRequestInfo(HttpServletRequest request) {
        StringBuilder reqInfo = new StringBuilder();
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String urlPath = urlPathHelper.getLookupPathForRequest(request);
        reqInfo.append(" RequestUri=").append(urlPath);
        reqInfo.append(" IP=").append(getIpAddrByRequest(request));


        String userName = "";
        try {
            SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
            if (sysUser != null) {
                userName = (sysUser.getUserName());
            }
        } catch (Exception e) {

        }
        reqInfo.append(" Operator=").append(userName);
        reqInfo.append(" Parameters=").append(getParameters(request).toString());
        return reqInfo;
    }


    /**
     * 将request查询参数封装至Map
     *
     * @param request 请求
     * @return 参数Map
     */
    private Map<String, Object> getParameters(HttpServletRequest request) {
        Enumeration<String> enume = request.getParameterNames();
        Map<String, Object> map = new HashMap<>(8);
        while (enume.hasMoreElements()) {
            String key = enume.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 主要功能:获取请求方IP
     * 注意事项:无
     *
     * @param request 请求
     * @return String IP
     */
    private String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
