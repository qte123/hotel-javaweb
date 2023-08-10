package com.tafleo.hotel.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    //插入时填充策略 插入操作要填充创建时间和更新时间
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill...");
        //setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
        this.setFieldValByName("gmt_create", new Date(), metaObject);
        this.setFieldValByName("gmt_modified",new Date(),metaObject);
    }

    //更新时的填充策略 更新操作只需要填充更新时间
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill....");
        this.setFieldValByName("gmt_modified",new Date(),metaObject);
    }
}
