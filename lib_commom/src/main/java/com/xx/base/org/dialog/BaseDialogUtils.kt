package com.xx.base.org.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.app.ProgressDialog
import com.xx.base.org.dialog.interfaces.OnDoubleClickListener
import com.xx.base.org.dialog.interfaces.OnSingleClickListener


/**
 * Created by lixingxing on 2019/4/25.
 */
object BaseDialogUtils {
    /**
     * 显示 确认取消提示框
     */
    fun showNormalDialog(
        context: Context,
        msg: String,
        onDoubleClickListener: OnDoubleClickListener? = null
    ) {
        showNormalDialog(context, msg, "提示", onDoubleClickListener)
    }

    fun showNormalDialog(
        context: Context,
        msg: String,
        title: String = "提示",
        onDoubleClickListener: OnDoubleClickListener? = null
    ):Dialog? {
        if (context !is Activity) {
            return null
        }
        var normalDialog =
            AlertDialog.Builder(context)
        if (title != null && title != "") {
            normalDialog.setTitle(title)
        }
        normalDialog.setMessage(msg)
        normalDialog.setPositiveButton("确定") { _: DialogInterface, _: Int ->
            if (onDoubleClickListener != null) {
                onDoubleClickListener.onSure()
            }
        }
        normalDialog.setNegativeButton("关闭")
        { _: DialogInterface, _: Int ->
            if (onDoubleClickListener != null) {
                onDoubleClickListener.onCancel()
            }
        }
        // 显示
        return normalDialog.show()
    }

    /**
     * 显示 确认提示框
     */
    fun showMessageDialog(
        context: Context,
        msg: String,
        onSingleClickListener: OnSingleClickListener? = null
    ) {
        showMessageDialog(context, msg, "提示", onSingleClickListener)
    }

    fun showMessageDialog(
        context: Context,
        msg: String,
        title: String = "",
        onSingleClickListener: OnSingleClickListener? = null
    ) {
        if (context !is Activity) {
            return
        }
        var normalDialog =
            AlertDialog.Builder(context)
        if (title != null && title != "") {
            normalDialog.setTitle(title)
        }
        normalDialog.setMessage(msg)
        normalDialog.setPositiveButton("确定") { _: DialogInterface, _: Int ->
            if (onSingleClickListener != null) {
                onSingleClickListener.onSure()
            }
        }
        // 显示
        normalDialog.show()
    }


    /**
     * 显示等待 dialog
     */
    fun showWaitingDialog(
        context: Context,
        msg: String = "等待中...",
        title: String = "",
        isCancelable: Boolean = false
    ): Dialog {
        val waitingDialog = ProgressDialog(context)
        if (title != null && title != "") {
            waitingDialog.setTitle(title)
        }
        waitingDialog.setMessage(msg)
        waitingDialog.isIndeterminate = true
        waitingDialog.setCancelable(isCancelable)
        waitingDialog.show()
        return waitingDialog
    }

    fun showProgressDialog(  context: Context,
                             msg: String = "正在处理中..."): ProgressDialog {
        /* @setProgress 设置初始进度
         * @setProgressStyle 设置样式（水平进度条）
         * @setMax 设置进度最大值
         */
        var MAX_PROGRESS = 100
        var progressDialog =
            ProgressDialog(context)
        progressDialog.progress = 0
        progressDialog.setTitle(msg)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.max = MAX_PROGRESS
        progressDialog.show()
        return progressDialog
    }
}

