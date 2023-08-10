package com.tafleo.pojo;

import java.util.Date;

//订单类
public class Order {
    private String id;//订单编号：唯一
    private String userIDNumber;//入住人的身份证号
    private int  roomType;//房间
    private Date enterTime;//入住时间
    private Date exitTime;//退房时间
    private int orderType;//订单类型 订单类型 0：预定订单  1：废弃订单
    private int price;//房间价格
    private Date addDate;//信息添加时间
    private Date modifyDate;//信息修改时间

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserIDNumber() {
        return userIDNumber;
    }

    public void setUserIDNumber(String userIDNumber) {
        this.userIDNumber = userIDNumber;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
