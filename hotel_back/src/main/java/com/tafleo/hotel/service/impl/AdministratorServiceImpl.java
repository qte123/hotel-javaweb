package com.tafleo.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tafleo.hotel.pojo.Administrator;
import com.tafleo.hotel.mapper.AdministratorMapper;
import com.tafleo.hotel.service.AdministratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;

    @Override
    public int add(Administrator administrator) {
        return administratorMapper.insert(administrator);
    }

    @Override
    public Administrator getAdmin(String username) {
        return administratorMapper.selectById(username);
    }

    @Override
    public Administrator login(String username, String password) {
        QueryWrapper<Administrator> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("password", password);
        return administratorMapper.selectOne(wrapper);
    }

    @Override
    public int updatePwd(String username, String password) {
        Administrator admin = administratorMapper.selectById(username);
        admin.setPassword(password);
        return administratorMapper.updateById(admin);
    }

    @Override
    public int deleteAdministrator(String username) {
        return administratorMapper.deleteById(username);
    }

    @Override
    public int modify(Administrator administrator) {
        return administratorMapper.updateById(administrator);
    }
}
