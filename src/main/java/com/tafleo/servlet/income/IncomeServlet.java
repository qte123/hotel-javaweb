package com.tafleo.servlet.income;

import com.tafleo.common.Common;
import com.tafleo.pojo.Income;
import com.tafleo.pojo.Order;
import com.tafleo.service.income.IncomeService;
import com.tafleo.service.income.IncomeServiceImpl;
import com.tafleo.service.order.OrderService;
import com.tafleo.service.order.OrderServiceImpl;
import com.tafleo.service.room.RoomService;
import com.tafleo.service.room.RoomServiceImpl;
import com.tafleo.util.Constants;
import com.tafleo.util.Function;
import com.tafleo.util.JSONUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IncomeServlet extends HttpServlet implements Common {
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
        if ("total".equals(method) && method != null) {
            this.getTotalIncome(req, resp);
        } else if ("daily".equals(method) && method != null) {
            this.getDailyIncome(req, resp);
        } else if ("getIncome".equals(method) && method != null) {
            this.getIncome(req, resp);
        } else if ("delete".equals(method) && method != null) {
            this.deleteIncome(req, resp);
        } else if ("countPrice".equals(method) && method != null) {
            this.countPrice(req, resp);
        }
    }

    //计算总收入
    public void getTotalIncome(HttpServletRequest req, HttpServletResponse resp) {
        int price = 0;
        OrderService orderService = new OrderServiceImpl();
        List<Order> orderList = orderService.getOrderList();
        for (Order order : orderList) {
            if (order.getOrderType() == 0) {
                price += order.getPrice();
            }
        }
        try {
            PrintWriter out = resp.getWriter();
            out.write(price + "");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //计算日收入
    public void getDailyIncome(HttpServletRequest req, HttpServletResponse resp) {
        int price = 0;
        OrderService orderService = new OrderServiceImpl();
        List<Order> orderList = orderService.getOrderList();
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for (Order order : orderList) {
            String time = new SimpleDateFormat("yyyy-MM-dd").format(order.getAddDate());
            if (order.getOrderType() == 0 && now.equals(time)) {
                price += order.getPrice();
            }
        }
        try {
            PrintWriter out = resp.getWriter();
            out.write(price + "");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询收入
    public void getIncome(HttpServletRequest req, HttpServletResponse resp) {
        IncomeService incomeService = new IncomeServiceImpl();
        Income income = incomeService.getIncome();
        String s = JSONUtil.entityToJSON(income);
        try {
            PrintWriter out = resp.getWriter();
            out.write(s);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除收入
    public void deleteIncome(HttpServletRequest req, HttpServletResponse resp) {
        IncomeService incomeService = new IncomeServiceImpl();
        incomeService.deleteIncome();
    }

    //营业额度查询
    public void countPrice(HttpServletRequest req, HttpServletResponse resp) {
        RoomService roomService = new RoomServiceImpl();
        int aPrice = roomService.roomTotalPrice(Constants.ROOM_TYPE_0);
        int bPrice = roomService.roomTotalPrice(Constants.ROOM_TYPE_1);
        int cPrice = roomService.roomTotalPrice(Constants.ROOM_TYPE_2);
        int dPrice = roomService.roomTotalPrice(Constants.ROOM_TYPE_3);
        int ePrice = roomService.roomTotalPrice(Constants.ROOM_TYPE_4);
        int aOrderRoom = roomService.roomTotalCount(Constants.ROOM_TYPE_0);
        int bOrderRoom = roomService.roomTotalCount(Constants.ROOM_TYPE_1);
        int cOrderRoom = roomService.roomTotalCount(Constants.ROOM_TYPE_2);
        int dOrderRoom = roomService.roomTotalCount(Constants.ROOM_TYPE_3);
        int eOrderRoom = roomService.roomTotalCount(Constants.ROOM_TYPE_4);
        int sumPrice = aPrice + bPrice + cPrice + dPrice + ePrice;
        int count = aOrderRoom + bOrderRoom + cOrderRoom + dOrderRoom + eOrderRoom;
        IncomeService incomeService = new IncomeServiceImpl();
        Income income = incomeService.getIncome();
        String s1 = "普通单间*" + aOrderRoom + "#" + Function.A_PRICE + "&" + aPrice + ",";
        String s2 = "豪华单间*" + bOrderRoom + "#" + Function.B_PRICE + "&" + bPrice + ",";
        String s3 = "普通双间*" + cOrderRoom + "#" + Function.C_PRICE + "&" + cPrice + ",";
        String s4 = "贵宾套房*" + dOrderRoom + "#" + Function.D_PRICE + "&" + dPrice + ",";
        String s5 = "总统套房*" + eOrderRoom + "#" + Function.E_PRICE + "&" + ePrice + ",";
        String s6 = "共计*" + count + "#" + "" + "&" + sumPrice + ",";
        String s7 = "累计收入*" + "" + "#" + "" + "&" + income.getTotalIncome();
        String s = s1 + s2 + s3 + s4 + s5 + s6 + s7;
        try {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.write(s);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
