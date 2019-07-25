package com.xx.base.org;

import android.app.Application;
import com.xx.base.org.util.BaseLocalDbUtils;
import com.xx.base.org.util.BaseUtils;

/**
 * Created by lixingxing on 2019/4/24.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化全局基础工具类
        BaseUtils.init(getApplicationContext());
        // 初始化nosql数据库
        BaseLocalDbUtils.init(getApplicationContext());
    }
}
