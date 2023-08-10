package com.tafleo.pojo;

import java.util.Date;
import java.util.UUID;

//收入类
public class Income {
    private String id;//收入主键
    private int dailyIncome;//日收入
    private int totalIncome;//总收入
    private Date updateTime;//最近更新时间

    public Income() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(int dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
