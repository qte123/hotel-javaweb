//路由接口实现

//获取项目静态资源地址
const path ='../public/';
//html目录地址
const htmlPath=path+'html/';
//root目录地址
const rootPath=htmlPath+'root/';

//首页
exports.goHome=(req,res)=>{
    res.render(path+'index.html');
}

//html目录下的页面跳转
//管理员登录页面跳转
exports.goLogin=(req,res)=>{
    res.render(htmlPath+'login.html');
}
//注册页面跳转
exports.goRegister=(req,res)=>{
    res.render(htmlPath+'register.html');
}
//订单页面跳转
exports.goCl=(req,res)=>{
    res.render(htmlPath+'check-login.html');
}
//订单查询页面跳转
exports.goCheck=(req,res)=>{
    res.render(htmlPath+'check.html');
}
//无权限页面跳转
exports.goError=(req,res)=>{
    res.render(htmlPath+'error.html');
}
//预定成功页面跳转
exports.goSuccess=(req,res)=>{
    res.render(htmlPath+'success.html');
}
//预定失败页面跳转
exports.goFail=(req,res)=>{
    res.render(htmlPath+'fail.html');
}
//注册成功页面跳转
exports.goYes=(req,res)=>{
    res.render(htmlPath+'yes.html');
}
//注册失败页面跳转
exports.goNo=(req,res)=>{
    res.render(htmlPath+'no.html');
}
//修改密码页面跳转
exports.goCpw=(req,res)=>{
    res.render(htmlPath+'changePW.html');
}

//root目录下的页面跳转
//房间分配页面跳转
exports.goAssign=(req,res)=>{
    res.render(rootPath+'Assign.html');
}
//房间管理页面跳转
exports.goCr=(req,res)=>{
    res.render(rootPath+'Charge Room.html');
}
//营业额度页面跳转
exports.goCount=(req,res)=>{
    res.render(rootPath+'count.html');
}
//住房查询页面跳转
exports.goHis=(req,res)=>{
    res.render(rootPath+'Hosing Information System.html');
}
//主页页面跳转
exports.goMenu=(req,res)=>{
    res.render(rootPath+'menu.html');
}
//信息查询页面跳转
exports.goMessage=(req,res)=>{
    res.render(rootPath+'message.html');
}
//用户管理页面跳转
exports.goUis=(req,res)=>{
    res.render(rootPath+'User Information System.html');
}
