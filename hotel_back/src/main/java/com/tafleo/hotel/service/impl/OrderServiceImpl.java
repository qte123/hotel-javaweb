package com.tafleo.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tafleo.hotel.pojo.Orders;
import com.tafleo.hotel.mapper.OrdersMapper;
import com.tafleo.hotel.pojo.User;
import com.tafleo.hotel.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tafleo.hotel.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public int add(Orders order) {
        return ordersMapper.insert(order);
    }

    @Override
    public List<Orders> getOrderList() {
        return ordersMapper.selectList(null);
    }

    @Override
    public List<Orders> getOrderListByType(int roomType) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("roomType", roomType);
        return ordersMapper.selectList(wrapper);
    }

    @Override
    public Orders getOrder(String userIDNumber) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.ne("orderType", Constants.ORDER_TYPE_2).eq("userIDNumber", userIDNumber);
        List<Orders> ordersList = ordersMapper.selectList(wrapper);
        if (ordersList.size() != 0) {
            return ordersList.get(0);
        } else return null;
    }

    @Override
    public int deleteOrder(String userIDNumber) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("userIDNumber",userIDNumber);
        Orders orders = ordersMapper.selectOne(wrapper);
        orders.setOrderType(Constants.ORDER_TYPE_2);
        ordersMapper.updateById(orders);
        return ordersMapper.deleteById(orders.getId());
    }

    @Override
    public int modify(Orders order) {
        return ordersMapper.updateById(order);
    }

    @Override
    public int getOrderCount(int roomType) {
        HashMap<String, Integer> map = new HashMap<>();
        if(roomType==-1) {
            map.put("roomType",null);
        }else {
            map.put("roomType", roomType);
        }
        return ordersMapper.getOrderCount(map);
    }

    @Override
    public List<User> getOrderList(int roomType, int currentPageNo, int pageSize) {
        currentPageNo = (currentPageNo - 1) * pageSize;
        HashMap<String, Integer> map = new HashMap<>();
        if(roomType==-1) {
            map.put("roomType",null);
        }else {
            map.put("roomType", roomType);
        }
        map.put("currentPageNo", currentPageNo);
        map.put("pageSize", pageSize);
        return ordersMapper.getOrderList(map);
    }
}
