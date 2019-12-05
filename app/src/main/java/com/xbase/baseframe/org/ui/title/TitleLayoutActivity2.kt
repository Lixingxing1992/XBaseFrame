package com.xbase.baseframe.org.ui.title

import android.content.Intent
import android.graphics.Color
import android.view.View
import com.xbase.baseframe.org.R
import com.xx.base.org.page.BaseActivity
import kotlinx.android.synthetic.main.ui_titlelayout.*
import org.jetbrains.anko.startActivity

/**
 * BaseTitleLayout
 * @author Lixingxing
 */
class TitleLayoutActivity2 : BaseActivity() {
    var titleStatusFromBackground = true
    override fun initDefaultData(intent: Intent) {
    }

    override fun setRootView() {
        setContentView(R.layout.ui_titlelayout)
    }

    override fun initView() {
        titleLayout.setDefault("BaseTitleLayout控件列表2")
            .setStatuBarPadding()
            .setRightText("setStatuBarView", View.OnClickListener {
                startActivity<TitleLayoutActivity>()
                finish()
            })
        radioColorSame.visibility = View.GONE
        // 状态栏颜色或背景是否和标题一致
        radioColorSame.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioColorSame1 -> {
                    titleStatusFromBackground = true
                    titleStatusColor.visibility = View.GONE
                }
                R.id.radioColorSame2 -> {
                    titleStatusFromBackground = false
                    titleStatusColor.visibility = View.VISIBLE
                }
            }
            titleLayout.titleStatusFromBackground = titleStatusFromBackground
        }
    }

    override fun getData() {

    }


    var colors = arrayOf(Color.BLACK, Color.RED, Color.parseColor("#008577"))
    var colorPosition = 0
    fun onclickBtn(view: View) {
        when (view) {
            btnChangColor -> {
                // 更换颜色
                titleLayout.setBackgroundColor(colors[colorPosition])
                if (colorPosition == (colors.size - 1)) {
                    colorPosition = 0
                } else {
                    colorPosition += 1
                }
            }
        }
    }
}
