package com.ov.video.service;

import com.ov.video.entity.OvComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ov.video.entity.OvUser;
import com.ov.video.entity.OvVideo;
import com.ov.video.vo.CommitInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
public interface IOvCommentService extends IService<OvComment> {

    List<CommitInfo> getComInfos(OvUser user);

    OvComment getByUID(String uid);

    List<CommitInfo> getCommitInfos(OvVideo byVideo, OvUser user);

    CommitInfo getCommitInfo(OvComment com ,OvVideo byVideo, OvUser user);

    Boolean delComment(Integer vid, Integer uid);


    boolean delCommentByCommitInfo(CommitInfo commitInfo, String requestCookieUid);

}
