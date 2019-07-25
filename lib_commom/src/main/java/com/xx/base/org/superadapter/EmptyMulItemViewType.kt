package com.xx.base.org.superadapter

import com.xx.base.org.superadapter.SimpleMulItemViewType
import com.xx.base.org.superadapter.SuperConfig

/**
 * Created by lixingxing on 2019/5/29.
 */
class EmptyMulItemViewType<T>(defaultItemLayout:Int,defaultEmyptyItemLayout:Int) : SimpleMulItemViewType<T>(){
    var defaultItemLayout:Int = 0
    var defaultEmyptyItemLayout:Int = 0
    init{
        this.defaultItemLayout = defaultItemLayout
        this.defaultEmyptyItemLayout = defaultEmyptyItemLayout
    }

    override fun getItemViewType(position: Int, t: T): Int {
        return if(t == null){
            SuperConfig.CODE_EMPTY
        }else{
            SuperConfig.CODE_DEFAULT
        }
    }

    override fun getLayoutId(viewType: Int): Int {
        return if(viewType == SuperConfig.CODE_EMPTY){
            defaultEmyptyItemLayout
        }else{
            defaultItemLayout
        }
    }
}
