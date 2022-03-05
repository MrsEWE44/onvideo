package com.ov.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
@TableName("ov_user")
@ApiModel(value = "OvUser对象", description = "")
public class OvUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    @ApiModelProperty("用户昵称")
    private String uname;

    @ApiModelProperty("用户头像")
    private String uicon;

    @ApiModelProperty("用户密码")
    private String upwd;

    @ApiModelProperty("用户角色id")
    private Integer rid;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUicon() {
        return uicon;
    }

    public void setUicon(String uicon) {
        this.uicon = uicon;
    }
    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }
    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    @Override
    public String toString() {
        return "OvUser{" +
            "uid=" + uid +
            ", uname=" + uname +
            ", uicon=" + uicon +
            ", upwd=" + upwd +
            ", rid=" + rid +
        "}";
    }
}
