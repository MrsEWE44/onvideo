package com.ov.video.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommitInfo {
    String uid,uname,uicon,msg,vtitle,vicon;
    Integer vid;
    LocalDateTime date;
}
