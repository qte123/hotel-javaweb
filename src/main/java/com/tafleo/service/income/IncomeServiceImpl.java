package com.tafleo.service.income;

import com.tafleo.dao.BaseDao;
import com.tafleo.dao.income.IncomeDao;
import com.tafleo.dao.income.IncomeDaoImpl;
import com.tafleo.pojo.Income;
import com.tafleo.pojo.Order;

import java.sql.Connection;
import java.sql.SQLException;

public class IncomeServiceImpl implements IncomeService {
    private IncomeDao incomeDao;

    public IncomeServiceImpl() {
        incomeDao = new IncomeDaoImpl();
    }

    @Override
    public boolean add(Income income) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            int updateRows = incomeDao.add(connection, income);
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
    public Income getIncome() {
        Connection connection = null;
        Income income = null;
        try {
            connection = BaseDao.getConnection();
            //通过业务层调用对应的具体的数据库操作
            income = incomeDao.getIncome(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return income;
    }

    @Override
    public boolean deleteIncome() {
        Connection connection = null;
        boolean flag = false;
        connection = BaseDao.getConnection();
        try {
            if (incomeDao.deleteIncome(connection) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    @Override
    public boolean modify(Income income) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if (incomeDao.modify(connection, income) > 0)
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
