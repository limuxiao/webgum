package com.ule.webgum.replys;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-03-05 14:13
 * @Creator: Liy
 * @Version: 1.0
 */

public final class IDFactory {

    public static final int ID_START = 0x0000;

    private static int cur_id = ID_START;


    public static synchronized int generateID(){
        cur_id += 1;
        return cur_id;
    }


}
