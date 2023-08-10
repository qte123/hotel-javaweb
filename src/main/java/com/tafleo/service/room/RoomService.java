package com.tafleo.service.room;

import com.tafleo.pojo.Room;
import com.tafleo.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoomService {
    //添加房间
    public boolean add(Room room);

    //查询所有房间,获取房间列表
    public List<Room> getRoomList();

    //通过房间号查询房间
    public Room getRoomByNumber(String roomNumber);

    //删除房间
    public boolean deleteRoom(String roomNumber);

    //根据预定人的身份证号删除房间
    public boolean deleteRoomByID(String userIDNumber);

    public boolean removeUserByID(String userIDNumber);

    //修改房间信息
    public boolean modify(Room room);

    //计算剩余房型数量
    public int getRoomCount(int roomType);

    //根据身份证号码查询
    public Room getRoomByIDNumber(String userIDNumber);

    //根据房型查询房间列表
    public List<Room> getRoomListByType(int roomType);

    //查询房间价格
    public int getRoomPrice(int roomType);

    //获取房间的总数
    public int getRoomNumber(int roomType);

    //获得房间列表 通过条件查询-roomList
    public List<Room> getRoomListByM(int roomType, int currentPageNo, int pageSize);

    //计算选择房型收入
    public int roomTotalPrice(int roomType);

    //查询已订和待退的房型数量
    public int roomTotalCount(int roomType);
}

