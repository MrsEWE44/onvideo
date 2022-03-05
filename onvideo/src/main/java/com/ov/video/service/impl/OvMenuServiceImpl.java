package com.ov.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ov.video.entity.OvMenu;
import com.ov.video.mapper.OvMenuMapper;
import com.ov.video.service.IOvMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ov.video.vo.MenuVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
@Service
public class OvMenuServiceImpl extends ServiceImpl<OvMenuMapper, OvMenu> implements IOvMenuService {

    @Override
    public List<MenuVO> getMenuVOS(int i) {
        LambdaQueryWrapper<OvMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvMenu::getFid,i);
        List<OvMenu> menus = this.list(queryWrapper);
        List<MenuVO> menuVOS = menus.stream().map(m -> {
            MenuVO menuVO = new MenuVO();
            menuVO.setPaid(m.getMid());
            menuVO.setTitle(m.getMtitle());
            if(i == 0){
                menuVO.setChilder(getMenuVOS(m.getMid()));
            }
            return menuVO;
        }).collect(Collectors.toList());
        return menuVOS;
    }

    @Override
    public List<MenuVO> getMenuVOS() {
        return getMenuVOS(0);
    }

    @Override
    public OvMenu getMenu(int fid, int mid) {
        LambdaQueryWrapper<OvMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvMenu::getFid,fid).eq(OvMenu::getMid,mid);
        return this.getOne(queryWrapper);
    }
}
