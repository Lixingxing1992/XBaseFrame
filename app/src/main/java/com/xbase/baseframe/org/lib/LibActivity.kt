package com.xbase.baseframe.org.lib

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.xbase.baseframe.org.R
import com.xbase.baseframe.org.model.FuncModel
import com.xx.base.org.superadapter.BaseSuperAdapter
import com.xx.base.org.superadapter.SuperViewHolder
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 基础库
 */
class LibActivity : AppCompatActivity() {
    var funcList = arrayListOf<FuncModel>(
        FuncModel(1,"通用标题头 BaseTitleLayout")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object: BaseSuperAdapter<FuncModel>(this,funcList,R.layout.button_item){
            override fun onBind(helper: SuperViewHolder, viewType: Int, layoutPosition: Int, item: FuncModel?) {
                item?.apply {
                    helper.setText(R.id.func_btn,funcName)
                    helper.itemView.setOnClickListener {
                    }
                }
            }

        }
    }
}
