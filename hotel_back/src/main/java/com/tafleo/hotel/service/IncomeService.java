package com.tafleo.hotel.service;

import com.tafleo.hotel.pojo.Income;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 收入表 服务类
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
public interface IncomeService extends IService<Income> {
    //添加收入信息
    public int add(Income income);

    //查询收入信息
    public Income getLastIncome();

    //删除收入信息
    public int deleteIncome(int id);

    //修改收入信息
    public int modify(Income income);
}
