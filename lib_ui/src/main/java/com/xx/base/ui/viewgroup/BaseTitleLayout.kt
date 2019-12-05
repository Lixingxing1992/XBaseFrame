package com.xx.base.ui.viewgroup

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.qqkj.base.ui.R
import com.xx.base.ui.util.BaseStatusBarUtils
import kotlinx.android.synthetic.main.view_title_content.view.*

/**
 * 通用标题头
 * @author Lixingxing
 */
class BaseTitleLayout @JvmOverloads constructor(
    var baseContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(baseContext, attrs, defStyleAttr) {
    var statusBarCode = 0 // 状态栏改变方法  1 setStatuBarPadding   2 setStatuBarView

    //状态栏背景是否和 BaseTitleLayout 的background一样
    var titleStatusFromBackground = true
    //状态栏背景
    var titleStatusBackground: Drawable? = null

    // 是否显示中间标题文字
    var titleTextVisible = true
    // 标题文字内容
    var titleText = ""
    // 左边返回按钮是否显示
    var titleLeftBackVisible = true
    // 左边返回按钮图片控件
    lateinit var view_layout_title_left: ImageView
    // 右边按钮图片控件
    lateinit var view_layout_title_right: ImageView


    init {
        LayoutInflater.from(baseContext).inflate(R.layout.view_title_content, this)

        view_layout_title_left = layout_title_left
        view_layout_title_right = layout_title_right

        var typedArray = baseContext.obtainStyledAttributes(attrs, R.styleable.BaseTitleLayout)
        for (i in 0 until typedArray.getIndexCount()) {
            val attr = typedArray.getIndex(i)
            if (attr == R.styleable.BaseTitleLayout_titleTextVisible) {
                titleTextVisible = typedArray.getBoolean(attr, titleTextVisible)
            } else if (attr == R.styleable.BaseTitleLayout_titleText) {
                titleText = typedArray.getString(attr) ?: ""
            } else if (attr == R.styleable.BaseTitleLayout_titleLeftBackVisible) {
                // 左边返回按钮是否显示
                titleLeftBackVisible = typedArray.getBoolean(attr, titleLeftBackVisible)
            } else if (attr == R.styleable.BaseTitleLayout_titleStatusFromBackground) {
                //状态栏背景是否和 BaseTitleLayout 的background相同
                titleStatusFromBackground = typedArray.getBoolean(attr, titleLeftBackVisible)
            } else if (attr == R.styleable.BaseTitleLayout_titleStatusBackground) {
                // 状态栏背景
                titleStatusBackground = typedArray.getDrawable(attr)
            }
        }
        typedArray.recycle()
        initSetting()
    }

    var isChangeView = false
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        var count = childCount
        if (count > 1 && !isChangeView) {
            var views = ArrayList<View>()
            for (i in (count - 1) downTo 1) {
                views.add(getChildAt(i))
            }
            for (view in views) {
                var layoutParams = view.layoutParams
                removeView(view)
                layout_title_layout.addView(view, layoutParams)
            }
            isChangeView = true
        } else {
            isChangeView = true
        }
    }

    fun initSetting() {
        layout_title_text.text = titleText
        if (titleTextVisible) {
            layout_title_text.visibility = View.VISIBLE
        } else {
            layout_title_text.visibility = View.GONE
        }
        if (titleLeftBackVisible) {
            layout_title_left.visibility = View.VISIBLE
            layout_title_left.setOnClickListener {
                (baseContext as Activity).finish()
            }
        }
    }

    override fun setBackground(background: Drawable?) {
        layout_title_layout.background = background
    }

    // 设置标题头paddingtop
    public fun setStatuBarPadding(): BaseTitleLayout {
        statusBarCode = 1
        BaseStatusBarUtils.setPaddingSmart(baseContext, layout_title_layout)
        return this
    }

    // 设置状态栏颜色
    fun setStatuBarView(): BaseTitleLayout {
        statusBarCode = 2

        var drawable = titleStatusBackground

        if (titleStatusFromBackground) {
            drawable = layout_title_layout.background
        }

        if(drawable == null){
            BaseStatusBarUtils.immersive(
                (baseContext as Activity)
            )
        }else{
            if (drawable is ColorDrawable) {
                var color = (drawable as ColorDrawable).color
                BaseStatusBarUtils.immersive(
                    (baseContext as Activity), color
                )
            }
        }

        return this
    }

    // 设置标题文字
    public fun setTitleText(str: String): BaseTitleLayout {
        layout_title_text.text = str
        return this
    }
    // 设置标题文字颜色
    public fun setTitleTextColor(color: Int): BaseTitleLayout {
        layout_title_text.setTextColor(color)
        return this
    }

    // 返回+文字标题
    public fun setDefault(str: String): BaseTitleLayout {
        layout_title_left.visibility = View.VISIBLE
        layout_title_left.setOnClickListener {
            (baseContext as Activity).finish()
        }
        setTitleText(str)
        return this
    }

    // 只有文字标题
    public fun setTitleOnly(str: String): BaseTitleLayout {
        layout_title_left.visibility = View.GONE
        setTitleText(str)
        return this
    }

    // 只有返回箭头
    public fun setLeftBackOnly(): BaseTitleLayout {
        titleTextVisible = false
        layout_title_text.visibility = View.GONE
        return this
    }

    // 设置左边图片
    public fun setLeftIcon(resId: Int, onClickListener: OnClickListener?): BaseTitleLayout {
        layout_title_left.visibility = View.VISIBLE
        if (resId != 0) layout_title_left.setImageResource(resId)
        layout_title_left.setOnClickListener(onClickListener)
        return this
    }

    // 设置右边图片
    public fun setRightIcon(resId: Int, onClickListener: OnClickListener?): BaseTitleLayout {
        layout_title_right.visibility = View.VISIBLE
        layout_title_right.setImageResource(resId)
        layout_title_right.setOnClickListener(onClickListener)
        return this
    }

    // 设置右边文字
    public fun setRightText(str: String, onClickListener: OnClickListener?): BaseTitleLayout {
        layout_title_right_text.visibility = View.VISIBLE
        layout_title_right_text.text = str
        layout_title_right_text.setOnClickListener(onClickListener)
        return this
    }

    // 因为默认是显示 左边返回键和中间文字的，如果有特殊要求，请调用该方法
    public fun hideDefault(): BaseTitleLayout {
        layout_title_left.visibility = View.GONE
        layout_title_text.visibility = View.GONE
        return this
    }


}
