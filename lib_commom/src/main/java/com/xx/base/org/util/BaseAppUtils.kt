package com.xx.base.org.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import com.xx.base.org.tedpermission.BasePermissionUtils
import com.xx.base.org.tedpermission.PermissionListener
import java.lang.Exception
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


    /**
     * 获取App名称
     *
     * @param context 上下文
     * @return App名称
     */
    fun getAppName(context: Context): String? {
        return getAppName(context, context.packageName)
    }

    /**
     * 获取App名称
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App名称
     */
    fun getAppName(context: Context, packageName: String): String? {
        if (BaseStringUtils.isEmpty(packageName)) return null
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            return pi?.applicationInfo?.loadLabel(pm)?.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return null
        }

    }



    /**
     * 获取App名称
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App名称
     */
    //打开WIFI网络设置界面
    fun openSettingWIFIUI( activity: Activity) {
        activity.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS));
    }


    fun openLocationSetting(activity: Activity){

    }


    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    fun  isOPen( context:Context):Boolean {
        var locationManager
        = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        var gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        var network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps|| network) {
            return true;
        }
        return false;
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
   fun openGPS( context:Context) {
        var GPSIntent = Intent()
        GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider")
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE")
        GPSIntent.setData(Uri.parse("custom:3"))
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send()
        } catch (e:Exception) {
            e.printStackTrace();
        }
    }
}
