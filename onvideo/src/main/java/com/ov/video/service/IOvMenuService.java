package com.ov.video.service;

import com.ov.video.entity.OvMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ov.video.vo.MenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
public interface IOvMenuService extends IService<OvMenu> {

    List<MenuVO> getMenuVOS(int i);

    List<MenuVO> getMenuVOS();

    OvMenu getMenu(int fid , int mid);

}
