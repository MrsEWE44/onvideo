package com.ov.video.service;

import com.ov.video.entity.OvUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ov.video.vo.LoginUser;
import com.ov.video.vo.UserHomePage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
public interface IOvUserService extends IService<OvUser> {

    boolean login(LoginUser loginUser);

    UserHomePage getUserHomePage(String uid);

    OvUser getUser(String uid);

    boolean addDefaultUser(LoginUser loginUser);

}
