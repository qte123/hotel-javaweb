package com.tafleo.dao.room;

import com.tafleo.pojo.Room;
import com.tafleo.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoomDao {
    //添加房间
    public int add(Connection connection, Room room) throws SQLException;

    //查询所有房间,获取房间列表
    public List<Room> getRoomList(Connection connection) throws SQLException;

    //通过房间号查询房间
    public Room getRoomByNumber(Connection connection, String roomNumber) throws SQLException;

    //根据身份证号码查询
    public Room getRoomByIDNumber(Connection connection, String userIDNumber) throws SQLException;

    //根据房型查询房间列表
    public List<Room> getRoomListByType(Connection connection, int roomType) throws SQLException;

    //删除房间
    public int deleteRoom(Connection connection, String roomNumber) throws Exception;

    //根据预定人的身份证号删除房间
    public int deleteRoomByID(Connection connection, String userIDNumber) throws Exception;

    //移除入住人
    public int removeUserByID(Connection connection, String userIDNumber) throws Exception;

    //修改房间信息
    public int modify(Connection connection, Room room) throws Exception;

    //计算剩余房型数量
    public int getRoomCount(Connection connection, int roomType) throws Exception;

    //查询房间价格
    public int getRoomPrice(Connection connection, int roomType) throws Exception;

    //获取房间的总数
    public int getRoomNumber(Connection connection, int roomType) throws Exception;

    //获得房间列表 通过条件查询-roomList
    public List<Room> getRoomListByM(Connection connection, int roomType, int currentPageNo, int pageSize) throws SQLException;

    //计算选择房型收入
    public int roomTotalPrice(Connection connection, int roomType) throws SQLException;

    //查询已订和待退的房型数量
    public int roomTotalCount(Connection connection, int roomType) throws SQLException;
}
