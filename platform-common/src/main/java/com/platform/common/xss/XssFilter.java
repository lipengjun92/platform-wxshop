/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤
 *
 * @author 李鹏军
 */
public class XssFilter implements Filter {
    private String[] excludedPageArray = new String[]{"/service/model/"};

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);

        boolean isExcludedPage = false;
        //判断是否在过滤url之外
        for (String page : excludedPageArray) {
            if (((HttpServletRequest) request).getServletPath().indexOf(page) > -1) {
                isExcludedPage = true;
                break;
            }
        }
        //排除过滤url
        if (isExcludedPage) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(xssRequest, response);
        }
    }

    @Override
    public void destroy() {
    }

}
