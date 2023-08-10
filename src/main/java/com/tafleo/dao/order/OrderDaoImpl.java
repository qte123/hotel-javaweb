package com.tafleo.dao.order;

import com.mysql.jdbc.StringUtils;
import com.tafleo.dao.BaseDao;
import com.tafleo.pojo.Order;
import com.tafleo.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    //添加订单
    @Override
    public int add(Connection connection, Order order) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int updateRows = 0;
        int count = 0;
        if (connection != null) {
            String sql = "insert into orders ( id,userIDNumber,roomType,enterTime,exitTime,orderType,price,addDate,modifyDate ) values ( ?,?,?,?,?,?,?,?,? )";
            Object[] params = {order.getId(), order.getUserIDNumber(), order.getRoomType(), order.getEnterTime(), order.getExitTime(), order.getOrderType(), order.getPrice(), order.getAddDate(), order.getModifyDate()};
            updateRows = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, rs);
        }
        return updateRows;
    }

    //获得订单列表
    @Override
    public List<Order> getOrderList(Connection connection) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Order order = null;
        List<Order> orderList = new ArrayList<>();
        if (connection != null) {
            String sql = "select * from orders";
            Object[] params = {};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                order = new Order();
                order.setId(rs.getString("id"));
                order.setUserIDNumber(rs.getString("userIDNumber"));
                order.setRoomType(rs.getInt("roomType"));
                order.setEnterTime(new Date(rs.getTimestamp("enterTime").getTime()));
                order.setExitTime(new Date(rs.getTimestamp("exitTime").getTime()));
                order.setOrderType(rs.getInt("orderType"));
                order.setPrice(rs.getInt("price"));
                order.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    order.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
                orderList.add(order);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return orderList;
    }

    //获得订单列表
    @Override
    public List<Order> getOrderListByType(Connection connection, int roomType) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Order order = null;
        List<Order> orderList = new ArrayList<>();
        if (connection != null) {
            String sql = "select * from orders where roomType = ?";
            Object[] params = {roomType};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                order = new Order();
                order.setId(rs.getString("id"));
                order.setUserIDNumber(rs.getString("userIDNumber"));
                order.setRoomType(rs.getInt("roomType"));
                order.setEnterTime(new Date(rs.getTimestamp("enterTime").getTime()));
                order.setExitTime(new Date(rs.getTimestamp("exitTime").getTime()));
                order.setOrderType(rs.getInt("orderType"));
                order.setPrice(rs.getInt("price"));
                order.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    order.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
                orderList.add(order);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return orderList;
    }

    //通过身份证号查询订单
    @Override
    public Order getOrder(Connection connection, String userIDNumber) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Order order = null;
        if (connection != null) {
            String sql = "select * from orders where userIDNumber=? and orderType = 0";
            Object[] params = {userIDNumber};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                order = new Order();
                order.setId(rs.getString("id"));
                order.setUserIDNumber(rs.getString("userIDNumber"));
                order.setRoomType(rs.getInt("roomType"));
                order.setEnterTime(new Date(rs.getTimestamp("enterTime").getTime()));
                order.setExitTime(new Date(rs.getTimestamp("exitTime").getTime()));
                order.setOrderType(rs.getInt("orderType"));
                order.setPrice(rs.getInt("price"));
                order.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    order.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return order;
    }

    //删除订单
    @Override
    public int deleteOrder(Connection connection, String userIDNumber) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "delete from orders where userIDNumber = ?";
            Object[] params = {userIDNumber};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //修改订单
    @Override
    public int modify(Connection connection, Order order) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "update orders set roomType=?,enterTime=?,exitTime=?,orderType=?,price=?, modifyDate=? where userIDNumber = ? ";
            Object[] params = {order.getRoomType(), order.getEnterTime(), order.getExitTime(), order.getOrderType(), order.getPrice(),
                    order.getModifyDate(), order.getUserIDNumber()};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //查询订单总数
    @Override
    public int getOrderCount(Connection connection, int roomType) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from user u ,orders o where u.IDNumber=o.userIDNumber");
            ArrayList<Object> list = new ArrayList<>();
            if (roomType != -1) {
                sql.append(" and o.roomType = ?");
                list.add(roomType);
            }
            //怎么把list转换为为数组
            Object[] params = list.toArray();
            String sqlstr = sql.toString();
            rs = BaseDao.execute(connection, pstm, rs, sqlstr, params);
            if (rs.next()) {
                count = rs.getInt("count");//从结果集中获取数量
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return count;
    }

    //获取订单列表 通过条件查询-orderList
    public List<User> getOrderList(Connection connection, int roomType, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();
        User user = null;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from user u,orders o where u.IDNumber = o.userIDNumber");
            List<Object> list = new ArrayList<>();
            if (roomType != -1) {
                sql.append(" and o.roomType = ?");
                list.add(roomType);
            }
            //在数据库中，分页使用 limit startIndex,pageSize; 总数
            //当前页 （当前页-1）*页面大小
            //0,5    1 0   01234
            //5,5    2 5   56789
            //10,5   3 10
            sql.append(" order by u.addDate DESC limit ? , ?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);
            Object[] params = list.toArray();
            String sqlstr = sql.toString();
            rs = BaseDao.execute(connection, pstm, rs, sqlstr, params);
            while (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setSex(rs.getString("sex"));
                user.setAge(rs.getString("age"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setIDNumber(rs.getString("IDNumber"));
                if (rs.getString("message") != null) {
                    user.setMessage(rs.getString("message"));
                }
                user.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    user.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
                userList.add(user);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return userList;
    }
}
