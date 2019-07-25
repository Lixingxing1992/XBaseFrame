package com.xx.base.org.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lixingxing on 2019/6/13.
 */
public class BaseDateUtils {

    // 获取格式化日期
    public static String getDateStr(Date date,String format){
        if(date == null){
            return "";
        }
        if(null == format || "".equals(format)){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
    // 默认 格式 "yyyy-MM-dd HH:mm:ss"
    public static String getDateStr(Date date){
       return getDateStr(date,null);
    }
}
