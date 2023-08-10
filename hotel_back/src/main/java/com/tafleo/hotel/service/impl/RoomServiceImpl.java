package com.tafleo.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tafleo.hotel.mapper.OrdersMapper;
import com.tafleo.hotel.pojo.Orders;
import com.tafleo.hotel.pojo.Room;
import com.tafleo.hotel.mapper.RoomMapper;
import com.tafleo.hotel.service.RoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tafleo.hotel.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 房间表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public int add(Room room) {
        return roomMapper.insert(room);
    }

    @Override
    public List<Room> getRoomList() {
        return roomMapper.selectList(null);
    }

    @Override
    public Room getRoomByIDNumber(String userIDNumber) {
        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("userIDNumber", userIDNumber).ne("status", 0);
        return roomMapper.selectOne(wrapper);
    }

    @Override
    public List<Room> getRoomListByType(int roomType) {
        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("roomType", roomType).eq("status", 0);
        return roomMapper.selectList(wrapper);
    }

    @Override
    public Room getRoomByNumber(String roomNumber) {
        return roomMapper.selectById(roomNumber);
    }

    @Override
    public int deleteRoom(String roomNumber) {
        return roomMapper.deleteById(roomNumber);
    }

    @Override
    public int deleteRoomByID(String userIDNumber) {
        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("userIDNumber", userIDNumber);
        return roomMapper.delete(wrapper);
    }

    @Override
    public int removeUserByID(String userIDNumber) {
        Room room = getRoomByIDNumber(userIDNumber);
        room.setUserIDNumber("");
        room.setStatus(Constants.ROOM_STATUS_0);
        return roomMapper.updateById(room);
    }

    @Override
    public int modify(Room room) {
        return roomMapper.updateById(room);
    }

    @Override
    public int getRoomCount(int roomType) {
        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("roomType", roomType).eq("status", 0);
        return roomMapper.selectCount(wrapper);
    }


    @Override
    public int getRoomPrice(int roomType) {
        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("roomType", roomType);
        Room room = roomMapper.selectList(wrapper).get(0);
        return room.getPrice();
    }

    @Override
    public int getRoomNumber(int roomType) {
        HashMap<String, Integer> map = new HashMap<>();
        if(roomType==-1){
            map.put("roomType", null);
        }else {
            map.put("roomType",roomType);
        }

        return roomMapper.getRoomNumber(map);
    }

    @Override
    public List<Room> getRoomListByM(int roomType, int currentPageNo, int pageSize) {
        currentPageNo = (currentPageNo - 1) * pageSize;
        System.out.println(currentPageNo);
        System.out.println(pageSize);
        HashMap<String, Integer> map = new HashMap<>();
        if(roomType==-1){
            map.put("roomType", null);
        }else {
            map.put("roomType",roomType);
        }
        map.put("currentPageNo", currentPageNo);
        map.put("pageSize", pageSize);
        return roomMapper.getRoomListByM(map);
    }

    @Override
    public int roomTotalPrice(int roomType) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("roomType", roomType).eq("orderType", 1);
        System.out.println(roomType+"ro");
        int count = ordersMapper.selectCount(wrapper);
        List<Orders> ordersList = ordersMapper.selectList(wrapper);
        int price=0;
        if (ordersList.size()!=0){
            price = ordersList.get(0).getPrice();
        }
        return count * price;
    }

    @Override
    public int roomTotalCount(int roomType) {
        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("roomType", roomType).eq("status", 1);
        QueryWrapper<Room> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("roomType", roomType).eq("status", 2);
        return roomMapper.selectCount(wrapper) + roomMapper.selectCount(wrapper2);
    }
}
