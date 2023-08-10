package com.tafleo.service.order;

import com.tafleo.dao.BaseDao;
import com.tafleo.dao.order.OrderDao;
import com.tafleo.dao.order.OrderDaoImpl;
import com.tafleo.pojo.Order;
import com.tafleo.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    //业务层都会调用dao层，所以我们要引入Dao层
    private OrderDao orderDao;

    public OrderServiceImpl() {
        orderDao = new OrderDaoImpl();
    }

    //添加订单
    @Override
    public boolean add(Order order) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            int updateRows = orderDao.add(connection, order);
            connection.commit();
            if (updateRows > 0) {
                flag = true;
                System.out.println("add success!");
            } else {
                System.out.println("add failed!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            //在service层进行connection连接的关闭
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    //获取订单列表
    @Override
    public List<Order> getOrderList() {
        Connection connection = null;
        List<Order> orderList = null;
        try {
            connection = BaseDao.getConnection();
            orderList = orderDao.getOrderList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return orderList;
    }

    //根据房型获取订单列表
    @Override
    public List<Order> getOrderListByType(int roomType) {
        Connection connection = null;
        List<Order> orderList = null;
        try {
            connection = BaseDao.getConnection();
            orderList = orderDao.getOrderListByType(connection, roomType);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return orderList;
    }

    //通过身份证号查询订单
    @Override
    public Order getOrder(String userIDNumber) {
        Connection connection = null;
        Order order = null;
        try {
            connection = BaseDao.getConnection();
            //通过业务层调用对应的具体的数据库操作
            order = orderDao.getOrder(connection, userIDNumber);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return order;
    }

    //删除订单
    @Override
    public boolean deleteOrder(String userIDNumber) {
        Connection connection = null;
        boolean flag = false;
        connection = BaseDao.getConnection();
        try {
            if (orderDao.deleteOrder(connection, userIDNumber) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    //修改订单
    @Override
    public boolean modify(Order order) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if (orderDao.modify(connection, order) > 0)
                flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    //获取订单的总数
    @Override
    public int getOrderCount(int roomType) {
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseDao.getConnection();
            count = orderDao.getOrderCount(connection, roomType);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }

    @Override
    public List<User> getOrderList(int roomType, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;

        try {
            connection = BaseDao.getConnection();
            userList = orderDao.getOrderList(connection, roomType, currentPageNo, pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }
}
