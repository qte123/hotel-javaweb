package com.tafleo.dao.room;

import com.mysql.jdbc.StringUtils;
import com.tafleo.dao.BaseDao;
import com.tafleo.pojo.Room;
import com.tafleo.pojo.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomDaoImpl implements RoomDao {
    @Override
    public int add(Connection connection, Room room) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int updateRows = 0;
        int count = 0;
        if (connection != null) {
            String sql = "insert into room ( id,roomNumber,roomType,price,status,userIDNumber,addDate,modifyDate) values (?,?,?,?,?,?,?,?)";
            Object[] params = {room.getId(), room.getRoomNumber(), room.getRoomType(), room.getPrice(), room.getStatus(), room.getUserIDNumber(), room.getAddDate(), room.getModifyDate()};
            updateRows = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, rs);
        }
        return updateRows;
    }

    @Override
    public List<Room> getRoomList(Connection connection) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Room room = null;
        List<Room> roomList = new ArrayList<>();
        if (connection != null) {
            String sql = "select * from room";
            Object[] params = {};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                room = new Room();
                room.setId(rs.getString("id"));
                room.setRoomType(rs.getInt("roomType"));
                room.setRoomNumber(rs.getString("roomNumber"));
                room.setPrice(rs.getInt("price"));
                room.setStatus(rs.getInt("status"));
                room.setUserIDNumber(rs.getString("userIDNumber"));
                room.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    room.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
                roomList.add(room);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return roomList;
    }


    @Override
    public Room getRoomByIDNumber(Connection connection, String userIDNumber) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Room room = null;
        if (connection != null) {
            String sql = "select * from room where userIDNumber= ? and status=1 or status= 2 ";
            Object[] params = {userIDNumber};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                room = new Room();
                room.setId(rs.getString("id"));
                room.setRoomType(rs.getInt("roomType"));
                room.setRoomNumber(rs.getString("roomNumber"));
                room.setPrice(rs.getInt("price"));
                room.setStatus(rs.getInt("status"));
                room.setUserIDNumber(rs.getString("userIDNumber"));
                room.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    room.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return room;
    }

    //根据房型查询房间列表
    public List<Room> getRoomListByType(Connection connection, int roomType) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Room room = null;
        List<Room> roomList = new ArrayList<>();
        if (connection != null) {
            String sql = "select * from room where roomType= ?";
            Object[] params = {roomType};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                room = new Room();
                room.setId(rs.getString("id"));
                room.setRoomType(rs.getInt("roomType"));
                room.setRoomNumber(rs.getString("roomNumber"));
                room.setPrice(rs.getInt("price"));
                room.setStatus(rs.getInt("status"));
                room.setUserIDNumber(rs.getString("userIDNumber"));
                room.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    room.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
                roomList.add(room);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return roomList;
    }

    //通过房间号查询房间
    @Override
    public Room getRoomByNumber(Connection connection, String roomNumber) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Room room = null;
        if (connection != null) {
            String sql = "select * from room where roomNumber= ? ";
            Object[] params = {roomNumber};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                room = new Room();
                room.setId(rs.getString("id"));
                room.setRoomType(rs.getInt("roomType"));
                room.setRoomNumber(rs.getString("roomNumber"));
                room.setPrice(rs.getInt("price"));
                room.setStatus(rs.getInt("status"));
                room.setUserIDNumber(rs.getString("userIDNumber"));
                room.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    room.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return room;
    }

    //删除房间
    @Override
    public int deleteRoom(Connection connection, String roomNumber) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "delete from room where roomNumber = ?";
            Object[] params = {roomNumber};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //根据预定人的身份证号删除房间
    public int deleteRoomByID(Connection connection, String userIDNumber) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "delete from room where userIDNumber = ?";
            Object[] params = {userIDNumber};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    public int removeUserByID(Connection connection, String userIDNumber) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "update room set userIDNumber='',status = 0 where userIDNumber = ? ";
            Object[] params = {userIDNumber};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }


    //修改房间
    @Override
    public int modify(Connection connection, Room room) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "update room set userIDNumber=?, price=?,status=?,modifyDate=? where roomNumber = ? ";
            Object[] params = {room.getUserIDNumber(), room.getPrice(), room.getStatus(), room.getModifyDate(), room.getRoomNumber()};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //计算剩余房型数量
    @Override
    public int getRoomCount(Connection connection, int roomType) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if (connection != null) {
            String sql = "select * from room where roomType = ? and status = 0 ";
            Object[] params = {roomType};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                count++;
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return count;
    }


    //查询房间价格
    @Override
    public int getRoomPrice(Connection connection, int roomType) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int price = 0;
        if (connection != null) {
            String sql = "select price from room where roomType= ? ";
            Object[] params = {roomType};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                price = rs.getInt("price");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return price;
    }

    //获取房间的总数
    public int getRoomNumber(Connection connection, int roomType) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from room");
            ArrayList<Object> list = new ArrayList<>();
            if (roomType != -1) {
                sql.append(" where roomType = ?");
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

    //获得房间列表 通过条件查询-roomList
    public List<Room> getRoomListByM(Connection connection, int roomType, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Room> roomList = new ArrayList<>();
        Room room = null;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from room");
            List<Object> list = new ArrayList<>();
            if (roomType != -1) {
                sql.append(" where roomType = ?");
                list.add(roomType);
            }
            //在数据库中，分页使用 limit startIndex,pageSize; 总数
            //当前页 （当前页-1）*页面大小
            //0,5    1 0   01234
            //5,5    2 5   56789
            //10,5   3 10
            sql.append(" order by addDate DESC limit ? , ?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);
            Object[] params = list.toArray();
            String sqlstr = sql.toString();
            rs = BaseDao.execute(connection, pstm, rs, sqlstr, params);
            while (rs.next()) {
                room = new Room();
                room.setId(rs.getString("id"));
                room.setRoomType(rs.getInt("roomType"));
                room.setRoomNumber(rs.getString("roomNumber"));
                room.setPrice(rs.getInt("price"));
                room.setStatus(rs.getInt("status"));
                room.setUserIDNumber(rs.getString("userIDNumber"));
                room.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    room.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
                roomList.add(room);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return roomList;
    }

    @Override
    public int roomTotalPrice(Connection connection, int roomType) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int price = 0;
        if (connection != null) {
            String sql = "select * from room where roomType= ? and status= 1";
            Object[] params = {roomType};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                price = price + rs.getInt("price");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return price;
    }

    @Override
    public int roomTotalCount(Connection connection, int roomType) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        PreparedStatement pstm1 = null;
        ResultSet rs1 = null;
        int count = 0;
        int count1 = 0;
        if (connection != null) {
            String sql = "select * from room where roomType = ? and status = 1 ";
            String sql1 = "select * from room where roomType = ? and status = 2 ";
            Object[] params = {roomType};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                count++;
            }
            Object[] params1 = {roomType};
            rs1 = BaseDao.execute(connection, pstm1, rs1, sql1, params1);
            while (rs1.next()) {
                count1++;
            }
            BaseDao.closeResource(null, pstm, rs);
            BaseDao.closeResource(null, pstm1, rs1);
        }
        return count + count1;
    }
}
