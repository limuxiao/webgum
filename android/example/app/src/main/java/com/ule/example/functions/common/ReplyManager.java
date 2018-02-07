package com.ule.example.functions.common;


import java.util.HashMap;
import java.util.Map;

/**
 * @Title:
 * @Desc:       自MainAcitivty 以startActivityForResult方式跳转到其他Activity，返回之后的应答管理类
 * @CreateTime: 2017-08-31 10:58
 * @Creator: Liy
 * @Version: 1.0
 * @See
 */

public final class ReplyManager {

    private static final Map<Integer,IReply> replys = new HashMap<>();

    public static final synchronized void addReply(IReply reply){
        replys.put(reply.getId(),reply);
    }

    public static final synchronized void removeReply(IReply reply){
        replys.remove(reply);
    }

    public static final void clear(){
        replys.clear();
    }

    public static final IReply getReply(int id){
        return replys.get(id);
    }

}
