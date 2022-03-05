package com.ov.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("ov_video")
@ApiModel(value = "OvVideo对象", description = "")
public class OvVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("视频id")
    @TableId(value = "vid", type = IdType.AUTO)
    private Integer vid;

    @ApiModelProperty("视频标题")
    private String vtitle;

    @ApiModelProperty("视频封面")
    private String vicon;

    @ApiModelProperty("视频发布时间")
    private LocalDateTime vdate;

    @ApiModelProperty("用户发布id")
    private Integer uid;

    @ApiModelProperty("视频链接/存放位置")
    private String vpath;

    public String getVmsg() {
        return vmsg;
    }

    public void setVmsg(String vmsg) {
        this.vmsg = vmsg;
    }

    @ApiModelProperty("视频介绍")
    private String vmsg;

    @ApiModelProperty("视频小分区id")
    private Integer vmid;

    @ApiModelProperty("视频大分区id")
    private Integer vfid;

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }
    public String getVtitle() {
        return vtitle;
    }

    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }
    public String getVicon() {
        return vicon;
    }

    public void setVicon(String vicon) {
        this.vicon = vicon;
    }
    public LocalDateTime getVdate() {
        return vdate;
    }

    public void setVdate(LocalDateTime vdate) {
        this.vdate = vdate;
    }
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getVpath() {
        return vpath;
    }

    public void setVpath(String vpath) {
        this.vpath = vpath;
    }
    public Integer getVmid() {
        return vmid;
    }

    public void setVmid(Integer vmid) {
        this.vmid = vmid;
    }
    public Integer getVfid() {
        return vfid;
    }

    public void setVfid(Integer vfid) {
        this.vfid = vfid;
    }

    @Override
    public String toString() {
        return "OvVideo{" +
            "vid=" + vid +
            ", vtitle=" + vtitle +
            ", vicon=" + vicon +
            ", vdate=" + vdate +
            ", uid=" + uid +
            ", vpath=" + vpath +
            ", vmid=" + vmid +
            ", vfid=" + vfid +
                ", vmsg=" + vmsg +
        "}";
    }
}
