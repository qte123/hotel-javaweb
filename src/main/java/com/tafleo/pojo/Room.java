package com.tafleo.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//房间类
public class Room {
    private String id;//房间编号：唯一
    private String roomNumber;//房间号
    private int roomType;//房间类型
    private int price;//房间价格
    private int status;//房间状态 0：空房 1：已预定 2：退房待处理
    private String userIDNumber;//入住人的身份证号
    private Date addDate;//信息添加时间
    private Date modifyDate;//信息修改时间

    public Room() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserIDNumber() {
        return userIDNumber;
    }

    public void setUserIDNumber(String userIDNumber) {
        this.userIDNumber = userIDNumber;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

}
