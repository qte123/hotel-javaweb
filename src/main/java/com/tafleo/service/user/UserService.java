package com.tafleo.service.user;

import com.tafleo.pojo.Room;
import com.tafleo.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    //查询用户
    public User getCurrentUser(String IDNumber);

    //通过身份证号和姓名查询用户
    public User getCurrentUser(String IDNumber, String name);

    //添加用户
    public boolean add(User user);

    //删除用户
    public boolean deleteUser(String IDNumber);

    //修改用户
    public boolean modify(User user);

    //获得用户列表
    public List<User> getUserList();

    //根据姓名模糊查询列表
    public List<User> getUserListByName(String name);

    //通过姓名和房型双重查询
    public List<User> getUserListByDouble(String name, int roomType);

    //获取用户的总数
    public int getUserCount(String name, int roomType);

    //获取用户列表 通过条件查询-userList
    public List<User> getUserList(String queryName, int roomType, int currentPageNo, int pageSize);

}
