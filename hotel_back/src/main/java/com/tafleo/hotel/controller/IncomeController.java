package com.tafleo.hotel.controller;


import com.tafleo.hotel.pojo.Income;
import com.tafleo.hotel.pojo.Orders;
import com.tafleo.hotel.service.IncomeService;
import com.tafleo.hotel.service.OrderService;
import com.tafleo.hotel.service.RoomService;
import com.tafleo.hotel.utils.Constants;
import com.tafleo.hotel.utils.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 收入表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/api/income")
public class IncomeController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private IncomeService incomeService;

    @Autowired
    private OrderService orderService;

    //营业额度查询
    @RequestMapping("/countPrice")
    public String countPrice() {
        Function.A_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_0);
        Function.B_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_1);
        Function.C_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_2);
        Function.D_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_3);
        Function.E_PRICE = roomService.getRoomPrice(Constants.ROOM_TYPE_4);
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
        Income income = incomeService.getLastIncome();
        String s1 = "普通单间*" + aOrderRoom + "#" + Function.A_PRICE + "&" + aPrice + ",";
        String s2 = "豪华单间*" + bOrderRoom + "#" + Function.B_PRICE + "&" + bPrice + ",";
        String s3 = "普通双间*" + cOrderRoom + "#" + Function.C_PRICE + "&" + cPrice + ",";
        String s4 = "贵宾套房*" + dOrderRoom + "#" + Function.D_PRICE + "&" + dPrice + ",";
        String s5 = "总统套房*" + eOrderRoom + "#" + Function.E_PRICE + "&" + ePrice + ",";
        String s6 = "共计*" + count + "#" + "" + "&" + sumPrice + ",";
        String s7 = "总收入*" + income.getTotalIncome() + "#" + "" + "&" +"" ;
        return s1 + s2 + s3 + s4 + s5 + s6 + s7;
    }
}

