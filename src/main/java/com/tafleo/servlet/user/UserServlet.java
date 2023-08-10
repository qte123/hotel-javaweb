package com.tafleo.servlet.user;

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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserServlet extends HttpServlet implements Common {
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
        if ("modify".equals(method) && method != null) {
            this.userModify(req, resp);
        } else if ("delete".equals(method) && method != null) {
            this.userDelete(req, resp);
        } else if ("select".equals(method) && method != null) {
            this.userSelect(req, resp);
        } else if ("userList".equals(method) && method != null) {
            this.getUserList(req, resp);
        } else if ("double".equals(method) && method != null) {
            this.getUserListByDouble(req, resp);
        } else if ("userByName".equals(method) && method != null) {
            this.getUserListByName(req, resp);
        } else if ("query".equals(method) && method != null) {
            this.query(req, resp);
        }
    }

    //修改用户
    public void userModify(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("afa");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        String age = req.getParameter("age");
        String ID = req.getParameter("IDNumber");
        String IDNumber = ID.substring(0, ID.indexOf("*"));
        String phoneNumber = req.getParameter("phoneNumber");
        int roomType = Integer.parseInt(req.getParameter("roomType"));
        String roomNumber = req.getParameter("roomNumber");
        UserService userService = new UserServiceImpl();
        RoomService roomService = new RoomServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        User user = userService.getCurrentUser(IDNumber);
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        user.setPhoneNumber(phoneNumber);
        //user.setMessage(message);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setModifyDate(date);
        Room roomByNumber = roomService.getRoomByNumber(roomNumber);
        int status = roomByNumber.getStatus();
        PrintWriter out = null;
        try {
            out = resp.getWriter();

            if (status == Constants.ROOM_STATUS_0) {
                roomByNumber.setUserIDNumber(IDNumber);
                roomByNumber.setStatus(Constants.ROOM_STATUS_1);
                roomByNumber.setModifyDate(date);

                Room roomByIDNumber = roomService.getRoomByIDNumber(IDNumber);
                roomByIDNumber.setUserIDNumber("");
                roomByIDNumber.setStatus(Constants.ROOM_STATUS_0);
                roomByIDNumber.setModifyDate(date);


                Order order = orderService.getOrder(IDNumber);
                order.setRoomType(roomType);
                order.setModifyDate(date);

                resp.setContentType("text/html;charset=UTF-8");
                if (userService.modify(user) && roomService.modify(roomByIDNumber) && roomService.modify(roomByNumber) && orderService.modify(order)) {
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

    //删除用户
    public void userDelete(HttpServletRequest req, HttpServletResponse resp) {
        String IDNumber = req.getParameter("IDNumber");
        UserService userService = new UserServiceImpl();
        RoomService roomService = new RoomServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        try {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            if (userService.deleteUser(IDNumber) && roomService.removeUserByID(IDNumber) && orderService.deleteOrder(IDNumber)) {
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

    //查询用户
    public void userSelect(HttpServletRequest req, HttpServletResponse resp) {
        String IDNumber = req.getParameter("IDNumber");
        System.out.println(IDNumber);
        UserService userService = new UserServiceImpl();
        User user = userService.getCurrentUser(IDNumber);
        String s = JSONUtil.entityToJSON(user);
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

    //获得用户列表
    public void getUserList(HttpServletRequest req, HttpServletResponse resp) {
        List<User> userList = null;
        UserService userService = new UserServiceImpl();
        userList = userService.getUserList();
        //把roomList转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = null;
        try {
            resp.setContentType("text/html;charset=UTF-8");
            outPrintWriter = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outPrintWriter.write(JSONArray.toJSONString(userList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //根据姓名模糊查询列表
    public void getUserListByName(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        UserService userService = new UserServiceImpl();
        List<User> userList = userService.getUserListByName(name);
        String s = JSONUtil.entityListToJSON(userList);
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

    //通过姓名和房型双重查询
    public void getUserListByDouble(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        int roomType = Integer.parseInt(req.getParameter("roomType"));
        UserService userService = new UserServiceImpl();
        List<User> userList = userService.getUserListByDouble(name, roomType);
        String s = JSONUtil.entityListToJSON(userList);
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

    //根据条件查询用户
    public void query(HttpServletRequest req, HttpServletResponse resp) {
        //从前端获取数据
        String queryName = req.getParameter("queryname");
        String temp = req.getParameter("roomType");
        String pageIndex = req.getParameter("pageIndex");
        int roomType = 0;

        //获取用户列表
        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList;

        //第一次走这个请求一定是第一页，页面大小固定的
        //int pageSize=5;//可以把这个写到配置文件中，方便后期修改
        int currentPageNo = 1;
        if (queryName == null) {
            queryName = "";
        }
        if (temp != null && !"".equals(temp)) {
            roomType = Integer.parseInt(temp);//给查询赋值
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户的总数(分页：上一页 下一页)
        int totalCount = userService.getUserCount(queryName, roomType);

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
        RoomService roomService = new RoomServiceImpl();
        //获取用户列表展示
        userList = userService.getUserList(queryName, roomType, currentPageNo, Constants.pageSize);
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            Room room = roomService.getRoomByIDNumber(u.getIDNumber());
            String s = u.getIDNumber() + "*" + room.getRoomType() + "#" + room.getRoomNumber();
            u.setIDNumber(s);
            userList.remove(i);
            userList.add(i, u);
        }
        String s = JSONUtil.entityListToJSON(userList);
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
