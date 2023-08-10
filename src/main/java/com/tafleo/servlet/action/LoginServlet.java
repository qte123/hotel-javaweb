package com.tafleo.servlet.action;

import com.tafleo.common.Common;
import com.tafleo.pojo.Administrator;
import com.tafleo.pojo.Income;
import com.tafleo.pojo.Room;
import com.tafleo.service.administrator.AdministratorService;
import com.tafleo.service.administrator.AdministratorServiceImpl;
import com.tafleo.service.income.IncomeService;
import com.tafleo.service.income.IncomeServiceImpl;
import com.tafleo.service.room.RoomService;
import com.tafleo.service.room.RoomServiceImpl;
import com.tafleo.util.Constants;
import com.tafleo.util.Function;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
用户信息接收类
 */

public class LoginServlet extends HttpServlet implements Common {
    public static int A_PRICE = 199;  //普通单间
    public static int B_PRICE = 499;  //豪华单间
    public static int C_PRICE = 2999;  //普通双间
    public static int D_PRICE = 6666; //贵宾套房
    public static int E_PRICE = 66666; //总统套房


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
            login(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //登录功能
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("LoginServlet--start.....");
        //获取用户名和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        AdministratorService administratorService = new AdministratorServiceImpl();
        Administrator administrator = administratorService.login(username, password);
        //和数据库中的密码进行对比，调用业务层
        PrintWriter out = resp.getWriter();
        if (administrator != null) {//查有此人，可以登录
            //将用户的信息放到Session里面
            HttpSession session = req.getSession();
            session.setAttribute(Constants.USERNAME_SESSION, administrator.getUsername()); //用户登录加入到session中
            RoomService roomService = new RoomServiceImpl();
            List<Room> roomList = roomService.getRoomList();
            IncomeService incomeService = new IncomeServiceImpl();
            Income income = incomeService.getIncome();
            if (income == null) {
                income = new Income();
                income.setId("1");
                income.setDailyIncome(0);
                income.setTotalIncome(0);
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                income.setUpdateTime(date);
                incomeService.add(income);
            } else {
                Function.OLD_PRICE = income.getTotalIncome();
            }
            if (roomList == null || roomList.size() == 0) {
                int sum = 1;
                for (int i = 1; i <= 5; i++) {
                    for (int j = 1; j <= 10; j++) {
                        Room room = new Room();
                        room.setId(sum + "");
                        String num = j < 10 ? "0" + j : "10";
                        if (i == 1) {
                            room.setRoomNumber("A-0" + num);
                            room.setRoomType(Constants.ROOM_TYPE_0);
                            room.setPrice(A_PRICE);
                        } else if (i == 2) {
                            room.setRoomNumber("B-0" + num);
                            room.setRoomType(Constants.ROOM_TYPE_1);
                            room.setPrice(B_PRICE);
                        } else if (i == 3) {
                            room.setRoomNumber("C-0" + num);
                            room.setRoomType(Constants.ROOM_TYPE_2);
                            room.setPrice(C_PRICE);
                        } else if (i == 4) {
                            room.setRoomNumber("D-0" + num);
                            room.setRoomType(Constants.ROOM_TYPE_3);
                            room.setPrice(D_PRICE);
                        } else {
                            room.setRoomNumber("E-0" + num);
                            room.setRoomType(Constants.ROOM_TYPE_4);
                            room.setPrice(E_PRICE);
                        }
                        room.setStatus(Constants.ROOM_STATUS_0);
                        Date date = null;
                        try {
                            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                            room.setAddDate(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        roomService.add(room);
                        sum++;
                    }
                }
            }
            //跳转到主页
            //resp.sendRedirect("html/menu.html");
            out.write("true");
        } else {//查无此人，无法登录
            //resp.sendRedirect("html/login.html");
            out.write("false");
        }
        out.flush();
        out.close();
    }
}
