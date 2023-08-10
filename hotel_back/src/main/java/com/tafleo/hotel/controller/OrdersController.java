package com.tafleo.hotel.controller;


import com.alibaba.fastjson.JSON;
import com.tafleo.hotel.pojo.Orders;
import com.tafleo.hotel.pojo.Room;
import com.tafleo.hotel.pojo.User;
import com.tafleo.hotel.service.OrderService;
import com.tafleo.hotel.service.RoomService;
import com.tafleo.hotel.service.UserService;
import com.tafleo.hotel.utils.Constants;
import com.tafleo.hotel.utils.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/api/order")
public class OrdersController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;

    //修改订单
    @RequestMapping("/modify")
    public String modifyOrder(int roomType, String enterTime, String exitTime, int orderType, int price, String userIDNumber) {
        Date enterTimeDate = null;
        Date exitTimeDate = null;
        try {
            enterTimeDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(enterTime);
            exitTimeDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(exitTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Orders order = orderService.getOrder(userIDNumber);
        order.setRoomType(roomType);
        order.setEnterTime(enterTimeDate);
        order.setExitTime(exitTimeDate);
        order.setOrderType(orderType);
        order.setPrice(price);
        if (orderService.modify(order) != 0) {
            return "true";
        } else {
            return "false";
        }
    }

    //删除订单
    @RequestMapping("/delete")
    public String deleteOrder(String userIDNumber) {
        if (orderService.deleteOrder(userIDNumber) != 0) {
            return "true";
        } else {
            return "false";
        }
    }

    //查询订单
    @RequestMapping("/selectByDouble")
    public String selectOrderDouble(String userIDNumber, String name) {
        User user = userService.getCurrentUser(userIDNumber, name);
        System.out.println(user+"user");
        if (user != null) {
            Orders order = orderService.getOrder(userIDNumber);
            if (order != null) {
                return "true";
            } else {
                return "false";
            }
        }
        return "false";
    }

    //查询订单
    @RequestMapping("/select")
    public String selectOrder(String userIDNumber) {
        Orders order = orderService.getOrder(userIDNumber);
        if (order != null) {
            return JSON.toJSONString(order);
        }
        return "";
    }

    //获得订单列表
    @RequestMapping("/orderList")
    public String getOrderList() {
        List<Orders>    orderList = orderService.getOrderList();
        return JSON.toJSONString(orderList);
    }

    //接受退房订单
    @RequestMapping("/accept")
    public String orderAccept(String userIDNumber) {
        Room room = roomService.getRoomByIDNumber(userIDNumber);
        if (room != null) {
            room.setStatus(Constants.ROOM_STATUS_2);
            return "false";
        } else {
            Orders order = orderService.getOrder(userIDNumber);
            order.setOrderType(Constants.ORDER_TYPE_2);
            orderService.modify(order);
            orderService.deleteOrder(userIDNumber);
            userService.deleteUser(userIDNumber);
        }
       return "true";
    }

    //根据条件查询订单
    @RequestMapping("/query")
    public String query(int roomType,int pageIndex) {

        //第一次走这个请求一定是第一页，页面大小固定的
        //int pageSize=5;//可以把这个写到配置文件中，方便后期修改
        int currentPageNo = 1;
        if (pageIndex != 0) {
            currentPageNo = pageIndex;
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
        int totalPageCount = pageSupport.getTotalPageCount()+1;
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
        List<User>  userList = orderService.getOrderList(roomType, currentPageNo, Constants.pageSize);
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            Room room = roomService.getRoomByIDNumber(u.getIDNumber());
            if (room == null) {
                System.out.println(u.getIDNumber());
                Orders order = orderService.getOrder(u.getIDNumber());
                System.out.println("order"+order);
                String s = u.getIDNumber() + "-" + order.getRoomType();
                u.setIDNumber(s);
                notRoomList.add(u);
            }
        }
        userList.clear();
       return JSON.toJSONString(notRoomList);
    }
}

