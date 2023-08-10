package com.tafleo.dao.administrator;

import com.tafleo.pojo.Administrator;

import java.sql.Connection;
import java.sql.SQLException;

public interface AdministratorDao {
    //添加管理员
    public int add(Connection connection, Administrator administrator) throws SQLException;

    //查询管理员（无密码）
    public Administrator getLoginAdministrator(Connection connection, String username) throws SQLException;

    //查询管理员
    public Administrator getLoginAdministrator(Connection connection, String username, String password) throws SQLException;

    //修改当前用户密码
    public int updatePwd(Connection connection, String username, String password) throws SQLException;

    //删除管理员
    public int deleteAdministrator(Connection connection, String username) throws Exception;

    //修改管理员
    public int modify(Connection connection, Administrator administrator) throws Exception;
}
