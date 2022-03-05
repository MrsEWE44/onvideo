package com.ov.video.annotation.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ov.video.annotation.NeedPermission;
import com.ov.video.entity.OvRole;
import com.ov.video.entity.OvUser;
import com.ov.video.expand.ResultStatus;
import com.ov.video.expand.resultMSG;
import com.ov.video.expand.utils.OVHttpUtils;
import com.ov.video.service.IOvRoleService;
import com.ov.video.service.IOvUserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Aspect
@Component
public class NeedPermissionImpl {

    @Autowired
    IOvUserService userService;

    @Autowired
    IOvRoleService roleService;


    public Boolean isMe(ProceedingJoinPoint pjp){
        for (Object arg : pjp.getArgs()) {
            if(arg instanceof HashMap){
                try {
                    String isme =((HashMap) arg).get("isme").toString();
                    if(isme.equals("false")){
                        return false;
                    }
                }catch (Exception e){
                    return true;
                }
            }
        }
        return true;
    }

    @Around(" @annotation(needPermission)")
    public Object isPermission(ProceedingJoinPoint pjp, NeedPermission needPermission) {
        Object result = null;
        Boolean me = isMe(pjp);
        boolean havePermission = false;
        for (Object o : pjp.getArgs()) {
            if(o instanceof HttpServletRequest){
                String uid = new OVHttpUtils().getRequestCookieUid((HttpServletRequest) o);
                LambdaQueryWrapper<OvUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(OvUser::getUid,uid);
                OvUser ovUser = userService.getOne(queryWrapper);
             try {
                 LambdaQueryWrapper<OvRole> ovRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
                 ovRoleLambdaQueryWrapper.eq(OvRole::getRid,ovUser.getRid());
                 OvRole ovRole = roleService.getOne(ovRoleLambdaQueryWrapper);
                 for (String s : needPermission.Role()) {
                     if(s.equals(ovRole.getRname())){
                         havePermission=true;
                     }
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }
            }
        }
        if(!havePermission && me){
            return resultMSG.add(ResultStatus.authorization_admin_change);
        }
    try {
            result=pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;

      }

}