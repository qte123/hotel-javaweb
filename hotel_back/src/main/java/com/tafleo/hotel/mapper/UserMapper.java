package com.tafleo.hotel.mapper;

import com.tafleo.hotel.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    //通过姓名和房型双重查询 动态SQL xml实现
    public List<User> getUserListByDouble(Map map);

    //获取用户的总数 动态SQL xml实现
    public int getUserCount(Map map);

    //获取用户列表 通过条件查询-userList 动态SQL xml实现
    public List<User> getUserList(Map map);
}
