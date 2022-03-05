package com.ov.video.controller;


import com.ov.video.expand.ResultStatus;
import com.ov.video.expand.resultMSG;
import com.ov.video.service.IOvMenuService;
import com.ov.video.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
@RestController
@RequestMapping("/video/menu")
public class OvMenuController {


    @Autowired
    IOvMenuService menuService;

    @GetMapping("/getMenus")
    public Object getMenus(){
        List<MenuVO> menuVOS = menuService.getMenuVOS();
        return resultMSG.add(ResultStatus.success).add("data",menuVOS);
    }





}
