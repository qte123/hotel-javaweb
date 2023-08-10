package com.tafleo.hotel.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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

@Component
//功能类
public class Function {
    public static int A_PRICE = 199;  //普通单间
    public static int B_PRICE = 499;  //豪华单间
    public static int C_PRICE = 2999;  //普通双间
    public static int D_PRICE = 6666; //贵宾套房
    public static int E_PRICE = 66666; //总统套房
    public static int OLD_PRICE;//总价初值

    //邀请码
    public static String sSignal;

    //静态代码块，类加载的时侯就初始化了
    static {
        Properties properties = new Properties();
        //通过类加载器读取对应的资源
        InputStream is = Function.class.getClassLoader().getResourceAsStream("sSignal.properties");
        try {
            properties.load(is);
            sSignal = properties.getProperty("sSignal");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}