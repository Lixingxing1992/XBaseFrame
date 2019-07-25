package com.xbase.baseframe.org.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.xbase.baseframe.org.R
import com.xbase.baseframe.org.lib.LibActivity
import com.xbase.baseframe.org.model.FuncModel
import com.xx.base.org.page.BaseActivity
import com.xx.base.org.superadapter.BaseSuperAdapter
import com.xx.base.org.superadapter.SuperViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

/**
 * UI库
 */
class UIActivity : BaseActivity() {
    var funcList = arrayListOf<FuncModel>(
        FuncModel(1, "通用标题头 BaseTitleLayout")
    )

    override fun initDefaultData(intent: Intent?) {
    }

    override fun setRootView() {
        setContentView(R.layout.activity_ui)
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
