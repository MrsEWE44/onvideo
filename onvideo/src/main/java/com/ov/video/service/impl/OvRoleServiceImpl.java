package com.ov.video.service.impl;

import com.ov.video.entity.OvRole;
import com.ov.video.mapper.OvRoleMapper;
import com.ov.video.service.IOvRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
@Service
public class OvRoleServiceImpl extends ServiceImpl<OvRoleMapper, OvRole> implements IOvRoleService {

    @Override
    public OvRole getByRole(String rolename) {
        for (OvRole ovRole : this.list()) {
            if(ovRole.getRname().equals(rolename)){
                return ovRole;
            }
        }
        return null;
    }
}
