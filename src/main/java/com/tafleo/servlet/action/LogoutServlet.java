package com.tafleo.servlet.action;

import com.tafleo.common.Common;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LogoutServlet extends HttpServlet implements Common {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        selectFunction(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }

    //功能选择
    public void selectFunction(HttpServletRequest req, HttpServletResponse resp) {
        try {
            logout(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //登出功能
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //移除用户的Constants.USER_SESSION
        req.getSession().invalidate();
        PrintWriter out = resp.getWriter();
        out.write("true");
        out.flush();
        out.close();
    }
}
