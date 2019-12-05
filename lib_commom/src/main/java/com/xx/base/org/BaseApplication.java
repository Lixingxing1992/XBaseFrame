package com.xx.base.org;

import android.app.Application;
import com.xx.base.org.util.BaseLocalDbUtils;
import com.xx.base.org.util.BaseUtils;

/**
 * Created by lixingxing on 2019/4/24.
 */
public class BaseApplication extends Application {
    private static Application app;

    public static Application getInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
