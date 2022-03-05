package com.ov.video.entity;

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
@TableName("ov_comment")
@ApiModel(value = "OvComment对象", description = "")
public class OvComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论发布时间")
    private LocalDateTime cdate;

    @ApiModelProperty("发布者id")
    private Integer uid;

    @ApiModelProperty("评论内容")
    private String msg;

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    @ApiModelProperty("评论的视频id")
    private Integer vid;

    public LocalDateTime getCdate() {
        return cdate;
    }

    public void setCdate(LocalDateTime cdate) {
        this.cdate = cdate;
    }
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "OvComment{" +
            "cdate=" + cdate +
            ", uid=" + uid +
            ", msg=" + msg +
                ", vid="+ vid+
        "}";
    }
}
