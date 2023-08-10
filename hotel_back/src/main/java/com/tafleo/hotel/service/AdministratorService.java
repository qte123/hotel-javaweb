package com.tafleo.hotel.service;

import com.tafleo.hotel.pojo.Administrator;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
public interface AdministratorService extends IService<Administrator> {

    //添加管理员
    public int add(Administrator administrator);

    //获取管理员
    public Administrator getAdmin(String username);

    //管理员登录
    public Administrator login(String username, String password);

    //根据用户ID修改密码
    public int updatePwd(String username, String password);

    //删除管理员(销号)
    public int deleteAdministrator(String username);

    //修改管理员
    public int modify(Administrator administrator);
}
