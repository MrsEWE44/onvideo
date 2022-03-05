package com.ov.video.service;

import com.ov.video.entity.OvRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
public interface IOvRoleService extends IService<OvRole> {

    public OvRole getByRole(String rolename);

}
