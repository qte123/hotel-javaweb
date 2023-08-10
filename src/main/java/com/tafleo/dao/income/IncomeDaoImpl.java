package com.tafleo.dao.income;

import com.tafleo.dao.BaseDao;
import com.tafleo.pojo.Income;
import com.tafleo.pojo.Order;
import com.tafleo.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class IncomeDaoImpl implements IncomeDao {
    //添加收入信息
    @Override
    public int add(Connection connection, Income income) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int updateRows = 0;
        int count = 0;
        if (connection != null) {
            String sql = "insert into income(id,dailyIncome,totalIncome,updateTime) values (?,?,?,?)";
            Object[] params = {income.getId(), income.getDailyIncome(), income.getTotalIncome(), income.getUpdateTime()};
            updateRows = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, rs);
        }
        return updateRows;
    }

    //查询收入信息
    @Override
    public Income getIncome(Connection connection) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Income income = null;
        if (connection != null) {
            String sql = "select * from income where id= ? ";
            Object[] params = {1};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                income = new Income();
                income.setId(rs.getString("id"));
                income.setDailyIncome(rs.getInt("dailyIncome"));
                income.setTotalIncome(rs.getInt("totalIncome"));
                income.setUpdateTime(new Date(rs.getTimestamp("updateTime").getTime()));
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return income;
    }

    //删除收入信息
    @Override
    public int deleteIncome(Connection connection) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "delete from income where id = 1";
            Object[] params = {};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //修改收入信息
    @Override
    public int modify(Connection connection, Income income) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (connection != null) {
            String sql = "update income set dailyIncome=?," +
                    "totalIncome=?,updateTime=? where id = ? ";
            Object[] params = {income.getDailyIncome(), income.getTotalIncome(), income.getUpdateTime(), income.getId()};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }
}
