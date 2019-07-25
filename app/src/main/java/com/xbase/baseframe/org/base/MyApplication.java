package com.xbase.baseframe.org.base;

import com.xx.base.org.BaseApplication;
import com.xx.base.project.base.ProBaseUtils;

/**
 * Created by lixingxing on 2019/4/16.
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ProBaseUtils.getInstance(getApplicationContext()).init();
    }

}
