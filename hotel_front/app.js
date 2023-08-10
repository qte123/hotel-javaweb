//导入express
const express=require('express');
//创建服务器的实例对象
const app=express();

//导入并配置cors中间件
const cors=require('cors');
app.use(cors())

const session=require('express-session');
app.use(session({
    secret:'itheima',
    resave:false,
    saveUninitialized:true
}))

//配置解析表单数据的中间件 这个中间件只能解析application/x-www-form-urlencoded格式的表单数据
app.use(express.urlencoded({extended:false}))

//导入并使用路由模块
const userRouter=require('./router');
app.engine('html',require('express-art-template'))
app.use(userRouter);

app.use(express.static("public",{
    extensions: ['html','htm']
}))

//启动服务器
app.listen(3007,()=>{
    console.log('api server start');
})

