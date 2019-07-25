package com.xx.base.org.util;

import android.util.Log;
import com.xx.base.org.BuildConfig;

/**
 * Created by lixingxing on 2019/4/19.
 */
public class BaseLogUtils extends Exception{
    static boolean isOpen = BuildConfig.DEBUG;
    public static void openLog(boolean isOpens){
        isOpen = isOpens;
    }
    public static void D(String TAG, String text){
        if(isOpen){
            Log.d(TAG, !BaseStringUtils.isEmpty(text)?text:" msg is null");
        }
    }
    public static void D(String text){
        D(BaseUtils.getContext().getPackageName(), text);
    }
    // 带有 BaseLogUtils信息的打印
    public static void Debug(String TAG, String text){
        D(TAG, new BaseLogUtils() + "\n" + text);
    }
    public static void Debug(String text){
        Debug(BaseUtils.getContext().getPackageName(),text);
    }


    public static void I(String TAG, String text){
        if(isOpen){
            Log.i(TAG, !BaseStringUtils.isEmpty(text)?text:" msg is null");
        }
    }
    public static void I(String text){
        I(BaseUtils.getContext().getPackageName(), text);
    }
    // 带有 BaseLogUtils信息的打印
    public static void Info(String TAG, String text){
        I(TAG, new BaseLogUtils() + "\n" + text);
    }
    public static void Info(String text){
        Info(BaseUtils.getContext().getPackageName(),text);
    }


    public static void W(String TAG, String text){
        if(isOpen){
            Log.e(TAG, !BaseStringUtils.isEmpty(text)?text:" msg is null");
        }
    }
    public static void W(String TAG, String text,Exception e){
        if(isOpen){
            Log.w(TAG,text,e);
        }
    }
    public static void W(String text){
        E(BaseUtils.getContext().getPackageName(), text);
    }
    // 带有 BaseLogUtils信息的打印
    public static void Warn(String TAG, String text){
        E(TAG, new BaseLogUtils().toString() + "\n" + text);
    }
    public static void Warn(String text){
        Error(BaseUtils.getContext().getPackageName(),text);
    }

    public static void E(String TAG, String text){
        if(isOpen){
            Log.e(TAG, !BaseStringUtils.isEmpty(text)?text:" msg is null");
        }
    }
    public static void E(String TAG, String text,Throwable e){
        if(isOpen){
            Log.e(TAG,text,e);
        }
    }
    public static void E(String text){
        E(BaseUtils.getContext().getPackageName(), text);
    }
    // 带有 BaseLogUtils信息的打印
    public static void Error(String TAG, String text){
        E(TAG, buildLogMsg(text) + "\n" + text);
    }
    public static void Error(String text){
        Error(BaseUtils.getContext().getPackageName(),buildLogMsg(text));
    }

    public String getStraceDesc(){
        StackTraceElement[] trace = getStackTrace();
        if (trace == null || trace.length == 0) {
            return "";
        }
        return "Log信息所在位置:" + trace[0].toString();
    }


    public static void sys(String text){
        if(isOpen){
            System.out.print(!BaseStringUtils.isEmpty(text)?text:" msg is null");
        }
    }

    public BaseLogUtils() {
        super();
    }

    public static String buildLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);

        return sb.toString();

    }

    @Override
    public String toString() {
        return getStraceDesc();//line() + "|" + fun() + "|";
    }
    
}
