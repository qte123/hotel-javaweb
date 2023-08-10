package com.tafleo.hotel.config;

import com.tafleo.hotel.pojo.Administrator;
import com.tafleo.hotel.pojo.User;
import com.tafleo.hotel.service.AdministratorService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的UserRealm extends AuthorizingRealm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private AdministratorService administratorService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        Administrator administrator = (Administrator) subject.getPrincipal();//拿到user对象
        System.out.println(administrator.getPerms());
        info.addStringPermission(administrator.getPerms());//将user对象里的权限授权
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证了=>授权doGetAuthorizationInfo");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //连接真实数据库
        Administrator administrator = administratorService.getAdmin(userToken.getUsername());
        if(administrator==null){//没有这个人
            return null;//抛出异常 UnknownAccountException
        }
        //密码认证，shiro做
        return new SimpleAuthenticationInfo(administrator,administrator.getPassword(),"");
    }
}
