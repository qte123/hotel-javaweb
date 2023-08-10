package com.tafleo.hotel.service;

import com.tafleo.hotel.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
public interface UserService extends IService<User> {
    //查询用户
    public User getCurrentUser(String IDNumber);

    //通过身份证号和姓名查询用户
    public User getCurrentUser(String IDNumber, String name);

    //添加用户
    public int add(User user);

    //删除用户
    public int deleteUser(String IDNumber);

    //修改用户
    public int modify(User user);

    //获得用户列表
    public List<User> getUserList();

    //根据姓名模糊查询列表
    public List<User> getUserListByName(String name);

    //通过姓名和房型双重查询 动态SQL xml实现
    public List<User> getUserListByDouble(String name, int roomType);

    //获取用户的总数 动态SQL xml实现
    public int getUserCount(String name, int roomType);

    //获取用户列表 通过条件查询-userList 动态SQL xml实现
    public List<User> getUserList(String name, int roomType, int currentPageNo, int pageSize);

}
