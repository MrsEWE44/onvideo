package com.ov.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ov.video.entity.OvUser;
import com.ov.video.mapper.OvUserMapper;
import com.ov.video.service.IOvCommentService;
import com.ov.video.service.IOvRoleService;
import com.ov.video.service.IOvUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ov.video.service.IOvVideoService;
import com.ov.video.vo.LoginUser;
import com.ov.video.vo.UserHomePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
@Service
public class OvUserServiceImpl extends ServiceImpl<OvUserMapper, OvUser> implements IOvUserService {

    @Autowired
    IOvVideoService videoService;

    @Autowired
    IOvCommentService commentService;

    @Autowired
    IOvRoleService roleService;


    @Override
    public boolean login(LoginUser loginUser) {
        LambdaQueryWrapper<OvUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvUser::getUid,loginUser.getUid()).eq(OvUser::getUpwd,loginUser.getPwd());
        OvUser one = this.getOne(queryWrapper);
        return one != null;
    }

    @Override
    public UserHomePage getUserHomePage(String uid) {
        UserHomePage userHomePage = new UserHomePage();
        OvUser user = this.getUser(uid);
        if(user != null){
            user.setUpwd(null);
            userHomePage.setOvUser(user);
            userHomePage.setVideoInfoList(videoService.getVideos(user));
            userHomePage.setCommitInfoList(commentService.getComInfos(user));
        }
        return userHomePage;
    }

    @Override
    public OvUser getUser(String uid) {
        LambdaQueryWrapper<OvUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvUser::getUid,uid);
        OvUser one = this.getOne(queryWrapper);
        try {
            String uicon = one.getUicon();
            if(uicon == null ||uicon.isEmpty() || uicon.indexOf("http") == -1){
                one.setUicon("");
            }
        }catch (Exception e){return null;}
        one.setUpwd(null);
        return one;
    }

    @Override
    public boolean addDefaultUser(LoginUser loginUser) {
        String usericonPath = "icon/uicon.jpg";
        OvUser ovUser = new OvUser();
        ovUser.setUpwd(loginUser.getPwd());
        ovUser.setUicon(usericonPath);
        ovUser.setUname(loginUser.getUid());
        ovUser.setRid(roleService.getByRole("USER").getRid());
        return this.save(ovUser);
    }
}
