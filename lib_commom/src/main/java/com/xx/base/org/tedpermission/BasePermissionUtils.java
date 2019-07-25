package com.xx.base.org.tedpermission;

import com.xx.base.org.util.BaseUtils;

/**
 * 权限类
 * Created by lixingxing on 2019/4/25.
 */
public class BasePermissionUtils {

    // 带提示框的权限检查
    public static void checkPermission(PermissionListener permissionListener,String message,String... permissions){
        new TedPermission(BaseUtils.getContext())
                .setPermissionListener(permissionListener)
                .setDeniedMessage(message)
                .setPermissions(permissions)
                .check();
    }
}
