package com.ov.video.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoInfo {
    String icon,title,uname,fname,mname;
    LocalDateTime date;
    Integer id,fid,mid,uid;
}
