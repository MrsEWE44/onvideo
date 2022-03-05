package com.ov.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ov.video.entity.OvComment;
import com.ov.video.entity.OvMenu;
import com.ov.video.entity.OvUser;
import com.ov.video.entity.OvVideo;
import com.ov.video.mapper.OvVideoMapper;
import com.ov.video.service.IOvCommentService;
import com.ov.video.service.IOvMenuService;
import com.ov.video.service.IOvVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ov.video.vo.CommitInfo;
import com.ov.video.vo.VideoInfo;
import com.ov.video.vo.VideoInfoPlayer;
import com.ov.video.vo.VideoPlayerPage;
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
public class OvVideoServiceImpl extends ServiceImpl<OvVideoMapper, OvVideo> implements IOvVideoService {


    @Autowired
    IOvMenuService menuService;

    @Autowired
    IOvCommentService commentService;

    @Override
    public List<VideoInfo> getVideos(OvUser user) {
        LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvVideo::getUid,user.getUid());
        List<OvVideo> ovVideos = this.list(queryWrapper);
        List<VideoInfo> videoInfos = ovVideos.stream().map(v -> {
            return getVideoInfo(v,user);
        }).collect(Collectors.toList());
        return videoInfos;
    }

    @Override
    public VideoInfo getVideoInfo(OvVideo v, OvUser user) {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setDate(v.getVdate());
        videoInfo.setFid(v.getVfid());
        videoInfo.setMid(v.getVmid());
        OvMenu menu = menuService.getMenu(v.getVfid(), v.getVmid());
        videoInfo.setMname(menu.getMtitle());
        String vicon = v.getVicon();
        videoInfo.setIcon((vicon == null || vicon.indexOf("http") == -1 ) ? "":vicon);
        videoInfo.setId(v.getVid());
        videoInfo.setUid(user.getUid());
        videoInfo.setUname(user.getUname());
        videoInfo.setTitle(v.getVtitle());
        return videoInfo;
    }

    @Override
    public List<OvVideo> getRandomVideo(Integer i) {
        return this.getBaseMapper().getRandomVideoLimit(i);
    }

    @Override
    public List<OvVideo> getRandomVideo(Integer fid, Integer mid, Integer limit) {
        return this.getBaseMapper().getRandomVideo2(fid,mid,limit);
    }

    @Override
    public Integer getByUserID(VideoInfo videoInfo) {
        return getByVideo(videoInfo).getUid();
    }

    @Override
    public VideoPlayerPage getVideoPlayerPageInfo(VideoInfo videoInfo, OvUser user) {
        OvVideo byVideo = getByVideo(videoInfo);
        LambdaQueryWrapper<OvComment> ovCommentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ovCommentLambdaQueryWrapper.eq(OvComment::getVid,byVideo.getVid());
        Long commits = commentService.count(ovCommentLambdaQueryWrapper);
        List<CommitInfo> commitInfos = commentService.getCommitInfos(byVideo,user);
        VideoInfoPlayer videoInfoPlayer = new VideoInfoPlayer();
        videoInfoPlayer.setDate(byVideo.getVdate());
        videoInfoPlayer.setMsg(byVideo.getVmsg());
        videoInfoPlayer.setTitle(byVideo.getVtitle());
        videoInfoPlayer.setViews(0L);
        videoInfoPlayer.setCommits(commits);
        VideoPlayerPage videoPlayerPage = new VideoPlayerPage();
        user.setUpwd(null);
        String uicon = user.getUicon();
        user.setUicon(uicon.indexOf("http") != -1 ? uicon:"");
        videoPlayerPage.setUinfo(user);
        String vpath = byVideo.getVpath();
        videoPlayerPage.setPlayer(vpath.indexOf("http") != -1?vpath:"");
        videoPlayerPage.setVideoInfoPlayer(videoInfoPlayer);
        videoPlayerPage.setCommitInfos(commitInfos);
        return videoPlayerPage;
    }

    @Override
    public OvVideo getByVideo(VideoInfo videoInfo) {
        LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvVideo::getVfid,videoInfo.getFid()).eq(OvVideo::getVmid,videoInfo.getMid()).eq(OvVideo::getVid,videoInfo.getId());
        return this.getOne(queryWrapper);
    }

    @Override
    public OvVideo getVideoByID(Integer vid) {
        LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvVideo::getVid,vid);
        OvVideo ovVideo = this.getOne(queryWrapper);
        String vpath = ovVideo.getVpath();
        String vicon = ovVideo.getVicon();
        if(vpath.indexOf("http") == -1){
            ovVideo.setVpath(null);
        }
        if(vicon.indexOf("http") == -1){
            ovVideo.setVicon(null);
        }
        return ovVideo;
    }

    @Override
    public List<OvVideo> getLeftImages(Integer limit) {
        return this.getBaseMapper().getLeftImages(limit);
    }

    @Override
    public List<OvVideo> getLeftImages2(Integer fid, Integer limit) {
        return this.getBaseMapper().getLeftImages2(fid,limit);
    }

    @Override
    public List<OvVideo> getRandomVideo(Integer fid, Integer limit) {
        return this.getBaseMapper().getRandomVideo(fid,limit);
    }
}
