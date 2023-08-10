package com.tafleo.service.administrator;

import com.tafleo.pojo.Administrator;

public interface AdministratorService {
    //添加管理员
    public boolean add(Administrator administrator);
    //获取管理员
    public Administrator getAdmin(String username);
    //管理员登录
    public Administrator login(String username, String password);
    //根据用户ID修改密码
    public boolean updatePwd(String username, String password);
    //删除管理员(销号)
    public boolean deleteAdministrator(String username);
    //修改管理员
    public boolean modify(Administrator administrator);
}
