package com.xbase.baseframe.org.ui.imageview

import android.content.Intent
import android.view.View
import com.xbase.baseframe.org.R
import com.xx.base.org.page.BaseActivity
import kotlinx.android.synthetic.main.ui_imageview_show.*

/**
 * @author Lixingxing
 */
class ImageViewShowActivity : BaseActivity() {
    internal var type = "BasePinchImageView"
    override fun initDefaultData(intent: Intent) {
        type = intent.getStringExtra("type")
    }

    override fun setRootView() {
        setContentView(R.layout.ui_imageview_show)
    }

    override fun initView() {
        when(type){
            "BasePinchImageView"->{
                titleLayout.setDefault("BasePinchImageView")
                basePinchImageView.visibility = View.VISIBLE
                basePinchImageView.setImageResource(R.mipmap.timo)
            }
            "BaseCircleImageView"->{
                titleLayout.setDefault("BaseCircleImageView")
                baseCircleImageView.visibility = View.VISIBLE
                baseCircleImageView.setImageResource(R.mipmap.timo)
            }
        }
        titleLayout.setStatuBarView()
    }

    override fun getData() {

    }
}
