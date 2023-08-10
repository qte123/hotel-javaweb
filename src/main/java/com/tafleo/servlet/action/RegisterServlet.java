package com.tafleo.servlet.action;

import com.tafleo.common.Common;
import com.tafleo.pojo.Administrator;
import com.tafleo.service.administrator.AdministratorService;
import com.tafleo.service.administrator.AdministratorServiceImpl;
import com.tafleo.util.Function;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RegisterServlet extends HttpServlet implements Common {
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
            this.register(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //注册功能
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String sSignal = req.getParameter("sSignal");
        System.out.println(username);
        System.out.println(password);
        System.out.println(email);
        System.out.println(sSignal);
        if (!sSignal.equals(Function.sSignal)) {
            resp.sendRedirect("html/no.html");
        } else {
            Administrator administrator = new Administrator();
            AdministratorService administratorService = new AdministratorServiceImpl();
            administrator.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            administrator.setUsername(username);
            administrator.setPassword(password);
            administrator.setEmail(email);
            try {
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                administrator.setAddDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (administratorService.add(administrator)) {
                resp.sendRedirect("html/yes.html");
            } else {
                resp.sendRedirect("html/no.html");
            }
        }
    }
}
