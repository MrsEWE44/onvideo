package com.ov.video.annotation.Impl;

import com.ov.video.annotation.isLogin;
import com.ov.video.expand.ResultStatus;
import com.ov.video.expand.resultMSG;
import com.ov.video.expand.utils.OVHttpUtils;
import com.ov.video.service.IOvUserService;
import com.ov.video.vo.LoginUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class isLoginImpl {

    @Autowired
    IOvUserService userService;

    @Around(" @annotation(isLogin)")
    public Object isPermission(ProceedingJoinPoint pjp, isLogin isLogin) {
        Object result = null;
        for (Object o : pjp.getArgs()) {
            if(o instanceof LoginUser){
                LoginUser loginUser = (LoginUser) o;
                boolean isLoginUser = userService.login(loginUser);
                if(!isLoginUser){
                   return resultMSG.add(ResultStatus.login_failure);
                }
            }
        }

        try {
            result=pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

}
