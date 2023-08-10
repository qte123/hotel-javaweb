package com.tafleo.dao.user;

import com.tafleo.pojo.Room;
import com.tafleo.pojo.User;
import com.tafleo.service.room.RoomService;
import com.tafleo.service.room.RoomServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserDao {
    //添加用户
    public int add(Connection connection, User user) throws SQLException;

    //通过身份证号查询用户
    public User getCurrentUser(Connection connection, String IDNumber) throws SQLException;

    //通过身份证号和姓名查询用户
    public User getCurrentUser(Connection connection, String IDNumber, String name) throws SQLException;

    //删除用户
    public int deleteUser(Connection connection, String IDNumber) throws Exception;

    //修改用户
    public int modify(Connection connection, User user) throws Exception;

    //获得用户列表
    public List<User> getUserList(Connection connection) throws Exception;

    //根据姓名模糊查询列表
    public List<User> getUserListByName(Connection connection, String name) throws Exception;

    //通过姓名和房型双重查询
    public List<User> getUserListByDouble(Connection connection, String name, int roomType) throws Exception;

    //获取用户的总数
    public int getUserCount(Connection connection, String name,int roomType) throws Exception;

    //获取用户列表 通过条件查询-userList
    public List<User> getUserList(Connection connection, String name, int roomType,int currentPageNo, int pageSize) throws SQLException;
}