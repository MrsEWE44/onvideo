package com.ov.video.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DynamicInfo {
    String icon , title,msg;
    Integer vid,uid;
    LocalDateTime date;
}
