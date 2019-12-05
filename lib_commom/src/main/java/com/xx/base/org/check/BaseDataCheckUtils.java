package com.xx.base.org.check;

import com.xx.base.org.util.BaseToastUtils;

/**
 * 数据检查
 * @author Lixingxing
 */
public class BaseDataCheckUtils {
    /**
     * 检查数据是否为null
     * @param object
     * @return
     */
    public static boolean checkDataIsNull(Object object){
        return object == null;
    }
    /**
     * 检查数据是否为null,并且抛出空指针异常
     * @param object
     * @return
     */
    public static void checkDataIsNullWithError(Object object,String errorMsg){
        if(null == errorMsg || "".equals(errorMsg)){
            errorMsg = "object can not be null!";
        }
        if(checkDataIsNull(object)){
            throw new NullPointerException(errorMsg);
        }
    }
    /**
     * 检查数据是否为null,并且toast出错误信息
     * @param object
     * @return
     */
    public static boolean checkDataIsNullWithToast(Object object,String errorMsg){
        if(null == errorMsg || "".equals(errorMsg)){
            errorMsg = "object can not be null!";
        }
        boolean flag = checkDataIsNull(object);
        if(flag){
            BaseToastUtils.toast(errorMsg);
        }
        return flag;
    }
}
