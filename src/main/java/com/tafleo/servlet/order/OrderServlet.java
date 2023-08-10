package com.tafleo.servlet.order;

import com.alibaba.fastjson.JSONArray;
import com.tafleo.common.Common;
import com.tafleo.pojo.Order;
import com.tafleo.pojo.Room;
import com.tafleo.pojo.User;
import com.tafleo.service.order.OrderService;
import com.tafleo.service.order.OrderServiceImpl;
import com.tafleo.service.room.RoomService;
import com.tafleo.service.room.RoomServiceImpl;
import com.tafleo.service.user.UserService;
import com.tafleo.service.user.UserServiceImpl;
import com.tafleo.util.Constants;
import com.tafleo.util.JSONUtil;
import com.tafleo.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServlet extends HttpServlet implements Common {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        selectFunction(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void selectFunction(HttpServletRequest req, HttpServletResponse resp) {
        String method = req.getParameter("method");
        if ("modify".equals(method) && method != null) {
            this.modifyOrder(req, resp);
        } else if ("delete".equals(method) && method != null) {
            this.deleteOrder(req, resp);
        } else if ("select".equals(method) && method != null) {
            this.selectOrder(req, resp);
        } else if ("orderList".equals(method) && method != null) {
            this.getOrderList(req, resp);
        } else if ("selectByDouble".equals(method) && method != null) {
            this.selectOrderDouble(req, resp);
        } else if ("query".equals(method) && method != null) {
            this.query(req, resp);
        } else if ("accept".equals(method) && method != null) {
            this.orderAccept(req, resp);
        }
    }

    //修改订单
    public void modifyOrder(HttpServletRequest req, HttpServletResponse resp) {
        int roomType = Integer.parseInt(req.getParameter("roomType"));
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
        int orderType = Integer.parseInt(req.getParameter("orderType"));
        int price = Integer.parseInt(req.getParameter("price"));
        String userIDNumber = req.getParameter("userIDNumber");
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.getOrder(userIDNumber);
        order.setRoomType(roomType);
        order.setEnterTime(enterTimeDate);
        order.setExitTime(exitTimeDate);
        order.setOrderType(orderType);
        order.setPrice(price);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        order.setModifyDate(date);
        try {
            PrintWriter out = resp.getWriter();
            if (orderService.modify(order)) {
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

    //删除订单
    public void deleteOrder(HttpServletRequest req, HttpServletResponse resp) {
        String userIDNumber = req.getParameter("userIDNumber");
        OrderService orderService = new OrderServiceImpl();
        try {
            PrintWriter out = resp.getWriter();
            if (orderService.deleteOrder(userIDNumber)) {
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

    //查询订单
    public void selectOrderDouble(HttpServletRequest req, HttpServletResponse resp) {
        String userIDNumber = req.getParameter("userIDNumber");
        String name = req.getParameter("name");
        OrderService orderService = new OrderServiceImpl();
        UserService userService = new UserServiceImpl();
        User user = userService.getCurrentUser(userIDNumber, name);
        if (user != null) {
            Order order = orderService.getOrder(userIDNumber);
            try {
                PrintWriter out = resp.getWriter();
                if (order != null) {
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
    }

    //查询订单
    public void selectOrder(HttpServletRequest req, HttpServletResponse resp) {
        String userIDNumber = req.getParameter("userIDNumber");
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.getOrder(userIDNumber);
        if (order != null) {
            String s = JSONUtil.entityToJSON(order);
            try {
                PrintWriter out = resp.getWriter();
                out.write(s);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //获得订单列表
    public void getOrderList(HttpServletRequest req, HttpServletResponse resp) {
        List<Order> orderList = null;
        OrderService orderService = new OrderServiceImpl();
        orderList = orderService.getOrderList();
        //把roomList转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = null;
        try {
            outPrintWriter = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outPrintWriter.write(JSONArray.toJSONString(orderList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //接受退房订单
    public void orderAccept(HttpServletRequest req, HttpServletResponse resp) {
        String userIDNumber = req.getParameter("userIDNumber");
        OrderService orderService = new OrderServiceImpl();
        UserService userService = new UserServiceImpl();
        RoomService roomService = new RoomServiceImpl();

        Room room = roomService.getRoomByIDNumber(userIDNumber);
        if (room != null) {
            room.setStatus(Constants.ROOM_STATUS_2);
            roomService.modify(room);
        } else {
            orderService.deleteOrder(userIDNumber);
            userService.deleteUser(userIDNumber);
        }
        try {
            PrintWriter out = resp.getWriter();
            out.write("true");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据条件查询订单
    public void query(HttpServletRequest req, HttpServletResponse resp) {
        //从前端获取数据
        String temp = req.getParameter("roomType");
        String pageIndex = req.getParameter("pageIndex");
        int roomType = 0;

        //获取用户列表
        OrderService orderService = new OrderServiceImpl();
        List<User> userList;

        //第一次走这个请求一定是第一页，页面大小固定的
        //int pageSize=5;//可以把这个写到配置文件中，方便后期修改
        int currentPageNo = 1;
        if (temp != null && !"".equals(temp)) {
            roomType = Integer.parseInt(temp);//给查询赋值
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户的总数(分页：上一页 下一页)
        int totalCount = orderService.getOrderCount(roomType);

        //页数工具类
        PageSupport pageSupport = new PageSupport();
        //当前页面位置
        pageSupport.setCurrentPageNo(currentPageNo);
        //页面大小，一页多少用户
        pageSupport.setPageSize(Constants.pageSize);
        //所有页面的用户人数
        pageSupport.setTotalCount(totalCount);
        //分页后，页面的个数
        int totalPageCount = pageSupport.getTotalPageCount();
        //控制首页与尾页
        //如果页面要小于1，就显示第一页的东西
        if (totalPageCount < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {//当前页面大于了最后一页
            currentPageNo = totalPageCount;
        }
        //创建无房间号用户列表
        List<User> notRoomList = new ArrayList<>();
        //获取用户列表展示
        userList = orderService.getOrderList(roomType, currentPageNo, Constants.pageSize);
        RoomService roomService = new RoomServiceImpl();
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            Room room = roomService.getRoomByIDNumber(u.getIDNumber());
            if (room == null) {
                System.out.println(u.getIDNumber());
                Order order = orderService.getOrder(u.getIDNumber());
                String s = u.getIDNumber() + "-" + order.getRoomType();
                u.setIDNumber(s);
                notRoomList.add(u);
            }
        }
        userList.clear();
        String s = JSONUtil.entityListToJSON(notRoomList);
        System.out.println(s);
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
