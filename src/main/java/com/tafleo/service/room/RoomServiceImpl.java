package com.tafleo.service.room;

import com.tafleo.dao.BaseDao;
import com.tafleo.dao.room.RoomDao;
import com.tafleo.dao.room.RoomDaoImpl;
import com.tafleo.dao.user.UserDao;
import com.tafleo.dao.user.UserDaoImpl;
import com.tafleo.pojo.Room;
import com.tafleo.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomServiceImpl implements RoomService {
    //业务层都会调用dao层，所以我们要引入Dao层
    private RoomDao roomDao;

    public RoomServiceImpl() {
        roomDao = new RoomDaoImpl();
    }

    //添加房间
    @Override
    public boolean add(Room room) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            int updateRows = roomDao.add(connection, room);
            connection.commit();
            if (updateRows > 0) {
                flag = true;
                System.out.println("add success!");
            } else {
                System.out.println("add failed!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            //在service层进行connection连接的关闭
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    //获取房间列表信息
    @Override
    public List<Room> getRoomList() {
        Connection connection = null;
        List<Room> roomList = null;
        try {
            connection = BaseDao.getConnection();
            roomList = roomDao.getRoomList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return roomList;
    }

    @Override
    public Room getRoomByNumber(String roomNumber) {
        Connection connection = null;
        Room room = null;

        try {
            connection = BaseDao.getConnection();
            room = roomDao.getRoomByNumber(connection, roomNumber);
        } catch (Exception e) {
            e.printStackTrace();
            room = null;
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return room;
    }

    @Override
    public boolean deleteRoom(String roomNumber) {
        Connection connection = null;
        boolean flag = false;
        connection = BaseDao.getConnection();
        try {
            if (roomDao.deleteRoom(connection, roomNumber) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    //根据预定人的身份证号删除房间
    public boolean deleteRoomByID(String userIDNumber) {
        Connection connection = null;
        boolean flag = false;
        connection = BaseDao.getConnection();
        try {
            if (roomDao.deleteRoomByID(connection, userIDNumber) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    public boolean removeUserByID(String userIDNumber) {
        Connection connection = null;
        boolean flag = false;
        connection = BaseDao.getConnection();
        try {
            if (roomDao.removeUserByID(connection, userIDNumber) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    @Override
    public boolean modify(Room room) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if (roomDao.modify(connection, room) > 0)
                flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    //计算剩余房型数量
    @Override
    public int getRoomCount(int roomType) {
        int count = 0;
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            count = roomDao.getRoomCount(connection, roomType);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }

    @Override
    public Room getRoomByIDNumber(String userIDNumber) {
        Connection connection = null;
        Room room = null;
        try {
            connection = BaseDao.getConnection();
            room = roomDao.getRoomByIDNumber(connection, userIDNumber);
        } catch (Exception e) {
            e.printStackTrace();
            room = null;
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return room;
    }

    @Override
    public List<Room> getRoomListByType(int roomType) {
        Connection connection = null;
        List<Room> roomList = null;
        try {
            connection = BaseDao.getConnection();
            roomList = roomDao.getRoomListByType(connection, roomType);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return roomList;
    }


    //查询房间价格
    public int getRoomPrice(int roomType) {
        Connection connection = null;
        Integer price = null;
        try {
            connection = BaseDao.getConnection();
            price = roomDao.getRoomPrice(connection, roomType);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return price;
    }


    //获取房间的总数
    @Override
    public int getRoomNumber(int roomType) {
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseDao.getConnection();
            count = roomDao.getRoomNumber(connection, roomType);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }

    //获得房间列表 通过条件查询-roomList
    @Override
    public List<Room> getRoomListByM(int roomType, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<Room> roomList = null;
        try {
            connection = BaseDao.getConnection();
            roomList = roomDao.getRoomListByM(connection, roomType, currentPageNo, pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return roomList;
    }

    @Override
    public int roomTotalPrice(int roomType) {
        Connection connection = null;
        Integer price = null;
        try {
            connection = BaseDao.getConnection();
            price = roomDao.roomTotalPrice(connection, roomType);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return price;
    }

    //查询已订和待退的房型数量
    public int roomTotalCount(int roomType) {
        int count = 0;
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            count = roomDao.roomTotalCount(connection, roomType);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }
}
