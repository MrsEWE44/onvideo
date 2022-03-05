package com.ov.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ov.video.entity.OvComment;
import com.ov.video.entity.OvUser;
import com.ov.video.entity.OvVideo;
import com.ov.video.mapper.OvCommentMapper;
import com.ov.video.service.IOvCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ov.video.service.IOvUserService;
import com.ov.video.service.IOvVideoService;
import com.ov.video.vo.CommitInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OvCommentServiceImpl extends ServiceImpl<OvCommentMapper, OvComment> implements IOvCommentService {

    @Override
    public List<CommitInfo> getComInfos(OvUser user) {
        LambdaQueryWrapper<OvComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvComment::getUid,user.getUid());
        List<OvComment> ovComments = this.list(queryWrapper);
        List<CommitInfo> commitInfos = ovComments.stream().map(c -> {
            CommitInfo commitInfo = new CommitInfo();
            commitInfo.setDate(c.getCdate());
            commitInfo.setMsg(c.getMsg());
            commitInfo.setUid(user.getUid().toString());
            commitInfo.setUname(user.getUname());
            commitInfo.setUicon(user.getUicon());
            return commitInfo;
        }).collect(Collectors.toList());
        return commitInfos;
    }

    @Override
    public OvComment getByUID(String uid) {
        LambdaQueryWrapper<OvComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvComment::getUid,uid);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<CommitInfo> getCommitInfos(OvVideo byVideo, OvUser user) {
        LambdaQueryWrapper<OvComment> ovCommentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ovCommentLambdaQueryWrapper.eq(OvComment::getVid,byVideo.getVid());
        List<OvComment> ovComments = this.list(ovCommentLambdaQueryWrapper);
        List<CommitInfo> commitInfos = ovComments.stream().map(com -> {
            OvUser user1 = this.getBaseMapper().getUserInfo(com.getUid());
            String uicon = user1.getUicon();

            CommitInfo commitInfo = new CommitInfo();
            commitInfo.setUicon(uicon.indexOf("http") != -1 ? uicon:"");
            commitInfo.setUid(com.getUid().toString());
            commitInfo.setUname(user1.getUname());
            commitInfo.setMsg(com.getMsg());
            commitInfo.setDate(com.getCdate());
            return commitInfo;
        }).collect(Collectors.toList());
        return commitInfos;
    }

    @Override
    public CommitInfo getCommitInfo(OvComment com ,OvVideo byVideo, OvUser user) {
        CommitInfo commitInfo = new CommitInfo();
        commitInfo.setUicon(user.getUicon());
        commitInfo.setUid(com.getUid().toString());
        commitInfo.setUname(user.getUname());
        commitInfo.setMsg(com.getMsg());
        commitInfo.setDate(com.getCdate());
        String vicon = byVideo.getVicon();
        commitInfo.setVicon(vicon.indexOf("http") == -1 ? "" : vicon);
        commitInfo.setVtitle(byVideo.getVtitle());
        commitInfo.setVid(byVideo.getVid());
        return commitInfo;
    }

    @Override
    public Boolean delComment(Integer vid, Integer uid) {
        LambdaQueryWrapper<OvComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvComment::getUid,uid).eq(OvComment::getVid,vid);
        return this.remove(queryWrapper);
    }

    @Override
    public boolean delCommentByCommitInfo(CommitInfo commitInfo, String requestCookieUid) {
        LambdaQueryWrapper<OvComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvComment::getCdate,commitInfo.getDate()).eq(OvComment::getMsg,commitInfo.getMsg()).eq(OvComment::getUid,requestCookieUid).eq(OvComment::getVid,commitInfo.getVid());

        return this.remove(queryWrapper);
    }


}
