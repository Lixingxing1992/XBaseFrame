package com.xx.base.org.check;

import java.io.File;

/**
 * 手机或程序检查
 *
 * @author Lixingxing
 */
public class BaseAppCheckUtils {

    //    通过检测指定目录下是否存在 su 程序来检测运行环境是否为 Root 设备
    public static boolean checkRootPathSU() {
        File f = null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/",
                "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
