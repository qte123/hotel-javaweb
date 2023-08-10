package com.tafleo.servlet.room;

import com.alibaba.fastjson.JSONArray;
import com.tafleo.common.Common;
import com.tafleo.pojo.Income;
import com.tafleo.pojo.Order;
import com.tafleo.pojo.Room;
import com.tafleo.service.income.IncomeService;
import com.tafleo.service.income.IncomeServiceImpl;
import com.tafleo.service.order.OrderService;
import com.tafleo.service.order.OrderServiceImpl;
import com.tafleo.service.room.RoomService;
import com.tafleo.service.room.RoomServiceImpl;
import com.tafleo.service.user.UserService;
import com.tafleo.service.user.UserServiceImpl;
import com.tafleo.util.Constants;
import com.tafleo.util.Function;
import com.tafleo.util.JSONUtil;
import com.tafleo.util.PageSupport;

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

public class RoomServlet extends HttpServlet implements Common {
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
        if ("roomList".equals(method) && method != null) {
            this.getRoomList(req, resp);
        } else if ("delete".equals(method) && method != null) {
            this.deleteRoom(req, resp);
        } else if ("add".equals(method) && method != null) {
            this.addRoom(req, resp);
        } else if ("modify".equals(method) && method != null) {
            this.modifyRoom(req, resp);
        } else if ("select".equals(method) && method != null) {
            this.selectRoom(req, resp);
        } else if ("count".equals(method) && method != null) {
            this.getRoomCount(req, resp);
        } else if ("roomByNumber".equals(method) && method != null) {
            this.getRoomByIDNumber(req, resp);
        } else if ("roomByType".equals(method) && method != null) {
            this.getRoomListByType(req, resp);
        } else if ("removeRoom".equals(method) && method != null) {
            this.removeRoom(req, resp);
        } else if ("numberList".equals(method) && method != null) {
            this.getNumberList(req, resp);
        } else if ("priceList".equals(method) && method != null) {
            this.getPriceList(req, resp);
        } else if ("query".equals(method) && method != null) {
            this.query(req, resp);
        } else if ("acceptRoom".equals(method) && method != null) {
            this.acceptRoom(req, resp);
        } else if ("removeAccept".equals(method) && method != null) {
            this.removeAccept(req, resp);
        } else if ("removeRefuse".equals(method) && method != null) {
            this.removeRefuse(req, resp);
        } else if ("modifyPrice".equals(method) && method != null) {
            this.modifyPrice(req, resp);
        }
    }

    //获取房间列表
    public void getRoomList(HttpServletRequest req, HttpServletResponse resp) {
        List<Room> roomList = null;
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        roomList = roomService.getRoomList();
        //把roomList转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = null;
        try {
            outPrintWriter = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outPrintWriter.write(JSONArray.toJSONString(roomList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //删除房间
    public void deleteRoom(HttpServletRequest req, HttpServletResponse resp) {
        String roomNumber = req.getParameter("roomNumber");
        System.out.println(roomNumber);
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        Room room = roomService.getRoomByNumber(roomNumber);
        //将房间置为废弃
        room.setStatus(Constants.ROOM_STATUS_3);
        try {
            PrintWriter out = resp.getWriter();
            if (roomService.modify(room)) {
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

    //添加房间
    public void addRoom(HttpServletRequest req, HttpServletResponse resp) {
        String roomNumber = req.getParameter("roomNumber");
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        Room room = roomService.getRoomByNumber(roomNumber);
        //将房间置为空房
        room.setStatus(Constants.ROOM_STATUS_0);
        try {
            PrintWriter out = resp.getWriter();
            if (roomService.modify(room)) {
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

    //修改房间
    public void modifyRoom(HttpServletRequest req, HttpServletResponse resp) {
        String roomNumber = req.getParameter("roomNumber");
        int price = Integer.parseInt(req.getParameter("price"));
        int status = Integer.parseInt(req.getParameter("status"));
        String userIDNumber = req.getParameter("userIDNumber");
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        Room room = roomService.getRoomByNumber(roomNumber);
        room.setPrice(price);
        room.setStatus(status);
        room.setUserIDNumber(userIDNumber);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        room.setModifyDate(date);
        try {
            PrintWriter out = resp.getWriter();
            if (roomService.modify(room)) {
                out.write("true");
            } else {
                out.write("false");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询房间
    public void selectRoom(HttpServletRequest req, HttpServletResponse resp) {
        String roomNumber = req.getParameter("roomNumber");
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        Room room = roomService.getRoomByNumber(roomNumber);
        String s = JSONUtil.entityToJSON(room);
        try {
            PrintWriter out = resp.getWriter();
            out.write(s);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //计算剩余房型数量
    public void getRoomCount(HttpServletRequest req, HttpServletResponse resp) {
        int roomType = Integer.parseInt(req.getParameter("roomType"));
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        int roomCount = roomService.getRoomCount(roomType);
        try {
            PrintWriter out = resp.getWriter();
            out.write(roomCount + "");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //根据身份证号码查询
    public void getRoomByIDNumber(HttpServletRequest req, HttpServletResponse resp) {
        String userIDNumber = req.getParameter("userIDNumber");
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        Room room = roomService.getRoomByIDNumber(userIDNumber);
        String s = JSONUtil.entityToJSON(room);
        try {
            PrintWriter out = resp.getWriter();
            out.write(s);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据房型查询房间列表
    public void getRoomListByType(HttpServletRequest req, HttpServletResponse resp) {
        int roomType = Integer.parseInt(req.getParameter("roomType"));
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        List<Room> roomList = roomService.getRoomListByType(roomType);
        String s = JSONUtil.entityListToJSON(roomList);
        try {
            PrintWriter out = resp.getWriter();
            out.write(s);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //接收退房请求
    public void removeRoom(HttpServletRequest req, HttpServletResponse resp) {
        String userIDNumber = req.getParameter("userIDNumber");
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        Room room = roomService.getRoomByIDNumber(userIDNumber);
        //将房间置为待退房
        room.setStatus(Constants.ROOM_STATUS_2);
        try {
            PrintWriter out = resp.getWriter();
            if (roomService.modify(room)) {
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

    //获得剩余房间数列表
    public void getNumberList(HttpServletRequest req, HttpServletResponse resp) {
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        int room1Count = roomService.getRoomCount(Constants.ROOM_TYPE_0);
        int room2Count = roomService.getRoomCount(Constants.ROOM_TYPE_1);
        int room3Count = roomService.getRoomCount(Constants.ROOM_TYPE_2);
        int room4Count = roomService.getRoomCount(Constants.ROOM_TYPE_3);
        int room5Count = roomService.getRoomCount(Constants.ROOM_TYPE_4);
        List<Integer> countList = new ArrayList<>();
        countList.add(room1Count);
        countList.add(room2Count);
        countList.add(room3Count);
        countList.add(room4Count);
        countList.add(room5Count);
        String s = JSONUtil.entityListToJSON(countList);
        try {
            PrintWriter out = resp.getWriter();
            out.write(s);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获得房间价格列表[
    public void getPriceList(HttpServletRequest req, HttpServletResponse resp) {
        RoomService roomService = new RoomServiceImpl();
        getPrice(roomService);
        List<Integer> priceList = new ArrayList<>();
        priceList.add(Function.A_PRICE);
        priceList.add(Function.B_PRICE);
        priceList.add(Function.C_PRICE);
        priceList.add(Function.D_PRICE);
        priceList.add(Function.E_PRICE);
        String s = JSONUtil.entityListToJSON(priceList);
        try {
            PrintWriter out = resp.getWriter();
            out.write(s);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询最新价格
    public void getPrice(RoomService roomService) {
        Function.A_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_0);
        Function.B_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_1);
        Function.C_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_2);
        Function.D_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_3);
        Function.E_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_4);
    }

    //根据条件查询订单
    public void query(HttpServletRequest req, HttpServletResponse resp) {
        //从前端获取数据
        String temp = req.getParameter("roomType");
        String pageIndex = req.getParameter("pageIndex");
        int roomType = 0;

        //获取用户列表
        RoomService roomService = new RoomServiceImpl();
        List<Room> roomList;

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
        int totalCount = roomService.getRoomNumber(roomType);

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
        //获取用户列表展示
        roomList = roomService.getRoomListByM(roomType, currentPageNo, Constants.pageSize);
        String s = JSONUtil.entityListToJSON(roomList);
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


    //同意用户预定，并分配房间
    public void acceptRoom(HttpServletRequest req, HttpServletResponse resp) {
        String userIDNumber = req.getParameter("userIDNumber");
        String roomNumber = req.getParameter("roomNumber");
        System.out.println(roomNumber);
        RoomService roomService = new RoomServiceImpl();
        Room room = roomService.getRoomByIDNumber(userIDNumber);
        IncomeService incomeService = new IncomeServiceImpl();
        try {
            PrintWriter out = resp.getWriter();
            if (room == null) {
                Room room1 = roomService.getRoomByNumber(roomNumber);
                System.out.println(room1);
                int status = room1.getStatus();
                System.out.println(status);
                if (status == Constants.ROOM_STATUS_0) {
                    room1.setUserIDNumber(userIDNumber);
                    room1.setStatus(Constants.ROOM_STATUS_1);
                    Income income = incomeService.getIncome();
                    int oldPrice = income.getTotalIncome();
                    income.setTotalIncome(room1.getPrice() + oldPrice);
                    income.setDailyIncome(room1.getPrice() + oldPrice);
                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    income.setUpdateTime(date);
                    if (roomService.modify(room1) && incomeService.modify(income)) {
                        out.write("true");
                    } else {
                        out.write("false");
                    }
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


    //管理员同意退房
    public void removeAccept(HttpServletRequest req, HttpServletResponse resp) {
        String roomNumber = req.getParameter("roomNumber");
        RoomService roomService = new RoomServiceImpl();
        UserService userService = new UserServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        Room room = roomService.getRoomByNumber(roomNumber);
        String userIDNumber = room.getUserIDNumber();
        room.setStatus(Constants.ROOM_STATUS_0);
        room.setUserIDNumber("");
        try {
            PrintWriter out = resp.getWriter();
            if (userService.deleteUser(userIDNumber) && orderService.deleteOrder(userIDNumber) && roomService.modify(room)) {
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

    //管理员拒绝退房
    public void removeRefuse(HttpServletRequest req, HttpServletResponse resp) {
        String roomNumber = req.getParameter("roomNumber");
        RoomService roomService = new RoomServiceImpl();
        System.out.println(roomNumber);
        Room room = roomService.getRoomByNumber(roomNumber);
        room.setStatus(Constants.ROOM_STATUS_1);
        try {
            PrintWriter out = resp.getWriter();
            if (roomService.modify(room)) {
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

    //修改房间价格
    public void modifyPrice(HttpServletRequest req, HttpServletResponse resp) {
        int roomType = Integer.parseInt(req.getParameter("roomNumber"));
        System.out.println(roomType);
        int price = Integer.parseInt(req.getParameter("price"));
        RoomService roomService = new RoomServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        List<Order> orderList = orderService.getOrderListByType(roomType);
        List<Room> roomList = roomService.getRoomListByType(roomType);
        boolean flag = true;
        for (int i = 0; i < roomList.size(); i++) {
            Room room = roomList.get(i);
            room.setPrice(price);
            if (!roomService.modify(room)) {
                flag = false;
                break;
            }
        }
        boolean flag1 = true;
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            order.setPrice(price);
            if (!orderService.modify(order)) {
                flag1 = false;
                break;
            }
        }
        try {
            PrintWriter out = resp.getWriter();
            if (flag && flag1) {
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
