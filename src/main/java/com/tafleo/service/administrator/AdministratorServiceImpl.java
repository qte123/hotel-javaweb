package com.tafleo.service.administrator;

import com.tafleo.dao.BaseDao;
import com.tafleo.dao.administrator.AdministratorDao;
import com.tafleo.dao.administrator.AdministratorDaoImpl;
import com.tafleo.pojo.Administrator;

import java.sql.Connection;
import java.sql.SQLException;

public class AdministratorServiceImpl implements AdministratorService {
    //业务层都会调用dao层，所以我们要引入Dao层
    private AdministratorDao administratorDao;

    public AdministratorServiceImpl() {
        administratorDao = new AdministratorDaoImpl();
    }

    @Override
    public boolean add(Administrator administrator) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            int updateRows = administratorDao.add(connection, administrator);
            connection.commit();
            if (updateRows > 0) {
                flag = true;
                System.out.println("add success!");
            } else {
                System.out.println("add failed!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            //在service层进行connection连接的关闭
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    @Override
    public Administrator getAdmin(String username) {
        Connection connection = null;
        Administrator administrator = null;
        try {
            connection = BaseDao.getConnection();
            //通过业务层调用对应的具体的数据库操作
            administrator = administratorDao.getLoginAdministrator(connection, username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return administrator;
    }

    //得到登录的管理员
    @Override
    public Administrator login(String username, String password) {
        Connection connection = null;
        Administrator administrator = null;
        try {
            connection = BaseDao.getConnection();
            //通过业务层调用对应的具体的数据库操作
            administrator = administratorDao.getLoginAdministrator(connection, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return administrator;
    }

    //修改管理员密码
    @Override
    public boolean updatePwd(String username, String password) {
        Connection connection = null;
        boolean flag = false;
        //修改密码
        try {
            connection = BaseDao.getConnection();
            if (administratorDao.updatePwd(connection, username, password) > 0) {
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    //删除管理员
    @Override
    public boolean deleteAdministrator(String username) {
        Connection connection = null;
        boolean flag = false;
        connection = BaseDao.getConnection();
        try {
            if (administratorDao.deleteAdministrator(connection, username) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    //修改管理员信息
    @Override
    public boolean modify(Administrator administrator) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if (administratorDao.modify(connection, administrator) > 0)
                flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
}
