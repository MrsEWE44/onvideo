package com.ov.video.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ov.video.annotation.NeedPermission;
import com.ov.video.annotation.isLogin;
import com.ov.video.entity.OvComment;
import com.ov.video.entity.OvUser;
import com.ov.video.entity.OvVideo;
import com.ov.video.expand.ResultStatus;
import com.ov.video.expand.resultMSG;
import com.ov.video.expand.utils.OVHttpUtils;
import com.ov.video.service.IOvCommentService;
import com.ov.video.service.IOvUserService;
import com.ov.video.service.IOvVideoService;
import com.ov.video.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sir
 * @since 2022-02-25
 */
@RestController
@RequestMapping("/video/user")
public class OvUserController {


    @Autowired
    IOvUserService userService;

    @Autowired
    IOvVideoService videoService;

    @Autowired
    IOvCommentService commentService;


    OVHttpUtils ovHttpUtils= new OVHttpUtils();

    @isLogin
    @PostMapping("/login")
    public Object loginuser(@RequestBody LoginUser loginUser , HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid",loginUser.getUid());
        ovHttpUtils.addCookie(hashMap,response);
        return resultMSG.add(ResultStatus.success);
    }

    @PostMapping("/reg")
    public Object reguser(@RequestBody LoginUser loginUser , HttpServletRequest request, HttpServletResponse response){
        LambdaQueryWrapper<OvUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvUser::getUid,loginUser.getUid());
        long count = userService.count(queryWrapper);
        if(count == 0){
          boolean add =   userService.addDefaultUser(loginUser);
          if(add){
              return resultMSG.add(ResultStatus.success);
          }
        }
        return resultMSG.add(ResultStatus.regUser);
    }

    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @GetMapping("/logout")
    public Object logout(HttpServletRequest request, HttpServletResponse response){
        ovHttpUtils.loginout(request, response);
        return resultMSG.add(ResultStatus.success);
    }

    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @GetMapping("/getUserInfo")
    public Object getUserInfo(HttpServletRequest request, HttpServletResponse response){
        OvUser user = userService.getUser(ovHttpUtils.getRequestCookieUid(request));
        return resultMSG.add(ResultStatus.success).add("data",user);
    }

    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @GetMapping("/getUserInfo2")
    public Object getUserInfo2(@PathParam("uid") String uid, HttpServletRequest request, HttpServletResponse response){
        OvUser user = userService.getUser(uid);
        return resultMSG.add(ResultStatus.success).add("data",user);
    }

    @GetMapping("/getUserIcon")
    public ResponseEntity<FileSystemResource> getUserIcon(@PathParam("uid") String uid, HttpServletRequest request, HttpServletResponse response){
        LambdaQueryWrapper<OvUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvUser::getUid,uid);
        OvUser one = userService.getOne(queryWrapper);
        String filePath = one.getUicon();
        File file = new File(filePath);
        if (file.exists()) {
            return ovHttpUtils.export(file);
        }
        return null;
    }

    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @PostMapping("/getUserHomePageVideos")
    public Object getUserHomePageVideos(@RequestBody HashMap<String,String> map, HttpServletRequest request, HttpServletResponse response){
        String isme = map.get("isme");
        String uid = map.get("uid");
        if(uid == null || uid.isEmpty()){
            uid=ovHttpUtils.getRequestCookieUid(request);
        }
        Integer page = null,size=null;
        try {
            page = Integer.valueOf(map.get("page")) ;
            size =Integer.valueOf( map.get("size"));
        }catch (Exception e){
            e.printStackTrace();
        }
        OvUser user = userService.getUser(uid);
        try {
            LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OvVideo::getUid,user.getUid());
            IPage<OvVideo> ovVideoPage = new Page<>(page, size);
            IPage<OvVideo> videoIPage = videoService.page(ovVideoPage,queryWrapper);
            List<VideoInfo> videoInfos = videoIPage.getRecords().stream().map(video->{
                return videoService.getVideoInfo(video,user);
            }).collect(Collectors.toList());
            ovHttpUtils.RefreshCookie(request, response);
            return resultMSG.add(ResultStatus.success).add("data",videoInfos).add("total",videoIPage.getTotal());
        }catch (Exception e){
            return resultMSG.add(ResultStatus.admin_download_error);
        }
    }

    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @PostMapping("/getUserHomePageCommits")
    public Object getUserHomePageCommits(@RequestBody HashMap<String,String> map, HttpServletRequest request, HttpServletResponse response){
        String isme = map.get("isme");
        String uid = map.get("uid");
        if(uid == null || uid.isEmpty()){
            uid=ovHttpUtils.getRequestCookieUid(request);
        }
        Integer page = null,size=null;
        try {
            page = Integer.valueOf(map.get("page")) ;
            size =Integer.valueOf( map.get("size"));
        }catch (Exception e){
            e.printStackTrace();
        }
        OvUser user = userService.getUser(uid);
        try {
            LambdaQueryWrapper<OvComment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OvComment::getUid,user.getUid());
            IPage<OvComment> ovCommentPage = new Page<>(page, size);
            IPage<OvComment> ovCommentIPage = commentService.page(ovCommentPage,queryWrapper);
            List<CommitInfo> commitInfos = ovCommentIPage.getRecords().stream().map(com -> {
                LambdaQueryWrapper<OvVideo> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OvVideo::getVid, com.getVid());
                OvVideo ovVideo = videoService.getOne(wrapper);
                return commentService.getCommitInfo(com,ovVideo, user);
            }).collect(Collectors.toList());
            ovHttpUtils.RefreshCookie(request, response);
            return resultMSG.add(ResultStatus.success).add("data",commitInfos).add("total",ovCommentIPage.getTotal());
        }catch (Exception e){
            return resultMSG.add(ResultStatus.admin_download_error);
        }
    }

    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @PostMapping("/getUserHomePageDynamics")
    public Object getUserHomePageDynamics(@RequestBody HashMap<String,String> map, HttpServletRequest request, HttpServletResponse response){
        String isme = map.get("isme");
        String uid = map.get("uid");
        if(uid == null || uid.isEmpty()){
            uid=ovHttpUtils.getRequestCookieUid(request);
        }
        Integer page = null,size=null;
        try {
            page = Integer.valueOf(map.get("page")) ;
            size =Integer.valueOf( map.get("size"));
        }catch (Exception e){
            e.printStackTrace();
        }
        OvUser user = userService.getUser(uid);
        try {
            LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OvVideo::getUid,user.getUid());
            IPage<OvVideo> ovVideoPage = new Page<>(page, size);
            IPage<OvVideo> videoIPage = videoService.page(ovVideoPage,queryWrapper);
            List<DynamicInfo> videoInfos = videoIPage.getRecords().stream().map(video->{
                DynamicInfo dynamicInfo = new DynamicInfo();
                String vicon = video.getVicon();
                if(vicon.indexOf("http") == -1 || vicon.isEmpty()){
                    vicon="";
                }
                dynamicInfo.setVid(video.getVid());
                dynamicInfo.setDate(video.getVdate());
                dynamicInfo.setIcon(vicon);
                dynamicInfo.setMsg(video.getVmsg());
                dynamicInfo.setTitle(video.getVtitle());
                dynamicInfo.setUid(video.getUid());
                return dynamicInfo;
            }).collect(Collectors.toList());
            ovHttpUtils.RefreshCookie(request, response);
            return resultMSG.add(ResultStatus.success).add("data",videoInfos).add("total",videoIPage.getTotal());
        }catch (Exception e){
            return resultMSG.add(ResultStatus.admin_download_error);
        }
    }

    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @PostMapping("/uploadFile")
    public Object uploadFile(@RequestParam("msg") String msg,@RequestParam("mid") String mid,@RequestParam("fid") String fid,@RequestParam("title") String title, @RequestParam("file") MultipartFile file,@RequestParam("icon") MultipartFile icon, HttpServletRequest request, HttpServletResponse response){
        LocalDateTime time = LocalDateTime.now().withNano(0);
        String suffix = file.getOriginalFilename();
        suffix=suffix.substring(suffix.lastIndexOf("."));
        String name = ovHttpUtils.getDATE()+suffix;
        String uid = ovHttpUtils.getRequestCookieUid(request);
        String dirPath="video/"+uid;
        ovHttpUtils.saveFileToLocal(dirPath,name,file);

        String suffix2 = icon.getOriginalFilename();
        suffix2=suffix2.substring(suffix2.lastIndexOf("."));
        String name2 = ovHttpUtils.getDATE()+suffix2;
        String dirPath2="icon/"+uid;
        ovHttpUtils.saveFileToLocal(dirPath2,name2,icon);

        OvVideo ovVideo = new OvVideo();
        ovVideo.setVdate(time);
        ovVideo.setUid(Integer.valueOf(uid));
        ovVideo.setVfid(Integer.valueOf(fid));
        ovVideo.setVmid(Integer.valueOf(mid));
        ovVideo.setVmsg(msg);
        ovVideo.setVpath(dirPath+"/"+name);
        ovVideo.setVtitle(title);
        ovVideo.setVicon(dirPath2+"/"+name2);
        boolean save = videoService.save(ovVideo);
        if(save){
            return resultMSG.add(ResultStatus.success);
        }
        return resultMSG.add(ResultStatus.error);
    }



}
