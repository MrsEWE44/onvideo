package com.ov.video.expand;

import java.util.HashMap;

public class resultMSG extends HashMap {

    public static resultMSG add(ResultStatus resultStatus){
        resultMSG msgResult = new resultMSG();
        msgResult.add("code",resultStatus.getStatus());
        msgResult.add("msg",resultStatus.getMsg());
        return  msgResult;
    }

    public resultMSG add(Object key , Object data){
        super.put(key,data);
        return  this;
    }

    public resultMSG put(Object key,Object data){
        return  add(key,data);
    }

}