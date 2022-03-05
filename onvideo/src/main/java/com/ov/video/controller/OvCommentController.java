package com.ov.video.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ov.video.annotation.NeedPermission;
import com.ov.video.entity.OvComment;
import com.ov.video.entity.OvUser;
import com.ov.video.entity.OvVideo;
import com.ov.video.expand.ResultStatus;
import com.ov.video.expand.resultMSG;
import com.ov.video.expand.utils.OVHttpUtils;
import com.ov.video.service.IOvCommentService;
import com.ov.video.service.IOvUserService;
import com.ov.video.service.IOvVideoService;
import com.ov.video.vo.CommitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
@Controller
@RequestMapping("/video/comment")
public class OvCommentController {

    @Autowired
    IOvCommentService commentService;

    @Autowired
    IOvVideoService videoService;

    @Autowired
    IOvUserService userService;


    OVHttpUtils ovHttpUtils= new OVHttpUtils();

    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @PostMapping("/sendMsg")
    public Object sendMsg(@RequestBody HashMap<String,String> map, HttpServletRequest request, HttpServletResponse response){
        Integer vid = null;
        try {
            LocalDateTime time = LocalDateTime.now().withNano(0);
            String msg = map.get("msg");
            vid = Integer.valueOf(map.get("vid"));
            String requestCookieUid = ovHttpUtils.getRequestCookieUid(request);
            OvUser user = userService.getUser(requestCookieUid);
            Integer uid = user.getUid();
            OvVideo ovVideo =  videoService.getVideoByID(vid);
            Integer ovVideoVid = ovVideo.getVid();
            OvComment comment = new OvComment();
            comment.setMsg(msg);
            comment.setUid(uid);
            comment.setVid(ovVideoVid);
            comment.setCdate(time);
            boolean save = commentService.save(comment);
            if(save){
               ovHttpUtils.RefreshCookie(request, response);
               return resultMSG.add(ResultStatus.success);
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultMSG.add(ResultStatus.error);
        }
        return resultMSG.add(ResultStatus.error);
    }


    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @PostMapping("/delMSG")
    public Object delMSG(@RequestBody CommitInfo commitInfo, HttpServletRequest request, HttpServletResponse response){
        try {
            String requestCookieUid = ovHttpUtils.getRequestCookieUid(request);

            boolean remove = commentService.delCommentByCommitInfo(commitInfo,requestCookieUid);
            if(remove){
                ovHttpUtils.RefreshCookie(request, response);
                return resultMSG.add(ResultStatus.success);
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultMSG.add(ResultStatus.error);
        }
        return resultMSG.add(ResultStatus.error);
    }



}
