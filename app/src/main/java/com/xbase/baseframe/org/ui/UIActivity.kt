package com.xbase.baseframe.org.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.xbase.baseframe.org.R
import com.xbase.baseframe.org.lib.LibActivity
import com.xbase.baseframe.org.model.FuncModel
import com.xbase.baseframe.org.ui.imageview.ImageViewActivity
import com.xbase.baseframe.org.ui.title.TitleLayoutActivity
import com.xx.base.org.page.BaseActivity
import com.xx.base.org.superadapter.BaseSuperAdapter
import com.xx.base.org.superadapter.SuperViewHolder
import com.xx.base.ui.viewgroup.BaseTitleLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ui_titlelayout.*
import org.jetbrains.anko.startActivity

/**
 * UI库
 * @author Lixingxing
 */
class UIActivity : BaseActivity() {
    var funcList = arrayListOf<FuncModel>(
        FuncModel(1, "BaseTitleLayout")
        , FuncModel(2,"ImageView")
    )

    override fun initDefaultData(intent: Intent?) {
    }

    override fun setRootView() {
        setContentView(R.layout.activity_ui)
        titleLayout.setDefault("UI控件列表").setStatuBarView()
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object : BaseSuperAdapter<FuncModel>(this, funcList, R.layout.button_item) {
            override fun onBind(helper: SuperViewHolder, viewType: Int, layoutPosition: Int, item: FuncModel?) {
                item?.apply {
                    helper.setText(R.id.func_btn, funcName)
                    helper.setOnClickListener(R.id.func_btn) {
                        when (funcId) {
                            1 -> {
                                //通用标题头
                                startActivity<TitleLayoutActivity>()
                            }
                            2 ->{
                                //ImageView
                                startActivity<ImageViewActivity>()
                            }
                        }
                    }
                }
            }

        }
    }

    override fun getData() {
    }

}
