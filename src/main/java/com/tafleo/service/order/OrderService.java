package com.tafleo.service.order;

import com.tafleo.pojo.Order;
import com.tafleo.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    //添加订单
    public boolean add(Order order);

    //获得订单列表
    public List<Order> getOrderList();

    //根据房型获得订单列表
    public List<Order> getOrderListByType(int roomType);

    //通过身份证号查询订单
    public Order getOrder(String userIDNumber);

    //删除订单
    public boolean deleteOrder(String userIDNumber);

    //修改订单
    public boolean modify(Order order);

    //获取订单的总数
    public int getOrderCount(int roomType) ;

    //获取订单列表 通过条件查询-orderList
    public List<User> getOrderList(int roomType, int currentPageNo, int pageSize) ;

}
