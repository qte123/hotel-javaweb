package com.tafleo.dao.administrator;

import com.tafleo.dao.BaseDao;
import com.tafleo.pojo.Administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AdministratorDaoImpl implements AdministratorDao {
    //添加管理员
    @Override
    public int add(Connection connection, Administrator administrator) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int updateRows = 0;
        int count = 0;
        if (connection != null) {
            String sql = "insert into administrator ( id,username,password,email,addDate,modifyDate) values (?,?,?,?,?,?)";
            Object[] params = {administrator.getId(), administrator.getUsername(), administrator.getPassword(), administrator.getEmail(), administrator.getAddDate(), administrator.getModifyDate()};
            updateRows = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, rs);
        }
        return updateRows;
    }

    @Override
    public Administrator getLoginAdministrator(Connection connection, String username) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Administrator administrator = null;


        if (connection != null) {
            String sql = "select * from administrator where username=? ";
            Object[] params = {username};

            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                administrator = new Administrator();
                administrator.setId(rs.getString("id"));
                administrator.setUsername(rs.getString("username"));
                administrator.setPassword(rs.getString("password"));
                administrator.setEmail(rs.getString("email"));
                administrator.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    administrator.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return administrator;
    }

    //查询管理员是否存在
    @Override
    public Administrator getLoginAdministrator(Connection connection, String username, String password) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Administrator administrator = null;


        if (connection != null) {
            String sql = "select * from administrator where username=? and password= ?";
            Object[] params = {username, password};

            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                administrator = new Administrator();
                administrator.setId(rs.getString("id"));
                administrator.setUsername(rs.getString("username"));
                administrator.setPassword(rs.getString("password"));
                administrator.setEmail(rs.getString("email"));
                administrator.setAddDate(new Date(rs.getTimestamp("addDate").getTime()));
                if (rs.getTimestamp("modifyDate") != null) {
                    administrator.setModifyDate(new Date(rs.getTimestamp("modifyDate").getTime()));
                }
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return administrator;
    }

    //修改密码
    @Override
    public int updatePwd(Connection connection, String username, String password) throws SQLException {
        PreparedStatement pstm = null;
        int execute = 0;
        if (connection != null) {
            String sql = "update administrator set password = ? where username = ?";
            Object params[] = {password, username};
            execute = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return execute;
    }

    //删除用户
    public int deleteAdministrator(Connection connection, String username) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "delete from administrator where username = ?";
            Object[] params = {username};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //修改用户
    public int modify(Connection connection, Administrator administrator) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "update administrator set email=?,modifyDate=? where username = ? ";
            Object[] params = {administrator.getEmail(), administrator.getModifyDate(), administrator.getUsername()};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }
}
