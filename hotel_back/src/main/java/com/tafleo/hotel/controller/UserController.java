package com.tafleo.hotel.controller;


import com.alibaba.fastjson.JSON;
import com.tafleo.hotel.pojo.Income;
import com.tafleo.hotel.pojo.Orders;
import com.tafleo.hotel.pojo.Room;
import com.tafleo.hotel.pojo.User;
import com.tafleo.hotel.service.IncomeService;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private IncomeService incomeService;

    @RequestMapping("/query")
    public String query(String queryname, int roomType, int pageIndex) {
        List<User> userList;

        //第一次走这个请求一定是第一页，页面大小固定的
        //int pageSize=5;//可以把这个写到配置文件中，方便后期修改
        int currentPageNo = 1;
        if (Objects.equals(queryname, "")) {
            queryname = null;
        }
        if (pageIndex != 0) {
            currentPageNo = pageIndex;
        }

        //获取用户的总数(分页：上一页 下一页)
        int totalCount = userService.getUserCount(queryname, roomType);

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
        userList = userService.getUserList(queryname, roomType, currentPageNo, Constants.pageSize);
        System.out.println(userList);
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            Room room = roomService.getRoomByIDNumber(u.getIDNumber());
            String s = u.getIDNumber() + "*" + room.getRoomType() + "#" + room.getRoomNumber();
            u.setIDNumber(s);
            userList.remove(i);
            userList.add(i, u);
        }
        return JSON.toJSONString(userList);
    }

    @RequestMapping("/userList")
    public String getUserList() {
        List<User> userList = userService.getUserList();
        return JSON.toJSONString(userList);
    }

    @RequestMapping("/select")
    public String userSelect(String userIDNumber) {
        User user = userService.getCurrentUser(userIDNumber);
        return JSON.toJSONString(user);
    }

    @RequestMapping("/modify")
    public String userModify(String name, String age, String sex, String IDNumber, String phoneNumber, int roomType, String roomNumber) {
        String userIDNumber = IDNumber.substring(0, IDNumber.indexOf("*"));
        User user = userService.getCurrentUser(userIDNumber);
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        user.setPhoneNumber(phoneNumber);
        Room roomByNumber = roomService.getRoomByNumber(roomNumber);
        int status = roomByNumber.getStatus();
        if (status == Constants.ROOM_STATUS_0) {
            roomByNumber.setUserIDNumber(userIDNumber);
            roomByNumber.setStatus(Constants.ROOM_STATUS_1);
            Room roomByIDNumber = roomService.getRoomByIDNumber(userIDNumber);
            roomByIDNumber.setUserIDNumber("");
            roomByIDNumber.setStatus(Constants.ROOM_STATUS_0);
            Orders order = orderService.getOrder(userIDNumber);
            order.setRoomType(roomType);
            Income lastIncome = incomeService.getLastIncome();
            int oldprice = lastIncome.getTotalIncome() - order.getPrice();
            int days = (int) ((order.getExitTime().getTime() - order.getEnterTime().getTime()) / (1000 * 60 * 60 * 24));
            int orderprice = roomService.getRoomPrice(roomType) * days;
            int newprice = oldprice + orderprice;
            order.setPrice(orderprice);
            lastIncome.setTotalIncome(newprice);
            if ((userService.modify(user) != 0) && (roomService.modify(roomByIDNumber) != 0) && (roomService.modify(roomByNumber) != 0) && (orderService.modify(order) != 0) && (incomeService.modify(lastIncome) != 0)) {
                return "true";
            } else {
                return "false";
            }
        } else {
            return "false";
        }
    }

    @RequestMapping("/delete")
    public String userDelete(String IDNumber) {
        if ((userService.deleteUser(IDNumber) != 0) && (roomService.removeUserByID(IDNumber) != 0) && (orderService.deleteOrder(IDNumber) != 0)) {
            return "true";
        } else {
            return "false";
        }
    }

    @RequestMapping("/double")
    public String getUserListByDouble(String name, int roomType) {
        List<User> userList = userService.getUserListByDouble(name, roomType);
        return JSON.toJSONString(userList);
    }

    @RequestMapping("/byname")
    public String getUserListByName(String name) {
        List<User> userList = userService.getUserListByName(name);
        return JSON.toJSONString(userList);
    }
}

