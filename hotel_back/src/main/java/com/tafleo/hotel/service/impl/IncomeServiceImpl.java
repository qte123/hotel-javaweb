package com.tafleo.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tafleo.hotel.pojo.Income;
import com.tafleo.hotel.mapper.IncomeMapper;
import com.tafleo.hotel.service.IncomeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收入表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Service
public class IncomeServiceImpl extends ServiceImpl<IncomeMapper, Income> implements IncomeService {
    @Autowired
    private IncomeMapper incomeMapper;

    @Override
    public int add(Income income) {
        return incomeMapper.insert(income);
    }

    @Override
    public Income getLastIncome() {
        QueryWrapper<Income> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");//以创建日期降序查询
        //Page<Income> page = new Page<>(0, 1); //查第1页前1个数据
        //IPage<Income> incomeIPage = incomeMapper.selectPage( wrapper);
        List<Income> incomeList = incomeMapper.selectList(wrapper);
        if (incomeList.size()!=0){
            return incomeList.get(0);
        }
        return null;
    }

    public int deleteIncome(int id) {
        return incomeMapper.deleteById(id);
    }

    @Override
    public int modify(Income income) {
        return incomeMapper.updateById(income);
    }
}
