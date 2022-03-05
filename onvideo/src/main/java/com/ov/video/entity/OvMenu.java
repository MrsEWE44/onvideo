package com.ov.video.entity;

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
@TableName("ov_menu")
@ApiModel(value = "OvMenu对象", description = "")
public class OvMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单id")
    private Integer mid;

    @ApiModelProperty("菜刀名称")
    private String mtitle;

    @ApiModelProperty("菜单上级id")
    private Integer fid;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }
    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }
    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @Override
    public String toString() {
        return "OvMenu{" +
            "mid=" + mid +
            ", mtitle=" + mtitle +
            ", fid=" + fid +
        "}";
    }
}
