package com.tafleo.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tafleo.hotel.mapper.RoomMapper;
import com.tafleo.hotel.pojo.Room;
import com.tafleo.hotel.pojo.User;
import com.tafleo.hotel.mapper.UserMapper;
import com.tafleo.hotel.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getCurrentUser(String IDNumber) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("IDNumber",IDNumber);
        List<User> userList = userMapper.selectList(wrapper);
        if (userList.size()!=0){
            return   userList.get(0);
        }
        return null;
    }

    @Override
    public User getCurrentUser(String IDNumber, String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("IDNumber", IDNumber).eq("name", name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public int add(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int deleteUser(String IDNumber) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("IDNumber",IDNumber);
        User user = userMapper.selectOne(wrapper);
        return userMapper.deleteById(user.getId());
    }

    @Override
    public int modify(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public List<User> getUserList() {
        return userMapper.selectList(null);
    }

    @Override
    public List<User> getUserListByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        return userMapper.selectList(wrapper);
    }

    @Override
    public List<User> getUserListByDouble(String name, int roomType) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","%"+name+"%");
        map.put("roomType", roomType);
        return userMapper.getUserListByDouble(map);
    }

    @Override
    public int getUserCount(String name, int roomType) {
        HashMap<String, Object> map = new HashMap<>();
        if (name==null){
            map.put("name", null);
        }else {
            map.put("name", "%"+name+"%");
        }
        if (roomType == -1) {
            map.put("roomType", null);
        } else {
            map.put("roomType", roomType);
        }
        return userMapper.getUserCount(map);
    }

    @Override
    public List<User> getUserList(String name, int roomType, int currentPageNo, int pageSize) {
        currentPageNo = (currentPageNo - 1) * pageSize;
        HashMap<String, Object> map = new HashMap<>();
        if (name==null){
            map.put("name", null);
        }else {
            map.put("name", "%"+name+"%");
        }
        if (roomType == -1) {
            map.put("roomType", null);
        } else {
            map.put("roomType", roomType);
        }
        map.put("currentPageNo", currentPageNo);
        map.put("pageSize", pageSize);
        return userMapper.getUserList(map);
    }
}
