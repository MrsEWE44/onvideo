package com.ov.video.controller.Error;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ov.video.expand.ResultStatus;
import com.ov.video.expand.resultMSG;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;

@RestController
public class MyErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    private static ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

    @RequestMapping(value=ERROR_PATH,produces = {MediaType.TEXT_HTML_VALUE})
    @ResponseBody
    public Object error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        try {
//            Integer code = Integer.valueOf(request.getAttribute("code").toString());
//            if(code == 0){
//                return resultMSG.add(ResultStatus.success).toString();
//            }
//        }catch (Exception e){}
        System.out.println("statusCode ::: " + statusCode);
        String filename = "500.html";
        switch (statusCode){
            case 400:
            case 401:
            case 402:
            case 403:
            case 405:
            case 415:
                filename="400.html";
                break;
            case 404:
                filename="404.html";
                break;
            case 500:
                filename="500.html";
                break;
            case 501:
            case 502:
            case 503:
                filename="50x.html";
                break;
                default:
                    filename="500.html";
                    break;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream("templates/error/"+filename)));
        String line = "";
        StringBuffer sb = new StringBuffer();
        while((line = bufferedReader.readLine()) != null){
            sb.append(line);
        }
        bufferedReader.close();
        return sb.toString();
    }
}