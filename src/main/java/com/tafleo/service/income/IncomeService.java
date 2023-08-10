package com.tafleo.service.income;

import com.tafleo.pojo.Income;

import java.sql.Connection;
import java.sql.SQLException;

public interface IncomeService {
    //添加收入信息
    public boolean add(Income income);

    //查询收入信息
    public Income getIncome();

    //删除收入信息
    public boolean deleteIncome();

    //修改收入信息
    public boolean modify(Income income);
}
