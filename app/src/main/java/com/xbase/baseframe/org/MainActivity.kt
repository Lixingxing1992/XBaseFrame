package com.xbase.baseframe.org

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.xbase.baseframe.org.lib.LibActivity
import com.xbase.baseframe.org.model.FuncModel
import com.xbase.baseframe.org.ui.UIActivity
import com.xx.base.org.page.BaseActivity
import com.xx.base.org.superadapter.BaseSuperAdapter
import com.xx.base.org.superadapter.SuperViewHolder
import com.xx.base.org.util.BaseToastUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

/**
 * @author Lixingxing
 */
class MainActivity : BaseActivity() {
    var funcList = arrayListOf<FuncModel>(
        FuncModel(0,"project库"),
        FuncModel(1,"基础库"),
        FuncModel(2,"基础UI库")
    )

    override fun initDefaultData(intent: Intent?) {
    }

    override fun setRootView() {
        setContentView(R.layout.activity_main)
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object: BaseSuperAdapter<FuncModel>(this,funcList,R.layout.button_item){
            override fun onBind(helper: SuperViewHolder, viewType: Int, layoutPosition: Int, item: FuncModel?) {
                item?.apply {
                    helper.setText(R.id.func_btn,funcName)
                    helper.setOnClickListener(R.id.func_btn) {
                        when(funcId){
                            1->{
                                // project库
                                baseContext.startActivity<LibActivity>()
                            }
                            1->{
                                // lib库
                                baseContext.startActivity<LibActivity>()
                            }
                            2->{
                                // ui库
                                baseContext.startActivity<UIActivity>()
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
