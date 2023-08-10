package com.tafleo.servlet.administrator;

import com.tafleo.common.Common;
import com.tafleo.pojo.Administrator;
import com.tafleo.service.administrator.AdministratorService;
import com.tafleo.service.administrator.AdministratorServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdministratorServlet extends HttpServlet implements Common {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        selectFunction(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }

    @Override
    public void selectFunction(HttpServletRequest req, HttpServletResponse resp) {
        String method = req.getParameter("method");
        if ("delete".equals(method) && method != null) {
            this.deleteAdministrator(req, resp);
        } else if ("modify".equals(method) && method != null) {
            this.modifyAdministrator(req, resp);
        } else if ("modifyPwd".equals(method) && method != null) {
            this.modifyPassword(req, resp);
        }
    }

    //删除管理员
    public void deleteAdministrator(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        AdministratorService administratorService = new AdministratorServiceImpl();
        try {
            PrintWriter out = resp.getWriter();
            if (administratorService.deleteAdministrator(username)) {
                out.write("true");
            } else {
                out.write("false");
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //修改管理员
    public void modifyAdministrator(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        AdministratorService administratorService = new AdministratorServiceImpl();
        Administrator admin = administratorService.getAdmin(username);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        admin.setEmail(email);
        admin.setModifyDate(date);
        try {
            PrintWriter out = resp.getWriter();
            if (administratorService.modify(admin)) {
                out.write("true");
            } else {
                out.write("false");
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //修改密码
    public void modifyPassword(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        System.out.println(username);
        System.out.println(oldPassword);
        System.out.println(newPassword);
        AdministratorService administratorService = new AdministratorServiceImpl();
        Administrator administrator = null;
        administrator = administratorService.login(username, oldPassword);

        try {
            PrintWriter out = resp.getWriter();
            if (administrator != null) {
                System.out.println(administrator);
                if (administratorService.updatePwd(username, newPassword)) {
                    out.write("true");
                } else {
                    out.write("false");
                }
            } else {
                out.write("false");
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
