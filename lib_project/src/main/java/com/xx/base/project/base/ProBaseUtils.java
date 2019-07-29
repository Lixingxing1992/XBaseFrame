package com.xx.base.project.base;

import android.content.Context;
import android.graphics.Color;
import com.xx.base.org.util.BaseLocalDbUtils;
import com.xx.base.org.util.BaseStatusBarUtils;
import com.xx.base.org.util.BaseUtils;
import com.xx.base.org.util.image.BaseImageUtils;
import com.xx.base.org.util.image.impl.GlideUtils;
import com.xx.base.project.config.ProFileConfig;
import com.xx.base.ui.BaseUIUtils;

/**
 * 项目基础设置（各种类库的一些初始化）
 * Created by lixingxing on 2019/7/25.
 */
public class ProBaseUtils {
    private static ProBaseUtils baseProjectUtils = null;
    public static ProBaseUtils getInstance(Context context){
        baseProjectUtils = new ProBaseUtils(context);
        return baseProjectUtils;
    }
    private Context context;
    private ProBaseUtils(Context context){
        this.context = context;
    }
    public ProBaseUtils init(){
        setttingForPro(context);
        return this;
    }

    private void setttingForPro(Context context){
        // 设置全局上下文和一些属性

        // 初始化全局基础工具类
        BaseUtils.init(context);

        // 初始化全局UI工具类
        BaseUIUtils.init(context);

        // 初始化nosql数据库
        BaseLocalDbUtils.init(context);
        // 设置网络请求
//        BaseHttpUtils.init(context,BuildConfig.DEBUG);
//        BaseHttpUtils.init(TDHttpService.class,TDDataListener.class);
//        BaseHttpUtils.initFile(DefaultFileService.class,TDDataListener.class);
        // 设置项目图片加载库 默认 Glide
        BaseImageUtils.init(new GlideUtils());
        // 设置项目在SD卡上的文件夹名称
        ProFileConfig.init("xbaseframe","xbaseframe_default");
        // 设置状态栏默认颜色
        BaseStatusBarUtils.init(Color.parseColor("#ff9480"),0);

//        // 设置 SmartRefreshLayout 的 header 和 footer
//        //设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                //全局设置主题颜色
//                layout.setPrimaryColors(Color.parseColor("#ff9480"), Color.WHITE);
//                return new BezierCircleHeader(context);
//                //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
//            }
//        });
//        //设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
//            @Override
//            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
//                //指定为经典Footer，默认是 BallPulseFooter
//                return new ClassicsFooter(context).setDrawableSize(20);
//            }
//        });
    }
}
