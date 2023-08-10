package com.tafleo.hotel.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author litao
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Administrator implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableId(value = "username", type = IdType.ID_WORKER)
    private String username;

    private String password;

    private String email;

    private String perms;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private Date gmt_create;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmt_modified;


}
