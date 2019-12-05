package com.xx.base.project.base;

import com.xx.base.org.BaseApplication;

/**
 * project application
 * @author Lixingxing
 */
public class ProBaseApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ProBaseUtils.getInstance(getApplicationContext()).init();
    }
}
