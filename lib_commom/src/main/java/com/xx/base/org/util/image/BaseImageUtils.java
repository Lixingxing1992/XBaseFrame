package com.xx.base.org.util.image;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.xx.base.org.R;
import com.xx.base.org.util.BaseUtils;
import com.xx.base.org.util.image.impl.GlideUtils;

/**
 * 图片加载工具类
 *
 * 初始化设置 init
 * 调用 create()
 * 调用时设置 create(BaseImageLoader imageShowInterfaces)
 * Created by lixingxing
 */
public class BaseImageUtils {
    static BaseImageLoader imageShowInterface;
    private BaseImageUtils() {
        throw new RuntimeException(" only static method ");
    }

    public static void init(BaseImageLoader imageShowInterfaces){
        if(imageShowInterfaces == null){
            imageShowInterfaces = new GlideUtils();
        }
        imageShowInterface = imageShowInterfaces;
    }

    // 默认是 GlideUtils
    public static BaseImageLoader create(){
        if(imageShowInterface == null){
            imageShowInterface = new GlideUtils();
        }
        return imageShowInterface;
    }
    //默认是 GlideUtils
    private static BaseImageLoader create(BaseImageLoader imageShowInterfaces) {
        if(imageShowInterfaces == null){
            imageShowInterfaces = new GlideUtils();
        }
        return imageShowInterfaces;
    }

}
