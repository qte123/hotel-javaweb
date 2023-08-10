package com.tafleo.hotel.controller;


import com.alibaba.fastjson.JSON;
import com.tafleo.hotel.pojo.Administrator;
import com.tafleo.hotel.pojo.Income;
import com.tafleo.hotel.pojo.Room;
import com.tafleo.hotel.service.AdministratorService;
import com.tafleo.hotel.service.IncomeService;
import com.tafleo.hotel.service.RoomService;
import com.tafleo.hotel.utils.Constants;
import com.tafleo.hotel.utils.Function;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Controller
@RequestMapping("/api/admin")
public class AdministratorController {

    public static int A_PRICE = 199;  //普通单间
    public static int B_PRICE = 499;  //豪华单间
    public static int C_PRICE = 2999;  //普通双间
    public static int D_PRICE = 6666; //贵宾套房
    public static int E_PRICE = 66666; //总统套房
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private RoomService roomService;

    @Autowired
    private IncomeService incomeService;

    @RequestMapping("/login")
    @ResponseBody
    public String login(String username, String password) {
        System.out.println("LoginServlet--start.....");
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);//执行登录方法，如果没有异常就说明ok
            //查有此人，可以登录
            //获取当前的用户
            List<Room> roomList = roomService.getRoomList();
            Income income = incomeService.getLastIncome();
            if (income == null) {
                income = new Income();
                income.setId(1);
                income.setTotalIncome(0);
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                income.setUpdateTime(date);
                incomeService.add(income);
            }
            else {
                Function.OLD_PRICE = income.getTotalIncome();
            }
            if (roomList == null || roomList.size() == 0) {
                int sum = 1;
                for (int i = 1; i <= 5; i++) {
                    for (int j = 1; j <= 10; j++) {
                        Room room = new Room();
                        room.setId(sum + "");
                        String num = j < 10 ? "0" + j : "10";
                        if (i == 1) {
                            room.setRoomNumber("A-0" + num);
                            room.setRoomType(Constants.ROOM_TYPE_0);
                            room.setPrice(A_PRICE);
                        } else if (i == 2) {
                            room.setRoomNumber("B-0" + num);
                            room.setRoomType(Constants.ROOM_TYPE_1);
                            room.setPrice(B_PRICE);
                        } else if (i == 3) {
                            room.setRoomNumber("C-0" + num);
                            room.setRoomType(Constants.ROOM_TYPE_2);
                            room.setPrice(C_PRICE);
                        } else if (i == 4) {
                            room.setRoomNumber("D-0" + num);
                            room.setRoomType(Constants.ROOM_TYPE_3);
                            room.setPrice(D_PRICE);
                        } else {
                            room.setRoomNumber("E-0" + num);
                            room.setRoomType(Constants.ROOM_TYPE_4);
                            room.setPrice(E_PRICE);
                        }
                        room.setStatus(Constants.ROOM_STATUS_0);
                        roomService.add(room);
                        sum++;
                    }
                }
            }
            //跳转到主页
            //resp.sendRedirect("html/menu.html");
            return "true";
        }catch (UnknownAccountException e){//用户名不存在
            return "false";
        }catch (IncorrectCredentialsException e){//密码不存在
            return "false";
        }
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "true";
    }

    @RequestMapping("/register")
    public String register(String username, String password, String email, String sSignal) {
        if (!sSignal.equals(Function.sSignal)) {
            return "redirect:/no";
        } else {
            Administrator administrator = new Administrator();
            administrator.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            administrator.setUsername(username);
            administrator.setPassword(password);
            administrator.setEmail(email);
            administrator.setPerms("admin:all");
            System.out.println(administrator);
            if (administratorService.add(administrator) != 0) {
                return "redirect:/yes";
            } else {
                return "redirect:/no";
            }
        }
    }

    //删除管理员
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteAdministrator(String username) {
        if (administratorService.deleteAdministrator(username) != 0) {
            return "true";
        } else {
            return "false";
        }
    }

    //修改管理员
    @RequestMapping("/modify")
    @ResponseBody
    public String modifyAdministrator(String username, String email) {
        Administrator admin = administratorService.getAdmin(username);
        admin.setEmail(email);
        if (administratorService.modify(admin) != 0) {
            return "true";
        } else {
            return "false";
        }

    }

    //修改密码
    @RequestMapping("/modifyPwd")
    @ResponseBody
    public String modifyPassword(String username, String oldPassword, String newPassword) {
        Administrator administrator = null;
        administrator = administratorService.login(username, oldPassword);
        if (administrator != null) {
            System.out.println(administrator);
            if (administratorService.updatePwd(username, newPassword) != 0) {
                return "true";
            } else {
                return "false";
            }
        } else {
            return "false";
        }
    }
}

