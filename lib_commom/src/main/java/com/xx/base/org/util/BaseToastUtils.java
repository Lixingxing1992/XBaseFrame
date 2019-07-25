package com.xx.base.org.util;


import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Toast显示工具类
 * Created by lixingxing
 */
public class BaseToastUtils {

    static Handler handler = new Handler(Looper.getMainLooper());

    public static void toast(final String str,final int type){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseUtils.getContext(),str, type == 0?Toast.LENGTH_SHORT:Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void toast(final String str){
        toast(str,0);
    }
    public static void toastLong(final String str){
        toast(str,1);
    }
}
