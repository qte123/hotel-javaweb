package com.tafleo.dao.user;

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

public class UserDaoImpl implements UserDao {
    //添加用户
    @Override
    public int add(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int updateRows = 0;
        int count = 0;
        if (connection != null) {
            String sql = "insert into user( id,name,sex,age,phoneNumber,IDNumber,message,addDate,modifyDate) values (?,?,?,?,?,?,?,?,?)";
            Object[] params = {user.getId(), user.getName(), user.getSex(), user.getAge(), user.getPhoneNumber(), user.getIDNumber(), user.getMessage(), user.getAddDate(), user.getModifyDate()};
            updateRows = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, rs);
        }
        return updateRows;
    }

    //通过身份证号码查询用户
    public User getCurrentUser(Connection connection, String IDNumber) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        if (connection != null) {
            String sql = "select * from user where IDNumber=? ";
            Object[] params = {IDNumber};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
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
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return user;
    }

    @Override
    public User getCurrentUser(Connection connection, String IDNumber, String name) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        if (connection != null) {
            String sql = "select * from user where IDNumber=? and name = ?";
            Object[] params = {IDNumber, name};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
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
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return user;
    }

    //删除用户
    public int deleteUser(Connection connection, String IDNumber) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "delete from user where IDNumber = ?";
            Object[] params = {IDNumber};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //修改用户
    public int modify(Connection connection, User user) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "update user set name=?," +
                    "sex=?,age=?,phoneNumber=?,message=?,modifyDate=? where IDNumber = ? ";
            Object[] params = {user.getName(), user.getSex(), user.getAge(),
                    user.getPhoneNumber(),
                    user.getMessage(), user.getModifyDate(), user.getIDNumber()};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //获得用户列表
    @Override
    public List<User> getUserList(Connection connection) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        List<User> userList = new ArrayList<>();
        if (connection != null) {
            String sql = "select * from user";
            Object[] params = {};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
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

    //根据姓名模糊查询列表
    public List<User> getUserListByName(Connection connection, String name) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        List<User> userList = new ArrayList<>();
        if (connection != null) {
            String sql = "select * from user where name like ?";
            String s = "%" + name + "%";
            Object[] params = {s};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
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

    //通过姓名和房型双重查询
    @Override
    public List<User> getUserListByDouble(Connection connection, String name, int roomType) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        List<User> userList = new ArrayList<>();
        if (connection != null) {
            String sql = "select * from user u,room r where u.IDNumber = r.userIDNumber and r.roomType = ? and u.name like ?";
            String s = "%" + name + "%";
            Object[] params = {roomType, s};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
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

    //查询用户总数
    @Override
    public int getUserCount(Connection connection, String name, int roomType) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from user u ,room r where u.IDNumber=r.userIDNumber");
            ArrayList<Object> list = new ArrayList<>();
            if (!StringUtils.isNullOrEmpty(name)) {
                sql.append(" and name like ?");
                list.add("%" + name + "%");//index:0
            }
            if (roomType != -1) {
                sql.append(" and r.roomType = ?");
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

    //获取用户列表 通过条件查询-userList
    public List<User> getUserList(Connection connection, String name, int roomType, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();
        User user = null;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from user u,room r where u.IDNumber = r.userIDNumber");
            List<Object> list = new ArrayList<>();
            if (!StringUtils.isNullOrEmpty(name)) {
                sql.append(" and u.name like ?");
                list.add("%" + name + "%");
            }
            if (roomType != -1) {
                sql.append(" and r.roomType = ?");
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
