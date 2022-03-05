package com.ov.video.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuVO {
    String title ;
    Integer paid;
    List<MenuVO> childer;
}

