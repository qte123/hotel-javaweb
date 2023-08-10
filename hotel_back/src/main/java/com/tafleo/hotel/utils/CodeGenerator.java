package com.tafleo.hotel.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class CodeGenerator {
    public static void main(String[] args) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("author","litao");
        map.put("isSwagger",true);
        map.put("username","root");
        map.put("password","123456");
        map.put("url","jdbc:mysql://localhost:3306/hotel_manage_sys?useSSl=false&useUnicode=true&characterEncoding=utf-8");
        map.put("module","hotel");
        map.put("parent","com.tafleo");
        new CodeGenerator().codeGenerator(map);
    }

    public void codeGenerator(Map<String,Object> map) {
        //需要构建一个代码自动生成器对象
        AutoGenerator mpg = new AutoGenerator();
        //配置策略
        //1.全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");//获取项目的地址
        gc.setOutputDir(projectPath + "/src/main/java");//生成文件地址
        gc.setAuthor((String) map.get("author"));
        gc.setOpen(false);//创建完成后是否打开资源管理器
        gc.setFileOverride(false);//是否覆盖
        gc.setServiceName("%sService");//去掉Service的I前缀
        gc.setIdType(IdType.ID_WORKER);//id默认算法
        gc.setDateType(DateType.ONLY_DATE);//默认日期
        gc.setSwagger2((Boolean) map.get("isSwagger"));//自动配置swagger
        mpg.setGlobalConfig(gc);

        //2.设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUsername((String) map.get("username"));
        dsc.setPassword((String) map.get("password"));
        dsc.setUrl((String) map.get("url"));
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        //3.包的配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName((String) map.get("module"));//模块名
        pc.setParent((String) map.get("parent"));//父包名
        pc.setEntity("pojo");//实体包名
        pc.setMapper("mapper");//mapper包名
        pc.setService("service");//service包名
        pc.setController("controller");//controller包名
        mpg.setPackageInfo(pc);

        //4.策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setInclude("administrator", "income", "orders", "room", "user");//设置要映射的表名
        sc.setNaming(NamingStrategy.underline_to_camel);//支持下划线驼峰命名
        sc.setColumnNaming(NamingStrategy.underline_to_camel);//映射表支持下划线
        sc.setSuperEntityClass("");
        sc.setEntityLombokModel(true);//是否使用lombok
        sc.setLogicDeleteFieldName("deleted");//逻辑删除字段名
        //自动填充配置
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        sc.setTableFillList(tableFills);
        //乐观锁
        sc.setVersionFieldName("version");
        sc.setRestControllerStyle(true);//开启restful驼峰命名
        sc.setControllerMappingHyphenStyle(true);//localhost:8080/hello_id_2
        mpg.setStrategy(sc);

        //执行
        mpg.execute();

    }
}
