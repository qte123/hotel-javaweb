package com.tafleo.filter;

import com.tafleo.pojo.Administrator;
import com.tafleo.util.Constants;

import java.io.IOException;

import java.io.PrintWriter;

import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;

import javax.servlet.FilterChain;

import javax.servlet.FilterConfig;

import javax.servlet.ServletException;

import javax.servlet.ServletRequest;

import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

public class SysFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("---------------------------->开始");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        String usernameSession = (String) session.getAttribute(Constants.USERNAME_SESSION);
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        resp.setDateHeader("Expires", 0); // Proxies.
        if (usernameSession != null) {// 如果现在存在了session，则请求向下继续传递
            filterChain.doFilter(servletRequest, servletResponse);
        } else {// 跳转到提示登陆页面
            resp.sendRedirect("/hotel/html/error.html");
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }
}