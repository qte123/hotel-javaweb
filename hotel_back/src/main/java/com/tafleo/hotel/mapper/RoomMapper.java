package com.tafleo.hotel.mapper;

import com.tafleo.hotel.pojo.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 房间表 Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Repository
public interface RoomMapper extends BaseMapper<Room> {
    //获取房间的总数 动态SQL xml实现
    public int getRoomNumber(Map map);

    //获得房间列表 通过条件查询-roomList 动态SQL xml实现
    public List<Room> getRoomListByM(Map map);
}
