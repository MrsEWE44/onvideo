package com.ov.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ov.video.mapper")
@SpringBootApplication
public class OnvideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnvideoApplication.class, args);
    }

}
