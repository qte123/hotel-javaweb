// 路由接口
const express=require('express');

const router=express.Router();

//导入路由处理函数模块
const handler=require('../router_handler');

//首页跳转
router.get("/",handler.goHome)
//输入index.html重定向到/
router.get('/index.html',handler.goHome)

//html目录下的页面跳转
//管理员登录页面跳转
router.get('/login',handler.goLogin)
//注册页面跳转
router.get('/register',handler.goRegister)
//订单页面跳转
router.get('/check-login',handler.goCl)
//订单查询页面跳转
router.get('/check',handler.goCheck)
//无权限页面跳转
router.get('/error',handler.goError)
//预定成功页面跳转
router.get('/success',handler.goSuccess)
//预定失败页面跳转
router.get('/fail',handler.goFail)
//注册成功页面跳转
router.get('/yes',handler.goYes)
//注册失败页面跳转
router.get('/no',handler.goNo)
//修改密码页面跳转
router.get('/cpw',handler.goCpw)

//root目录下的页面跳转
//房间分配页面跳转
router.get('/root/assign',handler.goAssign)
//房间管理页面跳转
router.get('/root/cr',handler.goCr)
//营业额度页面跳转
router.get('/root/count',handler.goCount)
//住房查询页面跳转
router.get('/root/his',handler.goHis)
//主页页面跳转
router.get('/root/menu',handler.goMenu)
//信息查询页面跳转
router.get('/root/message',handler.goMessage)
//用户管理页面跳转
router.get('/root/uis',handler.goUis)

module.exports=router