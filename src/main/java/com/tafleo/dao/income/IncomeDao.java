package com.tafleo.dao.income;

import com.tafleo.pojo.Income;
import com.tafleo.pojo.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IncomeDao {
    //添加收入信息
    public int add(Connection connection, Income income) throws SQLException;

    //查询收入信息
    public Income getIncome(Connection connection) throws SQLException;

    //删除收入信息
    public int deleteIncome(Connection connection) throws Exception;

    //修改收入信息
    public int modify(Connection connection, Income income) throws Exception;

}
