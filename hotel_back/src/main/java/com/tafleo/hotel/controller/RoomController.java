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
import com.tafleo.hotel.utils.Function;
import com.tafleo.hotel.utils.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 房间表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Controller
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private IncomeService incomeService;

    @RequestMapping("/reserve")
    public String reserve(String name, String sex, String age, String phone, String number, int select, String enterTime, String exitTime, String message) {
        User user = new User();
        Orders order = new Orders();
        System.out.println(name);
        Date enterTimeDate = null;
        Date exitTimeDate = null;
        System.out.println(enterTime);
        System.out.println(exitTime);
        try {
            enterTimeDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(enterTime);
            exitTimeDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(exitTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        user.setId(uuid);
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        user.setPhoneNumber(phone);
        user.setIDNumber(number);
        int price;
        int roomType;
        int days = (int) ((exitTimeDate.getTime() - enterTimeDate.getTime()) / (1000 * 60 * 60 * 24));
        Function.A_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_0);
        Function.B_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_1);
        Function.C_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_2);
        Function.D_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_3);
        Function.E_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_4);
        if (Constants.ROOM_TYPE_0 == select) {
            roomType = Constants.ROOM_TYPE_0;
            price = Function.A_PRICE * days;
        } else if (Constants.ROOM_TYPE_1 == select) {
            roomType = Constants.ROOM_TYPE_1;
            price = Function.B_PRICE * days;
        } else if (Constants.ROOM_TYPE_2 == select) {
            roomType = Constants.ROOM_TYPE_2;
            price = Function.C_PRICE * days;
        } else if (Constants.ROOM_TYPE_3 == select) {
            roomType = Constants.ROOM_TYPE_3;
            price = Function.D_PRICE * days;
        } else {
            roomType = Constants.ROOM_TYPE_4;
            price = Function.E_PRICE * days;
        }
        user.setMessage(message);
        order.setId(uuid);
        order.setUserIDNumber(number);
        order.setRoomType(roomType);
        order.setEnterTime(enterTimeDate);
        order.setExitTime(exitTimeDate);
        order.setOrderType(Constants.ORDER_TYPE_0);
        order.setPrice(price);
        User currentUser = userService.getCurrentUser(number);
        Orders order1 = orderService.getOrder(number);
        boolean flag = false;
        if (currentUser == null && order1 == null) {
            flag = (userService.add(user) != 0) && (orderService.add(order) != 0);
        }
        if (flag) {
            return "redirect:/success";
        } else {
            return "redirect:/fail";
        }
    }

    //获取房间列表
    @RequestMapping("/roomList")
    @ResponseBody
    public String getRoomList() {
        getPrice(roomService);
        List<Room> roomList = roomService.getRoomList();
        return JSON.toJSONString(roomList);
    }

    //删除房间
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRoom(String roomNumber) {
        getPrice(roomService);
        Room room = roomService.getRoomByNumber(roomNumber);
        //将房间置为废弃
        room.setStatus(Constants.ROOM_STATUS_3);

        if (roomService.modify(room) != 0) {
            return "true";
        } else {
            return "false";
        }
    }

    //添加房间
    @RequestMapping("/add")
    @ResponseBody
    public String addRoom(String roomNumber) {
        getPrice(roomService);
        Room room = roomService.getRoomByNumber(roomNumber);
        //将房间置为空房
        room.setStatus(Constants.ROOM_STATUS_0);

        if (roomService.modify(room) != 0) {
            return "true";
        } else {
            return "false";
        }
    }

    //修改房间
    @RequestMapping("/modify")
    @ResponseBody
    public String modifyRoom(String roomNumber, int price, int status, String userIDNumber) {
        getPrice(roomService);
        Room room = roomService.getRoomByNumber(roomNumber);
        room.setPrice(price);
        room.setStatus(status);
        room.setUserIDNumber(userIDNumber);
        if (roomService.modify(room) != 0) {
            return "true";
        } else {
            return "false";
        }
    }

    //查询房间
    @RequestMapping("/select")
    @ResponseBody
    public String selectRoom(String roomNumber) {
        getPrice(roomService);
        Room room = roomService.getRoomByNumber(roomNumber);
        return JSON.toJSONString(room);
    }

    //计算剩余房型数量
    @RequestMapping("/count")
    @ResponseBody
    public String getRoomCount(int roomType) {
        getPrice(roomService);
        int roomCount = roomService.getRoomCount(roomType);
        return roomCount + "";

    }

    //根据身份证号码查询
    @RequestMapping("/roomByNumber")
    @ResponseBody
    public String getRoomByIDNumber(String userIDNumber) {
        getPrice(roomService);
        Room room = roomService.getRoomByIDNumber(userIDNumber);
        return JSON.toJSONString(room);
    }

    //根据房型查询房间列表
    @RequestMapping("/roomByType")
    @ResponseBody
    public String getRoomListByType(int roomType) {
        getPrice(roomService);
        List<Room> roomList = roomService.getRoomListByType(roomType);
        return JSON.toJSONString(roomList);
    }

    //接收退房请求
    @RequestMapping("/removeRoom")
    @ResponseBody
    public String removeRoom(String userIDNumber) {
        getPrice(roomService);
        Room room = roomService.getRoomByIDNumber(userIDNumber);
        //将房间置为待退房
        room.setStatus(Constants.ROOM_STATUS_2);

        if (roomService.modify(room) != 0) {
            return "true";
        } else {
            return "false";
        }
    }

    //获得剩余房间数列表
    @RequestMapping("/numberList")
    @ResponseBody
    public String getNumberList() {
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
        return JSON.toJSONString(countList);
    }

    //获得房间价格列表
    @RequestMapping("/priceList")
    @ResponseBody
    public String getPriceList() {
        getPrice(roomService);
        List<Integer> priceList = new ArrayList<>();
        priceList.add(Function.A_PRICE);
        priceList.add(Function.B_PRICE);
        priceList.add(Function.C_PRICE);
        priceList.add(Function.D_PRICE);
        priceList.add(Function.E_PRICE);
        return JSON.toJSONString(priceList);
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
    @RequestMapping("/query")
    @ResponseBody
    public String query(int roomType, int pageIndex) {
        System.out.println(pageIndex);
        List<Room> roomList;

        //第一次走这个请求一定是第一页，页面大小固定的
        //int pageSize=5;//可以把这个写到配置文件中，方便后期修改
        int currentPageNo = 1;
        if (pageIndex != 0) {
            currentPageNo = pageIndex;
        }
        System.out.println(currentPageNo);
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
        int totalPageCount = pageSupport.getTotalPageCount()+1;
        System.out.println(totalPageCount+"tt");
        //控制首页与尾页
        //如果页面要小于1，就显示第一页的东西
        if (totalPageCount < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {//当前页面大于了最后一页
            currentPageNo = totalPageCount;
        }
        System.out.println(currentPageNo+"abc");
        //获取用户列表展示
        roomList = roomService.getRoomListByM(roomType, currentPageNo, Constants.pageSize);
        return JSON.toJSONString(roomList);
    }


    //同意用户预定，并分配房间
    @RequestMapping("/acceptRoom")
    @ResponseBody
    public String acceptRoom(String userIDNumber, String roomNumber) {
        System.out.println(roomNumber);
        Room room = roomService.getRoomByIDNumber(userIDNumber);
        if (room == null) {
            Room room1 = roomService.getRoomByNumber(roomNumber);
            System.out.println(room1);
            int status = room1.getStatus();
            System.out.println(status);
            if (status == Constants.ROOM_STATUS_0) {
                room1.setUserIDNumber(userIDNumber);
                room1.setStatus(Constants.ROOM_STATUS_1);
                Orders order = orderService.getOrder(userIDNumber);
                order.setOrderType(Constants.ORDER_TYPE_1);
                Income income = incomeService.getLastIncome();
                income.setTotalIncome(income.getTotalIncome()+order.getPrice());
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                income.setUpdateTime(date);
                incomeService.add(income);
                if ((roomService.modify(room1) != 0)&&(orderService.modify(order)!=0)) {
                    return "true";
                } else {
                    return "false";
                }
            } else {
                return "false";
            }
        } else {
            return "false";
        }
    }


    //管理员同意退房
    @RequestMapping("/removeAccept")
    @ResponseBody
    public String removeAccept(String roomNumber) {
        Room room = roomService.getRoomByNumber(roomNumber);
        String userIDNumber = room.getUserIDNumber();
        room.setStatus(Constants.ROOM_STATUS_0);
        room.setUserIDNumber("");
        Orders order = orderService.getOrder(userIDNumber);
        order.setOrderType(Constants.ORDER_TYPE_2);
        orderService.modify(order);
        if ((userService.deleteUser(userIDNumber) != 0) && (orderService.deleteOrder(userIDNumber) != 0) && (roomService.modify(room) != 0)) {
            return "true";
        } else {
            return "false";
        }
    }

    //管理员拒绝退房
    @RequestMapping("/removeRefuse")
    @ResponseBody
    public String removeRefuse(String roomNumber) {
        System.out.println(roomNumber);
        Room room = roomService.getRoomByNumber(roomNumber);
        room.setStatus(Constants.ROOM_STATUS_1);
        if (roomService.modify(room) != 0) {
            return "true";
        } else {
            return "false";
        }
    }

    //修改房间价格
    @RequestMapping("/modifyPrice")
    @ResponseBody
    public String modifyPrice(int roomType, int price) {
        List<Room> roomList = roomService.getRoomListByType(roomType);
        for (Room room : roomList) {
            room.setPrice(price);
            if(roomService.modify(room)==0){
                return "false";
            }
        }
        return "true";
    }
}

