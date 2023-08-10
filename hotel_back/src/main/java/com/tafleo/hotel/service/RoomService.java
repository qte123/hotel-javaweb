package com.tafleo.hotel.service;

import com.tafleo.hotel.pojo.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 房间表 服务类
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
public interface RoomService extends IService<Room> {
    //添加房间
    public int add(Room room);

    //查询所有房间,获取房间列表
    public List<Room> getRoomList();

    //根据身份证号码查询
    public Room getRoomByIDNumber(String userIDNumber);

    //根据房型查询房间列表
    public List<Room> getRoomListByType(int roomType);

    //通过房间号查询房间
    public Room getRoomByNumber(String roomNumber);

    //删除房间
    public int deleteRoom(String roomNumber);

    //根据预定人的身份证号删除房间
    public int deleteRoomByID(String userIDNumber);

    public int removeUserByID(String userIDNumber);

    //修改房间信息
    public int modify(Room room);

    //计算剩余房型数量
    public int getRoomCount(int roomType);


    //查询房间价格
    public int getRoomPrice(int roomType);

    //获取房间的总数 动态SQL xml实现
    public int getRoomNumber(int roomType);

    //获得房间列表 通过条件查询-roomList 动态SQL xml实现
    public List<Room> getRoomListByM(int roomType, int currentPageNo, int pageSize);

    //计算选择房型的总收入
    public int roomTotalPrice(int roomType);

    //查询已订和待退的房型数量
    public int roomTotalCount(int roomType);
}
