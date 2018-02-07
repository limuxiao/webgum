package com.ule.example.functions.common;

import android.content.Intent;

/**
 * @Title:          --      返回到MainActivity后的应答类
 * @Desc:
 * @CreateTime: 2017-08-31 10:54
 * @Creator: Liy
 * @Version: 1.0
 */

public interface IReply {

    int getId();

    void reply(Intent intent);

}
