package com.xbase.baseframe.org.ui.imageview

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.xbase.baseframe.org.R
import com.xbase.baseframe.org.model.FuncModel
import com.xx.base.org.page.BaseActivity
import com.xx.base.org.superadapter.BaseSuperAdapter
import com.xx.base.org.superadapter.SuperViewHolder
import kotlinx.android.synthetic.main.default_list.*
import kotlinx.android.synthetic.main.default_list.titleLayout
import org.jetbrains.anko.startActivity

/**
 * ImageView控件选择
 * @author Lixingxing
 */
class ImageViewActivity : BaseActivity() {
    var funcList = arrayListOf<FuncModel>(
        FuncModel(1, "BasePinchImageView")
        , FuncModel(2,"BaseCircleImageView")
    )

    override fun initDefaultData(intent: Intent) {

    }

    override fun setRootView() {
        setContentView(R.layout.default_list)
    }

    override fun initView() {
        titleLayout.setDefault("ImageView控件列表").setStatuBarView()
    }

    override fun getData() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object : BaseSuperAdapter<FuncModel>(this, funcList, R.layout.button_item) {
            override fun onBind(helper: SuperViewHolder, viewType: Int, layoutPosition: Int, item: FuncModel?) {
                item?.apply {
                    helper.setText(R.id.func_btn, funcName)
                    helper.setOnClickListener(R.id.func_btn) {
                        when (funcId) {
                            1 -> {
                                //BasePinchImageView
                                startActivity<ImageViewShowActivity>("type" to "BasePinchImageView")
                            }
                            2 ->{
                                //BaseCircleImageView
                                startActivity<ImageViewShowActivity>("type" to "BaseCircleImageView")
                            }
                        }
                    }
                }
            }

        }
    }
}
