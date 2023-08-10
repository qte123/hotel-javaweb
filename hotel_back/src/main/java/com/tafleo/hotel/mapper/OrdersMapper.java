package com.tafleo.hotel.mapper;

import com.tafleo.hotel.pojo.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tafleo.hotel.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {
    //获取订单的总数 动态SQL xml实现
    public int getOrderCount(Map map) ;

    //获取订单列表 通过条件查询-orderList 动态SQL xml实现
    public List<User> getOrderList(Map map) ;
}
