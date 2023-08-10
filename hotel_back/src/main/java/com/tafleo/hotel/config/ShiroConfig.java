package com.tafleo.hotel.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);
        /*
         anno：无需认证就可以访问
         authc：必须认证了才能访问
         user：必须拥有记住我功能才能使用
         perms：拥有对某个资源的权限才能访问
         role：拥有某个
        */
        //拦截过滤
        LinkedHashMap<String,String> filterMap = new LinkedHashMap<>();//过滤map是链式map

        //添加权限，正常情况下，没有权限会跳转到未授权页面
        //filterMap.put("/user/update","perms[admin:update]");//访问需要perms[user:add]权限
        //添加过滤,如果没有登录，则会跳转到登录页面
        filterMap.put("/api/*","authc");
        bean.setFilterChainDefinitionMap(filterMap);//设置过滤map
        bean.setLoginUrl("http://localhost:80/login");//未登录将跳转到登录页面
        bean.setUnauthorizedUrl("/noauth");//未授权将跳转到未授权页面
        return bean;
    }
    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        //创建DefaultWebSecurityManager对象
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    //创建realm对象，需要自定义类
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
