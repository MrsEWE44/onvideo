package com.ov.video.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ov.video.annotation.NeedPermission;
import com.ov.video.entity.OvUser;
import com.ov.video.entity.OvVideo;
import com.ov.video.expand.ResultStatus;
import com.ov.video.expand.resultMSG;
import com.ov.video.expand.utils.OVHttpUtils;
import com.ov.video.service.IOvCommentService;
import com.ov.video.service.IOvUserService;
import com.ov.video.service.IOvVideoService;
import com.ov.video.vo.VideoInfo;
import com.ov.video.vo.VideoPlayerPage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Collections;
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
@RequestMapping("/video/video")
public class OvVideoController {

    @Autowired
    IOvVideoService videoService;

    @Autowired
    IOvUserService userService;

    @Autowired
    IOvCommentService commentService;


    OVHttpUtils ovHttpUtils = new OVHttpUtils();


    @GetMapping("/getIndexHeadVideo")
    public Object getIndexHeadVideo(HttpServletRequest request, HttpServletResponse response){
        List<OvVideo> ovVideos = videoService.getRandomVideo(8);
        Collections.shuffle(ovVideos);
        List<VideoInfo> videoInfos = ovVideos.stream().map(ovVideo -> {
            OvUser user = userService.getUser(ovVideo.getUid().toString());
            return videoService.getVideoInfo(ovVideo, user);
        }).collect(Collectors.toList());
        ovHttpUtils.RefreshCookie(request, response);
        return resultMSG.add(ResultStatus.success).add("data",videoInfos);
    }

    @GetMapping("/getChilder")
    public Object getChilder(@PathParam("fid") String fid,HttpServletRequest request,HttpServletResponse response){
        LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvVideo::getVfid,fid).last("limit 8");
        List<OvVideo> ovVideos = videoService.list(queryWrapper);
        List<VideoInfo> videoInfos = ovVideos.stream().map(ovVideo -> {
            OvUser user = userService.getUser(ovVideo.getUid().toString());
            return videoService.getVideoInfo(ovVideo, user);
        }).collect(Collectors.toList());
        ovHttpUtils.RefreshCookie(request, response);
        return resultMSG.add(ResultStatus.success).add("data",videoInfos);
    }

    @GetMapping("/getChilder2")
    public Object getChilder2(@PathParam("fid") String fid,@PathParam("mid") String mid,HttpServletRequest request,HttpServletResponse response){
        LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvVideo::getVfid,fid).eq(OvVideo::getVmid,mid).last("limit 8");
        List<OvVideo> ovVideos = videoService.list(queryWrapper);
        List<VideoInfo> videoInfos = ovVideos.stream().map(ovVideo -> {
            OvUser user = userService.getUser(ovVideo.getUid().toString());
            return videoService.getVideoInfo(ovVideo, user);
        }).collect(Collectors.toList());
        ovHttpUtils.RefreshCookie(request, response);
        return resultMSG.add(ResultStatus.success).add("data",videoInfos);
    }

    @PostMapping("/getChilder3")
    public Object getChilder3(@RequestBody HashMap<String,String> map, HttpServletRequest request,HttpServletResponse response){
        String fid = map.get("fid");
        String mid = map.get("mid");
        Integer page = null,size = null;
        try {
           page = Integer.valueOf(map.get("page")) ;
           size = Integer.valueOf(map.get("size"));
        }catch (Exception e){
            e.printStackTrace();
        }
        LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvVideo::getVfid,fid).eq(OvVideo::getVmid,mid);
        IPage<OvVideo> ovVideoPage = new Page<>(page, size);
        IPage<OvVideo> videoIPage = videoService.page(ovVideoPage,queryWrapper);
        List<VideoInfo> videoInfos = videoIPage.getRecords().stream().map(ovVideo -> {
            OvUser user = userService.getUser(ovVideo.getUid().toString());
            return videoService.getVideoInfo(ovVideo, user);
        }).collect(Collectors.toList());
        ovHttpUtils.RefreshCookie(request, response);
        return resultMSG.add(ResultStatus.success).add("data",videoInfos).add("total",videoIPage.getTotal());
    }

    @GetMapping("/getRefreshVideo")
    public Object getRefreshVideo(@PathParam("fid") String fid,HttpServletRequest request,HttpServletResponse response){
        List<OvVideo> ovVideos = videoService.getRandomVideo(Integer.valueOf(fid),8);
        Collections.shuffle(ovVideos);
        List<VideoInfo> videoInfos = ovVideos.stream().map(ovVideo -> {
            OvUser user = userService.getUser(ovVideo.getUid().toString());
            return videoService.getVideoInfo(ovVideo, user);
        }).collect(Collectors.toList());
        ovHttpUtils.RefreshCookie(request, response);
        return resultMSG.add(ResultStatus.success).add("data",videoInfos);
    }

    @GetMapping("/getRefreshVideo2")
    public Object getRefreshVideo2(@PathParam("fid") String fid,@PathParam("mid") String mid,HttpServletRequest request,HttpServletResponse response){
        List<OvVideo> ovVideos = videoService.getRandomVideo(Integer.valueOf(fid),Integer.valueOf(mid),8);
        Collections.shuffle(ovVideos);
        List<VideoInfo> videoInfos = ovVideos.stream().map(ovVideo -> {
            OvUser user = userService.getUser(ovVideo.getUid().toString());
            return videoService.getVideoInfo(ovVideo, user);
        }).collect(Collectors.toList());
        ovHttpUtils.RefreshCookie(request, response);
        return resultMSG.add(ResultStatus.success).add("data",videoInfos);
    }

    @PostMapping("/getVideoPlayer")
    public Object getVideoPlayer(@RequestBody VideoInfo videoInfo , HttpServletRequest request, HttpServletResponse response){
        Integer uid = videoService.getByUserID(videoInfo);
        OvUser user = userService.getUser(uid.toString());
        VideoPlayerPage videoPlayerPage = videoService.getVideoPlayerPageInfo(videoInfo,user);
        ovHttpUtils.RefreshCookie(request, response);
        return resultMSG.add(ResultStatus.success).add("data",videoPlayerPage);
    }

    @GetMapping("/getVideoFile")
    public ResponseEntity<FileSystemResource> getVideoFile(@PathParam("vid") String vid, HttpServletRequest request, HttpServletResponse response){
        LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvVideo::getVid,vid);
        OvVideo one = videoService.getOne(queryWrapper);
        String filePath = one.getVpath();
//        String property = System.getProperty("user.dir");
//        System.out.println(property+" -- "+filePath);
        File file = new File(filePath);
        if (file.exists()) {
//            System.out.println("is exists");
            return ovHttpUtils.export(file);
        }
        return null;
    }

    @GetMapping("/getVideoIcon")
    public ResponseEntity<FileSystemResource> getVideoIcon(@PathParam("vid") String vid, HttpServletRequest request, HttpServletResponse response){
        LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OvVideo::getVid,vid);
        OvVideo one = videoService.getOne(queryWrapper);
        String filePath = one.getVicon();
//        String property = System.getProperty("user.dir");
//        System.out.println(property+" -- "+filePath);
        File file = new File(filePath);
        if (file.exists()) {
//            System.out.println("is exists");
            return ovHttpUtils.export(file);
        }
        return null;
    }

    @PostMapping("/searchVideo")
    public Object searchVideo(@RequestBody HashMap<String,String> map, HttpServletRequest request, HttpServletResponse response){
        String search = map.get("search");
        Integer size = null,page = null;
        try {
            size = Integer.valueOf(map.get("size"));
            page = Integer.valueOf(map.get("page"));
        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println("search ::: " + search + " -- size ::: " + size + "   --   page ::  " + page);
        LambdaQueryWrapper<OvVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(OvVideo::getVtitle,search).or().like(OvVideo::getVmsg,search);
        IPage<OvVideo> ovVideoPage = new Page<>(page, size);
        IPage<OvVideo> videoIPage = videoService.page(ovVideoPage,queryWrapper);
        List<VideoInfo> videoInfos = videoIPage.getRecords().stream().map(video->{
            OvUser user = userService.getUser(video.getUid().toString());
            return videoService.getVideoInfo(video,user);
        }).collect(Collectors.toList());
        ovHttpUtils.RefreshCookie(request, response);
        return resultMSG.add(ResultStatus.success).add("data",videoInfos).add("total",videoIPage.getTotal());
    }

    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @PostMapping("/delvideo")
    public Object searchVideo(@RequestParam("vid") String vid, HttpServletRequest request, HttpServletResponse response){
        try {
            OvVideo videoByID = videoService.getVideoByID(Integer.valueOf(vid));
            String videoPath = videoByID.getVpath();
            if(videoPath == null || videoPath.isEmpty() || videoPath.indexOf("http") != -1){
                videoPath="aaa/abc.txt";
                File file = new File(videoPath);
                file.createNewFile();
            }
            File file = new File(videoPath);
            boolean fdel = file.delete();
            OvUser user = userService.getUser(videoByID.getUid().toString());
            Boolean del = commentService.delComment(videoByID.getVid(),user.getUid());
            boolean delvideo = videoService.removeById(videoByID);
            if(del && delvideo &&fdel){
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
    @GetMapping("/getchangevideo")
    public Object getChangeVideo(@PathParam("vid") String vid, HttpServletRequest request, HttpServletResponse response) {
        return resultMSG.add(ResultStatus.success).add("data",videoService.getVideoByID(Integer.valueOf(vid)));
    }

    @NeedPermission(Role = {"ROOT","ADMIN","USER"})
    @PostMapping("/changevideo")
    public Object changeVideo(@RequestParam("icon") MultipartFile icon,@RequestParam("vid") String vid , @RequestParam("title") String title, @RequestParam("msg") String msg, HttpServletRequest request, HttpServletResponse response){
        try {
            LocalDateTime time = LocalDateTime.now().withNano(0);
            OvVideo videoByID = videoService.getVideoByID(Integer.valueOf(vid));
            videoByID.setVtitle(title);
            String uid = ovHttpUtils.getRequestCookieUid(request);
            String suffix2 = icon.getOriginalFilename();
            suffix2=suffix2.substring(suffix2.lastIndexOf("."));
            String name2 = ovHttpUtils.getDATE()+suffix2;
            String dirPath2="icon/"+uid;
            String videoPath = videoByID.getVicon();
            if(videoPath == null || videoPath.isEmpty() || videoPath.indexOf("http") != -1){
                videoPath="aaa/abc.txt";
                new File("aaa").mkdirs();
                File file = new File(videoPath);
                file.createNewFile();
            }
            File file = new File(videoPath);
            boolean fdel = file.delete();
            videoByID.setVicon(dirPath2+"/"+name2);
            videoByID.setVmsg(msg);
            videoByID.setVdate(time);
            boolean videoupdate = videoService.updateById(videoByID);
            if(fdel && videoupdate){
                ovHttpUtils.saveFileToLocal(dirPath2,name2,icon);
                ovHttpUtils.RefreshCookie(request, response);
                return resultMSG.add(ResultStatus.success);
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultMSG.add(ResultStatus.error);
        }
        return resultMSG.add(ResultStatus.error);
    }

    @GetMapping("/getLeftImages")
    public Object getLeftImages(@PathParam("limit") String limit,HttpServletRequest request,HttpServletResponse response){
//        Integer limit = 8;
        List<OvVideo> ovVideos = videoService.getLeftImages(Integer.valueOf(limit));
        Collections.shuffle(ovVideos);
        List<VideoInfo> videoInfos = ovVideos.stream().map(ovVideo -> {
            OvUser user = userService.getUser(ovVideo.getUid().toString());
            return videoService.getVideoInfo(ovVideo, user);
        }).collect(Collectors.toList());
        ovHttpUtils.RefreshCookie(request, response);
        return resultMSG.add(ResultStatus.success).add("data",videoInfos);
    }

    @GetMapping("/getLeftImages2")
    public Object getLeftImages2(@PathParam("fid") String fid,@PathParam("limit") String limit,HttpServletRequest request,HttpServletResponse response){
        List<OvVideo> ovVideos = videoService.getLeftImages2(Integer.valueOf(fid), Integer.valueOf(limit));
        Collections.shuffle(ovVideos);
        List<VideoInfo> videoInfos = ovVideos.stream().map(ovVideo -> {
            OvUser user = userService.getUser(ovVideo.getUid().toString());
            return videoService.getVideoInfo(ovVideo, user);
        }).collect(Collectors.toList());
        ovHttpUtils.RefreshCookie(request, response);
        return resultMSG.add(ResultStatus.success).add("data",videoInfos);
    }

    @GetMapping("/getVideoInfo")
    public Object getVideoInfo(@PathParam("vid") String vid,HttpServletRequest request,HttpServletResponse response){
        try {

            OvVideo videoByID = videoService.getVideoByID(Integer.valueOf(vid));
            OvUser user = userService.getUser(videoByID.getUid().toString());
            VideoInfo videoInfo = videoService.getVideoInfo(videoByID, user);
            ovHttpUtils.RefreshCookie(request, response);
            return resultMSG.add(ResultStatus.success).add("data",videoInfo);

        }catch(Exception e){
            e.printStackTrace();
        }
        return resultMSG.add(ResultStatus.error);

    }



}
