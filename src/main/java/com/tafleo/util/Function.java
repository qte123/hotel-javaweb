package com.tafleo.util;

import com.tafleo.dao.BaseDao;
import com.tafleo.pojo.Room;
import com.tafleo.service.room.RoomService;
import com.tafleo.service.room.RoomServiceImpl;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

//功能类
public class Function {

    public static int A_PRICE = 199;  //普通单间
    public static int B_PRICE = 499;  //豪华单间
    public static int C_PRICE = 2999;  //普通双间
    public static int D_PRICE = 6666; //贵宾套房
    public static int E_PRICE = 66666; //总统套房

    //邀请码
    public static String sSignal;

    //静态代码块，类加载的时侯就初始化了
    static {
        Properties properties = new Properties();
        //通过类加载器读取对应的资源
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("sSignal.properties");
        try {
            properties.load(is);
            sSignal = properties.getProperty("sSignal");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}