package com.tafleo.hotel.service;

import com.tafleo.hotel.pojo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tafleo.hotel.pojo.User;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
public interface OrderService extends IService<Orders> {
    //添加订单
    public int add(Orders order);

    //获得订单列表
    public List<Orders> getOrderList();

    //根据房型获得订单列表
    public List<Orders> getOrderListByType(int roomType);

    //通过身份证号查询订单
    public Orders getOrder(String userIDNumber);

    //删除订单
    public int deleteOrder(String userIDNumber);

    //修改订单
    public int modify(Orders order);

    //获取订单的总数 动态SQL xml实现
    public int getOrderCount(int roomType) ;

    //获取订单列表 通过条件查询-orderList 动态SQL xml实现
    public List<User> getOrderList(int roomType, int currentPageNo, int pageSize) ;

}
