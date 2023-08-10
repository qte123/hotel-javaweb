package com.tafleo.dao.order;

import com.tafleo.pojo.Order;
import com.tafleo.pojo.Room;
import com.tafleo.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    //添加订单
    public int add(Connection connection, Order order) throws SQLException;

    //获得订单列表
    public List<Order> getOrderList(Connection connection) throws SQLException;

    //根据房型获得订单列表
    public List<Order> getOrderListByType(Connection connection, int roomType) throws SQLException;

    //通过身份证号查询订单
    public Order getOrder(Connection connection, String userIDNumber) throws SQLException;

    //删除订单
    public int deleteOrder(Connection connection, String userIDNumber) throws Exception;

    //修改订单
    public int modify(Connection connection, Order order) throws Exception;

    //获取订单的总数
    public int getOrderCount(Connection connection,int roomType) throws Exception;

    //获取订单列表 通过条件查询-orderList
    public List<User> getOrderList(Connection connection,int roomType, int currentPageNo, int pageSize) throws SQLException;
}
