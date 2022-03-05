package com.ov.video.service;

import com.ov.video.entity.OvUser;
import com.ov.video.entity.OvVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ov.video.vo.VideoInfo;
import com.ov.video.vo.VideoPlayerPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
public interface IOvVideoService extends IService<OvVideo> {

    List<VideoInfo> getVideos(OvUser user);

    VideoInfo getVideoInfo(OvVideo ovVideo, OvUser user);

    List<OvVideo> getRandomVideo(Integer fid, Integer limit);

    List<OvVideo> getRandomVideo(Integer i);

    List<OvVideo> getRandomVideo(Integer fid, Integer mid, Integer limit);

    Integer getByUserID(VideoInfo videoInfo);

    VideoPlayerPage getVideoPlayerPageInfo(VideoInfo videoInfo, OvUser user);

    OvVideo getByVideo(VideoInfo videoInfo);

    OvVideo getVideoByID(Integer vid);

    List<OvVideo> getLeftImages(Integer limit);

    List<OvVideo> getLeftImages2(Integer fid, Integer limit);

}
