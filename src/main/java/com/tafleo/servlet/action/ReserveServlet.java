package com.tafleo.servlet.action;

import com.tafleo.common.Common;
import com.tafleo.pojo.Order;
import com.tafleo.pojo.User;
import com.tafleo.service.order.OrderService;
import com.tafleo.service.order.OrderServiceImpl;
import com.tafleo.service.user.UserService;
import com.tafleo.service.user.UserServiceImpl;
import com.tafleo.util.Constants;
import com.tafleo.util.Function;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ReserveServlet extends HttpServlet implements Common {
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
            this.reserve(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //订购功能
    public void reserve(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User();
        Order order = new Order();
        UserService userService = new UserServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        String name = new String(req.getParameter("name").getBytes("ISO8859-1"), "utf-8");
        String sex = new String(req.getParameter("sex").getBytes("ISO8859-1"), "utf-8");
        String age = req.getParameter("age");
        String phoneNumber = req.getParameter("phone");
        String IDNumber = req.getParameter("number");
        int select = Integer.parseInt(req.getParameter("select"));
        System.out.println(select);
        String enterTime = req.getParameter("enter-time");
        String exitTime = req.getParameter("exit-time");
        Date enterTimeDate = null;
        Date exitTimeDate = null;
        try {
            enterTimeDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(enterTime);
            exitTimeDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(exitTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String message = new String(req.getParameter("message").getBytes("ISO8859-1"), "utf-8");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        user.setId(uuid);
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        user.setPhoneNumber(phoneNumber);
        user.setIDNumber(IDNumber);
        int price;
        int roomType;
        if (Constants.ROOM_TYPE_0 == select) {
            roomType = Constants.ROOM_TYPE_0;
            price = Function.A_PRICE;
        } else if (Constants.ROOM_TYPE_1 == select) {
            roomType = Constants.ROOM_TYPE_1;
            price = Function.B_PRICE;
        } else if (Constants.ROOM_TYPE_2 == select) {
            roomType = Constants.ROOM_TYPE_2;
            price = Function.C_PRICE;
        } else if (Constants.ROOM_TYPE_3 == select) {
            roomType = Constants.ROOM_TYPE_3;
            price = Function.D_PRICE;
        } else {
            roomType = Constants.ROOM_TYPE_4;
            price = Function.E_PRICE;
        }
        user.setMessage(message);

        order.setId(uuid);
        order.setUserIDNumber(IDNumber);
        order.setRoomType(roomType);
        order.setEnterTime(enterTimeDate);
        order.setExitTime(exitTimeDate);
        order.setOrderType(Constants.ORDER_TYPE_0);
        order.setPrice(price);
        try {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            user.setAddDate(date);
            order.setAddDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User currentUser = userService.getCurrentUser(IDNumber);
        Order order1 = orderService.getOrder(IDNumber);
        boolean flag = false;
        if (currentUser == null && order1 == null) {
            flag = userService.add(user) && orderService.add(order);
        }
        if (flag) {
            resp.sendRedirect("html/success.html");
        } else {
            resp.sendRedirect("html/fail.html");
        }
    }
}
