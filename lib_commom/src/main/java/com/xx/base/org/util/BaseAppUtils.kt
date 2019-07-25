package com.xx.base.org.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.xx.base.org.tedpermission.BasePermissionUtils
import com.xx.base.org.tedpermission.PermissionListener
import java.util.ArrayList

/**
 * 程序里使用的一些通用方法
 * Created by lixingxing on 2019/5/20.
 */
object BaseAppUtils{
    fun getScreenWidth(context: Context):Int{
        val mDisplayMetrics = context.resources.displayMetrics
        return mDisplayMetrics.widthPixels
    }
    fun getScreenHeight(context: Context):Int{
        val mDisplayMetrics = context.resources.displayMetrics
        return mDisplayMetrics.heightPixels
    }
    /**
     * 拨打电话
     */
    fun callPhone(context: Context,phone:String) {
        if(phone == null || phone == ""){
            return
        }
        BasePermissionUtils.checkPermission(object: PermissionListener {
            @SuppressLint("MissingPermission")
            override fun onPermissionGranted() {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
                context.startActivity(intent)
            }
            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            }
        },"请允许程序获取您的拨打电话权限,否则无法使用该功能", Manifest.permission.CALL_PHONE)
    }
}