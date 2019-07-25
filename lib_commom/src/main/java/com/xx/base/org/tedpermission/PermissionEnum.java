/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Raphaël Bussa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.xx.base.org.tedpermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

/**
 * Created by raphaelbussa on 22/06/16.
 */
@SuppressLint("InlinedApi")
public enum PermissionEnum {

    BODY_SENSORS(Manifest.permission.BODY_SENSORS,"传感器权限"),
    READ_CALENDAR(Manifest.permission.READ_CALENDAR,"允许程序读取用户的日程信息"),
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR,"写入日程，但不可读取"),
    READ_CONTACTS(Manifest.permission.READ_CONTACTS,"允许应用访问联系人通讯录信息"),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS,"写入联系人，但不可读取"),
    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS,"访问GMail账户列表"),
    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE,"程序可以读取设备外部存储空间的文件"),
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE,"允许程序写入外部存储空间"),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION,"允许程序通过GPS芯片接收卫星的定位信息"),
    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION,"获取定位权限"),
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO,"录制声音通过手机或耳机的麦克"),
    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE,"访问电话状态"),
    CALL_PHONE(Manifest.permission.CALL_PHONE,"允许程序拨号"),
    READ_CALL_LOG(Manifest.permission.READ_CALL_LOG,"读取通话记录"),
    WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG,"允许程序写入（但是不能读）用户的联系人数据"),
    ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL,"允许一个应用程序添加语音邮件系统"),
    USE_SIP(Manifest.permission.USE_SIP,"允许程序使用SIP视频服务"),
    PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS,"允许程序监视，修改或放弃播出电话"),
    CAMERA(Manifest.permission.CAMERA,"允许访问摄像头进行拍照"),
    SEND_SMS(Manifest.permission.SEND_SMS,"发送短信"),
    RECEIVE_SMS(Manifest.permission.RECEIVE_SMS,"允许程序接收短信"),
    READ_SMS(Manifest.permission.READ_SMS,"允许程序读取短信内容"),
    RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH,"接收WAP PUSH信息"),
    RECEIVE_MMS(Manifest.permission.RECEIVE_MMS,"接收彩信");

    private final String permission;
    private final String permissionDesc;

    PermissionEnum(String permission, String permissionDesc) {
        this.permission = permission;
        this.permissionDesc = permissionDesc;
    }

    public static PermissionEnum fromManifestPermission(@NonNull String value) {
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            if (value.equalsIgnoreCase(permissionEnum.permission)) {
                return permissionEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return permission;
    }

    public String getPermission() {
        return permission;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }
}