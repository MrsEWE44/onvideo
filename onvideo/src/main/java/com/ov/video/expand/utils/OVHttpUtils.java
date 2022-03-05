package com.ov.video.expand.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OVHttpUtils {
    public void RefreshCookie(HttpServletRequest request , HttpServletResponse response){
        if(request.getCookies() != null){
            HashMap<String,Object> cookie_map = new HashMap<>();
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getValue() != null && !cookie.getValue().isEmpty()){
                    cookie_map.put(cookie.getName(),cookie.getValue());
                }
            }
            addCookie(cookie_map,response);
        }
    }
    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if("127.0.0.1".equals(ip)||"0:0:0:0:0:0:0:1".equals(ip)){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        return ip;
    }

    public String getRamdonString(int len) {
        StringBuffer sb = new StringBuffer();
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    public int getRandom(int max , int min){
        return new Random().nextInt(max)%(max-min+1) + min;
    }

    public String getDATE(){
        return new SimpleDateFormat("YYYYMMddHHmmssSS").format(new Date());
    }

    public String getRandom2(int len) {
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < len; i++) {
            rs.append(r.nextInt(10));
        }
        return rs.toString();
    }

    public String getRequestCookieUid(HttpServletRequest request){
        String uid = null;
        if(request.getCookies() != null){
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("uid")) {
                    uid = cookie.getValue();
                }
            }
        }
        return  uid;
    }

    public void addCookie(Map<String,Object> cookie_map, HttpServletResponse response){
        if(cookie_map.size() > 0){
            cookie_map.forEach((k,v)->{
                Cookie cookie = new Cookie(k, (String) v);
                if(k.equals("uid") && v.toString().isEmpty()){
                    cookie.setMaxAge(0);
                    cookie.setValue(null);
                    cookie.setPath("/");
                }else{
                    cookie.setMaxAge(60*10);
                    cookie.setPath("/");
                }
                response.addCookie(cookie);
            });
        }
    }

    public void loginout(HttpServletRequest request,HttpServletResponse response){
        if(request.getCookies() != null){
            for (Cookie cookie : request.getCookies()) {
                cookie.setMaxAge(0);
                cookie.setValue(null);
                response.addCookie(cookie);
            }

        }
    }

    public void saveFileToLocal(String dirPath,String name, MultipartFile file){
        String localFilePath = dirPath+"/"+name;
        File file1 = new File(dirPath);
        File file2 = new File(localFilePath);
        if(!file1.exists()){
            file1.mkdirs();
        }
        if(file2.exists()){
            file2.delete();
        }
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(file2);
            byte[] buff = new byte[1024];
            int len=-1;
            while((len = inputStream.read(buff)) > -1){
                outputStream.write(buff,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ResponseEntity<FileSystemResource> export(File file) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }


}
